package com.eurodyn.qlack.fuse.modules.mailing.service.impl;

import com.eurodyn.qlack.fuse.modules.mailing.dto.ContactDTO;
import com.eurodyn.qlack.fuse.modules.mailing.dto.DistributionListDTO;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiContact;
import com.eurodyn.qlack.fuse.modules.mailing.model.MaiDistributionList;
import com.eurodyn.qlack.fuse.modules.mailing.service.DistributionListManager;
import com.eurodyn.qlack.fuse.modules.mailing.util.ConverterUtil;
import com.eurodyn.qlack.fuse.modules.mailing.util.CriteriaBuilderUtil;
import com.eurodyn.qlack.fuse.modules.mailing.util.MaiConstants;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.restrictions.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * A Stateless Session EJB provides distribution list related services. For details regarding the functionality offered
 * see the respective interfaces.
 *
 * @author European Dynamics SA.
 */
@Stateless(name = "DistributionListManagerBean")
public class DistributionListManagerBean implements DistributionListManager {

  private final CriteriaBuilderUtil criteriaBuilderUtil = new CriteriaBuilderUtil(MaiConstants.DISTRIBUTIONLIST);
  @PersistenceContext(unitName = "QlackFuse-Mailing-PU")
  private EntityManager em;

  /**
   * Create a new distribution list.
   */
  @Override
  public void createDistributionList(DistributionListDTO dto) {
    em.persist(ConverterUtil.convertToDistributionListEntity(dto));
  }

  /**
   * Edit an existing distribution list.
   */
  @Override
  public void editDistributionList(DistributionListDTO dto) {
    em.merge(ConverterUtil.convertToDistributionListEntity(dto));
  }

  /**
   * Delete a distribution list.
   */
  @Override
  public void deleteDistributionList(String id) {
    MaiDistributionList entity = findDLEntity(id);
    em.remove(entity);
  }

  /**
   * Find a specific distribution list.
   */
  @Override
  public DistributionListDTO find(Object id) {
    return ConverterUtil.convertToDistributionListDTO(em.find(MaiDistributionList.class, id));
  }

  /**
   * Find MaiDistributionList Entity object for provided id.
   *
   * @return MaiDistributionList
   */
  private MaiDistributionList findDLEntity(Object id) {
    return em.find(MaiDistributionList.class, id);
  }

  /**
   * Search for a specific distribution list, with the criteria provided. (Only the name can be provided as criteria at
   * the moment.)
   */
  @Override
  public List<DistributionListDTO> search(DistributionListDTO dto) {
    Criteria criteria = criteriaBuilderUtil.getCriteria();
    if ((dto != null) && (dto.getName() != null)) {
      criteria.add(Restrictions.eq(MaiConstants.DISTRIBUTIONLIST_NAME, dto.getName()));
    }
    Query query = criteria.prepareQuery(em);

    List<MaiDistributionList> maiDistributionList = query.getResultList();

    List<DistributionListDTO> maiDistributionDTOList = new ArrayList();

    for (MaiDistributionList maiDistribution : maiDistributionList) {
      DistributionListDTO distributionListDTO =
          ConverterUtil.convertToDistributionListDTO(maiDistribution);
      maiDistributionDTOList.add(distributionListDTO);
    }

    return maiDistributionDTOList;
  }

  /**
   * Add a contact to a distribution list.
   */
  @Override
  public void addContactToDistributionList(String distributionId, String contactID) {
    addContactsToDistributionList(distributionId, new String[]{contactID});
  }

  private MaiContact findContactEntity(String contactID) {
    return em.find(MaiContact.class, contactID);
  }

  private void addContactsToDistributionList(String distributionId, String[] contactIDs) {
    MaiDistributionList dlist = findDLEntity(distributionId);

    // Get references for all contacts to be added.
    for (String contactID : contactIDs) {
      MaiContact contact = findContactEntity(contactID);
      dlist.getMaiContacts().add(contact);
    }
  }

  /**
   * Create a new contact.
   */
  @Override
  public void createContact(ContactDTO dto) {
    MaiContact maiContact = ConverterUtil.conctactDTOToMaiContact(dto);
    em.persist(maiContact);
  }

  /**
   * Remove a contact from a distribution list.
   */
  @Override
  public void removeContactFromDistributionList(String distributionId, String contactID) {
    MaiContact contact = findContactEntity(contactID);
    MaiDistributionList dlist = findDLEntity(distributionId);
    dlist.getMaiContacts().remove(contact);
  }
}
