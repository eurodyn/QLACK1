package com.eurodyn.qlack.fuse.modules.al.init;

import com.eurodyn.qlack.fuse.modules.al.dto.AuditLevelDTO;
import com.eurodyn.qlack.fuse.modules.al.dto.SearchDTO;
import com.eurodyn.qlack.fuse.modules.al.dto.SortDTO;
import com.eurodyn.qlack.fuse.modules.al.enums.AuditLogColumns;
import com.eurodyn.qlack.fuse.modules.al.enums.SearchOperator;
import com.eurodyn.qlack.fuse.modules.al.enums.SortOperator;
import com.eurodyn.qlack.fuse.modules.al.model.AlAudit;
import com.eurodyn.qlack.fuse.modules.al.model.AlAuditLevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class InitTestValues {

  public AuditLevelDTO createAuditLevelDTO() {
    AuditLevelDTO auditLevelDTO = new AuditLevelDTO();
    auditLevelDTO.setId("369caf53-41a9-4818-9a98-a4f9563da6d7");
    auditLevelDTO.setName("Test name");
    auditLevelDTO.setDescription("Test Description");
    auditLevelDTO.setCreatedOn(new Date());
    auditLevelDTO.setPrinSessionId("13909cea-fce0-4711-a782-32e48d53d8a7");

    return auditLevelDTO;
  }

  public AlAuditLevel createAlAuditLevel() {
    AlAuditLevel alAuditLevel = new AlAuditLevel();
    alAuditLevel.setId("369caf53-41a9-4818-9a98-a4f9563da6d7");
    alAuditLevel.setName("Test name");
    alAuditLevel.setDescription("Test Description");
    alAuditLevel.setCreatedOn(new Date().getTime());
    alAuditLevel.setPrinSessionId("13909cea-fce0-4711-a782-32e48d53d8a7");

    return alAuditLevel;
  }

  public List<AlAuditLevel> createAlAuditLevels() {
    List<AlAuditLevel> alAuditLevels = new ArrayList<>();
    alAuditLevels.add(createAlAuditLevel());

    return alAuditLevels;
  }

  public AlAudit createAlAudit() {
    AlAudit alAudit = new AlAudit();
    alAudit.setId("360125c6-2508-4951-8190-696319da6b5a");
    alAudit.setEvent("Test Event");
    alAudit.setShortDescription("Test Short Description");
    alAudit.setGroupName("Test Group Name");
    alAudit.setCreatedOn(new Date().getTime());
    alAudit.setLevelId(createAlAuditLevel());

    return alAudit;
  }

  public List<AlAudit> createAlAudits() {
    List<AlAudit> alAudits = new ArrayList<>();
    alAudits.add(createAlAudit());

    return alAudits;
  }

  public SearchDTO createSearchDTO() {
    SearchDTO searchDTO = new SearchDTO();
    searchDTO.setValue(Arrays.asList("Test Search Value"));
    searchDTO.setColumn(AuditLogColumns.groupName);
    searchDTO.setOperator(SearchOperator.EQUAL);

    return searchDTO;
  }

  public List<SearchDTO> createSearchDTOs() {
    List<SearchDTO> searchDTOS = new ArrayList<>();
    searchDTOS.add(createSearchDTO());

    return searchDTOS;
  }

  public SearchDTO createLikeSearchDTO() {
    SearchDTO searchDTO = new SearchDTO();
    searchDTO.setValue(Arrays.asList("Test Search Value"));
    searchDTO.setColumn(AuditLogColumns.groupName);
    searchDTO.setOperator(SearchOperator.LIKE);

    return searchDTO;
  }

  public List<SearchDTO> createLikeSearchDTOs() {
    List<SearchDTO> searchDTOS = new ArrayList<>();
    searchDTOS.add(createLikeSearchDTO());

    return searchDTOS;
  }

  public SortDTO createSortDTO() {
    SortDTO sortDTO = new SortDTO();
    sortDTO.setColumn(AuditLogColumns.groupName);
    sortDTO.setOperator(SortOperator.ASC);

    return sortDTO;
  }

  public SortDTO createReverseSortDTO() {
    SortDTO sortDTO = new SortDTO();
    sortDTO.setColumn(AuditLogColumns.groupName);
    sortDTO.setOperator(SortOperator.DESC);

    return sortDTO;
  }

  public List<SortDTO> createSortDTOs() {
    List<SortDTO> sortDTOS = new ArrayList<>();
    sortDTOS.add(createSortDTO());

    return sortDTOS;
  }

  public List<SortDTO> createReverseSortDTOs() {
    List<SortDTO> sortDTOS = new ArrayList<>();
    sortDTOS.add(createReverseSortDTO());

    return sortDTOS;
  }
}
