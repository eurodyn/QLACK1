package com.eurodyn.qlack.fuse.modules.lexicon.dto;

import com.eurodyn.qlack.fuse.commons.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO class for entity LexGroup
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LexGroupDTO extends BaseDTO {

  private String title;

  private String description;

  private long createdOn;

  private String createdBy;

  private String lastModifiedBy;

  private Long lastModifiedOn;

}
