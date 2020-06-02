package com.eurodyn.qlack.fuse.modules.lexicon.exception;

import com.eurodyn.qlack.fuse.commons.exception.QlackFuseException;
import javax.ejb.ApplicationException;

/**
 * This class and its subclasses are a form of Exception that indicates conditions that Lexicon related module might
 * want to catch.
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@ApplicationException(rollback = true)
public class QlackFuseLexiconException extends QlackFuseException {

  /**
   * Instantiate with errorCode and message
   */
  public QlackFuseLexiconException(CODES errorCode, String message) {
    super(errorCode, message);
  }

  /**
   * Enumeration of the Error codes.
   */
  public enum CODES implements ExceptionCode {

    ERR_LEXICON_0001, //Provided group ID is null.
    ERR_LEXICON_0002, //Provided group name already exist.
    ERR_LEXICON_0003, //Language id is required
    ERR_LEXICON_0004, //Locale id is required
    ERR_LEXICON_0005, //Group does not exist with the provided groupId.
    ERR_LEXICON_0006, //Provided searchTerm is null.
    ERR_LEXICON_0007, //Provided key ID is null.
    ERR_LEXICON_0008, //Provided key name already exist.
    ERR_LEXICON_0009, //Key does not exist with the provided keyId.
    ERR_LEXICON_0010, //Provided language ID is null.
    ERR_LEXICON_0011, //Provided data ID is null.
    ERR_LEXICON_0012, //Provided template ID is null.
    ERR_LEXICON_0013, //Provided Data DTO is null..
    ERR_LEXICON_0014, //Key Id is required in provided Data DTO.
    ERR_LEXICON_0015, //Provided group Name is null.
    ERR_LEXICON_0016, //Provided key Name is null.
    ERR_LEXICON_0017, //Provided language code is null.
    ERR_LEXICON_0018, //XML could not be parsed.
    ERR_LEXICON_0019, //File io exception.
    ERR_LEXICON_0020, //Parse configuration error.
    ERR_LEXICON_0021, //JAXB failed to marshal the java class to xml.
    ERR_LEXICON_0022, //Freemarket template process error.
    ERR_LEXICON_0023, //Mandatory field Language name is required.
    ERR_LEXICON_0024, //Mandatory field ISO Code is required.
    ERR_LEXICON_0025, //Mandatory field Group Name is required.
    ERR_LEXICON_0026, //Mandatory field key name is required.
    ERR_LEXICON_0027, //Template body should not be null.
    ERR_LEXICON_0028  // Buffering error while reading/writing to Excel.
  }

}