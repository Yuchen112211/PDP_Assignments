package polynomial;

/**
 * The interface of the class PolynomialImpl.
 * Besides the methods required by the assignment, the interface has several new methods
 * so that the design of the code can be easier and more convenient.
 */
public interface Polynomial {

  /**
   * This method is used to add a term into current polynomial.
   * The method will determine whether the power has already been input to the polynomial, then
   * decide what action would be operated to the polynomial.
   * While adding the term, the method will determine where to insert the term according to
   * the power.
   *
   * @param coefficient the coefficient used to form polynomial.
   * @param power the power used to form polynomial.
   */
  public void addTerm(int coefficient, int power);

  /**
   * Get the max power of current polynomial.
   *
   * @return the degree of the polynomial.
   */
  public int getDegree();

  /**
   * Get the related coefficient of given power.
   *
   * @param power the power used to get coefficient.
   * @return the related coefficient.
   */
  public int getCoefficient(int power);

  /**
   * Compute method, use a value to compute the result of polynomial.
   *
   * @param number given number, in double type.
   * @return the result.
   */
  public double evaluate(double number);

  /**
   * Add two polynomials, return a new Polynomial object.
   * The method simply call addTerm method, the addTerm method will determine what actions
   * will be done to the terms with same power.
   *
   * @param another the other polynomial.
   * @return a brand new polynomial object.
   */
  public Polynomial add(Polynomial another);

  /**
   * Multiply two polynomials, return a new Polynomial object.
   * Same as add method, the multiply method calls the addTerm method.
   *
   * @param another the other polynomial used to multiply current polynomial.
   * @return a brand new polynomial object.
   */
  public Polynomial multiply(Polynomial another);

  /**
   * Get the derivative polynomial of current polynomial.
   *
   * @return a brand new polynomial object.
   */
  public Polynomial derivative();

  /**
   * Get the current term of the polynomial, which should be a linked list generated
   * by my own way.
   *
   * @return current term of the polynomial.
   */
  public Term getTerm();

}
