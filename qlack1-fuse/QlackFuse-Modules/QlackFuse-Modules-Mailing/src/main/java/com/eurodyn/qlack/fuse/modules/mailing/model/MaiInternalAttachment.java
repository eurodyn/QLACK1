package com.eurodyn.qlack.fuse.modules.mailing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(
    name = "mai_internal_attachment"
)
public class MaiInternalAttachment implements Serializable {

  private String id;
  private MaiInternalMessages messagesId;
  private String filename;
  private String contentType;
  private byte[] data;
  private String format;

  public MaiInternalAttachment() {
  }

  public MaiInternalAttachment(MaiInternalMessages messagesId) {
    this.messagesId = messagesId;
  }

  public MaiInternalAttachment(MaiInternalMessages messagesId, String filename, String contentType, byte[] data,
      String format) {
    this.messagesId = messagesId;
    this.filename = filename;
    this.contentType = contentType;
    this.data = data;
    this.format = format;
  }

  @Id
  public String getId() {
    if (this.id == null) {
      this.id = UUID.randomUUID().toString();
    }

    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "messages_id",
      nullable = false
  )
  public MaiInternalMessages getMessagesId() {
    return this.messagesId;
  }

  public void setMessagesId(MaiInternalMessages messagesId) {
    this.messagesId = messagesId;
  }

  @Column(
      name = "filename",
      length = 254
  )
  public String getFilename() {
    return this.filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  @Column(
      name = "content_type",
      length = 254
  )
  public String getContentType() {
    return this.contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  @Column(
      name = "data"
  )
  public byte[] getData() {
    return this.data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  @Column(
      name = "format",
      length = 45
  )
  public String getFormat() {
    return this.format;
  }

  public void setFormat(String format) {
    this.format = format;
  }
}
