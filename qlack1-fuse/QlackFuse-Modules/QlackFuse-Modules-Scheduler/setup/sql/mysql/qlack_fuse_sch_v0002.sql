SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

-- ----------------------------------------------------------------------------------------------------------------------------
-- Quartz tables
-- ----------------------------------------------------------------------------------------------------------------------------

ALTER TABLE sch_FIRED_TRIGGERS ADD SCHED_TIME BIGINT(13);

-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
insert into db_version (dbversion, record_date, author, reason, type, component )
values ( '0002', CURDATE(), 'imo', 'version 2.2.x', 'UPGRADE', 'qlack_fuse_sch');

commit;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;