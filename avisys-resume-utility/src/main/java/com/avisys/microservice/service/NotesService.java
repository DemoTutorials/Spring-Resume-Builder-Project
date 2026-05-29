package com.avisys.microservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisys.microservice.dto.NotesDTO;
import com.avisys.microservice.entity.Notes;
import com.avisys.microservice.repository.NotesRepository;

@Service
public class NotesService {

	@Autowired
	NotesRepository notesRepository;

	public List<NotesDTO> getNotesByrefIdAndType(Long refId, String refType) {

		List<Notes> listNotes = notesRepository.findByIsDeletedAndReferenceTypeAndReferenceIdOrderByCreatedDateDesc(false,
				refType, refId);
		List<LocalDate> dates = new ArrayList<LocalDate>();
		listNotes.forEach(n -> {

			dates.add(n.getDate());
		});

		List<NotesDTO> notesDTO = new ArrayList<NotesDTO>();

		Set<LocalDate> foo = new LinkedHashSet<LocalDate>(dates);
		for (LocalDate localDate : foo) {
			NotesDTO notesDto = new NotesDTO();
			List<Notes> newNotes = listNotes.stream().filter(nn -> nn.getDate().equals(localDate))
					.collect(Collectors.toList());

			notesDto.setDate(localDate);
			notesDto.setNotes(newNotes);
			notesDTO.add(notesDto);
		}

		return notesDTO;
	}

	public Optional<Notes> getNotesById(Long id) {

		return notesRepository.findByNotesIdAndIsDeleted(id, false);
	}

	public Notes save(Notes notes) {
		LocalDateTime dateTime = LocalDateTime.now();
		notes.setCreatedDate(dateTime);
		notes.setIsDeleted(false);
		notes.setOwner(notes.getCreatedBy());
		return notesRepository.save(notes);
	}

	public Notes update(Notes notes) {
		LocalDateTime dateTime = LocalDateTime.now();
		Optional<Notes> findById = notesRepository.findByNotesIdAndIsDeleted(notes.getNotesId(), false);
		if (!findById.isPresent()) {
			throw new PersistenceException("Notes not found for ID :" + notes.getNotesId());
		}
		Notes existingNotes = findById.get();
		existingNotes.setNotes(notes.getNotes());
		existingNotes.setUpdatedDate(dateTime);
		existingNotes.setUpdatedBy(notes.getUpdatedBy());
		return notesRepository.save(existingNotes);
	}

	public Notes softDeleteNotes(Long id, String updatedBy) {
		LocalDateTime dateTime = LocalDateTime.now();
		Optional<Notes> findById = notesRepository.findById(id);
		if (findById.isEmpty()) {
			throw new PersistenceException("Notes not found for ID :" + id);
		}
		Notes existingNotes = findById.get();
		Boolean b = existingNotes.getIsDeleted();
		if (Boolean.TRUE.equals(b)) {
			throw new PersistenceException("Notes already Deleted.");
		}
		existingNotes.setIsDeleted(true);
		existingNotes.setUpdatedBy(updatedBy);
		existingNotes.setUpdatedDate(dateTime);
		return notesRepository.save(existingNotes);
	}

}
