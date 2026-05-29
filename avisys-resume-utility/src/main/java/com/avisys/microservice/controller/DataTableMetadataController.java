package com.avisys.microservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.microservice.datatable.dto.ColumnMetadata;
import com.avisys.microservice.datatable.dto.ColumnType;
import com.avisys.microservice.datatable.dto.DataTableMetadata;

@RestController
@RequestMapping("masters/data-table-metadata")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DataTableMetadataController {

	private static final DataTableMetadata PARENT_COMMON_MASTER_METADATA;
	private static final DataTableMetadata CHILD_COMMON_MASTER_METADATA;
	private static final DataTableMetadata EMAIL_METADATA;
	private static final DataTableMetadata EMAIL_TEMPLATE_METADATA;
	private static final DataTableMetadata ERROR_LOG_METADATA;

	static {
		PARENT_COMMON_MASTER_METADATA = new DataTableMetadata()
				.addColumnMetadata(new ColumnMetadata("", "commonMstId", ColumnType.RADIO, 10))
				.addColumnMetadata(new ColumnMetadata("Name", "mstName", ColumnType.TEXT, 25))
				.addColumnMetadata(new ColumnMetadata("Code", "code", ColumnType.TEXT, 25))
				.addColumnMetadata(new ColumnMetadata("Value", "value", ColumnType.TEXT, 25))
				.addColumnMetadata(new ColumnMetadata("Master", "mst", ColumnType.TEXT, 15));

		CHILD_COMMON_MASTER_METADATA = new DataTableMetadata()
				.addColumnMetadata(new ColumnMetadata("", "commonMstId", ColumnType.RADIO, 10))
				.addColumnMetadata(new ColumnMetadata("Name", "mstName", ColumnType.TEXT, 20))
				.addColumnMetadata(new ColumnMetadata("Code", "code", ColumnType.TEXT, 30))
				.addColumnMetadata(new ColumnMetadata("Value", "value", ColumnType.TEXT, 30))
				.addColumnMetadata(new ColumnMetadata("Priority", "priority", ColumnType.TEXT, 10));

		EMAIL_METADATA = new DataTableMetadata()
				.addColumnMetadata(new ColumnMetadata("", "jobName", ColumnType.RADIO, 5))
				.addColumnMetadata(new ColumnMetadata("Job Group", "jobGroup", ColumnType.TEXT, 20))
				.addColumnMetadata(new ColumnMetadata("Description", "description", ColumnType.TEXT, 20))
				.addColumnMetadata(new ColumnMetadata("Start Time", "startTime", ColumnType.TEXT, 20))
				.addColumnMetadata(new ColumnMetadata("Action", "action", ColumnType.BUTTON, 35));

		EMAIL_TEMPLATE_METADATA = new DataTableMetadata()
				.addColumnMetadata(new ColumnMetadata("", "id", ColumnType.RADIO, 10))
				.addColumnMetadata(new ColumnMetadata("Name", "name", ColumnType.TEXT, 30))
				.addColumnMetadata(new ColumnMetadata("Content", "content", ColumnType.TEXT, 30))
				.addColumnMetadata(new ColumnMetadata("Version", "version", ColumnType.TEXT, 30));
		
		ERROR_LOG_METADATA = new DataTableMetadata()
				.addColumnMetadata(new ColumnMetadata("", "id", ColumnType.RADIO, 10))
				.addColumnMetadata(new ColumnMetadata("Module", "module", ColumnType.TEXT, 15))
				.addColumnMetadata(new ColumnMetadata("Message", "message", ColumnType.TEXT, 20))
				.addColumnMetadata(new ColumnMetadata("Date", "dateAt", ColumnType.TEXT, 15))
				.addColumnMetadata(new ColumnMetadata("Description", "detailedDescription", ColumnType.TEXTAREA, 40));

	}

	@GetMapping("parent-common-master")
	public DataTableMetadata getNumberTypeDataTableMetadata() {
		return PARENT_COMMON_MASTER_METADATA;
	}

	@GetMapping("child-common-master")
	public DataTableMetadata getNumberFormatDataTableMetadata() {
		return CHILD_COMMON_MASTER_METADATA;
	}

	@GetMapping("email")
	public DataTableMetadata getInnerNumberFormatDataTableMetadata() {
		return EMAIL_METADATA;
	}

	@GetMapping("email-template")
	public DataTableMetadata getNumberSchemeDataTableMetadata() {
		return EMAIL_TEMPLATE_METADATA;
	}
	
	@GetMapping("error-log")
	public DataTableMetadata getErrorLogMetadata() {
		return ERROR_LOG_METADATA;
	}

}
