package com.eurodyn.qlack.fuse.modules.mailing.init;

import com.eurodyn.qlack.fuse.commons.dto.mailing.AttachmentDTO;
import com.eurodyn.qlack.fuse.commons.dto.mailing.EmailDTO;
import com.eurodyn.qlack.fuse.commons.dto.mailing.EmailDTO.EMAIL_TYPE;
import com.eurodyn.qlack.fuse.modules.mailing.dto.ContactDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.DistributionListDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.InternalMessagesDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.InternalMsgAttachmentDTO;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiContact;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiDistributionList;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiEmail;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiInternalAttachment;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiInternalMessages;
import com.eurodyn.qlack.fuse.modules.mailing.service.MailManager.EMAIL_STATUS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * This class is responsible for providing data for the unit tests.
 *
 * @author European Dynamics SA
 */
public class InitTestValues {

  private static final String EMAIL = "qlack@eurodyn.com";

  public AttachmentDTO createAttachmentDTO() throws IOException {
    AttachmentDTO attachmentDTO = new AttachmentDTO();
    attachmentDTO.setId("8740d380-9b87-4a61-902f-c33487751880");
    attachmentDTO.setFilename("test_attachment.png");
    File fi = new File("src/test/resources/test_attachment.png");
    attachmentDTO.setData(Files.readAllBytes(fi.toPath()));
    attachmentDTO.setContentType("img/png");

    return attachmentDTO;
  }

  public List<AttachmentDTO> createAttachmentDTOs() throws IOException {
    List<AttachmentDTO> attachmentDTOS = new ArrayList<>();
    attachmentDTOS.add(createAttachmentDTO());

    return attachmentDTOS;
  }

  public EmailDTO createEmailDTO() throws IOException {
    EmailDTO emailDTO = new EmailDTO();
    emailDTO.setId("b32a5858-c305-4e5e-9039-38516f00c8a8");
    emailDTO.setMessageId("64e301c0-e96f-4ff4-a06f-7cdad0122588");
    emailDTO.setToContact(EMAIL);
    emailDTO.setCcContact(Arrays.asList(EMAIL));
    emailDTO.setBccContact(Arrays.asList(EMAIL));
    emailDTO.setSubject("Test Subject");
    emailDTO.setBody("Test Body");
    emailDTO.setAttachments(createAttachmentDTOs());
    emailDTO.setEmailType(EMAIL_TYPE.TEXT);

    return emailDTO;
  }

  public List<EmailDTO> createEmailDTOs() throws IOException {
    List<EmailDTO> emailDTOS = new ArrayList<>();
    emailDTOS.add(createEmailDTO());

    return emailDTOS;
  }

  public MaiEmail createEmail() {
    MaiEmail maiEmail = new MaiEmail();
    maiEmail.setId("b32a5858-c305-4e5e-9039-38516f00c8a8");
    maiEmail.setStatus(EMAIL_STATUS.QUEUED.toString());
    maiEmail.setTries(Byte.parseByte("1"));
    maiEmail.setToEmails(EMAIL);
    maiEmail.setCcEmails(EMAIL);
    maiEmail.setBccEmails(EMAIL);
    maiEmail.setSubject("Test Subject");
    maiEmail.setBody("Test Body");
    maiEmail.setEmailType(EMAIL_TYPE.TEXT.toString());

    return maiEmail;
  }

  public List<MaiEmail> createMaiEmails() {
    List<MaiEmail> maiEmails = new ArrayList<>();
    maiEmails.add(createEmail());

    return maiEmails;
  }

  public ContactDTO createContactDTO() {
    ContactDTO contactDTO = new ContactDTO();
    contactDTO.setId("23d42043-9ec1-4446-b309-6b748f05fb8c");
    contactDTO.setEmail(EMAIL);
    contactDTO.setFirstName("QLACK");
    contactDTO.setLastName("Team");
    contactDTO.setLocale("en");
    contactDTO.setUserID("NULL");

    return contactDTO;
  }

  public List<ContactDTO> createContactDTOs() {
    List<ContactDTO> contactDTOS = new ArrayList<>();
    contactDTOS.add(createContactDTO());

    return contactDTOS;
  }

  public DistributionListDTO createDistributionListDTO() {
    DistributionListDTO distributionListDTO = new DistributionListDTO();
    distributionListDTO.setId("30148212-7d36-4b87-b931-a27db4224edf");
    distributionListDTO.setName("Test Distribution List");
    distributionListDTO.setCreatedBy("Test User");
    distributionListDTO.setDescription("Test Description");
    distributionListDTO.setContactList(createContactDTOs());

    return distributionListDTO;
  }

  public MaiDistributionList createMaiDistributionList() {
    MaiDistributionList maiDistributionList = new MaiDistributionList();
    maiDistributionList.setId("30148212-7d36-4b87-b931-a27db4224edf");
    maiDistributionList.setDescription("Test Description");
    maiDistributionList.setListName("Test Distribution List");
    maiDistributionList.setCreatedBy("Test User");

    return maiDistributionList;
  }

  public List<MaiDistributionList> createMaiDistributionLists() {
    List<MaiDistributionList> maiDistributionLists = new ArrayList<>();
    maiDistributionLists.add(createMaiDistributionList());

    return maiDistributionLists;
  }

  public MaiContact createMaiContact() {
    MaiContact maiContact = new MaiContact();
    maiContact.setId("23d42043-9ec1-4446-b309-6b748f05fb8c");
    maiContact.setEmail(EMAIL);
    maiContact.setFirstName("QLACK");
    maiContact.setLastName("Team");
    maiContact.setLocale("en");
    maiContact.setUserId("NULL");

    return maiContact;
  }

  public InternalMessagesDTO createInternalMessagesDTO() throws IOException {
    InternalMessagesDTO internalMessagesDTO = new InternalMessagesDTO();
    internalMessagesDTO.setId("f9726eb8-edce-4216-b71b-72b88a8377be");
    internalMessagesDTO.setSubject("Test Subject");
    internalMessagesDTO.setMessage("Test Message");
    internalMessagesDTO.setFrom(EMAIL);
    internalMessagesDTO.setTo(EMAIL);
    internalMessagesDTO.setInternalAttachments(createInternalMsgAttachmentDTOs());
    internalMessagesDTO.setDateSent(new Date());
    internalMessagesDTO.setDateReceived(new Date());

    return internalMessagesDTO;
  }

  public InternalMsgAttachmentDTO createInternalMsgAttachmentDTO() throws IOException {
    InternalMsgAttachmentDTO internalMsgAttachmentDTO = new InternalMsgAttachmentDTO();
    internalMsgAttachmentDTO.setId("d156c45c-330d-478e-8f02-c2d8cb2f5687");
    internalMsgAttachmentDTO.setFilename("test_attachment.png");
    File fi = new File("src/test/resources/test_attachment.png");
    internalMsgAttachmentDTO.setData(Files.readAllBytes(fi.toPath()));
    internalMsgAttachmentDTO.setContentType("img/png");

    return internalMsgAttachmentDTO;
  }

  public List<InternalMsgAttachmentDTO> createInternalMsgAttachmentDTOs() throws IOException {
    List<InternalMsgAttachmentDTO> internalMsgAttachmentDTOS = new ArrayList<>();
    internalMsgAttachmentDTOS.add(createInternalMsgAttachmentDTO());

    return internalMsgAttachmentDTOS;
  }

  public MaiInternalAttachment createMaiInternalAttachment() throws IOException {
    MaiInternalAttachment maiInternalAttachment = new MaiInternalAttachment();
    maiInternalAttachment.setId("d156c45c-330d-478e-8f02-c2d8cb2f5687");
    maiInternalAttachment.setFilename("test_attachment.png");
    File fi = new File("src/test/resources/test_attachment.png");
    maiInternalAttachment.setData(Files.readAllBytes(fi.toPath()));
    maiInternalAttachment.setContentType("img/png");
    maiInternalAttachment.setMessagesId(createMaiInternalMessages());

    return maiInternalAttachment;
  }

  public List<MaiInternalAttachment> createMaiInternalAttachments() throws IOException {
    List<MaiInternalAttachment> maiInternalAttachments = new ArrayList<>();
    maiInternalAttachments.add(createMaiInternalAttachment());

    return maiInternalAttachments;
  }

  public MaiInternalMessages createMaiInternalMessages() {
    MaiInternalMessages maiInternalMessages = new MaiInternalMessages();
    maiInternalMessages.setId("f9726eb8-edce-4216-b71b-72b88a8377be");
    maiInternalMessages.setSubject("Test Subject");
    maiInternalMessages.setMessage("Test Message");
    maiInternalMessages.setMailFrom(EMAIL);
    maiInternalMessages.setMailTo(EMAIL);
    maiInternalMessages.setDateSent(new Date().getTime());
    maiInternalMessages.setDateReceived(new Date().getTime());

    return maiInternalMessages;
  }

  public List<MaiInternalMessages> createMaiInternalMessagesList() {
    List<MaiInternalMessages> maiInternalMessages = new ArrayList<>();
    maiInternalMessages.add(createMaiInternalMessages());

    return maiInternalMessages;
  }

}
