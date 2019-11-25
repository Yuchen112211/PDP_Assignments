package model.generator;


import java.awt.Color;

import model.drawer.ImagePlotter;

/**
 * Produce the checker board graph with given width and width.
 */
class CheckerBoard{

  static void addCheckerBoard(int width, int height, ImagePlotter image) {
    image.setHeight(height * 8);
    image.setWidth(width * 8);
    image.setDimensions(0, width * 8, 0, height * 8);

    Color color = Color.BLACK;
    for (int i = 0; i < 8; i ++ ) {
      for (int k = 0; k < 8; k ++ ) {
        image.addRectangle(width * i, width * k, width, height, color);
        if (color.equals(Color.BLACK)) {
          color = Color.WHITE;
        }
        else {
          color = Color.BLACK;
        }
      }
      if (color.equals(Color.BLACK)) {
        color = Color.WHITE;
      }
      else {
        color = Color.BLACK;
      }
    }
  }

}
