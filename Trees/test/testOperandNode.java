import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import nodeUtils.OperandNode;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class, test the OperandNode class.
 */
public class testOperandNode {
  private OperandNode node;

  /**
   * Create a instance before every test.
   */
  @Before
  public void initiation() {
    this.node = new OperandNode("ME", null, null);
  }

  /**
   * Test whether the T is String can be correctly processed.
   */
  @Test
  public void testConstructorString() {
    assertEquals(this.node.getValue(),(String) "ME");
  }

  /**
   * Test whether the T is Integre can be correctly processed.
   */
  @Test
  public void testConstructorInt() {
    OperandNode tmp = new OperandNode(10,null,null);
    assertEquals(tmp.getValue(),(int) 10);
  }

  /**
   * Test whether the T is Double can be correctly processed.
   */
  @Test
  public void testConstructorDouble() {
    OperandNode tmp = new OperandNode(10.2,null,null);
    assertEquals(tmp.getValue(),(double) 10.2);
  }

  /**
   * Test whether the T is Float can be correctly processed.
   */
  @Test
  public void testConstructorFloat() {
    OperandNode tmp = new OperandNode((float)11.658, null, null);
    assertEquals((Float) tmp.getValue(), (float) 11.658, 0.00000000001);
  }

  /**
   * Test whether the T is null can be correctly processed.
   */
  @Test
  public void testConstructorNull() {
    OperandNode tmp = new OperandNode(null, null, null);
    assertEquals(tmp.getValue(), null);
  }

  /**
   * Test whether the complicated data structure can be used to create
   * an OperandNode.
   */
  @Test
  public void testConstructorComplicated() {
    Stack<String> tmp_stack = new Stack<>();
    tmp_stack.push("tmp");
    OperandNode tmp = new OperandNode(tmp_stack, null, null);
    assertEquals(tmp.getValue(), tmp_stack);
    assertEquals("tmp", tmp_stack.pop());
  }

  /**
   * Test whether the evaluate method runs correctly when T is String.
   */
  @Test
  public void testEvaluateString() {
    assertEquals(this.node.evaluate(),(String) "ME");
  }

  /**
   * Test whether the evaluate method runs correctly when T is Integer.
   */
  @Test
  public void testEvaluateInt() {
    OperandNode tmp = new OperandNode(10,null,null);
    assertEquals(tmp.evaluate(),(int) 10);
  }

  /**
   * Test whether the evaluate method runs correctly when T is Double.
   */
  @Test
  public void testEvaluateDouble() {
    OperandNode tmp = new OperandNode(10.2,null,null);
    assertEquals((double)tmp.evaluate(),(double) 10.2);
  }

  /**
   * Test whether the evaluate method runs correctly when T is float.
   */
  @Test
  public void testEvaluateFloat() {
    OperandNode tmp = new OperandNode((float)11.658, null, null);
    assertEquals((Float) tmp.evaluate(), (float) 11.658, 0.00000000001);
  }

  /**
   * Test whether the evaluate method runs correctly when T is null.
   */
  @Test
  public void testEvaluateNull() {
    OperandNode tmp = new OperandNode(null, null, null);
    assertEquals(tmp.evaluate(), null);
  }

  /**
   * Test the equals method with a simple case.
   */
  @Test
  public void testEquals0() {
    OperandNode tmp = new OperandNode("ME", null, null);
    assertEquals(this.node, tmp);
  }

  /**
   * Test the equals method when the value is in String type.
   */
  @Test
  public void testEqualsStringNode() {
    OperandNode tmp1 = new OperandNode("Test", null, null);
    OperandNode tmp2 = new OperandNode("Test", null, null);
    assertEquals(tmp1,tmp2);
  }

  /**
   * Test the equals method when the value is in Integer type.
   */
  @Test
  public void testEqualsIntNode() {
    OperandNode tmp1 = new OperandNode(12, null, null);
    OperandNode tmp2 = new OperandNode(12, null, null);
    assertEquals(tmp1,tmp2);
  }

  /**
   * Test the equals method when the node is in complicated forms.
   */
  @Test
  public void testEqualsMultipleNode() {
    OperandNode tmp1 = new OperandNode("Test1", null, null);
    OperandNode tmp2 = new OperandNode("Test1", null, null);
    assertEquals(tmp1, tmp2);
    OperandNode tmp3 = new OperandNode("Test2", tmp1, null);
    OperandNode tmp4 = new OperandNode("Test2", tmp2, null);
    assertEquals(tmp3, tmp4);
    OperandNode tmp5 = new OperandNode("Test3", tmp3, tmp1);
    OperandNode tmp6 = new OperandNode("Test3", tmp4, tmp2);
    assertEquals(tmp5, tmp6);;
  }

  /**
   * Test the equals method can be performed correctly on null.
   */
  @Test
  public void testGetLeftOrRightNull() {
    assertEquals(this.node.getLeft(),null);
    assertEquals(this.node.getRight(),null);
  }

  /**
   * Test the equals method can be performed correctly on left child.
   */
  @Test
  public void testGetLeft() {
    OperandNode tmp = new OperandNode(null, this.node, null);
    assertEquals(this.node,tmp.getLeft());
    assertEquals(null, tmp.getRight());
  }

  /**
   * Test the equals method can be performed correctly on right child.
   */
  @Test
  public void testGetRight() {
    OperandNode tmp = new OperandNode(null, null, this.node);
    assertEquals(this.node,tmp.getRight());
    assertEquals(null, tmp.getLeft());
  }

}
