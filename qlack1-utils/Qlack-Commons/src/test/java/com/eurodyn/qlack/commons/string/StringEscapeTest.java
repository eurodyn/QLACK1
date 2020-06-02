package com.eurodyn.qlack.commons.string;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author European Dynamics S.A
 */
public class StringEscapeTest {

  public StringEscapeTest() {
  }


  /**
   * Test of escapeHtml method, of class StringEscape.
   */
  @Test
  public void testEscapeHtml() {
    System.out.println("escapeHtml");

    String originalString = "D & <key> ' non-\\ through";
    String expResult = "D &amp; &lt;key&gt; ' non-\\ through";

    String result = StringEscape.escapeHtml(originalString);
    assertEquals(expResult, result);
  }

  /**
   * Test of unescapeHtml method, of class StringEscape.
   */
  @Test
  public void testUnescapeHtml() {
    System.out.println("unescapeHtml");

    String originalString = "D &amp; &lt;key&gt; ' non-\\ through";
    String expResult = "D & <key> ' non-\\ through";

    String result = StringEscape.unescapeHtml(originalString);
    assertEquals(expResult, result);

  }

  /**
   * Test of escapeLineBreaks method, of class StringEscape.
   */
  @Test
  public void testEscapeLineBreaks() {
    System.out.println("escapeLineBreaks");

    String originalString = "This is a test stirng. \n";
    String expResult = "This is a test stirng. <br/>";

    String result = StringEscape.escapeLineBreaks(originalString);
    assertEquals(expResult, result);

  }

  /**
   * Test of escapeCarriageReturn method, of class StringEscape.
   */
  @Test
  public void testEscapeCarriageReturn() {
    System.out.println("escapeCarriageReturn");

    String originalString = "This is a test stirng.\r";
    String expResult = "This is a test stirng.";

    String result = StringEscape.escapeCarriageReturn(originalString);
    assertEquals(expResult, result);
  }

  /**
   * Test of unescapeLineBreaks method, of class StringEscape.
   */
  @Test
  public void testUnescapeLineBreaks() {
    System.out.println("unescapeLineBreaks");

    String originalString = "This is a test stirng.<br/>";
    String expResult = "This is a test stirng.\n";

    String result = StringEscape.unescapeLineBreaks(originalString);
    assertEquals(expResult, result);
  }

  /**
   * Test of removeTags method, of class StringEscape.
   */
  @Test
  public void testRemoveTags() {
    System.out.println("removeTags");

    String originalString = "This is a <label>test</label>.";
    String expResult = "This is a test.";

    String result = StringEscape.removeTags(originalString);
    assertEquals(expResult, result);
  }

}