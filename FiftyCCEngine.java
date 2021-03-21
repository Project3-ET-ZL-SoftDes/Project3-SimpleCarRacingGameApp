/* Emolyn Tumwebaze
   3/14/2021
   This class creates a FiftyCCEngine for the user to opt for when playing the game.
* */

public class FiftyCCEngine extends Engine {

    private double SPEED_THRESHOLD;    // Represents how fast a car can move
    private double POWER_THRESHOLD;    // Represents the maximum output torque of a given 50 cc engine.

    // Default Constructor
    public FiftyCCEngine() {
        super();
    }

    // initializes the following attributes for the construction of a 50 cc engine.
    public FiftyCCEngine(String name, int numOfCylinders, boolean isSingleOrDualCamShaft) {
        super(name, numOfCylinders, isSingleOrDualCamShaft);
    }

    /**
     * retrieves and returns the threshold value of a given engine's speed.
     *
     * @return the maximum allowable speed via a threshold value spec.
     */
    @Override
    public double getSpeedThreshold() {

        SPEED_THRESHOLD = 25.0;
        return SPEED_THRESHOLD;

    }

    /**
     * retrieves and returns the threshold value of a given engine's power.
     *
     * @return the maximum allowable power via a threshold value spec.
     */
    @Override
    public double getPowerThreshold() {

        POWER_THRESHOLD = 15.0;
        return POWER_THRESHOLD;

    }

    /**
     * represents a sequence of characters describing meaningful content (i.e., attributes).
     *
     * @return a concatenation of meaningful characters describing things like attributes.
     */
    @Override
    public String toString() {

        return       super.toString() +
                ", Speed Threshold: " +
                  getSpeedThreshold() +
            " mph, Power Threshold: " +
          getPowerThreshold() + " mph";

    }

    /**
     * checks if two objects are equal to one another.
     *
     * @param o the object being compared for equality with "this" object.
     * @return true if both objects are equal and false if not.
     */
    @Override
    public boolean equals(Object o) {

        if(super.equals(o)) {
            return true;
        }

        FiftyCCEngine obj = (FiftyCCEngine) o;

        return SPEED_THRESHOLD - obj.SPEED_THRESHOLD <= 0.0001
            && POWER_THRESHOLD - obj.POWER_THRESHOLD <= 0.0001;
    }

}
