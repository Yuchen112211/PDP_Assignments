import java.util.HashMap;

/**
 * The abstract class for different kinds of coupons. Provides several functions abstract
 * version so that every kind of coupon can have customized functions.
 */
abstract class Coupon implements CouponInterface {

  //Indicates the type of current Coupon, which should be AmounOff or PercentOff or BuyXGetYFree.
  protected String type = "";
  //Indicates the current item name.
  protected static String itemName = "";
  //Form the Coupon Description in later methods.
  protected String description = "";
  //The original price of the item.
  protected static double originalPrice = 0;
  //Whether the coupon is stackable, default is stackable.
  protected boolean stackable = true;

  /**
   * The constructor of the basic class Coupon. Provide three basic parameters to be later used.
   *
   * @param type the coupon type
   * @param itemName the item that this coupon appies to
   * @param originalPrice the original price of the item
   */
  protected Coupon(String type, String itemName, double originalPrice) {
    this.type = type;
    this.itemName = itemName;
    this.originalPrice = originalPrice;
  }

  /**
   * Get the basic information of the coupon, the information is contained in every kinds of coupon.
   *
   * @return description_tmp
   */
  protected String getDescriptionBasic() {
    String description_tmp = "";
    description_tmp += ("Item: " + this.itemName + "\n");
    description_tmp += ("Coupon Type: " + this.type + "\n");
    description_tmp += ("Coupon Stackable: " + this.stackable + "\n");
    description_tmp += ("Original Price: " + Double.toString(this.originalPrice) + "\n");
    return description_tmp;
  }

  /**
   * This method is used to compare two coupons.
   *
   * @param anotherCoupon the other coupon that used to be stacked
   * @return boolean type
   */
  public int compareTo(Coupon anotherCoupon) {
    if (this.type == anotherCoupon.type
            && this.itemName == anotherCoupon.itemName
            && this.originalPrice == anotherCoupon.originalPrice) {
      return 1;
    }
    else {
      return 0;
    }
  }

  /**
   * Make the coupon stackable.
   */
  public void enableStack() {
    this.stackable = true;
  }

  /**
   * Make the coupon not stackable.
   */
  public void disableStack() {
    this.stackable = false;
  }

  /**
   * When applying stack operation, determine whether this coupons can be stacked.
   *
   * @param coupon the coupon that to be determined.
   * @throws InvalidCouponOperationExceptions contains the information of the exception.
   */
  public void determineStackable(Coupon coupon) throws InvalidCouponOperationExceptions {
    if (!this.stackable) {
      throw new InvalidCouponOperationExceptions(this.type + " Coupon Stack Error: "
              + "This coupon is not stackable.");
    }
    else if (!coupon.stackable) {
      throw new InvalidCouponOperationExceptions(this.type + " Coupon Stack Error: "
              + "The other coupon is not stackable.");
    }
  }

  @Override
  public abstract HashMap<String, Double> getNecessaryElement();

  @Override
  public abstract String getCouponDescription();

  @Override
  public abstract HashMap<String, Double> getPriceAfterDiscount(int quantity);

  @Override
  public abstract Coupon stackedCoupon(Coupon anotherCoupon)
          throws InvalidCouponOperationExceptions;
}
