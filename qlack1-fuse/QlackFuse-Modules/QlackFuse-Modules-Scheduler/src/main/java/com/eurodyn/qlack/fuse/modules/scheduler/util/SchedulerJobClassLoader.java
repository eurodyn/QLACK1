package com.eurodyn.qlack.fuse.modules.scheduler.util;

/**
 * @author European Dynamics S.A.
 */
public class SchedulerJobClassLoader extends ClassLoader {

  private final byte[] clazz;
  private final String className;

  /**
   * Constructor with the class in byteArray and the class name.
   */
  public SchedulerJobClassLoader(byte[] clazz, String className) {
    super();
    this.clazz = clazz;
    this.className = className;
  }

  /**
   * Find the class with the provided name.
   *
   * @param name - the class namer
   */
  @Override
  @SuppressWarnings("unchecked")
  public Class findClass(String name) throws ClassNotFoundException {
    // Serve our own class from the custom byte[]/
    if (className.equals(name)) {
      return defineClass(name, clazz, 0, clazz.length);
    } else { // Anything else references by our class, should be tried on the parent classloader.
      return Thread.currentThread().getContextClassLoader().loadClass(name);
    }
  }

}
