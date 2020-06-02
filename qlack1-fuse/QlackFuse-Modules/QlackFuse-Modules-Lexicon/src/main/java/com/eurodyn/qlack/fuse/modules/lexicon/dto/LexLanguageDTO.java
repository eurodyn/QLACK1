package com.eurodyn.qlack.fuse.modules.lexicon.dto;

import com.eurodyn.qlack.fuse.commons.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO class for entity LexLanguage
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LexLanguageDTO extends BaseDTO {

  private String name;

  private String locale;

  private long createdOn;

  private String createdBy;

  private Long lastModifiedOn;

  private String lastModifiedBy;

  private LexKeyDTO key;

  private boolean active;

}
