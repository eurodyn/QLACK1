SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';


-- -----------------------------------------------------
-- Table lex_group
-- -----------------------------------------------------
CREATE  TABLE lex_group (
  id VARCHAR(36) NOT NULL ,
  title VARCHAR(255) NULL ,
  description TEXT NULL ,
  created_on BIGINT NOT NULL ,
  created_by VARCHAR(36) NOT NULL ,
  last_modified_by VARCHAR(36) NULL ,
  last_modified_on BIGINT NULL ,
  PRIMARY KEY (id) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table lex_language
-- -----------------------------------------------------
CREATE  TABLE lex_language (
  id VARCHAR(36) NOT NULL ,
  name VARCHAR(64) NOT NULL ,
  locale VARCHAR(5) NOT NULL ,
  created_on BIGINT NOT NULL ,
  created_by VARCHAR(36) NOT NULL ,
  last_modified_on BIGINT NULL ,
  last_modified_by VARCHAR(36) NULL ,
  active TINYINT(1) NOT NULL ,
  PRIMARY KEY (id) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table lex_template
-- -----------------------------------------------------
CREATE  TABLE lex_template (
  id VARCHAR(36) NOT NULL ,
  name VARCHAR(255) NOT NULL ,
  value TEXT NULL ,
  created_on BIGINT NOT NULL ,
  created_by VARCHAR(36) NOT NULL ,
  last_modified_on BIGINT NULL ,
  last_modified_by VARCHAR(36) NULL ,
  PRIMARY KEY (id) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table lex_key
-- -----------------------------------------------------
CREATE  TABLE lex_key (
  id VARCHAR(36) NOT NULL ,
  name VARCHAR(255) NULL ,
  group_id VARCHAR(36) NULL ,
  created_by VARCHAR(36) NOT NULL ,
  created_on BIGINT NOT NULL ,
  last_modified_on BIGINT NULL ,
  last_modified_by VARCHAR(36) NULL ,
  PRIMARY KEY (id) ,
  INDEX fk_lex_key_lex_group1 (group_id ASC) ,
  UNIQUE INDEX name_UNIQUE (name ASC) ,
  CONSTRAINT fk_lex_key_lex_group1
    FOREIGN KEY (group_id )
    REFERENCES lex_group (id )
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table lex_data
-- -----------------------------------------------------
CREATE  TABLE lex_data (
  id VARCHAR(36) NOT NULL ,
  key_id VARCHAR(36) NOT NULL ,
  value TEXT NULL ,
  language_id VARCHAR(36) NOT NULL ,
  created_by VARCHAR(36) NOT NULL ,
  created_on BIGINT NOT NULL ,
  last_modified_on BIGINT NULL ,
  last_modified_by VARCHAR(36) NULL ,
  approved TINYINT(1) NOT NULL ,
  approved_by VARCHAR(36) NULL ,
  approved_on BIGINT NULL ,
  PRIMARY KEY (id) ,
  INDEX fk_lex_group_has_keys_lex_key1 (key_id ASC) ,
  INDEX fk_lex_data_lex_language1 (language_id ASC) ,
  UNIQUE INDEX key_id_UNIQUE (key_id ASC, language_id ASC) ,
  CONSTRAINT fk_lex_data_lex_key1
    FOREIGN KEY (key_id )
    REFERENCES lex_key (id )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT fk_lex_data_lex_language1
    FOREIGN KEY (language_id )
    REFERENCES lex_language (id )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
insert into db_version (dbversion, record_date, author, reason, type, component )
values ( '0001', CURDATE(), 'ssinh', 'Initial schema', 'STRUCTURE', 'qlack_fuse_lexicon');


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

commit;