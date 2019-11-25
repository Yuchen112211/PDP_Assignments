package view;


import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * The class implements all of common plotting methods. Any specific visualization class
 * should inherit this class and extend other methods from it.
 */
public class View implements ViewInterface {

  ImagePlotter plotter;

  protected View(int height, int width) {
    this.plotter = new ImagePlotter();
    this.plotter.setHeight(height);
    this.plotter.setWidth(width);
    this.plotter.setDimensions(-height, height, -width, width);
  }

  @Override
  public void scatterPlot(List<Point2D.Double> points, Color col) {
    for (Point2D.Double point: points) {
      this.plotter.addPoint((int) point.x,(int) point.y, col);
    }
  }

  @Override
  public void linePlot(Double a, Double b, Double c) {
    if (b == 0) {
      // Vertical line
      double x = c / a;

      this.plotter.addLine((int) x, (int) x, (int) x, (int) x);
    } else {
      double x1 = -200;
      double x2 = 200;
      double y1 = -(a / b) * x1 - c / b;
      double y2 = -(a / b) * x2 - c / b;

      this.plotter.addLine((int) x1, (int) y1, (int) x2, (int) y2);
    }
  }
}
