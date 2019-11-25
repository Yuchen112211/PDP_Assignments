/**
 * This interface represents a simple XML parser that accepts input one
 * character at a time.
 */
public interface XMLParser {
  /**
   * Accept a single character as input, and return the new parser as a result
   * of handling this character.
   *
   * @param c the input character
   * @return the parser after handling the provided character
   * @throws InvalidXMLException if the input causes the XML to be invalid
   */
  XMLParser input(char c) throws InvalidXMLException;

  /**
   * Return the current information which is necessary, should be implemented
   * in following specific methods.
   *
   * @return  the specific information that chose to be presented by method.
   */
  String output();
}
