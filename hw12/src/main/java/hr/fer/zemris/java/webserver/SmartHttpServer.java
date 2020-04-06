package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class represents http server which accepts method Get and constructs
 * responses using smart scripts and web workers.
 * 
 * @author Filip Hustić
 *
 */
public class SmartHttpServer {
	/**
	 * Address of server.
	 */
	private String address;
	/**
	 * Domain name of server.
	 */
	private String domainName;
	/**
	 * Port on which server runs.
	 */
	private int port;
	/**
	 * Number of treads used by server.
	 */
	private int workerThreads;
	/**
	 * Max duration of user's session between two calls.
	 */
	private int sessionTimeout;
	/**
	 * Supported mimeTypes.
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Main thread of server.
	 */
	private ServerThread serverThread;

	/**
	 * Thread which will clean sessions map from old unusable sessions.
	 */
	private Thread garabgeThread;

	/**
	 * Thread pool for server requests.
	 */
	private ExecutorService threadPool;
	/**
	 * Root to the documents of server
	 */
	private Path documentRoot;

	/**
	 * Map of web workers.
	 */
	private Map<String, IWebWorker> workersMap;

	/**
	 * Map of active web sessions.
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();

	/**
	 * Class which is used to get random session id.
	 */
	private Random sessionRandom = new Random();

	/**
	 * Path to the workers directory. Initially empty string, initilaized to real
	 * path if workers are present.
	 */
	private String workersDir = "";

	/**
	 * Length of sessions id in bytes.
	 */
	private final int SID_LENGTH = 16;

	/**
	 * INITIAL path to properties documents.
	 */
	private static String PATH_TO_DOCUMENTS = "webroot";

	/**
	 * Every GARBAGE_PERIOD milisecond session map garbage collector will clean non
	 * valid entries.
	 */
	private final int GARBAGE_PERIOD = 10000;

	/**
	 * Flag which points out is server started or not.
	 */
	private volatile boolean serverStarted = false;

	/**
	 * Flag which points out if garbage thread has started.
	 */
	private volatile boolean garbageThreadStarted = false;

	/**
	 * Constructor.
	 * 
	 * @param configFilePath path to the conifg file.
	 * @throws IOException               if files can not be read.
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws InvalidPathException      if path to web root does not exist.
	 */
	public SmartHttpServer(String configFileName)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		serverThread = new ServerThread();
		workersMap = new HashMap<String, IWebWorker>();
		getProperties(configFileName);

	}

	/**
	 * Methods starts server and garbage collector thread. If server is already
	 * running it keeps on running.
	 */
	protected synchronized void start() {
		if (!serverStarted) {
			System.out.println("Starting up server.");
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread.start();
			serverStarted = true;
			startGarbageCollector();
		}
	}

	/**
	 * Method signals main server thread and garbage collector thread to stop.
	 */
	protected synchronized void stop() {
		serverStarted = false;
		garbageThreadStarted = false;
		System.out.println("Shutting down server.");
	}

	/**
	 * Method starts garbage collector thread.
	 */
	private void startGarbageCollector() {
		garabgeThread = new Thread() {
			public void run() {
				while (garbageThreadStarted) {

					synchronized (sessions) {
						Iterator<Entry<String, SessionMapEntry>> iter = sessions.entrySet().iterator();

						while (iter.hasNext()) {
							Map.Entry<String, SessionMapEntry> entry = iter.next();
							Calendar currentTime = Calendar.getInstance();

							if ((currentTime.compareTo(entry.getValue().validUntil) > 0)) {
								iter.remove();
							}
						}
					}

					try {
						Thread.sleep(GARBAGE_PERIOD);
					} catch (InterruptedException e) {
					}

				}
			}
		};
		garabgeThread.setDaemon(true);
		garabgeThread.start();
		garbageThreadStarted = true;
	}

	/**
	 * Method extracts properties from configuration file.
	 * 
	 * @param configFilePath path to the configuration file.
	 * @throws InvalidPathException      if configuration file does't exist.
	 * @throws ClassNotFoundException    if there was no class of provided web
	 *                                   worker.
	 * @throws IOException               if there was exception while reading
	 *                                   workers configuration file.
	 * @throws InstantiationException    if there was exception while initializing
	 *                                   web worker class.
	 * @throws IllegalAccessException    if there was exception while initializing
	 *                                   web worker class.
	 * @throws IllegalArgumentException  if there was exception while initializing
	 *                                   web worker class.
	 * @throws InvocationTargetException if there was exception while initializing
	 *                                   web worker class.
	 * @throws NoSuchMethodException     if there was exception while initializing
	 *                                   web worker class.
	 * @throws SecurityException         if there was exception while initializing
	 *                                   web worker class.
	 * @throws NoSuchMethodException     if configuration file misses
	 *                                   server.address, server.domainName,
	 *                                   server.documentRoot or server.port
	 * @throws SecurityException
	 *
	 */
	private void getProperties(String configFilePath)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Path serverPropPath = Paths.get(configFilePath);

		if (!Files.exists(serverPropPath)) {
			throw new InvalidPathException("server.properties not found.", "server.properties");
		}

		Properties prop = new Properties();
		prop.load(Files.newInputStream(serverPropPath));

		address = prop.getProperty("server.address");
		if (address == null) {
			throw new NoSuchElementException("Server addres not specified in server.properties.");
		}

		domainName = prop.getProperty("server.domainName");
		if (domainName == null) {
			throw new NoSuchElementException("Server domain not specified in server.properties.");
		}

		String StrdocumentRoot = prop.getProperty("server.documentRoot");
		if (StrdocumentRoot == null) {
			throw new NoSuchElementException("DocumentRoot not specified in server.properties.");
		}
		documentRoot = Paths.get(StrdocumentRoot);
		if (!Files.exists(documentRoot)) {
			throw new InvalidPathException("Invalid path to the web root", PATH_TO_DOCUMENTS);
		}

		String strPort = prop.getProperty("server.port");
		if (strPort == null) {
			throw new NoSuchElementException("Port not specified in server.properties.");
		} else {
			try {
				port = Integer.parseInt(strPort);
			} catch (NumberFormatException e) {
				throw new NumberFormatException("Port not right type in server.properties");
			}
		}

		String strWorkThr = prop.getProperty("server.workerThreads");
		if (strWorkThr == null) {
			workerThreads = 1;
		} else {
			try {
				workerThreads = Integer.parseInt(strWorkThr);
			} catch (NumberFormatException e) {
				throw new NumberFormatException("WorkerThreads not right type in server.properties");
			}
		}

		String strTimeouts = prop.getProperty("session.timeout");
		if (strTimeouts == null) {
			sessionTimeout = 10;
		} else {
			try {
				sessionTimeout = Integer.parseInt(strTimeouts);
			} catch (NumberFormatException e) {
				throw new NumberFormatException("WorkerThreads not right type in server.properties");
			}
		}

		workersDir = prop.getProperty("workerfqcn");

		getMimeTypes(prop.getProperty("server.mimeConfig"));
		getWorkers(prop.getProperty("server.workers"));
	}

	/**
	 * Method initializes workers defined in workers configuration file. If path is
	 * null method considers there were no defined workers.
	 * 
	 * @param workerProperties path to workers configuration file.
	 * @throws InvalidPathException      if provided path to worker's configuration
	 *                                   does not exist.
	 * @throws ClassNotFoundException    if there was no class of provided web
	 *                                   worker.
	 * @throws IOException               if there was exception while reading
	 *                                   workers configuration file.
	 * @throws InstantiationException    if there was exception while initializing
	 *                                   web worker class.
	 * @throws IllegalAccessException    if there was exception while initializing
	 *                                   web worker class.
	 * @throws IllegalArgumentException  if there was exception while initializing
	 *                                   web worker class.
	 * @throws InvocationTargetException if there was exception while initializing
	 *                                   web worker class.
	 * @throws NoSuchMethodException     if there was exception while initializing
	 *                                   web worker class.
	 * @throws SecurityException         if there was exception while initializing
	 *                                   web worker class.
	 */
	private void getWorkers(String workerProperties)
			throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (workerProperties == null) {
			return;
		}
		Path workerPropertiesPath = Paths.get(workerProperties);
		if (!Files.exists(workerPropertiesPath)) {
			throw new InvalidPathException(workerProperties, "Defined worker's configuration path does not exist.");
		}

		Properties prop = new Properties();
		prop.load(Files.newInputStream(workerPropertiesPath));

		for (Entry<Object, Object> entry : prop.entrySet()) {
			String path = (String) entry.getKey();
			String fqcn = (String) entry.getValue();

			if (workersMap.containsKey(path)) {
				throw new IllegalStateException("There were duplicate paths in workers.properties.");
			}

			Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			Object newObject = referenceToClass.getDeclaredConstructor().newInstance();

			IWebWorker iww = (IWebWorker) newObject;

			workersMap.put(path, iww);
		}

	}

	/**
	 * Method extracts mime types form mime type configuration file.
	 * 
	 * @param configPath path to mime type configuration file.
	 * @throws IOException            if there was exception while reading file-
	 * @throws NoSuchElementException if path is invalid.
	 */
	private void getMimeTypes(String configPath) throws IOException {
		if (configPath == null) {
			throw new NoSuchElementException("MimeConfig path not specified in server.properties.");
		}

		Path mimeConfigPath = Paths.get(configPath);
		if (!Files.exists(mimeConfigPath)) {
			throw new InvalidPathException("MimeConfig not found.", "mime.properties");
		}

		Properties prop = new Properties();
		prop.load(Files.newInputStream(mimeConfigPath));

		prop.entrySet().stream().forEach(m -> mimeTypes.put((String) m.getKey(), (String) m.getValue()));
	}

	/**
	 * Class represents thread which will processes client's requests.
	 * 
	 * @author Filip Hustić
	 *
	 */
	protected class ServerThread extends Thread {
		@Override
		public void run() {

			ServerSocket serverSocket = null;
			try {

				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			while (serverStarted) {

				try {
					Socket clientSocket = serverSocket.accept();
					threadPool.execute(new ClientWorker(clientSocket));
				} catch (IOException e) {

				}

			}

		}

	}

	/**
	 * Client worker which works on client's request.
	 * 
	 * @author Filip Hustić
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * Client socket.
		 */
		private Socket csocket;
		/**
		 * Socket's input stream.
		 */
		private InputStream is;
		/**
		 * Socket's output stream.
		 */
		private OutputStream os;

		@SuppressWarnings("unused")
		/**
		 * Version of http protocol in request.
		 */
		private String version;

		@SuppressWarnings("unused")
		/**
		 * Used method in request.
		 */
		private String method;
		/**
		 * Host od the request.
		 */
		private String host;
		/**
		 * Request path with path and parameters.
		 */
		private String requestPath;
		/**
		 * Path of request, without parameters.
		 */
		private String originalPath;
		/**
		 * Parameters part of requestPath.
		 */
		private String requestParameters;
		/**
		 * Mime type of response.
		 */
		private String mimeType;
		/**
		 * Map of parameters provided in path.
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Temporary parameters.
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * Persistent parameters of session.
		 */
		private Map<String, String> permPrams = new HashMap<String, String>();
		/**
		 * Cookies sent in response.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Session id of this worker's session-
		 */
		private String SID;
		/**
		 * Request context used to sent response.
		 */
		RequestContext context;

		/**
		 * 
		 * @param csocket client socket.
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				is = csocket.getInputStream();
				os = csocket.getOutputStream();
				byte[] reqestBytes = readRequestBytes(is);

				if (reqestBytes == null) {
					sendError(os, 400, "Bad request.");
					return;
				}

				List<String> requestLines = extractHeader(reqestBytes);
				if (requestLines.isEmpty()) {
					sendError(os, 400, "Bad request.");
					return;
				}
				// find method, version and path
				if (!extractFromFirstLine(requestLines.get(0))) {
					sendError(os, 400, "Bad request.");
					return;
				}
				// find host
				searchHost(requestLines);

				// split requestPath to path and arguments
				if (!splitRequestPath(requestPath)) {
					sendError(os, 400, "Bad request.");
					return;
				}

				// put arguments into params map
				if (!parseRequestParameters()) {
					sendError(os, 400, "Bad request.");
					return;
				}

				checkSession(requestLines);

				internalDispatchRequest(originalPath, true);

			} catch (Exception e) {
				System.out.println("Error");
				e.printStackTrace();
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
				}
			}

		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Dispatch request of this worker. In this method request is processed.
		 * 
		 * @param urlPath    path to the resource.
		 * @param directCall true if this is client call, false if this is server call.
		 * @throws Exception if method initializes worker which does not exist.
		 */
		private void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if (!accesGranted(urlPath, directCall)) {
				sendError(os, 404, "Access denied.");
				return;
			}

			if (context == null) {
				context = new RequestContext(os, params, permPrams, outputCookies, tempParams, this);
			}
			setMimeType(urlPath);

			Pattern pattern = Pattern.compile("^/ext/(.*)");
			Matcher matcher = pattern.matcher(urlPath);

			if (matcher.find()) {
				convetionOverConfig(matcher.group(1), context);

			} else if (workersMap.containsKey(urlPath)) {
				workersMap.get(urlPath).processRequest(context);

			} else {
				Path resolvedPath = resolvePath(urlPath);
				if (!Files.isRegularFile(resolvedPath) && !Files.isReadable(resolvedPath)) {
					sendError(os, 403, "Forbidden");
					return;
				}

				if (isSmartScript(urlPath)) {
					if (!processSmartScript(resolvedPath, context)) {
						sendError(os, 500, "Internal server error");
						return;
					}
				} else {
					context.setMimeType(mimeType);
					context.write(Files.readAllBytes(resolvedPath));
				}
			}
		}

		/**
		 * Method checks if session with client is already active. If it is not new
		 * session is made.
		 * 
		 * @param headerLines lines of headers.
		 */
		private void checkSession(List<String> headerLines) {
			Pattern pattern = Pattern.compile("sid=([^ ]+)");

			String tempSid = "";
			for (String line : headerLines) {
				if (line.startsWith("Cookie:")) {
					Matcher matcher = pattern.matcher(line);
					if (matcher.find()) {
						tempSid = matcher.group(1);
					}
				}
			}

			synchronized (sessions) {
				if (tempSid.isEmpty()) {
					findSidCandiadate();
				} else {
					SessionMapEntry entry = sessions.get(tempSid);

					if (entry == null) {
						findSidCandiadate();
					} else {
						Calendar currentTime = Calendar.getInstance();

						if (!entry.host.equals(host) || (currentTime.compareTo(entry.validUntil) > 0)) {
							sessions.remove(tempSid);
							findSidCandiadate();
						} else {

							SID = tempSid;
							// if session is valid update duration time.
							entry.validUntil.add(Calendar.SECOND, sessionTimeout);

						}
					}
				}
				permPrams = sessions.get(SID).map;
			}
		}

		/**
		 * Method generates session id and puts session information into sessions map.
		 */
		private void findSidCandiadate() {

			while (true) {
				byte[] b = new byte[SID_LENGTH];
				sessionRandom.nextBytes(b);
				SID = b.toString();

				if (!sessions.containsKey(SID)) {
					Calendar currentTime = Calendar.getInstance();
					currentTime.add(Calendar.SECOND, sessionTimeout);

					sessions.put(SID,
							new SessionMapEntry(SID, host, currentTime, new ConcurrentHashMap<String, String>()));
					outputCookies.add(new RCCookie("sid", SID, null, host, "/"));
					break;
				}
			}

		}

		/**
		 * Method determines if access of request is granted or not.
		 * 
		 * @param urlPath    path of request.
		 * @param directCall is call direct.
		 * @return return true if access is granted, false otherwise.
		 */
		private boolean accesGranted(String urlPath, boolean directCall) {
			if (directCall && urlPath.trim().startsWith("/private")) {
				return false;
			} else {
				return true;
			}
		}

		/**
		 * Method does convention over configuration approach. Worker class is
		 * initialized and processRequest method is called.
		 * 
		 * @param className name of the worker's class.
		 * @param context   context.
		 * @throws Exception if worker's class can not be initializes.
		 */
		private void convetionOverConfig(String className, RequestContext context) throws Exception {

			try {
				Class<?> referenceToClass;
				referenceToClass = this.getClass().getClassLoader().loadClass(workersDir + "." + className);
				Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
				IWebWorker iww = (IWebWorker) newObject;

				iww.processRequest(context);

			} catch (ClassNotFoundException e) {
				sendError(os, 400, "Bad request.");
			}
		}

		/**
		 * Method reads headers bytes.
		 * 
		 * @param is client socket input stream.
		 * @return bytes of header.
		 * @throws IOException
		 */
		private byte[] readRequestBytes(InputStream is) throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

		/**
		 * Method transforms headers bytes into header lines.
		 * 
		 * @param requestBytes bytes of header.
		 * @return list of header lines.
		 */
		private List<String> extractHeader(byte[] requestBytes) {
			String request = new String(requestBytes, StandardCharsets.US_ASCII);
			List<String> requestLines = new ArrayList<String>();

			for (String line : request.split("\n")) {
				if (line.isEmpty()) {
					break;
				}
				requestLines.add(line);
			}

			return requestLines;
		}

		/**
		 * Method extracts request's method, path and http version and returns true if
		 * those are valid.
		 * 
		 * @param line header line.
		 * @return true if method, path and http version are valid for this web server.
		 */
		private boolean extractFromFirstLine(String line) {
			String[] parts = line.strip().replaceAll(" +", " ").split(" ");

			if (parts.length != 3) {
				return false;
			}

			if (!parts[0].equals("GET")) {
				return false;
			}
			this.method = parts[0];

			if (!parts[2].equals("HTTP/1.0") && !parts[2].equals("HTTP/1.1")) {
				return false;
			}
			this.version = parts[2];
			this.requestPath = parts[1];

			return true;
		}

		/**
		 * Method searches for host in request headers.
		 * 
		 * @param lines
		 */
		private void searchHost(List<String> lines) {
			// host finder.
			Pattern pattern = Pattern.compile(" *Host: *(.+):.*?");

			this.host = domainName;
			for (String line : lines) {
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					this.host = matcher.group(1);
					break;
				}
			}
		}

		/**
		 * MEthod splits request path into path and parameters part
		 * 
		 * @param requestPath request path.
		 * @return true if request path is valid, false otherwise.
		 */
		private boolean splitRequestPath(String requestPath) {
			String[] parts = requestPath.split("\\?");

			if (parts.length > 2) {
				return false;
			}

			this.originalPath = parts[0];
			if (parts.length == 2) {
				this.requestParameters = parts[1];
			}
			return true;
		}

		/**
		 * Method parses request parameters and puts them in params map.
		 * 
		 * @return true if parameters are valid, false otherwise.
		 */
		private boolean parseRequestParameters() {
			if (requestParameters == null) {
				return true;
			}

			String[] parts = requestParameters.split("&");

			for (String part : parts) {
				String[] keyValue = part.split("=");

				if (keyValue.length != 2) {
					return false;
				}

				params.put(keyValue[0], keyValue[1]);
			}

			return true;
		}

		/**
		 * Method resolves path with document root directory.
		 * 
		 * @param path to be resolved.
		 * @return resolved path.
		 */
		private Path resolvePath(String path) {
			return Paths.get(documentRoot.toString() + "/" + path);
		}

		/**
		 * Method sets mime type of request. If no mime type was found mime type is set
		 * to application/octet-stream.
		 * 
		 * @param urlPath
		 */
		private void setMimeType(String urlPath) {
			Pattern pattern = Pattern.compile(".*\\.(.*)$");
			Matcher matcher = pattern.matcher(urlPath);

			if (!matcher.find()) {
				mimeType = "application/octet-stream\r\n";
				return;
			} else {
				String extension = matcher.group(1).strip();

				if (mimeTypes.containsKey(extension)) {
					mimeType = mimeTypes.get(extension);
				} else {
					mimeType = "application/octet-stream\r\n";
				}
			}
		}

		/**
		 * Method determines if url is path to the smartscript.
		 * 
		 * @param url
		 * @return true if url is path to the smartscript, false otherwise.
		 */
		private boolean isSmartScript(String url) {
			Pattern pattern = Pattern.compile(".*\\.smscr *$");
			Matcher matcher = pattern.matcher(url);

			return matcher.find();
		}

		/**
		 * Method processes smart script which is at given path.
		 * 
		 * @param resolvedPath   path to the smartscript.
		 * @param requestContext context.
		 * @return true if processing has gone ok, false otherwise.
		 */
		private boolean processSmartScript(Path resolvedPath, RequestContext requestContext) {
			String documentBody;
			try {
				documentBody = new String(Files.readAllBytes(resolvedPath), "UTF-8");
			} catch (IOException e) {
				System.out.println("Problem while reading smart script. Ending program.");
				return false;
			}
			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), requestContext).execute();

			return true;
		}

		/**
		 * Method sends error to the client.
		 * 
		 * @param os        output client stream.
		 * @param errorCode
		 * @param errorText
		 * @throws IOException
		 */
		private void sendError(OutputStream os, int errorCode, String errorText) throws IOException {

			os.write(("HTTP/1.1 " + errorCode + " " + errorText + "\r\n" + "Server: smart http server.\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			os.flush();

		}
	}

	/**
	 * Class represents one session with its atrubutes.
	 * 
	 * @author Filip Hustić
	 *
	 */
	private static class SessionMapEntry {
		@SuppressWarnings("unused")
		/**
		 * Session id.
		 */
		String sid;
		/**
		 * Session host.
		 */
		String host;
		/**
		 * Time until when session is valid.
		 */
		Calendar validUntil;
		/**
		 * Sessions parameters map.
		 */
		Map<String, String> map;

		public SessionMapEntry(String sid, String host, Calendar validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}

	}

}
