package model;


import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * The interface of LinearRegression operations in model package.
 */
public interface LinearRegression {

  /**
   * Get the a,b and c parameters to form the linear regression function.
   * @param inputData The input collection of points.
   * @return The a,b and c.
   */
  public double[] getLinearFunction(ArrayList<Point2D.Double> inputData);

}
