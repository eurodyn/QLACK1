package com.eurodyn.qlack.fuse.modules.mailing.util;

import com.eurodyn.qlack.fuse.commons.dto.mailing.EmailDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.ContactDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.DistributionListDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.InternalMessagesDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.InternalMsgAttachmentDTO;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiContact;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiDistributionList;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiEmail;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiInternalAttachment;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiInternalMessages;
import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * This is utility class used for : 1.Converting DTO to entity. 2.Converting entity to DTO.
 *
 * @author European Dynamics SA.
 */
public class ConverterUtil {

  /**
   * Converts the entity MaiDistributionList to data transfer object DistributionListDTO
   *
   * @param entity MaiDistributionList entity.
   * @return DistributionListDTO Data transfer object, null if entity is null.
   */
  public static DistributionListDTO convertToDistributionListDTO(MaiDistributionList entity) {

    if (entity == null) {
      return null;
    }

    DistributionListDTO dto = new DistributionListDTO();
    dto.setId(entity.getId());
    dto.setDescription(entity.getDescription());
    dto.setName(entity.getListName());
    dto.setCreatedBy(entity.getCreatedBy());

    return dto;
  }

  /**
   * Converts the entity data transfer object DistributionListDTO to MaiDistributionList.
   *
   * @param dto Data transfer object
   * @return MaiDistributionList entity, null if DTO is null.
   */
  public static MaiDistributionList convertToDistributionListEntity(DistributionListDTO dto) {
    if (dto == null) {
      return null;
    }

    MaiDistributionList entity = new MaiDistributionList();
    entity.setId(dto.getId());
    entity.setDescription(dto.getDescription());
    entity.setListName(dto.getName());
    entity.setCreatedBy(dto.getCreatedBy());
    entity.setCreatedOn(dto.getCreatedOn());

    return entity;
  }

  /**
   * Converts the entity data transfer object ContactDTO to MaiContact.
   */
  public static MaiContact conctactDTOToMaiContact(ContactDTO dto) {
    MaiContact retVal = new MaiContact();
    retVal.setEmail(dto.getEmail());
    retVal.setFirstName(dto.getFirstName());
    retVal.setId(dto.getId());
    retVal.setLastName(dto.getLastName());
    retVal.setLocale(dto.getLocale());
    retVal.setUserId(dto.getUserID());

    return retVal;
  }

  /**
   * Creates a list of e-mails.
   *
   * @param emails String of e-mails separated by token .
   * @param token String token can be comma(,) or semicolon(;).
   * @return list of e-mails.
   */
  public static List createRecepientlist(String emails) {
    String token = ",";
    List contacts = new ArrayList<>();
    StringTokenizer st = new StringTokenizer(emails, token);
    while (st.hasMoreElements()) {
      String next = (String) st.nextElement();
      contacts.add(next);
    }

    return contacts.isEmpty()
        ? null
        : contacts;
  }

  /**
   * Create e-mails string separated by token
   *
   * @param emails List of e-mails.
   * @return String of token separated e-mails.
   */
  public static String createRecepientlist(List<String> emails) {

    StringBuilder emailAddress = new StringBuilder();
    if (emails != null && !emails.isEmpty()) {
      for (String email : emails) {
        if (emailAddress.length() > 0) {
          emailAddress.append(",");
        }
        emailAddress.append(email);
      }
    }

    return emailAddress.length() > 0
        ? emailAddress.toString()
        : null;
  }

  /**
   * Converts InternalMessagesDTO DTO to MaiInternalMessages.
   *
   * @param dto internal message data transfer object.
   * @return MaiInternalMessages entity.
   */
  public static MaiInternalMessages converToInternalMessagesEntityWithAttachments(
      InternalMessagesDTO dto) {

    if (dto == null) {
      return null;
    }
    MaiInternalMessages entity = converToInternalMessagesEntityWOAttachments(dto);

    List<InternalMsgAttachmentDTO> internalMsgAttachments =
        dto.getInternalAttachments();

    List<MaiInternalAttachment> entityAttachments = new ArrayList<>();

    if (internalMsgAttachments != null) {
      for (InternalMsgAttachmentDTO attachment : internalMsgAttachments) {
        MaiInternalAttachment maiInternalAttachment =
            convertToInternalMsgAttachmentEntity(attachment);
        if (attachment != null) {
          maiInternalAttachment.setMessagesId(entity);
        }
        entityAttachments.add(maiInternalAttachment);
      }
    }
    Set maiInternalAttachments = new HashSet(entityAttachments);
    entity.setMaiInternalAttachments(maiInternalAttachments);

    return entity;
  }

  /**
   * Converts InternalMessagesDTO DTO to MaiInternalMessages with out attachments.
   *
   * @param dto internal message data transfer object.
   * @return MaiInternalMessages entity.
   */
  public static MaiInternalMessages converToInternalMessagesEntityWOAttachments(
      InternalMessagesDTO dto) {

    if (dto == null) {
      return null;
    }
    MaiInternalMessages entity = new MaiInternalMessages();

    entity.setDateReceived(dto.getDateReceived().getTime());
    entity.setDateSent(dto.getDateSent().getTime());
    entity.setMailFrom(dto.getFromId());
    entity.setMessage(dto.getMessage());
    entity.setSubject(dto.getSubject());
    entity.setMailTo(dto.getToId());

    return entity;
  }

  /**
   * Converts the MaiInternalAttachment entity to data transfer object.
   *
   * @param dto internal message data transfer object.
   * @return MaiInternalAttachment entity.
   */
  public static MaiInternalAttachment convertToInternalMsgAttachmentEntity(
      InternalMsgAttachmentDTO dto) {

    if (dto == null) {
      return null;
    }

    MaiInternalAttachment entity = new MaiInternalAttachment();

    entity.setContentType(dto.getContentType());
    entity.setData(dto.getData());

    entity.setFilename(dto.getFilename());
    entity.setFormat(dto.getFormat());
    entity.setId(dto.getId());

    return entity;
  }

  /**
   * Converts MaiInternalMessages entity to InternalMessagesDTO.
   *
   * @param entity MaiInternalMessages
   * @return InternalMessagesDTO
   */
  public static InternalMessagesDTO converToInternalMessagesDTO(MaiInternalMessages entity) {
    if (entity == null) {
      return null;
    }
    InternalMessagesDTO dto = new InternalMessagesDTO();
    dto.setId(entity.getId());
    dto.setDateReceived(new Date(entity.getDateReceived() * 1000));
    dto.setDateSent(new Date(entity.getDateSent() * 1000));
    dto.setFromId(entity.getMailFrom());
    dto.setSubject(entity.getSubject());
    dto.setMessage(entity.getMessage());
    dto.setStatus(entity.getStatus());
    dto.setToId(entity.getMailTo());
    dto.setAttachment(entity.getDeleteType());

    Set<MaiInternalAttachment> entityAttachments =
        entity.getMaiInternalAttachments();

    List<InternalMsgAttachmentDTO> dtoAttachments =
        new ArrayList<>();

    if (entityAttachments != null && !entityAttachments.isEmpty()) {
      for (MaiInternalAttachment maiAttachment : entityAttachments) {
        dtoAttachments.add(convertToInternalMsgAttachmentDTO(maiAttachment));
      }
      dto.setAttachment("Y");
    }
    dto.setInternalAttachments(dtoAttachments);
    return dto;
  }

  /**
   * Converts the MaiInternalAttachment entity to DTO.
   *
   * @param entity Internal attachment.
   * @return MaiInternalAttachment entity.
   */
  public static InternalMsgAttachmentDTO convertToInternalMsgAttachmentDTO(
      MaiInternalAttachment entity) {

    if (entity == null) {
      return null;
    }

    InternalMsgAttachmentDTO dto = new InternalMsgAttachmentDTO();

    dto.setContentType(entity.getContentType());
    dto.setData(entity.getData());
    dto.setFilename(entity.getFilename());
    dto.setFormat(entity.getFormat());
    dto.setId(entity.getId());
    dto.setMessagesId(entity.getMessagesId().getId());

    return dto;
  }

  /**
   * Converts list of MaiInternalMessages entities to list data transfer object.
   *
   * @param maiInternalMessagesList list of MaiInternalMessages entities.
   * @return list of data transfer object.
   */
  public static List<InternalMessagesDTO> convertToInternalMessagesDTOList(
      List<MaiInternalMessages> maiInternalMessagesList, EntityManager em) {

    List<InternalMessagesDTO> messagesDTOList = new ArrayList<>();

    for (MaiInternalMessages maiInternalMessages : maiInternalMessagesList) {
      InternalMessagesDTO dto = converToInternalMessagesDTO(maiInternalMessages);
      messagesDTOList.add(dto);
    }
    return messagesDTOList;
  }

  /**
   * Converts email data transfer object to email entity.
   */
  public EmailDTO MaiEmailToEmailDTO(MaiEmail maiEmail) {
    return new EmailDTO();
  }
}
