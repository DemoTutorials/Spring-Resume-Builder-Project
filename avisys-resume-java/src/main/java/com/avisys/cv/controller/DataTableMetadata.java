package com.avisys.cv.controller;

import java.util.ArrayList;
import java.util.List;

import com.avisys.cv.dto.ColumnMetadata;

public class DataTableMetadata {

	private List<ColumnMetadata> columnsMetadata = new ArrayList<>();

	public DataTableMetadata addColumnMetadata(ColumnMetadata columnMetadata) {
		this.columnsMetadata.add(columnMetadata);
		return this;
	}

	public List<ColumnMetadata> getColumnsMetadata() {
		return columnsMetadata;
	}
}
