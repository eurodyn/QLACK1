package com.eurodyn.qlack.fuse.modules.mailing.util;

import lombok.Getter;
import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.CriteriaFactory;

@Getter
public class CriteriaBuilderUtil {

  private final Criteria criteria;

  private CriteriaBuilderUtil(Criteria criteria) {
    this.criteria = criteria;
  }

  public CriteriaBuilderUtil(String criteriaBuilder) {
    this.criteria = CriteriaFactory.createCriteria(criteriaBuilder);
  }
}
