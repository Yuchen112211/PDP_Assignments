package nodeUtils;

import intervals.Interval;

/**
 * The operator class is a derivative class of the node class.
 * This class provides the main compute functions. The value of the current node
 * is the operator that to be used later. Left and right node should be either
 * operator or operand, if its child is operator, call the evaluate method recursively;
 * If its child is operand, compute the operand based on the operator.
 * @param <T> the type
 */
public class OperatorNode<T> extends Node<T> {

  private Operators operator;

  public OperatorNode(Operators op, Node<T> left, Node<T> right) {
    super((T) null, left, right);
    this.operator = op;
  }

  @Override
  public T evaluate() {
    Double result_expression = null;
    T result = null;
    switch (this.operator) {
      case ADD:
        result_expression = (Double) this.getLeft().evaluate() + (Double) this.getRight().evaluate();
        result = (T) result_expression;
        break;
      case MUL:
        result_expression = (Double) this.getLeft().evaluate() * (Double) this.getRight().evaluate();
        result = (T) result_expression;
        break;
      case SUB:
        result_expression = (Double) this.getLeft().evaluate() - (Double) this.getRight().evaluate();
        result = (T) result_expression;
        break;
      case UNION:
        Interval left_interval_union = (Interval) this.getLeft().evaluate();
        Interval right_interval_union = (Interval) this.getRight().evaluate();
        Interval res_union = left_interval_union.union(right_interval_union);
        result = (T) res_union;
        break;
      case INTERSECTION:
        Interval left_interval_intersect = (Interval) this.getLeft().evaluate();
        Interval right_interval_intersect = (Interval) this.getRight().evaluate();
        Interval res_intersect = left_interval_intersect.intersect(right_interval_intersect);
        result = (T) res_intersect;
        break;
    }
    return result;
  }

  @Override
  public String textTree(String current_prefix) {
    String rst = "";
    if (this.operator.equals(Operators.ADD)) {
      rst += "+";
    }
    else if (this.operator.equals(Operators.SUB)) {
      rst += "-";
    }
    else if (this.operator.equals(Operators.MUL)) {
      rst += "*";
    }
    else if (this.operator.equals(Operators.UNION)) {
      rst += "U";
    }
    else if (this.operator.equals(Operators.INTERSECTION)) {
      rst += "I";
    }

    rst = rst + "\n" + current_prefix + "|" +
            "\n" + current_prefix + "|"  +
            "\n" + current_prefix +"|___" + this.getLeft().textTree(current_prefix+"|   ") +
            "\n" + current_prefix + "|"  +
            "\n" + current_prefix +"|___" + this.getRight().textTree(current_prefix+"    ");

    return rst;
  }

}
