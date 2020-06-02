SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

-- Convert trace_data column from blob to text

ALTER TABLE al_audit_trace
ADD COLUMN trace_data_text TEXT NOT NULL AFTER id;

UPDATE al_audit_trace SET trace_data_text = (SELECT CONVERT(trace_data USING utf8));

ALTER TABLE al_audit_trace DROP COLUMN trace_data;
ALTER TABLE al_audit_trace CHANGE COLUMN trace_data_text trace_data TEXT NOT NULL;

-- Make audit level name unique
ALTER TABLE al_audit_level 
ADD UNIQUE INDEX `name_unique` (`name` ASC) ;

SET AUTOCOMMIT=0;

-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
insert into db_version (dbversion, record_date, author, reason, type, component )
values ( '0004', CURDATE(), 'ckask', 'Convert trace_data to text', 'STRUCTURE', 'qlack_fuse_al');

COMMIT;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
