CREATE OR REPLACE FUNCTION email.after_delete_data()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
	begin

update email.email_job_data 
  set
	sched_name=old."sched_name",
	trigger_name=old."trigger_name",
	trigger_group=old."trigger_group",
	job_group=old."job_group",
	description=old."description",
	next_fire_time=old."next_fire_time",
	prev_fire_time=old."prev_fire_time",
	priority=old."priority",
	trigger_state=old."trigger_state",
	trigger_type=old."trigger_type",
	start_time=old."start_time",
	end_time=old."end_time",
	calendar_name=old."calendar_name" ,
	misfire_instr=old."misfire_instr"
where 
	job_name=old."job_name";

return null;
end;

$function$
;

CREATE OR REPLACE FUNCTION email.insert_data()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
	begin
insert
	into
	email.email_job_data (
	job_name,
	job_data,
	job_class_name ,
	is_durable ,
	is_nonconcurrent ,
	is_update_data ,
	requests_recovery)
select
	jd.job_name,
	jd.job_data,
	jd.job_class_name ,
	jd.is_durable ,
	jd.is_nonconcurrent ,
	jd.is_update_data ,
	jd.requests_recovery
from
	email.qrtz_job_details jd
where
	jd.job_name = new."job_name";

update
	email.email_job_data
set
	sched_name = new."sched_name",
	trigger_name = new."trigger_name",
	trigger_group = new."trigger_group",
	job_group = new."job_group",
	description = new."description",
	next_fire_time = new."next_fire_time",
	prev_fire_time = new."prev_fire_time",
	priority = new."priority",
	trigger_state = new."trigger_state",
	trigger_type = new."trigger_type",
	start_time = new."start_time",
	end_time = new."end_time",
	calendar_name = new."calendar_name" ,
	misfire_instr = new."misfire_instr"
where
	job_name = new."job_name";

return null;
end;

$function$
;

CREATE OR REPLACE FUNCTION email.update_data()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
	BEGIN
	update email.email_job_data 
	  set
		sched_name=new."sched_name",
		trigger_name=new."trigger_name",
		trigger_group=new."trigger_group",
		job_group=new."job_group",
		description=new."description",
		next_fire_time=new."next_fire_time",
		prev_fire_time=new."prev_fire_time",
		priority=new."priority",
		trigger_state=new."trigger_state",
		trigger_type=new."trigger_type",
		start_time=new."start_time",
		end_time=new."end_time",
		calendar_name=new."calendar_name" ,
		misfire_instr=new."misfire_instr"
	where 
		job_name=new."job_name";
	return null;
	END;
$function$
;
