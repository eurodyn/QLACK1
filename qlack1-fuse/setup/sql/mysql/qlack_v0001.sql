CREATE TABLE IF NOT EXISTS db_version (
    dbversion VARCHAR(6) NOT NULL,
    record_date DATE,
    author VARCHAR(20),
    reason VARCHAR(1000) NULL,
    type VARCHAR(50), 
    component VARCHAR(255),
    PRIMARY KEY (dbversion, component)
)
ENGINE = InnoDB;