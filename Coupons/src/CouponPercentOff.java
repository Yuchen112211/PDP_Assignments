import java.util.HashMap;

/**
 * This class is the percent-off coupon, which has a exclusive member, minusPercent
 * which represents the discount percent that this coupon can offer per unit of item.
 */
public class CouponPercentOff extends Coupon {
  //Exclusively used by CouponPercentOff class.
  private double minusPercent = 0;

  /**
   * The constructor of percent-off coupon.
   * @param itemName name of the item.
   * @param originalPrice original price of the item.
   * @param minusPercent the percent that the coupon can reduce.
   * @throws InvalidCouponOperationExceptions contains the information of the exception.
   */
  public CouponPercentOff(String itemName, double originalPrice,double minusPercent)
          throws InvalidCouponOperationExceptions {
    super("PercentOff", Coupon.itemName, Coupon.originalPrice);
    // The minusPercent and originalPrice cannot be less than 0.
    if (minusPercent < 0 || Coupon.originalPrice < 0) {
      throw new InvalidCouponOperationExceptions("PercentOff Coupon Initiation Error: "
              + "The off percent and original price cannot be less than 0");
    }
    // minusPercent cannot be bigger than 100%
    else if (minusPercent > 100) {
      throw new InvalidCouponOperationExceptions("PercentOff Coupon Initiation Error: "
              + "The off percent can not be bigger than 100%");
    }
    else {
      this.minusPercent = minusPercent;
    }
  }

  /**
   * Similar to the amount-off coupon, return the percent of the coupon can provide.
   * Also return the description.
   *
   * @return this.description
   */
  @Override
  public String getCouponDescription() {
    String description_tmp = this.getDescriptionBasic();

    description_tmp += ("Percent Off: " + Double.toString(this.minusPercent) + "%\n");
    this.description = description_tmp;
    return this.description;
  }

  /**
   * Compute the price after using the coupon, return the results in a HashMap.
   *
   * @param quantity the quantity of the item that the customer wants to get.
   * @return
   */
  @Override
  public HashMap<String, Double> getPriceAfterDiscount(int quantity) {
    HashMap<String, Double> prices = new HashMap<>();
    prices.put("Total quantity of item", (double) quantity);
    prices.put("Original Price per unit", this.originalPrice);
    // The rule of the percent off coupon is the original price multiply (1-minusPercent/100).
    double after_price = this.originalPrice * (1 - this.minusPercent / 100);
    prices.put("Discount Price for all", after_price * quantity);
    return prices;
  }

  /**
   * Again, return the percent that the coupon minus on original price.
   *
   * @return
   */
  @Override
  public HashMap<String, Double> getNecessaryElement() {
    HashMap<String, Double> rst = new HashMap<>();
    rst.put("minusPercent", this.minusPercent);
    return rst;
  }

  /**
   * Almost same method, except the stacked coupon has the combination of percent off of each
   * coupon.
   *
   * @param anotherCoupon the other coupon that is used to be stacked.
   * @return stacked
   * @throws InvalidCouponOperationExceptions contains the information of the exception.
   */
  @Override
  public Coupon stackedCoupon(Coupon anotherCoupon) throws InvalidCouponOperationExceptions {
    if (this.compareTo(anotherCoupon) == 0) {
      throw new InvalidCouponOperationExceptions("PercentOff Coupon Stack Error: "
              + "Two coupon are not the same type or same item or don't have same price.");
    }
    else {
      this.determineStackable(anotherCoupon);
      CouponPercentOff stacked = null;
      double total_minus = this.minusPercent
              + anotherCoupon.getNecessaryElement().get("minusPercent");
      double current_minusPercent = 100;
      if (total_minus < 100) {
        current_minusPercent = total_minus;
      }
      stacked = new CouponPercentOff(this.itemName,this.originalPrice,current_minusPercent);
      return stacked;
    }
  }

}


