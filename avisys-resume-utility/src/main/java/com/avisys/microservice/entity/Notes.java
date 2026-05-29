package com.avisys.microservice.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Table(name = "NOTES")
@SequenceGenerator(name = "note_id_seq", sequenceName = "note_id_seq", allocationSize = 1)
public class Notes {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_id_seq")
	@Column(name = "id")
	private Long notesId;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	@Column(name = "owner")
	private String owner;

	@Column(name = "reference_id")
	private Long referenceId;

	@Column(name = "reference_type")
	private String referenceType;
	
	@Transient
	private LocalDate date ;

	public LocalDate getDate() {
		
		return getCreatedDate().toLocalDate();
	  }

}
