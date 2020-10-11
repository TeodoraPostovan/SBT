package edu.dubenco.alina.sbt;

import org.json.JSONObject;

public class BackupItem {
	public enum Type {Registry, UserRights}
	
	public BackupItem(Type type, String key, String item, String value, String valueType, String newKey) {
		setType(type);
		setKey(key);
		setNewKey(newKey);
		setItem(item);
		setValue(value);
		setValueType(valueType);
	}

	public BackupItem(JSONObject json) {
		setType(Type.valueOf(get(json, "type")));
		setKey(get(json, "key"));
		setNewKey(get(json, "newKey"));
		setItem(get(json, "item"));
		setValue(get(json, "value"));
		setValueType(get(json, "valueType"));
	}
	
	private Type type;
	private String key;
	private String newKey;
	private String item;
	private String value;
	private String valueType;
	
	private static String get(JSONObject json, String key) {
		if(json.has(key)) {
			return json.getString(key);
		} else return null;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getItem() {
		return item;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValueType() {
		return valueType;
	}
	
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getNewKey() {
		return newKey;
	}

	public void setNewKey(String newKey) {
		this.newKey = newKey;
	}
	
}
