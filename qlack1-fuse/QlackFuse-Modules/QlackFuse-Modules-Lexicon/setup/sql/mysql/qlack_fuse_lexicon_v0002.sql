SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

ALTER TABLE lex_template ADD COLUMN language_id VARCHAR(36) NOT NULL AFTER last_modified_by ;
ALTER TABLE lex_template 
  ADD CONSTRAINT fk_lg_id
  FOREIGN KEY (language_id )
  REFERENCES lex_language (id )
  ON DELETE CASCADE
  ON UPDATE CASCADE
, ADD INDEX fk_lg_id (language_id ASC) ;


-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
insert into db_version (dbversion, record_date, author, reason, type, component )
values ( '0002', CURDATE(), 'nmi', 'Modified templates', 'STRUCTURE', 'qlack_fuse_lexicon');


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

commit;