package com.eurodyn.qlack.fuse.modules.al.util;

import com.eurodyn.qlack.fuse.modules.al.dto.AuditLevelDTO;
import com.eurodyn.qlack.fuse.modules.al.dto.AuditLogDTO;
import com.eurodyn.qlack.fuse.modules.al.model.AlAudit;
import com.eurodyn.qlack.fuse.modules.al.model.AlAuditLevel;
import com.eurodyn.qlack.fuse.modules.al.model.AlAuditTrace;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is utility class used to convert Audit related models/list data to DTO and vice-a-versa.
 *
 * @author European Dynamics SA
 */
public final class ConverterUtil {

  private static final Logger logger = Logger.getLogger(ConverterUtil.class.getSimpleName());

  private ConverterUtil() {
  }

  /**
   * Convert AuditLevelDTO DTO to AlAuditLevel Model.
   *
   * @return the AlAuditLevel
   */
  public static AlAuditLevel convertToAuditLevelModel(AuditLevelDTO level) {
    logger.log(Level.FINEST, "Converting audit level DTO to audit level ''{0}''.", level);
    AlAuditLevel alLevel = null;
    if (null != level) {
      alLevel = new AlAuditLevel();
      alLevel.setId(level.getId());
      alLevel.setName(level.getName());
      alLevel.setDescription(level.getDescription());
      alLevel.setPrinSessionId(level.getPrinSessionId());
      if (level.getCreatedOn() != null) {
        alLevel.setCreatedOn(level.getCreatedOn().getTime());
      }
    }
    return alLevel;
  }

  /**
   * Convert AlAuditLevel Model to AuditLevelDTO DTO
   *
   * @return AuditLevelDTO
   */
  public static AuditLevelDTO convertToAuditLevelDTO(AlAuditLevel alLevel) {
    logger.log(Level.FINEST, "Converting audit level model to audit level ''{0}''.", alLevel);
    AuditLevelDTO level = null;
    if (null != alLevel) {
      level = new AuditLevelDTO();
      level.setId(alLevel.getId());
      level.setName(alLevel.getName());
      level.setDescription(alLevel.getDescription());
      if (alLevel.getCreatedOn() != null) {
        level.setCreatedOn(new Date(alLevel.getCreatedOn()));
      }
      level.setPrinSessionId(alLevel.getPrinSessionId());
    }
    return level;
  }

  /**
   * Convert AuditLogDTO DTO to AlAudit model
   *
   * @return AlAudit
   */
  public static AlAudit convertToAuditLogModel(AuditLogDTO log) {
    logger.log(Level.FINEST, "Converting audit log DTO to audit log ''{0}''.", log);
    AlAudit alLog = null;
    if (null != log) {
      alLog = new AlAudit();
      alLog.setTraceId(getAuditTraceObject(log));
      if (null != log.getCreatedOn()) {
        alLog.setCreatedOn(log.getCreatedOn().getTime());
      }
      alLog.setEvent(log.getEvent());
      alLog.setPrinSessionId(log.getPrinSessionId());
      alLog.setShortDescription(log.getShortDescription());
      alLog.setLevelId(ConverterUtil.convertToAuditLevelModel(
          null == log.getLevel() ? null : new AuditLevelDTO(log.getLevel())));
      alLog.setReferenceId(log.getReferenceId());
      alLog.setGroupName(log.getGroupName());
      alLog.setLang(log.getLang());
    }
    return alLog;
  }

  /**
   * Convert AlAudit model to AuditLogDTO DTO
   *
   * @return AuditLogDTO
   */
  public static AuditLogDTO convertToAuditLogDTO(AlAudit log) {
    logger.log(Level.FINEST, "Converting audit level model to audit ''{0}''.", log);
    AuditLogDTO alLog = null;
    if (null != log) {
      alLog = new AuditLogDTO();
      if (null != log.getTraceId()) {
        alLog.setTraceData(log.getTraceId().getTraceData());
      }
      if (null != log.getCreatedOn()) {
        alLog.setCreatedOn(new Date(log.getCreatedOn()));
      }
      alLog.setEvent(log.getEvent());
      alLog.setPrinSessionId(log.getPrinSessionId());
      alLog.setShortDescription(log.getShortDescription());
      alLog.setLevel(log.getLevelId().getName());
      alLog.setReferenceId(log.getReferenceId());
      alLog.setGroupName(log.getGroupName());
      alLog.setLang(log.getLang());
    }
    return alLog;
  }

  /**
   * Get AuditLogDTO List from AlAudit List
   *
   * @return List<AuditLogDTO>
   */
  public static List<AuditLogDTO> convertToAuditLogList(List<AlAudit> list) {
    logger.log(Level.FINEST, "Converting audit log model list to audit log DTO list.");
    if (list == null) {
      return new ArrayList<>();
    }
    List<AuditLogDTO> aList = new ArrayList<>(list.size());
    for (AlAudit auditLog : list) {
      aList.add(ConverterUtil.convertToAuditLogDTO(auditLog));
    }
    return aList;
  }

  /**
   * Get AuditLevelDTO List from AlAuditLevel List
   *
   * @return List<AuditLevelDTO>
   */
  public static List<AuditLevelDTO> convertToAuditLevelList(List<AlAuditLevel> list) {
    logger.log(Level.FINEST, "Converting audit level model list to audit level DTO list ''{0}''.",
        list);
    if (list == null) {
      return new ArrayList<>();
    }
    List<AuditLevelDTO> aList = new ArrayList<>(list.size());
    for (AlAuditLevel auditLog : list) {
      aList.add(ConverterUtil.convertToAuditLevelDTO(auditLog));
    }
    return aList;
  }

  private static AlAuditTrace getAuditTraceObject(AuditLogDTO log) {
    AlAuditTrace trace = null;
    String obj = log.getTraceData();
    if (null != obj) {
      trace = new AlAuditTrace();
      trace.setTraceData(log.getTraceData());
    }
    logger.log(Level.FINEST, "Trace ''{0}''.", log);
    return trace;
  }
}
