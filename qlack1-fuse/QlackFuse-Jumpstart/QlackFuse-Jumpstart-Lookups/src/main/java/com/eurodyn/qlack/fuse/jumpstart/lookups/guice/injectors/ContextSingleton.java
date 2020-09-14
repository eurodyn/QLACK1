package com.eurodyn.qlack.fuse.jumpstart.lookups.guice.injectors;

import com.eurodyn.qlack.fuse.jumpstart.lookups.util.PropertiesLoaderSingleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author European Dynamics SA.
 */
public class ContextSingleton {

  public static final Logger logger = Logger.getLogger(ContextSingleton.class.getName());
  private static final Map<String, Object> cache = Collections
      .synchronizedMap(new HashMap<>());
  private static final ContextSingleton INSTANCE = new ContextSingleton();
  private final boolean isProduction;
  private Context context;


  private ContextSingleton() {
    logger.log(Level.CONFIG, "Initialising ContextSingleton.");

    Map<String, String> applicationProperties = new HashMap<>();
    applicationProperties.put("java.naming.factory.initial",
        PropertiesLoaderSingleton.getInstance()
            .getProperty("QlackFuseJS.Lookups.java.naming.factory.initial"));
    applicationProperties.put("java.naming.factory.url.pkgs",
        PropertiesLoaderSingleton.getInstance()
            .getProperty("QlackFuseJS.Lookups.java.naming.factory.url.pkgs"));
    applicationProperties.put("java.naming.factory.state",
        PropertiesLoaderSingleton.getInstance()
            .getProperty("QlackFuseJS.Lookups.java.naming.factory.state"));
    applicationProperties.put("org.omg.CORBA.ORBInitialHost",
        PropertiesLoaderSingleton.getInstance()
            .getProperty("QlackFuseJS.Lookups.org.omg.CORBA.ORBInitialHost"));
    applicationProperties.put("org.omg.CORBA.ORBInitialPort",
        PropertiesLoaderSingleton.getInstance()
            .getProperty("QlackFuseJS.Lookups.org.omg.CORBA.ORBInitialPort"));

    //Only put not null/empty properties in the InitialContext properties in order to support
    //getting an initial context without specifying all the properties if they are not
    //required/supported by the container (ex. JBoss7)
    Properties properties = new Properties();
    for (Entry<String, String> entry : applicationProperties.entrySet()) {
      if ((entry.getValue() != null) && (!entry.getValue().trim().isEmpty())) {
        properties.put(entry.getKey(), entry.getValue());
      }
    }
    try {
      context = new InitialContext(properties);
    } catch (NamingException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
    }

    isProduction = "production".equals(
        PropertiesLoaderSingleton.getInstance().getProperty("QlackFuseJS.Lookups.running.mode"));

    logger.log(Level.CONFIG, "ContextSingleton initialised.");
  }

  public static ContextSingleton getInstance() {
    return INSTANCE;
  }

  public Object lookup(String jndiName) throws NamingException {
    logger.log(Level.FINER, "Requested JNDI lookup for: {0}. Production mode = {1}.",
        new String[]{jndiName, String.valueOf(isProduction)});

    Object objectRef;
    if (isProduction) {
      objectRef = cache.get(jndiName);
      if (objectRef == null) {
        logger.log(Level.FINER, "Did not find {0} on cache. Looking it up now.", jndiName);
        objectRef = context.lookup(jndiName);
        if (objectRef != null) {
          logger.log(Level.FINER, "{0} was found on JNDI", jndiName);
          cache.put(jndiName, objectRef);
        } else {
          throw new NamingException("JNDI lookup for " + jndiName + " returned a null object.");
        }
      }
    } else {
      objectRef = context.lookup(jndiName);
    }

    return objectRef;
  }

}
