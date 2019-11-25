package polynomial;

/**
 * This is the interface of the term class.
 * TermInterface declares only two method, used to return the member of Term class.
 */
public interface TermInterface {

  /**
   * getCoefficient method returns the coefficient.
   *
   * @return integer, the coefficient of the current term.
   */
  public int getCoefficient();

  /**
   * getPower method returns the power.
   *
   * @return integer, the power of the current term.
   */
  public int getPower();

}
