package com.eurodyn.qlack.fuse.modules.lexicon.util;

import com.eurodyn.qlack.fuse.modules.lexicon.exception.QlackFuseLexiconException;
import com.eurodyn.qlack.fuse.modules.lexicon.exception.QlackFuseLexiconException.CODES;
import lombok.extern.slf4j.Slf4j;

/**
 * Util class for Lexicon module
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Slf4j
public class LexiconValidationUtil {

  private static final String NULL_KEY_MESSAGE = "Provided keyID is null";

  private LexiconValidationUtil() {
  }

  /**
   * This methods validates if provided groupingID is null.
   */
  public static void validateNullForGroupID(String groupingID) throws QlackFuseLexiconException {
    if (groupingID == null || groupingID.trim().equals("")) {
      log.error("Provided group ID is null.");
      throw new QlackFuseLexiconException(CODES.ERR_LEXICON_0001, "Provided group ID is null.");
    }
  }

  /**
   * This methods validates if provided searchTerm is null.
   *
   * @throws QlackFuseLexiconException Throws exception if provided searchTerm is null.
   */
  public static void validateNullForSearchTerm(String searchTerm) throws QlackFuseLexiconException {
    if (searchTerm == null || searchTerm.trim().equals("")) {
      log.error("Provided searchTerm is null.");
      throw new QlackFuseLexiconException(CODES.ERR_LEXICON_0006, "Provided searchTerm is null.");
    }
  }

  /**
   * This methods validates if provided keyID is null.
   */
  public static void validateNullForKeyID(String keyID) throws QlackFuseLexiconException {
    if (keyID == null || keyID.trim().equals("")) {
      log.error(NULL_KEY_MESSAGE);
      throw new QlackFuseLexiconException(CODES.ERR_LEXICON_0007, NULL_KEY_MESSAGE);
    }
  }

  /**
   * This methods validates if provided langID is null.
   */
  public static void validateNullForLanguageID(String langID) throws QlackFuseLexiconException {
    if (langID == null || langID.trim().equals("")) {
      log.error(NULL_KEY_MESSAGE);
      throw new QlackFuseLexiconException(CODES.ERR_LEXICON_0010, "Provided language ID is null.");
    }
  }
  //

  /**
   * This methods validates if provided language code is null.
   */
  public static void validateNullForLanguageCode(String langCode) throws QlackFuseLexiconException {
    if (langCode == null || langCode.trim().equals("")) {
      log.error("Provided key code is null.");
      throw new QlackFuseLexiconException(CODES.ERR_LEXICON_0017,
          "Provided language code is null.");
    }
  }
}
