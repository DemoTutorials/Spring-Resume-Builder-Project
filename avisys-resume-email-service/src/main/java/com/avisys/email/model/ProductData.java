package com.avisys.email.model;

import java.util.ArrayList;

import org.springframework.data.domain.Page;

public class ProductData<T> {

	ArrayList<TableHeader> headerlist;
	Page <T> page;
	
	
	
	public ProductData() {
		super();
	}
	public ProductData(ArrayList<TableHeader> headerlist, Page<T> page) {
		super();
		this.headerlist = headerlist;
		this.page = page;
	}
	public ArrayList<TableHeader> getHeaderlist() {
		return headerlist;
	}
	public void setHeaderlist(ArrayList<TableHeader> headerlist) {
		this.headerlist = headerlist;
	}
	public Page<T> getPage() {
		return page;
	}
	public void setPage(Page<T> page) {
		this.page = page;
	}
}
