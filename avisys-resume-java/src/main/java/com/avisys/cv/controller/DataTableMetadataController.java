package com.avisys.cv.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.cv.dto.ColumnMetadata;
import com.avisys.cv.dto.ColumnType;

@RestController
@RequestMapping("data-table-metadata")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DataTableMetadataController {

	private static final DataTableMetadata RECRUITER;
	
	private static final DataTableMetadata ADMIN_DASHBOARD;

	private static final DataTableMetadata HR_DASHBOARD;
	
	static {
		RECRUITER = new DataTableMetadata()
				.addColumnMetadata(new ColumnMetadata("", "personId", "personId", ColumnType.RADIO, 5))
				.addColumnMetadata(new ColumnMetadata("Name", "name", "firstName", ColumnType.TEXT, 20))
				.addColumnMetadata(new ColumnMetadata("Email", "email", "email", ColumnType.TEXT, 25))
				.addColumnMetadata(new ColumnMetadata("Domain", "domain", "domain", ColumnType.TEXT, 20))
				.addColumnMetadata(new ColumnMetadata("Skills", "skills", "skills", ColumnType.TEXT, 30))
				.addColumnMetadata(new ColumnMetadata("Experience", "totalExperience", "totalExperience", ColumnType.TEXT, 10))
				.addColumnMetadata(new ColumnMetadata("Resume", "action", "action", ColumnType.BUTTON, 5));
		
		ADMIN_DASHBOARD = new DataTableMetadata()
				.addColumnMetadata(new ColumnMetadata("", "personId", "personId", ColumnType.RADIO, 5))
				.addColumnMetadata(new ColumnMetadata("Name", "name", "firstName", ColumnType.TEXT, 20))
				.addColumnMetadata(new ColumnMetadata("Email", "email", "email", ColumnType.TEXT, 20))
				.addColumnMetadata(new ColumnMetadata("Domain", "domain", "domain", ColumnType.TEXT, 15))
				.addColumnMetadata(new ColumnMetadata("Skills", "skills", "skills", ColumnType.TEXT, 25))
				.addColumnMetadata(new ColumnMetadata("Experience", "totalExperience", "totalExperience", ColumnType.TEXT, 10))
				.addColumnMetadata(new ColumnMetadata("Company Name", "companyName", "companyName", ColumnType.TEXT, 15))
				.addColumnMetadata(new ColumnMetadata("Resume", "action", "action", ColumnType.BUTTON, 5));
		
		HR_DASHBOARD = new DataTableMetadata()
				.addColumnMetadata(new ColumnMetadata("", "personId", "personId", ColumnType.RADIO, 5))
				.addColumnMetadata(new ColumnMetadata("Name", "name", "firstName", ColumnType.TEXT, 20))
				.addColumnMetadata(new ColumnMetadata("Email", "email", "email", ColumnType.TEXT, 20))
				.addColumnMetadata(new ColumnMetadata("Domain", "domain", "domain", ColumnType.TEXT, 15))
				.addColumnMetadata(new ColumnMetadata("Skills", "skills", "skills", ColumnType.TEXT, 20))
				.addColumnMetadata(new ColumnMetadata("Experience", "totalExperience", "totalExperience", ColumnType.TEXT, 10))
				.addColumnMetadata(new ColumnMetadata("Job Profile", "jobPostCode", "jobPostCode", ColumnType.TEXT, 20))
				.addColumnMetadata(new ColumnMetadata("Resume", "action", "action", ColumnType.BUTTON, 5));
	}

	@GetMapping("recruiter")
	public DataTableMetadata getResume() {
		return RECRUITER;
	}
	
	@GetMapping("dashboard/admin")
	public DataTableMetadata getDashboarAdmin() {
		return ADMIN_DASHBOARD;
	}
	
	@GetMapping("dashboard/hr")
	public DataTableMetadata getDashboarHr() {
		return HR_DASHBOARD;
	}

}
