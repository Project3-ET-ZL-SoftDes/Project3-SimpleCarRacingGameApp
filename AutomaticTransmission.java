/* Emolyn Tumwebaze
   3/14/2021
   This class creates an Automatic Transmission for the user to pick when playing the game.
* */

public class AutomaticTransmission extends Transmission {

    private double DRAG_RATE;                             // specifies the amount of drag force on a car.
    private boolean HAS_TURBO;                            // denotes whether to add additional power to power attr.

    // Default constructor
    public AutomaticTransmission() {
        super();
    }

    /**
     * Initializes the following attributes to construct an object of type AutomaticTransmission.
     *
     * @param name            transmission name.
     * @param gearRatio       ratio of crankshaft to camshaft gears.
     * @param size            specifies the size of the automatic transmission.
     * @param numberOfGears   denotes total number of gears within an automatic transmission.
     */
    public AutomaticTransmission(String name, double gearRatio, double size, int numberOfGears) {
        super(name, gearRatio, size, numberOfGears);
    }

    /**
     * calculates a drag force threshold for a given automatic transmission.
     *
     * @return the threshold value in the form of a primitive double data type.
     */
    @Override
    public double determineDragRate() {

        final int FIFTEEN = 15;
        final double LOW_DRAG = 26.3;
        final double HIGH_DRAG = 36.3;

        DRAG_RATE = !(getNumberOfGears() >= FIFTEEN) ? LOW_DRAG : HIGH_DRAG;
        return DRAG_RATE;

    }

    /**
     * determines if a certain gear ratio is appropriate for allowing the addition of a turbo to the engine.
     *
     * @return the value in a form of a boolean denoting whether it was true that the turbo has been added.
     */
    @Override
    public boolean isTurboAdded() {
        HAS_TURBO = getGearRatio() >= 51.0 && getGearRatio() <= 100.0;
        return  HAS_TURBO;
    }

    /**
     * represents a sequence of characters describing meaningful content (i.e., attributes).
     *
     * @return a concatenation of meaningful characters describing things like attributes.
     */
    @Override
    public String toString() {
        return super.toString() + "Drag Rate: " + determineDragRate() + ", Has Turbo: " + isTurboAdded();
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

        AutomaticTransmission obj = (AutomaticTransmission) o;

        return DRAG_RATE - obj.DRAG_RATE <= 0.0001
            && HAS_TURBO == obj.HAS_TURBO;

    }

}
