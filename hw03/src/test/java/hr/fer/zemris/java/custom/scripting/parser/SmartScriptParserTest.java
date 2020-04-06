package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.hw03.script.tester.Loader;
import hr.fer.zemris.java.hw03.script.tester.SmartScriptTester;

public class SmartScriptParserTest {

		public void testStructTheSame(Node node1, Node node2) {
			if (node1 instanceof ForLoopNode) {
				testForLoopElements((ForLoopNode) node1, (ForLoopNode) node2);
			} else if (node1 instanceof EchoNode)
				testEchoElements((EchoNode) node1, (EchoNode) node2);
			else if (node1 instanceof TextNode)
				testTextElements((TextNode) node1, (TextNode) node2);

			if (node1.numberOfChildren() != 0) {
				for (int i = 0; i < node1.numberOfChildren(); ++i) {
					testStructTheSame(node1.getChild(i), node2.getChild(i));
				}
			}
		}

		public void testForLoopElements(ForLoopNode node1, ForLoopNode node2) {
			assertTrue(node2 instanceof ForLoopNode);
			assertEquals(node1.getVariable().asText(), node2.getVariable().asText());
			assertEquals(node1.getStartExpression().asText(), node2.getStartExpression().asText());
			assertEquals(node1.getEndExpression().asText(), node2.getEndExpression().asText());

			if (node1.getStepExpression() != null) {
				assertEquals(node1.getStepExpression().asText(), node2.getStepExpression().asText());
			} else {
				assertNull(node2.getStepExpression());
			}
		}

		public void testEchoElements(EchoNode node1, EchoNode node2) {
			assertTrue(node2 instanceof EchoNode);
			Element[] node1elem = node1.getElements();

			for (int i = 0; i < node1elem.length; ++i) {
				assertEquals(node1elem[i].asText(), node1elem[i].asText());
			}
		}

		public void testTextElements(TextNode node1, TextNode node2) {
			assertTrue(node2 instanceof TextNode);
			assertEquals(node1.getText(), node2.getText());
		}

		@SuppressWarnings({ "static-access"})
		@Test
		public void test() {
			Loader loader = new Loader();
			SmartScriptTester tester = new SmartScriptTester();

			for (int i = 1; i < 12; ++i) {
				System.out.println("Iteration " + i);
				String docBody = loader.loader("doc" + i + ".txt");
				SmartScriptParser parser = new SmartScriptParser(docBody);
				DocumentNode document = parser.getDocumentNode();
				String originalDocumentBody = tester.createOriginalDocumentBody(document);
				System.out.println(originalDocumentBody);
				System.out.printf("%n%n");
				SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
				DocumentNode document2 = parser2.getDocumentNode();
				testStructTheSame(document, document2);
			}
		}
	
	
	
	@Test
	public void testExhoNode() {
		String line = "abcde {$= hej sta ima echo$}  \\{$=i$}.";

		SmartScriptParser parser = new SmartScriptParser(line);
		Node peak = parser.getPeekOfStack();
		assertTrue(peak instanceof DocumentNode);

		Node child1 = peak.getChild(0);
		Node child2 = peak.getChild(1);
		Node child3 = peak.getChild(2);
		assertTrue(child1 instanceof TextNode);
		assertTrue(child2 instanceof EchoNode);
		assertTrue(child3 instanceof TextNode);
	}

	@Test
	public void testExhoNode2() {
		String line = "{$= i i * @sin \"0.000\" @decfmt $} ovo je text";

		SmartScriptParser parser = new SmartScriptParser(line);
		Node peak = parser.getPeekOfStack();
		assertTrue(peak instanceof DocumentNode);

		Node child1 = peak.getChild(0);
		Node child2 = peak.getChild(1);
		assertTrue(child1 instanceof EchoNode);
		assertTrue(child2 instanceof TextNode);
	}

	@Test
	public void testExhoNode3() {
		String line = "{$= ii*@sin-12a_12\"0.000\"@decfmt $} ovo je text";

		SmartScriptParser parser = new SmartScriptParser(line);
		Node peak = parser.getPeekOfStack();
		assertTrue(peak instanceof DocumentNode);

		Node child1 = peak.getChild(0);
		Node child2 = peak.getChild(1);
		assertTrue(child1 instanceof EchoNode);
		assertTrue(child2 instanceof TextNode);
	}

	@Test
	public void testEchoNodeNotClosed() {
		String line = "{$= hej sta ima echo";

		assertThrows(SmartScriptParserException.class, () -> {
		 new SmartScriptParser(line);
		});
	}

	@Test
	public void testEchoNodeVrongVariableNames() {
		String line = "{$= abc $ $} ";

		assertThrows(SmartScriptParserException.class, () -> {
		new SmartScriptParser(line);
		});
	}

	@Test
	public void testEchoNodeVrongVariableNames2() {
		String line = "{$= abc _a $} ";

		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(line);
		});
	}

	@Test
	public void testForLoop() {
		String line = "E moj boško {$   FoR   i 12 20  $} eh {$=i var$} eh {$END$}";

		SmartScriptParser parser = new SmartScriptParser(line);

		Node pater = parser.getPeekOfStack();
		assertTrue(pater instanceof DocumentNode);

		Node child1 = pater.getChild(0);
		Node child2 = pater.getChild(1);
		assertTrue(child1 instanceof TextNode);
		assertTrue(child2 instanceof ForLoopNode);

		Node grandChild1 = child2.getChild(0);
		Node grandChild2 = child2.getChild(1);
		Node grandChild3 = child2.getChild(2);
		assertTrue(grandChild1 instanceof TextNode);
		assertTrue(grandChild2 instanceof EchoNode);
		assertTrue(grandChild3 instanceof TextNode);

	}

	@Test
	public void testForLoop2() {
		String line = "E moj boško {$   FoR   i12 20ak  $} eh {$=i var$} eh {$END$}";

		SmartScriptParser parser = new SmartScriptParser(line);

		Node pater = parser.getPeekOfStack();
		assertTrue(pater instanceof DocumentNode);

		Node child1 = pater.getChild(0);
		Node child2 = pater.getChild(1);
		assertTrue(child1 instanceof TextNode);
		assertTrue(child2 instanceof ForLoopNode);

		Node grandChild1 = child2.getChild(0);
		Node grandChild2 = child2.getChild(1);
		Node grandChild3 = child2.getChild(2);
		assertTrue(grandChild1 instanceof TextNode);
		assertTrue(grandChild2 instanceof EchoNode);
		assertTrue(grandChild3 instanceof TextNode);
	}

	@Test
	public void testForLoopWithoutClosing() {
		String line = "E moj boško {$   FoR   i12 20ak ";

		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(line);
		});
	}

	@Test
	public void testForLoopWithoutClosing2() {
		String line = "{$for  1 2 3} {$= i 1 2 3 echo} \r\n" + "	text ";

		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(line);
		});
	}

	@Test
	public void testForLoopInsideForLoop() {
		String line = "{$for i {$for$} 2 $}";

		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(line);
		});
	}

	@Test
	public void testEndings() {
		String line = "This is sample text.\r\n" + "{$ FOR i 1 10 1 $}\r\n"
				+ " This is {$= i $}-th time this message is generated.\r\n" + "{$END$}";

		SmartScriptParser parser = new SmartScriptParser(line);

		Node pater = parser.getPeekOfStack();
		assertTrue(pater instanceof DocumentNode);

		Node child1 = pater.getChild(0);
		Node child2 = pater.getChild(1);
		assertTrue(child1 instanceof TextNode);
		assertTrue(child2 instanceof ForLoopNode);

		Node grandchild = child2.getChild(0);
		Node grandchild2 = child2.getChild(1);
		Node grandchild3 = child2.getChild(2);
		assertTrue(grandchild instanceof TextNode);
		assertTrue(grandchild2 instanceof EchoNode);
		assertTrue(grandchild3 instanceof TextNode);
	}

	@Test
	public void testInvalidForLoops() {
		invalidForLoops("{$ FOR 3 1 10 1 $}{$END$}");
		invalidForLoops("{$ FOR * \"1\" -10 \"1\" $}{$END$}");
		invalidForLoops("{$ FOR * \"1\" -10 \"1\" $}{$END$}");
		invalidForLoops("{$ FOR * \"1\" -10 \"1\" $}{$END$}");
		invalidForLoops("{$ FOR year $}{$END$}");
		invalidForLoops("{$ FOR year 1 10 1 3 $}{$END$}");
		invalidForLoops("{$ FOR year @sin 10 3 $}{$END$}");
		invalidForLoops("{$ while year 1 10 1 3 $}{$END$}");
		invalidForLoops("{$ FOR var 10 $} tekst {$END$} {$END$}");
		invalidForLoops("{$ FOR var 1 10 10 20$} tekst {$END$} {$END$}");
	}

	@Test
	public void testInvalidEnds() {

		invalidForLoops("{$END$}");
		invalidForLoops("{$END$} {$For i 1 2 3$}");
		invalidForLoops("{$ FOR var 1 10 $} tekst {$END$} {$END$}");

	}

	public void invalidForLoops(String line) {
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(line);
		});
	}

	

}
