import org.junit.Before;
import org.junit.Test;

import listadt.ListADTImpl;

import static junit.framework.TestCase.assertEquals;

/**
 * The test class for ListADT method interleave. Test every kinds of circumstances.
 */
public class TestInterleave {
  ListADTImpl adt;

  @Before
  public void createADT() {
    adt = new ListADTImpl();
  }

  @Test
  public void testInterleaveEmpty() {
    assertEquals("()", adt.toString());
    adt.interleave();
    assertEquals("()", adt.toString());
  }

  @Test
  public void testInterleaveOnly() {
    adt.addBack(1);
    assertEquals("(1)", adt.toString());
    adt.interleave();
    assertEquals("(1)", adt.toString());
  }

  @Test
  public void testInterleaveExactTwo() {
    adt.addBack(1);
    adt.addBack(2);
    assertEquals("(1 2)", adt.toString());
    adt.interleave();
    assertEquals("(1 2)", adt.toString());
  }

  @Test
  public void testInterleaveExactTree() {
    adt.addBack(1);
    adt.addBack(2);
    adt.addBack(3);
    assertEquals("(1 2 3)", adt.toString());
    adt.interleave();
    assertEquals("(1 3 2)", adt.toString());
  }

  @Test
  public void testInterleaveExactFour() {
    adt.addBack(1);
    adt.addBack(2);
    adt.addBack(3);
    adt.addBack(4);
    assertEquals("(1 2 3 4)", adt.toString());
    adt.interleave();
    assertEquals("(1 3 2 4)", adt.toString());
  }

  @Test
  public void testInterleaveString() {
    adt.addBack("abc");
    assertEquals("(abc)", adt.toString());
    adt.interleave();
    assertEquals("(abc)", adt.toString());
    adt.addBack("def");
    adt.addBack("ghi");
    adt.addBack("jkl");
    assertEquals("(abc def ghi jkl)", adt.toString());
    adt.interleave();
    assertEquals("(abc ghi def jkl)", adt.toString());
  }
}
