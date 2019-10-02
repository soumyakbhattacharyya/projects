/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.chain.observer;

import com.cognizant.formit.main.AppConstants;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

/**
 * this is a rather simple implementation of what forwarding collection of google's guava library implements TODO : refactor to use standard
 * ForwardingCollection approach
 *
 * @author cognizant
 */
public final class ObservableMap extends Observable implements Map<String, String> {

	/**
	 * static instance factory
	 *
	 * @return
	 */
	public static final ObservableMap newInstance() {
		return new ObservableMap();
	}
	final Map<String, String> map = new HashMap<String, String>();

	public Map<String, String> getMap() {
		return map;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object o) {
		return map.containsKey(o);
	}

	@Override
	public boolean containsValue(Object o) {
		return map.containsValue(o);
	}

	@Override
	public String get(Object o) {
		return map.get(o);
	}

	@Override
	public String put(String k, String v) {
		String returnableString = map.put(k, v);
		setChanged();
		notifyObservers(k + AppConstants.HASH + v);
		return returnableString;
	}

	@Override
	public String remove(Object o) {
		return map.remove(o);
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> argumentMap) {
		map.putAll(argumentMap);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<String> values() {
		return map.values();
	}

	@Override
	public Set<Entry<String, String>> entrySet() {
		return map.entrySet();
	}
}