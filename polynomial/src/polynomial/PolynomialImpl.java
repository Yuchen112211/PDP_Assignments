package polynomial;

/**
 * The implementation of interface Polynomial.
 * Should be the main class that function.
 */
public class PolynomialImpl implements Polynomial {

  //Core member of the polynomial, it is linked by another term.
  public Term term;

  /**
   * The default constructor of polynomial. Does nothing.
   */
  public PolynomialImpl() {
    this.term = null;
  }

  /**
   * The constructor of polynomial which takes string as a parameter.
   * Split the string by space, form each term by designed patterns.
   *
   * @param input a string that represents the polynomial.
   */
  public PolynomialImpl(String input) throws IllegalArgumentException {
    //Each element represents a term.
    if (input.equals("")) {
      this.term = null;
      return ;
    }
    String[] split_inputs = input.split( " ");
    for (String current_string : split_inputs) {
      if (current_string.contains((CharSequence) "^")) {
        int index_string = 0;
        for (int k = 0; k < current_string.length(); k++) {
          if (current_string.charAt(k) == 'x') {
            index_string = k;
            break;
          }
        }
        int coefficient = 1;
        //Determine whether the coefficient is 1.
        if (index_string > 1) {
          coefficient = Integer.parseInt(current_string.substring(0, index_string));
        }
        int power = Integer.parseInt(current_string.substring(index_string + 2,
                current_string.length()));
        //Power cannot be less than 0.
        if (power < 0) {
          throw new IllegalArgumentException("Power cannot be negative.");
        }
        this.addTerm(coefficient, power);
      } else {
        //When the term is a constant number.
        this.addTerm(Integer.parseInt(current_string), 0);
      }
    }
  }

  /**
   * Method used to get the current term.
   *
   * @return term of current polynomial.
   */
  @Override
  public Term getTerm() {
    return this.term;
  }

  /**
   * The core method of the class, will be called in following methods.
   * Add the term to the polynomial, does not return anything.
   *
   * @param coefficient the coefficient used to form polynomial.
   * @param power the power used to form polynomial.
   */
  @Override
  public void addTerm(int coefficient, int power) throws IllegalArgumentException {
    try {
      //If the polynomial is not initiated before, the term is empty.
      if (this.term == null) {
        //Just add the term into the polynomial.
        this.term = new Term(coefficient, power);
      }
      else {
        Term iterate_term = this.term;
        //If the adding term will be the first term of the polynomial.
        if (iterate_term.getPower() < power) {
          this.term = new Term(coefficient, power);
          this.term.next = iterate_term;
          return ;
        }
        //Find where to insert the term.
        Term last_term = null;
        while (iterate_term.next != null && iterate_term.next.getPower() > power) {
          last_term = iterate_term;
          iterate_term = iterate_term.next;
        }
        Term to_be_processed = iterate_term.next;
        if (iterate_term.getPower() == power) {
          if (last_term == null) {
            if (iterate_term.getCoefficient() + coefficient == 0) {
              this.term = iterate_term.next;
            }
            else {
              this.term = new Term(iterate_term.getCoefficient() + coefficient, power);
              this.term.next = to_be_processed;
            }
          }
        }
        //If the adding term will be at the tail.
        else if (to_be_processed == null) {
          iterate_term.next = new Term(coefficient, power);
        }
        else {
          //If the adding term power is already in the polynomial.
          if (to_be_processed.getPower() == power) {

            //If the sum of the coefficients equals 0.
            if (to_be_processed.getCoefficient() + coefficient == 0) {
              iterate_term.next = to_be_processed.next;
            }
            else {
              Term to_be_saved = to_be_processed.next;
              iterate_term.next = new Term(to_be_processed.getCoefficient() + coefficient, power);
              iterate_term.next.next = to_be_saved;
            }
          }
          else {
            //Insert the term into existing two terms.
            iterate_term.next = new Term(coefficient,power);
            iterate_term.next.next = to_be_processed;
          }
        }
      }
    }
    //
    catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Get the first term's power, as the polynomial is strictly sorted when addTerm
   * method was called.
   *
   * @return the degree of the polynomial.
   */
  @Override
  public int getDegree() {
    Term iterate_term = this.term;
    return iterate_term.getPower();
  }

  /**
   * Take a integer as a parameter, which represents a power to get the related
   * coefficient in current polynomial.
   * If the power does not exist in current polynomial, return 0.
   *
   * @param power the power used to get coefficient.
   * @return the related coefficient.
   */
  @Override
  public int getCoefficient(int power) {
    Term iterate_term = this.term;
    while (iterate_term != null) {
      if (iterate_term.getPower() == power) {
        return iterate_term.getCoefficient();
      }
      else {
        iterate_term = iterate_term.next;
      }
    }
    return 0;
  }

  /**
   * Compute the polynomial with given number.
   *
   * @param number given number, in double type.
   * @return the result of computing.
   */
  @Override
  public double evaluate(double number) {
    double final_value = 0;
    Term iterate_term = this.term;
    while (iterate_term != null) {
      int coefficient_now = iterate_term.getCoefficient();
      int power_now = iterate_term.getPower();
      double current_value = number;

      for (int i = 0; i < power_now - 1; i ++ ) {
        current_value *= number;
      }
      if (power_now == 0) {
        final_value += coefficient_now;
      }
      else {
        final_value += (current_value * coefficient_now);
      }
      iterate_term = iterate_term.next;
    }
    return final_value;
  }

  /**
   * Add another polynomial to current polynomial, but make no update to existing
   * polynomial, return a new polynomial.
   *
   * @param another the other polynomial.
   * @return a brand new polynomial.
   */
  @Override
  public Polynomial add(Polynomial another) {
    Polynomial result = new PolynomialImpl();
    Polynomial first = this;

    Term current_term = null;
    if (first.getTerm() == null) {
      result = another;
      return result;
    }
    if (first.getTerm() != null) {
      current_term = first.getTerm();
    }
    else {
      result = another;
      return result;
    }
    while (current_term != null) {
      result.addTerm(current_term.getCoefficient(),current_term.getPower());
      current_term = current_term.next;
    }
    current_term = null;
    if (another == null) {
      result = this;
      return result;
    }
    if (another.getTerm() != null) {
      current_term = another.getTerm();
    }
    else {
      result = this;
      return result;
    }
    while (current_term != null) {
      result.addTerm(current_term.getCoefficient(),current_term.getPower());
      current_term = current_term.next;
    }
    return result;
  }

  /**
   * Multiply two polynomial and return a new polynomial of the result.
   *
   * @param another the other polynomial used to multiply current polynomial.
   * @return a brand new polynomial.
   */
  @Override
  public Polynomial multiply(Polynomial another) {
    if (this.getTerm() == null || another.getTerm() == null) {
      return new PolynomialImpl();
    }
    Polynomial result = new PolynomialImpl();
    Term first = this.getTerm();

    while (first != null) {
      Term second = another.getTerm();
      while (second != null) {
        int power_now = first.getPower() + second.getPower();
        int coefficient_now = first.getCoefficient() * second.getCoefficient();
        result.addTerm(coefficient_now, power_now);
        second = second.next;
      }
      first = first.next;
    }
    return result;
  }

  /**
   * Get the derivative polynomial of current polynomial, whose each term's coefficient
   * is original coefficient multiply the power, the power is the original power minus 1.
   *
   * @return the derivative polynomial.
   */
  @Override
  public Polynomial derivative() {
    Polynomial result = new PolynomialImpl();
    Term current_term = this.getTerm();
    while (current_term != null && current_term.getPower() != 0) {
      result.addTerm(current_term.getCoefficient() * current_term.getPower(),
              current_term.getPower() - 1);
      current_term = current_term.next;
    }
    return result;
  }

  /**
   * Get the string that represents the current polynomial.
   *
   * @return the related string of the polynomial.
   */
  public String toString() {
    if (this == null) {
      return "0";
    }
    if (this.getTerm() == null) {
      return "0";
    }
    Term current_term = this.term;
    String result = "";
    if (current_term.getCoefficient() != 1) {
      result += String.valueOf(current_term.getCoefficient());
    }
    if (current_term.getPower() != 0) {
      result += "x^";
      result += String.valueOf(current_term.getPower());
    }
    current_term = current_term.next;
    while (current_term != null) {
      if (current_term.getCoefficient() > 0) {
        result += "+";
      }
      if (current_term.getCoefficient() != 1) {
        result += String.valueOf(current_term.getCoefficient());
      }
      if (current_term.getPower() != 0) {
        result += "x";
        if (current_term.getPower() != 1) {
          result += "^";
          result += String.valueOf(current_term.getPower());
        }
      }
      current_term = current_term.next;
    }
    return result;
  }

  /**
   * Override the equals method.
   *
   * @param another another polynomial to be compared.
   * @return
   */
  @Override
  public boolean equals(Object another) {
    if (another == this) {
      return true;
    }
    if (!(another instanceof Polynomial)) {
      return false;
    }
    Polynomial another_poly = (PolynomialImpl) another;
    if (this.getTerm() == null && another_poly.getTerm() != null) {
      return false;
    }
    if (this.getTerm() != null && another_poly.getTerm() == null) {
      return false;
    }
    Term first = this.getTerm();
    Term second = another_poly.getTerm();
    while (first != null) {
      if (second == null) {
        return false;
      }
      int first_coefficient = first.getCoefficient();
      int first_power = first.getPower();
      int second_coefficient = second.getCoefficient();
      int second_power = second.getPower();
      if (first_coefficient != second_coefficient || first_power != second_power) {
        return false;
      }
      first = first.next;
      second = second.next;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 17;
    Term term = this.getTerm();

    result = 31 * result + term.getCoefficient();
    result = 31 * result + term.getPower();
    return result;
  }

}
