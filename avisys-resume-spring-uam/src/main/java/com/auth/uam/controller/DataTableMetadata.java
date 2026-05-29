package com.auth.uam.controller;

import java.util.ArrayList;
import java.util.List;

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
