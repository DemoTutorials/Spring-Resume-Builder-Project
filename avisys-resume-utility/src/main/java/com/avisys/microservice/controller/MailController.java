package com.avisys.microservice.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.microservice.dto.MailDTO;
import com.avisys.microservice.dto.MailNotificationDTO;
import com.avisys.microservice.entity.MailNotification;
import com.avisys.microservice.exception.ResourceNotFoundExceptionGeneric;
import com.avisys.microservice.service.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

@RestController
@RequestMapping("mail")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MailController {

	@Autowired
	MailService mailService;

	@GetMapping(path = "all/{id}/{type}")
	public ResponseEntity<List<MailNotificationDTO>> getMailListByRequestId(@PathVariable(value = "id") String id,
			@PathVariable(value = "type") String type) {
		List<MailNotificationDTO> mailList = mailService.getMails(type, id);

		return new ResponseEntity<>(mailList, HttpStatus.OK);
	}

	@PostMapping(path = "create/{sendMail}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<MailNotification> create(@RequestParam String maildto,
			@Nullable @RequestPart List<MultipartFile> file, @PathVariable("sendMail") String sendMail) {
		try {

			MailDTO target2 = new ObjectMapper().registerModule(new JSR310Module())
					.readValue(maildto, MailDTO.class);
			MailNotification mailSaved = mailService.save(target2, file, sendMail);
			System.out.println("This is from data");
			return new ResponseEntity<>(mailSaved, HttpStatus.OK);
		} catch (DataIntegrityViolationException dive) {
			throw new DataIntegrityViolationException("Data IntegrityViolationException" + dive);
		} catch (Exception e) {
			throw new PersistenceException("Failed to sending mail.", e);
		}
	}

	@PutMapping(path = "update")
	public ResponseEntity<MailNotification> update(@RequestPart MailNotification mailNotification) {
		try {
			MailNotification mailUpdate = mailService.update(mailNotification);
			return new ResponseEntity<>(mailUpdate, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<MailNotification> getById(@PathVariable(value = "id") Long id) {
		Optional<MailNotification> mailOptional = mailService.getById(id);
		if (mailOptional.isPresent()) {
			return new ResponseEntity<>(mailOptional.get(), HttpStatus.OK);
		} else {
			throw new ResourceNotFoundExceptionGeneric("Billing Account not found.");
		}
	}

	@DeleteMapping("softdelete/{id}/{updatedBy}")
	public ResponseEntity<MailNotification> softDelete(@PathVariable Long id, @PathVariable String updatedBy) {
		MailNotification mailNotification = mailService.softDelete(id, updatedBy);
		return new ResponseEntity<>(mailNotification, HttpStatus.OK);
	}

}
