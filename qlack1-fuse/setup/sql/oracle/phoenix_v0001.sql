create table DB_VERSION (
    dbversion VARCHAR2(6 char) NOT NULL,
    RECORD_DATE date,
    AUTHOR varchar2(20 char),
    reason VARCHAR2(1000 char) NULL,
    type varchar2(50 char), 
    component VARCHAR2(255 char),
    PRIMARY KEY (dbversion, component)
)