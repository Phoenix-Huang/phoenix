package com.morningstar.grocerystore.util;



/**
 * @author phuang2
 * A sample data structure to store pairs
 * Then we can put it to list and the keys are not unique 
 * 
 */
 
public class KeyValuePair<K, V> {
	private K key;
	private V value;

	public KeyValuePair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V v) {
		this.value = v;
	}
}
