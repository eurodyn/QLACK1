package com.eurodyn.qlack.fuse.modules.lexicon.dto;

import com.eurodyn.qlack.fuse.commons.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO class for entity LeyKey
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LexKeyDTO extends BaseDTO {

  private String groupId;

  private String name;

  private String createdBy;

  private long createdOn;

  private Long lastModifiedOn;

  private String lastModifiedBy;

  private LexDataDTO[] data;

}
