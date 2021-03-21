/* Emolyn Tumwebaze
   3/14/2021
   Transmission class is an abstract class incharge of giving the user a variety of transmissions
   to pick from.

* */

public abstract class Transmission {

    private String name;        // Represents the name of a transmission
    private double gearRatio;   // ratio between cam and crankshaft gears within a transmission.
    private double size;        // Represents the size of the transmission
    private int numberOfGears;  // Represents the number of gears a car will have

    // Default Constructor
    public Transmission() {
        this("QuickTransmission", 3.6, 15.31, 21);
    }

    // initializes the following attributes for the construction of a transmission.
    public Transmission(String name, double gearRatio,double size, int numberOfGears) {

        this.name = name;
        this.gearRatio = gearRatio;
        this.size = size;
        this.numberOfGears = numberOfGears;

    }

    /**
     * calculates a drag force threshold for a given automatic transmission.
     *
     * @return the threshold value in the form of a primitive double data type.
     */
    public abstract double determineDragRate();

    /**
     * determines if a certain gear ratio is appropriate for allowing the addition of a turbo to the engine.
     *
     * @return the value in a form of a boolean denoting whether it was true that the turbo has been added.
     */
    public abstract boolean isTurboAdded();

    /**
     * accessor method for the transmission's name.
     *
     * @return the name of a given transmission.
     */
    public String getName() {
        return name;
    }

    /**
     * accessor method for the gear ratio.
     *
     * @return the ratio of cam to crankshaft.
     */
    public double getGearRatio() {
        return gearRatio;
    }

    /**
     * accessor method for the transmission's size.
     *
     * @return the size of a given transmission.
     */
    public double getSize() {
        return size;
    }

    /**
     * accessor method for the number of gears or pistons contained within a transmission.
     *
     * @return the total number of gears or pistons.
     */
    public int getNumberOfGears() {
        return numberOfGears;
    }

    /**
     * represents a sequence of characters describing meaningful content (i.e., attributes).
     *
     * @return a concatenation of meaningful characters describing things like attributes.
     */
    @Override
    public String toString() {

        return "Transmission name: " + name + ", Gear Ratio: "  + gearRatio +
               ", Size: " + size + ", Number of Gear(s): " + numberOfGears  +
               ( (numberOfGears == 1) ? " gear, " : " gears, " );

    }

    /**
     * checks if two objects are equal to one another.
     *
     * @param o the object being compared for equality with "this" object.
     * @return true if both objects are equal and false if not.
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        } else if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Transmission obj = (Transmission) o;

        return       name.compareTo(obj.name) == 0
                &&   gearRatio - obj.gearRatio <= 0.0001
                &&   size - obj.size <= 0.0001
                &&   numberOfGears - obj.numberOfGears == 0;
    }

    /**
     *   a unique hash code value that can be used for indexing
     *   within an associative array data structure such as a HashMap.
     *
     *   @return a unique integer value.
     */
    @Override
    public int hashCode() {
        return (31 * getName().hashCode()) + 27;
    }

}
