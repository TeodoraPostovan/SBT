package edu.dubenco.alina.sbt;

import org.json.JSONObject;

public class CustomItem {
	private boolean selected;
	private JSONObject json;
	private String reference;
	private String valueType;
	private String solution;
	private String right_type;
	private String description;
	private String valueData;
	private String type;
	private String seeAlso;
	private String info;
	private String regItem;
	private String regOption;
	private String regKey;
	private String lockoutPolicy;
	
	public CustomItem(JSONObject json) {
		setJson(json);
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public JSONObject getJson() {
		return json;
	}
	
	public void setJson(JSONObject json) {
		this.json = json;
		if(json.has("reference")) {
			this.setReference(json.getString("reference"));
		}
		if(json.has("value_type")) {
			this.setValueType(json.getString("value_type"));;
		}
		if(json.has("solution")) {
			this.setSolution(json.getString("solution"));
		}
		if(json.has("right_type")) {
			this.setRight_type(json.getString("right_type"));
		}
		if(json.has("description")) {
			this.setDescription(json.getString("description"));
		}
		if(json.has("value_data")) {
			this.setValueData(json.getString("value_data"));
		}
		if(json.has("type")) {
			this.setType(json.getString("type"));
		}
		if(json.has("see_also")) {
			this.setSeeAlso(json.getString("see_also"));
		}
		if(json.has("info")) {
			this.setInfo(json.getString("info"));
		}
		if(json.has("reg_item")) {
			this.setRegItem(json.getString("reg_item"));
		}
		if(json.has("reg_option")) {
			this.setRegOption(json.getString("reg_option"));
		}
		if(json.has("reg_key")) {
			this.setRegKey(json.getString("reg_key"));
		}
		if(json.has("lockout_policy")) {
			this.setLockoutPolicy(json.getString("lockout_policy"));
		}
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public String getValueType() {
		return valueType;
	}
	
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	public String getSolution() {
		return solution;
	}
	
	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	public String getRight_type() {
		return right_type;
	}
	
	public void setRight_type(String right_type) {
		this.right_type = right_type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getValueData() {
		return valueData;
	}
	
	public void setValueData(String valueData) {
		this.valueData = valueData;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSeeAlso() {
		return seeAlso;
	}
	
	public void setSeeAlso(String seeAlso) {
		this.seeAlso = seeAlso;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getRegItem() {
		return regItem;
	}
	
	public void setRegItem(String regItem) {
		this.regItem = regItem;
	}
	
	public String getRegOption() {
		return regOption;
	}
	
	public void setRegOption(String regOption) {
		this.regOption = regOption;
	}
	
	public String getRegKey() {
		return regKey;
	}
	
	public void setRegKey(String regKey) {
		this.regKey = regKey;
	}
	
	public String getLockoutPolicy() {
		return lockoutPolicy;
	}
	
	public void setLockoutPolicy(String lockoutPolicy) {
		this.lockoutPolicy = lockoutPolicy;
	}
}
