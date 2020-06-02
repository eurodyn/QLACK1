package com.eurodyn.qlack.commons.string;


/**
 * This is a string utility.
 *
 * @author European Dynamics SA
 */
public class StringUtils {

  /**
   * Matches Regular Expressions.
   *
   * @return boolean true/false
   */
  public static boolean matchesREA(String originalString, String[] suffixes) {
    for (String i : suffixes) {
      if (originalString.matches(i)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Processes a string and replaces all occurrences of web addresses (sub-strings beginning with http://) with an html
   * link to the given address (&lt;a href="..."&gt; ... &lt;/a&gt;). The web addresses are considered to be ending at
   * the next occurrence of a space or a double quote.
   *
   * @param text The string to process
   * @param isHtmlEscaped A flag defining whether text is html escaped
   * @return the resulting string
   */
  public static String processHtmlLinks(String text, boolean isHtmlEscaped) {
    String result = "";
    String quoteChar = isHtmlEscaped ? "&quot;" : "\"";
    int currentChar = 0;

    int urlStart = text.indexOf("http://");
    if (urlStart == -1) {
      urlStart = text.length();
    }
    result = result.concat(text.substring(currentChar, urlStart));

    int nextSpace = text.indexOf(' ', urlStart);
    int nextQuote = text.indexOf(quoteChar, urlStart);
    int urlEnd;
    if (nextSpace == -1) {
      urlEnd = nextQuote;
    } else {
      urlEnd = (nextQuote == -1) ? nextSpace : Math.min(nextSpace, nextQuote);
    }

    while (urlStart < text.length()) {
      if (urlEnd == -1) {
        urlEnd = text.length();
      }

      String anchor = "<a href=\"" + text.substring(urlStart, urlEnd) + "\">"
          + text.substring(urlStart, urlEnd) + "</a>";
      result = result.concat(anchor);

      urlStart = text.indexOf("http://", urlEnd);
      currentChar = urlEnd;

      if (urlStart > -1) {
        nextSpace = text.indexOf(' ', urlStart);
        nextQuote = text.indexOf(quoteChar, urlStart);
        if (nextSpace == -1) {
          urlEnd = nextQuote;
        } else {
          urlEnd = (nextQuote == -1) ? nextSpace : Math.min(nextSpace, nextQuote);
        }
      } else {
        urlStart = text.length();
      }

      result = result.concat(text.substring(currentChar, urlStart));
    }

    return result;
  }

  /**
   * Filters a string using an array of filter targets. Note that this method does not replace a word with another word
   * but merely replaces a word with a set of characters to indicate that a particular word has been filtered out of a
   * string. e.g. text = The quick brown fox jumps over the quick lazy dog filter = {"the", "quick"} result = *** ****
   * brown fox jumps over *** **** lazy dog
   *
   * @param text The sentence to be filtered.
   * @param filter The words to filter out. As this method is internally using String.replaceAll you can also use RE
   * here.
   * @param filterChar The character to filter with (i.e. the '*' on the above example).
   * @param caseSensitive Whether the search for the filter words will be case sensitive or not.
   * @return The original sentence filtered out.
   */
  public static String filter(String text, String[] filter, char filterChar,
      boolean caseSensitive) {
    // Return whatever passed in in case we do not have proper arguments.
    if (text == null || text.equals("") || filter == null) {
      return text;
    }

    // Loop through the filter array and replace the words.
    String retVal = text;
    for (int i = 0; i < filter.length; i++) {
      if (caseSensitive) {
        retVal = retVal.replaceAll(filter[i], repeat(filterChar, filter[i].length() + 1));
      } else {
        retVal = retVal.replaceAll("(?i)" + filter[i], repeat(filterChar, filter[i].length() + 1));
      }
    }

    return retVal;
  }

  /**
   * Creates a new string by repeating a specific character a specified number of times.
   *
   * @param chr The character to create the string from.
   * @param repeat The number of times to repeat the character.
   * @return A string containing 'repeat' times the character 'chr'.
   */
  public static String repeat(char chr, int repeat) {
    if (repeat <= 0) {
      return null;
    }

    StringBuilder sb = new StringBuilder(chr);
    for (int i = 1; i <= repeat; i++) {
      sb.append(chr);
    }

    return sb.toString();
  }

  /**
   * Removes all html links from a string, turning them into plain text. For example &lt;a href="link"&gt;some
   * text&lt;/a&gt; will become: some text
   */
  public static String removeHtmlLinks(String text) {
    String result = "";

    int linkStart = text.indexOf("<a");
    if (linkStart == -1) {
      linkStart = text.length();
    }
    result = result.concat(text.substring(0, linkStart));

    int linkEnd = text.indexOf(">", linkStart);
    if (linkEnd == -1) {
      linkEnd = text.length();
    }

    while (linkEnd < text.length()) {
      int endLinkStart = text.indexOf("</a", linkEnd);
      int endLinkEnd = text.indexOf(">", endLinkStart);

      result = result.concat(text.substring(linkEnd + 1, endLinkStart));

      linkStart = text.indexOf("<a", endLinkEnd);

      if (linkStart > -1) {
        linkEnd = text.indexOf(">", linkStart);
        if (linkEnd == -1) {
          linkEnd = text.length();
        }
      } else {
        linkStart = text.length();
        linkEnd = text.length();
      }

      result = result.concat(text.substring(endLinkEnd + 1, linkStart));
    }

    return result;
  }

  /**
   * Converts a String array into a String with all elements of the array delimited by the user- specified delimiter
   * String.
   */
  public static String flattenArray(String[] a, String delimiter) {
    StringBuilder retVal = null;

    if (a != null) {
      retVal = new StringBuilder();
      for (int i = 0; i < a.length; i++) {
        retVal.append(a[i]);
        if (i + 1 < a.length) {
          retVal.append(delimiter != null ? delimiter : "");
        }
      }
    }

    return retVal != null ? retVal.toString() : null;
  }
}
