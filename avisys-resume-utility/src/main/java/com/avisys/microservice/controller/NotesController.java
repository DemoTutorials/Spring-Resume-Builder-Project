package com.avisys.microservice.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.microservice.dto.NotesDTO;
import com.avisys.microservice.entity.Notes;
import com.avisys.microservice.service.NotesService;

@RestController
@RequestMapping("notes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotesController {

	@Autowired
	NotesService notesService;

	@GetMapping(path = "all/{refId}/{refType}")
	public ResponseEntity<List<NotesDTO>> getNoteListByReferenceId(@PathVariable(value = "refId") Long refId,
			@PathVariable(value = "refType") String refType) {
		List<NotesDTO> listOfNotes = notesService.getNotesByrefIdAndType(refId, refType);
		return new ResponseEntity<>(listOfNotes, HttpStatus.OK);

	}

	@GetMapping(path = "{id}")
	public ResponseEntity<Notes> getById(@PathVariable(value = "id") Long id) {
		Optional<Notes> notes = notesService.getNotesById(id);
		if (notes.isPresent()) {
			return new ResponseEntity<>(notes.get(), HttpStatus.OK);
		} else {
			throw new PersistenceException("Notes not found.");
		}
	}

	@PostMapping(path = "create")
	public ResponseEntity<Notes> create(@RequestBody Notes notes) {
		try {
			Notes savedNotes = notesService.save(notes);
			return new ResponseEntity<>(savedNotes, HttpStatus.OK);
		} catch (DataIntegrityViolationException dive) {
			throw new DataIntegrityViolationException("Data IntegrityViolationException" + dive);
		} catch (Exception e) {
			throw new PersistenceException("Failed to save notes.", e);
		}
	}

	@PutMapping(path = "update")
	public ResponseEntity<Notes> update(@RequestBody Notes notes) {
		try {
			Notes notesUpdate = notesService.update(notes);
			return new ResponseEntity<>(notesUpdate, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@DeleteMapping("softdelete/{id}/{updatedBy}")
	public ResponseEntity<Notes> softDelete(@PathVariable Long id, @PathVariable String updatedBy) {
		try {
		Notes notesServiceRequest = notesService.softDeleteNotes(id, updatedBy);
		return new ResponseEntity<>(notesServiceRequest, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
}
