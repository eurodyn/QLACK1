package com.eurodyn.qlack.fuse.modules.lexicon.dto;

import com.eurodyn.qlack.fuse.commons.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO class for entity LexData
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LexDataDTO extends BaseDTO {

  private String locale;

  private String value;

  private String createdBy;

  private long createdOn;

  private Long lastModifiedOn;

  private String lastModifiedBy;

  private boolean approved;

  private String approvedBy;

  private long approvedOn;
}
