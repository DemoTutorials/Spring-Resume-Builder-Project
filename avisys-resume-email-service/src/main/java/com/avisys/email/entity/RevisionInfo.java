package com.avisys.email.entity;

import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Data
@ToString
@Table(name = "revision_info")
public class RevisionInfo {
	
	public RevisionInfo() {}

	public RevisionInfo(Long revId, Instant revTstmp, String actionBy, Long referenceId, String entityTag,
			String revType, String actionFor) {
		
		this.revId = revId;
		this.revTstmp = revTstmp;
		this.actionBy = actionBy;
		this.referenceId = referenceId;
		this.entityTag = entityTag;
		this.revType = revType;
		this.actionFor = actionFor;
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rev_id")
	private Long revId;

	@Column(name = "rev_tstmp")
	private Instant revTstmp;

//	@Column(name = "userId")
//	private String userId;

	@Column(name = "action_by")
	private String actionBy;

	@Column(name = "reference_id")
	private Long referenceId;

	@Column(name = "entity_tag")
	private String entityTag;

	@Column(name = "rev_type")
	private String revType;
	
	@Column(name = "action_for")
	private String actionFor;

	@JsonIgnoreProperties({ "revisionInfo" })
	@OneToMany(mappedBy = "revisionInfo")
	private List<AuditingInfo> auditingInfo;
}
