package fib;

/**
 * The implementation of the FibonacciCounter interface.
 */
public class FibonacciCounterImpl implements FibonacciCounter {

  int count;
  int currentNumber;
  int nextNumber;

  /**
   * The only constructor of the FibonacciCounterImpl. Initiate the
   * count to be 1 and the number to 0.
   */
  public FibonacciCounterImpl() {
    this.count = 1;
    this.currentNumber = 0;
    this.nextNumber = 1;
  }

  /**
   * The increase method, increase the current count and move the number.
   */
  @Override
  public void increase() {
    this.count += 1;
    int tmp = this.nextNumber + this.currentNumber;
    this.currentNumber = this.nextNumber;
    this.nextNumber = tmp;
  }

  /**
   * The decrease method, decrease the current count and move the number.
   * @throws Exception If the count is 1 not, can not decrease.
   */
  @Override
  public void decrease() throws Exception {
    if (this.count == 1) {
      throw new Exception("Current count is 1, can not decrease any more.");
    }
    this.count -= 1;
    int tmp = this.nextNumber - this.currentNumber;
    this.nextNumber = this.currentNumber;
    this.currentNumber = tmp;
  }

  /**
   * Get the current count of the instance.
   * @return the current count.
   */
  @Override
  public int getCount() {
    return this.count;
  }

  /**
   * Get the current number of the instance.
   * @return the current number.
   */
  @Override
  public int getNumber() {
    return this.currentNumber;
  }
}
