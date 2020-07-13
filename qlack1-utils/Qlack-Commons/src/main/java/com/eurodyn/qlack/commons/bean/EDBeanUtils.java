package com.eurodyn.qlack.commons.bean;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author European Dynamics SA
 */
public class EDBeanUtils {

  private EDBeanUtils() {
  }

  /**
   * Convenience method to extract a property from a bean array.
   */
  public static String[] getPropertyFromBeanArray(Object[] beanArray, String propertyName)
      throws NoSuchMethodException {
    String[] retVal = new String[beanArray.length];
    int counter = 0;
    for (Object o : beanArray) {
      try {
        if (propertyName.contains(".")) {
          String[] parts = propertyName.split(Pattern.quote("."));
          Object value = o;
          for (String part : parts) {
            PropertyDescriptor pd;

            pd = new PropertyDescriptor(part, value.getClass());

            Method getter = pd.getReadMethod();
            value = getter.invoke(value);

          }
          retVal[counter++] = value.toString();
        } else {
          PropertyDescriptor pd = new PropertyDescriptor(propertyName, o.getClass());

          Method getter = pd.getReadMethod();

          Object value = getter.invoke(o);
          retVal[counter++] = value.toString();
        }
      } catch (IntrospectionException e) {
        e.printStackTrace();
        throw new NoSuchMethodException();
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
      }

    }

    return retVal;
  }

  /**
   * Similar to @getPropertyFromBeanArray but returns only unique values.
   */
  public static String[] getUniquePropertyFromBeanArray(Object[] beanArray, String propertyName)
      throws NoSuchMethodException {
    List<String> l = new ArrayList<String>();
    for (Object o : beanArray) {
      try {
        Object value = o;
        if (propertyName.contains(".")) {
          String[] parts = propertyName.split(Pattern.quote("."));
          for (String part : parts) {
            PropertyDescriptor pd = new PropertyDescriptor(part, value.getClass());
            Method getter = pd.getReadMethod();
            value = getter.invoke(value);
          }

        } else {
          PropertyDescriptor pd = new PropertyDescriptor(propertyName, o.getClass());

          Method getter = pd.getReadMethod();

          value = getter.invoke(o);
        }
        if (!l.contains(value.toString())) {
          l.add(value.toString());
        }
      } catch (IntrospectionException e) {
        e.printStackTrace();
        throw new NoSuchMethodException();
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }

    return l.toArray(new String[l.size()]);
  }
}
