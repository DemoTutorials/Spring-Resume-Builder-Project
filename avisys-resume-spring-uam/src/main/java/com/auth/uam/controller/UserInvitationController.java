package com.auth.uam.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.uam.dto.AcceptInvitationDTO;
import com.auth.uam.dto.Status;
import com.auth.uam.dto.UserInvitationDTO;
import com.auth.uam.dto.UserInvitationDatatableDTO;
import com.auth.uam.service.UserInvitationService;

@RestController
@RequestMapping("user-invitation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserInvitationController {

	@Autowired
	UserInvitationService userInvitationService;

	@PostMapping("save")
	public ResponseEntity<List<UserInvitationDTO>> save(@Valid @RequestBody List<UserInvitationDTO> userInvitationDTO,
			@RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			List<UserInvitationDTO> saveUserInvitation = userInvitationService.saveUserInvitation(userInvitationDTO, userId);
			return new ResponseEntity<>(saveUserInvitation, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	@GetMapping("all")
	public ResponseEntity<Page<?>> getSearchAndPagination(@Nullable String name, Pageable pageable, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			System.out.println("headerValue: " + userId);
			Page<UserInvitationDatatableDTO> userPage = userInvitationService.getSearchAndPagination(name, pageable,userId);
			return new ResponseEntity<>(userPage, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	@PostMapping("accept-user-invitation")
	public ResponseEntity<Status> acceptUserInvitation(@RequestBody AcceptInvitationDTO acceptInvitationDTO) {
		try {
			
			Status acceptInvitation = userInvitationService.acceptUserInvitation(acceptInvitationDTO);
			return new ResponseEntity<>(acceptInvitation, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Status(e.getMessage(),"400"), HttpStatus.BAD_REQUEST);
		}
	}
	

}
