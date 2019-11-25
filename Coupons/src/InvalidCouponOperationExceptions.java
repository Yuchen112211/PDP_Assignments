/**
 * This class represents a checked exception. If any kind of operation is
 * invalid, throw this exception with necessary information.
 */
public class InvalidCouponOperationExceptions extends Exception {

  public InvalidCouponOperationExceptions(String message) {
    super(message);
  }
}
