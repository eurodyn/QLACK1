package com.eurodyn.qlack.fuse.modules.mailing.dto;

import com.eurodyn.qlack.fuse.commons.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Base Data transfer object for Mailing.
 *
 * @author European Dynamics SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailBaseDTO extends BaseDTO {

  private String id;

}
