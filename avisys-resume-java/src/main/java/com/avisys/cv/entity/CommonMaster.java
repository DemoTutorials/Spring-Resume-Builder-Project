package com.avisys.cv.entity;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(schema = "crm_kwa", name = "COMMON_MST")
public class CommonMaster {
 
	@Id
	@Column(name = "COMMON_MST_ID")
	private long commonMstId;
 
	@Column(name = "MST_NAME")
	private String mstName;
 
	@Column(name = "CODE")
	private String code;
 
	@Column(name = "VALUE")
	private String value;
 
	@Column(name = "FOREIGN_KEY")
	private String foreignKey;
 
	@Column(name = "LOCAL_VALUE")
	private String localValue;
 
	@Column(name = "ADDITIONAL_VALUE")
	private String additionalValue;
 
	@Column(name = "PRIORITY")
	private Integer priority;
 
	@Column(name = "DESCRIPTION")
	private String description;
 
	@Column(name = "STATUS")
	private boolean status;
 
	@Column(name = "IS_DELETED")
	private boolean deleted;
 
	@Column(name = "IS_MST")
	private boolean mst;
 
	@Column(name = "CREATED_BY")
	private String createdBy;
 
	@Column(name = "CREATED_DATE")
	private Instant createdDate;
 
	@Column(name = "UPDATED_BY")
	private String updatedBy;
 
	@Column(name = "UPDATED_DATE")
	private Instant updatedDate;
 
	@Column(name = "START_DATE")
	private Instant startDate;
 
	@Column(name = "END_DATE")
	private Date endDate;
 
	public long getCommonMstId() {
		return commonMstId;
	}
 
	public void setCommonMstId(long commonMstId) {
		this.commonMstId = commonMstId;
	}
 
	public String getCreatedBy() {
		return createdBy;
	}
 
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
 
	public Instant getCreatedDate() {
		return createdDate;
	}
 
	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}
 
	public String getUpdatedBy() {
		return updatedBy;
	}
 
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
 
	public Instant getUpdatedDate() {
		return updatedDate;
	}
 
	public void setUpdatedDate(Instant updatedDate) {
		this.updatedDate = updatedDate;
	}
 
	public String getMstName() {
		return mstName;
	}
 
	public void setMstName(String mstName) {
		this.mstName = mstName;
	}
 
	public String getCode() {
		return code;
	}
 
	public void setCode(String code) {
		this.code = code;
	}
 
	public String getValue() {
		return value;
	}
 
	public void setValue(String value) {
		this.value = value;
	}
 
	public String getlocalValue() {
		return localValue;
	}
 
	public void setlocalValue(String localValue) {
		this.localValue = localValue;
	}
 
	public String getadditionalValue() {
		return additionalValue;
	}
 
	public void setadditionalValue(String additionalValue) {
		this.additionalValue = additionalValue;
	}
 
	public String getDescription() {
		return description;
	}
 
	public void setDescription(String description) {
		this.description = description;
	}
 
	public boolean getStatus() {
		return status;
	}
 
	public void setStatus(boolean status) {
		this.status = status;
	}
 
	public boolean getDeleted() {
		return deleted;
	}
 
	public void setDeleted(boolean isDeleted) {
		this.deleted = isDeleted;
	}
 
	public boolean getMst() {
		return mst;
	}
 
	public void setMst(boolean isMst) {
		this.mst = isMst;
	}
 
	public String getForeignKey() {
		return foreignKey;
	}
 
	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}
 
	public Instant getStartDate() {
		return startDate;
	}
 
	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}
 
	public Date getEndDate() {
		return endDate;
	}
 
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
 
	public Integer getPriority() {
		return priority;
	}
 
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
 
}
