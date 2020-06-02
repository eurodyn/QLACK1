package com.eurodyn.qlack.commons.string;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author European Dynamics S.A.
 */
public class StringUtilsTest {

  public StringUtilsTest() {
  }

  /**
   * Test of matchesREA method, of class StringUtils.
   */
  @Test
  public void testMatchesREA_true() {
    System.out.println("matchesREA_true");

    String originalString = "Java is a programming language.";
    String[] suffixes = {"\\w.*",
        "(Java is (not)?a programming language.)"};
    boolean expResult = true;

    boolean result = StringUtils.matchesREA(originalString, suffixes);
    assertEquals(expResult, result);
  }

  /**
   * Test of matchesREA method, of class StringUtils.
   */
  @Test
  public void testMatchesREA_false() {
    System.out.println("matchesREA_false");

    String originalString = "Java is a programming language.";
    String[] suffixes = {"\\w*",
        "(Java is(not)?a programming language.)"};
    boolean expResult = false;

    boolean result = StringUtils.matchesREA(originalString, suffixes);
    assertEquals(expResult, result);
  }

  // TODO: isEmpty function has been pernmanetly removed ???
  //    /**
  //     * Test of isEmpty method, of class StringUtils.
  //     */
  //    @Test
  //    public void testIsEmpty_false() {
  //        System.out.println("isEmpty_false");
  //
  //        String value = "This string is not empty";
  //        boolean expResult = false;
  //
  //        boolean result = StringUtils.isEmpty(value);
  //        assertEquals(expResult, result);
  //    }
  //
  //    /**
  //     * Test of isEmpty method, of class StringUtils.
  //     */
  //    @Test
  //    public void testIsEmpty_true() {
  //        System.out.println("isEmpty_true");
  //
  //        String value = "";
  //        boolean expResult = true;
  //
  //        boolean result = StringUtils.isEmpty(value);
  //        assertEquals(expResult, result);
  //    }
  //
  //    /**
  //     * Test of isEmpty method, of class StringUtils.
  //     */
  //    @Test
  //    public void testIsEmpty_null() {
  //        System.out.println("isEmpty_null");
  //
  //        String value = null;
  //        boolean expResult = true;
  //
  //        boolean result = StringUtils.isEmpty(value);
  //        assertEquals(expResult, result);
  //    }

  /**
   * Test of processHtmlLinks method, of class StringUtils.
   */
  @Test
  public void testProcessHtmlLinks() {
    System.out.println("processHtmlLinks");

    String text = "This is a link sample: http://www.google.com <br/> "
        + "And this is another link: http://www.yahoo.com";
    boolean isHtmlEscaped = false;
    String expResult = "This is a link sample: <a href=\"http://www.google.com\">"
        + "http://www.google.com</a> <br/> And this is another link: "
        + "<a href=\"http://www.yahoo.com\">http://www.yahoo.com</a>";

    String result = StringUtils.processHtmlLinks(text, isHtmlEscaped);
    assertEquals(expResult, result);
  }

  /**
   * Test of repeat method, of class StringUtils.
   */
  @Test
  public void testRepeat() {
    System.out.println("repeat");

    char cha = 'a';
    int repeat = 3;
    String expResult = "aaa";

    String result = StringUtils.repeat(cha, repeat);
    assertEquals(expResult, result);
  }

  /**
   * Test of removeHtmlLinks method, of class StringUtils.
   */
  @Test
  public void testRemoveHtmlLinks() {
    System.out.println("removeHtmlLinks");

    String text = "<a href=\"www.google.com\">some text</a>";
    String expResult = "some text";

    String result = StringUtils.removeHtmlLinks(text);
    assertEquals(expResult, result);
  }

  /**
   * Test of flattenArray method, of class StringUtils.
   */
  @Test
  public void testFlattenArray() {
    System.out.println("flattenArray");
    String[] a = {"One", "Two", "Three", "Four", "Five", "Six"};
    String delimiter = ";";
    String expResult = "One;Two;Three;Four;Five;Six";

    String result = StringUtils.flattenArray(a, delimiter);
    System.out.println(result);
    assertEquals(expResult, result);
  }

}