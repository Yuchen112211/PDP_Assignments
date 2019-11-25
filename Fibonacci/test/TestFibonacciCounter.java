import org.junit.Before;
import org.junit.Test;

import fib.FibonacciCounterImpl;

import static junit.framework.TestCase.assertEquals;

/**
 * The test of the FibonacciCounterImpl class, test the class
 * methods completely.
 */
public class TestFibonacciCounter {

  FibonacciCounterImpl fibs;

  /**
   * The initiation of the instance.
   */
  @Before
  public void initiation() {
    fibs = new FibonacciCounterImpl();
  }

  /**
   * Test whether the decrease method can perform correctly when the current
   * count is 1.
   */
  @Test
  public void test1() {
    try {
      fibs.decrease();
    }
    catch (Exception e) {
      assertEquals("Current count is 1, can not decrease any more.",
              e.getMessage());
    }
  }

  /**
   * Test whether the increase method can perform correctly.
   */
  @Test
  public void test2() {
    assertEquals(1, fibs.getCount());
    assertEquals(0, fibs.getNumber());
    fibs.increase();
    assertEquals(2, fibs.getCount());
    assertEquals(1, fibs.getNumber());
  }

  /**
   * Test whether the increase method can perform correctly multiple times.
   */
  @Test
  public void test3() {
    for (int i = 0; i < 5; i ++ ) {
      fibs.increase();
    }
    assertEquals(6, fibs.getCount());
    assertEquals(5, fibs.getNumber());
  }

  /**
   * Test whether the increase and decrease methods can perform correctly
   * multiple times.
   */
  @Test
  public void test4() {
    for (int i = 0; i < 5; i ++ ) {
      fibs.increase();
    }
    try {
      for (int i = 0; i < 3; i ++ ) {
        fibs.decrease();
      }
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
    assertEquals(3, fibs.getCount());
    assertEquals(1, fibs.getNumber());
  }

}
