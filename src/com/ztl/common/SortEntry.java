package com.ztl.common;

import java.util.Map.Entry;

public class SortEntry<K, V> implements Entry<K, V> {
	
	private K key;
	
	private V value;
	
	public K getName() {
		return getKey();
	}

	public K getKey() {
		return key;
	}
	
	public V getOrder() {
		return getValue();
	}

	public V getValue() {
		return value;
	}
	
	public K setKey(K key) {
		K oldKey = this.key;
		this.key = key;
		return oldKey;
	}

	public V setValue(V value) {
		V oldValue = this.value;
		this.value = value;
		return oldValue;
	}
}
