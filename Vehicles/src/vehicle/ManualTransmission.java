package vehicle;

/**
 * The interfaces of main Class RegularManualTransmission.
 */
public interface ManualTransmission {


  /**
  * Get the current speed of this object in INT.
  *
  * @return Vehicle.RegularManualTransmission.speed
  */
  public int getSpeed();


  /**
  * Get the current gear of the car object in INT.
  *
  * @return Vehicle.RegularManualTransmission.gear
  */
  public int getGear();


  /**
  * After gear change or speed change, check whether the change can be performed successfully,
  * if so, keep the change and update the status of this object according to current situation,
  * if not, based on the action type, the function undo the change, update the status.
  * This function does both the determine and update work.
  *
  */
  public void changeStatus();


  /**
  * Performs the increase speed action without the consideration of whether it succeeds or not.
  * Leave the determine procedure to function changeStatus().
  *
  * @return Vehicle.RegularManualTransmission
  */
  public RegularManualTransmission increaseSpeed();


  /**
  * Performs the decrease speed action without the consideration of whether it succeeds or not.
  *
  * @return Vehicle.RegularManualTransmission
  */
  public RegularManualTransmission decreaseSpeed();


  /**
   * Performs the increase gear action without the consideration of whether it succeeds or not.
   *
   * @return Vehicle.RegularManualTransmission
   */
  public RegularManualTransmission increaseGear();


  /**
  * Performs the decrease gear action without the consideration of whether it succeeds or not.
  *
  * @return Vehicle.RegularManualTransmission
  */
  public RegularManualTransmission decreaseGear();


  /**
  * Get the current status of the object in STRING.
  *
  * @return Vehicle.RegularManualTransmission.current_status
  */
  public String getStatus();
}
