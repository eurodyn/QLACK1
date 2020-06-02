package com.eurodyn.qlack.fuse.commons.util;

import com.eurodyn.qlack.fuse.commons.dto.BaseDTO;
import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * Utility class to convert 1.transfer object to entity 2.entity to transfer object.
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Slf4j
public class ConverterUtility {

  private ConverterUtility() {
  }

  /**
   * This method is used for converting transfer object to entity.
   *
   * @param frm BaseDTO.
   * @param to Entity Object.
   * @return Returns the converted entity object.
   * @see BaseDTO
   */
  public static Object convertToEntity(BaseDTO frm, Class to) {
    Object o = null;
    try {
      if (frm == null) {
        return null;
      }
      o = to.getDeclaredConstructor().newInstance();
      copyProperties(frm, o);
    } catch (InstantiationException ex) {
      log.error("Error Instantiating:{0}", ex.getMessage());
    } catch (IllegalAccessException ex) {
      log.error("Error Accessing:{0}", ex.getMessage());
    } catch (NoSuchMethodException | InvocationTargetException e) {
      log.error(e.getMessage());
    }
    return o;
  }

  /**
   * This method is used for converting entity to transfer object.
   *
   * @param frm Entity Object.
   * @param to BaseDTO.
   * @return Returns the converted transfer object object.
   * @see BaseDTO
   */
  public static BaseDTO convertToDTO(Object frm, Class to) {

    BaseDTO dto = null;
    try {
      if (frm == null) {
        return null;
      }
      dto = (BaseDTO) to.getDeclaredConstructor().newInstance();

      copyProperties(frm, dto);
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
      log.error("Error1:{0}", ex.getMessage());
    }
    return dto;
  }

  public static void copyProperties(Object fromObj, Object toObj) {
    Class<? extends Object> fromClass = fromObj.getClass();
    Class<? extends Object> toClass = toObj.getClass();

    try {
      BeanInfo fromBean = Introspector.getBeanInfo(fromClass);
      BeanInfo toBean = Introspector.getBeanInfo(toClass);

      PropertyDescriptor[] toPd = toBean.getPropertyDescriptors();
      PropertyDescriptor[] fromPd = fromBean.getPropertyDescriptors();

      for (PropertyDescriptor toPropertyDescriptor : toPd) {

        for (PropertyDescriptor fromPropertyDescriptor : fromPd) {
          if (toPropertyDescriptor.getName().equals(fromPropertyDescriptor.getName()) && !fromPropertyDescriptor
              .getDisplayName().equals("class") && (toPropertyDescriptor.getWriteMethod()
              != null) && (fromPropertyDescriptor.getReadMethod() != null)) {
            toPropertyDescriptor.getWriteMethod().invoke(toObj,
                fromPropertyDescriptor.getReadMethod().invoke(fromObj, null));
          }
        }

      }
    } catch (IntrospectionException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
      log.error(e.getLocalizedMessage());
    }
  }
}