package com.avisys.microservice.dto;

import java.util.List;

public class UserDTO {

	private String userId;

	private String email;

	private String password;

	private String firstName;

	private String lastName;

	private int statusCode;

	private boolean isTemporary;

	private List<String> role;

	private String status;

	private List<String> avaliableRoles;

	private String reportsTo;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getAvaliableRoles() {
		return avaliableRoles;
	}

	public void setAvaliableRoles(List<String> avaliableRoles) {
		this.avaliableRoles = avaliableRoles;
	}

	public boolean getIsTemporary() {
		return isTemporary;
	}

	public void setIsTemporary(boolean isTemporary) {
		this.isTemporary = isTemporary;
	}

	public String getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(String reportsTo) {
		this.reportsTo = reportsTo;
	}

}