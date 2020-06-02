package com.eurodyn.qlack.fuse.modules.lexicon.service;

import com.eurodyn.qlack.commons.search.PagingParams;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexGroupDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.exception.QlackFuseLexiconException;
import javax.ejb.Remote;

/**
 * Remote interface for Group management
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Remote
public interface GroupManager {

  /**
   * This method creates Group and returns the original lexGroupDTO populated with the ID of the newly created group.
   *
   * @param lexGroupDTO LexGroupDTO
   * @return lexGroupDTO
   * @throws QlackFuseLexiconException Throws exception if provided lexGroupDTO is null or provided group name already
   * exists.
   */
  LexGroupDTO createGroup(LexGroupDTO lexGroupDTO) throws QlackFuseLexiconException;

  /**
   * This method deletes group for provided group ID.
   *
   * @throws QlackFuseLexiconException Throws exception if provided group ID is null
   */
  void deleteGroupByID(String groupID) throws QlackFuseLexiconException;

  /**
   * This method retrieves group DTO for provided group ID
   *
   * @return group DTO
   * @throws QlackFuseLexiconException Throws exception if provided lexGroupDTO is null
   */
  LexGroupDTO viewGroupByID(String groupID) throws QlackFuseLexiconException;

  /**
   * This method updates group for provided Group DTO
   *
   * @return lexGroupDTO
   * @throws QlackFuseLexiconException Throws exception provided lexGroupDTO is null or provided group name already
   * exists.
   */
  LexGroupDTO updateGroup(LexGroupDTO lexGroupDTO) throws QlackFuseLexiconException;

  /**
   * This method returns array of Group DTO for provided pagination parameter
   *
   * @param paging parameters (pageSize and currentPage)
   * @return GroupDTOs
   */
  LexGroupDTO[] listGroups(PagingParams paging);

  /**
   * Performs search on the title and the description of the Lexicon group.
   *
   * @return LexGroupDTOs
   * @throws QlackFuseLexiconException Throws exception if provided searchTerm is null.
   */
  LexGroupDTO[] searchGroups(String searchTerm, PagingParams paging)
      throws QlackFuseLexiconException;
}
