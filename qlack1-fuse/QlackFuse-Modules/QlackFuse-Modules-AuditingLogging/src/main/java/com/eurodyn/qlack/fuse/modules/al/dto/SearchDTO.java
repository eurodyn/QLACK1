package com.eurodyn.qlack.fuse.modules.al.dto;

import com.eurodyn.qlack.fuse.modules.al.enums.AuditLogColumns;
import com.eurodyn.qlack.fuse.modules.al.enums.SearchOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author European Dynamics SA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO implements Serializable {

  private List<String> value;

  private AuditLogColumns column;

  private SearchOperator operator;

  @Override
  public String toString() {
    return "SearchDTO{" + "value=" + value + ", column=" + column + ", operator=" + operator + '}';
  }

}
