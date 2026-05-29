package com.auth.uam.utils;

import java.util.UUID;

public class UUIDGenerate {

	public static String generateToken() {
		StringBuilder token = new StringBuilder();

		return token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString()).toString();
	}

	public static String generateUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static String generateUUID7() {
		int uuid = (int)((Math.random() * (9999999 - 1000000)) + 1000000);
		return uuid+"";
	}
}
