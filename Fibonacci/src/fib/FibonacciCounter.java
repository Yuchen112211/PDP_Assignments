package fib;

/**
 * The interface of the FibonacciCounter, provides several methods
 * that make the instance works like fibonacci.
 */
public interface FibonacciCounter {

  /**
   * Increase the current count of the instance, and move the fibonacci
   * number to the next one.
   */
  public void increase();

  /**
   * Decrease the current count of the instance, and move the fibonacci
   * number to the previous one.
   * @throws Exception If the count is 1 not, can not decrease.
   */
  public void decrease() throws Exception;

  /**
   * Get the current count of the fibonacci instance.
   * @return the current count.
   */
  public int getCount();

  /**
   * Get the current number of the fibonacci instance.
   * @return the current number.
   */
  public int getNumber();

}
