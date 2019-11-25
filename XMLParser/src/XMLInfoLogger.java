import java.util.ArrayList;

/**
 * XMLInfoLogger not only has all the function that the XMLValidator has,
 * this class can also parse the characters that has been input.
 * Most of the code in XMLInfoLogger is the same as XMLValidator.
 *
 */
public class XMLInfoLogger extends XML {

  //New element information: record the characters if it is not contained in tags.
  private String information = "";
  //New element outputs: record the outputs in designed form.
  private ArrayList<String> outputs = new ArrayList<>();

  @Override
  public XMLParser input(char c) throws InvalidXMLException {
    if (c == '<') {
      if (this.start_tag_input) {
        throw new InvalidXMLException("The tag is not closed, "
                + "can not open another tag");
      } else if (this.has_root && this.tags.size() == 0) {
        throw new InvalidXMLException("Every XML can only have one root");
      }
      else {
        if (!this.has_root) {
          this.has_root = true;
        }
        this.start_tag_input = true;
      }
    } else if (c != '<' && c != '>') {
      if (!this.start_tag_input) {
        if (this.tags.size() == 0 && c != ' ' && c != '\n' && c != '\b') {
          throw new InvalidXMLException("The information can not be outside "
                  + "of the tags");
        } else {
          //Add the character c into information
          this.information += c;
        }
      } else {
        //Determine if there's illegal character input.
        if (!Character.isDigit(c) && !Character.isLetter(c)
                && c != ':' && c != '_' && c != '-' && c != '/') {
          throw new InvalidXMLException("Input of a tag can only "
                  + "be characters,digit and '-' or '_'.");
        }
        if (this.is_first) {
          if (c == '-' || Character.isDigit(c)) {
            throw new InvalidXMLException("Can not start a tag with '-' or digit.");
          }
          this.is_first = false;
          this.current_tag += c;
          if (c == '/') {
            this.is_first = true;
            this.start_tag_closing = true;
          }
          else if (this.start_tag_closing) {
            if (this.tags.peek().charAt(0) != c) {
              throw new InvalidXMLException("The tags you close now does not pair "
                      + "the previous opened tag");
            }
          }
        }
        else {
          this.current_tag += c;
          if (start_tag_closing) {
            String previous_tag = this.tags.peek();
            int length_now = this.current_tag.length();
            if (!previous_tag.substring(0,length_now - 1).equals(this.current_tag.substring(1))) {
              throw new InvalidXMLException("The tags you close now does not pair "
                      + "the previous opened tag");
            }
          }
        }
      }
    } else if (c == '>') {
      if (!this.start_tag_input) {
        throw new InvalidXMLException("The tag has not started, "
                + "can not close tag");
      } else {
        if (this.current_tag.charAt(0) == '/') {
          if (this.tags.size() == 0) {
            throw new InvalidXMLException("There's no tag initiated before, "
                    + "can not pair the tags");
          } else {
            String to_pair = this.tags.pop();
            this.current_tag = this.current_tag.substring(1);
            if (!to_pair.equals(this.current_tag)) {
              throw new InvalidXMLException("The tags you close now does not pair "
                      + "the previous opened tag");
            }
            else {
              if (!this.information.equals("")) {
                //The information is completed, add the information into outputs.
                this.outputs.add("Characters:" + this.information);
              }
              //The closed tag is completed, add the ended tag into outs.
              this.outputs.add("Ended:" + this.current_tag);
            }
          }
          this.start_tag_closing = false;
        } else {
          this.tags.push(this.current_tag);
          if (!this.information.equals("")) {
            // When the tag is closed, if the information is not empty, add it to outputs.
            this.outputs.add("Characters:" + this.information);
          }
          //When the opened tag is closed, add it to outputs.
          this.outputs.add("Started:" + this.current_tag);
        }
        this.current_tag = "";
        this.information = "";
        this.is_first = true;
        this.start_tag_input = false;
      }
    }
    if (this.tags.size() == 0 && this.is_first && !this.start_tag_input) {
      this.status = "Status:Valid";
    }
    else {
      this.status = "Status:Incomplete";
    }
    return this;
  }

  /**
   * Parse all the output data that has been added before.
   * The method forms all the data that has been input before, add necessary prefix,then
   * return the whole data as a string.
   *
   * @return rst which is all the outputs with \n character
   */
  @Override
  public String output() {
    String rst = "";
    for (int i = 0; i < this.outputs.size(); i ++ ) {
      rst += this.outputs.get(i);
      rst += '\n';
    }
    return rst;
  }

}