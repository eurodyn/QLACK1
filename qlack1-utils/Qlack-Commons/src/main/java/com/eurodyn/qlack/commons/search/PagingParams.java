package com.eurodyn.qlack.commons.search;

import java.io.Serializable;

/**
 * This class is a paging utility.
 *
 * @author European Dynamics SA
 */
public class PagingParams implements Serializable {

  /**
   * Default page size.
   */
  public static final int DEFAULT_PAGE_SIZE = 20;
  private static final long serialVersionUID = -5072605761125964814L;
  private int pageSize;
  private int currentPage;
  private int totalResults;

  /**
   * Default Constructor.
   */
  public PagingParams() {
    pageSize = DEFAULT_PAGE_SIZE;
    currentPage = 1;
  }

  /**
   * Paramaterized Constructor.
   */
  public PagingParams(int pageSize, int currentPage) {
    this.pageSize = pageSize;
    this.currentPage = currentPage;
  }

  /**
   *
   */
  public PagingParams(int currentPage) {
    this.currentPage = currentPage;
  }

  /**
   * @return the pageSize
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * @param pageSize the pageSize to set
   */
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * @return the currentPage
   */
  public int getCurrentPage() {
    return currentPage;
  }

  /**
   * @param currentPage the currentPage to set
   */
  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  /**
   * @return the totalResults
   */
  public int getTotalResults() {
    return totalResults;
  }

  /**
   * @param totalResults the totalResults to set
   */
  public void setTotalResults(int totalResults) {
    this.totalResults = totalResults;
  }
}
