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
    name = "mai_internal_attachment"
)
@Getter
@Setter
public class MaiInternalAttachment implements Serializable {

  @Id
  private String id;

  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "messages_id",
      nullable = false
  )
  private MaiInternalMessages messagesId;

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
      name = "data"
  )
  private byte[] data;

  @Column(
      name = "format",
      length = 45
  )
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

  public String getId() {
    if (this.id == null) {
      this.id = UUID.randomUUID().toString();
    }

    return this.id;
  }

}
