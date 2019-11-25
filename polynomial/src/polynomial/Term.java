package polynomial;

/**
 * The Term class has three members.
 * coefficient: represents the coefficient of current term.
 * power: represents the power of current term.
 * next: This is the main idea of designing this class. The member is another Term instance,
 * the next term linked to this term. This can be regarded as a linked list.
 *
 */
public class Term implements TermInterface {
  private int coefficient;
  private int power;
  //The next term linked to this term.
  public Term next;

  /**
   * Constructor of Term.
   *
   * @param coefficient int, the coefficient of the term.
   * @param power int, the power of the term.
   * @throws IllegalArgumentException contains illegal information about the exception.
   */
  public Term(int coefficient, int power) throws IllegalArgumentException {
    if (power < 0) {
      throw new IllegalArgumentException("Power cannot be negative and must be Integer.");
    }
    this.coefficient = coefficient;
    this.power = power;
    this.next = null;
  }

  /**
   * Get the coefficient of current term.
   *
   * @return return the coefficient of current term.
   */
  @Override
  public int getCoefficient() {
    return this.coefficient;
  }

  /**
   * Get the power of current term.
   *
   * @return return the power of current term.
   */
  @Override
  public int getPower() {
    return this.power;
  }
}

