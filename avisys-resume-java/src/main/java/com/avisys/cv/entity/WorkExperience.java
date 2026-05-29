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
@Table(name = "work_experience_info")
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exp_id")
    private Long expId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "company")
    private String company;
    
    @Column(name = "client_name")
    private String clientName;

    @Column(name = "position")
    private String position;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "description")
    private String description;
    
    @Column(name = "city")
    private String city;
	
	@Column(name = "state")
    private String state;
	
	@Column(name = "country")
    private String country;
	
	@Column(name = "domain")
	private String domain;
	
	@Column(name = "years_of_experience")
	private Double yearsOfExperience;
    
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

	public WorkExperience() {
		
	}
	public WorkExperience(Long expId, Person person, String company, String clientName, String position, String startDate, String endDate,
			String description, String city, String state, String stateValue, String country,String countryValue, String domain, String domainValue, Double yearsOfExperience) {
		this.expId = expId;
		this.person = person;
		this.company = company;
		this.clientName = clientName;
		this.position = position;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.city = city;
		this.state = stateValue;
		this.country = countryValue;
		this.domain = domainValue;
		this.yearsOfExperience = yearsOfExperience;
	}
	
}

