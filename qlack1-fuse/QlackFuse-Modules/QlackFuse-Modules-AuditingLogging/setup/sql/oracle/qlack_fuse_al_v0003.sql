CREATE INDEX FK_AUDIT_REF_ID ON &owner_name.."AL_AUDIT"(REFERENCE_ID) TABLESPACE &index_tablespace;

-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
INSERT INTO &owner_name.."DB_VERSION"
            (dbversion, record_date, author,
             reason,
             TYPE, component
            )
     VALUES ('0003', SYSDATE, 'asam',
             'Add index on REFERENCE_ID column of AL_AUDIT table',
             'STRUCTURE', 'qlack_fuse_al'
            );

COMMIT ;