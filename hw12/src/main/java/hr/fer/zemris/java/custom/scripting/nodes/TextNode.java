package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing a piece of textual data.
 * 
 * @author Filip Hustić
 *
 */
public class TextNode extends Node {

	/**
	 * Text of TextNode.
	 */
	private String text;

	/**
	 * Constructor which takes in text of @this TextNode.
	 * 
	 * @param text text of @this TextNode
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}

	/**
	 * 
	 * @return text.
	 */
	public String getText() {
		return text;
	}
	
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
