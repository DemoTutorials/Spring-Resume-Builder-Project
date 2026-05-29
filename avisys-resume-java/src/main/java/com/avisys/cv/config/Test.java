package com.avisys.cv.config;

import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

public class Test {
	
	public static String methodName(String url) {
		 
		String s1 = url;
 
		s1 = s1.replace("[", "");
		s1 = s1.replace("]", "");
 
		System.out.println(s1);
 
		return s1;
 
	}
}
