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
    name = "mai_attachment"
)
public class MaiAttachment implements Serializable {

  @Id
  private String id;

  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "email_id",
      nullable = false
  )
  private MaiEmail emailId;

  @Column(
      name = "filename",
      length = 254
  )
  private String filename;

  @Column(
      name = "content_type",
      length = 254
  )
  private String contentType;

  @Column(
      name = "data",
      nullable = false
  )
  private byte[] data;

  @Column(
      name = "attachment_size"
  )
  private Long attachmentSize;

  public MaiAttachment() {
  }

  public MaiAttachment(MaiEmail emailId, byte[] data) {
    this.emailId = emailId;
    this.data = data;
  }

  public MaiAttachment(MaiEmail emailId, String filename, String contentType, byte[] data, Long attachmentSize) {
    this.emailId = emailId;
    this.filename = filename;
    this.contentType = contentType;
    this.data = data;
    this.attachmentSize = attachmentSize;
  }

  public String getId() {
    if (this.id == null) {
      this.id = UUID.randomUUID().toString();
    }

    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public MaiEmail getEmailId() {
    return emailId;
  }

  public void setEmailId(MaiEmail emailId) {
    this.emailId = emailId;
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

  public Long getAttachmentSize() {
    return attachmentSize;
  }

  public void setAttachmentSize(Long attachmentSize) {
    this.attachmentSize = attachmentSize;
  }
}
