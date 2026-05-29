package com.avisys.microservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.avisys.microservice.datatable.dto.ButtonForDataTable;

@Entity
@Table(name = "email_job_data", schema = "email")
public class EmailDetails {
	@Column(name = "sched_name")
	private String scheduleName;

	@Id
	@Column(name = "trigger_name")
	private String triggerName;

	@Column(name = "trigger_group")
	private String triggerGroup;

	@Column(name = "job_name")
	private String jobName;

	@Column(name = "job_group")
	private String jobGroup;

	@Column(name = "description")
	private String description;

	@Column(name = "next_fire_time")
	private long nextFireTime;

	@Column(name = "prev_fire_time")
	private long prevFireTime;

	@Column(name = "priority")
	private int priority;

	@Column(name = "trigger_state")
	private String triggerState;

	@Column(name = "trigger_type")
	private String triggerType;

	@Column(name = "start_time")
	private long startTime;

	@Column(name = "end_time")
	private long endTime;

	@Column(name = "calendar_name")
	private String calendarName;

	@Column(name = "misfire_instr")
	private int misfireInstr;

	@Column(name = "job_data")
	private byte[] jobData;

	@Column(name = "job_class_name")
	private String jobClassName;

	@Column(name = "is_durable")
	private boolean isDurable;

	@Column(name = "is_nonconcurrent")
	private boolean isNonconcurrent;

	@Column(name = "is_update_data")
	private boolean isUpdateData;

	@Column(name = "requests_recovery")
	private boolean requestsRecovery;

	@Transient
	private ButtonForDataTable[] action;

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

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
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

	public ButtonForDataTable[] getAction() {
		return action;
	}

	public void setAction(ButtonForDataTable[] action) {
		this.action = action;
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
