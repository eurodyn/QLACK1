-- -----------------------------------------------------
-- Table lex_group
-- -----------------------------------------------------
CREATE  TABLE lex_group (
  ID VARCHAR2(36 CHAR) NOT NULL ENABLE,
  title VARCHAR2(255 CHAR) ,
  description CLOB ,
  created_on NUMBER(19,0) NOT NULL ENABLE ,
  created_by VARCHAR2(36 CHAR) NOT NULL ENABLE ,
  last_modified_by VARCHAR2(36 CHAR) ,
  last_modified_on NUMBER(19,0) ,
  CONSTRAINT lex_group_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;



-- -----------------------------------------------------
-- Table lex_language
-- -----------------------------------------------------
CREATE  TABLE lex_language (
  ID VARCHAR2(36 CHAR) NOT NULL ENABLE ,
  NAME VARCHAR2(64 CHAR) NOT NULL ENABLE ,
  locale VARCHAR2(5 CHAR) NOT NULL ENABLE ,
  created_on NUMBER(19,0) NOT NULL ENABLE ,
  created_by VARCHAR2(36 CHAR) NOT NULL ENABLE ,
  last_modified_on NUMBER(19,0) ,
  last_modified_by VARCHAR2(36 CHAR) ,
  active NUMBER(1,0) NOT NULL ENABLE ,
  CONSTRAINT lex_language_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;



-- -----------------------------------------------------
-- Table lex_template
-- -----------------------------------------------------
CREATE  TABLE lex_template (
  ID VARCHAR2(36 CHAR) NOT NULL ENABLE ,
  NAME VARCHAR2(255  CHAR) NOT NULL ENABLE ,
  VALUE CLOB ,
  created_on NUMBER(19,0) NOT NULL ENABLE ,
  created_by VARCHAR2(36 CHAR) NOT NULL ENABLE ,
  last_modified_on NUMBER(19,0) ,
  last_modified_by VARCHAR2(36 CHAR) ,
  language_id VARCHAR2(36 CHAR) NOT NULL ENABLE ,
  CONSTRAINT lex_template_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

ALTER TABLE lex_template
ADD(
  CONSTRAINT fk_lg_id
  FOREIGN KEY (language_id)
  REFERENCES lex_language(ID)
  ON DELETE CASCADE);

CREATE INDEX fk_lg_id ON lex_template(language_id ASC) TABLESPACE &index_tablespace;


-- -----------------------------------------------------
-- Table lex_key
-- -----------------------------------------------------
CREATE  TABLE lex_key (
  ID VARCHAR2(36 CHAR) NOT NULL ENABLE ,
  NAME VARCHAR2(255 CHAR) ,
  GROUP_ID VARCHAR2(36 CHAR) ,
  created_by VARCHAR2(36 CHAR) NOT NULL ENABLE ,
  created_on NUMBER(19,0) NOT NULL ENABLE ,
  last_modified_on NUMBER(19,0) ,
  last_modified_by VARCHAR2(36 CHAR) ,
  CONSTRAINT lex_key_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

CREATE INDEX fk_lex_key_lex_group1 ON lex_key(GROUP_ID ASC) TABLESPACE &index_tablespace;

CREATE UNIQUE INDEX name_unique ON lex_key(NAME ASC) TABLESPACE &index_tablespace;

ALTER TABLE lex_key
ADD(
    CONSTRAINT fk_lex_key_lex_group1
    FOREIGN KEY (GROUP_ID )
    REFERENCES lex_group (ID )
    ON DELETE SET NULL);



-- -----------------------------------------------------
-- Table lex_data
-- -----------------------------------------------------
CREATE  TABLE lex_data (
  ID VARCHAR2(36 CHAR) NOT NULL ENABLE ,
  key_id VARCHAR2(36 CHAR) NOT NULL ENABLE ,
  VALUE CLOB ,
  language_id VARCHAR2(36 CHAR) NOT NULL ENABLE ,
  created_by VARCHAR2(36 CHAR) NOT NULL ENABLE ,
  created_on NUMBER(19,0) NOT NULL ENABLE ,
  last_modified_on NUMBER(19,0) ,
  last_modified_by VARCHAR2(36 CHAR) ,
  approved NUMBER(1,0) NOT NULL ENABLE ,
  approved_by VARCHAR2(36 CHAR) ,
  approved_on NUMBER(19,0) ,
  CONSTRAINT lex_data_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

CREATE INDEX fk_lex_group_has_keys_lex_key1 ON lex_data(key_id ASC) TABLESPACE &index_tablespace;

CREATE INDEX fk_lex_data_lex_language1 ON lex_data(language_id ASC) TABLESPACE &index_tablespace;

CREATE UNIQUE INDEX key_id_unique ON lex_data(key_id ASC, language_id ASC) TABLESPACE &index_tablespace;

ALTER TABLE lex_data
ADD(
    CONSTRAINT fk_lex_data_lex_key1
    FOREIGN KEY (key_id )
    REFERENCES lex_key (ID )
    ON DELETE CASCADE,
    CONSTRAINT fk_lex_data_lex_language1
      FOREIGN KEY (language_id )
      REFERENCES lex_language (ID )
      ON DELETE CASCADE
    );



-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
INSERT INTO db_version
            (dbversion, record_date, author, reason, TYPE,
             component
            )
     VALUES ('0001', SYSDATE, 'ehond', 'Initial schema', 'STRUCTURE',
             'qlack_core_lexicon'
            );