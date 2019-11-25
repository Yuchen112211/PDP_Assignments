import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * XMLInfoLogger is the test designed to test class XMLInfoLogger
 * Include 15 different test, cover every aspects.
 * Most of the tests are the same in XMLinfoLoggerTest since the InfoLogger not only need to
 * determine if the input is valid, and also parse the data.
 */
public class XMLInfoLoggerTest {
  private XMLInfoLogger infoLogger;

  private String inputString(String tmp) {
    try {
      for (int i = 0; i < tmp.length(); i ++ ) {
        this.infoLogger.input(tmp.charAt(i));
      }
      return "Success";
    }
    catch (InvalidXMLException e) {
      return e.getMessage();
    }
  }

  @Before
  public void setup() {
    infoLogger = new XMLInfoLogger();
  }


  @Test
  public void testTagBeginInDigit1() {
    try {
      this.infoLogger.input('<');
      this.infoLogger.input('8');
    }
    catch (InvalidXMLException e) {
      assertEquals("Can not start a tag with '-' or digit.",e.getMessage());
    }
  }

  @Test
  public void testTagBeginInDigit2() {
    try {
      this.infoLogger.input('<');
      this.infoLogger.input('3');
    }
    catch (InvalidXMLException e) {
      assertEquals("Can not start a tag with '-' or digit.",e.getMessage());
    }
  }

  @Test
  public void testTagBeginInMinus1() {
    try {
      this.infoLogger.input('<');
      this.infoLogger.input('-');
    }
    catch (InvalidXMLException e) {
      assertEquals("Can not start a tag with '-' or digit.",e.getMessage());
    }
  }

  @Test
  public void testTagBeginInMinus2() {
    try {
      this.infoLogger.input('<');
      this.infoLogger.input('h');
      this.infoLogger.input('t');
      this.infoLogger.input('m');
      this.infoLogger.input('l');
      this.infoLogger.input('>');
      this.infoLogger.input('<');
      this.infoLogger.input('-');
    }
    catch (InvalidXMLException e) {
      assertEquals("Can not start a tag with '-' or digit.",e.getMessage());
    }
  }

  @Test
  public void testTagContainIllegalCharacter1() {
    try {
      this.infoLogger.input('<');
      this.infoLogger.input('h');
      this.infoLogger.input('t');
      this.infoLogger.input(' ');
      this.infoLogger.input('l');
      this.infoLogger.input('>');
    }
    catch (InvalidXMLException e) {
      assertEquals("Input of a tag can only be characters,digit and '-' or '_'.",e.getMessage());
    }
  }

  @Test
  public void testTagContainIllegalCharacter2() {
    try {
      this.infoLogger.input('<');
      this.infoLogger.input('h');
      this.infoLogger.input('t');
      this.infoLogger.input('&');
      this.infoLogger.input('l');
      this.infoLogger.input('>');
    }
    catch (InvalidXMLException e) {
      assertEquals("Input of a tag can only be characters,digit and '-' or '_'.",e.getMessage());
    }
  }

  @Test
  public void testTagNotClosedReOpen() {
    try {
      this.inputString("<html");
      this.infoLogger.input('<');
    }
    catch (InvalidXMLException e) {
      assertEquals("The tag is not closed, can not open another tag",
              e.getMessage());
    }
  }

  @Test
  public void testCloseTagBeforeOpen() {
    try {
      this.infoLogger.input('>');
    }
    catch (InvalidXMLException e) {
      assertEquals("The tag has not started, can not close tag",e.getMessage());
    }
  }

  @Test
  public void testTagNotOnlyRoot() {
    String inputs = "<html>This is a head</html>";
    try {
      this.inputString(inputs);
      this.infoLogger.input('<');
    }
    catch (InvalidXMLException e) {
      assertEquals("Every XML can only have one root", e.getMessage());
    }

  }

  @Test
  public void testTagInformationOutsideOfTags() {
    String inputs = "<html>This is a head</html>";
    try {
      this.inputString(inputs);
      this.infoLogger.input('a');
    }
    catch (InvalidXMLException e) {
      assertEquals("The information can not be outside of the tags", e.getMessage());
    }
  }

  @Test
  public void testClosedTagDoesNotPairOpened1() {
    String inputs = "<html>This is a head</abcd>";
    assertEquals("The tags you close now does not pair the previous opened tag",
            this.inputString(inputs));
  }

  @Test
  public void testClosedTagDoesNotPairOpened2() {
    String inputs = "<html>This is a head</a";
    assertEquals("The tags you close now does not pair the previous opened tag",
            this.inputString(inputs));
  }

  @Test
  public void testNoTagOpenedCanNotClose() {
    try {
      this.infoLogger.input('<');
      this.infoLogger.input('/');
    }
    catch (InvalidXMLException e) {
      assertEquals("There's no tag opened before, can not pair the tags",e.getMessage());
    }
  }

  /**
   * Test if the output method can correctly parse the data input before.
   */
  @Test
  public void testParseData1() {
    String inputs = "<html> This is a body</html>";
    String rst_input = this.inputString(inputs);
    assertEquals("Success",rst_input);
    String output = this.infoLogger.output();
    assertEquals("Started:html\n"
            + "Characters: This is a body\n"
            + "Ended:html\n",output);
  }

  /**
   * Test if the output method can correctly parse the data input before.
   */
  @Test
  public void testParseData2() {
    String inputs = "<html> This is \\n a body <";
    String rst_input = this.inputString(inputs);
    assertEquals("Success",rst_input);
    String output = this.infoLogger.output();
    assertEquals("Started:html\n",output);
  }

  /**
   * Test if the output method can correctly parse the data input before.
   */
  @Test
  public void testParseData3() {
    String inputs = "<html> This is    a body</html>";
    String rst_input = this.inputString(inputs);
    assertEquals("Success",rst_input);
    String output = this.infoLogger.output();
    assertEquals("Started:html\n"
            + "Characters: This is    a body\n"
            + "Ended:html\n",output);
  }

  /**
   * Test if the output method can correctly parse the data input before.
   */
  @Test
  public void testParseData4() {
    String inputs = "<html>_<head> This is a heading</head><p>Paragraph</p></html>";
    String rst_input = this.inputString(inputs);
    assertEquals("Success",rst_input);
    String output = this.infoLogger.output();
    assertEquals("Started:html\n"
            + "Characters:_\n"
            + "Started:head\n"
            + "Characters: This is a heading\n"
            + "Ended:head\n"
            + "Started:p\n"
            + "Characters:Paragraph\n"
            + "Ended:p\n"
            + "Ended:html\n",output);

  }
}