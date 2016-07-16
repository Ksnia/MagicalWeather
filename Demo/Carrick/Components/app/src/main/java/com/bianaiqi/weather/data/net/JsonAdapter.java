package com.bianaiqi.weather.data.net;


import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.os.Parcelable;

import com.bianaiqi.weather.WeatherUtils;
import com.google.gson.Gson;

public abstract class JsonAdapter implements Parcelable {

	protected Gson gson = WeatherUtils.getGson();
	protected JSONObject internalObject;

	public static final String JSON_NULL_STRING_FLAG = "<Null>";
	
	public JsonAdapter() {
		// TODO Auto-generated constructor stub
		internalObject = new JSONObject();
	}
	public JsonAdapter(JSONObject internalObject) {
		// TODO Auto-generated constructor stub
		this.internalObject = internalObject;
	}
	public JSONObject accumulate(String name, Object value)
			throws JSONException {
		return internalObject.accumulate(name, value);
	}

	public boolean equals(Object o) {
		return internalObject.equals(o);
	}

	public Object get(String name) throws JSONException {
		return internalObject.get(name);
	}

	public boolean getBoolean(String name) throws JSONException {
		return internalObject.getBoolean(name);
	}

	public double getDouble(String name) throws JSONException {
		return internalObject.getDouble(name);
	}

	public int getInt(String name) throws JSONException {
		return internalObject.getInt(name);
	}

	public JSONArray getJSONArray(String name) throws JSONException {
		return internalObject.getJSONArray(name);
	}

	public JSONObject getJSONObject(String name) throws JSONException {
		return internalObject.getJSONObject(name);
	}

	public long getLong(String name) throws JSONException {
		return internalObject.getLong(name);
	}

	public String getString(String name) throws JSONException {
		return internalObject.getString(name);
	}

	public boolean has(String name) {
		return internalObject.has(name);
	}

	public int hashCode() {
		return internalObject.hashCode();
	}

	public boolean isNull(String name) {
		return internalObject.isNull(name);
	}

	public Iterator<?> keys() {
		return internalObject.keys();
	}

	public int length() {
		return internalObject.length();
	}

	public JSONArray names() {
		return internalObject.names();
	}

	public Object opt(String name) {
		return internalObject.opt(name);
	}

	public boolean optBoolean(String name, boolean fallback) {
		return internalObject.optBoolean(name, fallback);
	}

	public boolean optBoolean(String name) {
		return internalObject.optBoolean(name);
	}

	public double optDouble(String name, double fallback) {
		return internalObject.optDouble(name, fallback);
	}

	public double optDouble(String name) {
		return internalObject.optDouble(name);
	}

	public int optInt(String name, int fallback) {
		return internalObject.optInt(name, fallback);
	}

	public int optInt(String name) {
		return internalObject.optInt(name);
	}

	public JSONArray optJSONArray(String name) {
		return internalObject.optJSONArray(name);
	}

	public JSONObject optJSONObject(String name) {
		return internalObject.optJSONObject(name);
	}

	public long optLong(String name, long fallback) {
		return internalObject.optLong(name, fallback);
	}

	public long optLong(String name) {
		return internalObject.optLong(name);
	}

	public String optString(String name, String fallback) {
		return internalObject.optString(name, fallback);
	}

	public String optString(String name) {
		return internalObject.optString(name);
	}

	public JSONObject put(String name, boolean value) throws JSONException {
		return internalObject.put(name, value);
	}

	public JSONObject put(String name, double value) throws JSONException {
		return internalObject.put(name, value);
	}

	public JSONObject put(String name, int value) throws JSONException {
		return internalObject.put(name, value);
	}

	public JSONObject put(String name, long value) throws JSONException {
		return internalObject.put(name, value);
	}

	public void put(String name, Object value) {
		try {
			internalObject.put(name, value);
		} catch (Exception ex) {
			ex.printStackTrace();
			//AppstoreException.handlException(ex);
		}
	}

	public JSONObject putOpt(String name, Object value) throws JSONException {
		return internalObject.putOpt(name, value);
	}

	public Object remove(String name) {
		return internalObject.remove(name);
	}

	public JSONArray toJSONArray(JSONArray names) throws JSONException {
		return internalObject.toJSONArray(names);
	}

	public String toString() {
		return internalObject.toString();
	}

	public String toString(int indentSpaces) throws JSONException {
		return internalObject.toString(indentSpaces);
	}


	public void readFromBundle(Bundle b) {
		try {
			JSONObject subObject = null; 
			if (b != null && internalObject != null) {
				for (String key : b.keySet()) {
					
					Object oriValue = b.get(key);
					
					try { subObject = new JSONObject(oriValue.toString()); } catch(Throwable t) {subObject = null;}
					
					if (subObject != null) {
						internalObject.putOpt(key, subObject);
					} else {
						internalObject.putOpt(key, oriValue);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//AppstoreException.handlException(e);
		}	
	}
	
	public void clear() {
		if (internalObject != null) {
			Iterator i = internalObject.keys();
			if (i != null) {
				while (i.hasNext()) {
					internalObject.remove(i.next().toString());
				}
			}
		}
	}
	
}
