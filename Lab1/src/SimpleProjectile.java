/**
 * This class implements the particle interface and represents
 * a simple Newtonian particle.
 */
public class SimpleProjectile implements Particle {

  final float gravityAcc = -9.81f;
  private float initialX;
  private float initialY;

  private float velocityX;
  private float velocityY;

  /**
   * Construct a SimpleProjectile object that has the
   * provided initial x coordinate, initial y coordinate, horizontal
   * velocity and vertical velocity.
   *
   * @param initialX   the initial x coordinate
   * @param initialY   the initial y coordinate
   * @param velocityX the initial horizontal velocity
   * @param velocityY the initial vertical velocity
   */
  public SimpleProjectile(float initialX, float initialY, float velocityX, float velocityY) {
    this.initialX = initialX;
    this.initialY = initialY;
    this.velocityX = velocityX;
    this.velocityY = velocityY;
  }

  /**
   * Return the initial x coordinate.
   *
   * @return the initial x coordinate
   */
  public float getInitialX() {
    return this.initialX;
  }

  /**
   * Return the initial y coordinate.
   *
   * @return the initial y coordinate
   */
  public float getInitialY() {
    return this.initialY;
  }

  /**
   * Return the initial horizontal velocity.
   *
   * @return the initial horizontal velocity
   */
  public float getVelocity0X() {
    return this.velocityX;
  }

  /**
   * Return the initial vertical velocity.
   *
   * @return the initial vertical velocity
   */
  public float getVelocity0Y() {
    return this.velocityY;
  }

  /**
   * The getState method. Description in interface.
   * @param time the time at which the state must be obtained
   */
  @Override
  public String getState(float time) {
    float final_x;
    float final_y;

    String outputString;
    //calculate the time when the particle to fall to the ground
    float ground_time = 2 * Math.abs(velocityY / gravityAcc);

    if (velocityY < 0) {
      final_x = initialX + velocityX * time;
      final_y = initialY;
    } else {
      if (time <= 0) {
        final_x = initialX;
        final_y = initialY;
      } else if (time >= ground_time) {
        final_x = initialX + velocityX * ground_time;
        final_y = initialY;
      } else {
        final_x = initialX + velocityX * time;
        final_y = initialY + velocityY * time + gravityAcc * time * time / 2;
      }
    }

    outputString = String.format("At time %.2f: position is (%.2f,%.2f)", time, final_x, final_y);
    return outputString;
  }
}