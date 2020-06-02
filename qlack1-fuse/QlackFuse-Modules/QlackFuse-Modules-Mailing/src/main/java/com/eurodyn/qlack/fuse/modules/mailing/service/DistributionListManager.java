package com.eurodyn.qlack.fuse.modules.mailing.service;

import com.eurodyn.qlack.fuse.modules.mailing.dto.ContactDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.DistributionListDTO;
import com.eurodyn.qlack.fuse.modules.mailing.exception.QlackFuseMailingException;
import javax.ejb.Remote;

import java.util.List;

/**
 * A Stateless Session EJB provides services to manage distribution list.
 *
 * @author European Dynamics SA.
 */
@Remote
public interface DistributionListManager {

  void createDistributionList(DistributionListDTO dto) throws QlackFuseMailingException;

  void editDistributionList(DistributionListDTO dto) throws QlackFuseMailingException;

  void deleteDistributionList(String id);

  DistributionListDTO find(Object id);

  List<DistributionListDTO> search(DistributionListDTO dto);

  void addContactToDistributionList(String distributionId, String contactID);

  void createContact(ContactDTO dto);

  void removeContactFromDistributionList(String distributionId, String contactID);
}
