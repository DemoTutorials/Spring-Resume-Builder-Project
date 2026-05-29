package com.avisys.microservice.dto;

public class User {

	private String userId;
	private String email;
	private String firstName;
	private String lastName;
	private String realmId;
	private String userName;
	private boolean enabled;
	private boolean isTemporary;
	private String reportsTo;
	private Long designationId;
	private String contact;

	private String createdBy;
	private String updatedBy;

	public User(String userId, String email, String firstName, String lastName, String realmId, String userName) {
		super();
		this.userId = userId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.realmId = realmId;
		this.userName = userName;
	}

	public User() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRealmId() {
		return realmId;
	}

	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean getIsEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", realmId=" + realmId + ", userName=" + userName + ", enabled=" + enabled + ", isTemporary="
				+ isTemporary + ", reportsTo=" + reportsTo + ", designationId=" + designationId + ", contact=" + contact
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
	}

}
