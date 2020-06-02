SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';


-- -----------------------------------------------------
-- Table mai_email
-- -----------------------------------------------------
CREATE  TABLE mai_email (
  id VARCHAR(36) NOT NULL ,
  subject VARCHAR(45) NULL ,
  body LONGTEXT NULL ,
  to_emails VARCHAR(1024) NULL ,
  cc_emails VARCHAR(1024) NULL ,
  bcc_emails VARCHAR(1024) NULL ,
  status VARCHAR(1) NULL ,
  server_response VARCHAR(1024) NULL ,
  email_type VARCHAR(64) NULL ,
  date_sent BIGINT(20) NULL DEFAULT NULL ,
  PRIMARY KEY (id) )
ENGINE = InnoDB;

ALTER TABLE mai_email ADD INDEX idx_me_datesent (date_sent DESC) ;


-- -----------------------------------------------------
-- Table mai_attachment
-- -----------------------------------------------------
CREATE  TABLE mai_attachment (
  id VARCHAR(36) NOT NULL ,
  email_id VARCHAR(36) NULL ,
  filename VARCHAR(500) NULL ,
  content_type VARCHAR(100) NULL DEFAULT NULL ,
  data LONGBLOB NULL ,
  format VARCHAR(45) NULL COMMENT 'binary or text' ,
  PRIMARY KEY (id) ,
  CONSTRAINT fk_mai_attachment_mai_email1
    FOREIGN KEY (email_id )
    REFERENCES mai_email (id )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX idx_attachment_mai_email1 ON mai_attachment (email_id ASC) ;


-- -----------------------------------------------------
-- Table mai_distribution_list
-- -----------------------------------------------------
CREATE  TABLE mai_distribution_list (
  id VARCHAR(36) NOT NULL ,
  list_name VARCHAR(45) NULL ,
  description VARCHAR(45) NULL ,
  PRIMARY KEY (id) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table mai_template
-- -----------------------------------------------------
CREATE  TABLE mai_template (
  id VARCHAR(36) NOT NULL ,
  name VARCHAR(45) NULL ,
  description VARCHAR(255) NULL ,
  body TEXT NULL ,
  PRIMARY KEY (id) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table mai_newsletter
-- -----------------------------------------------------
CREATE  TABLE mai_newsletter (
  id VARCHAR(36) NOT NULL ,
  description VARCHAR(200) NULL ,
  distribution_list_id VARCHAR(36) NOT NULL ,
  template_id VARCHAR(36) NULL ,
  body TEXT NULL ,
  status VARCHAR(10) NULL ,
  PRIMARY KEY (id) ,
  CONSTRAINT fk_mai_newsletter_mai_distribution_list1
    FOREIGN KEY (distribution_list_id )
    REFERENCES mai_distribution_list (id )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_mai_newsletter_mai_template1
    FOREIGN KEY (template_id )
    REFERENCES mai_template (id )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX idx_newsletter_mai_distribution_list1 ON mai_newsletter (distribution_list_id ASC) ;

CREATE INDEX idx_newsletter_mai_template1 ON mai_newsletter (template_id ASC) ;


-- -----------------------------------------------------
-- Table mai_contact
-- -----------------------------------------------------
CREATE  TABLE mai_contact (
  id VARCHAR(36) NOT NULL ,
  userid VARCHAR(36) NULL ,
  contact_email VARCHAR(45) NULL ,
  is_valid_email_id BIT(1) NULL ,
  is_default_email BIT(1) NULL ,
  PRIMARY KEY (id) )
ENGINE = InnoDB;

CREATE UNIQUE INDEX userid_UNIQUE ON mai_contact (userid ASC) ;


-- -----------------------------------------------------
-- Table mai_newsletter_schedule
-- -----------------------------------------------------
CREATE  TABLE mai_newsletter_schedule (
  id VARCHAR(36) NOT NULL ,
  start_date BIGINT NULL ,
  newsletter_id VARCHAR(36) NULL ,
  recurring_option VARCHAR(10) NULL ,
  PRIMARY KEY (id) ,
  CONSTRAINT fk_mai_newsletter_schedule_mai_newsletter1
    FOREIGN KEY (newsletter_id )
    REFERENCES mai_newsletter (id )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX idx_newsletter_schedule_mai_newsletter1 ON mai_newsletter_schedule (newsletter_id ASC) ;

CREATE UNIQUE INDEX newsletter_id_UNIQUE ON mai_newsletter_schedule (newsletter_id ASC) ;


-- -----------------------------------------------------
-- Table mai_distr_list_has_contact
-- -----------------------------------------------------
CREATE  TABLE mai_distr_list_has_contact (
  distribution_list_id VARCHAR(36) NOT NULL ,
  contact_id VARCHAR(36) NOT NULL ,
  Active BIT(1) NULL ,
  PRIMARY KEY (distribution_list_id, contact_id) ,
  CONSTRAINT fk_mai_distribution_list_has_mai_contact_mai_distribution_list1
    FOREIGN KEY (distribution_list_id )
    REFERENCES mai_distribution_list (id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_mai_distribution_list_has_mai_contact_mai_contact1
    FOREIGN KEY (contact_id )
    REFERENCES mai_contact (id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX idx_distribution_list_has_mai_contact_mai_distribution_list1 ON mai_distr_list_has_contact (distribution_list_id ASC) ;

CREATE INDEX idx_distribution_list_has_mai_contact_mai_contact1 ON mai_distr_list_has_contact (contact_id ASC) ;


-- -----------------------------------------------------
-- Table mai_internal_messages
-- -----------------------------------------------------
CREATE  TABLE mai_internal_messages (
  id VARCHAR(36) NOT NULL ,
  message TEXT NOT NULL ,
  mail_from VARCHAR(36) NOT NULL ,
  mail_to VARCHAR(36) NOT NULL ,
  date_sent BIGINT(20) NULL DEFAULT NULL ,
  date_received BIGINT(20) NULL DEFAULT NULL ,
  status VARCHAR(7) NULL DEFAULT NULL ,
  subject VARCHAR(100) NOT NULL ,
  delete_type VARCHAR(1) NOT NULL ,
  PRIMARY KEY (id) )
ENGINE = InnoDB;

ALTER TABLE mai_internal_messages ADD INDEX idx_mim_mailtotype (mail_to ASC, delete_type ASC) ;
ALTER TABLE mai_internal_messages ADD INDEX idx_mim_mf_dt (mail_from ASC, delete_type ASC) ;


-- -----------------------------------------------------
-- Table mai_internal_attachment
-- -----------------------------------------------------
CREATE  TABLE mai_internal_attachment (
  id VARCHAR(36) NOT NULL ,
  messages_id VARCHAR(36) NOT NULL ,
  filename VARCHAR(500) NULL ,
  content_type VARCHAR(100) NULL DEFAULT NULL ,
  data LONGBLOB NULL ,
  format VARCHAR(45) NULL COMMENT 'binary or text' ,
  PRIMARY KEY (id) ,
  CONSTRAINT fk_mai_internal_attachment_mai_messages1
    FOREIGN KEY (messages_id )
    REFERENCES mai_internal_messages (id )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX idx_internal_attachment_mai_messages1 ON mai_internal_attachment (messages_id ASC) ;


ALTER TABLE mai_distr_list_has_contact DROP FOREIGN KEY fk_mai_distribution_list_has_mai_contact_mai_contact1 , DROP FOREIGN KEY fk_mai_distribution_list_has_mai_contact_mai_distribution_list1 ;
ALTER TABLE mai_distr_list_has_contact
  ADD CONSTRAINT fk_mai_distribution_list_has_mai_contact_mai_contact1
  FOREIGN KEY (contact_id )
  REFERENCES mai_contact (id )
  ON DELETE CASCADE
  ON UPDATE NO ACTION,
  ADD CONSTRAINT fk_mai_distribution_list_has_mai_contact_mai_distribution_list1
  FOREIGN KEY (distribution_list_id )
  REFERENCES mai_distribution_list (id )
  ON DELETE CASCADE
  ON UPDATE NO ACTION;

ALTER TABLE mai_email ADD COLUMN server_response_date BIGINT(20) NULL  AFTER date_sent ;
ALTER TABLE mai_email ADD COLUMN added_on_date BIGINT(20) NOT NULL  AFTER server_response_date ;
ALTER TABLE mai_email ADD COLUMN retries TINYINT NOT NULL  AFTER added_on_date ;
ALTER TABLE mai_email CHANGE COLUMN status status VARCHAR(32) NULL DEFAULT NULL  ;
ALTER TABLE mai_email CHANGE COLUMN retries tries TINYINT NOT NULL  ;
ALTER TABLE mai_email CHANGE COLUMN subject subject VARCHAR(254) NULL DEFAULT NULL  ;

ALTER TABLE mai_internal_attachment CHANGE COLUMN filename filename VARCHAR(254) NULL DEFAULT NULL  , CHANGE COLUMN content_type content_type VARCHAR(254) NULL DEFAULT NULL  ;

ALTER TABLE mai_attachment CHANGE COLUMN filename filename VARCHAR(254) NULL DEFAULT NULL  , CHANGE COLUMN content_type content_type VARCHAR(254) NULL DEFAULT NULL  ;
ALTER TABLE mai_attachment DROP FOREIGN KEY fk_mai_attachment_mai_email1 ;
ALTER TABLE mai_attachment DROP COLUMN format , ADD COLUMN attachment_size BIGINT NULL  AFTER data , CHANGE COLUMN email_id email_id VARCHAR(36) NOT NULL  , CHANGE COLUMN filename filename VARCHAR(254) NOT NULL  , CHANGE COLUMN content_type content_type VARCHAR(254) NOT NULL  , CHANGE COLUMN data data LONGBLOB NOT NULL  ,
  ADD CONSTRAINT fk_mai_attachment_mai_email1
  FOREIGN KEY (email_id )
  REFERENCES mai_email (id )
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE mai_contact DROP COLUMN is_default_email , DROP COLUMN is_valid_email_id , DROP COLUMN userid , ADD COLUMN first_name VARCHAR(254) NULL  AFTER email , ADD COLUMN last_name VARCHAR(254) NULL  AFTER first_name , ADD COLUMN locale VARCHAR(5) NULL  AFTER last_name , CHANGE COLUMN contact_email email VARCHAR(45) NOT NULL
, DROP INDEX userid_UNIQUE ;
ALTER TABLE mai_contact ADD COLUMN user_id VARCHAR(36) NULL  AFTER locale ;

ALTER TABLE mai_distr_list_has_contact DROP COLUMN Active ;

ALTER TABLE mai_distribution_list ADD COLUMN created_on BIGINT NULL  AFTER description , ADD COLUMN created_by VARCHAR(254) NULL  AFTER created_on , CHANGE COLUMN list_name list_name VARCHAR(45) NOT NULL  ;
ALTER TABLE mai_newsletter_schedule DROP FOREIGN KEY fk_mai_newsletter_schedule_mai_newsletter1 ;
ALTER TABLE mai_newsletter_schedule DROP COLUMN recurring_option , DROP COLUMN start_date , ADD COLUMN scheduledFor BIGINT NOT NULL  AFTER newsletter_id , ADD COLUMN sent TINYINT(1) NOT NULL  AFTER scheduledFor , ADD COLUMN sentOn BIGINT NULL  AFTER sent , CHANGE COLUMN newsletter_id newsletter_id VARCHAR(36) NOT NULL  ,
  ADD CONSTRAINT fk_mai_newsletter_schedule_mai_newsletter1
  FOREIGN KEY (newsletter_id )
  REFERENCES mai_newsletter (id )
  ON DELETE CASCADE
  ON UPDATE CASCADE
, DROP INDEX newsletter_id_UNIQUE ;

CREATE  TABLE mai_newsletter_has_dl (
  newsletter_id VARCHAR(36) NOT NULL ,
  dlist_id VARCHAR(36) NOT NULL ,
  INDEX fk_nhdl_news_id (newsletter_id ASC) ,
  PRIMARY KEY (newsletter_id, dlist_id) ,
  INDEX fk_njdl_dlist_id (dlist_id ASC) ,
  CONSTRAINT fk_nhdl_news_id
    FOREIGN KEY (newsletter_id )
    REFERENCES mai_newsletter (id )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT fk_njdl_dlist_id
    FOREIGN KEY (dlist_id )
    REFERENCES mai_distribution_list (id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

ALTER TABLE mai_newsletter DROP FOREIGN KEY fk_mai_newsletter_mai_distribution_list1 ;
ALTER TABLE mai_newsletter DROP COLUMN status , DROP COLUMN body , DROP COLUMN distribution_list_id , ADD COLUMN title VARCHAR(254) NOT NULL  AFTER template_id , CHANGE COLUMN description description VARCHAR(512) NULL DEFAULT NULL
, DROP INDEX idx_newsletter_mai_distribution_list1 ;
ALTER TABLE mai_newsletter DROP FOREIGN KEY fk_mai_newsletter_mai_template1 ;
ALTER TABLE mai_newsletter CHANGE COLUMN template_id template_id VARCHAR(36) NOT NULL  ,
  ADD CONSTRAINT fk_mai_newsletter_mai_template1
  FOREIGN KEY (template_id )
  REFERENCES mai_template (id )
  ON DELETE CASCADE
  ON UPDATE CASCADE;

-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
insert into db_version (dbversion, record_date, author, reason, type, component )
values ( '0001', CURDATE(), 'cshin', 'Initial schema', 'STRUCTURE', 'qlack_fuse_mai');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;