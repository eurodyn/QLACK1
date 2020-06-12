package com.eurodyn.qlack.fuse.modules.lexicon.service.impl;

import com.eurodyn.qlack.commons.exception.QlackCommonsException;
import com.eurodyn.qlack.commons.exception.QlackCommonsException.CODES;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexTemplate;
import com.eurodyn.qlack.fuse.modules.lexicon.service.TemplatesManager;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Service implementation for Template Interface.
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Stateless(name = "TemplatesManagerBean")
public class TemplatesManagerBean implements TemplatesManager {

  @PersistenceContext(unitName = "QlackFuse-Lexicon-PU")
  private EntityManager em;

  private static final Logger logger = Logger.getLogger(TemplatesManagerBean.class.getName());

  @Override
  public String getTemplateByName(String templateName) {
    String retVal = null;

    Query q = em.createQuery("from LexTemplate t where t.name = :name");
    q.setParameter("name", templateName);
    @SuppressWarnings("unchecked")
    List<LexTemplate> r = q.getResultList();
    if (!r.isEmpty()) {
      retVal = r.get(0).getValue();
    }

    return retVal;
  }

  @Override
  public String processTemplateByName(String templateName, Map<String, Object> templateData)
      throws QlackCommonsException {
    StringWriter retVal = null;
    String templateBody = getTemplateByName(templateName);

    if (!StringUtils.isEmpty(templateBody)) {
      retVal = new StringWriter();
      try {
        Template template = new Template(templateName, new StringReader(templateBody), null);
        template.process(templateData, retVal);
        retVal.flush();
      } catch (TemplateException ex) {
        throw new QlackCommonsException(CODES.ERR_COM_0000, ex.getLocalizedMessage());
      } catch (IOException ex) {
        throw new QlackCommonsException(CODES.ERR_COM_0000, ex.getLocalizedMessage());
      }
    }

    return retVal != null ? retVal.toString() : null;
  }
}
