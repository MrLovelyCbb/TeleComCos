package com.telecom.cos.task;

import java.util.HashMap;

import com.telecom.cos.http.TelecomException;

public class TelecomParams {

	private HashMap<String, Object> params = null;

	public TelecomParams() {
		params = new HashMap<String, Object>();
	}

	public TelecomParams(String key, Object value) {
		this();
		put(key, value);
	}

	public void put(String key, Object value) {
		params.put(key, value);
	}

	public Object get(String key) {
		return params.get(key);
	}

	public boolean getBoolean(String key) throws TelecomException {
		Object object = get(key);
		if (object.equals(Boolean.FALSE)
				|| (object instanceof String && ((String) object)
						.equalsIgnoreCase("false"))) {
			return false;
		} else if (object.equals(Boolean.TRUE)
				|| (object instanceof String && ((String) object)
						.equalsIgnoreCase("true"))) {
			return true;
		}
		throw new TelecomException(key + " is not a Boolean.");
	}

	public double getDouble(String key) throws TelecomException {
		Object object = get(key);
		try {
			return object instanceof Number ? ((Number) object).doubleValue()
					: Double.parseDouble((String) object);
		} catch (Exception e) {
			throw new TelecomException(key + " is not a number.");
		}
	}

	public int getInt(String key) throws TelecomException {
		Object object = get(key);
		try {
			return object instanceof Number ? ((Number) object).intValue()
					: Integer.parseInt((String) object);
		} catch (Exception e) {
			throw new TelecomException(key + " is not an int.");
		}
	}

	public String getString(String key) throws TelecomException {
		Object object = get(key);
		return object == null ? null : object.toString();
	}

	public boolean has(String key) {
		return this.params.containsKey(key);
	}
}
