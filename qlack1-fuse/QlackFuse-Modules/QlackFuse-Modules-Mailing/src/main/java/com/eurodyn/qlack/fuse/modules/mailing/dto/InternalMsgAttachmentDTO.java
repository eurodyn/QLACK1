package com.eurodyn.qlack.fuse.modules.mailing.dto;

/**
 * Data transfer object for internal messages attachments.
 *
 * @author European Dynamics SA.
 */
public class InternalMsgAttachmentDTO extends MailBaseDTO {

  private String messagesId;

  private String filename;

  private String contentType;

  private byte[] data;

  private String format;

  public String getMessagesId() {
    return messagesId;
  }

  public void setMessagesId(String messagesId) {
    this.messagesId = messagesId;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }
}
