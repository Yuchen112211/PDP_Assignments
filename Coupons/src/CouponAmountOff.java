import java.util.HashMap;

/**
 * This class is the amount-off coupon, which has a exclusive member, minusAmount
 * which represents the discount money that this coupon can offer per unit of item.
 */
public class CouponAmountOff extends Coupon {

  //Exclusively used by CouponAmountOff class.
  private double minusAmount = 0;

  /**
   * The constructor of CouponAmountOff.
   *
   * @param itemName the name of the item that the coupon can apply to.
   * @param originalPrice the original price of the item.
   * @param minusAmount the amount that the coupon can reduce.
   * @throws InvalidCouponOperationExceptions contains the information about exception.
   */
  public CouponAmountOff(String itemName, double originalPrice, double minusAmount)
          throws InvalidCouponOperationExceptions {
    super("AmountOff", itemName,originalPrice);
    // any parameters can not be less than 0
    if (minusAmount < 0 || originalPrice < 0) {
      throw new InvalidCouponOperationExceptions("AmountOff Coupon Initiation Error: "
              + "The off amount and original price cannot be less than 0");
    }
    // minusAmount can not be smaller than originalPrice
    else if (minusAmount > originalPrice) {
      throw new InvalidCouponOperationExceptions("AmountOff Coupon Initiation Error: "
              + "The off amount can not be bigger than original price");
    }
    else {
      this.minusAmount = minusAmount;
    }
  }

  /**
   * In this class, this method collects anything useful information about the Coupon.
   * This case, the method exclusively has Amount Off keyword.
   *
   * @return this.description
   */
  @Override
  public String getCouponDescription() {
    String description_tmp = this.getDescriptionBasic();

    description_tmp += ("Amount Off: " + Double.toString(this.minusAmount) + "\n");
    this.description = description_tmp;
    return this.description;
  }

  /**
   * The method used to compute the price after using the coupon. Return them in a HashMap.
   *
   * @param quantity which is the quantity that the customer will buy, in order to find out how
   *                 much money costs totally.
   * @return prices, a HashMap which contains keys and values.
   */
  @Override
  public HashMap<String, Double> getPriceAfterDiscount(int quantity) {
    HashMap<String, Double> prices = new HashMap<>();
    prices.put("Total quantity of item", (double) quantity);
    prices.put("Original Price per unit", this.originalPrice);
    double after_price = this.originalPrice - this.minusAmount;
    prices.put("Discount Price for all", after_price * quantity);
    return prices;
  }

  /**
   * Customized method for this kind of coupon. Only returns the minusAmount of this class.
   * In another way of speaking, this method is backup for coupon stack, while there is a
   * stack operation, use this method to get elements related with coupon stack.
   * 
   * @return
   */
  @Override
  public HashMap<String, Double> getNecessaryElement() {
    HashMap<String, Double> elements = new HashMap<>();
    // Only minusAmount is needed to be returned.
    elements.put("minusAmount", this.minusAmount);
    return elements;
  }

  /**
   * Method used to determine whether two coupons can be stacked, and to process the stack action,
   * then return a new coupon that has been stacked.
   * @param anotherCoupon the other coupons used to be stacked
   * @return stacked which is the stacked coupon.
   * @throws InvalidCouponOperationExceptions contains the information about the exception.
   */
  @Override
  public Coupon stackedCoupon(Coupon anotherCoupon) throws InvalidCouponOperationExceptions {
    if (this.compareTo(anotherCoupon) == 0) {
      throw new InvalidCouponOperationExceptions("AmountOff Coupon Stack Error: "
              + "Two coupon are not the same type or same item or don't have same price.");
    }
    else {
      this.determineStackable(anotherCoupon);
      CouponAmountOff stacked = null;
      // Since the minusAmount is private to CouponAmountOff,
      // we can only get our element through this operation.
      double total_minus = this.minusAmount
              + anotherCoupon.getNecessaryElement().get("minusAmount");
      double current_minusAmount = this.originalPrice;
      if (total_minus < this.originalPrice) {
        current_minusAmount = total_minus;
      }
      stacked = new CouponAmountOff(this.itemName,this.originalPrice,current_minusAmount);
      return stacked;
    }
  }

}
