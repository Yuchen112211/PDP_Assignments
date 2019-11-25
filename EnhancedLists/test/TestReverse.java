import org.junit.Before;
import org.junit.Test;

import listadt.ListADTImpl;

import static junit.framework.TestCase.assertEquals;

/**
 * The test class for ListADT method reverse. Test all kinds of circumstances.
 */
public class TestReverse {

  ListADTImpl adt;

  @Before
  public void createADT() {
    adt = new ListADTImpl();
  }

  @Test
  public void testReverseEmpty() {
    assertEquals("()", adt.toString());
    adt.reverse();
    assertEquals("()", adt.toString());
  }

  @Test
  public void testReverseSingle() {
    adt.addBack(2);
    assertEquals("(2)", adt.toString());
    adt.reverse();
    assertEquals("(2)", adt.toString());
  }

  @Test
  public void testReverseInt() {
    adt.addBack(1);
    adt.addBack(2);
    adt.addBack(3);
    adt.addBack(4);
    adt.addBack(5);
    assertEquals("(1 2 3 4 5)", adt.toString());
    adt.reverse();
    assertEquals("(5 4 3 2 1)", adt.toString());
  }

  @Test
  public void testReverseString() {
    adt.addFront("abc");
    adt.addFront("def");
    adt.addFront("ghj");
    assertEquals("(ghj def abc)", adt.toString());
    adt.reverse();
    assertEquals("(abc def ghj)", adt.toString());
  }

  @Test
  public void testReverseDouble() {
    adt.addBack(3.2);
    adt.addBack(5.8);
    adt.addBack(1.9);
    adt.addBack(2.0);

    assertEquals("(3.2 5.8 1.9 2.0)", adt.toString());
    adt.reverse();
    assertEquals("(2.0 1.9 5.8 3.2)", adt.toString());
  }

  @Test
  public void testReverseTwoDifferentTypes() {
    adt.addBack(1);
    adt.addBack("123");

    assertEquals("(1 123)", adt.toString());
    adt.reverse();
    assertEquals("(123 1)", adt.toString());
  }

  @Test
  public void testReverseSeveralDifferent() {
    adt.addBack(2);
    adt.addBack(5.34);
    adt.addBack("abc");
    adt.addBack(-80);

    assertEquals("(2 5.34 abc -80)", adt.toString());
    adt.reverse();
    assertEquals("(-80 abc 5.34 2)", adt.toString());
  }

  @Test
  public void reverseSame() {
    adt.addBack(2);
    adt.addBack(2);
    adt.addBack(2);
    assertEquals("(2 2 2)", adt.toString());
    adt.reverse();
    assertEquals("(2 2 2)", adt.toString());
  }

}
