package nodeUtils;

import java.util.Objects;

import intervals.Interval;

/**
 * This class represents the basic node class, which has only one abstract
 * method evaluate. This class provide several methods as basic functions.
 * Node has two children, left and right, indicates the next two operands to be
 * processed.
 * @param <T> Represents the dynamic type that passed to create node.
 */
public abstract class Node<T> {
  private T value;
  private Node<T> left;
  private Node<T> right;

  /**
   * Construct a node instance given the value, left tree and right tree.
   * @param val the value that this node would store.
   * @param left the left node.
   * @param right the right node.
   */
  protected Node(T val, Node left, Node right) {
    this.value = val;
    this.left = left;
    this.right = right;
  }

  /**
   * Get the current value of current node instance.
   * @return the value of this node.
   */
  public T getValue() {
    return value;
  }

  /**
   * Get the left child of current node instance.
   * @return the left node of this node.
   */
  public Node getLeft() {
    return this.left;
  }

  /**
   * Get the right child of current node instance.
   * @return the right node of this node.
   */
  public Node getRight() {
    return this.right;
  }

  /**
   * Parse the given string object to an Interval object.
   * @param str the interval to be parsed in str format.
   * @return the Interval instance
   */
  public Interval parseStringInterval(String str) {
    String[] indexes = str.split(",");
    int left_index = Integer.parseInt(indexes[0]);
    int right_index = Integer.parseInt(indexes[1]);
    return new Interval(left_index, right_index);
  }

  /**
   * Override the original equals method, in order to compare two node instances.
   * @param another another node given to be compared.
   * @return whether the two node is equal or not.
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) {
      return true;
    }
    if (!(another instanceof Node)) {
      return false;
    }
    else {
      Node<T> another_node = (Node<T>) another;
      if (this.getValue() != another_node.getValue()) {
        return false;
      }
      if (this.getLeft() == null) {
        if (another_node.getLeft() != null) {
          return false;
        }
        else if (this.getRight() == null) {
          return another_node.getRight() == null;
        }
        else {
          return this.getRight().equals(another_node.getRight());
        }
      }
      else if (this.getRight() == null) {
        if (another_node.getRight() == null) {
          return this.getLeft().equals(another_node.getLeft());
        }
        else {
          return false;
        }
      }
      return this.getLeft().equals(another_node.getLeft())
              && this.getRight().equals(another_node.getRight());
    }
  }

  /**
   * Override the hashCode method, which will not be used in this case.
   * @return
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.value);
  }

  /**
   * Returns a string that is the textual representation of the tree
   * structure which is a relatively complicated one.
   * @param current_prefix The prefix of the tree.
   * @return The text tree.
   */
  public abstract String textTree(String current_prefix);

  /**
   * The evaluate method should provide customized functions for different derivative
   * classes. If the derivative class is operand, simply return the value of the node;
   * If the derivative class is operator, compute on given operator char.
   * @return Same type of instance as T.
   */
  public abstract T evaluate();

}
