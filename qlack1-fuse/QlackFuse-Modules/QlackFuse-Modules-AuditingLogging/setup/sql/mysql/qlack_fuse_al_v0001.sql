SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';


-- -----------------------------------------------------
-- Table al_audit_level
-- -----------------------------------------------------
CREATE  TABLE al_audit_level (
  id VARCHAR(36) NOT NULL ,
  name VARCHAR(10) NOT NULL ,
  description VARCHAR(45) NULL DEFAULT NULL ,
  prin_session_id VARCHAR(40) NULL ,
  created_on BIGINT NULL ,
  PRIMARY KEY (id) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table al_audit_trace
-- -----------------------------------------------------
CREATE  TABLE al_audit_trace (
  trace_data LONGBLOB NOT NULL ,
  id VARCHAR(36) NOT NULL ,
  PRIMARY KEY (id) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table al_audit
-- -----------------------------------------------------
CREATE  TABLE al_audit (
  id VARCHAR(36) NOT NULL ,
  prin_session_id VARCHAR(40) NULL DEFAULT NULL ,
  short_description VARCHAR(2048) NULL DEFAULT NULL ,
  level_id VARCHAR(36) NOT NULL DEFAULT '3' ,
  event VARCHAR(40) NOT NULL ,
  created_on BIGINT NULL ,
  trace_id VARCHAR(36) NULL ,
  PRIMARY KEY (id) ,
  INDEX fk_audit_levelId (level_id ASC) ,
  INDEX fk_audit_eventId (event ASC) ,
  INDEX fk_al_audit_traceId (trace_id ASC) ,
  CONSTRAINT fk_al_audit_levelId
    FOREIGN KEY (level_id )
    REFERENCES al_audit_level (id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_al_audit_traceId
    FOREIGN KEY (trace_id )
    REFERENCES al_audit_trace (id )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET AUTOCOMMIT=0;

INSERT INTO al_audit_level values(0,'trace','Trace level',null,null);
INSERT INTO al_audit_level values(1,'debug','Debug level',null,null);
INSERT INTO al_audit_level values(2,'info','Info level',null,null);
INSERT INTO al_audit_level values(3,'warn','Warn level',null,null);
INSERT INTO al_audit_level values(4,'error','Error level',null,null);
INSERT INTO al_audit_level values(5,'fatal','Fatal level',null,null);

-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
insert into db_version (dbversion, record_date, author, reason, type, component )
values ( '0001', CURDATE(), 'nmix', 'Initial schema', 'STRUCTURE', 'qlack_fuse_al');

COMMIT;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
