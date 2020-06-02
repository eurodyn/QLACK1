package com.eurodyn.qlack.fuse.modules.lexicon.service;

import com.eurodyn.qlack.commons.exception.QlackCommonsException;
import javax.ejb.Remote;

import java.util.Map;

/**
 * Remote interface for Template management
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Remote
public interface TemplatesManager {

  String getTemplateByName(String templateName);

  String processTemplateByName(String templateName, Map<String, Object> templateData)
      throws QlackCommonsException;
}
