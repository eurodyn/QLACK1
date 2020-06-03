package com.eurodyn.qlack.fuse.modules.al.service.impl;

import com.eurodyn.qlack.fuse.modules.al.dto.AuditLevelDTO;
import com.eurodyn.qlack.fuse.modules.al.service.AuditLevelManager;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Stateless(name = "AuditLevelManagerBean")
public class AuditLevelManagerBean implements AuditLevelManager {

  @PersistenceContext(unitName = "QlackFuse-AuditLogging-PU")
  private EntityManager entityManager;

  @Override
  public String addLevel(AuditLevelDTO level) {
    return null;
  }

  @Override
  public void deleteLevelById(String levelId) {

  }

  @Override
  public void deleteLevelByName(String levelName) {

  }

  @Override
  public void updateLevel(AuditLevelDTO level) {

  }

  @Override
  public AuditLevelDTO getAuditLevelByName(String levelName) {
    return null;
  }

  @Override
  public List<AuditLevelDTO> listAuditLevels() {
    return null;
  }
}
