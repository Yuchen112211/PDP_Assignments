import polynomial.Polynomial;
import polynomial.PolynomialImpl;
import polynomial.Term;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The test of class polynomial.
 */
public class TestPolynomial {

  private Polynomial poly;

  /**
   * Initiation of the polynomial.
   */
  @Before
  public void createPolynomial() {
    this.poly = new PolynomialImpl();
  }

  /**
   * Test if the PolynomialImpl constructor method which takes a string as parameter can
   * successfully create the object.
   */
  @Test
  public void testPolynomialConstructorString() {
    String input = "-2x^8 +3x^6 +9x^3 +100";
    Polynomial result = new PolynomialImpl(input);

    Term current_term = result.getTerm();

    assertEquals(-2, current_term.getCoefficient());
    assertEquals(8, current_term.getPower());
    current_term = current_term.next;

    assertEquals(3, current_term.getCoefficient());
    assertEquals(6, current_term.getPower());
    current_term = current_term.next;

    assertEquals(9, current_term.getCoefficient());
    assertEquals(3, current_term.getPower());
    current_term = current_term.next;

    assertEquals(100, current_term.getCoefficient());
    assertEquals(0, current_term.getPower());
    current_term = current_term.next;

    assertEquals(null, current_term);

  }

  /**
   * Test if the addTerm would throw exception when power is negative.
   */
  @Test
  public void testPolynomialAddTermNegative() {
    try {
      this.poly.addTerm(5,-2);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Power cannot be negative and must be Integer.", e.getMessage());
    }
  }

  /**
   * Test if the addTerm method works when called once.
   */
  @Test
  public void testPolynomialAddTermFirst() {
    try {
      this.poly.addTerm(5,3);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Power cannot be negative and must be Integer.", e.getMessage());
    }
    Term current_term = this.poly.getTerm();
    assertEquals(5, current_term.getCoefficient());
    assertEquals(3, current_term.getPower());
  }

  /**
   * Test if the addTerm method works when called several times.
   * All terms are different in power.
   */
  @Test
  public void testPolynomialAddTermSeveralDifferent() {
    try {
      this.poly.addTerm(2,3);
      this.poly.addTerm(4,2);
      this.poly.addTerm(5,1);
      Term current_term = this.poly.getTerm();

      assertEquals(current_term.getCoefficient(),2);
      assertEquals(current_term.getPower(),3);
      current_term = current_term.next;

      assertEquals(current_term.getCoefficient(),4);
      assertEquals(current_term.getPower(),2);
      current_term = current_term.next;

      assertEquals(current_term.getCoefficient(),5);
      assertEquals(current_term.getPower(),1);
      current_term = current_term.next;

      assertEquals(null, current_term);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Power cannot be negative and must be Integer.", e.getMessage());
    }
  }

  /**
   * Test if the getDegree method returns the right result.
   */
  @Test
  public void testPolynomialGetDegree0() {
    this.poly.addTerm(2,3);
    this.poly.addTerm(4,6);
    this.poly.addTerm(5,1);
    assertEquals(6, this.poly.getDegree());
  }

  /**
   * Test if the getDegree method returns the right result.
   */
  @Test
  public void testPolynomialGetDegree1() {
    this.poly.addTerm(2,3);
    this.poly.addTerm(4,6);
    this.poly.addTerm(5,1);
    this.poly.addTerm(4,68);
    this.poly.addTerm(4,6);
    this.poly.addTerm(4,6);
    this.poly.addTerm(4,6);
    assertEquals(68, this.poly.getDegree());
  }

  /**
   * Test if the addTerm can perform sorting function.
   */
  @Test
  public void testPolynomialAddTermSort() {
    try {
      this.poly.addTerm(5,1);

      this.poly.addTerm(2,4);

      this.poly.addTerm(4,2);

      this.poly.addTerm(6,3);

      Term current_term = this.poly.getTerm();
      assertEquals(current_term.getCoefficient(),2);
      assertEquals(current_term.getPower(),4);
      current_term = current_term.next;

      assertEquals(current_term.getCoefficient(),6);
      assertEquals(current_term.getPower(),3);
      current_term = current_term.next;

      assertEquals(current_term.getCoefficient(),4);
      assertEquals(current_term.getPower(),2);
      current_term = current_term.next;

      assertEquals(current_term.getCoefficient(),5);
      assertEquals(current_term.getPower(),1);
      current_term = current_term.next;

      assertEquals(null, current_term);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Power cannot be negative and must be Integer.", e.getMessage());
    }
  }

  /**
   * Test if the addTerm can perform on terms with same power.
   */
  @Test
  public void testPolynomialAddTermSamePower() {
    try {
      this.poly.addTerm(5,2);
      this.poly.addTerm(15,2);
      Term current_term = this.poly.getTerm();

      assertEquals(current_term.getCoefficient(),20);
      assertEquals(current_term.getPower(),2);

      current_term = current_term.next;
      assertEquals(null, current_term);

    }
    catch (IllegalArgumentException e) {
      assertEquals("Power cannot be negative and must be Integer.", e.getMessage());
    }
  }

  /**
   * Test if addTerm can perform on terms with same power when coefficient is 0 on the tail.
   */
  @Test
  public void testPolynomialAddTermSamePowerEnd0() {
    try {
      this.poly.addTerm(5,2);
      this.poly.addTerm(-5,3);

      this.poly.addTerm(-5,2);

      Term current_term = this.poly.getTerm();
      assertEquals(3, current_term.getPower());

      current_term = current_term.next;
      assertEquals(null, current_term);

    }
    catch (IllegalArgumentException e) {
      assertEquals("Power cannot be negative and must be Integer.", e.getMessage());
    }
  }

  /**
   * Test if addTerm can perform on terms with same power when coefficient is 0 on the middle.
   */
  @Test
  public void testPolynomialAddTermSamePowerMiddle0() {
    try {
      this.poly.addTerm(-5,1);
      this.poly.addTerm(5,2);
      this.poly.addTerm(-5,3);
      this.poly.addTerm(-5,2);
      Term current_term = this.poly.getTerm();
      assertEquals(3, current_term.getPower());

      current_term = current_term.next;
      assertEquals(1, current_term.getPower());

      current_term = current_term.next;
      assertEquals(null, current_term);

    }
    catch (IllegalArgumentException e) {
      assertEquals("Power cannot be negative and must be Integer.", e.getMessage());
    }
  }

  /**
   * Test if addTerm can perform on terms when making first term 0.
   */
  @Test
  public void testPolynomialSamePowerStart0() {
    try {
      this.poly.addTerm(5,2);
      this.poly.addTerm(5,1);
      this.poly.addTerm(-5,2);
      Term current_term = this.poly.getTerm();

      assertEquals(1, current_term.getPower());

    }
    catch (IllegalArgumentException e) {
      assertEquals("Power cannot be negative and must be Integer.", e.getMessage());
    }
  }

  /**
   * Test whether the add method returns the true polynomial.
   */
  @Test
  public void testPolynomialAdd() {
    this.poly.addTerm(5,2);
    this.poly.addTerm(5,3);

    Polynomial another = new PolynomialImpl();
    another.addTerm(3,3);
    another.addTerm(3,1);

    Polynomial result = this.poly.add(another);

    Term current_term = result.getTerm();

    assertEquals(8, current_term.getCoefficient());
    assertEquals(3, current_term.getPower());
    current_term = current_term.next;

    assertEquals(5, current_term.getCoefficient());
    assertEquals(2, current_term.getPower());
    current_term = current_term.next;

    assertEquals(3, current_term.getCoefficient());
    assertEquals(1, current_term.getPower());
    current_term = current_term.next;

    assertEquals(null, current_term);
  }

  /**
   * Test whether add method returns the true polynomial when some term get reduced to 0.
   */
  @Test
  public void testPolynomialAddReducedTo0() {
    this.poly.addTerm(5,2);
    this.poly.addTerm(5,3);

    Polynomial another = new PolynomialImpl();
    another.addTerm(-5,3);
    another.addTerm(3,1);

    Polynomial result = this.poly.add(another);

    Term current_term = result.getTerm();

    assertEquals(5, current_term.getCoefficient());
    assertEquals(2, current_term.getPower());
    current_term = current_term.next;

    assertEquals(3, current_term.getCoefficient());
    assertEquals(1, current_term.getPower());
    current_term = current_term.next;

    assertEquals(null, current_term);
  }

  /**
   * Test whether the getCoefficient method can return the right result.
   */
  @Test
  public void testPolynomialGetCoefficient() {
    this.poly.addTerm(152,6);
    this.poly.addTerm(412,2);
    this.poly.addTerm(63,3);
    assertEquals(this.poly.getCoefficient(6), 152);
    assertEquals(this.poly.getCoefficient(2), 412);
    assertEquals(this.poly.getCoefficient(3), 63);
  }

  /**
   * Test whether the evaluate method can return the right result.
   */
  @Test
  public void testPolynomialEvaluate() {
    this.poly.addTerm(5, 2);
    this.poly.addTerm(1, 3);
    this.poly.addTerm(7, 1);
    assertEquals(25.125, this.poly.evaluate(1.5), 0.001);
    this.poly.addTerm(2, 9);
    this.poly.addTerm(4, 3);

    assertEquals(1098, this.poly.evaluate(2), 0.001);
    assertEquals(39567, this.poly.evaluate(3), 0.001);
    assertEquals(524716, this.poly.evaluate(4), 0.001);
  }

  /**
   * Test whether the multiply method can return the right result.
   */
  public void testPolynomialMultiply() {
    this.poly.addTerm(5,2);
    this.poly.addTerm(5,3);

    Polynomial another = new PolynomialImpl();
    another.addTerm(-5,3);
    another.addTerm(3,1);

    Polynomial result = this.poly.multiply(another);
    Term current_term = result.getTerm();

    assertEquals(-25, current_term.getCoefficient());
    assertEquals(6, current_term.getPower());
    current_term = current_term.next;

    assertEquals(-25, current_term.getCoefficient());
    assertEquals(6, current_term.getPower());
    current_term = current_term.next;

    assertEquals(-25, current_term.getCoefficient());
    assertEquals(5, current_term.getPower());
    current_term = current_term.next;

    assertEquals(15, current_term.getCoefficient());
    assertEquals(4, current_term.getPower());
    current_term = current_term.next;

    assertEquals(15, current_term.getCoefficient());
    assertEquals(3, current_term.getPower());
    current_term = current_term.next;

    assertEquals(null, current_term);
  }

  /**
   * Test whether the derivative method can return the right result.
   */
  @Test
  public void testPolynomialDerivative() {
    this.poly.addTerm(4,2);
    this.poly.addTerm(1,3);
    this.poly.addTerm(5,6);
    this.poly.addTerm(8,5);
    this.poly.addTerm(2,4);
    this.poly.addTerm(3,0);

    Polynomial result = this.poly.derivative();

    Term current_term = result.getTerm();

    assertEquals(30, current_term.getCoefficient());
    assertEquals(5, current_term.getPower());
    current_term = current_term.next;

    assertEquals(40, current_term.getCoefficient());
    assertEquals(4, current_term.getPower());
    current_term = current_term.next;

    assertEquals(8, current_term.getCoefficient());
    assertEquals(3, current_term.getPower());
    current_term = current_term.next;

    assertEquals(3, current_term.getCoefficient());
    assertEquals(2, current_term.getPower());
    current_term = current_term.next;

    assertEquals(8, current_term.getCoefficient());
    assertEquals(1, current_term.getPower());
    current_term = current_term.next;

    assertEquals(null, current_term);
  }

  /**
   * Test whether the toString method returns the correct result when polynomial is empty.
   */
  @Test
  public void testPolynomialToStringEmpty() {
    Polynomial current = new PolynomialImpl();
    assertEquals("0",current.toString());
  }

  /**
   * Test whether the toString method returns the correct result when polynomial
   * is not empty.
   */
  @Test
  public void testPolynomialToString() {
    Polynomial current = new PolynomialImpl();
    current.addTerm(-3,3);
    current.addTerm(7,2);
    current.addTerm(2,1);
    current.addTerm(5,4);
    current.addTerm(3,0);
    String result = "5x^4-3x^3+7x^2+2x+3";
    assertEquals(result,current.toString());
  }
}
