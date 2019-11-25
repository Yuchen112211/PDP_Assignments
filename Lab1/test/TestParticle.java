import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * The test class of the SimpleProjectile class.
 */
public class TestParticle {
  SimpleProjectile particle;

  @Test
  public void test1() {
    particle = new SimpleProjectile(1,2,3,4);
    assertEquals(particle.getState(0),"At time 0.00: position is (1.00,2.00)");
  }

  @Test
  public void test2() {
    particle = new SimpleProjectile(10,10,5,0);
    assertEquals(particle.getState(2), "At time 2.00: position is (10.00,10.00)");
  }


}
