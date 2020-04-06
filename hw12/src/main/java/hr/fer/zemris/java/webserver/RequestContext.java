package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents one client request.
 * 
 * @author Filip Hustić
 *
 */
public class RequestContext {

	/**
	 * Class represents cookie which holds information about request.
	 * 
	 * @author Filip Hustić
	 *
	 */
	public static class RCCookie {
		/**
		 * Name of value.
		 */
		private String name;
		/**
		 * Value.
		 */
		private String value;
		/**
		 * Domain from which request is coming.
		 */
		private String domain;
		/**
		 * Path of request.
		 */
		private String path;
		/**
		 * Max age of request.
		 */
		private Integer maxAge;

		/**
		 * Basic constructor
		 * 
		 * @param name    of value.
		 * @param value   value.
		 * @param domain  domain.
		 * @param path    path.
		 * @param maxName maximal name.
		 * @throws NullPointerException if name or value are null;
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			Objects.requireNonNull(name);
			Objects.requireNonNull(value);
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Set-Cookie: ");
			sb.append(name);
			sb.append("=");
			sb.append(value);

			if (domain != null) {
				sb.append("; Domain=");
				sb.append(domain);
			}

			if (path != null) {
				sb.append("; Path=");
				sb.append(path);
			}

			if (maxAge != null) {
				sb.append("; Max-age=");
				sb.append(maxAge);
			}

			return sb.toString();
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public String getDomain() {
			return domain;
		}

		public String getPath() {
			return path;
		}

		public Integer getMaxName() {
			return maxAge;
		}

	}

	/**
	 * Stream through which request is written.
	 */
	private OutputStream outputStream;
	/**
	 * Charset of request.
	 */
	private Charset charset;
	/**
	 * Encoding of charset.
	 */
	private String encoding = "UTF-8";
	/**
	 * Status code.
	 */
	private int statusCode = 200;
	/**
	 * Status text.
	 */
	private String statusText = "OK";
	/**
	 * Mime type.
	 */
	private String mimeType = "text/html";
	/**
	 * Length of content.
	 */
	private Long contentLength = null;

	/**
	 * Map of parameters. Map is immutable.
	 */
	private Map<String, String> parameters;
	/**
	 * Map of temporary parameters.
	 */
	private Map<String, String> temporaryParameters;
	/**
	 * Map of persistent, changeable, parameters
	 */
	private Map<String, String> persistentParameters;
	/**
	 * List of output cookies.
	 */
	private List<RCCookie> outputCookies;
	/**
	 * Boolean which determines if header of request has been written.
	 */
	private boolean headerGenerated = false;

	/**
	 * Dispatcher.
	 */
	private IDispatcher iDispatcher;

	/**
	 * Charset of header.
	 */
	private static final Charset HEADER_CHARSET = StandardCharsets.ISO_8859_1;

	/**
	 * Constructor.
	 * 
	 * @param outputStream         outputStream.
	 * @param parameters           given parameters.
	 * @param persistentParameters persistent parameters which will be copied into
	 *                             immutable map.
	 * @param outputCookies        output cookies.
	 * @throws NullPointerException if outputStream is null.
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		this(outputStream, parameters, persistentParameters, outputCookies, new HashMap<String, String>(), null);
	}

	/**
	 * Constructor.
	 * 
	 * @param outputStream         outputStream.
	 * @param parameters           given parameters.
	 * @param persistentParameters persistent parameters which will be copied into
	 *                             immutable map.
	 * @param temporaryParameters  temporary parameters.
	 * @param iDispatcher          dispatcher.
	 * @param outputCookies        output cookies.
	 * @throws NullPointerException if outputStream is null.
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher iDispatcher) {
		Objects.requireNonNull(outputStream);

		this.outputStream = outputStream;
		this.parameters = Collections.unmodifiableMap(parameters);
		this.persistentParameters = persistentParameters;
		this.outputCookies = outputCookies;
		this.temporaryParameters = temporaryParameters;
		this.iDispatcher = iDispatcher;
	}

	/**
	 * Method gets parameter form parameters map or null if given key is not key in
	 * the map.
	 * 
	 * @param name key.
	 * @return value of given key or null if key doesn't not exist.
	 */
	public String getParameter(String name) {
		if (name == null) {
			return null;
		}
		return parameters.get(name);
	}

	/**
	 * Method return immutable set of names in parameters map.
	 * 
	 * @return immutable set of names in parameters map.
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(new HashSet<String>(parameters.keySet()));
	}

	/**
	 * Method gets parameter form persistentParameters map or null if given key is
	 * not key in the map.
	 * 
	 * @param name key.
	 * @return value of given key or null if key doesn't not exist.
	 */
	public String getPersistentParameter(String name) {
		if (name == null) {
			return null;
		}

		return persistentParameters.get(name);
	}

	/**
	 * Method return immutable set of names in persistent parameters map.
	 * 
	 * @return immutable set of names in parameters map.
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(new HashSet<String>(persistentParameters.keySet()));
	}

	/**
	 * Method puts value into persistent parameter map.
	 * 
	 * @param name  key.
	 * @param value value.
	 * @throws NullPointerException if name is null.
	 */
	public void setPersistentParameter(String name, String value) {
		Objects.requireNonNull(name);
		persistentParameters.put(name, value);
	}

	/**
	 * Method removes object form persistent parameter map under given key.
	 * 
	 * @param name key.
	 */
	public void removePersistentParameter(String name) {
		if (name == null) {
			return;
		}
		persistentParameters.remove(name);
	}

	/**
	 * Method returns value form temporary parameter map.
	 * 
	 * @param name key.
	 * @return value of given key.
	 */
	public String getTemporaryParameter(String name) {
		if (name == null) {
			return null;
		}
		return temporaryParameters.get(name);
	}

	/**
	 * Method return immutable set of names in tremporary parameters map.
	 * 
	 * @return immutable set of names in parameters map.
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(new HashSet<String>(temporaryParameters.keySet()));
	}

	/**
	 * Method puts value into temporary parameter map.
	 * 
	 * @param name  key.
	 * @param value value.
	 * @throws NullPointerException if name is null.
	 */
	public void setTemporaryParameter(String name, String value) {
		Objects.requireNonNull(name);
		temporaryParameters.put(name, value);
	}

	/**
	 * Method removes object form temporary parameter map under given key.
	 * 
	 * @param name key.
	 */
	public void removeTemporaryParameter(String name) {
		if (name == null) {
			return;
		}
		temporaryParameters.remove(name);
	}

	/**
	 * Method adds given cookie into cookies list.
	 * 
	 * @param cookie to be added.
	 * @throws NullPointerException if cookie is null.
	 * @throws RuntimeException     if cookie is added after generation of header.
	 */
	public void addRCCookie(RCCookie cookie) {
		Objects.requireNonNull(cookie);
		if (headerGenerated) {
			throw new RuntimeException("Change tired to be done after header generation.");
		}

		outputCookies.add(cookie);
	}

	/**
	 * 
	 * @return identifier which is unique for current user session
	 */
	public String getSessionID() {
		return "";
	}

	/**
	 * Method writes given text into output stream by predefined charset.
	 * 
	 * @param text to be written
	 * @return @this
	 * @throws IllegalCharsetNameException if predefined charset is invalid.
	 * @throws IOException                 if there was error while writing through
	 *                                     output stream.
	 */
	public RequestContext write(byte[] data) throws IOException {
		return write(data, 0, data.length);
	}

	/**
	 * Method writes given text into output stream by predefined charset.
	 * 
	 * @param text to be written
	 * @return @this
	 * @throws IllegalCharsetNameException if predefined charset is invalid.
	 * @throws IOException                 if there was error while writing through
	 *                                     output stream.
	 */
	public RequestContext write(String text) throws IOException {
		if (charset == null) {
			charset = Charset.forName(encoding);
		}
		byte[] data = text.getBytes(StandardCharsets.UTF_8);
		return write(data, 0, data.length);
	}

	/**
	 * Method writes given text into output stream by predefined charset.
	 * 
	 * @param text to be written
	 * @return @this
	 * @throws IllegalCharsetNameException if predefined charset is invalid.
	 * @throws IOException                 if there was error while writing through
	 *                                     output stream.
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (charset == null) {
			charset = Charset.forName(encoding);
		}

		if (!headerGenerated) {
			constructHeader();
			headerGenerated = true;
		}

		outputStream.write(data, offset, len);
		return this;
	}

	/**
	 * Method constructs header.
	 * 
	 * @throws IOException
	 */
	private void constructHeader() throws IOException {
		if (statusText == null || mimeType == null) {
			throw new NullPointerException("Some of predefined args for header construction are not defined");
		}
		writeStatus();
		writeContentType();
		writeCookies();

		outputStream.write("\r\n".getBytes(HEADER_CHARSET));
	}

	/**
	 * Method writes status in header.
	 * 
	 * @throws IOException
	 */
	private void writeStatus() throws IOException {
		StringBuilder status = new StringBuilder();

		status.append("HTTP/1.1 ");
		status.append(statusCode);
		status.append(" ");
		status.append(statusText);
		status.append("\r\n");

		byte[] data = status.toString().getBytes(HEADER_CHARSET);
		outputStream.write(data);
	}

	/**
	 * Method writes content in header.
	 * 
	 * @throws IOException
	 */
	private void writeContentType() throws IOException {
		StringBuilder content = new StringBuilder();

		content.append("Content-Type: ");
		content.append(mimeType);

		if (mimeType.startsWith("text/")) {
			content.append(" charset=");
			content.append(StandardCharsets.UTF_8);
		}

		if (contentLength != null) {
			content.append("\r\n");
			content.append("Content-Length: ");
			content.append(contentLength);
		}

		content.append("\r\n");

		byte[] data = content.toString().getBytes(HEADER_CHARSET);
		outputStream.write(data);
	}

	/**
	 * Method writes cookies in header.
	 * 
	 * @throws IOException
	 */
	private void writeCookies() throws IOException {
		if (!outputCookies.isEmpty()) {

			for (RCCookie cookie : outputCookies) {
				StringBuilder cookieOutput = new StringBuilder();
				cookieOutput.append(cookie.toString());
				cookieOutput.append("\r\n");
				outputStream.write(cookieOutput.toString().getBytes(HEADER_CHARSET));
			}

		}

	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public String getEncoding() {
		return encoding;
	}

	/**
	 * Method sets encoding.
	 * 
	 * @param encoding
	 * @throws RuntimeException if header of request has already been constructed.
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new RuntimeException("Change tired to be done after header generation.");
		}

		this.encoding = encoding;
	}

	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Method sets statusCode.
	 * 
	 * @param statusCode
	 * @throws RuntimeException if header of request has already been constructed.
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException("Change tired to be done after header generation.");
		}
		this.statusCode = statusCode;
	}

	public String getStatusText() {
		return statusText;
	}

	/**
	 * Method sets statusText.
	 * 
	 * @param statusText
	 * @throws RuntimeException if header of request has already been constructed.
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException("Change tired to be done after header generation.");
		}
		this.statusText = statusText;
	}

	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Method sets mime type.
	 * 
	 * @param mimeType
	 * @throws RuntimeException if header of request has already been constructed.
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException("Change tired to be done after header generation.");
		}
		this.mimeType = mimeType;
	}

	public Long getContentLength() {
		return contentLength;
	}

	/**
	 * Method set contentLength.
	 * 
	 * @param contentLength
	 * @throws RuntimeException if header of request has already been constructed.
	 */
	public void setContentLength(Long contentLength) {
		if (headerGenerated) {
			throw new RuntimeException("Change tired to be done after header generation.");
		}
		this.contentLength = contentLength;
	}

	public IDispatcher getiDispatcher() {
		return iDispatcher;
	}

}
