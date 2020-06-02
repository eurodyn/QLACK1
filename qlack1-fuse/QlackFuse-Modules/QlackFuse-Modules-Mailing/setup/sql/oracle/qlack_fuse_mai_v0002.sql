-- -----------------------------------------------------
-- Table mai_email
-- -----------------------------------------------------

ALTER TABLE mai_email ADD retries NUMBER(3,0) DEFAULT 3 NOT NULL;

-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
insert into &owner_name.."DB_VERSION" (dbversion, record_date, author, reason, type, component )
values ( '0002', SYSDATE, 'European Dynamics', 'Add retries field', 'UPDATE', 'qlack_fuse_mai');
