package com.avisys.email.model;

//-- email.email_job_data definition
//
//-- Drop table
//
//-- DROP TABLE email.email_job_data;
//
//CREATE TABLE email.email_job_data (
//	sched_name varchar(120) NULL,
//	trigger_name varchar(200) NULL,
//	trigger_group varchar(200) NULL,
//	job_name varchar(200) NULL,
//	job_group varchar(200) NULL,
//	description varchar(250) NULL,
//	next_fire_time int8 NULL,
//	prev_fire_time int8 NULL,
//	priority int4 NULL,
//	trigger_state varchar(16) NULL,
//	trigger_type varchar(8) NULL,
//	start_time int8 NULL,
//	end_time int8 NULL,
//	calendar_name varchar(200) NULL,
//	misfire_instr int2 NULL,
//	job_data bytea NULL,
//	job_class_name varchar(250) NULL,
//	is_durable bool NULL,
//	is_nonconcurrent bool NULL,
//	is_update_data bool NULL,
//	requests_recovery bool NULL
//);
public class EmailDetails {

	private String scheduleName;
	private String triggerName;
	private String triggerGroup;
	private String jobName;
	private String jobGroup;
	private String description;
	private long nextFireTime;
	private long prevFireTime;
	private int priority;
	private String triggerState;
	private String triggerType;
	private long startTime;
	private long endTime;
	private String calendarName;
	private int misfireInstr;
	private byte[] jobData;
	private String jobClassName;
	private boolean isDurable;
	private boolean isNonconcurrent;
	private boolean isUpdateData;
	private boolean requestsRecovery;

	public EmailDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(long nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public long getPrevFireTime() {
		return prevFireTime;
	}

	public void setPrevFireTime(long prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getTriggerState() {
		return triggerState;
	}

	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	public int getMisfireInstr() {
		return misfireInstr;
	}

	public void setMisfireInstr(int misfireInstr) {
		this.misfireInstr = misfireInstr;
	}

	public byte[] getJobData() {
		return jobData;
	}

	public void setJobData(byte[] jobData) {
		this.jobData = jobData;
	}

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

	public boolean getIsDurable() {
		return isDurable;
	}

	public void setIsDurable(boolean isDurable) {
		this.isDurable = isDurable;
	}

	public boolean getIsNonconcurrent() {
		return isNonconcurrent;
	}

	public void setIsNonconcurrent(boolean isNonconcurrent) {
		this.isNonconcurrent = isNonconcurrent;
	}

	public boolean getIsUpdateData() {
		return isUpdateData;
	}

	public void setIsUpdateData(boolean isUpdateData) {
		this.isUpdateData = isUpdateData;
	}

	public boolean getRequestsRecovery() {
		return requestsRecovery;
	}

	public void setRequestsRecovery(boolean requestsRecovery) {
		this.requestsRecovery = requestsRecovery;
	}

	@Override
	public String toString() {
		return "EmailDetails [scheduleName=" + scheduleName + ", triggerName=" + triggerName + ", triggerGroup="
				+ triggerGroup + ", jobName=" + jobName + ", jobGroup=" + jobGroup + ", description=" + description
				+ ", nextFireTime=" + nextFireTime + ", prevFireTime=" + prevFireTime + ", priority=" + priority
				+ ", triggerState=" + triggerState + ", triggerType=" + triggerType + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", calendarName=" + calendarName + ", misfireInstr=" + misfireInstr
				+ ", jobData=" + jobData + ", jobClassName=" + jobClassName + ", isDurable=" + isDurable
				+ ", isNonconcurrent=" + isNonconcurrent + ", isUpdateData=" + isUpdateData + ", requestsRecovery="
				+ requestsRecovery + "]";
	}

}
