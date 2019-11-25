import java.util.Stack;

/**
 * The abstract class that implements the interface XMLParser.
 * The Class has two exactly same methods as the interface.
 * Class XML shows the dynamic dispatch of Java.
 */
abstract class XML implements XMLParser {

  //current_tag: To record the tag that is being inputting.
  public String current_tag = "";
  //tags: To record all opened tags to be paired,
  //pop once every since the closed tag has entered.
  public Stack<String> tags = new Stack<>();
  //start_tag_input: To record whether there is a tag started
  //input before ('<' input before).
  public boolean start_tag_input = false;
  //start_tag_closing: To see if the tag that is being input is a closed tag
  //('</' input before). This is used to test if the input character input now
  //does not pair up the opened tag before.
  public boolean start_tag_closing = false;
  //is_first: To record if the input character is the first character of the tag
  public boolean is_first = true;
  //has_root: To record if the XML input already has a root tag.
  public boolean has_root = false;
  //status: To record the current input status.
  public String status = "Status:Empty";

  /**
   * Abstract method input, which would be implemented in each derivative class.
   * Method should provide the functions like determine whether the input is correct,
   * record the characters that has been input, parse the data and so on.
   * Accept a single character as input, and return the new parser as a result
   * of handling this character.
   *
   * @param c the input character
   * @return the parser after handling the provided character
   * @throws InvalidXMLException if the input causes the XML to be invalid
   */
  @Override
  public abstract XMLParser input(char c) throws InvalidXMLException;

  /**
   * Abstract method output, which would be implemented in each derivative class.
   * Method should provide the information and characters that the method chose to show.
   *
   * @return the specific information that chose to be presented by method.
   */
  @Override
  public abstract String output();
}

