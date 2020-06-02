package com.eurodyn.qlack.fuse.jumpstart.lookups.guice.annotations;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author European Dynamics SA.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@BindingAnnotation
public @interface InjectFromJNDI {

  String jndiName();
}