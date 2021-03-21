
/**
 *   zachary lavoie
 *   3/14/2021
 *   CS 225 Software Design
 *
 *   Description: all classes that implement this interface will only be composed of
 *                objects that are physically driveable (i.e., have the ability to move and stop.).
 *                e.g., a given 4-wheel car (sedan) will be able to physically move and stop itself
 *                with assistance from the user (driver).
 */
public interface Driveable {

    /**
     * when a value for mph is passed to this method, a given object will then be able to move itself.
     *
     * @param mph denotes acceleration rate (i.e., accelerate by how much which is specified by the parameter.).
     */
    void accelerate(double mph);

    /**
     * when this method is invoked, a given object will be able to stop itself.
     */
    void brake();

}
