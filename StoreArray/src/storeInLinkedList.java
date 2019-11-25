import java.util.ArrayList;

/**
 * Supported LinkedList class.
 */
class LinkedList {
  public int val;
  public LinkedList next;

  /**
   * Constructor of the class.
   * @param value the given integer to construct the linked list.
   */
  public LinkedList(int value) {
    val = value;
    next = null;
  }

  /**
   * Constructor of the class.
   * @param value the given integer to construct the linked list.
   * @param node the next node to be tagged at the tail.
   */
  public LinkedList(int value, LinkedList node) {
    val = value;
    next = node;
  }
}

/**
 * The class used to store the two-dimensional array.
 */
public class storeInLinkedList {
  ArrayList<LinkedList> data;
  int m;
  int n;

  /**
   * Constructor of this class.
   */
  public storeInLinkedList() {
    data = new ArrayList<>();
  }

  /**
   * Put the 2-dimensional array into this instance.
   * @param inputs the 2-dimensional array.
   */
  public void put(int[][] inputs) {
    m = inputs.length;
    n = inputs[0].length;
    for (int i = 0; i < inputs.length; i ++ ) {
      int[] tmp = inputs[i];
      LinkedList rst = null;
      for (int k = inputs[i].length - 1; k >= 0 ; k --) {
        rst = new LinkedList(inputs[i][k],rst);
      }
      data.add(rst);
    }
  }

  /**
   * The retrieve method to return the data that exists in the instance.
   * @return the related 2-dimensional array.
   */
  public int[][] retrieve() {
    int[][] rst = new int[m][n];
    for (int i = 0; i < m; i ++ ) {
      ArrayList<Integer> tmp = new ArrayList<>();
      LinkedList node = data.get(i);
      for (int k = 0; k < n; k ++ ) {
        rst[i][k] = (node.val);
        node = node.next;
      }
    }
    return rst;
  }

  public static void main(String[] args) {
    int[][] a = {{1,2},{3,4}};
    storeInLinkedList x = new storeInLinkedList();
    x.put(a);
    int[][] b = x.retrieve();
    for (int i = 0; i < b.length; i ++ ) {
      for (int k = 0; k < b[0].length; k ++ ) {
        System.out.print(b[i][k]);
        System.out.print(" ");
      }
      System.out.println("");
    }
  }
}
