package com.eurodyn.qlack.extras.dbrebuild;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ehond
 */
public class PropertiesLoader {

  private static final Logger logger = Logger.getLogger(PropertiesLoader.class.getName());
  private Properties properties;

  public PropertiesLoader(String fileToLoad) {
    logger.log(Level.CONFIG, "Initialising PropertiesLoader for: {0}.", fileToLoad);
    properties = new Properties();
    logger.log(Level.INFO, "Looking for properties file: {0}.", fileToLoad);

    InputStream in = null;
    try {
      in = new FileInputStream(new File(fileToLoad));
      if (in != null) {
        properties.load(in);
        logger.log(Level.INFO, "Loaded properties file {0}.", fileToLoad);
      } else {
        logger.log(Level.SEVERE, "Could not find properties file: {0}.", fileToLoad);
      }
    } catch (IOException ex) {
      logger.log(Level.SEVERE, "Could not load properties file {0} [file was found].", fileToLoad);
      throw new RuntimeException(
          "Could not load properties file '" + fileToLoad + "' [file was found].", ex);
    } finally {
      try {
        in.close();
      } catch (IOException ex) {
        logger.log(Level.SEVERE, "Could not close inputstream used to load properties file {0}.",
            fileToLoad);
      }
    }

  }

  /**
   * Returns the loaded properties as a map.
   */
  public Map<String, String> getPropertiesMap() {
    Map<String, String> propertiesMap = new HashMap<String, String>();
    for (Map.Entry<Object, Object> entry : properties.entrySet()) {
      String property = (entry.getKey() != null ? (String) entry.getKey() : null);
      String value = (entry.getValue() != null ? (String) entry.getValue() : null);

      if (property != null && value != null) {
        propertiesMap.put(property, value);
      }
    }

    return propertiesMap;
  }
}