package vehicle;

import java.util.HashMap;

/**
 * * * RegularManualTransmission is the main class that are to be designed and tested.
 *
 */
public class RegularManualTransmission implements ManualTransmission {

  public static final String[] STATUS_INFORMATIONS  = { "OK: everything is OK.",
    "OK: you may increase the gear.","OK: you may decrease the gear.",
    "Cannot increase speed, increase gear first.",
    "Cannot decrease speed, decrease gear first.",
    "Cannot increase gear, increase speed first.",
    "Cannot decrease gear, decrease speed first.",
    "Cannot increase speed. Reached maximum speed.",
    "Cannot decrease speed. Reached minimum speed.",
    "Cannot increase gear. Reached maximum gear.",
    "Cannot decrease gear. Reached minimum gear." };

  private int speed = 0;// Record the current speed of
  private int gear = 1;
  private HashMap<Integer,int[]> ranges = new HashMap<Integer, int[]>();
  private String current_instruction = "";
  private String current_status = "OK: everything is OK.";

  /**
   * Constructor of RegularManualTransmission.
   * @param l1 the lowest speed of gear 1
   * @param l2 the lowest speed of gear 2
   * @param l3 the lowest speed of gear 3
   * @param l4 the lowest speed of gear 4
   * @param l5 the lowest speed of gear 5
   * @param r1 the highest speed of gear 1
   * @param r2 the highest speed of gear 2
   * @param r3 the highest speed of gear 3
   * @param r4 the highest speed of gear 4
   * @param r5 the highest speed of gear 5
   *           Each lx and rx forms a range, which represents the speed range of gear x and
   *           the parameters should strictly obeys to certain rules
   *           then the construction of an object can be completed.
   *           The rules are explained in the constructor's exceptions.
   * */
  public RegularManualTransmission(int l1,int r1, int l2, int r2, int l3,
                                   int r3, int l4, int r4, int l5, int r5) {
    if (l1 != 0) {
      throw new IllegalArgumentException("ERROR: Lowest speed should be 0");
    }
    // The lowest speed of gear 1 must be 0
    else if ( l1 > r1 || l2 > r2 || l3 > r3 || l4 > r4 || l5 > r5 ) {
      throw new IllegalArgumentException("ERROR: Lowest speed should be smaller "
              + "than highest speed in one gear");
    }
    // In one gear, the lowest speed must be strictly smaller than highest speed.
    else if ( l1 > l2 || l2 > l3 || l3 > l4 || l4 > l5 ) {
      throw new IllegalArgumentException("ERROR: The lowest speed should be strictly smaller "
              + "between adjacent gears");
    }

    // In adjacent two gears, speed ranges may be overlapping,
    // but the lowest speed of latter gear must be strictly
    // bigger than the lowest speed of former gear.
    else if ( l2 - r1 > 0 || l3 - r2 > 0 || l4 - r3 > 0 || l5 - r4 > 0 ) {
      throw new IllegalArgumentException("ERROR: The ranges cannot be non-overlapping");
    }
    // Every speed must be contained, which means each adjacent may be overlapping,
    // but cannot be non-overlapping.
    else if ( r4 > r5 || r3 > r5 || r2 > r5 || r1 > r5 ) {
      throw new IllegalArgumentException("ERROR: The highest speed should be in last range");
    }
    // The highest speed of the car must be the highest speed in gear 5.

    int[] current_range = new int[2]; // used to form int[lx,rx]
    int[] left_range = new int[5]; // used to contain all lowest speed in each gear.
    int[] right_range = new int[5]; // used to contain all highest speed in each gear.
    left_range[0] = l1;
    left_range[1] = l2;
    left_range[2] = l3;
    left_range[3] = l4;
    left_range[4] = l5;
    right_range[0] = r1;
    right_range[1] = r2;
    right_range[2] = r3;
    right_range[3] = r4;
    right_range[4] = r5;

    for (int i = 0;i < 5;i++) {
      int[] tmp_range = new int[]{left_range[i],right_range[i]};
      this.ranges.put(i + 1,tmp_range);
    }
  }

  /**
   * Function to determine whether the change of speed or gear can be safely completed.
   * If can be safely completed, maintain the current change; If not, undo the change.
   * Update the current status at the end of the function.
   *
   */
  @Override
  public void changeStatus() {
    //Get the current gear.
    int current_gear = this.getGear();
    //Get the current speed.
    int current_speed = this.getSpeed();
    //Get the speed range under current gear.
    int[] current_range = this.ranges.get(current_gear);

    //Process the status when the instruction is "increase speed"
    //It has 4 types of situation, each of them indicates a certain status.
    if (current_instruction.equals("increase speed")) {
      if (current_gear == 5) {
        if (current_speed <= current_range[1]) {
          this.current_status = STATUS_INFORMATIONS[0];
        }
        else if (current_speed > current_range[1]) {
          //Cannot increase speed, reaches maximum speed.
          this.speed -= 1;
          this.current_status = STATUS_INFORMATIONS[7];
        }
      }
      else {
        int[] next_range = this.ranges.get(current_gear + 1);
        if (current_speed <= current_range[1])
          if (current_speed < next_range[0]) {
            //OK
            this.current_status = STATUS_INFORMATIONS[0];
          }
          else {
            //OK but the speed is in the range of next gear.
            this.current_status = STATUS_INFORMATIONS[1];
          }
        else {
          //Cannot increase speed, reaches the highest speed of current gear.
          this.speed -= 1;
          this.current_status = STATUS_INFORMATIONS[3];
        }
      }
    }

    //Process the status when the instruction is "decrease speed"
    //It has 4 types of situation, each of them indicates a certain status.
    else if (current_instruction.equals("decrease speed")) {
      if (current_gear == 1) {
        if (current_speed >= current_range[0]) {
          //OK
          this.current_status = STATUS_INFORMATIONS[0];
        }
        else if (current_speed < current_range[0]) {
          //Cannot decrease speed, reaches the minimum speed.
          this.speed += 1;
          this.current_status = STATUS_INFORMATIONS[8];
        }
      }
      else {
        int[] next_range = this.ranges.get(current_gear - 1);
        if (current_speed >= current_range[0])
          if (current_speed > next_range[1]) {
            //OK
            this.current_status = STATUS_INFORMATIONS[0];
          }
          else {
            //OK but the speed is in the range of former gear.
            this.current_status = STATUS_INFORMATIONS[2];
          }
        else {
          //Cannot decrease speed, reaches the lowest speed of current gear.
          this.speed += 1;
          this.current_status = STATUS_INFORMATIONS[4];
        }
      }
    }

    //Process the status when the instruction is "increase gear"
    //It has 3 types of situation, each of them indicates a certain status.
    else if (current_instruction.equals("increase gear")) {
      if (current_gear > 5) {
        //Cannot increase gear, reaches the maximum gear.
        this.gear -= 1;
        this.current_status =  STATUS_INFORMATIONS[9];
      }
      else {
        if (current_speed >= current_range[0]) {
          //OK
          this.current_status = STATUS_INFORMATIONS[0];
        }
        else {
          //Cannot increase gear, the speed is not in the range of next gear.
          this.gear -= 1;
          this.current_status = STATUS_INFORMATIONS[5];
        }
      }
    }
    else if (current_instruction.equals("decrease gear")) {
      if (current_gear <= 0) {
        //Cannot decrease gearm reaches the minimum gear.
        this.gear += 1;
        this.current_status = STATUS_INFORMATIONS[10];
      }
      else {
        if (current_speed <= current_range[1]) {
          //OK
          this.current_status = STATUS_INFORMATIONS[0];
        }
        else {
          //Cannot decrease gear, the speed is not in the range of former gear.
          this.gear += 1;
          this.current_status = STATUS_INFORMATIONS[6];
        }
      }
    }
    return ;
  }


  /**
   * Function to get the speed of current object.
   *
   * @return this.speed
   */
  @Override
  public int getSpeed() {
    return this.speed;
  }

  /**
   * Function to get the gear of current object.
   *
   * @return this.gear
   */
  @Override
  public int getGear() {
    return this.gear;
  }

  /**
   * Function to get the status of current object.
   *
   * @return this.current_status
   */
  @Override
  public String getStatus() {
    return this.current_status;
  }

  /**
   * Function performs the speed increasing action.
   * Then call the changeStatus() function to determine whether the action can be completed.
   *
   * @return this
   */
  @Override
  public RegularManualTransmission increaseSpeed() {
    this.speed += 1;
    this.current_instruction = "increase speed";
    this.changeStatus();
    return this;
  }

  /**
   * Function performs the speed decreasing action.
   * Then call the changeStatus() function to determine whether the action can be completed.
   *
   * @return this
   */
  @Override
  public RegularManualTransmission decreaseSpeed() {
    this.speed -= 1;
    this.current_instruction = "decrease speed";
    this.changeStatus();
    return this;
  }

  /**
   * Function  performs the gear increasing action.
   * Then call the changeStatus() function to determine whether the action can be completed.
   *
   * @return this
   */
  @Override
  public RegularManualTransmission increaseGear() {
    this.gear += 1;
    this.current_instruction = "increase gear";
    this.changeStatus();
    return this;
  }

  /**
   * Function  performs the gear decreasing action.
   * Then call the changeStatus() function to determine whether the action can be completed.
   *
   * @return RegularManualTransmission
   */
  @Override
  public RegularManualTransmission decreaseGear() {
    this.current_instruction = "decrease gear";
    this.gear -= 1;
    this.changeStatus();
    return this;
  }
}
