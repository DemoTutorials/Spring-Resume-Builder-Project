package com.auth.uam.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonMaster {

	private long commonMstId;
	private String code;
	private String value;
	private Integer priority;
}
