-- ----------------------------------------------------------------------------------------------------------------------------
-- Quartz tables
-- ----------------------------------------------------------------------------------------------------------------------------

ALTER TABLE sch_FIRED_TRIGGERS ADD SCHED_TIME NUMBER(13);

-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
insert into db_version (dbversion, record_date, author, reason, type, component )
values ( '0002', SYSDATE, 'imo', 'version 2.2.x', 'UPGRADE', 'qlack_fuse_sch');