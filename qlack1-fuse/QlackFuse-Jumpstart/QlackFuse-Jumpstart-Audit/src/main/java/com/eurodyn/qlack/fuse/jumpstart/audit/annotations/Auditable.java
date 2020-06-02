package com.eurodyn.qlack.fuse.jumpstart.audit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author European Dynamics SA.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auditable {

  String eventName() default "system";

  String level() default "";

  String descriptionObject() default "";

  String description() default "";

  // The username can be obtained in two different ways:
  //      - if the value you enter here starts with a '#', then the aspect will try to find and call
  //        a function with that name to obtain the username.
  //      - if the value you enter here does not start with a '#', then it will be used as passed.
  String userName() default "system";

  String groupName() default "";
}
