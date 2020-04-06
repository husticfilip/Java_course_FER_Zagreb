package hr.fer.zemris.java.hw03.script.tester;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class that helps in testing of parser. Document that can be handed to parser
 * must be in resources directory.
 * 
 * @author Filip Hustić
 *
 */
public class SmartScriptTester {

	/**
	 * Name of the file which will be parsed.
	 */
	private static String fileName= "HWExample.txt";
	
	public static void main(String[] args) {
		Loader loader = new Loader();
		String docBody = loader.loader(fileName);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			e.printStackTrace();
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			e.printStackTrace();
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);

	}

	/**
	 * Method creates original document from structural tree. It delegates creating
	 * original document to {@link #createViaRecursion(Node, StringBuilder)}.
	 * 
	 * @param document node which is root node of structural tree.
	 * @return original document represented by structural tree.
	 */
	public static String createOriginalDocumentBody(DocumentNode document) {
		StringBuilder line = new StringBuilder();
		createViaRecursion(document, line);
		return line.toString();
	}

	/**
	 * Method which does Depth first searching of tree and appending to the line
	 * values of original document.
	 * 
	 * @param node parent node which elements will be appended to the line and then
	 *             expand children if there are some. Children are visited by the
	 *             order that they are placed in parents node children collection.
	 * @param line
	 */
	private static void createViaRecursion(Node node, StringBuilder line) {

		if (node instanceof ForLoopNode) {
			appendForLoopElements((ForLoopNode) node, line);
		} else if (node instanceof EchoNode)
			appendEchoElements((EchoNode) node, line);
		else if (node instanceof TextNode)
			appendTextElements((TextNode) node, line);

		if (node.numberOfChildren() != 0) {
			for (int i = 0; i < node.numberOfChildren(); ++i) {
				createViaRecursion(node.getChild(i), line);
			}
		}

		// after children of forLoopNode goes END
		if (node instanceof ForLoopNode)
			line.append("{$END$}");
	}

	/**
	 * Method which appends to the line elements of ForLoopNode which is passed to
	 * the method.
	 * @param node ForLoopNode which elements are appended to the line.
	 * @param line string builder that holds value of document.
	 */
	private static void appendForLoopElements(ForLoopNode node, StringBuilder line) {
		line.append("{$ FOR " + node.getVariable().asText() + " " + node.getStartExpression().asText() + " ");

		if (node.getStepExpression() != null) {
			line.append(node.getStepExpression().asText() + " ");
		}

		line.append(node.getEndExpression().asText() + " $} ");
	}

	/**
	 * Method which appends to the line elements of EchoNode which is passed to
	 * the method.
	 * @param node EchoNode which elements are appended to the line.
	 * @param line string builder that holds value of document.
	 */
	private static void appendEchoElements(EchoNode node, StringBuilder line) {
		line.append("{$= ");
		for (Element element : node.getElements()) {
			line.append(element.asText() + " ");
		}
		line.append("$} ");
	}

	/**
	 * Method which appends to the line elements of TextNode which is passed to
	 * the method.
	 * @param node TextNode which elements are appended to the line.
	 * @param line string builder that holds value of document.
	 */
	private static void appendTextElements(TextNode node, StringBuilder line) {
		line.append(node.getText() + " ");
	}
}
