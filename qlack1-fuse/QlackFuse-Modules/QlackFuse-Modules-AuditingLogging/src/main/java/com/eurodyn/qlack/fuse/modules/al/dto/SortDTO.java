package com.eurodyn.qlack.fuse.modules.al.dto;

import com.eurodyn.qlack.fuse.modules.al.enums.AuditLogColumns;
import com.eurodyn.qlack.fuse.modules.al.enums.SortOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author European Dynamics SA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SortDTO implements Serializable {

  private AuditLogColumns column;

  private SortOperator operator;

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
