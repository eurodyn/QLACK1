-- ----------------------------------------------------------------------------------------------------------------------------
-- Quartz tables
-- ----------------------------------------------------------------------------------------------------------------------------
CREATE TABLE sch_JOB_DETAILS(
	SCHED_NAME VARCHAR2(120) NOT NULL,
	JOB_NAME VARCHAR2(200) NOT NULL,
	JOB_GROUP VARCHAR2(200) NOT NULL,
	DESCRIPTION VARCHAR2(250) NULL,
	JOB_CLASS_NAME VARCHAR2(250) NOT NULL,
	IS_DURABLE VARCHAR2(1) NOT NULL,
	IS_NONCONCURRENT VARCHAR2(1) NOT NULL,
	IS_UPDATE_DATA VARCHAR2(1) NOT NULL,
	REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
	JOB_DATA BLOB NULL,
CONSTRAINT sch_job_details_pk PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

CREATE TABLE sch_TRIGGERS (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    JOB_NAME  VARCHAR2(200) NOT NULL, 
    JOB_GROUP VARCHAR2(200) NOT NULL,
    DESCRIPTION VARCHAR2(250) NULL,
    NEXT_FIRE_TIME NUMBER(13) NULL,
    PREV_FIRE_TIME NUMBER(13) NULL,
    PRIORITY NUMBER(13) NULL,
    TRIGGER_STATE VARCHAR2(16) NOT NULL,
    TRIGGER_TYPE VARCHAR2(8) NOT NULL,
    START_TIME NUMBER(13) NOT NULL,
    END_TIME NUMBER(13) NULL,
    CALENDAR_NAME VARCHAR2(200) NULL,
    MISFIRE_INSTR NUMBER(2) NULL,
    JOB_DATA BLOB NULL,
    CONSTRAINT sch_TRIGGERS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) USING INDEX TABLESPACE &index_tablespace --,
)
TABLESPACE &main_tablespace;

CREATE INDEX sch_TRIGGER_TO_JOBS_FK ON sch_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP) TABLESPACE &index_tablespace;

ALTER TABLE sch_TRIGGERS ADD (
    CONSTRAINT sch_TRIGGER_TO_JOBS_FK
    FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
    REFERENCES sch_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
);


CREATE TABLE sch_SIMPLE_TRIGGERS (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    REPEAT_COUNT NUMBER(7) NOT NULL,
    REPEAT_INTERVAL NUMBER(12) NOT NULL,
    TIMES_TRIGGERED NUMBER(10) NOT NULL,
    CONSTRAINT sch_SIMPLE_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

ALTER TABLE sch_SIMPLE_TRIGGERS ADD (
	CONSTRAINT sch_SIMPLE_TRIG_TO_TRIG_FK
	FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
	REFERENCES sch_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE sch_CRON_TRIGGERS ( 

    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    CRON_EXPRESSION VARCHAR2(120) NOT NULL,
    TIME_ZONE_ID VARCHAR2(80),
    CONSTRAINT sch_CRON_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) USING INDEX TABLESPACE &index_tablespace
) 
TABLESPACE &main_tablespace;

ALTER TABLE sch_CRON_TRIGGERS ADD (
	CONSTRAINT sch_CRON_TRIG_TO_TRIG_FK
	FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
	REFERENCES sch_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE sch_SIMPROP_TRIGGERS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    STR_PROP_1 VARCHAR2(512) NULL,
    STR_PROP_2 VARCHAR2(512) NULL,
    STR_PROP_3 VARCHAR2(512) NULL,
    INT_PROP_1 NUMBER(10) NULL,
    INT_PROP_2 NUMBER(10) NULL,
    LONG_PROP_1 NUMBER(13) NULL,
    LONG_PROP_2 NUMBER(13) NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR2(1) NULL,
    BOOL_PROP_2 VARCHAR2(1) NULL,
    CONSTRAINT sch_SIMPROP_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

ALTER TABLE sch_SIMPROP_TRIGGERS ADD (
	CONSTRAINT sch_SIMPROP_TRIG_TO_TRIG_FK
	FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
	REFERENCES sch_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE sch_BLOB_TRIGGERS (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    BLOB_DATA BLOB NULL,
    CONSTRAINT sch_BLOB_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

ALTER TABLE sch_BLOB_TRIGGERS ADD (
	CONSTRAINT BLOB_TRIG_TO_TRIG_FK
	FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
	REFERENCES sch_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE sch_CALENDARS (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    CALENDAR_NAME  VARCHAR2(200) NOT NULL, 
    CALENDAR BLOB NOT NULL,
    CONSTRAINT sch_CALENDARS_PK PRIMARY KEY (SCHED_NAME,CALENDAR_NAME) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

CREATE TABLE sch_PAUSED_TRIGGER_GRPS (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_GROUP  VARCHAR2(200) NOT NULL, 
    CONSTRAINT sch_PAUSED_TRIG_GRPS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

CREATE TABLE sch_FIRED_TRIGGERS 
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    ENTRY_ID VARCHAR2(95) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    FIRED_TIME NUMBER(13) NOT NULL,
    PRIORITY NUMBER(13) NOT NULL,
    STATE VARCHAR2(16) NOT NULL,
    JOB_NAME VARCHAR2(200) NULL,
    JOB_GROUP VARCHAR2(200) NULL,
    IS_NONCONCURRENT VARCHAR2(1) NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NULL,
    CONSTRAINT sch_FIRED_TRIGGER_PK PRIMARY KEY (SCHED_NAME,ENTRY_ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

CREATE TABLE sch_SCHEDULER_STATE (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
    CHECKIN_INTERVAL NUMBER(13) NOT NULL,
    CONSTRAINT sch_SCHEDULER_STATE_PK PRIMARY KEY (SCHED_NAME,INSTANCE_NAME) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

CREATE TABLE sch_LOCKS (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    LOCK_NAME  VARCHAR2(40) NOT NULL, 
    CONSTRAINT sch_LOCKS_PK PRIMARY KEY (SCHED_NAME,LOCK_NAME) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

CREATE INDEX IDX_sch_J_REQ_RECOVERY ON sch_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY)  TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_J_GRP ON sch_JOB_DETAILS(SCHED_NAME,JOB_GROUP) TABLESPACE &index_tablespace;

CREATE INDEX IDX_sch_T_JG ON sch_TRIGGERS(SCHED_NAME,JOB_GROUP) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_T_C ON sch_TRIGGERS(SCHED_NAME,CALENDAR_NAME) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_T_G ON sch_TRIGGERS(SCHED_NAME,TRIGGER_GROUP) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_T_STATE ON sch_TRIGGERS(SCHED_NAME,TRIGGER_STATE) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_T_N_STATE ON sch_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_T_N_G_STATE ON sch_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_T_NEXT_FIRE_TIME ON sch_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_T_NFT_ST ON sch_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_T_NFT_MISFIRE ON sch_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_T_NFT_ST_MISFIRE ON sch_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_T_NFT_ST_MISFIRE_GRP ON sch_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE) TABLESPACE &index_tablespace;

CREATE INDEX IDX_sch_FT_TRIG_INST_NAME ON sch_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_FT_INST_JOB_REQ_RCVRY ON sch_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_FT_J_G ON sch_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_FT_JG ON sch_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_FT_T_G ON sch_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) TABLESPACE &index_tablespace;
CREATE INDEX IDX_sch_FT_TG ON sch_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP) TABLESPACE &index_tablespace;

-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
insert into db_version (dbversion, record_date, author, reason, type, component )
values ( '0001', SYSDATE, 'kperp', 'initial schema', 'STRUCTURE', 'qlack_fuse_sch');
