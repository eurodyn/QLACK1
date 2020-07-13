package com.eurodyn.qlack.fuse.modules.lexicon.service.impl;

import com.eurodyn.qlack.commons.search.PagingParams;
import com.eurodyn.qlack.fuse.commons.search.ApplyPagingParams;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexLanguageDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.exception.QlackFuseLexiconException;
import com.eurodyn.qlack.fuse.modules.lexicon.exception.QlackFuseLexiconException.CODES;
import com.eurodyn.qlack.fuse.modules.lexicon.model.LexLanguage;
import com.eurodyn.qlack.fuse.modules.lexicon.service.KeyManager;
import com.eurodyn.qlack.fuse.modules.lexicon.service.LanguageManager;
import com.eurodyn.qlack.fuse.modules.lexicon.util.ConverterUtil;
import com.eurodyn.qlack.fuse.modules.lexicon.util.LexiconValidationUtil;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.CriteriaFactory;
import net.bzdyl.ejb3.criteria.Order;
import net.bzdyl.ejb3.criteria.restrictions.Restrictions;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Stateless Session EJB and also web service implementation class providing methods for language management.
 *
 * @author EUROPEAN DYNAMICS SA.
 */
@Stateless(name = "LanguageManagerBean")
public class LanguageManagerBean implements LanguageManager {

  private static final Logger logger = Logger.getLogger(LanguageManagerBean.class.getName());
  @PersistenceContext(unitName = "QlackFuse-Lexicon-PU")
  private EntityManager em;
  @EJB(name = "KeyManagerBean")
  private KeyManager keyManager;

  /**
   * {@inheritDoc}
   *
   * @param lexLanguageDTO {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public LexLanguageDTO createLanguage(LexLanguageDTO lexLanguageDTO) {
    LexLanguage language = ConverterUtil.lexLanguageDTOtoLexLanguage(lexLanguageDTO);
    language.setCreatedOn(System.currentTimeMillis());
    em.persist(language);
    lexLanguageDTO.setId(language.getId());

    return lexLanguageDTO;
  }

  /**
   * {@inheritDoc}
   *
   * @param locale {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public void deleteLanguage(String locale) throws QlackFuseLexiconException {
    try {
      em.remove(getLanguageEntityByLocale(locale));
    } catch (IllegalArgumentException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
      throw new QlackFuseLexiconException(CODES.ERR_LEXICON_0009,
          "Language does not exist with the provided languageId.");
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param languageID {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public LexLanguageDTO viewLanguage(String languageID) throws QlackFuseLexiconException {
    LexiconValidationUtil.validateNullForLanguageID(languageID);
    LexLanguage language = em.find(LexLanguage.class, languageID);
    if (language == null) {
      return null;
    } else {
      return ConverterUtil.lexLanguageToLexLanguageDTO(language);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param locale {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public LexLanguageDTO getLanguageByLocale(String locale) throws QlackFuseLexiconException {
    LexiconValidationUtil.validateNullForLanguageCode(locale);
    Query query = em.createQuery("select l from LexLanguage l where l.locale = :locale");
    query.setParameter("locale", locale);
    LexLanguage lexLanguage = (LexLanguage) query.getSingleResult();

    return ConverterUtil.lexLanguageToLexLanguageDTO(lexLanguage);
  }

  /**
   * {@inheritDoc}
   *
   * @param lexLanguageDTO {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public LexLanguageDTO updateLanguage(LexLanguageDTO lexLanguageDTO)
      throws QlackFuseLexiconException {
    LexLanguage language = ConverterUtil.lexLanguageDTOtoLexLanguage(lexLanguageDTO);
    language.setLastModifiedOn(System.currentTimeMillis());
    em.merge(language);

    return viewLanguage(language.getId());
  }

  /**
   * {@inheritDoc}
   *
   * @param paging {@inheritDoc}
   * @param includeInactive {@inheritDoc}
   */
  @Override
  public LexLanguageDTO[] listLanguages(PagingParams paging, boolean includeInactive) {
    Criteria criteria = CriteriaFactory.createCriteria("LexLanguage");
    criteria.addOrder(Order.asc("name"));
    if (!includeInactive) {
      criteria.add(Restrictions.eq("active", true));
    }
    Query query = criteria.prepareQuery(em);
    query = ApplyPagingParams.apply(query, paging);
    List<LexLanguageDTO> retVal = new ArrayList<>();
    for (LexLanguage o : (Iterable<LexLanguage>) query.getResultList()) {
      retVal.add(ConverterUtil.lexLanguageToLexLanguageDTO(o));
    }

    return retVal.toArray(new LexLanguageDTO[retVal.size()]);
  }

  /**
   * {@inheritDoc}
   *
   * @param locale {@inheritDoc}
   * @param defaultLocale {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public String getEffectiveLanguage(String locale, String defaultLocale) {
    String retVal = null;

    Query q = em.createQuery(
        "select l from LexLanguage l "
            + "where l.locale = :locale "
            + " and l.active = :active");
    q.setParameter("locale", locale);
    q.setParameter("active", true);
    if (!q.getResultList().isEmpty()) {
      retVal = locale;
    }

    // If no value was found and the user-locale can be further reduced, try again,
    if ((retVal == null) && (locale != null) && (locale.contains("_"))) {
      String reducedLocale = locale.substring(0, locale.indexOf('_'));
      q.setParameter("locale", reducedLocale);
      if (!q.getResultList().isEmpty()) {
        retVal = reducedLocale;
      }
    }

    // If nothing worked, return the default locale passed-in.
    if (retVal == null) {
      retVal = defaultLocale;
    }

    return retVal;
  }

  private LexLanguage getLanguageEntityByLocale(String locale) {
    LexLanguage retVal = null;

    Criteria criteria = CriteriaFactory.createCriteria("LexLanguage");
    criteria.add(Restrictions.eq("locale", locale));

    List l = criteria.prepareQuery(em).getResultList();
    if (!l.isEmpty()) {
      retVal = (LexLanguage) l.get(0);
    }

    return retVal;
  }

  /**
   * {@inheritDoc}
   *
   * @param locale {@inheritDoc}
   * @param newStatus {@inheritDoc}
   */
  @Override
  public void toggleStatus(String locale, boolean newStatus) {
    LexLanguage lang = getLanguageEntityByLocale(locale);
    if (lang != null) {
      lang.setActive(newStatus);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param locale {@inheritDoc}
   * @return {@inheritDoc}
   * @throws QlackFuseLexiconException {@inheritDoc}
   */
  @Override
  public byte[] downloadLanguage(String locale) throws QlackFuseLexiconException {
    byte[] retVal = null;

    // Get the translations for the requested locale.
    Map<String, String> translations = keyManager.getTranslationsForLocale(locale, null);

    // Create an Excel workbook.
    try (Workbook wb = new HSSFWorkbook()) {
      CreationHelper createHelper = wb.getCreationHelper();
      Sheet sheet = wb.createSheet("Translations (" + locale + ")");

      // Add the header.
      Row row1 = sheet.createRow(0);
      Cell cell1 = row1.createCell(0);
      cell1.setCellValue(createHelper.createRichTextString("Translations - " + locale));

      // Add the data.
      int rowCounter = 3;
      for (Entry<String, String> key : translations.entrySet()) {
        Row row = sheet.createRow(rowCounter++);
        row.createCell(0).setCellValue(createHelper.createRichTextString(key.getKey()));
        row.createCell(1).setCellValue(createHelper.createRichTextString(translations.get(key.getKey())));
      }

      // Create the byte[] holding the Excel data.
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      wb.write(bos);
      retVal = bos.toByteArray();
    } catch (IOException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
      throw new QlackFuseLexiconException(CODES.ERR_LEXICON_0028, "Could not create Excel "
          + "byte[].");
    }

    return retVal;
  }

  /**
   * {@inheritDoc}
   *
   * @param locale {@inheritDoc}
   * @param lgXL {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public void uploadLanguage(String locale, byte[] lgXL, String modifiedBy)
      throws QlackFuseLexiconException {
    try (Workbook wb = WorkbookFactory.create(new BufferedInputStream(
        new ByteArrayInputStream(lgXL)))) {
      Sheet sheet = wb.getSheetAt(0);
      em.setFlushMode(FlushModeType.COMMIT);
      // Skip first three rows (the header of the XL file) and start parsing translations.
      for (int i = 3; i <= sheet.getLastRowNum(); i++) {
        String keyName = sheet.getRow(i).getCell(0).getStringCellValue();
        String keyValue = sheet.getRow(i).getCell(1).getStringCellValue();
        keyManager.updateTranslationByKeyName(keyName, locale, keyValue, modifiedBy);
      }
    } catch (IOException ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
      throw new QlackFuseLexiconException(CODES.ERR_LEXICON_0028, "Could not read Excel.");
    }
  }
}
