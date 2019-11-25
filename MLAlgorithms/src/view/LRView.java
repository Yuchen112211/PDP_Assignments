package view;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * LRView is the specific view class for linear regression.
 * The intention is to plot the regression line and the scatter points
 * at the same time.
 */
public class LRView extends View {

  /**
   * The public constructor of the LRView takes the height and width as the input parameters.
   * @param height is the height of the plot.
   * @param width is the width of the plot.
   */
  public LRView(int height, int width) {
    super(height, width);
  }

  /**
   * Plot the result of linear regression.
   * @param points scatter points that are used to create the linear regression.
   * @param regression is a list representing the result of linear regression.
   */
  public void plot(List<Point2D.Double> points, double[] regression) {

    double a = regression[0];
    double b = regression[1];
    double c = regression[2];

    this.linePlot(a, b, c);
    this.scatterPlot(points, Color.BLACK);

    try {
      this.plotter.write("LinearRegression.png");
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
