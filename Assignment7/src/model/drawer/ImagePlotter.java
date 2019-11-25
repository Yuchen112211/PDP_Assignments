package model.drawer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * This class is a plotter of data onto an image.
 * It provides operations to add points, lines and
 * circles to draw on the image.
 * It is also possible to set the size of the image to
 * be created, along with the range of the data that
 * is provided to it.
 */
public class ImagePlotter {
  private List<Integer> rectangles;
  private List<Color> rectangleColors;
  private int xmin;
  private int xmax;
  private int ymin;
  private int ymax;
  private final int pointSize;
  private int width;
  private int height;

  /**
   * The constructor of the ImagePlotter.
   */
  public ImagePlotter() {
    reset();
    pointSize = 3;
    width = height = 500;
  }

  public void addRectangle(int x, int y, int width, int height, Color color) {
    rectangles.add(x);
    rectangles.add(y);
    rectangles.add(width);
    rectangles.add(height);
    rectangleColors.add(color);

  }
  /**
   * Set the range in which all the added points, circles and lines lie. This
   * provides the range of the data as added to this plotter.
   * @param xmin The min value of X in order to set width.
   * @param xmax The max value of X in order to set width.
   * @param ymin The min value of Y in order to set height.
   * @param ymax The max value of Y in order to set height.
   */
  public void setDimensions(int xmin, int xmax, int ymin, int ymax) {
    this.xmin = xmin;
    this.xmax = xmax;
    this.ymin = ymin;
    this.ymax = ymax;

    //modify it to retain aspect ratio
    double aspectRatio = (double)width / height;
    double w = xmax - xmin;
    double h = ymax - ymin;
    if (h * aspectRatio < w) {
      h = w / aspectRatio;
    }
    else {
      w = h * aspectRatio;
    }
    this.xmin = (int)(0.5 * (xmin + xmax) - 0.5 * w);
    this.xmax = (int)(0.5 * (xmin + xmax) + 0.5 * w);
    this.ymin = (int)(0.5 * (ymin + ymax) - 0.5 * h);
    this.ymax = (int)(0.5 * (ymin + ymax) + 0.5 * h);

  }

  /**
   * Draw all the shapes added thus far to an image and save it to the
   * specific path.
   * @param path The output path.
   * @throws IOException The file IO exception.
   */
  public void write(String path) throws IOException {
    BufferedImage image = new BufferedImage(width,height,BufferedImage
            .TYPE_INT_ARGB);

    Graphics2D g2d = (Graphics2D) image.getGraphics();

    g2d.setColor(Color.WHITE);
    g2d.fillRect(0,0,width,height);
    AffineTransform mat = new AffineTransform();
    mat.concatenate(AffineTransform.getTranslateInstance(0, this.height));
    mat.concatenate(AffineTransform.getScaleInstance(1, -1));

    mat.concatenate(AffineTransform
            .getScaleInstance(
                    (double) this.width / (xmax - xmin),
                    (double) this.height / (ymax - ymin)));
    mat.concatenate(
            AffineTransform.getTranslateInstance(-xmin, -ymin));

    g2d.setTransform(mat);

    for (int i = 0; i < rectangles.size(); i += 4) {
      int x = rectangles.get(i);
      int y = rectangles.get(i + 1);
      int width = rectangles.get(i + 2);
      int height = rectangles.get(i + 3);
      g2d.setColor(rectangleColors.get(i / 4));
      g2d.fillRect(x, y, width, height);
    }

    String imageFormat = path.substring(path.indexOf(".") + 1);
    ImageIO.write(
            image,
            imageFormat,
            new FileOutputStream(path));
  }

  /**
   * Reset this plotter. All shapes are deleted as a result of resetting.
   */
  public void reset() {
    rectangles = new ArrayList<>();
    rectangleColors = new ArrayList<>();
  }

  /**
   * Set the width of the image that is created by this plotter.
   * @param w The width.
   */
  public void setWidth(int w) {
    width = w;
  }

  /**
   * Set the height of the image that is created by this plotter.
   * @param h The height.
   */

  public void setHeight(int h) {
    height = h;
  }
}
