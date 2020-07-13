package com.eurodyn.qlack.commons.aspects;

/**
 * A class with utility functionality for Aspects. Aspects do not necessarily need to extend this class unless they do
 * require its services.
 *
 * @author European Dynamics SA
 */
public class AspectSupport {

  /**
   * Finds the value of a named argument inside a JoinPoint.
   *
   * @param argumentName The name of the argument to lookup its value.
   * @param argumentNames The names of all arguments for this particular method signature.
   * @param arguments The values of all arguments for this particular method signature.
   * @return The value of argumentName or null if the requested argument could not be found.
   */
  public Object getArgumentValue(String argumentName, String[] argumentNames, Object[] arguments) {
    Object retVal = null;

    for (int i = 0; i < arguments.length; i++) {
      if (argumentNames[i].equals(argumentName)) {
        retVal = arguments[i];
        break;
      }
    }

    return retVal;
  }

}
