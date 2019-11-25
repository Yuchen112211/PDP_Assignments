
/**
 * XMLValidator class provides the dynamic method dispatch of XML.
 * This class mainly provide functions about determining whether the input characters
 * are valid, if not, throw the exceptions that has been designed before, if valid,
 * change the status private element of the class.
 *
 */
public class XMLValidator extends XML {

  /**
   * Method who take single character as input.
   * Every character that has been entered will be tested whether it will cause
   * the current XML file to be invalid. If so, throw the InvalidXMLException.
   *
   * @param c the input character
   * @return the parser after handling the provided character
   * @throws InvalidXMLException if the input causes the XML to be invalid
   */
  @Override
  public XMLParser input(char c) throws InvalidXMLException {
    if (c == '<') {
      if (this.start_tag_input) {
        // There is an unclosed tag before, cannot open another tag.
        throw new InvalidXMLException("The tag is not closed, "
                + "can not open another tag");
      } else if (this.has_root && this.tags.size() == 0) {
        throw new InvalidXMLException("Every XML can only have one root");
      }
      else {
        if (!this.has_root) {
          // Record there's a root appeared.
          this.has_root = true;
        }
        this.start_tag_input = true;
      }
    } else if (c != '<' && c != '>') {
      if (!this.start_tag_input) {
        if (this.tags.size() == 0 && c != ' ' && c != '\n' && c != '\b') {
          //this.tags.size() is 0 indicates that there's no completed tag before
          //current character, which would result in throwing exceptions.
          throw new InvalidXMLException("The information can not be outside "
                  + "of the tags");
        }
      } else {
        //Determine if there's illegal character input.
        if (!Character.isDigit(c) && !Character.isLetter(c)
                && c != ':' && c != '_' && c != '-' && c != '/') {
          throw new InvalidXMLException("Input of a tag can only "
                  + "be characters,digit and '-' or '_'.");
        }
        if (this.is_first) {
          //First character of a tag cannot be digit or '-'
          if (c == '-' || Character.isDigit(c)) {
            throw new InvalidXMLException("Can not start a tag with '-' or digit.");
          }
          this.is_first = false;
          this.current_tag += c;
          if (c == '/') {
            //If the tag is a closed tag(determined by '/'), the '/' is not considered
            //the first character.
            if (this.tags.size() == 0) {
              throw new InvalidXMLException("Can not close a tag when there's no tag opened");
            }
            this.is_first = true;
            //Record current tag is a closed tag.
            this.start_tag_closing = true;
          }
          else if (this.start_tag_closing) {
            if (this.tags.peek().charAt(0) != c) {
              //Compare the pairing tags, if any character is not the same, throw exception
              throw new InvalidXMLException("The tags you close now does not pair "
                      + "the previous opened tag");
            }
          }
        }
        else {
          //Forms the tag's content by adding characters.
          this.current_tag += c;
          if (start_tag_closing) {
            //Peek the top of the tags stack.
            String previous_tag = this.tags.peek();
            int length_now = this.current_tag.length();
            //Determine if the opened tag and the closing tag can pair up.
            if (!previous_tag.substring(0,length_now - 1).equals(this.current_tag.substring(1))) {
              throw new InvalidXMLException("The tags you close now does not pair "
                      + "the previous opened tag");
            }
          }
        }
      }
    } else if (c == '>') {
      //Cannot close a tag before open one.
      if (!this.start_tag_input) {
        throw new InvalidXMLException("The tag has not started, "
                + "can not close tag");
      } else {
        if (this.current_tag.charAt(0) == '/') {
          //If there's no tag to pair up, can not create a close tag.
          if (this.tags.size() == 0) {
            throw new InvalidXMLException("There's no tag opened before, "
                    + "can not pair the tags");
          } else {
            //Pop out the tag that opened closest before, compare the two tags.
            //If not paired, throw exception.
            String to_pair = this.tags.pop();
            this.current_tag = this.current_tag.substring(1);
            if (!to_pair.equals(this.current_tag)) {
              throw new InvalidXMLException("The tags you close now does not pair "
                      + "the previous opened tag");
            }
            //Update the start_tag_closing status.
            this.start_tag_closing = false;
          }
        } else {
          //First character is not '/', and it did not throw any exception
          this.tags.push(this.current_tag);
        }
        //Update all the status.
        this.current_tag = "";
        this.is_first = true;
        this.start_tag_input = false;
      }
    }
    if (this.tags.size() == 0 && this.is_first && !this.start_tag_input) {
      //If all the input has been processed and no exception has been thrown, update
      //the status to Valid.
      this.status = "Status:Valid";
    }
    else {
      //The status is incomplete as long as there's opened tag that has not been paired.
      this.status = "Status:Incomplete";
    }
    return this;
  }

  /**
   * The method is used to observe the current status of the validator.
   *
   * @return String this.status
   */
  @Override
  public String output() {

    return this.status;
  }

  public static void main(String[] args) throws InvalidXMLException {
    XML x = new XMLValidator();
    x.input('<');
    x.input('<');
    x.input('<');
    x.input('<');
  }
}
