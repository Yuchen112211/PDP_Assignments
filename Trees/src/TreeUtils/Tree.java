package TreeUtils;

import java.util.HashMap;
import java.util.Stack;

import intervals.Interval;
import nodeUtils.Node;
import nodeUtils.OperandNode;
import nodeUtils.OperatorNode;
import nodeUtils.Operators;

public class Tree implements TreeInterface {

  Node root;

  @Override
  public String determineType(String postfix_raw) {
    String postfix = postfix_raw.trim();
    if (postfix.equals("")) {
      throw new IllegalArgumentException("Postfix expression should not be empty.");
    }
    HashMap<String,String> operators_map = new HashMap<>();
    operators_map.put("+", "Expression Tree");
    operators_map.put("-", "Expression Tree");
    operators_map.put("*", "Expression Tree");
    operators_map.put("/", "Expression Tree");
    operators_map.put("I", "Interval Tree");
    operators_map.put("U", "Interval Tree");
    String[] inputs = postfix.split(" ");
    String type_char = inputs[inputs.length-1].trim();
    if (type_char.length() != 1) {
      throw new IllegalArgumentException("Un-supported operator in tree.");
    }
    else if (operators_map.get(type_char) != null) {
      return operators_map.get(type_char);
    }
    else {
     throw new IllegalArgumentException("Un-supported operator in tree");
    }

  }

  private void intervalTreeValidation(String[] inputs) {
    Stack<Node> to_be_processed = new Stack<>();

    HashMap<String, Operators> stringToOperator = new HashMap<>();
    stringToOperator.put("U", Operators.UNION);
    stringToOperator.put("I", Operators.INTERSECTION);

    for (String input : inputs) {
      input = input.trim();
      //This is a operator.
      if (input.length() == 1) {
        //Find out if there's no two operands to pair up with the operator.
        if (to_be_processed.size() < 2) {
          throw new IllegalArgumentException("Input Interval operands can not pair up.");
        }
        //Get the operator.
        Operators operator = stringToOperator.get(input);
        //The operator should either be I or U, throw exceptions if is other character.
        if (operator == null) {
          throw new IllegalArgumentException("Un-supported operator: \"" + input + "\" in Interval Tree." );
        }

        //Get the last 2 operands that got pushed into the stack.
        Node<String> right = to_be_processed.pop();
        Node<String> left = to_be_processed.pop();
        //Create current node to be a operatorNode, with left and right node.
        Node<String> current_node = new OperatorNode<String>(operator, left, right);
        //Push the current node into the stack, in order to be computed and evaluated
        //in the future.
        to_be_processed.push(current_node);
      }
      else if (input.equals("")) {
        continue;
      }
      //If this is not an operator, and does not contain a comma, then it is invalid.
      else if (!input.contains(",")) {
        throw new IllegalArgumentException("Input Interval operands is not in A,B form.");
      } else {
        //The interval must be in A,B form and can only have two number in Integer form.
        String[] partitions = input.split(",");
        String left_str = partitions[0];
        String right_str = partitions[1];
        //If left number or right number is null, throw exceptions.
        if (left_str.equals("") || right_str.equals("")) {
          throw new IllegalArgumentException("Input Interval operands are not in A,B form.");
        }
        //If there are more than 2 numbers in the operand.
        else if (partitions.length != 2) {
          throw new IllegalArgumentException("Input Interval operands are not in A,B form.");
        }

        //Determine whether the numbers are Integers.
        try {
          int left = Integer.parseInt(left_str);
          int right = Integer.parseInt(right_str);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Input Interval operands is not Integer.");
        }
        //Push the node into the stack for further process.
        to_be_processed.push(new OperandNode<String>(input, null, null));
      }
    }
    to_be_processed.pop();
    if (!to_be_processed.isEmpty()) {
      throw new IllegalArgumentException("Postfix expression must end with operator.");
    }

  }

  private void expressionTreeValidation(String[] inputs) {
    Stack<Node> to_be_processed = new Stack<>();

    HashMap<String, Operators> stringToOperator = new HashMap<>();
    stringToOperator.put("+", Operators.ADD);
    stringToOperator.put("-", Operators.SUB);
    stringToOperator.put("*", Operators.MUL);

    String operator_str = "+-*/";

    for (String input : inputs) {
      input = input.trim();
      //This is a operator.
      if (input.length() == 1 && operator_str.contains(input)) {
        //Find out if there's no two operands to pair up with the operator.
        if (to_be_processed.size() < 2) {
          throw new IllegalArgumentException("Input Interval operands can not pair up.");
        }
        //Get the operator.
        Operators operator = stringToOperator.get(input);
        //The operator should either be I or U, throw exceptions if is other character.
        if (operator == null) {
          throw new IllegalArgumentException("Un-supported operator: \"" + input + "\" in Expression Tree." );
        }

        //Get the last 2 operands that got pushed into the stack.
        Node<String> right = to_be_processed.pop();
        Node<String> left = to_be_processed.pop();
        //Create current node to be a operatorNode, with left and right node.
        Node<String> current_node = new OperatorNode<String>(operator, left, right);
        //Push the current node into the stack, in order to be computed and evaluated
        //in the future.
        to_be_processed.push(current_node);
      }
      else if (input.equals("")) {
        continue;
      }
      else {
        try {
          double current_operand = Double.parseDouble(input);
        }
        catch (NumberFormatException e) {
          throw new IllegalArgumentException("The operands in Expression Tree are not in double type.");
        }
        to_be_processed.push(new OperandNode<String>(input, null, null));
      }
    }

    to_be_processed.pop();
    if (!to_be_processed.isEmpty()) {
      throw new IllegalArgumentException("Postfix expression must end with operator.");
    }

  }

  @Override
  public void validation(String postfix_raw, String tree_type) {
    String postfix = postfix_raw.trim();
    if (postfix.equals("")) {
      throw new IllegalArgumentException("Postfix expression should not be empty.");
    }

    String[] inputs = postfix.split(" ");

    if (tree_type.equals("Interval Tree")) {
      this.intervalTreeValidation(inputs);
    }
    else if (tree_type.equals("Expression Tree")) {
      this.expressionTreeValidation(inputs);
    }
  }

  @Override
  public Node formTree(String postfix, String tree_type) {
    HashMap<String, Operators> stringToOperator = new HashMap<>();
    stringToOperator.put("U", Operators.UNION);
    stringToOperator.put("I", Operators.INTERSECTION);
    stringToOperator.put("+", Operators.ADD);
    stringToOperator.put("-", Operators.SUB);
    stringToOperator.put("*", Operators.MUL);

    String[] inputs = postfix.trim().split(" ");
    Stack<Node > to_be_processed = new Stack<>();
    String operator_str = "+-*/UI";
    for (String input : inputs) {
      input = input.trim();
      //This is a operator.
      if (input.length() == 1 && operator_str.contains(input)) {
        Operators operator = stringToOperator.get(input);

        Node right = to_be_processed.pop();
        Node left = to_be_processed.pop();

        Node current_node = new OperatorNode (operator, left, right);

        to_be_processed.push(current_node);
      }
      else if (input.equals("")) {
        continue;
      }
      else {
        //The interval must be in A,B form and can only have two number in Integer form.
        if (tree_type == "Interval Tree") {
          String[] partitions = input.split(",");
          int left_index = Integer.parseInt(partitions[0]);
          int right_index = Integer.parseInt(partitions[1]);
          Interval current_operand = new Interval(left_index, right_index);
          to_be_processed.push(new OperandNode (current_operand, null, null));
        }
        else if (tree_type == "Expression Tree") {
          double current_operand = Double.parseDouble(input);
          to_be_processed.push(new OperandNode (current_operand, null, null));
        }
      }
    }
    root = to_be_processed.pop();
    return root;
  }

  public String texTree() {
    return root.textTree("");
  }
}
