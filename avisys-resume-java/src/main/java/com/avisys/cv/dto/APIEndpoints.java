package com.avisys.cv.dto;

import java.time.LocalDateTime;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
@Table(name = "APIENDPOINTS")
@AllArgsConstructor
@NoArgsConstructor
public class APIEndpoints {

	private String methodName;

	private String path;

	private String controllerName;

	private String serviceName;

	private LocalDateTime creationDate;

}
