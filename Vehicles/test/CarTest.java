import org.junit.Before;
import org.junit.Test;

import vehicle.RegularManualTransmission;

import static org.junit.Assert.assertEquals;

/**
* This class is the test unit, include 22 test cases.
* Has two elements: this.car and expectOutput.
*/
public class CarTest {

  private RegularManualTransmission car;
  private String[] expectOutput = new String[]{"OK: everything is OK.",
    "OK: you may increase the gear.", "OK: you may decrease the gear.",
    "Cannot increase speed, increase gear first.",
    "Cannot decrease speed, decrease gear first.",
    "Cannot increase gear, increase speed first.",
    "Cannot decrease gear, decrease speed first.",
    "Cannot increase speed. Reached maximum speed.",
    "Cannot decrease speed. Reached minimum speed.",
    "Cannot increase gear. Reached maximum gear.",
    "Cannot decrease gear. Reached minimum gear."};

  /**
   * This function is the setup function before every test.
   * Except the tests of constructor, every other test will be based on the
   * RegularManualTransmission object that this function create.
   * left: an int array with the size of 5, indicates the lowest speed in very gear.
   * right: an int array with the size of 5, indicates the highest speed in very gear.
   * car: object that created to be tested.
   *
   */
  @Before
  public void setup() {
    int[] left = {0,5,15,25,35};
    int[] right = {5,20,30,40,50};
    try {
      this.car = new RegularManualTransmission(left[0],right[0],left[1],
              right[1],left[2],right[2],left[3],right[3],left[4],right[4]);
    }
    catch (IllegalArgumentException ex) {
      System.out.println(ex.getMessage());
    }
  }

  /**
  * This function test the constructor function.
  * The main purpose of this test is to see if the construction can be completed if
  * the lowest speed is not 0.
  *
  */
  @Test
  public void testInitialLowest0() {
    try {
      //The l1 is not 0
      RegularManualTransmission current_this = new RegularManualTransmission(
          1,1,1,1,1,1,1,1,1,1);
    }
    catch (IllegalArgumentException ex) {
      assertEquals("ERROR: Lowest speed should be 0",ex.getMessage());
    }
    System.out.println("Test InitialLowest0 has passed");
  }


  /**
   * This function test the constructor function.
   * The main purpose of this test is to see if the construction can be completed if
   * the lowest speed can be bigger than highest speed in the same gear.
   *
   */
  @Test
  public void testInitialLeftBiggerRight() {
    try {
      //l2 is bigger than r2.
      RegularManualTransmission current_car = new RegularManualTransmission(
          0,1,3,2,4,5,6,7,8,9);
    }
    catch (IllegalArgumentException ex) {
      assertEquals("ERROR: Lowest speed should be smaller than highest speed in one gear",
              ex.getMessage());
    }
    System.out.println("Test InitialLeftBiggerRight has passed");
  }

  /**
   * This function test the constructor function.
   * The main purpose of this test is to see if the construction can be completed if
   * the highest speed in higher gear is not strictly bigger than highest speed in smaller gear.
   *
   */
  @Test
  public void testInitialSmallerAdjacent() {
    try {
      //l3 is smaller than l2
      RegularManualTransmission current_car = new RegularManualTransmission(
          0,5,5,10,4,12,11,15,10,20);
    }
    catch (IllegalArgumentException ex) {
      assertEquals("ERROR: The lowest speed should be strictly smaller between adjacent gears",
              ex.getMessage());
    }
    System.out.println("Test InitialSmallerAdjacent has passed");
  }

  /**
   * This function test the constructor function.
   * The main purpose of this test is to see if the construction can be completed if
   * the ranges of the gears are non-overlapping.
   *
   */
  @Test
  public void testInitialNonOverlapping() {
    try {
      // Speed 11,16,21,22,23,24 is not contained.
      RegularManualTransmission current_this = new RegularManualTransmission(
          0,5,6,10,12,15,17,20,25,30);
    }
    catch (IllegalArgumentException ex) {
      assertEquals("ERROR: The ranges cannot be non-overlapping",ex.getMessage());
    }
    System.out.println("Test InitialNonOverlapping has passed");
  }

  /**
   * This function test the constructor function.
   * The main purpose of this test is to see if the construction can be completed if
   * the highest speed of the object is not in the last gear but in other gears.
   *
   */
  @Test
  public void testInitialHighestBeInLast() {
    try {
      //Highest speed is in gear 4.
      RegularManualTransmission current_this = new RegularManualTransmission(
          0,5,5,10,10,20,20,40,30,35);
    }
    catch (IllegalArgumentException ex) {
      assertEquals("ERROR: The highest speed should be in last range",ex.getMessage());
    }
    System.out.println("Test InitialHighestBeInLast has passed");
  }

  /**
  * This function test the getSpeed function.
  * See if the getSpeed's return and actual speed of the object are the same.
  *
  */
  @Test
  public void testCarGetSpeed() {
    //Actual speed should be 3, since increaseSpeed is called 3 times.
    for (int i = 0;i < 3;i++) {
      this.car = this.car.increaseSpeed();
    }
    assertEquals(3,this.car.getSpeed());
    System.out.println("Test CarGetSpeed has passed");
  }

  /**
   * This function test the getGear function.
   * See if the getGear's return and actual gear of the object are the same.
   *
   */
  @Test
  public void testCarGetGear() {
    //Actual gear should be 2, since speed is increased and increaseGear is called once.
    assertEquals(1,this.car.getGear());
    for (int i = 0;i < 5;i++) {
      this.car = this.car.increaseSpeed();

    }
    this.car = this.car.increaseGear();
    assertEquals(2,this.car.getGear());
    System.out.println("Test CarGetGear has passed");
  }

  /**
   * This function test the getStatus function.
   * See if the getStatus's return and actual status of the object are the same.
   *
   */
  @Test
  public void testGetStatus() {
    assertEquals(expectOutput[0],this.car.getStatus());
  }

  /**
  * This function test the increaseSpeed function.
  * See if the status of the object is correct when the speed is increased
  * without possibility to increase gear.
  *
  */
  @Test
  public void testCarIncreaseSpeedOkNoGear() {
    for (int i = 0;i < 4;i++) {
      this.car = this.car.increaseSpeed();
      assertEquals(expectOutput[0],this.car.getStatus());
    }
    System.out.println("Test CarIncreaseSpeedOkNoGear has passed");
  }

  /**
   * This function test the increaseSpeed function.
   * See if the status of the object is correct when the speed is increased
   * with possibility to increase gear.
   *
   */
  @Test
  public void testCarIncreaseSpeedOkIncreaseGear() {
    for (int i = 0;i < 4;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.increaseSpeed();
    assertEquals(expectOutput[1],this.car.getStatus());
    System.out.println("Test CarIncreaseSpeedOkIncreaseGear has passed");
  }

  /**
   * This function test the increaseSpeed function.
   * See if the increase action can be completed when reaches the highest speed of current gear.
   * Check whether the status is correct.
   *
   */
  @Test
  public void testCarIncreaseSpeedFailIncreaseGear() {

    for (int i = 0;i < 10;i++) {
      this.car = this.car.increaseSpeed();
    }
    assertEquals(expectOutput[3],this.car.getStatus());
    assertEquals(5,this.car.getSpeed());
    System.out.println("Test CarIncreaseSpeedFailIncreaseGear");
  }

  /**
   * This function test the increaseSpeed function.
   * See if the increase action can be completed when reaches the highest speed of the object.
   * Check whether the status is correct.
   *
   */
  @Test
  public void testCarIncreaseSpeedFailReachedMaximum() {
    for (int j = 0;j < 4;j++) {
      for (int i = 0;i < 10;i++) {
        this.car = this.car.increaseSpeed();
      }
      this.car = this.car.increaseGear();
    }
    for (int i = 0;i < 20;i++) {
      this.car = this.car.increaseSpeed();
    }
    assertEquals(50,this.car.getSpeed());
    assertEquals(expectOutput[7],this.car.getStatus());
    System.out.println("Test CarIncreaseSpeedFailedReachedMaximum has passed");
  }

  /**
   * This function test the decreaseSpeed function.
   * See if the status of the object is correct when the speed is increased
   * without possibility to decrease gear.
   *
   */
  @Test
  public void testCarDecreaseSpeedOkNoGear() {
    for (int i = 0;i < 4;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.decreaseSpeed();
    assertEquals(3,this.car.getSpeed());
    assertEquals(expectOutput[0],this.car.getStatus());
    System.out.println("Test CarDecreaseSpeedOkNoGear has passed");
  }

  /**
   * This function test the decreaseSpeed function.
   * See if the status of the object is correct when the speed is increased
   * with possibility to decrease gear.
   *
   */
  @Test
  public void testCarDecreaseSpeedOkDecreaseGear() {
    for (int i = 0;i < 5;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car.increaseGear();
    for (int i = 0;i < 12;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.increaseGear();
    this.car = this.car.decreaseSpeed();
    assertEquals(16,this.car.getSpeed());
    assertEquals(expectOutput[2],this.car.getStatus());
    System.out.println("Test CarDecreaseSpeedOkDecreaseGear has passed");
  }

  /**
   * This function test the decreaseSpeed function.
   * See if the decrease action can be completed when reaches the lowest speed of current gear.
   * Check whether the status is correct.
   *
   */
  @Test
  public void testCarDecreaseSpeedFailDecreaseGear() {
    for (int i = 0;i < 5;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.increaseGear();
    for (int i = 0;i < 12;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.increaseGear();
    for (int i = 0;i < 10;i++) {
      this.car = this.car.decreaseSpeed();
    }
    assertEquals(15,this.car.getSpeed());
    assertEquals(expectOutput[4],this.car.getStatus());
    System.out.println("Test CarDecreaseSpeedFailDecreaseGear has passed");
  }

  /**
   * This function test the decreaseSpeed function.
   * See if the decrease action can be completed when reaches the lowest speed of the object.
   * Check whether the status is correct.
   *
   */
  @Test
  public void testCarDecreaseSpeedFailReachedMinimum() {
    this.car = this.car.decreaseSpeed();
    assertEquals(0,this.car.getSpeed());
    assertEquals(expectOutput[8],this.car.getStatus());
    System.out.println("Test CarDecreaseSpeedFailReachedMinimum has passed");

  }

  /**
   * This function test the increaseGear function.
   * See if the status of the object is correct when the gear is increased
   * with the condition of speed.
   *
   */
  @Test
  public void testCarIncreaseGearOk() {
    for (int i = 0;i < 10;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.increaseGear();
    assertEquals(expectOutput[0],this.car.getStatus());
    System.out.println("Test CarIncreaseGearOk has passed");
  }

  /**
  * This function test the increaseGear function.
  * See if the increase action can be completed when the speed
  * has not reached the lowest speed of next gear.
  * Check whether the status is correct.
  *
  */
  @Test
  public void testCarIncreaseGearFailIncreaseSpeed() {
    for (int i = 0;i < 3;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.increaseGear();
    assertEquals(expectOutput[5],this.car.getStatus());
    for (int i = 0;i < 3;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.increaseGear();
    for (int i = 0;i < 5;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.increaseGear();
    assertEquals(2,this.car.getGear());
    assertEquals(expectOutput[5],this.car.getStatus());
    System.out.println("Test CarIncreaseGearFailIncreaseSpeed has passed");
  }

  /**
   * This function test the increaseGear function.
   * See if the increase action can be completed when the gear has reached 5.
   * Check whether the status is correct.
   *
   */
  @Test
  public void testCarIncreaseGearFailReachedMaximum() {
    for (int i = 0;i < 5;i++) {
      for (int j = 0;j < 20;j++) {
        this.car = this.car.increaseSpeed();
      }
      this.car = this.car.increaseGear();
    }
    this.car = this.car.increaseGear();
    assertEquals(5,this.car.getGear());
    assertEquals(expectOutput[9],this.car.getStatus());
    System.out.println("Test CarIncreaseGearFailReachedMaximum");
  }

  /**
   * This function test the decreaseGear function.
   * See if the status of the object is correct when the gear is decreased
   * with the condition of speed.
   *
   */
  @Test
  public void testCarDecreaseGearOk() {
    for (int i = 0;i < 10;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.increaseGear();
    this.car = this.car.decreaseGear();
    assertEquals(1,this.car.getGear());
    assertEquals(expectOutput[0],this.car.getStatus());
    System.out.println("Test CarDecreaseGearOk has passed");
  }

  /**
   * This function test the decreaseGear function.
   * See if the decrease action can be completed when the speed
   * has not reached the highest speed of former gear.
   * Check whether the status is correct.
   *
   */
  @Test
  public void testCarDecreaseGearFailDecreaseSpeed() {
    for (int i = 0;i < 5;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.increaseGear();
    for (int i = 0;i < 20;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.increaseGear();
    for (int i = 0;i < 5;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.decreaseGear();
    assertEquals(3,this.car.getGear());
    assertEquals(expectOutput[6],this.car.getStatus());
    System.out.println("Test CarDecreaseGearFailDecreaseSpeed has passed");
  }

  /**
   * This function test the decreaseGear function.
   * See if the decrease action can be completed when the gear has reached 1.
   * Check whether the status is correct.
   *
   */
  @Test
  public void testCarDecreaseGearFailReachedMinimum() {
    for (int i = 0;i < 3;i++) {
      this.car = this.car.increaseSpeed();
    }
    this.car = this.car.decreaseGear();
    assertEquals(1,this.car.getGear());
    assertEquals(expectOutput[10],this.car.getStatus());
    System.out.println("Test CarDecreaseGearFailReachedMinimum has passed");
  }
}
