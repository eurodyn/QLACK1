package com.eurodyn.qlack.fuse.modules.mailing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(
    name = "mai_attachment"
)
@Getter
@Setter
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
  
}
