package com.avisys.microservice.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Data
@ToString
@Table(schema = "crm",name = "user_info")
@SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "PROFILE_IMAGE")
	private String profileImage;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "contact")
	private String contact;

	@Column(name = "REPORTS_TO", nullable = true)
	private String reportsTo;

	@Column(name = "CREATED_BY", nullable = false)
	private String createdBy;

	@Column(name = "CREATE_DATE", nullable = false)
	private Instant createDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "LAST_UPDATE_DATE")
	private Instant lastUpdateDate;
	
	@Column(name = "REALM_ID")
	private String realmId;

	@Column(name = "ENABLED")
	private boolean enabled;

	@Column(name = "DELETED")
	private Boolean deleted = false;

	@ManyToOne
	@JoinColumn(name = "DESIGNATION_ID")
	private Designation designation;
	
	@PrePersist
	private void prePersist() {
		this.createDate = Instant.now();
	}

	@PreUpdate
	private void preUpdate() {
		this.lastUpdateDate = Instant.now();
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", userId=" + userId + ", userName=" + userName + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", profileImage=" + profileImage + ", email=" + email + ", contact="
				+ contact + ", reportsTo=" + reportsTo + ", createdBy=" + createdBy + ", createDate=" + createDate
				+ ", updatedBy=" + updatedBy + ", lastUpdateDate=" + lastUpdateDate + ", realmId=" + realmId
				+ ", enabled=" + enabled + ", deleted=" + deleted + ", designation=" + designation + "]";
	}
	
	
}
