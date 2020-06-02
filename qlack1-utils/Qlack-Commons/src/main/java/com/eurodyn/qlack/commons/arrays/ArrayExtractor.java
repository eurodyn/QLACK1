package com.eurodyn.qlack.commons.arrays;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author European Dynamics SA
 */
public class ArrayExtractor {

  /**
   * A helper method allowing you to obtain a array of specified type by extracting it using a bean property of an
   * another array. This is useful when you have a complex DTO object and you only need to e.g. extract all User IDs
   * from it.
   */
  public static String[] extractFromBeanArray(Object[] array, String propertyName)
      throws NoSuchMethodException {
    String[] retVal = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      try {
        if (propertyName.contains(".")) {
          String[] parts = propertyName.split(Pattern.quote("."));
          Object value = array[i];
          for (int j = 0; j < parts.length; j++) {
            PropertyDescriptor pd = new PropertyDescriptor(parts[j], value.getClass());
            Method getter = pd.getReadMethod();
            value = getter.invoke(value);
          }
          retVal[i] = value.toString();
        } else {
          PropertyDescriptor pd = new PropertyDescriptor(propertyName, array[i].getClass());

          Method getter = pd.getReadMethod();

          Object value = getter.invoke(array[i]);
          retVal[i] = value.toString();
        }
      } catch (IntrospectionException e) {
        e.printStackTrace();
        throw new NoSuchMethodException();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }

    }

    return retVal;
  }

}