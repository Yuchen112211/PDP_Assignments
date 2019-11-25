import java.util.HashMap;

/**
 * This class is the buyXgetYFree coupon, which is different than the former two kinds of
 * coupons, this can take various sets of X and Y, and then the class stores them together.
 *
 */
public class CouponBuyXGetYFree extends Coupon {

  //Exclusively used by CouponbuyXgetYFree class.
  private int buyX;
  private int getYFree;

  /**
   * Different from the former two classes, this method takes in 2 integers as parameters.
   * Though there's only one X and one Y when initialization, we still have to stores them into
   * ArrayList so that later when being stacked, all X and Y can be processed properly.
   *
   * @param itemName the name of the item
   * @param originalPrice the original price of the item
   * @param buyX buyX means the minimum amount to buy to get free items.
   * @param getYFree the free item that customer can get once buy buyX items.
   * @throws InvalidCouponOperationExceptions contains information about the exception.
   */
  public CouponBuyXGetYFree(String itemName, double originalPrice, int buyX, int getYFree)
          throws InvalidCouponOperationExceptions {
    super("buyXgetYFree", itemName, originalPrice);
    if (buyX < 0 || getYFree < 0) {
      throw new InvalidCouponOperationExceptions("buyXgetYFree Coupon Initiation Error: "
              + "The buyX or getYFree Cannot be less than 0");
    }
    else if (buyX == 0) {
      throw new InvalidCouponOperationExceptions("buyXgetYFree Coupon Initiation Error: "
              + "The buyX can not be 0");
    }
    this.buyX = buyX;
    this.getYFree = getYFree;
  }

  /**
   * Different from former classes, uses a for loop to push every X and Y into the description
   * and return it.
   *
   * @return
   */
  @Override
  public String getCouponDescription() {
    String description_tmp = this.getDescriptionBasic();

    description_tmp += ("buyX: " + Integer.toString(this.buyX) + "\n");
    description_tmp += ("getYFree: " + Integer.toString(this.getYFree) + "\n");

    this.description = description_tmp;
    return this.description;
  }

  /**
   * Return all X and Y of the object.
   *
   * @return
   */
  @Override
  public HashMap<String, Double> getNecessaryElement() {
    HashMap<String, Double> rst = new HashMap<>();
    rst.put("buyX", (double) this.buyX);
    rst.put("getYFree", (double) this.getYFree);
    return rst;
  }

  /**
   * In this method, go over every pair of X and Y, determine how many sets of each pair of X and Y
   * the current quantity can manage to include, then forms the final data into a HashMap.
   *
   * @param quantity the quantity of the itam that the customer want to get.
   * @return
   */
  @Override
  public HashMap<String, Double> getPriceAfterDiscount(int quantity) {
    HashMap<String, Double> prices = new HashMap<>();
    prices.put("Total quantity of item", (double) quantity);
    prices.put("Original Price per unit", this.originalPrice);
    double after_price = 0;
    double free_total = 0;
    int set_number = quantity / (this.buyX + this.getYFree);
    double free_money = set_number * this.getYFree * this.originalPrice;
    int left_to_decide = quantity - set_number * (this.buyX + this.getYFree);
    if (left_to_decide > this.buyX) {
      free_money += (left_to_decide - this.buyX) * this.originalPrice;
    }
    free_total += free_money;
    after_price = this.originalPrice * quantity - free_total;
    prices.put("Discount Price for all", after_price);
    return prices;
  }

  /**
   * Take another coupon as parameter, determine whether the coupon can be stacked, the add
   * the another pair of X and Y into the original pairs. Return the new object.
   *
   * @param anotherCoupon the other coupon used to be stacked.
   * @return either of the coupons.
   * @throws InvalidCouponOperationExceptions contains information about the exception.
   */
  @Override
  public Coupon stackedCoupon(Coupon anotherCoupon) throws InvalidCouponOperationExceptions {
    if (this.compareTo(anotherCoupon) == 0) {
      throw new InvalidCouponOperationExceptions("buyXgetYFree Coupon Stack Error: "
              + "Two coupon are not the same type or same item or don't have same price.");
    }
    else {
      this.determineStackable(anotherCoupon);
      HashMap<String, Double> anotherCoupon_info = anotherCoupon.getNecessaryElement();
      int add_buyX = (int) (double) anotherCoupon_info.get("buyX");
      int add_getYFree = (int) (double) anotherCoupon_info.get("getYFree");
      if ((double) add_getYFree / add_buyX < (double) this.getYFree / this.buyX) {
        return this;
      }
      else if ((double) add_getYFree / add_buyX > (double) this.getYFree / this.buyX) {
        return anotherCoupon;
      }
      else if (add_buyX < this.buyX) {
        return anotherCoupon;
      }
      else {
        return this;
      }
    }
  }

}
