import java.lang.reflect.Array;

public class storeInArray {

  int[][] data;

  public storeInArray() {
    this.data = null;
  }

  public void put(int[][] inputs) {
    this.data = inputs;
  }

  public int[][] retrieve() {
    return this.data;
  }

  public void print() {
    for (int i = 0; i < data.length; i ++ ){
      for (int k = 0; k < data[0].length; k ++ ) {
        System.out.print(data[i][k]);
        System.out.print(" ");
      }
      System.out.println("");
    }
  }

  public static void main(String[] args) {
    int[][] a = {{1,2},{3,4}};
    storeInArray x = new storeInArray();
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
