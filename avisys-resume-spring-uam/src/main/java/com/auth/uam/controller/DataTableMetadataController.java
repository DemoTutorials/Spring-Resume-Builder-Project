package com.auth.uam.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("data-table-metadata")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DataTableMetadataController {

	private static final DataTableMetadata USER;
	private static final DataTableMetadata ROLE;
	private static final DataTableMetadata PERMISSION;
	private static final DataTableMetadata ENDPOINTS;
	private static final DataTableMetadata JOB_POST;

	static {
		USER = new DataTableMetadata().addColumnMetadata(new ColumnMetadata("", "id", "id", ColumnType.RADIO, 3))
				.addColumnMetadata(new ColumnMetadata("Sr.No.", "srNo", "srNo", ColumnType.TEXT, 5))
				.addColumnMetadata(new ColumnMetadata("User Type", "userType", "userType", ColumnType.TEXT, 12))
				.addColumnMetadata(new ColumnMetadata("User Name", "userName", "userName", ColumnType.TEXT, 26))
				.addColumnMetadata(new ColumnMetadata("Name", "name", "firstName,lastName", ColumnType.TEXT, 22))
				.addColumnMetadata(new ColumnMetadata("Company Name", "companyName", "companyName", ColumnType.TEXT, 18))
				.addColumnMetadata(
						new ColumnMetadata("Mobile Number", "mobileNumber", "mobileNumber", ColumnType.TEXT, 14));

		ROLE = new DataTableMetadata()
				.addColumnMetadata(new ColumnMetadata("", "roleId", "roleId", ColumnType.RADIO, 3))
				.addColumnMetadata(new ColumnMetadata("Sr.No.", "srNo", "srNo", ColumnType.TEXT, 5))
				.addColumnMetadata(
						new ColumnMetadata("Role Name", "roleName", "roleName", ColumnType.TEXT, 30))
				.addColumnMetadata(
						new ColumnMetadata("Description", "description", "description", ColumnType.TEXT, 31))
				.addColumnMetadata(
						new ColumnMetadata("Owner", "ownerName", "ownerName", ColumnType.TEXT, 31));

		PERMISSION = new DataTableMetadata()
				.addColumnMetadata(new ColumnMetadata("", "permissionId", "permissionId", ColumnType.RADIO, 3))
				.addColumnMetadata(new ColumnMetadata("Sr.No.", "srNo", "srNo", ColumnType.TEXT, 5))
				.addColumnMetadata(
						new ColumnMetadata("Permission Name", "permissionName", "permissionName", ColumnType.TEXT, 30))
				.addColumnMetadata(
						new ColumnMetadata("Description", "description", "description", ColumnType.TEXT, 31))
				.addColumnMetadata(
						new ColumnMetadata("Endpoint", "apiEndpoints.path", "apiEndpoints.path", ColumnType.TEXT, 31));

		ENDPOINTS = new DataTableMetadata()
				.addColumnMetadata(new ColumnMetadata("", "id", "id", ColumnType.RADIO, 3))
				.addColumnMetadata(new ColumnMetadata("Sr.No.", "srNo", "srNo", ColumnType.TEXT, 5))
				.addColumnMetadata(
						new ColumnMetadata("Method Type", "methodName", "methodName", ColumnType.TEXT, 30))
				.addColumnMetadata(
						new ColumnMetadata("Controller Name", "controllerName", "controllerName", ColumnType.TEXT, 31))
				.addColumnMetadata(
						new ColumnMetadata("URL", "path", "path", ColumnType.TEXT, 31));
		
		JOB_POST = new DataTableMetadata().addColumnMetadata(new ColumnMetadata("", "jobId", "jobId", ColumnType.RADIO, 5))
				.addColumnMetadata(new ColumnMetadata("Company Name", "companyName", "companyName", ColumnType.TEXT, 18))
				.addColumnMetadata(new ColumnMetadata("Job Profile", "jobProfile", "jobProfile", ColumnType.TEXT, 15))
				.addColumnMetadata(new ColumnMetadata("Job Post Code", "jobPostCode", "jobPostCode", ColumnType.TEXT, 22))
				.addColumnMetadata(new ColumnMetadata("Description", "description", "description", ColumnType.TEXT, 25))
				.addColumnMetadata(new ColumnMetadata("Active", "active", "active", ColumnType.TEXT, 15));
	}

	@GetMapping("user")
	public DataTableMetadata getOrderStatusMapping() {
		return USER;
	}

	@GetMapping("role")
	public DataTableMetadata role() {
		return ROLE;
	}

	@GetMapping("permission")
	public DataTableMetadata permission() {
		return PERMISSION;
	}

	@GetMapping("endpoints")
	public DataTableMetadata endpoints() {
		return ENDPOINTS;
	}

	@GetMapping("job-post")
	public DataTableMetadata jobPost() {
		return JOB_POST;
	}
}
