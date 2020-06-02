package com.eurodyn.qlack.commons.fileio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * #### CONSIDER REPLACING THIS CLASS WITH APACHE COMMONS I/O #####
 *
 * @author European Dynamics SA
 */
public class FileReader {

  private FileReader() {

  }

  /**
   * Read binary file.
   *
   * @return byte array
   */
  public static byte[] readBinaryFile(String filename) throws Exception {
    return readBinaryFile(new File(filename));
  }

  /**
   * Read binary file.
   *
   * @return byte array
   */
  public static byte[] readBinaryFile(File file) throws Exception {

    long fileSize = file.length();
    if (fileSize > Integer.MAX_VALUE) {
      throw new Exception("Unsupported file size [" + fileSize + "]");
    }

    byte[] retVal = new byte[(int) fileSize];
    int readFileSize = 0;
    try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {
      readFileSize = in.read(retVal, 0, (int) fileSize);
    }

    if (readFileSize != fileSize) {
      throw new Exception("Incomplete file read [filesize=" + fileSize + ", read=" + readFileSize);
    }

    return retVal;
  }

  /**
   * Loads properties file
   *
   * @return properties
   */
  @SuppressWarnings("unchecked")
  public static Properties loadProperties(Class clazz, String fileName) throws Exception {
    Properties properties = new Properties();
    InputStream inputStream = clazz.getResourceAsStream(fileName);
    if (inputStream == null) {
      inputStream = clazz.getClassLoader().getResourceAsStream(fileName);
    }
    properties.load(inputStream);
    return properties;
  }

}