-- -----------------------------------------------------
-- TABLE AL_AUDIT_LEVEL
-- -----------------------------------------------------
CREATE  TABLE &owner_name.."AL_AUDIT_LEVEL" (
  ID VARCHAR2(36 CHAR) NOT NULL ENABLE,
  NAME VARCHAR2(10 CHAR) NOT NULL ENABLE,
  DESCRIPTION VARCHAR2(45 CHAR),
  PRIN_SESSION_ID VARCHAR2(40 CHAR),
  CREATED_ON NUMBER(19,0),
 CONSTRAINT AL_AUDIT_LEVEL_PK PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;


-- -----------------------------------------------------
-- TABLE AL_AUDIT_TRACE
-- -----------------------------------------------------
CREATE  TABLE &owner_name.."AL_AUDIT_TRACE" (
  TRACE_DATA BLOB NOT NULL ENABLE,
  ID VARCHAR2(36 CHAR) NOT NULL ENABLE,
  CONSTRAINT AL_AUDIT_TRACE_PK PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

-- -----------------------------------------------------
-- TABLE AL_AUDIT
-- -----------------------------------------------------
CREATE  TABLE &owner_name.."AL_AUDIT" (
  ID VARCHAR2(36 CHAR) NOT NULL ENABLE,
  PRIN_SESSION_ID VARCHAR2(256 CHAR),
  SHORT_DESCRIPTION VARCHAR2(2048 CHAR),
  LEVEL_ID VARCHAR2(36 CHAR) DEFAULT '3' NOT NULL  ENABLE,
  EVENT   VARCHAR2(40 CHAR) NOT NULL ENABLE,
  CREATED_ON NUMBER(19,0),
  TRACE_ID VARCHAR2(36 CHAR),
CONSTRAINT AL_AUDIT_PK PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

CREATE INDEX FK_AUDIT_LEVELID ON &owner_name.."AL_AUDIT"(LEVEL_ID ASC) TABLESPACE &index_tablespace;
CREATE INDEX FK_AUDIT_EVENTID ON &owner_name.."AL_AUDIT"(EVENT ASC) TABLESPACE &index_tablespace;
CREATE INDEX FK_AL_AUDIT_TRACEID ON &owner_name.."AL_AUDIT"(TRACE_ID ASC) TABLESPACE &index_tablespace;

ALTER TABLE &owner_name.."AL_AUDIT"
ADD(
  CONSTRAINT FK_AL_AUDIT_LEVELID
  FOREIGN KEY (LEVEL_ID)
  REFERENCES &owner_name.."AL_AUDIT_LEVEL"(ID),
  CONSTRAINT FK_AL_AUDIT_TRACEID
  FOREIGN KEY (TRACE_ID)
  REFERENCES &owner_name.."AL_AUDIT_TRACE"(ID)
  ON DELETE CASCADE
  );

INSERT INTO &owner_name.."AL_AUDIT_LEVEL" VALUES (0, 'trace', 'Trace level', NULL, NULL);
INSERT INTO &owner_name.."AL_AUDIT_LEVEL" VALUES (1, 'debug', 'Debug level', NULL, NULL);
INSERT INTO &owner_name.."AL_AUDIT_LEVEL" VALUES (2, 'info', 'Info level', NULL, NULL);
INSERT INTO &owner_name.."AL_AUDIT_LEVEL" VALUES (3, 'warn', 'Warn level', NULL, NULL);
INSERT INTO &owner_name.."AL_AUDIT_LEVEL" VALUES (4, 'error', 'Error level', NULL, NULL);
INSERT INTO &owner_name.."AL_AUDIT_LEVEL" VALUES (5, 'fatal', 'Fatal level', NULL, NULL);

-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
INSERT INTO &owner_name.."DB_VERSION"
            (dbversion, record_date, author, reason, TYPE,
             component
            )
     VALUES ('0001', SYSDATE, 'ehond', 'Initial schema', 'STRUCTURE',
             'qlack_fuse_al'
            );

COMMIT;