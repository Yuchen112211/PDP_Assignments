package intervals;

import java.util.Objects;

import TreeUtils.Tree;
import nodeUtils.Node;

/**
 * The class is an implementation of Intervals interface.
 * Represents the tree which contains OperatorNode and OperandNode, used to present
 * a interval tree in order to make compute more efficient.
 */
public class IntervalTree extends Tree implements Intervals {
  private Node<Interval> root;

  /**
   * Get the root of current tree. In this case usually a OperatorNode.
   * @return the root.
   */
  public Node getRoot() {
    return this.root;
  }

  /**
   * Construct an IntervalTree instance by given String.
   * The String should be in postfix, the method would determine whether the given
   * String is in postfix; Then the method would check whether the Interval operand
   * is in Integer type. If anything goes wrong, throw exceptions.
   * @param postfix postfix forms of interval tree.
   */
  public IntervalTree(String postfix) {
    String tree_type = "Interval Tree";
    if (this.determineType(postfix) != tree_type) {
      throw new IllegalArgumentException("Can not form an Interval tree with expressions.");
    }
    this.validation(postfix, tree_type);
    this.root = this.formTree(postfix, tree_type);
  }

  /**
   * The evaluate method will return a Interval instance formed by the tree.
   * @return the interval represented by the tree.
   */
  @Override
  public Interval evaluate() {
    return this.root.evaluate();
  }

  @Override
  public String textTree() {
    String rst = "";
    return rst;
  }

  /**
   * Override the equals method, used to determine the two IntervalTree be the same
   * or not.
   * @param other the other tree instance.
   * @return boolean type represents whether they equals or not.
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (! (other instanceof IntervalTree)) {
      return false;
    }
    return this.evaluate().equals(((IntervalTree) other).evaluate());
  }

  /**
   * Override the hashCode method, in this case will not be used.
   * @return the hashCode computed by this method.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.root.evaluate());
  }

  public String texTree() {
    return root.textTree("");
  }

  public static void main(String[] args) {
    String postfix = "1,2 3,4 4,6 U 3,5 I U";
    try {
      IntervalTree x = new IntervalTree(postfix);
      System.out.println(x.texTree());
    } catch (IllegalArgumentException E) {
      System.out.println(E.getMessage());
    }
  }
}
