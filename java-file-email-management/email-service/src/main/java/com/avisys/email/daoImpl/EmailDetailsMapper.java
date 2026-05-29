package com.avisys.email.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.avisys.email.model.EmailDetails;

public class EmailDetailsMapper implements RowMapper<EmailDetails> {

	@Override
	public EmailDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

		EmailDetails email = new EmailDetails();

		email.setScheduleName(rs.getString("sched_name"));
		email.setCalendarName(rs.getString("calendar_name"));
		email.setDescription(rs.getString("description"));
		email.setEndTime(rs.getLong("end_time"));
		email.setIsDurable(rs.getBoolean("is_durable"));
		email.setIsNonconcurrent(rs.getBoolean("is_nonconcurrent"));
		email.setIsUpdateData(rs.getBoolean("is_update_data"));
		email.setJobClassName(rs.getString("job_class_name"));
		email.setJobData(rs.getBytes("job_data"));
		email.setJobGroup(rs.getString("job_group"));
		email.setJobName(rs.getString("job_name"));
		email.setMisfireInstr(rs.getInt("misfire_instr"));
		email.setNextFireTime(rs.getLong("next_fire_time"));
		email.setPrevFireTime(rs.getLong("prev_fire_time"));
		email.setPriority(rs.getInt("priority"));
		email.setRequestsRecovery(rs.getBoolean("requests_recovery"));
		email.setScheduleName(rs.getString("sched_name"));
		email.setStartTime(rs.getLong("start_time"));
		email.setTriggerGroup(rs.getString("trigger_group"));
		email.setTriggerName(rs.getString("trigger_name"));
		email.setTriggerState(rs.getString("trigger_state"));
		email.setTriggerType(rs.getString("trigger_type"));

		return email;
	}

}
