import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * The test of BuyXGetYFree coupon.
 */
public class TestCouponBuyXGetYFree {
  private CouponBuyXGetYFree current_coupon;

  /**
   * The function to be called before every test. Create a new object of Percent Off Coupon.
   */
  @Before
  public void create() {
    try {
      this.current_coupon = new CouponBuyXGetYFree("Napkin",20,3,2);
    }
    catch (InvalidCouponOperationExceptions e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * To test whether the coupon's parameter BuyX can be less than 0.
   */
  @Test
  public void testInitiationNegative() {
    try {
      this.current_coupon = new CouponBuyXGetYFree("Whatever",20,-10,20);
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX or GetYFree Cannot be less than 0",e.getMessage());
    }
  }

  /**
   * To test whether the coupon's parameter GetYFree can be less than 0.
   */
  @Test
  public void testInitiationNegative1() {
    try {
      this.current_coupon = new CouponBuyXGetYFree("Whatever",20,10,-20);
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX or GetYFree Cannot be less than 0",e.getMessage());
    }
  }

  /**
   * To test whether the coupon's BuyX can be 0.
   */
  @Test
  public void testInitiationEquals0() {
    try {
      this.current_coupon = new CouponBuyXGetYFree("Whatever",20,0,20);
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX can not be 0",e.getMessage());
    }
  }

  /**
   * To test whether the getNecessaryElement can return the correct element of the object.
   */
  @Test
  public void testGetNecessaryElement() {
    try {
      this.current_coupon = new CouponBuyXGetYFree("Whatever",20,10,5);
      assertEquals(10, this.current_coupon.getNecessaryElement().get("BuyX"),0.0000001);
      assertEquals(5, this.current_coupon.getNecessaryElement().get("GetYFree"),0.0000001);

    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX or GetYFree Cannot be less than 0",e.getMessage());
    }
  }

  /**
   * To test whether the getCouponDescription can return the correct description.
   */
  @Test
  public void testGetCouponDescription() {
    String current_description = this.current_coupon.getCouponDescription();
    String expected = "Item: Napkin\nCoupon Type: BuyXGetYFree\nCoupon Stackable: true\n"
            + "Original Price: 20.0\nBuyX: 3\nGetYFree: 2\n";
    assertEquals(expected,current_description);
  }

  /**
   * Test whether two different kinds of coupons can be stacked.
   */
  @Test
  public void testStackedCouponDifferentType() {
    try {
      CouponPercentOff another = new CouponPercentOff("Napkin",20,10);
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("BuyXGetYFree Coupon Stack Error: "
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
   * To test whether same type of coupons but with different items can be stacked.
   */
  @Test
  public void testStackedCouponDifferentItem() {
    try {
      CouponBuyXGetYFree another = new CouponBuyXGetYFree("Paper",20,10,5);
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("BuyXGetYFree Coupon Stack Error: "
                + "Two coupon are not the same type or "
                + "same item or don't have same price.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX or GetYFree Cannot be less than 0",e.getMessage());
    }
  }

  /**
   * To test whether same type and same item of coupons but
   * with different original price can be stacked.
   */
  @Test
  public void testStackedCouponDifferentOriginalPrice() {
    try {
      CouponBuyXGetYFree another = new CouponBuyXGetYFree("Paper",10,5,2);
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("BuyXGetYFree Coupon Stack Error: "
                + "Two coupon are not the same type or "
                + "same item or don't have same price.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX or GetYFree Cannot be less than 0",e.getMessage());
    }
  }

  /**
   * Test whether the coupons can be stacked when the other coupon is not stackable.
   */
  @Test
  public  void testStackedCouponOtherNotStackable() {
    try {
      CouponBuyXGetYFree another = new CouponBuyXGetYFree("Napkin",20,5,2);
      another.disableStack();
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("BuyXGetYFree Coupon Stack Error: "
                + "The other coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX or GetYFree Cannot be less than 0",e.getMessage());
    }
  }

  /**
   * Test whether the coupons can be stacked when the current coupon is not stackable.
   */
  @Test
  public void testStackedCouponThisNotStackable() {
    try {
      CouponBuyXGetYFree another = new CouponBuyXGetYFree("Napkin",20,5,2);
      this.current_coupon.disableStack();
      try {
        this.current_coupon.stackedCoupon(another);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("BuyXGetYFree Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX or GetYFree Cannot be less than 0",e.getMessage());
    }
  }

  /**
   * Test whether the stacked coupon has correct information after multiple stacks.
   */
  @Test
  public void testStackedMultipleStack() {
    try {
      CouponBuyXGetYFree another = new CouponBuyXGetYFree("Napkin",20,10,2);
      try {
        Coupon rst = this.current_coupon.stackedCoupon(another);
        for (int i = 0; i < 20; i ++ ) {
          try {
            rst = rst.stackedCoupon(another);
          }
          catch (InvalidCouponOperationExceptions e) {
            assertEquals("BuyXGetYFree Coupon Stack Error: "
                    + "This coupon is not stackable.", e.getMessage());
          }
        }
        assertEquals(2,rst.getNecessaryElement().get("GetYFree"),0.0000001);
        assertEquals(3,rst.getNecessaryElement().get("BuyX"),0.0000001);

      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("BuyXGetYFree Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX or GetYFree Cannot be less than 0",e.getMessage());
    }
  }

  /**
   * Test after a coupon is used, whether the discount price is correct.
   */
  @Test
  public void testCorrectTotalPrice() {
    HashMap<String, Double> total_price = this.current_coupon.getPriceAfterDiscount(100);
    assertEquals(1200,total_price.get("Discount Price for all"),0.0000001);

    total_price = this.current_coupon.getPriceAfterDiscount(12);
    assertEquals(160,total_price.get("Discount Price for all"),0.0000001);

    total_price = this.current_coupon.getPriceAfterDiscount(123);
    assertEquals(1500,total_price.get("Discount Price for all"),0.0000001);

    total_price = this.current_coupon.getPriceAfterDiscount(59);
    assertEquals(720,total_price.get("Discount Price for all"),0.0000001);
  }

  /**
   * Test whether the discount price is correct when BuyX equals the quantity, which should be the
   * original price for all items.
   */
  @Test
  public void testCorrectTotalQuantityEqualsBuyX() {
    try {
      CouponBuyXGetYFree another = new CouponBuyXGetYFree("Napkin", 20, 20, 20);
      HashMap<String, Double> total_price = another.getPriceAfterDiscount(20);
      assertEquals(400,total_price.get("Discount Price for all"),0.0000001);
      assertEquals(20,total_price.get("Original Price per unit"),0.0000001);
      assertEquals(20,total_price.get("Total quantity of item"),0.0000001);
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX or GetYFree Cannot be less than 0",e.getMessage());
    }
  }

  /**
   * Test whether the discount price is correct when quantity is bigger than BuyX but smaller than
   * BuyX plus GetYFree, which should be original price for all items.
   */
  @Test
  public void testCorrectTotalQuantityExceedsBuyX() {
    try {
      CouponBuyXGetYFree another = new CouponBuyXGetYFree("Napkin", 20, 20, 20);
      HashMap<String, Double> total_price = another.getPriceAfterDiscount(29);
      assertEquals(400,total_price.get("Discount Price for all"),0.0000001);
      assertEquals(20,total_price.get("Original Price per unit"),0.0000001);
      assertEquals(29,total_price.get("Total quantity of item"),0.0000001);
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX or GetYFree Cannot be less than 0",e.getMessage());
    }
  }

  /**
   * Test whether the information is correct when the other coupon has a better ratio.
   */
  @Test
  public void testStackedCouponBetterRatio() {
    try {
      CouponBuyXGetYFree another = new CouponBuyXGetYFree("Napkin",20,6,6);
      try {
        Coupon after_stacked = this.current_coupon.stackedCoupon(another);
        HashMap<String, Double> total_price = after_stacked.getPriceAfterDiscount(95);
        assertEquals(960,total_price.get("Discount Price for all"),0.0000001);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("BuyXGetYFree Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX or GetYFree Cannot be less than 0",e.getMessage());
    }
  }

  /**
   * Test whether the information is correct when the other coupon has worse ratio.
   */
  @Test
  public void testStackedCouponWorseRatio() {
    try {
      CouponBuyXGetYFree another = new CouponBuyXGetYFree("Napkin",20,4,2);
      try {
        Coupon after_stacked = this.current_coupon.stackedCoupon(another);
        HashMap<String, Double> total_price = after_stacked.getPriceAfterDiscount(95);
        assertEquals(1140,total_price.get("Discount Price for all"),0.0000001);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("BuyXGetYFree Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX or GetYFree Cannot be less than 0",e.getMessage());
    }
  }

  /**
   * Test whether the information is correct when the two stacked coupons have the same ratio.
   */
  @Test
  public void testStackedCouponSameRatio() {
    try {
      CouponBuyXGetYFree another = new CouponBuyXGetYFree("Napkin",20,6,4);
      try {
        Coupon after_stacked = this.current_coupon.stackedCoupon(another);
        HashMap<String, Double> total_price = after_stacked.getPriceAfterDiscount(95);
        assertEquals(1140,total_price.get("Discount Price for all"),0.0000001);
      }
      catch (InvalidCouponOperationExceptions e) {
        assertEquals("BuyXGetYFree Coupon Stack Error: "
                + "This coupon is not stackable.", e.getMessage());
      }
    }
    catch (InvalidCouponOperationExceptions e) {
      assertEquals("BuyXGetYFree Coupon Initiation Error: "
              + "The BuyX or GetYFree Cannot be less than 0",e.getMessage());
    }
  }

}
