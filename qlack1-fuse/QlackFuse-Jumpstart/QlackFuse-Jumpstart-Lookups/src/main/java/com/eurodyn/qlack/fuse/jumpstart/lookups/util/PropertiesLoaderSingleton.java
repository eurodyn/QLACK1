package com.eurodyn.qlack.fuse.jumpstart.lookups.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A properties loader mechanism from anywhere in the classpath. The list of files to be loaded are defined in
 * 'filesToLoad' String array; filename beginning with a "!" are optional (i.e. they do not cause an error in case they
 * can not be resolved).
 *
 * @author European Dynamics SA.
 */
public class PropertiesLoaderSingleton {

  private static final Logger logger = Logger.getLogger(PropertiesLoaderSingleton.class.getName());

  private static PropertiesLoaderSingleton _instance = new PropertiesLoaderSingleton();
  private static Properties properties = new Properties();

  private PropertiesLoaderSingleton() {
    String[] filesToLoad = {"QlackFuseJS-Lookups.properties"};

    logger.log(Level.CONFIG, "Initialising PropertiesLoaderSingleton for: {0}.",
        Arrays.deepToString(filesToLoad));
    for (String nextFileToLoad : filesToLoad) {
      boolean isOptional = false;
      if (nextFileToLoad.startsWith("!")) {
        logger.log(Level.INFO, "Looking for optional properties file: {0}.", nextFileToLoad);
        isOptional = true;
        nextFileToLoad = nextFileToLoad.substring(1);
      } else {
        logger.log(Level.INFO, "Looking for properties file: {0}.", nextFileToLoad);
      }
      InputStream in = Thread.currentThread().getContextClassLoader()
          .getResourceAsStream(nextFileToLoad);
      if (in != null) {
        Properties newProperties = new Properties();
        try {
          newProperties.load(in);
          properties.putAll(newProperties);
          logger.log(Level.INFO, "Loaded properties file {0} from {1}.",
              new String[]{nextFileToLoad, Thread.currentThread().getContextClassLoader()
                  .getResource(nextFileToLoad).toString()});
        } catch (IOException ex) {
          if (!isOptional) {
            logger.log(Level.SEVERE, "Could not load properties file {0} [file was found].",
                nextFileToLoad);
            throw new RuntimeException(
                "Could not load properties file '" + nextFileToLoad + "' [file was found].", ex);
          }
        } finally {
          try {
            in.close();
          } catch (IOException ex) {
            logger
                .log(Level.SEVERE, "Could not close inputstream used to load properties file {0}.",
                    nextFileToLoad);
          }
        }
      } else {
        if (!isOptional) {
          logger.log(Level.SEVERE, "Could not find properties file: {0}.", nextFileToLoad);
        }
      }
    }
  }

  /**
   *
   */
  public static PropertiesLoaderSingleton getInstance() {
    return _instance;
  }

  /**
   * @return String
   */
  public String getProperty(String propertyName) {
    if (propertyName != null) {
      Object property = properties.getProperty(propertyName);
      logger.log(Level.FINEST, "Returning application property {0} with value {1}.",
          new String[]{propertyName, (String) property});
      return (property != null ? (String) property : null);
    } else {
      return null;
    }
  }
}
