package com.eurodyn.qlack.fuse.commons.dto.mailing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * DTO class for QLACK Email Attachment class
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDTO implements Serializable {

  private String id;

  private String filename;

  private String contentType;

  private byte[] data;

  /**
   * Parameterized Constructor
   *
   * @param id The ID of the attachment
   * @param filename The file name to be attached
   * @param contentType The content type for attachment
   */
  public AttachmentDTO(String id, String filename, String contentType) {
    this.id = id;
    this.filename = filename;
    this.contentType = contentType;
  }

  @Override
  public String toString() {
    return "Attachment id is :" + getId() + "file name is:" + getFilename()
        + "content type is :" + getContentType();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final AttachmentDTO other = (AttachmentDTO) obj;
    if ((this.getId() == null) ? (other.getId() != null) : !this.id.equals(other.id)) {
      return false;
    }
    if ((this.getFilename() == null) ? (other.getFilename() != null)
        : !this.filename.equals(other.filename)) {
      return false;
    }
    if ((this.getContentType() == null) ? (other.getContentType() != null)
        : !this.contentType.equals(other.contentType)) {
      return false;
    }
    return Arrays.equals(this.data, other.data);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 13 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
    hash = 13 * hash + (this.getFilename() != null ? this.getFilename().hashCode() : 0);
    hash = 13 * hash + (this.getContentType() != null ? this.getContentType().hashCode() : 0);
    hash = 13 * hash + Arrays.hashCode(this.getData());
    return hash;
  }
}
