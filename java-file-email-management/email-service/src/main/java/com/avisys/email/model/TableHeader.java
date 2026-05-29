package com.avisys.email.model;

public class TableHeader {

	String header;
	String value;
	int width;
	String type;
	String search_key;

	public TableHeader() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TableHeader(String header, String value, int width,String type,String search_key) {
		super();
		this.header = header;
		this.value = value;
		this.width = width;
		this.type=type;
		this.search_key=search_key;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSearch_key() {
		return search_key;
	}

	public void setSearch_key(String search_key) {
		this.search_key = search_key;
	}

	@Override
	public String toString() {
		return "TableHeader [header=" + header + ", value=" + value + ", width=" + width + ", type=" + type
				+ ", search_key=" + search_key + "]";
	}

}
