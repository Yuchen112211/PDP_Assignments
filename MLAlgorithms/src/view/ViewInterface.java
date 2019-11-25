package view;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * ViewInterface defines a set of common plots. They will be used by
 * other sub-classes of View.
 */
public interface ViewInterface {

  /**
   * Make a scatter plot.
   * @param points are a list of scatter points that will be included in the plot.
   * @param col represents the color of those points.
   */
  void scatterPlot(List<Point2D.Double> points, Color col);

  /**
   * Make a line plot. a*x + b*y + c = 0
   * @param a the coefficient of x-coordinate.
   * @param b the coefficient of y-coordinate.
   * @param c bias coefficient of the line.
   */
  void linePlot(Double a, Double b, Double c);
}
