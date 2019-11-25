package expression;

import TreeUtils.Tree;
import nodeUtils.Node;

public class ExpressionTree extends Tree implements Expression {
  private Node<Double> root;

  public ExpressionTree(String postfix) {
    String tree_type = "Expression Tree";
    if (this.determineType(postfix) != tree_type) {
      throw new IllegalArgumentException("Can not form an Expression tree with Intervals.");
    }
    this.validation(postfix, tree_type);
    this.root = this.formTree(postfix, tree_type);
  }


  @Override
  public double evaluate() {
    return this.root.evaluate();
  }

  @Override
  public String infix() {
    return null;
  }

  @Override
  public String schemeExpression() {
    return null;
  }


  public static void main(String[] args) {
    ExpressionTree x = new ExpressionTree("1 4 6 - 5 + *");
    System.out.println(x.texTree());
  }
}
