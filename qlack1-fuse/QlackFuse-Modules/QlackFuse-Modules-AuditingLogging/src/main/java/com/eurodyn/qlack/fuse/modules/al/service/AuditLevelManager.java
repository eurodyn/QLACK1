package com.eurodyn.qlack.fuse.modules.al.service;

import com.eurodyn.qlack.fuse.modules.al.dto.AuditLevelDTO;
import com.eurodyn.qlack.fuse.modules.al.model.AlAuditLevel;

import javax.ejb.Remote;

import java.util.List;

/**
 * Local interface to manage AuditLevelDTO related operations.
 *
 * @author European Dynamics SA
 */
@Remote
public interface AuditLevelManager {

  /**
   * Creates a new audit level
   *
   * @param level The details of the level to be created. The current date is set as createdOn date
   * @return The id of the new level
   */
  String addLevel(AuditLevelDTO level);

  /**
   * To delete audit level by its id.
   *
   * @param levelId - The id of the level to be deleted
   */
  void deleteLevelById(String levelId);

  /**
   * To delete audit level by name.
   *
   * @param levelName - The name of the level to be deleted
   */
  void deleteLevelByName(String levelName);

  /**
   * Updates an audit level
   *
   * @param level The level to be updated. The level's id is used to identify the level, while the rest of the level
   * fields contain the updated information
   */
  void updateLevel(AuditLevelDTO level);

  /**
   * To get audit level by name.
   *
   * @param levelName - String - Name of the level.
   * @return The audit level information
   */
  AuditLevelDTO getAuditLevelByName(String levelName);

  /**
   * To get all audit levels present in system.
   *
   * @return List of AuditLevels.
   */
  List<AuditLevelDTO> listAuditLevels();

  /**
   * To get all audit levels present in the DB
   *
   * @return List of AuditLevels.
   */
  List<AlAuditLevel> getAllAudLevelsFromDB();
}
