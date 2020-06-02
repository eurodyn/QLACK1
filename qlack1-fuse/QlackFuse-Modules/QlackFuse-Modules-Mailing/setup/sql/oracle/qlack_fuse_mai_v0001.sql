-- -----------------------------------------------------
-- Table mai_email
-- -----------------------------------------------------

CREATE  TABLE &owner_name.."MAI_EMAIL" (
  ID VARCHAR2(36 CHAR) NOT NULL ENABLE,
  subject VARCHAR2(254 CHAR),
  BODY CLOB ,
  from_email VARCHAR2(255 CHAR),
  to_emails VARCHAR2(1024 CHAR),
  cc_emails VARCHAR2(1024 CHAR),
  bcc_emails VARCHAR2(1024 CHAR),
  status VARCHAR2(32 CHAR),
  server_response VARCHAR2(1024 CHAR),
  email_type VARCHAR2(64 CHAR),
  date_sent NUMBER(19,0),
  server_response_date NUMBER(19, 0),
  added_on_date NUMBER(19, 0) NOT NULL,
  tries NUMBER(3, 0) NOT NULL,  
CONSTRAINT   mai_email_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

CREATE INDEX IDX_ME_DATESENT ON &owner_name.."MAI_EMAIL"(date_sent DESC) TABLESPACE &index_tablespace;

CREATE INDEX TRIES_STATUS_INDEX ON &owner_name.."MAI_EMAIL"(tries ASC, status ASC) TABLESPACE &index_tablespace;

-- -----------------------------------------------------
-- Table mai_attachment
-- -----------------------------------------------------
CREATE  TABLE &owner_name.."MAI_ATTACHMENT" (
  ID VARCHAR2(36 CHAR) NOT NULL ,
  email_id VARCHAR2(36 CHAR) NOT NULL,
  filename VARCHAR2(254 CHAR) NOT NULL,
  content_type VARCHAR2(254 CHAR) NOT NULL,
  DATA BLOB NOT NULL,
  attachment_size NUMBER(19, 0),
  CONSTRAINT mai_attachment_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;
  
ALTER TABLE &owner_name.."MAI_ATTACHMENT" ADD (
  CONSTRAINT fk_mai_attach_mai_email1
 FOREIGN KEY (email_id)
 REFERENCES &owner_name.."MAI_EMAIL" (ID) ON DELETE CASCADE); 
 
CREATE INDEX IDX_ATTACHMENT_MAI_EMAIL1 ON &owner_name.."MAI_ATTACHMENT"(email_id ASC) TABLESPACE &index_tablespace;


-- -----------------------------------------------------
-- Table mai_distribution_list
-- -----------------------------------------------------
CREATE  TABLE &owner_name.."MAI_DISTRIBUTION_LIST" (
  id VARCHAR2(36 CHAR) NOT NULL ,
  list_name VARCHAR2(45 CHAR) NOT NULL,
  description VARCHAR2(45 CHAR),
  created_on NUMBER(19, 0),
  created_by VARCHAR2(254 CHAR),
  
  CONSTRAINT mai_distribution_list_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;


-- -----------------------------------------------------
-- Table mai_template
-- -----------------------------------------------------
CREATE  TABLE &owner_name.."MAI_TEMPLATE" (
  id VARCHAR2(36 CHAR) NOT NULL ,
  name VARCHAR2(45 CHAR),
  description VARCHAR2(255 CHAR),
  body CLOB,
  CONSTRAINT mai_template_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;


-- -----------------------------------------------------
-- Table mai_newsletter
-- -----------------------------------------------------
CREATE  TABLE &owner_name.."MAI_NEWSLETTER" (
  id VARCHAR2(36 CHAR) NOT NULL ,
  description VARCHAR2(512 CHAR) ,
  template_id VARCHAR2(36 CHAR) NOT NULL,
  title VARCHAR(254) NOT NULL,
CONSTRAINT mai_newsletter_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

ALTER TABLE &owner_name.."MAI_NEWSLETTER" ADD (
 CONSTRAINT fk_mai_newsletter_template1
 FOREIGN KEY (template_id)
 REFERENCES &owner_name.."MAI_TEMPLATE" (ID) ON DELETE CASCADE
 );

CREATE INDEX IDX_NEWSLETTER_MAI_TEMPLATE1 ON &owner_name.."MAI_NEWSLETTER"(template_id ASC) TABLESPACE &index_tablespace;


-- -----------------------------------------------------
-- Table mai_contact
-- -----------------------------------------------------
CREATE  TABLE &owner_name.."MAI_CONTACT" (
  id VARCHAR2(36 CHAR) NOT NULL ,
  email VARCHAR2(45 CHAR) NOT NULL,
  first_name VARCHAR2(254 CHAR),
  last_name VARCHAR2(254 CHAR),
  locale VARCHAR2(5 CHAR),
  user_id VARCHAR2(36 CHAR),
  CONSTRAINT mai_contact_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;

-- CREATE UNIQUE INDEX userid_UNIQUE ON &owner_name.."MAI_CONTACT" (userid ASC) TABLESPACE &index_tablespace;


-- -----------------------------------------------------
-- Table mai_newsletter_schedule
-- -----------------------------------------------------
CREATE  TABLE &owner_name.."MAI_NEWSLETTER_SCHEDULE" (
  id VARCHAR2(36 CHAR) NOT NULL ,
  newsletter_id VARCHAR2(36 CHAR) NOT NULL,
  scheduled_for NUMBER(19, 0) NOT NULL,
  sent NUMBER(1, 0) NOT NULL,
  sent_on NUMBER(19, 0),
CONSTRAINT mai_newsletter_schedule_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;  
  
ALTER TABLE &owner_name.."MAI_NEWSLETTER_SCHEDULE" ADD (
  CONSTRAINT fk_mai_newsl_schedule_newslet1
 FOREIGN KEY (newsletter_id)
 REFERENCES &owner_name.."MAI_NEWSLETTER" (ID) ON DELETE CASCADE
 );  

CREATE INDEX IDX_NEWSL_SCHEDULE_NEWSLETTER1 ON &owner_name.."MAI_NEWSLETTER_SCHEDULE" (newsletter_id ASC) TABLESPACE &index_tablespace;

-- -----------------------------------------------------
-- Table mai_distr_list_has_contact
-- -----------------------------------------------------
CREATE  TABLE &owner_name.."MAI_DISTR_LIST_HAS_CONTACT" (
  distribution_list_id VARCHAR2(36 CHAR) NOT NULL ,
  contact_id VARCHAR2(36 CHAR) NOT NULL ,
  CONSTRAINT mai_distr_list_has_contact_pk PRIMARY KEY (distribution_list_id, contact_id) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;  
  
ALTER TABLE &owner_name.."MAI_DISTR_LIST_HAS_CONTACT" ADD (
  CONSTRAINT fk_mai_distrib_list_cont_list1
 FOREIGN KEY (distribution_list_id)
 REFERENCES &owner_name.."MAI_DISTRIBUTION_LIST" (ID) ON DELETE CASCADE,
 CONSTRAINT fk_mai_distr_list_ct_contact1
 FOREIGN KEY (contact_id)
 REFERENCES &owner_name.."MAI_CONTACT" (ID) ON DELETE CASCADE
 );    
  
CREATE INDEX IDX_DISTRIB_LIST_CONT_LIST1 ON &owner_name.."MAI_DISTR_LIST_HAS_CONTACT"(distribution_list_id ASC) TABLESPACE &index_tablespace;

CREATE INDEX IDX_DISTRIB_LIST_CONT_CONTACT1 ON &owner_name.."MAI_DISTR_LIST_HAS_CONTACT"(contact_id ASC) TABLESPACE &index_tablespace;


-- -----------------------------------------------------
-- Table mai_internal_messages
-- -----------------------------------------------------
CREATE  TABLE &owner_name.."MAI_INTERNAL_MESSAGES" (
  id VARCHAR2(36 CHAR) NOT NULL ,
  message CLOB NOT NULL ,
  mail_from VARCHAR2(36 CHAR) NOT NULL ,
  mail_to VARCHAR2(36 CHAR) NOT NULL ,
  date_sent NUMBER(19, 0)  ,
  date_received NUMBER(19, 0) ,
  status VARCHAR2(7 CHAR) ,
  subject VARCHAR2(100 CHAR) NOT NULL ,
  delete_type VARCHAR2(1 CHAR) NOT NULL ,
  CONSTRAINT mai_internal_messages_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;  


CREATE INDEX IDX_MIM_MAILTOTYPE ON &owner_name.."MAI_INTERNAL_MESSAGES"(mail_to ASC, delete_type ASC) TABLESPACE &index_tablespace;
CREATE INDEX IDX_MIM_MF_DT ON &owner_name.."MAI_INTERNAL_MESSAGES"(mail_from ASC, delete_type ASC) TABLESPACE &index_tablespace;



-- -----------------------------------------------------
-- Table mai_internal_attachment
-- -----------------------------------------------------
CREATE  TABLE &owner_name.."MAI_INTERNAL_ATTACHMENT" (
  id VARCHAR2(36) NOT NULL ,
  messages_id VARCHAR2(36) NOT NULL ,
  filename VARCHAR2(254) ,
  content_type VARCHAR2(254) ,
  data BLOB ,
  format VARCHAR2(45) ,
  CONSTRAINT mai_internal_attachment_pk PRIMARY KEY (ID) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;  

ALTER TABLE &owner_name.."MAI_INTERNAL_ATTACHMENT" ADD (
  CONSTRAINT FK_INT_ATTACHMENT_MESSAGES1
 FOREIGN KEY (messages_id)
 REFERENCES &owner_name.."MAI_INTERNAL_MESSAGES" (ID) ON DELETE CASCADE
 );


CREATE INDEX IDX_INT_ATTACHMENT_MESSAGES1 ON &owner_name.."MAI_INTERNAL_ATTACHMENT"(messages_id ASC) TABLESPACE &index_tablespace;



-- -----------------------------------------------------
-- Table mai_newsletter_has_dl
-- -----------------------------------------------------
CREATE  TABLE &owner_name.."MAI_NEWSLETTER_HAS_DL" (
  newsletter_id VARCHAR2(36 CHAR) NOT NULL ,
  dlist_id VARCHAR2(36 CHAR) NOT NULL ,
  CONSTRAINT mai_newsletter_has_dl_pk PRIMARY KEY (newsletter_id, dlist_id) USING INDEX TABLESPACE &index_tablespace
)
TABLESPACE &main_tablespace;  
 
ALTER TABLE &owner_name.."MAI_NEWSLETTER_HAS_DL" ADD (
  CONSTRAINT FK_NHDL_NEWS_ID
 FOREIGN KEY (newsletter_id)
 REFERENCES &owner_name.."MAI_NEWSLETTER" (ID) ON DELETE CASCADE,
  CONSTRAINT FK_NJDL_DLIST_ID
 FOREIGN KEY (dlist_id)
 REFERENCES &owner_name.."MAI_DISTRIBUTION_LIST" (ID) ON DELETE CASCADE
 ); 


-- -----------------------------------------------------
-- dbversion update
-- -----------------------------------------------------
insert into &owner_name.."DB_VERSION" (dbversion, record_date, author, reason, type, component )
values ( '0001', SYSDATE, 'European Dynamics', 'Initial schema', 'STRUCTURE', 'qlack_fuse_mai');
