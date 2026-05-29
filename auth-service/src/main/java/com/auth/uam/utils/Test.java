package com.auth.uam.utils;

import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

public class Test {

	public static void main(String[] args) {
//		String encodeString="virendra";
//		
//		String encoded = DatatypeConverter.printBase64Binary(encodeString.getBytes());
//		
//		System.out.println(encoded);
//
//		String decoded = new String(DatatypeConverter.parseBase64Binary("dmlyZW5kcmE="));
//		System.out.println(decoded);
		
//		dmlyZW5kcmE=
//		DMLYzw5KCMe=
		
		System.out.println(generateToken());
	}

	public static String generateToken() {
		StringBuilder token = new StringBuilder();

		return token.append(UUID.randomUUID().toString())
				.append(UUID.randomUUID().toString()).toString();
	}
}
