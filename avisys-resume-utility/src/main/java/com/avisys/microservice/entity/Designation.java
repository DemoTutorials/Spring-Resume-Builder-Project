package com.avisys.microservice.entity;

import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Data
@ToString
@Table(name = "DESIGNATION",schema = "crm")
@SequenceGenerator(name = "master_seq", sequenceName = "master_seq", allocationSize = 1)
public class Designation {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_seq")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PARENT_ID")
	private Long parentId;

	@Column(name = "CREATED_BY", nullable = false)
	private String createdBy;

	@Column(name = "CREATE_DATE", nullable = false)
	private Instant createDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "LAST_UPDATE_DATE")
	private Instant lastUpdateDate;

	@Column(name = "DELETED")
	private Boolean deleted = false;

	@OneToMany(mappedBy = "designation")
	@JsonIgnore
	private List<UserInfo> usersList;

	@PrePersist
	private void prePersist() {
		this.createDate = Instant.now();
	}

	@PreUpdate
	private void preUpdate() {
		this.lastUpdateDate = Instant.now();
	}

}
