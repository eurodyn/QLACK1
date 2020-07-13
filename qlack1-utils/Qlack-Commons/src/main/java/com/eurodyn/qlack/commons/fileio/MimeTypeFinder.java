package com.eurodyn.qlack.commons.fileio;

import eu.medsea.mimeutil.MimeUtil2;

import java.io.File;
import java.util.Collection;

/**
 * @author European Dynamics SA
 */
public class MimeTypeFinder {

  private MimeTypeFinder() {
  }

  /**
   * Find  mime types of a file.
   *
   * @param f - file
   * @return Collection of mime types
   */
  public static Collection findMimeTypes(File f) throws Exception {
    return (findMimeTypes(FileReader.readBinaryFile(f)));
  }

  /**
   * Find mime types of a file.
   *
   * @param fileAsByteArray the file as a byte array
   * @return Collection of mime types
   */
  public static Collection findMimeTypes(byte[] fileAsByteArray) {
    Collection retVal = null;

    try {
      MimeUtil2 mimeUtil = new MimeUtil2();
      mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
      retVal = mimeUtil.getMimeTypes(fileAsByteArray);
      mimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
    } catch (Exception e) {
      System.out.println(e.toString());

    }
    return retVal;
  }
}//end of class.

