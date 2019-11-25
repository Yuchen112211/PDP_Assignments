package model.generator;

import java.awt.*;

import model.drawer.ImagePlotter;

class RainbowFlag {
  static void addRainbowFlagVertical(int width, int height, ImagePlotter image) {
    int block_width = width * 2;
    int block_height = height * 2 / 7;

    Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.pink,
    Color.CYAN};
    for (int i = 0; i < 7; i ++ ) {
      image.addRectangle(0, block_height * i, block_width, block_height, colors[i]);
    }
  }

  static void addRainbowFlagHorizontal(int width, int height, ImagePlotter image) {
    int block_width = width * 2 / 7;
    int block_height = height * 2;

    Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.pink,
            Color.CYAN};
    for (int i = 0; i < 7; i ++ ) {
      image.addRectangle(block_width * i, 0, block_width, block_height, colors[i]);
    }
  }
}
