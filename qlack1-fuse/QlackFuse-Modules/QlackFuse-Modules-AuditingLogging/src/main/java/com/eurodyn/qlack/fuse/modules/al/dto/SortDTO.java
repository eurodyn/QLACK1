package com.eurodyn.qlack.fuse.modules.al.dto;

import com.eurodyn.qlack.fuse.modules.al.enums.AuditLogColumns;
import com.eurodyn.qlack.fuse.modules.al.enums.SortOperator;

import java.io.Serializable;

/**
 * @author European Dynamics SA
 */
public class SortDTO implements Serializable {

  private AuditLogColumns column;

  private SortOperator operator;

  public AuditLogColumns getColumn() {
    return column;
  }

  public void setColumn(AuditLogColumns column) {
    this.column = column;
  }

  public SortOperator getOperator() {
    return operator;
  }

  public void setOperator(SortOperator operator) {
    this.operator = operator;
  }

  @Override
  public String toString() {
    return "SortDTO{" + "column=" + column + ", operator=" + operator + '}';
  }

  public AuditLogColumns getAuditLogColumnEnumByName(String columnName) {
    AuditLogColumns[] values = AuditLogColumns.values();
    for (AuditLogColumns enumeration : values) {
      if (enumeration.name().equals(columnName)) {
        return enumeration;
      }
    }

    return null;
  }

}
