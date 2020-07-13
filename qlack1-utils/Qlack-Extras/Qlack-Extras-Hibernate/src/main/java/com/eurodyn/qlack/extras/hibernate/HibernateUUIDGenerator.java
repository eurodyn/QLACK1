package com.eurodyn.qlack.extras.hibernate;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author European Dynamics SA
 */
public class HibernateUUIDGenerator implements IdentifierGenerator {

  private static final Logger logger = Logger.getLogger(HibernateUUIDGenerator.class.getName());

  /**
   * @return Serializable
   */
  @Override
  public Serializable generate(SessionImplementor session, Object object) {

    String uniqueID = UUID.randomUUID().toString();
    logger.log(Level.FINEST, "Generated ID ''{0}''.", uniqueID);

    return uniqueID;
  }
}
