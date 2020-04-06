package hr.fer.zemris.java.custom.collections;


/**
 * <p>
 * Class Dictionary represents map of key value pars.
 * <p>
 * Keys are unique values so one key can hold just one value. But one value can
 * be assigned to many different keys.
 * 
 * @author Filip Hustić
 *
 */
public class Dictionary<K, V> {

	/**
	 * Structure which represents one key-value input in dictionary.
	 */
	static class DictionaryElement<K, V> {
		/**
		 * Key of paired value.
		 */
		private K key;
		/**
		 * Value of paired value.
		 */
		private V value;
		
		/**
		 * Constructor which takes in pair of key and value.
		 * @param key key of the pair.
		 * @param value value of the pair.
		 */
		public DictionaryElement(K key, V value) {
			this.key=key;
			this.value=value;
		}

		
		/**
		 *
		 * @return key of the pair.
		 */
		public K getKey() {
			return key;
		}


		/**
		 * 
		 * @return value of the pair.
		 */
		public V getValue() {
			return value;
		}



		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		@SuppressWarnings({ "unchecked" })
		@Override
		public boolean equals(Object obj) {
			if (this.key == obj)
				return true;
			if (obj == null)
				return false;
			if (key.getClass() != obj.getClass())
				return false;
			K other = (K) obj;
			if (key == null) {
				if (other != null)
					return false;
			} else if (!key.equals(other))
				return false;
			return true;
		}
		
		
	}

	/**
	 * Collection which will store key-value pairs.
	 */
	ArrayIndexedCollection<DictionaryElement<K, V>> collection;

	/**
	 * Default constructor in which collection of key-values pairs is initialized.
	 */
	public Dictionary() {
		collection = new ArrayIndexedCollection<Dictionary.DictionaryElement<K, V>>();
	}

	/**
	 * Method checks if Dictionary is empty.
	 * 
	 * @return true if collection contains at least one key-value pair, false
	 *         otherwise.
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 * Method returns number of key-value pairs stored in dictionary.
	 * @return number of key-value pairs stored in dictionary.
	 */
	public int size() {
		return collection.size();
 	}
	
	/**
	 * Method removes all key-value pairs from dictionary.
	 */
	public void clear() {
		collection.clear();
	}
	
	/**
	 * Method puts new key-value pair into dictionary. If 
	 * there is already pair with given key, that pair is 
	 * removed and replaced with new key-value given in this
	 * method.
	 * @param key key of the pair.
	 * @param value value of the pair.
	 * @throws IllegalArgumentException if given key is null.
	 */
	public void put(K key, V value) {
		if(key == null)
			throw new IllegalArgumentException("Given key is null.");
		
		if(collection.contains(key)) {
			collection.remove(collection.indexOf(key));
		}
		
		collection.add(new DictionaryElement<K,V>(key,value));
	}
	
	public V get(Object key) {
		int index = collection.indexOf(key);
		
		if(index==-1) 
			return null;
		else
			return collection.get(index).getValue();
	}

}
