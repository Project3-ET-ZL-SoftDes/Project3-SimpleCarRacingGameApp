import javafx.scene.paint.Color;

/**
 *   zachary lavoie
 *   3/14/2021
 *   CS 225 Software Design
 *
 *   Description: This class is a representation of a simple car object.
 *                A given car is a driveable object which means it has
 *                the ability to move and stop itself. This car representation
 *                is supposed to be thought of as just the basics that embody a
 *                simple car (i.e., a car will have essentials like a driver name, wheel type,
 *                initially set speed and power values, etc.)
 */

public class Car implements Driveable {

    private int numTires;                  // how many tires the car will have.
    private double speed;                  // the speed of which the car is traveling at.
    private double power;                  // the power of the car's transmission in terms of percentage of strength.
    private String wheelType;              // the name of the car's steering wheel.
    private String driverName;             // the name of the driver of the car (i.e., the user).
    private Color carColor;                // the color of which the car is "dressed" with.
    private Engine engine;                 // the car's engine. It is the "heart."
    private Transmission transmission;     // the car's transmission. It is the "muscle."

    /**
     *     default constructor makes a call to an overloaded constructor and
     *  passes in actual parameters to denote default values for a given default
     *  instance of type Car.
     */
    public Car() {
        this(4, 35.0, 25.8,
           "Frat", "SpaceDriver", Color.RED,
                       new FiftyCCEngine(), new AutomaticTransmission());
    }

    /**
     * overloaded constructor initializes the following attributes' values with everything
     * that is minimally expected for the construction of a simple car object.
     *
     * @param numTires       total number of tires.
     * @param speed          initial acceleration speed.
     * @param power          initial transmission power.
     * @param wheelType      name for the driver's steering wheel (i.e., the user's steering wheel.).
     * @param driverName     name of the driver (i.e., user's name specified upon construction of a car object.).
     * @param carColor       preferred for the look (i.e., the color of a car's uni-body) of a given car object.
     * @param engine         allows the car object to mechanically run. A car object cannot exist without an engine.
     * @param transmission   transfers power to the wheels; movement. A car object cannot exist without a transmission.
     */
    public Car( int numTires, double speed, double power,
                String wheelType, String driverName, Color carColor,
                Engine engine, Transmission transmission )
    {
        this.numTires = numTires;
        this.speed = speed;
        this.power = power;
        this.wheelType = wheelType;
        this.driverName = driverName;
        this.carColor = carColor;
        this.engine = engine;
        this.transmission = transmission;
    }

    /**
     * accessor method for returning the value corresponding to the total number of tires.
     *
     * @return number of tires expressed as an integer primitive value.
     */
    public int getTires() {
        return numTires;
    }

    /**
     * accessor method for returning the value corresponding to the car's current speed at which it travels at.
     *
     * @return A car object's current traveling speed expressed in miles per hour (mph).
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * accessor method for returning the value corresponding to the car's transmission power (horse power).
     *
     * @return a percentage value corresponding to a car's overall horse power put out via its transmission.
     */
    public double getPower() {
        return power;
    }

    /**
     * accessor method for returning the value corresponding to the name of a car object's steering wheel.
     *
     * @return a string representation of a sequence of characters that express a meaningful name for a given wheel.
     */
    public String getWheel() {
        return wheelType;
    }

    /**
     * accessor method for returning the value corresponding to the name of the driver of a car object (i.e., user).
     *
     * @return a string representation of a sequence of characters that express a meaningful name for a given user.
     */
    public String getNameOfDriver() {
        return driverName;
    }

    /**
     * represents the exterior body color of a given car object.
     *
     * @return a reference to a car object's color.
     */
    public Color getColor() {
        return carColor;
    }

    /**
     * represents a car object's engine that will allow the car to be a driveable machine.
     *
     * @return a reference to a car object's engine.
     */
    public Engine getEngine() {
        return engine;
    }

    /**
     * represents a car object's transmission that will allow power to transfer to the tires, causing movement.
     *
     * @return a reference to a car object's transmission.
     */
    public Transmission getTransmission() {
        return transmission;
    }

    /**
     * represents the contents of a given car object (e.g., the engine, transmission, and the name of the driver).
     *
     * @return a string representation of a sequence of meaningful characters that correspond
     *         to the engine, transmission, and name of the driver (or user) in the form of human-readable text.
     */
    @Override
    public String toString() {

        return "------------------------------------------------------------" +
               "----------------------------------------------------------------------\n" +
               "Driver : " + getNameOfDriver() + "\n--------\n\nEngine:\n--------\n" + getEngine()
             + "\n\nTransmission:\n--------------\n" + getTransmission()
             + "\n------------------------------------------------------------" +
               "---------------------------------------------------------------------\n";
    }

    /**
     *  checks if another object (the goal would be looking for another car object to compare with)
     *  is equal to "this" car object.
     *
     * @param o another object (hopefully a car).
     * @return true if both instances are equal (i.e., checking if both objects are pointing to the same object,
     *         checking attributes of both instances for 100% similarities, etc.) and false if deemed "not equal."
     */
    @Override
    public boolean equals(Object o) {

        final double EPSILON_THRESHOLD_VALUE = 0.0001;
        final int ZERO = 0;

        if(this == o) {
            return true;
        } else if(o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Car carObj = (Car)o;

        return (numTires - carObj.numTires) == ZERO
            && (speed - carObj.speed) <= EPSILON_THRESHOLD_VALUE
            && (power - carObj.power) <= EPSILON_THRESHOLD_VALUE
            && wheelType.compareTo(carObj.wheelType) == ZERO
            && driverName.compareTo(carObj.driverName) == ZERO
            && carColor == carObj.carColor
            && engine.equals(carObj.engine)
            && transmission.equals(carObj.transmission);

    }

    /**
     * Creates a meaningful integer value that can be used in conjunction with a Map abstract data type
     * (e.g., a HashMap data structure that implements the Map abstract data type.). When invoked, an integer
     * value will be computed from variant instance members chosen from within the "Car" class definition.
     *
     * @return a meaningful integer value that represents a given instance of type Car that can be used for indexing
     *         within an associative array data structure such as HashMap, LinkedHashMap, etc.
     */
    @Override
    public int hashCode() {

        int multiHash =  7 * ( getWheel().hashCode() + getNameOfDriver().hashCode()
                             + getColor().hashCode() + getEngine().hashCode()
                             + getTransmission().hashCode() );

        return (31 * multiHash) + 57;

    }

    /**
     * takes a parameter and uses its value to modify the state of a given car object's speed.
     *
     * @param mph denotes acceleration rate (i.e., accelerate by how much which is specified by the parameter.).
     */
    @Override
    public void accelerate(double mph) {

        if( (speed + mph) > engine.getSpeedThreshold() ) {
            return;
        }

        speed += mph;

    }

    /**
     *   when invoked, it will cause a given car object to
     *   begin to slow down until, if not already, the speed falls to 0.0.
     */
    @Override
    public void brake() {

        final double VELOCITY = 2.0;
        final int ZERO = 0;

        if(speed - VELOCITY < ZERO) {
            speed = ZERO;
        }

        speed -= VELOCITY;

    }

}
