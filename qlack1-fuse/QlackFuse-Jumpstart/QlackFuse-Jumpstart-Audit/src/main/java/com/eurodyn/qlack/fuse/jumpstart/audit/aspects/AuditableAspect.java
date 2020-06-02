package com.eurodyn.qlack.fuse.jumpstart.audit.aspects;

import com.eurodyn.qlack.commons.debug.dump.Dumper;
import com.eurodyn.qlack.fuse.jumpstart.audit.annotations.Auditable;
import com.eurodyn.qlack.fuse.jumpstart.audit.client.AuditClient;
import com.eurodyn.qlack.fuse.modules.al.dto.AuditLevelDTO;
import com.eurodyn.qlack.fuse.modules.al.dto.AuditLogDTO;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An aspect with an anonymous pointcut to automatically create audit messages.
 *
 * @author European Dynamics SA.
 */
@Aspect
public class AuditableAspect {

  private static final Logger logger = Logger.getLogger(AuditableAspect.class.getName());

  @Pointcut("execution(@com.eurodyn.qlack.fuse.jumpstart.audit.client.annotations.Auditable * * (..))")
  void auditablePointCut() {
  }

  @AfterReturning("auditablePointCut()")
  public void auditAction(JoinPoint jp) {
    try {
      // Get a reference to the annotation.
      MethodSignature sig = (MethodSignature) jp.getStaticPart().getSignature();
      Auditable auditableAnnotation = sig.getMethod().getAnnotation(Auditable.class);

      // Construct the Annotation DTO object.
      AuditLogDTO auditDTO = new AuditLogDTO();
      auditDTO.setCreatedOn(Calendar.getInstance().getTime());

      // For the name of the event, if not a custom one has been supplied, the name of
      // the Action
      // class is used.
      auditDTO.setEvent(auditableAnnotation.eventName());

      // For the level of the audit, we use INFO unless a custom level has been
      // provided.
      if (!auditableAnnotation.level().isEmpty()) {
        auditDTO.setLevel(auditableAnnotation.level());
      } else {
        auditDTO.setLevel(AuditLevelDTO.DefaultLevels.info.toString());
      }

      // The description of the auditted event is composed of the description provided
      // by the
      // user plus a dump of the descriptionObject (if any).
      auditDTO.setShortDescription(auditableAnnotation.description());
      if (!auditableAnnotation.descriptionObject().isEmpty()) {
        String[] objectsToGetInfoFrom = auditableAnnotation.descriptionObject().split(",");
        StringBuilder extraInfo = new StringBuilder();
        for (String nextObject : objectsToGetInfoFrom) {
          String dumpInfo = dump(
              jp.getThis().getClass().getMethod(nextObject.trim()).invoke(jp.getThis()));
          if (!StringUtils.isEmpty(dumpInfo)) {
            extraInfo.append(nextObject.trim());
            extraInfo.append("=");
            extraInfo.append(dumpInfo);
            extraInfo.append("\n");
          }
        }
        auditDTO.setShortDescription(auditDTO.getShortDescription() + "\n" + extraInfo.toString());
      }

      if (auditableAnnotation.userName().startsWith("#")) {
        String methodToCall = auditableAnnotation.userName().substring(1);
        auditDTO.setPrinSessionId(
            (String) jp.getThis().getClass().getMethod(methodToCall).invoke(jp.getThis()));
      } else {
        auditDTO.setPrinSessionId(auditableAnnotation.userName());
      }

      if (!auditableAnnotation.groupName().isEmpty()) {
        auditDTO.setGroupName(auditableAnnotation.groupName());
      }

      AuditClient.getInstance().audit(auditDTO);
    } catch (Exception ex) {
      logger.log(Level.SEVERE, "Error processing Auditable annotation: " + ex.getLocalizedMessage(),
          ex);
    }
  }

  public static String dump(Object o) {
    if (o == null) {
      return "";
    }
    if (o.getClass().toString().startsWith("class java.lang")) {
      return (o.toString());
    } else {
      return Dumper.dumpMap(describe(o));
    }
  }

  private static Map<String, String> describe(Object o) {
    Map<String, String> map = new HashMap<>();
    try {
      BeanInfo bean = Introspector.getBeanInfo(o.getClass());
      PropertyDescriptor[] descriptors = bean.getPropertyDescriptors();
      for (PropertyDescriptor propertyDescriptor : descriptors) {
        String value = StringUtils.EMPTY;
        if (propertyDescriptor.getReadMethod() != null) {
          Object object = propertyDescriptor.getReadMethod().invoke(o);
          if (object != null) {
            value = object.toString();
          }
        }
        map.put(propertyDescriptor.getName(), value);
      }
    } catch (IntrospectionException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
      logger.log(Level.SEVERE, "Error processing Auditable annotation: " + e.getLocalizedMessage(),
          e);
    }

    return map;

  }
}
