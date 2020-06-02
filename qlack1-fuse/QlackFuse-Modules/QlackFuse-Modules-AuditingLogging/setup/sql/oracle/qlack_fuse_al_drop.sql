drop table al_audit;
drop table al_audit_trace;
drop table al_audit_level;

delete from db_version where component='qlack_fuse_al';
