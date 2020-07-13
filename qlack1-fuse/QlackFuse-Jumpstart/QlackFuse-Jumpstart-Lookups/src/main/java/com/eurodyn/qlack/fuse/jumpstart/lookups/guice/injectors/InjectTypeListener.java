package com.eurodyn.qlack.fuse.jumpstart.lookups.guice.injectors;

import com.eurodyn.qlack.fuse.jumpstart.lookups.guice.annotations.InjectFromJNDI;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author European Dynamics SA.
 */
public class InjectTypeListener implements TypeListener {

  public static final Logger logger = Logger.getLogger(InjectTypeListener.class.getName());

  @Override
  public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
    for (Field field : typeLiteral.getRawType().getDeclaredFields()) {
      if (field.isAnnotationPresent(InjectFromJNDI.class)) {
        logger.log(Level.FINEST, "Injecting InjectFromJNDIInjector to {0}.",
            field.getDeclaringClass().getName());
        typeEncounter.register(new InjectFromJNDIInjector<>(field));
      }
    }
  }
}
