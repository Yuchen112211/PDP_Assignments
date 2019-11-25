import java.util.HashMap;

/**
 * This interface represents a Coupon that provides
 * various functions.
 */
public interface CouponInterface extends Comparable<Coupon> {

  /**
   * Use plain and simple language to explain how this coupon works.
   *
   * @return the description of the coupon
   */
  String getCouponDescription();

  /**
   * By providing the quantity of the item, this method should return the price after
   * the discount, along with the original price in order to see how much the customer
   * can save.
   *
   * @param quantity represents how many pieces of item that will be discounted.
   * @return  a HashMap, which contains the discounted price and the original price.
   */
  HashMap<String, Double> getPriceAfterDiscount(int quantity);

  /**
   * Return the necessary element of a coupon.
   * Since there are three kinds of coupon, we cannot access elements in other class, so
   * we have to form the elements into a data structure and return it, then we can proceed
   * to handle these elements. This is the main idea of Union Type.
   *
   * @return
   */
  HashMap<String, Double> getNecessaryElement();

  /**
   * Perform the stack operation of two coupons.
   * Should determine whether the two coupons can be stacked together, if so, return
   * the new coupon that is formed by these two coupons; if not, throw exceptions.
   *
   * @param anotherCoupon the coupon that used to stack the current coupon.
   * @return  return the new Coupon
   */
  Coupon stackedCoupon(Coupon anotherCoupon) throws InvalidCouponOperationExceptions;

}
