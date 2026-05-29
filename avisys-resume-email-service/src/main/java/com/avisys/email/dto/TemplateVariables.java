package com.avisys.email.dto;

public class TemplateVariables {

	private String key;
	private String value;

	public TemplateVariables(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public TemplateVariables() {
		super();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "TemplateVariables [key=" + key + ", value=" + value + "]";
	}

}
