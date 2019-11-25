package model.generator;

import java.io.IOException;

/**
 * The interface that provides the entrance to call all kinds of graph generating algorithm.
 */
public interface GeneratedImg {
  /**
   * The entrance of the generating graph function, should determine which graph to draw.
   * @param width The width of the graph.
   * @param height The height of the graph.
   * @param algorithm The algorithm that the method is calling.
   * @param outputPath Where the graph should be.
   */
  public void draw(int width, int height, String algorithm, String outputPath) throws IOException;
}
