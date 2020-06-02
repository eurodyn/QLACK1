SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP TABLE IF EXISTS sch_simple_triggers ;
DROP TABLE IF EXISTS sch_simprop_triggers ;
DROP TABLE IF EXISTS sch_cron_triggers;
DROP TABLE IF EXISTS sch_blob_triggers;
DROP TABLE IF EXISTS sch_trigger_listeners;
DROP TABLE IF EXISTS sch_job_listeners ;
DROP TABLE IF EXISTS sch_triggers ;
DROP TABLE IF EXISTS sch_job_details ;
DROP TABLE IF EXISTS sch_calendars;
DROP TABLE IF EXISTS sch_fired_triggers;
DROP TABLE IF EXISTS sch_paused_trigger_grps;
DROP TABLE IF EXISTS sch_scheduler_state;
DROP TABLE IF EXISTS sch_locks;
DROP TABLE IF EXISTS sch_participant;
DROP TABLE IF EXISTS sch_job_group;
DROP TABLE IF EXISTS sch_task_result;

delete from db_version where component='qlack_fuse_sch';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;