package com.avisys.cv.dto;

public class ColumnMetadata {
	private String name;
	private String mappedBy;
	private String sortBy;
	private ColumnType type;
	private int width;

	public ColumnMetadata(String name, String mappedBy, String sortBy, ColumnType type, int width) {
		this.name = name;

		this.mappedBy = mappedBy;
		this.sortBy = sortBy;
		this.type = type;
		this.width = width;
	}

	public String getName() {
		return name;
	}

	public String getMappedBy() {
		return mappedBy;
	}

	public String getSortBy() {
		return sortBy;
	}

	public ColumnType getType() {
		return type;
	}

	public int getWidth() {
		return width;
	}
}
