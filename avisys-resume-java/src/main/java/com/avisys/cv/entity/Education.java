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

import org.springframework.data.jpa.repository.Query;

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
@Table(name = "education_info")
public class Education {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "edu_id")
	private Long eduId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	private Person person;

	@Column(name = "qualification")
	private String qualification;

	@Column(name = "stream")
	private String stream;

	@Column(name = "institution")
	private String institution;

	@Column(name = "completion_year")
	private Integer completionYear;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	@Column(name = "description")
	private String description;

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
	
	public Education() {
		
	}
	public Education(Long eduId, Person person, String qualification, String qualificationValue, String stream, String institution,
			Integer completionYear, String city, String state, String stateValue, String country, String countryValue, String description) {
		this.eduId = eduId;
		this.person = person;
		this.qualification = qualificationValue;
		this.stream = stream;
		this.institution = institution;
		this.completionYear = completionYear;
		this.city = city;
		this.state = stateValue;
		this.country = countryValue;
		this.description = description;
	}

}
