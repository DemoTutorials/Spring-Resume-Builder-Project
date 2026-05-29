package com.avisys.microservice.entity;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.avisys.microservice.jsonviews.Views;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "COMMON_MST")
@SequenceGenerator(name = "common_mst_common_mst_id_seq", sequenceName = "common_mst_common_mst_id_seq", allocationSize = 1)
public class CommonMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_mst_common_mst_id_seq")
	@JsonView({ Views.commonMaster.class, Views.commonMasterPut.class, Views.commonMasterView.class })
	@Column(name = "COMMON_MST_ID")
	private long commonMstId;

	@Column(name = "MST_NAME")
	@JsonView({ Views.commonMaster.class, Views.commonMasterPut.class })
	private String mstName;

	@Column(name = "CODE")
	@JsonView({ Views.commonMaster.class, Views.commonMasterPut.class, Views.commonMasterView.class })
	private String code;

	@Column(name = "VALUE")
	@JsonView({ Views.commonMaster.class, Views.commonMasterPut.class, Views.commonMasterView.class,
			Views.commonMasterValue.class })
	private String value;

	@Column(name = "FOREIGN_KEY")
	@JsonView({ Views.commonMaster.class, Views.commonMasterPut.class })
	private String foreignKey;

	@Column(name = "LOCAL_VALUE")
	@JsonView(Views.noView.class)
	private String localValue;

	@Column(name = "ADDITIONAL_VALUE")
	@JsonView(Views.commonMaster.class)
	private String additionalValue;

	@Column(name = "PRIORITY")
	@JsonView({ Views.commonMaster.class, Views.commonMasterView.class })
	private Integer priority;

	@Column(name = "DESCRIPTION")
	@JsonView(Views.noView.class)
	private String description;

	@Column(name = "STATUS")
	@JsonView(Views.noView.class)
	private boolean status;

	@Column(name = "IS_DELETED")
	@JsonView({ Views.noView.class })
	private boolean deleted;

	@Column(name = "IS_MST")
	@JsonView({ Views.commonMaster.class, Views.commonMasterPut.class })
	private boolean mst;

	@Column(name = "CREATED_BY")
	@JsonView(Views.commonMasterPost.class)
	private String createdBy;

	@Column(name = "CREATED_DATE")
	@JsonView(Views.noView.class)
	private Instant createdDate;

	@Column(name = "UPDATED_BY")
	@JsonView(Views.commonMasterPut.class)
	private String updatedBy;

	@Column(name = "UPDATED_DATE")
	@JsonView(Views.noView.class)
	private Instant updatedDate;

	@Column(name = "START_DATE")
	@JsonView(Views.noView.class)
	private Instant startDate;

	@Column(name = "END_DATE")
	@JsonView(Views.noView.class)
	private Date endDate;

	@PrePersist
	private void prePersist() {
		this.createdDate = Instant.now();
		this.startDate = Instant.now();
	}

	@PreUpdate
	private void preUpdate() {
		this.updatedDate = Instant.now();
	}

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

	@Override
	public String toString() {
		return "CommonMaster [commonMstId=" + commonMstId + ", mstName=" + mstName + ", code=" + code + ", value="
				+ value + ", foreignKey=" + foreignKey + ", localValue=" + localValue + ", additionalValue="
				+ additionalValue + ", priority=" + priority + ", description=" + description + ", status=" + status
				+ ", deleted=" + deleted + ", mst=" + mst + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}

}
