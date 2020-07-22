package com.eurodyn.qlack.fuse.modules.mailing.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * A properties loader mechanism from anywhere in the classpath. The list of files to be loaded are defined in
 * 'filesToLoad' String array; filename beginning with a "!" are optional (i.e. they do not cause an error in case they
 * can not be resolved).
 *
 * @author European Dynamics SA
 */
@Slf4j
public class PropertiesLoaderSingleton {

  private static final PropertiesLoaderSingleton _instance = new PropertiesLoaderSingleton();

  //properties
  private static Properties properties;

  /**
   * Constructor of PropertiesLoaderSingleton.
   */
  private PropertiesLoaderSingleton() {
    String[] filesToLoad = {"QlackFuse-Mailing.properties"};

    log.info("Initialising PropertiesLoaderSingleton for: {0}.",
        Arrays.deepToString(filesToLoad));
    properties = new Properties();
    		
    for (String nextFileToLoad : filesToLoad) {
      boolean isOptional = false;
      if (nextFileToLoad.startsWith("!")) {
        log.info("Looking for optional properties file: {0}.", nextFileToLoad);
        isOptional = true;
        nextFileToLoad = nextFileToLoad.substring(1);
      } else {
        log.info("Looking for properties file: {0}.", nextFileToLoad);
      }
      InputStream in = Thread.currentThread().getContextClassLoader()
          .getResourceAsStream(nextFileToLoad);
      if (in != null) {
        initForInputStream(in, nextFileToLoad, isOptional);
      } else {
        if (!isOptional) {
          log.error("Could not find properties file: {0}.", nextFileToLoad);
        }
      }
    }
  }

  private void initForInputStream(InputStream in, String nextFileToLoad, boolean isOptional) {
    Properties newProperties = new Properties();
    try {
      newProperties.load(in);
      properties.putAll(newProperties);
      log.info("Loaded properties file {0} from {1}.",
          new String[]{nextFileToLoad, Thread.currentThread().getContextClassLoader()
              .getResource(nextFileToLoad).toString()});
    } catch (IOException ex) {
      if (!isOptional) {
        log.error("Could not load properties file {0} [file was found].",
            nextFileToLoad);
        throw new RuntimeException(
            "Could not load properties file '" + nextFileToLoad + "' [file was found].", ex);
      }
    } finally {
      try {
        in.close();
      } catch (IOException ex) {
        log.error("Could not close inputstream used to load properties file {0}.",
            nextFileToLoad);
      }
    }
  }

  /**
   * Get the instance of PropertiesLoaderSingleton.
   */
  public static PropertiesLoaderSingleton getInstance() {
    return _instance;
  }

  /**
   * Get a property by its property name.
   *
   * @return String
   */
  public String getProperty(String propertyName) {
    if (propertyName != null) {
      Object property = properties.getProperty(propertyName);
      log.info("Returning application property {0} with value {1}.",
          new String[]{propertyName, (String) property});
      return (property != null ? (String) property : null);
    } else {
      return null;
    }
  }
}
