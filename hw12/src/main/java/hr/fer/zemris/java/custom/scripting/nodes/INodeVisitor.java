package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Visitor pattern for applying actions on nodes.
 * @author Filip Hustić
 *
 */
public interface INodeVisitor {

	/**
	 * Apply action on TextNode
	 * @param node
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Apply action on ForLoopNode
	 * @param node
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Apply action on EchoNode
	 * @param node
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Apply action on DocumentNode
	 * @param node
	 */
	public void visitDocumentNode(DocumentNode node);
}
