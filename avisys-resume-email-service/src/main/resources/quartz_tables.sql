-- DROP SCHEMA email_test;

CREATE SCHEMA email_test;
-- email_test.email_job_data definition

-- Drop table

-- DROP TABLE email_test.email_job_data;

CREATE TABLE email_test.email_job_data (
	sched_name varchar(120) NULL,
	trigger_name varchar(200) NULL,
	trigger_group varchar(200) NULL,
	job_name varchar(200) NULL,
	job_group varchar(200) NULL,
	description varchar(250) NULL,
	next_fire_time int8 NULL,
	prev_fire_time int8 NULL,
	priority int4 NULL,
	trigger_state varchar(16) NULL,
	trigger_type varchar(8) NULL,
	start_time int8 NULL,
	end_time int8 NULL,
	calendar_name varchar(200) NULL,
	misfire_instr int2 NULL,
	job_data bytea NULL,
	job_class_name varchar(250) NULL,
	is_durable bool NULL,
	is_nonconcurrent bool NULL,
	is_update_data bool NULL,
	requests_recovery bool NULL
);


-- email_test.qrtz_calendars definition

-- Drop table

-- DROP TABLE email_test.qrtz_calendars;

CREATE TABLE email_test.qrtz_calendars (
	sched_name varchar(120) NOT NULL,
	calendar_name varchar(200) NOT NULL,
	calendar bytea NOT NULL,
	CONSTRAINT qrtz_calendars_pkey PRIMARY KEY (sched_name, calendar_name)
);


-- email_test.qrtz_fired_triggers definition

-- Drop table

-- DROP TABLE email_test.qrtz_fired_triggers;

CREATE TABLE email_test.qrtz_fired_triggers (
	sched_name varchar(120) NOT NULL,
	entry_id varchar(95) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	instance_name varchar(200) NOT NULL,
	fired_time int8 NOT NULL,
	sched_time int8 NOT NULL,
	priority int4 NOT NULL,
	state varchar(16) NOT NULL,
	job_name varchar(200) NULL,
	job_group varchar(200) NULL,
	is_nonconcurrent bool NULL,
	requests_recovery bool NULL,
	CONSTRAINT qrtz_fired_triggers_pkey PRIMARY KEY (sched_name, entry_id)
);
CREATE INDEX idx_qrtz_ft_inst_job_req_rcvry ON email_test.qrtz_fired_triggers USING btree (sched_name, instance_name, requests_recovery);
CREATE INDEX idx_qrtz_ft_j_g ON email_test.qrtz_fired_triggers USING btree (sched_name, job_name, job_group);
CREATE INDEX idx_qrtz_ft_jg ON email_test.qrtz_fired_triggers USING btree (sched_name, job_group);
CREATE INDEX idx_qrtz_ft_t_g ON email_test.qrtz_fired_triggers USING btree (sched_name, trigger_name, trigger_group);
CREATE INDEX idx_qrtz_ft_tg ON email_test.qrtz_fired_triggers USING btree (sched_name, trigger_group);
CREATE INDEX idx_qrtz_ft_trig_inst_name ON email_test.qrtz_fired_triggers USING btree (sched_name, instance_name);


-- email_test.qrtz_job_details definition

-- Drop table

-- DROP TABLE email_test.qrtz_job_details;

CREATE TABLE email_test.qrtz_job_details (
	sched_name varchar(120) NOT NULL,
	job_name varchar(200) NOT NULL,
	job_group varchar(200) NOT NULL,
	description varchar(250) NULL,
	job_class_name varchar(250) NOT NULL,
	is_durable bool NOT NULL,
	is_nonconcurrent bool NOT NULL,
	is_update_data bool NOT NULL,
	requests_recovery bool NOT NULL,
	job_data bytea NULL,
	CONSTRAINT qrtz_job_details_pkey PRIMARY KEY (sched_name, job_name, job_group)
);
CREATE INDEX idx_qrtz_j_grp ON email_test.qrtz_job_details USING btree (sched_name, job_group);
CREATE INDEX idx_qrtz_j_req_recovery ON email_test.qrtz_job_details USING btree (sched_name, requests_recovery);


-- email_test.qrtz_locks definition

-- Drop table

-- DROP TABLE email_test.qrtz_locks;

CREATE TABLE email_test.qrtz_locks (
	sched_name varchar(120) NOT NULL,
	lock_name varchar(40) NOT NULL,
	CONSTRAINT qrtz_locks_pkey PRIMARY KEY (sched_name, lock_name)
);


-- email_test.qrtz_paused_trigger_grps definition

-- Drop table

-- DROP TABLE email_test.qrtz_paused_trigger_grps;

CREATE TABLE email_test.qrtz_paused_trigger_grps (
	sched_name varchar(120) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	CONSTRAINT qrtz_paused_trigger_grps_pkey PRIMARY KEY (sched_name, trigger_group)
);


-- email_test.qrtz_scheduler_state definition

-- Drop table

-- DROP TABLE email_test.qrtz_scheduler_state;

CREATE TABLE email_test.qrtz_scheduler_state (
	sched_name varchar(120) NOT NULL,
	instance_name varchar(200) NOT NULL,
	last_checkin_time int8 NOT NULL,
	checkin_interval int8 NOT NULL,
	CONSTRAINT qrtz_scheduler_state_pkey PRIMARY KEY (sched_name, instance_name)
);


-- email_test.qrtz_triggers definition

-- Drop table

-- DROP TABLE email_test.qrtz_triggers;

CREATE TABLE email_test.qrtz_triggers (
	sched_name varchar(120) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	job_name varchar(200) NOT NULL,
	job_group varchar(200) NOT NULL,
	description varchar(250) NULL,
	next_fire_time int8 NULL,
	prev_fire_time int8 NULL,
	priority int4 NULL,
	trigger_state varchar(16) NOT NULL,
	trigger_type varchar(8) NOT NULL,
	start_time int8 NOT NULL,
	end_time int8 NULL,
	calendar_name varchar(200) NULL,
	misfire_instr int2 NULL,
	job_data bytea NULL,
	CONSTRAINT qrtz_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group),
	CONSTRAINT qrtz_triggers_sched_name_job_name_job_group_fkey FOREIGN KEY (sched_name,job_name,job_group) REFERENCES email_test.qrtz_job_details(sched_name,job_name,job_group)
);
CREATE INDEX idx_qrtz_t_c ON email_test.qrtz_triggers USING btree (sched_name, calendar_name);
CREATE INDEX idx_qrtz_t_g ON email_test.qrtz_triggers USING btree (sched_name, trigger_group);
CREATE INDEX idx_qrtz_t_j ON email_test.qrtz_triggers USING btree (sched_name, job_name, job_group);
CREATE INDEX idx_qrtz_t_jg ON email_test.qrtz_triggers USING btree (sched_name, job_group);
CREATE INDEX idx_qrtz_t_n_g_state ON email_test.qrtz_triggers USING btree (sched_name, trigger_group, trigger_state);
CREATE INDEX idx_qrtz_t_n_state ON email_test.qrtz_triggers USING btree (sched_name, trigger_name, trigger_group, trigger_state);
CREATE INDEX idx_qrtz_t_next_fire_time ON email_test.qrtz_triggers USING btree (sched_name, next_fire_time);
CREATE INDEX idx_qrtz_t_nft_misfire ON email_test.qrtz_triggers USING btree (sched_name, misfire_instr, next_fire_time);
CREATE INDEX idx_qrtz_t_nft_st ON email_test.qrtz_triggers USING btree (sched_name, trigger_state, next_fire_time);
CREATE INDEX idx_qrtz_t_nft_st_misfire ON email_test.qrtz_triggers USING btree (sched_name, misfire_instr, next_fire_time, trigger_state);
CREATE INDEX idx_qrtz_t_nft_st_misfire_grp ON email_test.qrtz_triggers USING btree (sched_name, misfire_instr, next_fire_time, trigger_group, trigger_state);
CREATE INDEX idx_qrtz_t_state ON email_test.qrtz_triggers USING btree (sched_name, trigger_state);


-- email_test.qrtz_blob_triggers definition

-- Drop table

-- DROP TABLE email_test.qrtz_blob_triggers;

CREATE TABLE email_test.qrtz_blob_triggers (
	sched_name varchar(120) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	blob_data bytea NULL,
	CONSTRAINT qrtz_blob_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group),
	CONSTRAINT qrtz_blob_triggers_sched_name_trigger_name_trigger_group_fkey FOREIGN KEY (sched_name,trigger_name,trigger_group) REFERENCES email_test.qrtz_triggers(sched_name,trigger_name,trigger_group)
);


-- email_test.qrtz_cron_triggers definition

-- Drop table

-- DROP TABLE email_test.qrtz_cron_triggers;

CREATE TABLE email_test.qrtz_cron_triggers (
	sched_name varchar(120) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	cron_expression varchar(120) NOT NULL,
	time_zone_id varchar(80) NULL,
	CONSTRAINT qrtz_cron_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group),
	CONSTRAINT qrtz_cron_triggers_sched_name_trigger_name_trigger_group_fkey FOREIGN KEY (sched_name,trigger_name,trigger_group) REFERENCES email_test.qrtz_triggers(sched_name,trigger_name,trigger_group)
);


-- email_test.qrtz_simple_triggers definition

-- Drop table

-- DROP TABLE email_test.qrtz_simple_triggers;

CREATE TABLE email_test.qrtz_simple_triggers (
	sched_name varchar(120) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	repeat_count int8 NOT NULL,
	repeat_interval int8 NOT NULL,
	times_triggered int8 NOT NULL,
	CONSTRAINT qrtz_simple_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group),
	CONSTRAINT qrtz_simple_triggers_sched_name_trigger_name_trigger_group_fkey FOREIGN KEY (sched_name,trigger_name,trigger_group) REFERENCES email_test.qrtz_triggers(sched_name,trigger_name,trigger_group)
);


-- email_test.qrtz_simprop_triggers definition

-- Drop table

-- DROP TABLE email_test.qrtz_simprop_triggers;

CREATE TABLE email_test.qrtz_simprop_triggers (
	sched_name varchar(120) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	str_prop_1 varchar(512) NULL,
	str_prop_2 varchar(512) NULL,
	str_prop_3 varchar(512) NULL,
	int_prop_1 int4 NULL,
	int_prop_2 int4 NULL,
	long_prop_1 int8 NULL,
	long_prop_2 int8 NULL,
	dec_prop_1 numeric(13, 4) NULL,
	dec_prop_2 numeric(13, 4) NULL,
	bool_prop_1 bool NULL,
	bool_prop_2 bool NULL,
	CONSTRAINT qrtz_simprop_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group),
	CONSTRAINT qrtz_simprop_triggers_sched_name_trigger_name_trigger_grou_fkey FOREIGN KEY (sched_name,trigger_name,trigger_group) REFERENCES email_test.qrtz_triggers(sched_name,trigger_name,trigger_group)
);



CREATE OR REPLACE FUNCTION email_test.after_delete_data()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
	begin

update email_test.email_job_data 
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

CREATE OR REPLACE FUNCTION email_test.insert_data()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
	begin
insert
	into
	email_test.email_job_data (
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
	email_test.qrtz_job_details jd
where
	jd.job_name = new."job_name";

update
	email_test.email_job_data
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

CREATE OR REPLACE FUNCTION email_test.update_data()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
	BEGIN
	update email_test.email_job_data 
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


-- Table Triggers

create trigger on_delete_add_tgr after
delete
    on
    email_test.qrtz_triggers for each row execute function email_test.after_delete_data();
create trigger on_insert_tgr after
insert
    on
    email_test.qrtz_triggers for each row execute function email_test.insert_data();
create trigger on_update_tgr after
update
    on
    email_test.qrtz_triggers for each row execute function email_test.update_data();
