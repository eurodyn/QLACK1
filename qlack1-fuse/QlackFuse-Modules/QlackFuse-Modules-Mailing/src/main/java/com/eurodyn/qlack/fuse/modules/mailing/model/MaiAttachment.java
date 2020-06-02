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

  private String id;
  private MaiEmail emailId;
  private String filename;
  private String contentType;
  private byte[] data;
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
      name = "email_id",
      nullable = false
  )
  public MaiEmail getEmailId() {
    return this.emailId;
  }

  public void setEmailId(MaiEmail emailId) {
    this.emailId = emailId;
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
      name = "data",
      nullable = false
  )
  public byte[] getData() {
    return this.data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  @Column(
      name = "attachment_size"
  )
  public Long getAttachmentSize() {
    return this.attachmentSize;
  }

  public void setAttachmentSize(Long attachmentSize) {
    this.attachmentSize = attachmentSize;
  }
}
