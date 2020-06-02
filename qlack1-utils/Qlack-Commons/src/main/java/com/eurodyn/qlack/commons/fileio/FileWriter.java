package com.eurodyn.qlack.commons.fileio;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * #### CONSIDER REPLACING THIS CLASS WITH APACHE COMMONS I/O #####
 *
 * @author European Dynamics SA
 */
public class FileWriter {

  private FileWriter() {

  }

  /**
   * Write a binary file.
   */
  public static void writeBinary(String filename, byte[] data)
      throws IOException {
    writeBinary(new File(filename), data);
  }

  /**
   * Write a binary file.
   */
  public static void writeBinary(File file, byte[] data) throws IOException {
    FileOutputStream fos = new FileOutputStream(file);

    try (BufferedOutputStream bos = new BufferedOutputStream(fos)) {
      bos.write(data);
    }
  }


}
