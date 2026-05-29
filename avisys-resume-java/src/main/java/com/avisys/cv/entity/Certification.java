package com.avisys.cv.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "certifications_info")
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cert_id")
    private Long certId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "certification_name")
    private String certificationName;

    @Column(name = "issuing_organization")
    private String issuingOrganization;

    @Column(name = "issue_date")
    private String issueDate;
    
	@Column(name = "is_deleted")
	private Boolean isDeleted;
    
    @Column(name = "created_by")
	private String createdBy;

	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@Column(name = "last_update_by")
	private String lastUpdateBy;

	@Column(name = "last_update_date")
	private LocalDateTime lastUpdateDate;

	public Certification() {
		
	}
	public Certification(Long certId, Person person, String certificationName, String certificationNameValue, String issuingOrganization, String issueDate) {
		this.certId = certId;
		this.person = person;
		this.certificationName = certificationNameValue;
		this.issuingOrganization = issuingOrganization;
		this.issueDate = issueDate;
	}
}

