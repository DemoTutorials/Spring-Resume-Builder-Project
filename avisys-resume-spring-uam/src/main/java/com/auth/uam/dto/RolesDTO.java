package com.auth.uam.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.auth.uam.constant.ErrorMessages;
import com.auth.uam.entity.Permission;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class RolesDTO {
	private Long roleId;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String roleName;

	private String description;

	private Boolean isDeleted;

	private String createdBy;

	private LocalDateTime creationDate;

	private String lastUpdateBy;

	private LocalDateTime lastUpdateDate;

	private List<Permission> permission;
}
