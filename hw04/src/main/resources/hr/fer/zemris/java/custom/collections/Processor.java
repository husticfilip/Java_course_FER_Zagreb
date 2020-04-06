package hr.fer.zemris.java.custom.collections;

/**
 * Processor represents conceptual contract between clients which will have
 * objects to be processed and actual concrete Processor.
 * 
 * @author Filip Hustić
 *
 */
public interface Processor {

	/**
	 * Processes given object.
	 * 
	 * @param value is object which client wants to be processed.
	 */
	public void process(Object value);

}
