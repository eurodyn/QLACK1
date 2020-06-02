package com.eurodyn.qlack.fuse.modules.mailing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object for internal messages attachments.
 *
 * @author European Dynamics SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternalMsgAttachmentDTO extends MailBaseDTO {

  private String messagesId;

  private String filename;

  private String contentType;

  private byte[] data;

  private String format;

}
