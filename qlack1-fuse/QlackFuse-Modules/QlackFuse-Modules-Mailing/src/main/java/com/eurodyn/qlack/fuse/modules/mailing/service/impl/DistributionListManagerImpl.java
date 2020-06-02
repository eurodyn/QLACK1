package com.eurodyn.qlack.fuse.modules.mailing.service.impl;

import com.eurodyn.qlack.fuse.modules.mailing.dto.ContactDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.DistributionListDTO;
import com.eurodyn.qlack.fuse.modules.mailing.exception.QlackFuseMailingException;
import com.eurodyn.qlack.fuse.modules.mailing.service.DistributionListManager;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Stateless(name = "DistributionListManagerImpl")
public class DistributionListManagerImpl implements DistributionListManager {

  @PersistenceContext(unitName = "QlackFuse-Mailing-PU")
  private EntityManager entityManager;

  @Override
  public void createDistributionList(DistributionListDTO dto) throws QlackFuseMailingException {

  }

  @Override
  public void editDistributionList(DistributionListDTO dto) throws QlackFuseMailingException {

  }

  @Override
  public void deleteDistributionList(String id) {

  }

  @Override
  public DistributionListDTO find(Object id) {
    return null;
  }

  @Override
  public List<DistributionListDTO> search(DistributionListDTO dto) {
    return null;
  }

  @Override
  public void addContactToDistributionList(String distributionId, String contactID) {

  }

  @Override
  public void createContact(ContactDTO dto) {

  }

  @Override
  public void removeContactFromDistributionList(String distributionId, String contactID) {

  }
}
