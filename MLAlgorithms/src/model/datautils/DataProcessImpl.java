package model.datautils;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * The implementation of the DataProcess interface, provide exactly one method
 * loadData which would open the file and parse the data into java.awt.geom.Point2D.java.awt.geom.Point2D.Double.
 */
public class DataProcessImpl implements DataProcess {

  /**
   * Empty constructor of DataProcessImpl.
   */
  public DataProcessImpl() {

  }

  /**
   * Helper method, parse the String in A B form into Point2D.
   * Parse the input string to java.awt.geom.Point2D.java.awt.geom.Point2D.Double instance.
   * @param input the input string.
   * @return the organized data in java.awt.geom.Point2D.java.awt.geom.Point2D.Double form.
   */
  private Point2D.Double parseData(String input) {
    double x = Double.parseDouble(input.split(" ")[0]);
    double y = Double.parseDouble(input.split(" ")[1]);

    return new Point2D.Double(x,y);
  }

  @Override
  public ArrayList<Point2D.Double> loadData(String filePath) {
    ArrayList<Point2D.Double> data = new ArrayList<>();

    BufferedReader reader = null;

    //Open the reader.
    try {
      reader = new BufferedReader(new FileReader(filePath));
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }

    //Read every line of the file.
    String current_line = null;
    while (true) {
      try {
        current_line = reader.readLine();
        if (current_line == null) {
          break;
        }

        Point2D.Double current_point = this.parseData(current_line);
        data.add(current_point);
      }
      catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    //Close up the opened reader.
    try {
      reader.close();
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return data;
  }
}
