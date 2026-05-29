package com.avisys.cv.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Data
@ToString
@Table(name = "personal_info")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id")
	private Long personId;

	@Column(name = "profile_pic")
	private String profilePic;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "summary")
	private String summary;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "linkedin")
	private String linkedin;

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

	@Column(name = "company_code")
	private String companyCode;
	
	@Transient
	private String companyName;
	
	@Transient
	private String jobPostCode;

	@Column(name = "total_experience")
	private Float totalExperience;

	@Column(name = "upload_file")
	private String uploadFile;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Education> educationList = new ArrayList<>();

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Certification> certificationList = new ArrayList<>();

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Language> languageList = new ArrayList<>();

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Skill> skillsList = new ArrayList<>();

	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private OtherSkills otherSkillsList;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<WorkExperience> experienceList = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "person_jobpost", schema = "jasper_cv",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private Set<JobPost> jobPosts;
	
	public Person() {
	}
	
	public Person(Long personId, String city, String country, String countryValue, String email, String firstName,
			String lastName, String linkedin, String phone, String profilePic, String state, String stateValue,
			String summary, String companyCode, Float totalExperience, String uploadFile) {
		this.personId = personId;
		this.city = city;
		this.country = countryValue;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.linkedin = linkedin;
		this.phone = phone;
		this.profilePic = profilePic;
		this.state = stateValue;
		this.summary = summary;
		this.companyCode = companyCode;
		this.totalExperience = totalExperience;
		this.uploadFile = uploadFile;
	}

}
