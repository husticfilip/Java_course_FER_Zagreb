package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class represents map of stacks.
 * <p>
 * Every single stack is stack of specific key and all of on values on specific
 * stack have the same map key.
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class ObjectMultistack {

	/**
	 * Class represents one in stack map.
	 * 
	 * @author Filip Hustić
	 *
	 */
	private static class MultistackEntry {
		/**
		 * Wrapper of value which entry holds.
		 */
		public ValueWrapper valueWrapper;
		/**
		 * Pointer to the next entry on the stack.
		 */
		public MultistackEntry next;

		public MultistackEntry(ValueWrapper valueWrapper) {
			super();
			this.valueWrapper = valueWrapper;
		}

	}

	/**
	 * Map which holds stacks. Every stack is identified with specific key.
	 */
	private Map<String, MultistackEntry> stackMap;

	/**
	 * Default constructor in which stackMap is initialized.
	 */
	public ObjectMultistack() {
		stackMap = new HashMap<String, MultistackEntry>();
	}

	/**
	 * Method constructs new entry which holds given valueWrapper and pushes it on
	 * stack which is identified with given key.
	 * <p>
	 * If stack with given key does not exist it is initialized and new entry is
	 * pushed on it.
	 * <p>
	 * 
	 * @param key          of stack on which entry will be pushed.
	 * @param valueWrapper value which new entry will hold.
	 */
	public void push(String key, ValueWrapper valueWrapper) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(valueWrapper);

		MultistackEntry newEntry = new MultistackEntry(valueWrapper);
		MultistackEntry headEntry = stackMap.get(key);
		// there is no entry with given key
		if (headEntry != null) {
			newEntry.next = headEntry;
		}

		stackMap.put(key, newEntry);
	}

	/**
	 * Method pops entry from stack specified with given keyName and value of that
	 * entry is returned.
	 * <p>
	 * If pop entry was last on its stack, stack is removed from map.
	 * <p>
	 * 
	 * @param keyName key of stack from which value will be pop.
	 * @return value which pop entry holds.
	 * @throws EmptyStackException if stack with given keyName does not exist or is
	 *                             empty.
	 */
	public ValueWrapper pop(String keyName) {
		MultistackEntry headEntry = stackMap.get(keyName);
		if (headEntry == null) {
			throw new EmptyStackException();
		} else {
			if (headEntry.next == null) {
				stackMap.remove(keyName);
			} else {
				stackMap.put(keyName, headEntry.next);
			}
			return headEntry.valueWrapper;
		}

	}

	/**
	 * Method returns value of peek entry of stack with identified with given key.
	 * 
	 * @param keyName key of stack from which peek entry's value will be returned.
	 * @return value of peek entry which belongs to stack with given key.
	 * @throws EmptyStackException if there is no entry with given key.
	 */
	public ValueWrapper peek(String keyName) {
		MultistackEntry headEntry = stackMap.get(keyName);
		if (headEntry == null) {
			throw new EmptyStackException();
		} else {
			return headEntry.valueWrapper;
		}

	}

	/**
	 * Method checks if stack identified with given keyName exists or if it is
	 * empty.
	 * 
	 * @param keyName key of stack.
	 * @return true is stack does not exist or is empty. True otherwise.
	 */
	public boolean isEmpty(String keyName) {
		return stackMap.get(keyName) == null;
	}

}
