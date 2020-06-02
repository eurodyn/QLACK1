package com.eurodyn.qlack.fuse.commons.search;

import com.eurodyn.qlack.commons.search.PagingParams;
import javax.persistence.Query;
import lombok.NoArgsConstructor;

/**
 * @author EUROPEAN DYNAMICS SA.
 */
public class ApplyPagingParams {

  private ApplyPagingParams(){}

  /**
   * @return Query
   */
  public static Query apply(Query q, PagingParams pagingParams) {
    if ((pagingParams != null) && (pagingParams.getCurrentPage() > -1)) {
      q.setFirstResult((pagingParams.getCurrentPage() - 1) * pagingParams.getPageSize());
      q.setMaxResults(pagingParams.getPageSize());
    }

    return q;
  }
}