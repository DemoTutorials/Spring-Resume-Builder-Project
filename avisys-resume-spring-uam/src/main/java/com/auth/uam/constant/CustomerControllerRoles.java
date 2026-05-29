package com.auth.uam.constant;

import java.util.ArrayList;
import java.util.List;

public class CustomerControllerRoles {

	
	public static final String GETALL = "customer-get-all";
	public static final String SAVE = "customer-save";
	public static final String UPDATE = "customer-update";
	public static final String DELETE = "customer-delete";

	public static List<String> get() {
		List<String> list = new ArrayList<>();
		list.add(GETALL);
		list.add(SAVE);
		list.add(UPDATE);
		list.add(DELETE);
		return list;
	}
	
	
}
