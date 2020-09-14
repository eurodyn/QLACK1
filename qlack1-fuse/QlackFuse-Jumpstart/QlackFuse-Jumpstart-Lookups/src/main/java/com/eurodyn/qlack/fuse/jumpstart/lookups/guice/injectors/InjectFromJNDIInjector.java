package com.eurodyn.qlack.fuse.jumpstart.lookups.guice.injectors;

import com.eurodyn.qlack.fuse.jumpstart.lookups.guice.annotations.InjectFromJNDI;
import com.google.inject.MembersInjector;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author European Dynamics SA.
 */
public class InjectFromJNDIInjector<T> implements MembersInjector<T> {

  public static final Logger logger = Logger.getLogger(InjectFromJNDIInjector.class.getName());
  private final Field field;
  private String jndiName;


  InjectFromJNDIInjector(Field f) {
    this.field = f;
    InjectFromJNDI annotation = f.getAnnotation(InjectFromJNDI.class);
    this.jndiName = annotation.jndiName();
    this.field.setAccessible(true);
  }

  private Object lookup() throws Exception {
    return ContextSingleton.getInstance().lookup(jndiName);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void injectMembers(T t) {
    try {
      this.field.set(t, lookup());
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
    }
  }
}
