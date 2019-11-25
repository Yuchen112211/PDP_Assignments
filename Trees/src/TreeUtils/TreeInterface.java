package TreeUtils;

import nodeUtils.Node;

public interface TreeInterface {
  abstract void validation(String postfix, String tree_type);

  abstract Node formTree(String postfix, String tree_type);

  abstract String determineType(String postfix);
}
