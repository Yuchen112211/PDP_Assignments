import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * The test of the amount-off coupon.
 */
public class TestCouponAmountOff {

  private CouponAmountOff current_coupon;

  /**
   * The function to be called before every test. Create a new object of Amount Off Coupon.
   */
  @Before
  public void create() {
    try {
      this.current_coupon = new CouponAmountOff("Napkin",20,10);
    }
    catch (InvalidCouponOperationExceptions e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * To test if the minus_amount can be bigger than the original_price.
   */
  @Test
  public void testInitiationMinusBigger() {
    try {
      this.current_coupon = new CouponAmountOff("Whatever",20,30);
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off amount can not be bigger than original price",e.getMessage());
    }
  }

  /**
   * To test if the minus_amount of original_price can be less than 0.
   */
  @Test
  public void testInitiationNegative() {
    try {
      this.current_coupon = new CouponAmountOff("Whatever",20,-10);
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off amount and original price cannot be less than 0",e.getMessage());
    }
  }

  /**
   * To test if the minus amount is correctly recorded. This is a test on
   * getNecessaryElement method.
   */
  @Test
  public void testGetNecessaryElement() {
    try {
      this.current_coupon = new CouponAmountOff("Whatever",20,10);
      assertEquals(10, this.current_coupon.getNecessaryElement().get("minus_amount"),0.0000001);
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off amount can not be bigger than original price",e.getMessage());
    }
  }

  /**
   * To test if the description of the object is correct. This is a test on
   * getCouponDescription method.
   */
  @Test
  public void testGetCouponDescription() {
    String current_description = this.current_coupon.getCouponDescription();
    String expected = "Item: Napkin\nCoupon Type: AmountOff\nCoupon Stackable: true\n"
            + "Original Price: 20.0\nAmount Off: 10.0\n";
    assertEquals(expected,current_description);
  }

  /**
   * To test if the method can correctly return the discounted price.
   */
  @Test
  public void testGetPriceAfterDiscount() {
    HashMap<String, Double> prices = this.current_coupon.getPriceAfterDiscount(10);
    double final_price = prices.get("Discount Price for all");
    assertEquals(final_price,100,0.0000001);
  }

  /**
   * To test whether different types of coupons can stack together.
   */
  @Test
  public void testStackedCouponDifferentType() {
    try {
      CouponPercentOff another = new CouponPercentOff("Napkin",20,10);
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("AmountOff Coupon Stack Error: "
                + "Two coupon are not the same type or "
                + "same item or don't have same price.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("PercentOff Coupon Initiation Error: "
              + "The off percent can not be bigger than 100%",e.getMessage());
    }
  }

  /**
   * To test whether coupons with same type but different items can stack together.
   */
  @Test
  public void testStackedCouponDifferentItem() {
    try {
      CouponAmountOff another = new CouponAmountOff("Paper",20,10);
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("AmountOff Coupon Stack Error: "
                + "Two coupon are not the same type or "
                + "same item or don't have same price.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off amount can not be bigger than original price",e.getMessage());
    }
  }

  /**
   * To test whether two coupon applied to same item but with
   * different original_price can be stacked.
   */
  @Test
  public void testStackedCouponDifferentOriginalPrice() {
    try {
      CouponAmountOff another = new CouponAmountOff("Napkin",10,5);
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("AmountOff Coupon Stack Error: "
                + "Two coupon are not the same type or "
                + "same item or don't have same price.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off amount can not be bigger than original price",e.getMessage());
    }
  }


  /**
   * When the other coupon is not stackable, test if two coupons can be stacked.
   */
  @Test
  public  void testStackedCouponOtherNotStackable() {
    try {
      CouponAmountOff another = new CouponAmountOff("Napkin",20,5);
      another.disableStack();
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("AmountOff Coupon Stack Error: "
                + "The other coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off amount can not be bigger than original price",e.getMessage());
    }
  }

  /**
   * When the current coupon is not stackable, test if two coupons can be stacked.
   */
  @Test
  public void testStackedCouponThisNotStackable() {
    try {
      CouponAmountOff another = new CouponAmountOff("Napkin",20,5);
      this.current_coupon.disableStack();
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("AmountOff Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off amount can not be bigger than original price",e.getMessage());
    }
  }

  /**
   * Test if a coupon has been multiple stacked can get the right minus_amount.
   */
  @Test
  public void testStackedMultipleStack() {
    try {
      CouponAmountOff another = new CouponAmountOff("Napkin",20,0.1);
      try {
        Coupon rst = this.current_coupon.stackedCoupon(another);
        for (int i = 0; i < 20; i ++ ) {
          try {
            rst = rst.stackedCoupon(another);
          }
          catch (InvalidCouponOperationExceptions e) {
            assertEquals("AmountOff Coupon Stack Error: "
                    + "This coupon is not stackable.", e.getMessage());
          }
        }
        assertEquals(12.1,rst.getNecessaryElement().get("minus_amount"),0.0000001);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("AmountOff Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off amount can not be bigger than original price",e.getMessage());
    }
  }

  /**
   * Test whether the stacked coupon has correct minus_amount.
   */
  @Test
  public void testStackedCouponMinusAmount() {
    try {
      CouponAmountOff another = new CouponAmountOff("Napkin",20,5);
      try {
        Coupon after_stacked = this.current_coupon.stackedCoupon(another);
        double current_minus = after_stacked.getNecessaryElement().get("minus_amount");
        assertEquals(15,current_minus,0.0000001);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("AmountOff Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off amount can not be bigger than original price",e.getMessage());
    }
  }

  /**
   * Test the stacked coupon has the correct minus_amount when two original coupons have a total
   * minus_amount which is bigger than original price.
   */
  @Test
  public void testStackedCouponMinusBelow0() {
    try {
      CouponAmountOff another = new CouponAmountOff("Napkin",20,15);
      try {
        Coupon after_stacked = this.current_coupon.stackedCoupon(another);
        double current_minus = after_stacked.getNecessaryElement().get("minus_amount");
        assertEquals(20,current_minus,0.0000001);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("AmountOff Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off amount can not be bigger than original price",e.getMessage());
    }
  }

  /**
   * Test after using a coupon whether the discount price is correct.
   */
  @Test
  public void testCorrectTotalPrice() {
    HashMap<String, Double> total_price = this.current_coupon.getPriceAfterDiscount(100);
    assertEquals(1000,total_price.get("Discount Price for all"),0.0000001);
    assertEquals(20,total_price.get("Original Price per unit"),0.0000001);
    assertEquals(100,total_price.get("Total quantity of item"),0.0000001);
  }

  /**
   * Test after using a coupon which reduces the total price to 0 whether the discount
   * price is correct.
   */
  @Test
  public void testCorrectTotalPriceOverOriginal() {
    try {
      CouponAmountOff another = new CouponAmountOff("Napkin", 20, 20);
      HashMap<String, Double> total_price = another.getPriceAfterDiscount(100);
      assertEquals(0,total_price.get("Discount Price for all"),0.0000001);
      assertEquals(20,total_price.get("Original Price per unit"),0.0000001);
      assertEquals(100,total_price.get("Total quantity of item"),0.0000001);
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off amount can not be bigger than original price",e.getMessage());
    }
  }

  /**
   * After stacked another coupon, test whether the stacked coupon has the correct total distount
   * price.
   */
  @Test
  public void testStackedCouponCorrectTotalPrice() {
    try {
      CouponAmountOff another = new CouponAmountOff("Napkin",20,5);
      try {
        Coupon after_stacked = this.current_coupon.stackedCoupon(another);
        HashMap<String, Double> total_price = after_stacked.getPriceAfterDiscount(100);
        assertEquals(500,total_price.get("Discount Price for all"),0.0000001);
        assertEquals(20,total_price.get("Original Price per unit"),0.0000001);
        assertEquals(100,total_price.get("Total quantity of item"),0.0000001);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("AmountOff Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off amount can not be bigger than original price",e.getMessage());
    }
  }

  /**
   * After stacked a coupon which made the stacked coupon reduces the money to 0, whether the total
   * discount price is correct.
   */
  @Test
  public void testStackedCouponOverOriginalPriceCorrectTotalPrice() {
    try {
      CouponAmountOff another = new CouponAmountOff("Napkin",20,15);
      try {
        Coupon after_stacked = this.current_coupon.stackedCoupon(another);
        HashMap<String, Double> total_price = after_stacked.getPriceAfterDiscount(100);
        assertEquals(0,total_price.get("Discount Price for all"),0.0000001);
        assertEquals(20,total_price.get("Original Price per unit"),0.0000001);
        assertEquals(100,total_price.get("Total quantity of item"),0.0000001);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("AmountOff Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off amount can not be bigger than original price", e.getMessage());
    }
  }

}

