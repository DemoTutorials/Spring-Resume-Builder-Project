package com.avisys.cv.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.avisys.cv.exception.ErrorMessages;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class PersonDto {

	private Long personId;

	private String profilePic;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String firstName;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String lastName;

	private String summary;

	private String city;

	private String state;

	private String country;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String email;

	private String phone;
	
	private String companyCode;

	private String linkedin;
	
	private String name;
	
	private Float totalExperience;
	
	private String uploadFile;

	public void setName(boolean showName) {
		this.name = showName? firstName+ " " + lastName.charAt(0) + "." : firstName+" "+lastName;
	}
	
	public void setContact(boolean showcontact) {
		this.phone = showcontact? phone : null;
	}

	public String getLocation() {
		return city + ", " + state;
	}
	
}
