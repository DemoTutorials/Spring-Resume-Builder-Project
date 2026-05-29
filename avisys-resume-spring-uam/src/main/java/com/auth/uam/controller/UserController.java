package com.auth.uam.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.uam.dto.ChangePasswordDTO;
import com.auth.uam.dto.CommonMaster;
import com.auth.uam.dto.ForgotPasswordDTO;
import com.auth.uam.dto.PostDto;
import com.auth.uam.dto.RegistrationDto;
import com.auth.uam.dto.Status;
import com.auth.uam.dto.UserDTO;
import com.auth.uam.dto.UserDatatable;
import com.auth.uam.dto.UserDetailsDTO;
import com.auth.uam.entity.User;
import com.auth.uam.exception.NotFoundGenericException;
import com.auth.uam.security.service.UserService;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	UserService userService;

//	@GetMapping("get")
//	public ResponseEntity<List<UserProjection>> getUserList() {
//
//		List<UserProjection> list = userService.getListUser();
//		return new ResponseEntity<>(list, HttpStatus.OK);
//	}

	@GetMapping("all")
	public ResponseEntity<Page<UserDatatable>> getSearchAndPagination(@Nullable String name, Pageable pageable,
			@RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			System.out.println("headerValue: " + userId);
			Page<UserDatatable> userPage = userService.getSearchAndPagination(name, pageable, userId);
			return new ResponseEntity<>(userPage, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<User> getById(@PathVariable(value = "id") Long id) {
		Optional<User> optionalUser = userService.getByIdUser(id);
		if (optionalUser.isPresent()) {
			return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
		} else
			throw new PersistenceException("Record not fond");
	}

	@GetMapping("/search/JobPost")
	public ResponseEntity<Page<PostDto>> searchJobPost(@PageableDefault Pageable pageable,
			@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
			@RequestParam(value = "userId") Long userId) {
		Page<PostDto> result = this.userService.searchJobPost(pageable, keyword, userId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("save")
	public ResponseEntity<User> save(@Valid @RequestBody UserDTO user, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			System.out.println("headerValue: " + userId);
			User savedUser = userService.create(user, userId);
			return new ResponseEntity<>(savedUser, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PutMapping("update")
	public ResponseEntity<User> update(@Valid @RequestBody UserDTO user, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			System.out.println("headerValue: " + userId);
			User savedUser = userService.updateRoles(user, userId);
			return new ResponseEntity<>(savedUser, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PostMapping("change-password")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO,
			@RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			System.out.println("headerValue: " + userId);
			String changePassword = userService.changePassword(changePasswordDTO, userId);
			return new ResponseEntity<>(new Status(changePassword), HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@DeleteMapping("soft-delete")
	public ResponseEntity<User> delete(Long id, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			System.out.println("headerValue: " + userId);
			User deletedUser = userService.softDelete(id, userId);
			return new ResponseEntity<>(deletedUser, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PostMapping("forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO,
			@RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			System.out.println("headerValue: " + userId);
			String forgotPassword = userService.forgotPassword(forgotPasswordDTO, userId);
			return new ResponseEntity<>(new Status(forgotPassword), HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PostMapping("register")
	public ResponseEntity<?> resgisteration(@Valid @RequestBody RegistrationDto register,
			@RequestHeader("Authorization") String token) {
		try {
			Status registerUser = userService.registration(register);
			return new ResponseEntity<>(registerUser, HttpStatus.OK);
		} catch (Exception e) {
			throw new NotFoundGenericException(e.getMessage());
		}
	}

	@GetMapping("get-user-details/{userId}")
	public ResponseEntity<String> getUserNameByUserId(@PathVariable(value = "userId") String userId) {
		String userName = userService.getUserDetailsByUserId(userId);
		return new ResponseEntity<>(userName, HttpStatus.OK);
	}

	@GetMapping("user-details/{userId}")
	public ResponseEntity<UserDetailsDTO> getUserDetailsByUserId(@PathVariable(value = "userId") String userId) {
		UserDetailsDTO userDetailsDTO = userService.getUserDetailsByUserUniqueId(userId);
		return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);
	}

	@GetMapping("user-types")
	public ResponseEntity<List<CommonMaster>> getUserTypesDropdownByUserId(@RequestHeader HttpHeaders headers) {
		String userId = headers.getFirst("userId");
		System.out.println("headerValue: " + userId);
		List<CommonMaster> commonMaster = userService.getUserTypesDropdownByUserId(userId);
		return new ResponseEntity<>(commonMaster, HttpStatus.OK);
	}
	

	@PostMapping("createUserByHr")
	public ResponseEntity<User> createUserByHr(@Valid @RequestBody UserDTO user, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			System.out.println("headerValue: " + userId);
			User savedUser = userService.createUserByHr(user, userId);
			return new ResponseEntity<>(savedUser, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
}
