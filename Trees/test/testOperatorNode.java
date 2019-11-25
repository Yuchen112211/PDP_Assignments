import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import intervals.Interval;
import nodeUtils.OperandNode;
import nodeUtils.OperatorNode;
import nodeUtils.Operators;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class, test whether the OperatorNode can perform as expected.
 */
public class testOperatorNode {
  Operators op;
  OperatorNode node;
  HashMap<String, Operators> stringToOperator = new HashMap<>();

  /**
   * Map the characters to operators store in Operators enum before each
   * testcase is created and tested.
   */
  @Before
  public void initiation() {
    this.stringToOperator.put("+", Operators.ADD);
    this.stringToOperator.put("-", Operators.SUB);
    this.stringToOperator.put("*", Operators.MUL);
    this.stringToOperator.put("U", Operators.UNION);
    this.stringToOperator.put("I", Operators.INTERSECTION);
  }

  /**
   * Test the equals method with simple testcase.
   */
  @Test
  public void testEquals() {
    this.node = new OperatorNode(stringToOperator.get("+"), null, null);
    OperatorNode tmp = new OperatorNode(stringToOperator.get("+"), null, null);
    assertEquals(tmp, this.node);
  }

  /**
   * Test the equals method with complicated testcase.
   */
  @Test
  public void testEqualsComplicated() {
    this.node = new OperatorNode(stringToOperator.get("+"),
            new OperandNode("4,10", null, null),
            new OperandNode("2,5", null, null));
    OperatorNode tmp = new OperatorNode(stringToOperator.get("+"),
            new OperandNode("4,10", null, null),
            new OperandNode("2,5", null, null));
    assertEquals(tmp, this.node);
  }

  /**
   * Test the add operation on OperatorNode.
   */
  @Test
  public void testAdd() {
    String current_operator = "+";
    this.op = stringToOperator.get(current_operator);
    OperandNode tmp1 = new OperandNode(3.0, null, null);
    OperandNode tmp2 = new OperandNode(5.0, null, null);
    this.node = new OperatorNode(this.op, tmp1, tmp2);
    assertEquals(8.0, this.node.evaluate());
  }

  /**
   * Test the subtract operation on OperatorNode.
   */
  @Test
  public void testSub() {
    String current_operator = "-";
    this.op = stringToOperator.get(current_operator);
    OperandNode tmp1 = new OperandNode(3.0, null, null);
    OperandNode tmp2 = new OperandNode(5.0, null, null);
    this.node = new OperatorNode(this.op, tmp1, tmp2);
    assertEquals(-2.0, this.node.evaluate());
  }

  /**
   * Test the multiply operation on OperatorNode.
   */
  @Test
  public void testMulPos() {
    String current_operator = "*";
    this.op = stringToOperator.get(current_operator);
    OperandNode tmp1 = new OperandNode(3.0, null, null);
    OperandNode tmp2 = new OperandNode(5.0, null, null);
    this.node = new OperatorNode(this.op, tmp1, tmp2);
    assertEquals(15.0, this.node.evaluate());
  }

  /**
   * Test the multiply operation can be processed correctly when negative number
   * is involved.
   */
  @Test
  public void testMulNeg() {
    String current_operator = "*";
    this.op = stringToOperator.get(current_operator);
    OperandNode tmp1 = new OperandNode(-3.0, null, null);
    OperandNode tmp2 = new OperandNode(5.0, null, null);
    this.node = new OperatorNode(this.op, tmp1, tmp2);
    assertEquals(-15.0, this.node.evaluate());
  }

  /**
   * Test the multiply operation can be processed correctly when two negative
   * numbers are involved.
   */
  @Test
  public void testMulDoubleNeg() {
    String current_operator = "*";
    this.op = stringToOperator.get(current_operator);
    OperandNode tmp1 = new OperandNode(-3.0, null, null);
    OperandNode tmp2 = new OperandNode(-5.0, null, null);
    this.node = new OperatorNode(this.op, tmp1, tmp2);
    assertEquals(15.0, this.node.evaluate());
  }

  /**
   * Test the evaluate method in complicated form.
   */
  @Test
  public void testComplicatedEvaluate1() {
    this.node = new OperatorNode(stringToOperator.get("*"),
            new OperandNode(-3.0, null, null),
            new OperandNode(-5.0, null, null));

    OperatorNode res=  new OperatorNode(stringToOperator.get("-"),
            new OperandNode(16.0, null, null),
            this.node);

    assertEquals(1.0,res.evaluate());
  }

  /**
   * Test the evaluate method in complicated form.
   */
  @Test
  public void testComplicatedEvaluate2() {
    this.node = new OperatorNode(stringToOperator.get("+"),
            new OperandNode(-4.3, null, null),
            new OperandNode(5.6, null, null));

    OperatorNode tmp = new OperatorNode(stringToOperator.get("-"),
            new OperandNode(11.2, null, null),
            this.node);

    OperatorNode res = new OperatorNode(stringToOperator.get("*"),
            tmp,
            new OperandNode(4.9, null, null));

    assertEquals(48.51000000,res.evaluate());
  }

  /**
   * Test the union operation on OperatorNode.
   */
  @Test
  public void testUnion() {
    Interval interval1 = new Interval(10,15);
    Interval interval2 = new Interval(5,8);
    this.node = new OperatorNode(stringToOperator.get("U"),
            new OperandNode(interval1, null, null),
            new OperandNode(interval2, null, null));
    assertEquals("5,15", this.node.evaluate().toString());
  }

  /**
   * Test the intersection operation on OperatorNode.
   */
  @Test
  public void testIntersection() {
    String tmp1_str = "10,15";
    String tmp2_str = "5,12";
    this.node = new OperatorNode(stringToOperator.get("I"),
            new OperandNode(new Interval(10, 15), null, null),
            new OperandNode(new Interval(5,12), null, null));
    assertEquals("10,12", this.node.evaluate().toString());
  }

  /**
   * Test the intersection operation on OperatorNode when there's no overlap
   * between two intervals.
   */
  @Test
  public void testNoIntersection() {
    this.node = new OperatorNode(stringToOperator.get("I"),
            new OperandNode(new Interval(10,15), null, null),
            new OperandNode(new Interval(5,8), null, null));
    assertEquals(Integer.MIN_VALUE + "," + Integer.MIN_VALUE, this.node.evaluate().toString());
  }

  /**
   * Test the evaluate method on OperatorNode under complicated form.
   */
  @Test
  public void testComplicatedEvaluate() {
    this.node = new OperatorNode(stringToOperator.get("U"),
            new OperatorNode(stringToOperator.get("I"),
                    new OperandNode(new Interval(3, 6), null, null),
                    new OperandNode(new Interval(1, 7), null, null)),
            new OperatorNode(stringToOperator.get("U"),
                    new OperandNode(new Interval(11, 23), null, null),
                    new OperandNode(new Interval(15,17), null, null)));
    assertEquals("3,23", this.node.evaluate().toString());
  }

}
