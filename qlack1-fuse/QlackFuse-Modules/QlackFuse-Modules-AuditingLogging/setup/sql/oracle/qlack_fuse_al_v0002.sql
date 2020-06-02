

ALTER TABLE &owner_name.."AL_AUDIT" ADD (
   "REFERENCE_ID" VARCHAR2(36 CHAR),
   "GROUP_NAME" VARCHAR2(255 CHAR)
);

-- Convert trace_data column from blob to clob

ALTER TABLE &owner_name.."AL_AUDIT_TRACE"
ADD (trace_data_clob  CLOB);

--UPDATE al_audit_trace
--   SET trace_data_clob = blob_to_clob (trace_data);
--COMMIT ;


ALTER TABLE &owner_name.."AL_AUDIT_TRACE" DROP COLUMN trace_data;
ALTER TABLE &owner_name.."AL_AUDIT_TRACE" RENAME COLUMN trace_data_clob TO trace_data;
ALTER TABLE &owner_name.."AL_AUDIT_TRACE" MODIFY(trace_data NOT NULL);


-- Make audit level name unique
CREATE UNIQUE INDEX name_unique ON  &owner_name.."AL_AUDIT_LEVEL" (NAME ASC) TABLESPACE &index_tablespace;

-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
INSERT INTO &owner_name.."DB_VERSION"
            (dbversion, record_date, author,
             reason,
             TYPE, component
            )
     VALUES ('0002', SYSDATE, 'ehond',
             'Add extra columns for referencing logged record and group of logged record - Convert trace_data to text',
             'STRUCTURE', 'qlack_fuse_al'
            );

COMMIT ;