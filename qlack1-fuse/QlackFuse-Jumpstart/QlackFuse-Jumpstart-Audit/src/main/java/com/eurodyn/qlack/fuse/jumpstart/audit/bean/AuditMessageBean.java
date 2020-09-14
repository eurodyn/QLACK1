package com.eurodyn.qlack.fuse.jumpstart.audit.bean;

import com.eurodyn.qlack.fuse.jumpstart.audit.util.PropertiesLoaderSingleton;
import com.eurodyn.qlack.fuse.jumpstart.lookups.guice.injectors.ContextSingleton;
import com.eurodyn.qlack.fuse.modules.al.dto.AuditLogDTO;
import com.eurodyn.qlack.fuse.modules.al.service.AuditLoggingManager;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author European Dynamics SA
 */
//@MessageDriven(name = "AuditMDB")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/AuditQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")})
@TransactionManagement(TransactionManagementType.BEAN)
public class AuditMessageBean implements MessageListener {
  //IMPORTANT: MDBs should implement MessageListener, in order to be deployed on Jboss-AS server

  private static final Logger logger = Logger.getLogger(AuditMessageBean.class.getName());
  private static AuditLoggingManager audit;
  @Resource
  private UserTransaction userTx;

  public static void init() {
    try {
      String parentEar = PropertiesLoaderSingleton.getInstance()
          .getProperty("QlackFuseJS.audit.parent.ear");
      audit = (AuditLoggingManager) ContextSingleton.getInstance().lookup(
          "java:global/" + parentEar
              + "QlackFuse-Modules-AuditingLogging/AuditLoggingManagerBean");
    } catch (NamingException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
    }
  }

  public void onMessage(Message message) {
    logger.log(Level.FINER, "Received an audit message.");
    try {
      AuditLogDTO auditDTO = (AuditLogDTO) ((ObjectMessage) message).getObject();
      //initialize audit if not set
      if (audit == null) {
        init();
      }
      //Log the audit in a new transaction in order to avoid poison message situation which
      //will occur if an SQL exception is thrown.
      userTx.begin();
      audit.logAudit(auditDTO);
      userTx.commit();
    } catch (Exception ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new EJBException(ex);
    }
  }

}
