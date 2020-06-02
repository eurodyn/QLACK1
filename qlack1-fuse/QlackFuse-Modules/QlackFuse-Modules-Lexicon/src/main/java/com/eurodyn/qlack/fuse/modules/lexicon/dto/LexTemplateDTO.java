package com.eurodyn.qlack.fuse.modules.lexicon.dto;

import com.eurodyn.qlack.fuse.commons.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO class for entity LexTemplate
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LexTemplateDTO extends BaseDTO {

  private String name;

  private String value;

  private long createdOn;

  private String createdBy;

  private Long lastModifiedOn;

  private String lastModifiedBy;

}
