import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * This is the test of CouponPercentOff class.
 */
public class TestCouponPercentOff {

  private CouponPercentOff current_coupon;

  /**
   * This method is called before every test, make the current_coupon a new CouponPercentOff.
   */
  @Before
  public void create() {
    try {
      this.current_coupon = new CouponPercentOff("Napkin",20,10);
    }
    catch (InvalidCouponOperationExceptions e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Test whether minus_percent can be over 100 when initiation.
   */
  @Test
  public void testInitiationPercentOver100() {
    try {
      this.current_coupon = new CouponPercentOff("Whatever",20,110);
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("PercentOff Coupon Initiation Error: "
              + "The off percent can not be bigger than 100%",e.getMessage());
    }
  }

  /**
   * Test whether any parameter can be negative when initiation.
   */
  @Test
  public void testInitiationNegative() {
    try {
      this.current_coupon = new CouponPercentOff("Whatever",20,-10);
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("PercentOff Coupon Initiation Error: "
              + "The off percent and original price cannot be less than 0",e.getMessage());
    }
  }

  /**
   * Test whether getNecessaryElement method returns the correct information.
   */
  @Test
  public void testGetNecessaryElement() {
    try {
      this.current_coupon = new CouponPercentOff("Whatever",20,10);
      assertEquals(10, this.current_coupon.getNecessaryElement().get("minus_percent"),0.0000001);
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("PercentOff Coupon Initiation Error: "
              + "The off percent can not be bigger than 100%",e.getMessage());
    }
  }

  /**
   * Test whether the getCouponDescription can return the correct information.
   */
  @Test
  public void testGetCouponDescription() {
    String current_description = this.current_coupon.getCouponDescription();
    String expected = "Item: Napkin\nCoupon Type: PercentOff\nCoupon Stackable: true\n"
            + "Original Price: 20.0\nPercent Off: 10.0%\n";
    assertEquals(expected,current_description);
  }

  /**
   * Test whether the discount price is correct after using the coupon.
   */
  @Test
  public void testGetPriceAfterDiscount() {
    HashMap<String, Double> prices = this.current_coupon.getPriceAfterDiscount(10);
    double final_price = prices.get("Discount Price for all");
    assertEquals(final_price,180,0.0000001);
  }

  /**
   * Test whether different coupons can be stacked.
   */
  @Test
  public void testStackedCouponDifferentType() {
    try {
      CouponAmountOff another = new CouponAmountOff("Napkin",20,10);
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("PercentOff Coupon Stack Error: "
                + "Two coupon are not the same type or "
                + "same item or don't have same price.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("AmountOff Coupon Initiation Error: "
              + "The off percent can not be bigger than 100%",e.getMessage());
    }
  }

  /**
   * Test whether Coupons with same type but with different item can be stacked.
   */
  @Test
  public void testStackedCouponDifferentItem() {
    try {
      CouponPercentOff another = new CouponPercentOff("Paper",20,10);
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("PercentOff Coupon Stack Error: "
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
   * Test whether coupons with same type and same item but different original price can be stacked.
   */
  @Test
  public void testStackedCouponDifferentOriginalPrice() {
    try {
      CouponPercentOff another = new CouponPercentOff("Paper",10,5);
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("PercentOff Coupon Stack Error: "
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
   * Test whether coupons can be stacked when other coupon is not stackable.
   */
  @Test
  public  void testStackedCouponOtherNotStackable() {
    try {
      CouponPercentOff another = new CouponPercentOff("Napkin",20,5);
      another.disableStack();
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("PercentOff Coupon Stack Error: "
                + "The other coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("PercentOff Coupon Initiation Error: "
              + "The off percent can not be bigger than 100%",e.getMessage());
    }
  }

  /**
   * Test whether coupons can be stacked when current coupon is not stackable.
   */
  @Test
  public void testStackedCouponThisNotStackable() {
    try {
      CouponPercentOff another = new CouponPercentOff("Napkin",20,5);
      this.current_coupon.disableStack();
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("PercentOff Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("PercentOff Coupon Initiation Error: "
              + "The off percent can not be bigger than 100%",e.getMessage());
    }
  }

  /**
   * Test whether the information is correct after multiple stack operations.
   */
  @Test
  public void testStackedMultipleStack() {
    try {
      CouponPercentOff another = new CouponPercentOff("Napkin",20,1);
      try {
        Coupon rst = this.current_coupon.stackedCoupon(another);
        for (int i = 0; i < 20; i ++ ) {
          try {
            rst = rst.stackedCoupon(another);
          }
          catch (InvalidCouponOperationExceptions e) {
            assertEquals("PercentOff Coupon Stack Error: "
                    + "This coupon is not stackable.", e.getMessage());
          }
        }
        assertEquals(31,rst.getNecessaryElement().get("minus_percent"),0.0000001);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("PercentOff Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("PercentOff Coupon Initiation Error: "
              + "The off percent can not be bigger than 100%",e.getMessage());
    }
  }

  /**
   * Test whether the minus percent is correct after stack operation.
   */
  @Test
  public void testStackedCouponMinusPercent() {
    try {
      CouponPercentOff another = new CouponPercentOff("Napkin",20,5);
      try {
        Coupon after_stacked = this.current_coupon.stackedCoupon(another);
        double current_minus = after_stacked.getNecessaryElement().get("minus_percent");
        assertEquals(15,current_minus,0.0000001);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("PercentOff Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("PercentOff Coupon Initiation Error: "
              + "The off percent can not be bigger than 100%",e.getMessage());
    }
  }

  /**
   * Test whether the information and prices are correct after stack operation,
   * and the stacked coupon combined minus_percent is over 100.
   */
  @Test
  public void testStackedCouponMinusOver100() {
    try {
      CouponPercentOff another = new CouponPercentOff("Napkin",20,95);
      try {
        Coupon after_stacked = this.current_coupon.stackedCoupon(another);
        double current_minus = after_stacked.getNecessaryElement().get("minus_percent");
        assertEquals(100,current_minus,0.0000001);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("PercentOff Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("PercentOff Coupon Initiation Error: "
              + "The off percent can not be bigger than 100%",e.getMessage());
    }
  }

  /**
   * Test whether discount price is correct by providing the item's quantity.
   */
  @Test
  public void testCorrectTotalPrice() {
    HashMap<String, Double> total_price = this.current_coupon.getPriceAfterDiscount(100);
    assertEquals(1800,total_price.get("Discount Price for all"),0.0000001);
    assertEquals(20,total_price.get("Original Price per unit"),0.0000001);
    assertEquals(100,total_price.get("Total quantity of item"),0.0000001);
  }

  /**
   * Test whether the discount price is correct after using the coupon.
   */
  @Test
  public void testCorrectTotalPriceOverOriginal() {
    try {
      CouponPercentOff another = new CouponPercentOff("Napkin", 20, 100);
      HashMap<String, Double> total_price = another.getPriceAfterDiscount(100);
      assertEquals(0,total_price.get("Discount Price for all"),0.0000001);
      assertEquals(20,total_price.get("Original Price per unit"),0.0000001);
      assertEquals(100,total_price.get("Total quantity of item"),0.0000001);
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("PercentOff Coupon Initiation Error: "
              + "The off percent can not be bigger than 100%",e.getMessage());
    }

  }

  /**
   * Test whether the discount price is correct after two coupons are stacked.
   */
  @Test
  public void testStackedCouponCorrectTotalPrice() {
    try {
      CouponPercentOff another = new CouponPercentOff("Napkin",20,5);
      try {
        Coupon after_stacked = this.current_coupon.stackedCoupon(another);
        HashMap<String, Double> total_price = after_stacked.getPriceAfterDiscount(100);
        assertEquals(1700,total_price.get("Discount Price for all"),0.0000001);
        assertEquals(20,total_price.get("Original Price per unit"),0.0000001);
        assertEquals(100,total_price.get("Total quantity of item"),0.0000001);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("PercentOff Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("PercentOff Coupon Initiation Error: "
              + "The off percent can not be bigger than 100%",e.getMessage());
    }
  }

  /**
   * Test whether discount price is 0 when the minus_percent is over 100 after stack operations.
   */
  @Test
  public void testStackedCouponOverOriginalPriceCorrectTotalPrice() {
    try {
      CouponPercentOff another = new CouponPercentOff("Napkin",20,95);
      try {
        Coupon after_stacked = this.current_coupon.stackedCoupon(another);
        HashMap<String, Double> total_price = after_stacked.getPriceAfterDiscount(100);
        assertEquals(0,total_price.get("Discount Price for all"),0.0000001);
        assertEquals(20,total_price.get("Original Price per unit"),0.0000001);
        assertEquals(100,total_price.get("Total quantity of item"),0.0000001);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("PercentOff Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("PercentOff Coupon Initiation Error: "
              + "The off percent can not be bigger than 100%",e.getMessage());
    }
  }


}
