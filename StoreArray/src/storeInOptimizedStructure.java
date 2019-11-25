import java.util.ArrayList;

/**
 * Use a String to store the frequency of the character appears and the character.
 * Each pair is in A,B forms.
 */
public class storeInOptimizedStructure {
  ArrayList<String> data;
  int m;
  int n;

  /**
   * Default Constructor.
   */
  public storeInOptimizedStructure() {
    data = new ArrayList<>();
  }

  /**
   * Put the 2-dimensional array into current data of the instance.
   * Use String to represents one element's frequency and the element.
   * For example, {3 3 3} would be converted to {"3,3"}, {13} would be converted to {"1,13"}.
   * @param inputs
   */
  public void put(int[][] inputs) {
    m = inputs.length;
    n = inputs[0].length;
    for (int i = 0; i < inputs.length; i ++ ) {
      String current_str = "";
      int count = 1;
      String current_char = Integer.toString(inputs[i][0]);
      for (int k = 1; k < inputs[i].length; k ++ ) {
        if (!Integer.toString(inputs[i][k]).equals(current_char)) {
          current_str = current_str + Integer.toString(count) +"," + current_char + " ";
          current_char = Integer.toString(inputs[i][k]);
          count = 1;
        }
        else {
          count += 1;
        }
      }
      current_str = current_str + Integer.toString(count) + "," + current_char + " ";
      data.add(current_str);
    }
  }

  /**
   * Retrieve the 2-dimensional array stored in the instance, return the formatted forms of data.
   * @return The original data of the 2-dimensional array.
   */
  public int[][] retrieve() {
    int[][] rst = new int[m][n];
    for (int i = 0 ; i < data.size(); i ++ ) {
      String current = data.get(i);
      int current_index = 0;
      String[] partitions = data.get(i).split(" ");
      for (int k = 0; k < partitions.length; k ++ ) {
        int count = Integer.parseInt(partitions[k].split(",")[0]);
        int to_add = Integer.parseInt(partitions[k].split(",")[1]);
        for (int j = 0; j < count; j ++ ) {
          rst[i][current_index] = to_add;
          current_index += 1;
        }
      }
    }
    return rst;
  }

  public static void main(String[] args) {
    storeInOptimizedStructure rst = new storeInOptimizedStructure();
    int[][] a = {{1,2},{3,4}};
    rst.put(a);
    for (int i = 0 ; i < rst.data.size() ; i ++ ) {
      System.out.println(rst.data.get(i));
    }
    int[][] b = rst.retrieve();
    for (int i = 0; i < b.length; i ++ ) {
      for (int k = 0; k < b[0].length; k ++ ) {
        System.out.print(b[i][k]);
        System.out.print(" ");
      }
      System.out.println("");
    }
  }
}
