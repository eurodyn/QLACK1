package com.eurodyn.qlack.fuse.modules.lexicon.service;

import com.eurodyn.qlack.commons.search.PagingParams;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexDataDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexKeyDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.exception.QlackFuseLexiconException;
import javax.ejb.Remote;

import java.util.HashMap;

/**
 * Remote interface for Key management
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Remote
public interface KeyManager {

  /**
   * Creates a new key into Lexicon. If the caller has populated the LexDataDTO[] structure it also created the
   * respective translations for the key.
   *
   * @param lexKeyDTO The DTO containing the information for the key to be created.
   * @return The ID of the newly created key.
   * @throws QlackFuseLexiconException If the creation of the new key failed.
   */
  String createKey(LexKeyDTO lexKeyDTO) throws QlackFuseLexiconException;

  /**
   * Updates an existing translation to a new value.
   *
   * @param keyName The name of the key to be updated.
   * @param locale The locale for which the translation is updated.
   * @param value The new value of the translation.
   * @param modifiedBy The name of the person that performs this modification.
   * @throws QlackFuseLexiconException If the update could not take place.
   */
  void updateTranslationByKeyName(String keyName, String locale, String value, String modifiedBy)
      throws QlackFuseLexiconException;

  /**
   * Updates an existing translation to a new value.
   *
   * @param keyID The ID of the key to be updated.
   * @param locale The locale for which the translation is updated.
   * @param value The new value of the translation.
   * @param modifiedBy The name of the person that performs this modification.
   * @throws QlackFuseLexiconException If the update could not take place.
   */
  void updateTranslationByKeyID(String keyID, String locale, String value, String modifiedBy)
      throws QlackFuseLexiconException;

  /**
   * Updates all the translations of a particular key.
   *
   * @param keyName The name of the key to be updated.
   * @param lDTOs An array with all the translations that will be updated.
   * @param modifiedBy The name of the person that performs this modification.
   * @throws QlackFuseLexiconException If the updated could not take place.
   */
  void updateTranslationsByKeyNameWithDTO(String keyName, LexDataDTO[] lDTOs, String modifiedBy)
      throws QlackFuseLexiconException;

  /**
   * Updates all the translations of a particular key.
   *
   * @param keyName The name of the key to be updated.
   * @param data An Map with all the translations that will be updated in the form of (locale_name, key_value) pairs.
   * @param modifiedBy The name of the person that performs this modification.
   * @throws QlackFuseLexiconException If the updated could not take place.
   */
  void updateTranslationsByKeyName(String keyName, HashMap<String, String> data, String modifiedBy)
      throws QlackFuseLexiconException;

  /**
   * Updates all the translations of a particular key.
   *
   * @param keyID The id of the key to be updated.
   * @param lDTOs An array with all the translations that will be updated.
   * @param modifiedBy The name of the person that performs this modification.
   * @throws QlackFuseLexiconException If the updated could not take place.
   */
  void updateTranslationsByKeyID(String keyID, LexDataDTO[] lDTOs, String modifiedBy)
      throws QlackFuseLexiconException;

  /**
   * Deletes an existing key from the Lexicon. Note that all translations for this key are also deleted permanently.
   *
   * @param keyName The name of the key to delete.
   * @throws QlackFuseLexiconException If the key could not be deleted.
   */
  void deleteKeyByName(String keyName) throws QlackFuseLexiconException;

  /**
   * Deletes an existing key from the Lexicon. Note that all translations for this key are also deleted permanently.
   *
   * @param keyID The id of the key to delete.
   * @throws QlackFuseLexiconException If the key could not be deleted.
   */
  void deleteKeyByID(String keyID) throws QlackFuseLexiconException;

  /**
   * Returns the information held about a particular key.
   *
   * @param keyID The id of the key to return the information about.
   * @return A DTO with all the information for a particular key. Note that the translations of that particular key are
   * not included.
   * @throws QlackFuseLexiconException If the requested key could not be found.
   */
  LexKeyDTO viewKeyByID(String keyID) throws QlackFuseLexiconException;

  /**
   * Returns the information held about a particular key.
   *
   * @param keyNAme The name of the key to return the information about.
   * @return A DTO with all the information for a particular key. Note that the translations of that particular key are
   * not included.
   * @throws QlackFuseLexiconException If the requested key could not be found.
   */
  LexKeyDTO viewKeyByName(String keyName) throws QlackFuseLexiconException;

  /**
   * Renames an existing key.
   *
   * @param keyName The name of the key to be renamed.
   * @param newName The new name of the key to be renamed to.
   * @param modifiedBy The name of the person that performs this modification.
   * @throws QlackFuseLexiconException If the key could not be renamed.
   */
  void renameKeyByName(String keyName, String newName, String modifiedBy)
      throws QlackFuseLexiconException;

  /**
   * Renames an existing key.
   *
   * @param keyID The ID of the key to be renamed.
   * @param newName The new name of the key to be renamed to.
   * @param modifiedBy The name of the person that performs this modification.
   * @throws QlackFuseLexiconException If the key could not be renamed.
   */
  void renameKeyByID(String keyID, String newName, String modifiedBy)
      throws QlackFuseLexiconException;

  /**
   * Returns the keys of a particular group.
   *
   * @param groupId The ID of the group to return the keys from.
   * @param paging Paging parameters.
   * @return An array of LexKeyDTO with the keys belonging to the requested group.
   */
  LexKeyDTO[] listKeysByGroupID(String groupId, PagingParams paging);

  /**
   * Finds the keys matching a particular name, optionally restricting the search within a particular group.
   *
   * @param groupId The ID of the group to restrict the search into.
   * @param searchTerm The name of the key (partially matched) to be searched.
   * @param paging Paging parameters.
   * @return An array of LexKeyDTO with the matching keys.
   * @throws QlackFuseLexiconException If the search could not be performed.
   */
  LexKeyDTO[] searchKeys(String groupId, String searchTerm, PagingParams paging)
      throws QlackFuseLexiconException;

  /**
   * Finds all the translations (i.e. in all locales) for a particular key.
   *
   * @param keyName The name of the key to return its translations.
   * @return A Map consisting of (locale_name, translation_value) pairs.
   */
  HashMap<String, String> getTranslationsByKeyName(String keyName);

  /**
   * Finds all the available translations for a particular locale.
   *
   * @param locale The locale to return to translations for.
   * @param filter An optional filter to restrict the search on. The filter is applied on either the name of the key or
   * the value of a translations and it is matched anywhere.
   * @return A Map consisting of (key_name, translation_value) for the particular locale.
   */
  HashMap<String, String> getTranslationsForLocale(String locale, String filter);

  /**
   * Updates all keys for a particular locale. This method is usually helpful when you want to create a new language and
   * you need to quickly populate with some default values or if you have a screen where you edit all the keys of a
   * particular language at once.
   *
   * @param locale The locale of the keys to be updated.
   * @param values A Map consisting of (key_name, translation_value) pairs.
   * @param modifiedBy The name of the person that performs this modification.
   */
  void updateTranslationsForLocale(String locale, HashMap<String, String> values,
      String modifiedBy);

  /**
   * Adds the requested translations for a particular key.
   *
   * @param locale The locale to update the keys for.
   * @param values A Map consisting of (key_name, translation_value) pairs.
   * @param createdBy The name of the person that performs this addition.
   */
  void addTranslationsForLocale(String locale, HashMap<String, String> values, String createdBy);


  /**
   * Returns the translation of a particular key for a specific locale.
   *
   * @param key The key to lookup.
   * @param locale The specific locale value for the requested key.
   * @return The value of they key on the specific locale. If the key could not be found, it returns the key name passed
   * in.
   */
  String getTranslationForKeyAndLocale(String key, String locale);


  /**
   * Finds all the available translations for a particular group and locale
   *
   * @param groupId The id of the group of which to retrieve translations
   * @param locale The locale for which to retrieve the translations
   * @return A Map consisting of (key_name, translation_value) for the specified group and locale.
   */
  HashMap<String, String> getTranslationsForGroupAndLocale(String groupId, String locale);
}
