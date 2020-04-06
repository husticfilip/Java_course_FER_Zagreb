package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Class that represents parser for structured document format that contains
 * foor loops nodes, echo nodes and text nodes. It builds structural tree by the
 * help of SmartScriptParser which generates tokens for each node.After
 * constructing a node parse puts it in the right place of structural tree.
 * 
 * @author Filip Hustić
 *
 */
public class SmartScriptParser {

	/**
	 * Lexer which generates tokens by certain set of rules.
	 */
	private SmartScriptLexer lexer;
	/*
	 * Stack that helps for constructing structural tree.
	 */
	private ObjectStack<Node> stack;

	/**
	 * Constructor that takes in body of the document. It alse initialized stack and
	 * delegates parsing to methid {@link #parse()}
	 * 
	 * @param body
	 */
	public SmartScriptParser(String body) {
		lexer = new SmartScriptLexer(body);
		stack = new ObjectStack<Node>();
		stack.push(new DocumentNode());
		parse();
	}

	/**
	 * Method which creates structural tree of nodes. If lexer is in the state of
	 * OUTSIDE_OF_TAGS generated tokens are stored in String text. Once the token
	 * START_OF_TAG is generated text is stored in form of TextNode as the child of
	 * the Node that is on the top of stack and further operations is delegated to
	 * method {@link #parseInTags()}. After return form parseInTags() lexer
	 * continues to work in OUTSIDE_OF_TAGS_STATE state. Building is done until EOF
	 * token is generated. If after EOF stack contains more than one element there
	 * was some for loop without ending and exception is thrown.
	 * 
	 * @throws SmartScriptParserException if after EOF token stack contains more
	 *                                    than one element. It indicates that not
	 *                                    all for loops were closed.
	 */
	private void parse() {

		String text = "";
		while (true) {

			SmartScriptToken token = readNextToken();
			if (token.getType() == SmartScriptTokenType.EOF) {
				if (!text.isEmpty())
					((Node) stack.peak()).addChildNode(new TextNode(text.trim()));

				break;

			} else if (token.getType() == SmartScriptTokenType.START_OF_TAG) {
				if (!text.isEmpty())
					((Node) stack.peak()).addChildNode(new TextNode(text));

				parseInTags();
				text = "";// new iteration of text part
			} else {
				text +=token.getValue().asText() + " ";
			}
		}

		if (stack.size() != 1) {
			throw new SmartScriptParserException("All non empty tags were not closed");
		}
	}

	/**
	 * Method delegates creation of Nodes that consist of tags(forLoopNode and
	 * EchoNode) to methods {@link #handleEcho()} if given tag is = ,
	 * {@link #handleForLoop()} if given tag is for(for is case-insensitive) or to
	 * the {@link #handleEnd()} of given tag is end(alse case-insesnitive). Also it
	 * changes state of lexer to the TAG_STATE at the beggining of method and back
	 * to the OUTSIDE_OF_TAGS_STATE at the end of method.
	 * 
	 * @throws SmartScriptParserException if name of tag is not =,for or end.
	 */
	private void parseInTags() {
		SmartScriptToken token = lexer.getToken();
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		if (token.getValue().asText().equals("=")) {
			handleEcho();
		} else if (token.getValue().asText().toLowerCase().equals("for")) {
			handleForLoop();
		} else if (token.getValue().asText().toLowerCase().equals("end")) {
			handleEnd();
		} else {
			throw new SmartScriptParserException("Name of tag must be either for or =");
		}

		lexer.setState(SmartScriptLexerState.OUTSIDE_OF_TAGS_STATE);
	}

	/**
	 * Method handles syntax in echo tag. It reads every element of inside the tag
	 * and stores them into collection. That collection is latter turned into array
	 * via {@link #getEchoNodeArray(ArrayIndexedCollection)} and passed as parametar
	 * of new EchoNode.New EchoNode is then added as child to the elemnt at the top
	 * ot stack.
	 */
	private void handleEcho() {
		ArrayIndexedCollection<Element> echoElements = new ArrayIndexedCollection<Element>();
		SmartScriptToken token;

		while ((token = readNextToken()).getType() != SmartScriptTokenType.END_OF_TAG) {
			echoElements.add(token.getValue());
		}
		((Node) stack.peak()).addChildNode(new EchoNode(getEchoNodeArray(echoElements)));
	}

	/**
	 * Method handles syntax in for loop tag. For loop consists of variable after
	 * which comes 2 or 3(if not exception is thrown) elements that are type of
	 * variable,number or string. To check if they are of that type
	 * {@link #testIsVariableNumberOrString(SmartScriptToken)} is called, if given
	 * token is not of one of that type exception is thrown. Also If first element
	 * is not variable exception is thrown.
	 * 
	 * @throws SmartScriptParserException if first element of loop is not variable,
	 *                                    or if number of elements is not 3 or 4, or
	 *                                    if elements are of wrong type.
	 */
	private void handleForLoop() {

		SmartScriptToken token = readNextToken();
		if (token.getType() != SmartScriptTokenType.VARIABLE) {
			throw new SmartScriptParserException("First element in for tag must be variable.");
		} else {
			ElementVariable variable = (ElementVariable) token.getValue();

			ArrayIndexedCollection<SmartScriptToken> forElements = new ArrayIndexedCollection<SmartScriptToken>();
			while ((token = readNextToken()).getType() != SmartScriptTokenType.END_OF_TAG) {
				forElements.add(token); // add everything after variable
			}

			if (forElements.size() == 2) {
				Element start = testIsVariableNumberOrString((SmartScriptToken) forElements.get(0));
				Element end = testIsVariableNumberOrString((SmartScriptToken) forElements.get(1));
				stack.push(new ForLoopNode(variable, start, end, null));
			} else if (forElements.size() == 3) {
				Element start = testIsVariableNumberOrString((SmartScriptToken) forElements.get(0));
				Element end = testIsVariableNumberOrString((SmartScriptToken) forElements.get(1));
				Element step = testIsVariableNumberOrString((SmartScriptToken) forElements.get(2));
				stack.push(new ForLoopNode(variable, start, end, step));
			} else {

				throw new SmartScriptParserException("Number of elements of for loop is not right.");
			}
		}
	}

	/**
	 * Method handles endTag. It cheks if end tag is closed. If it is then checks if
	 * there is more than one Node on the stack. If there is then it pops top node
	 * and adds it as child to the new top of the stack.
	 * 
	 * @throws SmartScriptParserException if there is 1 or 0 elements on the stack.
	 */
	private void handleEnd() {
		SmartScriptToken token = readNextToken();

		if (token.getType() != SmartScriptTokenType.END_OF_TAG) {
			throw new SmartScriptParserException("You did not close END tag, $} is missing");
		} else if (stack.isEmpty() || stack.size() == 1) {
			throw new SmartScriptParserException("Stack is empty.");
		} else {
			ForLoopNode forNode = (ForLoopNode) stack.pop();
			((Node) stack.peak()).addChildNode(forNode);
		}
	}

	/**
	 * Helper method that checks if given token is of type variable, number or
	 * string.
	 * 
	 * @param token that is check.
	 * @return Element value of token.
	 * @throws SmartScriptParserException if token is not of type variable,number or
	 *                                    string.
	 */
	private Element testIsVariableNumberOrString(SmartScriptToken token) {
		if (token.getType() != SmartScriptTokenType.VARIABLE && token.getType() != SmartScriptTokenType.NUMBER
				&& token.getType() != SmartScriptTokenType.STRING) {
			throw new SmartScriptParserException("Some element of for loop is not of the right type.");
		} else {
			return token.getValue();
		}

	}

	/**
	 * Helper method which tries to generate new token. If lexer throws exception it
	 * is catched and rethrown.
	 * 
	 * @return next Token.
	 * @throws SmartScriptParserException if there was exception in lexer while
	 *                                    generating new token.
	 */
	private SmartScriptToken readNextToken() {
		try {
			return lexer.nextToken();
		} catch (SmartScriptParserException ex) {
			throw new SmartScriptParserException(ex.getMessage());
		}
	}

	/**
	 * Returns Node at the top of the stack without popping it.
	 * 
	 * @return Noode at the top of the stack withoud popping it.
	 */
	public Node getPeekOfStack() {
		return (Node) stack.peak();
	}

	/**
	 * Method constructs array of Elements passed through collection.
	 * 
	 * @param collection that holds elements.
	 * @return array of Elements.
	 */
	private Element[] getEchoNodeArray(ArrayIndexedCollection<Element> collection) {
		Element[] array = new Element[collection.size()];
		ElementsGetter<Element> getter = collection.createElementsGetter();

		int i = 0;
		while (getter.hasNextElement()) {
			array[i++] = (Element) getter.getNextElement();
		}

		return array;
	}

	/**
	 * Get DocumentNode with its children in place after parsing.
	 * 
	 * @return documentNode.
	 */
	public DocumentNode getDocumentNode() {
		return (DocumentNode) stack.pop();
	}

}
