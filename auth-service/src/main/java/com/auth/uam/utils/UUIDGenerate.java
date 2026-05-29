package com.auth.uam.utils;

import java.util.UUID;

public class UUIDGenerate {

	public static String generateToken() {
		StringBuilder token = new StringBuilder();

		return token.append(UUID.randomUUID().toString())
				.append(UUID.randomUUID().toString()).toString();
	}
}
