import polynomial.Term;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This is the test for Term class.
 */
public class TestTerms {
  /**
   * Test the initiation of the Term class.
   */
  @Test
  public void testTermInitiationNegative() {
    try {
      Term term = new Term(102, -1);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Power cannot be negative and must be Integer.", e.getMessage());
    }
  }

  /**
   * Test if the coefficient is passed correctly.
   */
  @Test
  public void testTermInitiationCoefficient() {
    try {
      Term term = new Term(50,22);
      assertEquals(term.getCoefficient(), 50);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Power cannot be negative and must be Integer.", e.getMessage());
    }
  }

  /**
   * Test if the power is passed correctly.
   */
  @Test
  public void testTermInitiationPower() {
    try {
      Term term = new Term(41,22);
      assertEquals(term.getPower(), 22);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Power cannot be negative and must be Integer.", e.getMessage());
    }
  }

  /**
   * Test if the next term is correctly initialized.
   */
  @Test
  public void testTermNext() {
    try {
      Term term = new Term(22,31);
      assertEquals(term.next, null);
      term.next = new Term(2,5);
      assertEquals(term.next.getPower(),5);
      assertEquals(term.next.getCoefficient(),2);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Power cannot be negative and must be Integer.", e.getMessage());
    }
  }

}
