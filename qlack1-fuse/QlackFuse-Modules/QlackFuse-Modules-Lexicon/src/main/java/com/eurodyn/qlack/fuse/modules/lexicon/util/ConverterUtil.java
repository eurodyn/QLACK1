package com.eurodyn.qlack.fuse.modules.lexicon.util;

import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexGroupDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexKeyDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexLanguageDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexGroup;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexKey;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexLanguage;

/**
 * Utility class to convert from DTO to model bean or vice versa
 *
 * @author EUROPEAN DYNAMICS SA.
 */
public class ConverterUtil {

  private ConverterUtil() {
  }

  /**
   * This method convert Group DTO object to Group Entity
   */
  public static LexGroup lexGroupDTOtoLexGroup(LexGroupDTO lexGroupDTO) {
    LexGroup lexGroup = new LexGroup();
    lexGroup.setId(lexGroupDTO.getId());
    lexGroup.setTitle(lexGroupDTO.getTitle());
    lexGroup.setDescription(lexGroupDTO.getDescription());
    lexGroup.setCreatedBy(lexGroupDTO.getCreatedBy());
    lexGroup.setCreatedOn(lexGroupDTO.getCreatedOn());
    lexGroup.setLastModifiedBy(lexGroupDTO.getLastModifiedBy());
    lexGroup.setLastModifiedOn(lexGroupDTO.getLastModifiedOn());
    return lexGroup;
  }
  //

  /**
   * This method convert Group Entity object to Group DTO
   */
  public static LexGroupDTO lexGroupToLexGroupDTO(LexGroup group) {
    LexGroupDTO groupDTO = new LexGroupDTO();
    groupDTO.setId(group.getId());
    groupDTO.setTitle(group.getTitle());
    groupDTO.setDescription(group.getDescription());
    groupDTO.setCreatedBy(group.getCreatedBy());
    groupDTO.setCreatedOn(group.getCreatedOn());
    groupDTO.setLastModifiedBy(group.getLastModifiedBy());
    groupDTO.setLastModifiedOn(group.getLastModifiedOn());
    return groupDTO;
  }


  /**
   * This method convert Key entity object to key DTO object
   */
  public static LexKeyDTO lexKeyToLexKeyDTO(LexKey key) {
    LexKeyDTO keyDTO = new LexKeyDTO();
    keyDTO.setId(key.getId());
    keyDTO.setName(key.getName());
    keyDTO.setCreatedBy(key.getCreatedBy());
    keyDTO.setCreatedOn(key.getCreatedOn());
    keyDTO.setLastModifiedBy(key.getLastModifiedBy());
    keyDTO.setLastModifiedOn(key.getLastModifiedOn());

    return keyDTO;
  }

  /**
   * This method convert Language DTO object to Language Entity
   */
  public static LexLanguage lexLanguageDTOtoLexLanguage(LexLanguageDTO langDTO) {
    LexLanguage lexLanguage = new LexLanguage();
    lexLanguage.setId(langDTO.getId());
    lexLanguage.setName(langDTO.getName());
    lexLanguage.setLocale(langDTO.getLocale());
    lexLanguage.setCreatedBy(langDTO.getCreatedBy());
    lexLanguage.setCreatedOn(langDTO.getCreatedOn());
    lexLanguage.setLastModifiedBy(langDTO.getLastModifiedBy());
    lexLanguage.setLastModifiedOn(langDTO.getLastModifiedOn());
    lexLanguage.setActive(langDTO.isActive());
    return lexLanguage;
  }
  //

  /**
   * This method convert Language entity object to Language DTO object
   */
  public static LexLanguageDTO lexLanguageToLexLanguageDTO(LexLanguage lang) {
    LexLanguageDTO langDTO = new LexLanguageDTO();
    langDTO.setId(lang.getId());
    langDTO.setName(lang.getName());
    langDTO.setLocale(lang.getLocale());
    langDTO.setCreatedBy(lang.getCreatedBy());
    langDTO.setCreatedOn(lang.getCreatedOn());
    langDTO.setLastModifiedBy(lang.getLastModifiedBy());
    langDTO.setLastModifiedOn(lang.getLastModifiedOn());
    langDTO.setActive(lang.isActive());
    return langDTO;
  }
}
