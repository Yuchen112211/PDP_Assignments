package nodeUtils;

/**
 * The OperandNode class is a derivative class of Node, accept any kind
 * of value, use the generic form T to pass through paramters.
 * @param <T> the dynamic type.
 */
public class OperandNode<T> extends Node<T> {

  /**
   * Construct a OperandNode instance, performs nothing special. Represents the
   * simple node only contains a value.
   * @param value the value of current node.
   * @param left the left child, which should always be null.
   * @param right the right child, which should always be null.
   */
  public OperandNode(T value, Node left, Node right) {
    super(value ,left, right);
  }

  /**
   * The evaluate method return the current node of the operand node instance.
   * @return current value.
   */
  @Override
  public T evaluate(){
    return this.getValue();
  }

  @Override
  public String textTree(String current_prefix) {
    return "" + this.getValue();
  }
}
