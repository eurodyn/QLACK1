package com.eurodyn.qlack.fuse.modules.mailing.util;

import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.CriteriaFactory;

public class CriteriaBuilderUtil {

  private final Criteria criteria;

  public CriteriaBuilderUtil(String criteriaBuilder) {
    this.criteria = CriteriaFactory.createCriteria(criteriaBuilder);
  }

  public Criteria getCriteria() {
    return criteria;
  }
}
