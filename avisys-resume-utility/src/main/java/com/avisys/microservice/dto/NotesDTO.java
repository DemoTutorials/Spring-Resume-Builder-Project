package com.avisys.microservice.dto;

import java.time.LocalDate;
import java.util.List;

import com.avisys.microservice.entity.Notes;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class NotesDTO {

	private LocalDate date;

	private List<Notes> notes;
}
