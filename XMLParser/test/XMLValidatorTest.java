import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * XMLValidatorTest is the test designed to test class XMLValidator
 * Include 12 different test, cover every aspects.
 */
public class XMLValidatorTest {
  private XMLValidator validator;

  /**
   * Given a String, use for loop to input every character of the String.
   * If throw exception then stop the input. If completed the input of the String then
   * return a String "Success" indicates all the input is valid.
   * @param tmp  which is the input string.
   * @return "Success" or throw InvalidXMLException
   */
  private String inputString(String tmp) {
    try {
      for (int i = 0; i < tmp.length(); i ++ ) {
        this.validator.input(tmp.charAt(i));
      }
      return "Success";
    }
    catch (InvalidXMLException e) {
      return e.getMessage();
    }
  }

  /**
   * Before every test, initiate a new XMLValidator.
   */
  @Before
  public void setup() {
    validator = new XMLValidator();
  }

  /**
   * Test if the first character of a tag can be digit.
   */
  @Test
  public void testTagBeginInDigit1() {
    try {
      this.validator.input('<');
      this.validator.input('8');
    }
    catch (InvalidXMLException e) {
      assertEquals("Can not start a tag with '-' or digit.",e.getMessage());
    }
  }

  /**
   * Test if the first character of a tag can be digit.
   */
  @Test
  public void testTagBeginInDigit2() {
    try {
      this.validator.input('<');
      this.validator.input('3');
    }
    catch (InvalidXMLException e) {
      assertEquals("Can not start a tag with '-' or digit.",e.getMessage());
    }
  }

  /**
   * Test if the first character of a tag can be '-'.
   */
  @Test
  public void testTagBeginInMinus1() {
    try {
      this.validator.input('<');
      this.validator.input('-');
    }
    catch (InvalidXMLException e) {
      assertEquals("Can not start a tag with '-' or digit.",e.getMessage());
    }
  }

  /**
   * Test if the first character of a tag can be '-'.
   */
  @Test
  public void testTagBeginInMinus2() {
    try {
      this.validator.input('<');
      this.validator.input('h');
      this.validator.input('t');
      this.validator.input('m');
      this.validator.input('l');
      this.validator.input('>');
      this.validator.input('<');
      this.validator.input('-');
    }
    catch (InvalidXMLException e) {
      assertEquals("Can not start a tag with '-' or digit.",e.getMessage());
    }
  }

  /**
   * Test if the tag can contain illegal character.
   */
  @Test
  public void testTagContainIllegalCharacter1() {
    try {
      this.validator.input('<');
      this.validator.input('h');
      this.validator.input('t');
      this.validator.input(' ');
      this.validator.input('l');
      this.validator.input('>');
    }
    catch (InvalidXMLException e) {
      assertEquals("Input of a tag can only be characters,digit and '-' or '_'.",e.getMessage());
    }
  }

  /**
   * Test if the tag can contain illegal character.
   */
  @Test
  public void testTagContainIllegalCharacter2() {
    try {
      this.validator.input('<');
      this.validator.input('h');
      this.validator.input('t');
      this.validator.input('&');
      this.validator.input('l');
      this.validator.input('>');
    }
    catch (InvalidXMLException e) {
      assertEquals("Input of a tag can only be characters,digit and '-' or '_'.",e.getMessage());
    }
  }

  /**
   * Test if the tags can be opened multiply (multiple '<').
   */
  @Test
  public void testTagNotClosedReOpen() {
    try {
      this.inputString("<html");
      this.validator.input('<');
    }
    catch (InvalidXMLException e) {
      assertEquals("The tag is not closed, can not open another tag",
              e.getMessage());
    }
  }

  /**
   * Test if the tag can be closed before it is opened ('>' before '<').
   */
  @Test
  public void testCloseTagBeforeOpen() {
    try {
      this.validator.input('>');
    }
    catch (InvalidXMLException e) {
      assertEquals("The tag has not started, can not close tag",e.getMessage());
    }
  }

  /**
   * Test if the XML can have more than one root.
   */
  @Test
  public void testTagNotOnlyRoot() {
    String inputs = "<html>This is a head</html>";
    try {
      this.inputString(inputs);
      this.validator.input('<');
    }
    catch (InvalidXMLException e) {
      assertEquals("Every XML can only have one root", e.getMessage());
    }

  }

  /**
   * Test if the characters can be input before any tag is initiated.
   */
  @Test
  public void testTagInformationOutsideOfTags() {
    String inputs = "<html>This is a head</html>";
    try {
      this.inputString(inputs);
      this.validator.input('a');
    }
    catch (InvalidXMLException e) {
      assertEquals("The information can not be outside of the tags", e.getMessage());
    }
  }

  /**
   * Test if the closing tag can be input when it does not pair the opened tag.
   */
  @Test
  public void testClosedTagDoesNotPairOpened1() {
    String inputs = "<html>This is a head</abcd>";
    assertEquals("The tags you close now does not pair the previous opened tag",
            this.inputString(inputs));
  }

  /**
   * Test if the closing tag can be input when it does not pair the opened tag.
   */
  @Test
  public void testClosedTagDoesNotPairOpened2() {
    String inputs = "<html>This is a head</a";
    assertEquals("The tags you close now does not pair the previous opened tag",
            this.inputString(inputs));
  }

  /**
   * Test if the tag can be a end tag when there is no start tag before.
   */
  @Test
  public void testNoTagOpenedCanNotClose() {
    try {
      this.validator.input('<');
      this.validator.input('/');
    }
    catch (InvalidXMLException e) {
      assertEquals("There's no tag opened before, can not pair the tags",e.getMessage());
    }
  }

  /**
   * Test the status of the validator of each different operation.
   */
  @Test
  public void testStatus() {
    assertEquals("Status:Empty",this.validator.output());
    String exception_message = this.inputString("<html>This is html");
    if (exception_message.equals("Success")) {
      assertEquals("Status:Incomplete", this.validator.output());
    }
    exception_message = this.inputString("<body></body>");
    if (exception_message.equals("Success")) {
      assertEquals("Status:Incomplete", this.validator.output());
    }
    exception_message = this.inputString("</html>");
    if (exception_message.equals("Success")) {
      assertEquals("Status:Valid", this.validator.output());
    }
  }
}