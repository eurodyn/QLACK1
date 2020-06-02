package com.eurodyn.qlack.commons.string;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author European Dynamics S.A.
 */
public class StringEscape {

  /**
   * Html escapes the characters in a string. For example & will become &amp;
   *
   * @param originalString The string to escape
   * @return The escaped string
   */
  public static String escapeHtml(String originalString) {
    return StringEscapeUtils.escapeHtml(originalString);
  }

  /**
   * Html un-escapes the characters in a string. For example &amp; will become &
   *
   * @param originalString The string to unescape
   * @return The unescaped string
   */
  public static String unescapeHtml(String originalString) {
    return StringEscapeUtils.unescapeHtml(originalString);
  }

  /**
   * Replaces the line breaks in a string (\n) with html line breaks (&lt;br/&gt;)
   *
   * @param originalString The string to escape
   * @return The escaped string
   */
  public static String escapeLineBreaks(String originalString) {
    return org.apache.commons.lang.StringUtils.replace(originalString, "\n", "<br/>");
  }

  /**
   * Replaces the Carriage Return in a string (\r) with blank
   *
   * @param originalString The string to escape
   * @return The escaped string
   */
  public static String escapeCarriageReturn(String originalString) {
    return org.apache.commons.lang.StringUtils.replace(originalString, "\r", "");
  }

  /**
   * Replaces html line breaks (&lt;br/&gt;) with string line breaks (\n)
   *
   * @param originalString The string to unescape
   * @return String The unescaped string
   */
  public static String unescapeLineBreaks(String originalString) {
    return org.apache.commons.lang.StringUtils.replace(originalString, "<br/>", "\n");
  }

  /**
   * Remove tags from a string.
   */
  public static String removeTags(String originalString) {
    return originalString.replaceAll("<.*?>", "");
  }
}
