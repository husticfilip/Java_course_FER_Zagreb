package hr.fer.zemris.java.custom.scripting.nodes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NodeTest {

	
	public void testAddingSizeAndGet() {
		Node node = new Node();
		
		Node node2=new Node();
		node.addChildNode(node2);
		assertEquals(1, node.numberOfChildren());
		node.addChildNode(new Node());
		assertEquals(2, node.numberOfChildren());
	
		assertEquals(node2, node.getChild(0));
		assertThrows(IndexOutOfBoundsException.class, ()->{
			node.getChild(-1);
		});
		assertThrows(IndexOutOfBoundsException.class, ()->{
			node.getChild(2);
		});
	}
	
}
