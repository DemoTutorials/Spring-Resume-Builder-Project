package com.avisys.microservice.dto;

import java.util.List;

public class HierarchyResponse {

	private String name;
	private Long id;
	private Long parentId;
	private List<HierarchyResponse> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HierarchyResponse> getChildren() {
		return children;
	}

	public void setChildren(List<HierarchyResponse> children) {
		this.children = children;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
