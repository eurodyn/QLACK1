package com.eurodyn.qlack.fuse.modules.lexicon.service;

import com.eurodyn.qlack.commons.search.PagingParams;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexLanguageDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.exception.QlackFuseLexiconException;
import javax.ejb.Remote;

/**
 * Remote interface for Language management
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Remote
public interface LanguageManager {

  /**
   * This method creates Language and returns the original lexLanguageDTO populated with the ID of the newly created
   * language.
   *
   * @param lexLanguageDTO LexLanguageDTO
   * @return lexLanguageDTO
   * @throws QlackFuseLexiconException Throws exception if provided lexLanguageDTO is null or provided language name
   * already exists.
   */
  LexLanguageDTO createLanguage(LexLanguageDTO lexLanguageDTO) throws QlackFuseLexiconException;

  /**
   * This method deletes language for provided language ID.
   *
   * @throws QlackFuseLexiconException Throws exception if provided language ID is null
   */
  void deleteLanguage(String locale) throws QlackFuseLexiconException;

  /**
   * This method retrieves language DTO for provided language ID
   *
   * @return language DTO
   * @throws QlackFuseLexiconException Throws exception if provided lexLanguageDTO is null
   */
  LexLanguageDTO viewLanguage(String languageID) throws QlackFuseLexiconException;

  /**
   * This method retrieves language DTO for provided language locale
   *
   * @return language DTO
   * @throws QlackFuseLexiconException Throws exception if provided lexLanguageDTO is null
   */
  LexLanguageDTO getLanguageByLocale(String locale) throws QlackFuseLexiconException;

  /**
   * This method updates language for provided Language DTO
   *
   * @return lexLanguageDTO
   * @throws QlackFuseLexiconException Throws exception provided lexLanguageDTO is null or provided language name
   * already exists.
   */
  LexLanguageDTO updateLanguage(LexLanguageDTO lexLanguageDTO) throws QlackFuseLexiconException;

  /**
   * This method returns array of Language DTO for provided pagination parameter
   *
   * @return LanguageDTOs
   */
  LexLanguageDTO[] listLanguages(PagingParams paging, boolean includeInactive);

  /**
   * This method returns the locale of an active language. Provided the requested locale, if the language * is active
   * then it is returned, else the reduced locale is returned, or finally the default locale.
   *
   * @param locale the locale to request
   * @param defaultLocale the default locale, if the requested is not active or does not exist
   * @return (String) the locale
   */
  String getEffectiveLanguage(String locale, String defaultLocale);

  /**
   * Toggles the status of a language between active/inactive.
   *
   * @param locale The locale to toggle the status for. it cannot be null and it must exist in the DB.
   * @param newStatus The new status to be set.
   */
  void toggleStatus(String locale, boolean newStatus);

  /**
   * Creates an Excel file with the translations of the requested locale. The returned Excel file is based on Excel
   * '97(-2007) file format (.xls).
   *
   * @param locale The locale for which to retrieve all translations.
   * @return A byte array representing an Excel '97 (.xls) file with the requested translations.
   * @throws QlackFuseLexiconException When the Excel file could not be prepared.
   */
  byte[] downloadLanguage(String locale) throws QlackFuseLexiconException;

  /**
   * Updates the system translations with the translations found on the provided Excel. Note that the locale should
   * exist as well as the respective keys. Keys which can not be found on the lexicon database are simply ignored.
   *
   * @param locale The locale for which the translations are uploaded.
   * @param lgXL The Excel file as uploaded by the user.
   * @param modifiedBy The name of the person uploading this language.
   * @throws QlackFuseLexiconException When the Excel file could not be parsed.
   */
  void uploadLanguage(String locale, byte[] lgXL, String modifiedBy)
      throws QlackFuseLexiconException;
}
