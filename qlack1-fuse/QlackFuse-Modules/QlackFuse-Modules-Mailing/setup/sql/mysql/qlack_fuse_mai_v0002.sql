SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

ALTER TABLE mai_email ADD COLUMN from_email VARCHAR(255) NULL AFTER body ;

-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
insert into db_version (dbversion, record_date, author, reason, type, component )
values ( '0002', CURDATE(), 'nmi', 'Added from column to emails', 'STRUCTURE', 'qlack_fuse_mai');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;