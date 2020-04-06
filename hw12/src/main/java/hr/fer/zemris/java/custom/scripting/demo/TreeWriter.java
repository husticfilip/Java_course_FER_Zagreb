package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

public class TreeWriter {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Number of arguments must be one. Path to the smart script.");
			return;
		}

		Path path = Paths.get(args[0]);

		if (!Files.exists(path)) {
			System.out.println("File with given path does not exist");
			return;
		}

		String body;
		try {
			body = new String(Files.readAllBytes(path), "UTF-8");
		} catch (IOException e) {
			System.out.println("Problem while reading smart script. Ending program.");
			return;
		}

		SmartScriptParser parser = new SmartScriptParser(body);
		parser.getDocumentNode().accept(new WriterVisitor());

	}

	public static class WriterVisitor implements INodeVisitor {

		private int level = 1;

		@Override
		public void visitTextNode(TextNode node) {
			System.out.printf("%s ", node.getText().strip());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			printNewline();

			if (node.getStepExpression() != null) {
				System.out.printf("{$ FOR %s %s %s %s $}", node.getVariable().asText(),
						node.getStartExpression().asText(), node.getStepExpression().asText(),
						node.getEndExpression().asText());
			} else {
				System.out.printf("{$ FOR %s  %s %s $}", node.getVariable().asText(),
						node.getStartExpression().asText(), node.getEndExpression().asText());
			}

			if (node.numberOfChildren() != 0) {
				level += 4;
				printNewline();
		
				for (int i = 0, size = node.numberOfChildren(); i < size; ++i) {
					node.getChild(i).accept(this);
				}
				level -= 4;
			}

			printNewline();
			System.out.printf("{$END$}");
		}

		private void printNewline() {
			String spaces = String.format("%" + level + "s", "");
			System.out.printf("%n" + spaces);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.printf("{$ ");

			for (Element element : node.getElements()) {
				System.out.printf("%s ", element.asText());
			}

			System.out.printf("$}");

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {

			for (int i = 0, size = node.numberOfChildren(); i < size; ++i) {
				node.getChild(i).accept(this);
				System.out.printf("%n");
			}

		}

	}

}
