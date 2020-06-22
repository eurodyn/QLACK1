package com.eurodyn.qlack.fuse.modules.lexicon.init;

import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexDataDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexGroupDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexKeyDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexLanguageDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexData;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexGroup;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexKey;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexLanguage;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class is responsible for providing data for the unit tests.
 *
 * @author European Dynamics SA
 */
public class InitTestValues {

  private final static String USERNAME = "Test User";

  public LexGroup createLexGroup() {
    LexGroup lexGroup = new LexGroup();
    lexGroup.setId("29562733-4183-424a-b1b4-f38cfae2f637");
    lexGroup.setTitle("Test Title");
    lexGroup.setDescription("Test Description");
    lexGroup.setCreatedBy(USERNAME);
    lexGroup.setCreatedOn(new Date().getTime());
    lexGroup.setLastModifiedBy(USERNAME);
    lexGroup.setLastModifiedOn(new Date().getTime());

    return lexGroup;
  }

  public LexGroupDTO createLexGroupDTO() {
    LexGroupDTO lexGroupDTO = new LexGroupDTO();
    lexGroupDTO.setId("29562733-4183-424a-b1b4-f38cfae2f637");
    lexGroupDTO.setTitle("Test Title");
    lexGroupDTO.setDescription("Test Description");
    lexGroupDTO.setCreatedBy(USERNAME);
    lexGroupDTO.setCreatedOn(new Date().getTime());
    lexGroupDTO.setLastModifiedBy(USERNAME);
    lexGroupDTO.setLastModifiedOn(new Date().getTime());

    return lexGroupDTO;
  }

  public List<LexGroup> createLexGroups() {
    List<LexGroup> lexGroups = new ArrayList<>();
    lexGroups.add(createLexGroup());

    return lexGroups;
  }

  public LexTemplate createLexTemplate() {
    LexTemplate lexTemplate = new LexTemplate();
    lexTemplate.setValue("Test Template");

    return lexTemplate;
  }

  public List<LexTemplate> createLexTemplates() {
    List<LexTemplate> lexTemplates = new ArrayList<>();
    lexTemplates.add(createLexTemplate());

    return lexTemplates;
  }

  public LexDataDTO createLexDataDTO() {
    LexDataDTO lexDataDTO = new LexDataDTO();
    lexDataDTO.setId("89a302ce-8b9b-4fba-86f4-36ebb839e7e1");
    lexDataDTO.setCreatedBy(USERNAME);
    lexDataDTO.setCreatedOn(new Date().getTime());
    lexDataDTO.setLastModifiedBy(USERNAME);
    lexDataDTO.setLastModifiedOn(new Date().getTime());
    lexDataDTO.setApproved(true);
    lexDataDTO.setApprovedBy(USERNAME);
    lexDataDTO.setApprovedOn(new Date().getTime());
    lexDataDTO.setLocale("en");
    lexDataDTO.setValue("Test Translated Value");

    return lexDataDTO;
  }

  public LexData createLexData() {
    LexData lexData = new LexData();
    lexData.setId("89a302ce-8b9b-4fba-86f4-36ebb839e7e1");
    lexData.setCreatedBy(USERNAME);
    lexData.setCreatedOn(new Date().getTime());
    lexData.setLastModifiedBy(USERNAME);
    lexData.setLastModifiedOn(new Date().getTime());
    lexData.setApproved(true);
    lexData.setApprovedBy(USERNAME);
    lexData.setApprovedOn(new Date().getTime());
    lexData.setValue("Test Translated Value");
    lexData.setKeyId(createLexKey());

    return lexData;
  }

  public List<LexData> createLexDataList() {
    List<LexData> lexData = new ArrayList<>();
    lexData.add(createLexData());

    return lexData;
  }

  public LexDataDTO[] createLexDataDTOs() {
    List<LexDataDTO> lexDataDTOS = new ArrayList<>();
    lexDataDTOS.add(createLexDataDTO());

    return lexDataDTOS.toArray(new LexDataDTO[0]);
  }

  public LexKeyDTO createLexKeyDTO() {
    LexKeyDTO lexKeyDTO = new LexKeyDTO();
    lexKeyDTO.setId("3e93ce18-263b-46fe-ba50-3b21556ad11c");
    lexKeyDTO.setName("Key Name");
    lexKeyDTO.setCreatedBy(USERNAME);
    lexKeyDTO.setCreatedOn(new Date().getTime());
    lexKeyDTO.setLastModifiedBy(USERNAME);
    lexKeyDTO.setLastModifiedOn(new Date().getTime());
    lexKeyDTO.setGroupId("a0cc5430-2dd8-43b8-a88e-53218bdc8dda");
    lexKeyDTO.setData(createLexDataDTOs());

    return lexKeyDTO;
  }

  public LexKey createLexKey() {
    LexKey lexKey = new LexKey();
    lexKey.setId("3e93ce18-263b-46fe-ba50-3b21556ad11c");
    lexKey.setName("Key Name");
    lexKey.setCreatedBy(USERNAME);
    lexKey.setCreatedOn(new Date().getTime());
    lexKey.setLastModifiedBy(USERNAME);
    lexKey.setLastModifiedOn(new Date().getTime());

    return lexKey;
  }

  public LexKey[] createLexKeys() {
    List<LexKey> lexKeys = new ArrayList<>();
    lexKeys.add(createLexKey());

    return lexKeys.toArray(new LexKey[0]);
  }

  public LexLanguageDTO createLexLanguageDTO() {
    LexLanguageDTO lexLanguageDTO = new LexLanguageDTO();
    lexLanguageDTO.setId("0d1c0e8e-0d03-459f-9443-49eb2b612967");
    lexLanguageDTO.setName("English");
    lexLanguageDTO.setLocale("en");
    lexLanguageDTO.setCreatedBy(USERNAME);
    lexLanguageDTO.setCreatedOn(new Date().getTime());
    lexLanguageDTO.setLastModifiedBy(USERNAME);
    lexLanguageDTO.setLastModifiedOn(new Date().getTime());
    lexLanguageDTO.setActive(true);
    lexLanguageDTO.setKey(createLexKeyDTO());

    return lexLanguageDTO;
  }

  public LexLanguageDTO[] createLexLanguageDTOs() {
    List<LexLanguageDTO> lexLanguageDTOS = new ArrayList<>();
    lexLanguageDTOS.add(createLexLanguageDTO());

    return lexLanguageDTOS.toArray(new LexLanguageDTO[0]);
  }

  public LexLanguage createLexLanguage() {
    LexLanguage lexLanguage = new LexLanguage();
    lexLanguage.setId("0d1c0e8e-0d03-459f-9443-49eb2b612967");
    lexLanguage.setName("English");
    lexLanguage.setLocale("en");
    lexLanguage.setCreatedBy(USERNAME);
    lexLanguage.setCreatedOn(new Date().getTime());
    lexLanguage.setLastModifiedBy(USERNAME);
    lexLanguage.setLastModifiedOn(new Date().getTime());
    lexLanguage.setActive(true);

    return lexLanguage;
  }

  public LexLanguage[] createLexLanguages() {
    List<LexLanguage> lexLanguages = new ArrayList<>();
    lexLanguages.add(createLexLanguage());

    return lexLanguages.toArray(new LexLanguage[0]);
  }

  public HashMap<String, String> createLanguageHashMap() {
    HashMap<String, String> hashMap = new LinkedHashMap<>();
    hashMap.put(createLexData().getKeyId().getName(), createLexData().getValue());

    return hashMap;
  }

  public byte[] getLanguagesByteArray() throws IOException {
    Path resourceDirectory = Paths
        .get("src/test/resources/en_translations.xls");

    ClassLoader classLoader = getClass().getClassLoader();

    return Files.readAllBytes(resourceDirectory);
  }

}
