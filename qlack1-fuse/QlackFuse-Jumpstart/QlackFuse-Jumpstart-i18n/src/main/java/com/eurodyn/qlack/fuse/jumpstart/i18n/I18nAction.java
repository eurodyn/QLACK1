package com.eurodyn.qlack.fuse.jumpstart.i18n;

import com.eurodyn.qlack.fuse.jumpstart.i18n.util.PropertiesLoaderSingleton;
import com.eurodyn.qlack.fuse.jumpstart.lookups.guice.injectors.ContextSingleton;
import com.eurodyn.qlack.fuse.modules.lexicon.dto.LexLanguageDTO;
import com.eurodyn.qlack.fuse.modules.lexicon.service.KeyManager;
import com.eurodyn.qlack.fuse.modules.lexicon.service.LanguageManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.I18nInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An action overriding the default getText() methods in order to lookup keys in the Lexicon module. NOTE: This class
 * does not support automatic calculation of expressions, so use it with care (i.e. do not include in your lookup key
 * OGNL/etc. expressions - you can simply wrap them around your getText()).
 *
 * @author European Dynamics SA.
 */
public class I18nAction extends ActionSupport {

  private static final Logger logger = Logger.getLogger(I18nAction.class.getName());
  // Prefix indicating a key that needs to be translated.
  private static final String TRANSLATABLE_TEXT_TRANSLATION_PREFIX = "@";
  // Delimiter for the parameters of a translation key.
  private static final String TRANSLATABLE_TEXT_PARAMS_DELIMITER = "\\|";
  private static final String EAR = "QlackFuseJS.i18n.parent.ear";
  private static Map<String, Map<String, String>> dictionary;
  // A helper secondary dictionary keeping only filtered keys.
  private static Map<String, Map<String, String>> secondaryDictionary;
  private static Pattern p;
  private static KeyManager keyManager;
  private static LanguageManager languageManager;
  // The locale-reduce algorithm results cache.
  private static Map<String, String> effectiveLocaleMap;
  private static List<LexLanguageDTO> activeLanguages;

  static {
    try {
      // Since EJBs get declared as Static, we can not use the @InjectEJB (i.e. pointcut runs
      // after the static initializer).
      logger.log(Level.CONFIG, "Initialising I18nAction.");
      logger.log(Level.CONFIG, "Parent EAR: {0}",
          PropertiesLoaderSingleton.getInstance().getProperty(EAR));
      keyManager = (KeyManager) ContextSingleton.getInstance().lookup("java:global/"
          + PropertiesLoaderSingleton.getInstance().getProperty(EAR)
          + "QlackFuse-Modules-Lexicon/KeyManagerBean");
      languageManager = (LanguageManager) ContextSingleton.getInstance().lookup("java:global/"
          + PropertiesLoaderSingleton.getInstance().getProperty(EAR)
          + "QlackFuse-Modules-Lexicon/LanguageManagerBean");
      logger.log(Level.CONFIG, "Fetching application translations.");
      fetchTranslations();

      // Initialise regexp to be applied when parsing lexicon entries with translations.
      p = Pattern.compile("@(\\S+)\\[(.*?)\\]|@(\\S+)");

      // Initialise the locale-reduce map.
      effectiveLocaleMap = new HashMap<String, String>();

      logger.log(Level.CONFIG, "I18nAction initialised.");
    } catch (Exception ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
    }
  }

  public static List<LexLanguageDTO> getActiveLanguages() {
    return activeLanguages;
  }

  private static void fetchTranslations() {
    LexLanguageDTO lgDTOs[] = languageManager.listLanguages(null, false);
    dictionary = new HashMap<>();
    activeLanguages = new ArrayList<>();
    secondaryDictionary = new HashMap<>();
    for (LexLanguageDTO lgDTO : lgDTOs) {
      logger.log(Level.CONFIG, "Fetching translations for locale ''{0}''.",
          lgDTO.getLocale());
      activeLanguages.add(lgDTO);
      Map<String, String> pairs =
          keyManager.getTranslationsForLocale(lgDTO.getLocale(), null);
      dictionary.put(lgDTO.getLocale(), pairs);
      // Search obtained keys to populate the Javascript dictionary.
      if (!StringUtils.isEmpty(PropertiesLoaderSingleton.getInstance()
          .getProperty("QlackFuseJS.i18n.secondary.dictionary.regex"))) {
        String filter = PropertiesLoaderSingleton.getInstance()
            .getProperty("QlackFuseJS.i18n.secondary.dictionary.regex");
        HashMap<String, String> innerDict = new HashMap<>();
        for (Entry<String, String> key : pairs.entrySet()) {
          if (key.getKey().matches(filter)) {
            innerDict.put(key.getKey(), pairs.get(key.getKey()));
          }
          secondaryDictionary.put(lgDTO.getLocale(), innerDict);
        }
      }
    }
  }

  public static synchronized void resetTranslations() {
    logger.log(Level.CONFIG, "Resetting application translations.");
    dictionary.clear();
    secondaryDictionary.clear();
    fetchTranslations();
  }

  public static synchronized void addTranslations(String locale, Map<String, String> translations) {
    Map<String, String> existingTranslations = dictionary.remove(locale);
    existingTranslations.putAll(translations);
    dictionary.put(locale, existingTranslations);
  }

  public static String getEffectiveLocale(String locale) {
    String retVal;
    if (effectiveLocaleMap.get(locale) != null) {
      retVal = effectiveLocaleMap.get(locale);
    } else {
      retVal = languageManager.getEffectiveLanguage(locale,
          PropertiesLoaderSingleton.getInstance().getProperty("QlackFuseJS.i18n.default.locale"));
      effectiveLocaleMap.put(locale, retVal);
    }

    return retVal;
  }

  private HashMap<String, String> getLgKeys(String locale) {
    Object obj = dictionary.get(locale);

    return obj != null
        ? (HashMap<String, String>) obj
        : null;
  }

  @Override
  public String getText(String aTextName) {
    return getText(aTextName, aTextName, new ArrayList<>());
  }

  @Override
  public String getText(String aTextName, String defaultValue) {
    return getText(aTextName, defaultValue, new ArrayList<>());
  }

  @Override
  public String getText(String aTextName, String defaultValue, String obj) {
    return getText(aTextName, defaultValue, Arrays.asList(obj));
  }

  @Override
  public String getText(String aTextName, List<?> args) {
    return getText(aTextName, aTextName, Arrays.asList(args));
  }

  @Override
  public String getText(String key, String[] args) {
    return getText(key, key, args);
  }

  @Override
  public String getText(String key, String defaultValue, String[] args) {
    String retVal = defaultValue;
    // Since we do not want any parsing errors here to break the rendering of the page,
    // we simply log them.
    try {
      logger.log(Level.FINEST, "Fetching key ''{0}'', for locale ''{1}'' with default value "
          + "''{2}'' and parameters ''{3}''.", new Object[]{key, getLocale(),
          defaultValue, args});

      // Find the keys of the effective locale.
      HashMap<String, String> lgObj = getLgKeys(getEffectiveLocale(getLocale().toString()));
      if (lgObj != null) {
        String keyObj = lgObj.get(key);
        if (keyObj != null) {
          retVal = keyObj;
          // Note: Make sure that your translations do not contain characters that may
          // confuse Messageformat, namely {, } and '. If such characters need to be
          // retained in your final string you need to properly escape them.
          retVal = MessageFormat.format(retVal, (Object[]) args);
        }
      }
    } catch (Exception ex) {
      logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
    }

    return retVal;
  }

  @Override
  public String getText(String key, String defaultValue, List<?> args) {
    List<String> processedArgs = null;
    // In case the List has come from Freemarker it is wraped in a SequenceAdapter which we
    // need first to unwrap.
    if (args != null && !args.isEmpty() && args.get(0) instanceof AbstractList) {
      AbstractList al = (AbstractList) args.get(0);
      processedArgs = new ArrayList<>(al.size());
      for (Object o : al) {
        processedArgs.add(o.toString());
      }
    } else {
      processedArgs = (List<String>) args;
    }

    return getText(key, defaultValue, processedArgs != null
        ? processedArgs.toArray(new String[processedArgs.size()])
        : new String[0]);
  }

  @Override
  public String getText(String key, String defaultValue, List<?> args, ValueStack stack) {
    // Valustack is ignored - see introductory comment.
    return getText(key, defaultValue, args);
  }

  @Override
  public String getText(String key, String defaultValue, String[] args, ValueStack stack) {
    // Valustack is ignored - see introductory comment.
    return getText(key, defaultValue, Arrays.asList(args));
  }

  @Override
  public ResourceBundle getTexts() {
    logger.log(Level.WARNING, "Requested unimplemented getTexts() from I18nAction.");
    throw new UnsupportedOperationException("getTexts() is not implemented by I18nAction class.");
  }

  public Map<String, String> getDictionary(String locale) {
    return dictionary.get(locale);
  }

  public Map<String, String> getSecondaryDictionary(String locale) {
    return secondaryDictionary.get(locale);
  }

  /**
   * This is a helper method to be used in FTLs when they need to display values from the database which contain
   * translations and need to be evaluated while rendering the page. Translation keys are denoted with the "@" prefix in
   * that case and can contain an unbound paremeter list within [ ]. For example: @mytranslationkey[param1|param2|....|paramN].
   *
   * @param txt The text to be evaluated.
   * @return The text representation having all dynamic translations evaluated.
   */
  public String parseTranslatableText(String txt) {
    try {
      if (txt.contains(TRANSLATABLE_TEXT_TRANSLATION_PREFIX)) {
        Matcher m = p.matcher(txt);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
          if (m.group(1) != null) {   // A translation with arguments.
            String[] params = m.group(2).split(TRANSLATABLE_TEXT_PARAMS_DELIMITER);
            parseTranslatableTextIterator(params);
            m.appendReplacement(sb, getText(m.group(1), params));
          } else {    // A translation without arguments.
            m.appendReplacement(sb, getText(m.group(3)));
          }
        }
        return m.appendTail(sb).toString();
      } else {
        return txt;
      }
    } catch (Exception ex) { // We do not want any error here to break page-rendering.
      logger.log(Level.WARNING, "Could not parse translation '" + txt + "': "
          + ex.getLocalizedMessage(), ex);
      return txt;
    }
  }

  private void parseTranslatableTextIterator(String[] params) {
    for (int i = 0; i < params.length; i++) {
      if (params[i].startsWith(TRANSLATABLE_TEXT_TRANSLATION_PREFIX)) {
        params[i] = parseTranslatableText(
            params[i].replace("&#92;", "\\]").replace("&#91;", "\\[")
                .replace("&#124;", "\\|"));
      }
    }
  }

  /**
   * This is a helper method to include parameters in dynamic translations (
   *
   * @param txt The text to be escaped.
   * @return The original text escaped, so that it doesn't break (
   * @see parseTranslatableText) making sure that such parameters do not break the string processing taking place in (
   * @see parseTranslatableText). In fact, [ ] and | are converted to their HTML-entities equivalent.
   * @see parseTranslatableText).
   */
  public String escapeTranslatableText(String txt) {
    if (!StringUtils.isEmpty(txt)) {
      return txt.replaceAll("\\]", "&#92;").replaceAll("\\[", "&#91;").replaceAll("\\|", "&#124;");
    } else {
      return txt;
    }
  }

  private Locale getUserLocale() {
    return (Locale) ActionContext.getContext().getSession()
        .get(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE);
  }

  /**
   * Renders a Date following localisation rules.
   *
   * @param d The Date to render.
   * @return The String representation of the given Date.
   */
  public String dateToString(Date d) {
    return DateFormat.getDateInstance(DateFormat.SHORT, getUserLocale()).format(d);
  }

  /**
   * Renders a Number following localisation rules.
   *
   * @param o The Number to render.
   * @return The String representation of the given number.
   */
  public String numberToString(Object o) {
    return NumberFormat.getNumberInstance(getUserLocale()).format(o);
  }

  /**
   * Converts a String to a Date using the user's Locale stored by Struts in the Session. Note that this method expects
   * a SHORT date format to work.
   *
   * @param d The string representing the date in SHORT format.
   * @return A Date representation of the String.
   * @throws ParseException If the string could not be parsed to a date.
   */
  public Date stringToDate(String d) throws ParseException {
    return DateFormat.getDateInstance(DateFormat.SHORT, getUserLocale()).parse(d);
  }

  /**
   * Converts a String to a Number using the user's Locale stored by Struts in the Session.
   *
   * @param n The string representing the Number.
   * @return The Number representationof the String.
   * @throws ParseException If the string could not be parsed to a Number.
   */
  public Number stringToNumber(String n) throws ParseException {
    return NumberFormat.getNumberInstance(getUserLocale()).parse(n);
  }
}
