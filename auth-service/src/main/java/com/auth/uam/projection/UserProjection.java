package com.auth.uam.projection;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@Data
@ToString
public class UserProjection  {

	
	
	public UserProjection(Long id,String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}
	Long id;

	String userName;
}
