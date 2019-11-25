import org.junit.Test;

import intervals.IntervalTree;
import nodeUtils.Node;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class, test the Interval Tree class.
 */
public class testIntervalTree {

  IntervalTree tree;

  /**
   * Test whether an IntervalTree can be create by an empty String.
   */
  @Test
  public void testConstructorEmpty() {
    try {
      this.tree = new IntervalTree("");
    } catch (IllegalArgumentException e) {
      assertEquals("Postfix expression should not be empty.", e.getMessage());
    }
  }

  /**
   * Test whether the Interval's start can be bigger than its end.
   */
  @Test
  public void testConstructorInvalidInterval() {
    try {
      this.tree = new IntervalTree("2,1 3,4 U");
    } catch(IllegalArgumentException e) {
      assertEquals("Invalid interval", e.getMessage());
    }
  }

  /**
   * Test whether the Operator size can be bigger than 1.
   */
  @Test
  public void testConstructorIllegal1() {
    try {
      this.tree = new IntervalTree("1,2 3,5 UI");
    } catch (IllegalArgumentException e) {
      assertEquals("Un-supported operator in tree.", e.getMessage());
    }
  }

  /**
   * Test whether the unsupported operator can be used to create an IntervalTree.
   */
  @Test
  public void testConstructorIllegal2() {
    try {
      this.tree = new IntervalTree("1,2 3,5 A");
    } catch (IllegalArgumentException e) {
      assertEquals("Un-supported operator in tree", e.getMessage());
    }
  }

  /**
   * Test whether there can be more than 2 operands for 1 operator.
   */
  @Test
  public void testConstructorIllegal3() {
    try {
      this.tree = new IntervalTree("4,6 3,5,2 I");
    } catch (IllegalArgumentException e) {
      assertEquals("Input Interval operands are not in A,B form.",
              e.getMessage());
    }
  }

  /**
   * Test whether a interval's parameter can be double.
   */
  @Test
  public void testConstructorIllegal4() {
    try {
      this.tree = new IntervalTree("4,6 3,5.2 I");
    } catch (IllegalArgumentException e) {
      assertEquals("Input Interval operands is not Integer.",
              e.getMessage());
    }
  }

  /**
   * Test whether there can be operator that does not pair up.
   */
  @Test
  public void testConstructorIllegal5() {
    try {
      this.tree = new IntervalTree("4,6 3,5 I U");
    } catch (IllegalArgumentException e) {
      assertEquals("Input Interval operands can not pair up.",
              e.getMessage());
    }
  }

  /**
   * Test whether there can be less than 2 operands for 1 operator.
   */
  @Test
  public void testConstructorIllegal6() {
    try {
      this.tree = new IntervalTree("3,5 I");
    } catch (IllegalArgumentException e) {
      assertEquals("Input Interval operands can not pair up.",
              e.getMessage());
    }
  }

  /**
   * Test whether the tree can be constructed when the String does not end
   * in operators.
   */
  @Test
  public void testConstructorIllegal7() {
    try {
      this.tree = new IntervalTree("3,5 4,7 I 2,5");
    } catch (IllegalArgumentException e) {
      assertEquals("Un-supported operator in tree.",
              e.getMessage());
    }
  }

  /**
   * Test whether a interval's parameter can be String or Char.
   */
  @Test
  public void testConstructorIllegal8() {
    try {
      this.tree = new IntervalTree("4,6 3,5a I");
    } catch (IllegalArgumentException e) {
      assertEquals("Input Interval operands is not Integer.",
              e.getMessage());
    }
  }

  /**
   * Test whether the constructor can process input in order.
   */
  @Test
  public void testConstructorInOrder() {
    try {
      this.tree = new IntervalTree("4,6 3,5 I");
      Node root = this.tree.getRoot();
      Node left = root.getLeft();
      Node right = root.getRight();
      assertEquals("4,6", left.getValue().toString());
      assertEquals("3,5", right.getValue().toString());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Test whether the tree can still be created when there's leading spaces.
   */
  @Test
  public void testConstructorLeadingSpaces() {
    this.tree = new IntervalTree("  4,6        3,5 I");
    Node root = this.tree.getRoot();
    Node left = root.getLeft();
    Node right = root.getRight();
    assertEquals("4,6", left.getValue().toString());
    assertEquals("3,5", right.getValue().toString());
    assertEquals("4,5", this.tree.evaluate().toString());
  }

  /**
   * Test whether the tree can still be created when there's leading spaces.
   */
  @Test
  public void testConstructorTailingSpaces() {
    this.tree = new IntervalTree("  4,6    3,5   I    ");
    Node root = this.tree.getRoot();
    Node left = root.getLeft();
    Node right = root.getRight();
    assertEquals("4,6", left.getValue().toString());
    assertEquals("3,5", right.getValue().toString());
    assertEquals("4,5", this.tree.evaluate().toString());
  }

  /**
   * Test the Intersection function in an IntervalTree.
   */
  @Test
  public void testEvaluateIntersection() {
    try {
      this.tree = new IntervalTree("3,5 4,7 I");
      assertEquals("4,5", this.tree.evaluate().toString());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Test the union function in an IntervalTree.
   */
  @Test
  public void testEvaluateUnion() {
    try {
      this.tree = new IntervalTree("3,5 4,7 U");
      assertEquals("3,7", this.tree.evaluate().toString());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Test the intersection function performs correctly when there's
   * no intersection between two Intervals.
   */
  @Test
  public void testEvaluateNoneIntersection() {
    try {
      this.tree = new IntervalTree("3,5 6,7 I");
      assertEquals(Integer.MIN_VALUE + "," + Integer.MIN_VALUE,
              this.tree.evaluate().toString());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Test the Union and Intersection combination works correctly.
   */
  @Test
  public void testEvaluateUnionThenIntersectNone() {
    this.tree = new IntervalTree("10,12 3,5 6,7 U I");
    assertEquals(Integer.MIN_VALUE + "," + Integer.MIN_VALUE,
            this.tree.evaluate().toString());
  }

  /**
   * Test the Union and Intersection combination works correctly.
   */
  @Test
  public void testEvaluateIntersectThenUnion() {
    this.tree = new IntervalTree("10,12 3,4 6,7 I U");
    assertEquals(Integer.MIN_VALUE + ",12", this.tree.evaluate().toString());
  }

}
