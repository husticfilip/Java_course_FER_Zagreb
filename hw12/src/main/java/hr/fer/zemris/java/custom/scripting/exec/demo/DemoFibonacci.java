package hr.fer.zemris.java.custom.scripting.exec.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class DemoFibonacci {

	public static void main(String[] args) {
		Path path = Paths.get("src/main/resources/htmlFibonacci.txt");

		if (!Files.exists(path)) {
			System.out.println("File with given path does not exist");
			return;
		}

		String documentBody;
		try {
			documentBody = new String(Files.readAllBytes(path), "UTF-8");
		} catch (IOException e) {
			System.out.println("Problem while reading smart script. Ending program.");
			return;
		}

		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
		
	}

}
