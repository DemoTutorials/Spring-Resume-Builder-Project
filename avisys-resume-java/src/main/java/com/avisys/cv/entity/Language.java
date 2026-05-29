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
@Table(name = "languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lang_id")
    private Long langId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "language")
    private String language;
    
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

	public Language() {
		
	}
	public Language(Long langId, Person person, String language, String languageValue) {
		this.langId = langId;
		this.person = person;
		this.language = languageValue;
	}

	
}
