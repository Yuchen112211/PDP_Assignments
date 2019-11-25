package model.datautils;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * The interface only contains one method which is read and parse data from
 * a String represents a file path.
 */
public interface DataProcess {

  /**
   * Public method getData should be called by the controller on given
   * file path.
   * This method would load the data from file and store them into the private
   * member of model. The client and controller should not see how this class
   * get the data.
   * @param filePath The file that contains the data given by the client.
   * @return The Data collections of the given file contains.
   */
  public ArrayList<Point2D.Double> loadData(String filePath);
}