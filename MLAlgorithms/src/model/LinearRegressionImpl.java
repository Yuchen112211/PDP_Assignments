package model;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import model.datautils.DataProcess;
import model.datautils.DataProcessImpl;
import view.LRView;

/**
 * The implementation of LinearRegression interface.
 */
public class LinearRegressionImpl implements LinearRegression {

  /**
   * Helper method, compute the average Point2D.Double.
   * @param inputData The input Point2D.Double collection.
   * @return The average Point2D.Double.
   */
  private Point2D.Double computeMean(ArrayList<Point2D.Double> inputData) {
    double x_sum = 0;
    double y_sum = 0;
    for (int i = 0; i < inputData.size(); i ++ ) {
      x_sum += inputData.get(i).x;
      y_sum += inputData.get(i).y;
    }
    double x_mean = x_sum / inputData.size();
    double y_mean = y_sum / inputData.size();
    return new Point2D.Double(x_mean, y_mean);
  }

  /**
   * Helper method, compute the three needed numbers.
   * @param inputData The input Point2D.Double collection.
   * @param average The average Point2D.Double of all instances.
   * @return The three double numbers in array.
   */
  private double[] computeSums(ArrayList<Point2D.Double> inputData, Point2D.Double average) {
    double s_xy = 0;
    double s_xx = 0;
    double s_yy = 0;


    for (Point2D.Double tmp : inputData) {
      s_xx += (tmp.x - average.x) * (tmp.x - average.x);
      s_xy += (tmp.x - average.x) * (tmp.y - average.y);
      s_yy += (tmp.y - average.y) * (tmp.y - average.y);
    }

    double[] rst_d = {s_xx, s_yy, s_xy};

    return rst_d;
  }

  /**
   * Helper method, compute the middle Theta.
   * @param input The three numbers that computed before in computeSums method.
   * @return The Theta.
   */
  private double computeTheta(double[] input) {
    double d = 2 * input[2] / (input[0] - input[1]);
    return Math.atan(d);
  }

  /**
   * Helper method, compute the T that will be used in the algorithm.
   * @param input The three numbers that computed before in computeSums method.
   * @return The T.
   */
  private double computeT(double[] input) {
    double theta = this.computeTheta(input);
    double t1 = theta;
    double t2 = theta + Math.PI;
    double rst1 = (input[1] - input[0]) * Math.cos(t1) - 2 * input[2] * Math.sin(t1);
    double rst2 = (input[1] - input[0]) * Math.cos(t2) - 2 * input[2] * Math.sin(t2);
    if (rst1 >= 0) {
      return t1;
    }
    return t2;
  }

  /**
   * The only method that can be accessed publicly, return the a, b and c that form the function.
   * @param inputData
   * @return
   */
  @Override
  public double[] getLinearFunction(ArrayList<Point2D.Double> inputData) {
    Point2D.Double median = this.computeMean(inputData);
    double[] sums = this.computeSums(inputData, median);
    double T = this.computeT(sums);
    double a = Math.cos(T/2);
    double b = Math.sin(T/2);
    double c = (-1) * a * median.x + (-1) * b * median.y;
    return new double[] {a*10, b*10, c*10};
  }

  public static void main(String[] args) {

    String file = "/home/xieych/Downloads/NEU-Assignment6-data/linedata-2.txt";
    DataProcess process = new DataProcessImpl();
    ArrayList<Point2D.Double> tmp = process.loadData(file);
    ArrayList<Point2D.Double> tmp1 = process.loadData(file);

    LinearRegression rst = new LinearRegressionImpl();

    LRView views = new LRView(800,800);

    double[] data =  rst.getLinearFunction(tmp);

    System.out.println(data[0]);
    System.out.println(data[1]);
    System.out.println(data[2]);

    views.plot(tmp1,data);

  }
}
