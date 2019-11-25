package model.generator;

import java.io.IOException;

import model.drawer.ImagePlotter;

public class GenerateImgImpl implements GeneratedImg {

  @Override
  public void draw(int width, int height, String algorithm, String outputPath) throws IOException {
    ImagePlotter image = new ImagePlotter();
    image.setHeight(height);
    image.setWidth(width);
    image.setDimensions(0, width * 2, 0, height * 2);

    switch (algorithm) {
      case "Checker Board":
        CheckerBoard.addCheckerBoard(width, height, image);
        break;
      case "Rainbow Flag-horizontal":
        RainbowFlag.addRainbowFlagHorizontal(width, height, image);
        break;
      case "Rainbow Flag-Vertical":
        RainbowFlag.addRainbowFlagVertical(width, height, image);
      default:
        throw new IllegalArgumentException("The algorithm is not supported");

    }
    image.write(outputPath);
  }

  public static void main(String[] args) throws IOException, IllegalArgumentException {
    GenerateImgImpl x = new GenerateImgImpl();
    x.draw(10,10,"Checker Board","img.png");
  }
}
