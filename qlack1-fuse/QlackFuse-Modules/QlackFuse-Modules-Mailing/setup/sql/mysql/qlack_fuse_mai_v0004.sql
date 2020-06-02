SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

ALTER TABLE mai_attachment ADD CONSTRAINT fk_mai_attach_mai_email1 FOREIGN KEY (email_id) REFERENCES mai_email(id);

-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
insert into db_version (dbversion, record_date, author, reason, type, component )
values ( '0004', CURDATE(), 'imous', 'Mailing Attachment updates', 'FIX', 'qlack_fuse_mai');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;