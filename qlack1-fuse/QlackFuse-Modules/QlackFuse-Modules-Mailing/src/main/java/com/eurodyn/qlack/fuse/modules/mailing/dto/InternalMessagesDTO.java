package com.eurodyn.qlack.fuse.modules.mailing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Data transfer object for internal messages.
 *
 * @author European Dynamics SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternalMessagesDTO extends MailBaseDTO {

  private String subject;

  private String message;

  private String from;

  private String fromId;

  private String to;

  private String toId;

  private Date dateSent;

  private Date dateReceived;

  private String status;

  private String attachment;

  private List<InternalMsgAttachmentDTO> internalAttachments;

  private String type;

  private String fwdAttachmentId;

  private String deleteType;

}
