package com.avisys.microservice.model;

public class EmailTemplateVariable {

	private String customerName;
	private String to;
	private String customerEmail;
	private String customerContactNumber;
	private String customerBilingAmount;
	private String customerBillingDate;
	private String customerAddressType;
	private String temporaryPassword;
	private String forgetPasswordUrl;
	private String requestNumber;
	private String accountNumber;
	private String requestCallType;
	private String billingAccount;
	private String redirectUrl;
	private String date;
	private String text;

	
	
	public EmailTemplateVariable(EmailTemplateVariable etv) {
		super();
		this.customerName = etv.customerName;
		this.to = etv.to;
		this.customerEmail = etv.customerEmail;
		this.customerContactNumber = etv.customerContactNumber;
		this.customerBilingAmount = etv.customerBilingAmount;
		this.customerBillingDate = etv.customerBillingDate;
		this.customerAddressType = etv.customerAddressType;
		this.temporaryPassword = etv.temporaryPassword;
		this.forgetPasswordUrl = etv.forgetPasswordUrl;
		this.requestNumber = etv.requestNumber;
		this.accountNumber = etv.accountNumber;
		this.requestCallType = etv.requestCallType;
		this.billingAccount = etv.billingAccount;
		this.redirectUrl = etv.redirectUrl;
		this.date = etv.date;
		this.text = etv.text;
	}

	public EmailTemplateVariable() {
		super();
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerContactNumber() {
		return customerContactNumber;
	}

	public void setCustomerContactNumber(String customerContactNumber) {
		this.customerContactNumber = customerContactNumber;
	}

	public String getCustomerBilingAmount() {
		return customerBilingAmount;
	}

	public void setCustomerBilingAmount(String customerBilingAmount) {
		this.customerBilingAmount = customerBilingAmount;
	}

	public String getCustomerBillingDate() {
		return customerBillingDate;
	}

	public void setCustomerBillingDate(String customerBillingDate) {
		this.customerBillingDate = customerBillingDate;
	}

	public String getCustomerAddressType() {
		return customerAddressType;
	}

	public void setCustomerAddressType(String customerAddressType) {
		this.customerAddressType = customerAddressType;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getTemporaryPassword() {
		return temporaryPassword;
	}

	public void setTemporaryPassword(String temporaryPassword) {
		this.temporaryPassword = temporaryPassword;
	}

	public String getForgetPasswordUrl() {
		return forgetPasswordUrl;
	}

	public void setForgetPasswordUrl(String forgetPasswordUrl) {
		this.forgetPasswordUrl = forgetPasswordUrl;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getRequestCallType() {
		return requestCallType;
	}

	public void setRequestCallType(String requestCallType) {
		this.requestCallType = requestCallType;
	}

	public String getBillingAccount() {
		return billingAccount;
	}

	public void setBillingAccount(String billingAccount) {
		this.billingAccount = billingAccount;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "EmailTemplateVariable [customerName=" + customerName + ", to=" + to + ", customerEmail=" + customerEmail
				+ ", customerContactNumber=" + customerContactNumber + ", customerBilingAmount=" + customerBilingAmount
				+ ", customerBillingDate=" + customerBillingDate + ", customerAddressType=" + customerAddressType
				+ ", temporaryPassword=" + temporaryPassword + ", forgetPasswordUrl=" + forgetPasswordUrl
				+ ", requestNumber=" + requestNumber + ", accountNumber=" + accountNumber + ", requestCallType="
				+ requestCallType + ", billingAccount=" + billingAccount + ", redirectUrl=" + redirectUrl + ", date="
				+ date + ", text=" + text + "]";
	}

}
