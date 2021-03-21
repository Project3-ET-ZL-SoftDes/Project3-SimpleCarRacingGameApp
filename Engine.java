/* Emolyn Tumwebaze
   3/14/2021
   Engine class is an abstract class in charge of giving the user a variety of engines
   to pick from.
* */

public abstract class Engine {

    private String name;                      // represents name of a given engine.
    private int numOfCylinders;               // number of cylinders of a given engine
    private boolean isSingleOrDualCamShaft;   // denotes whether the cylinder head contains 1 or 2 camshafts.

    // Default Constructor
    public Engine() {
        this("QuickEngine", 21, false);
    }

    /**
     * Initializes the following attributes for the construction of an object of type Engine.
     *
     * @param name                      the name of a given engine.
     * @param numOfCylinders            represents the total number of pistons contained within a given engine.
     * @param isSingleOrDualCamShaft    a boolean representing an engine with 1 or 2 camshafts by true being 1,
     *                                  and 2 being 2.
     */
    public Engine(String name, int numOfCylinders, boolean isSingleOrDualCamShaft) {

        this.name = name;
        this.numOfCylinders = numOfCylinders;
        this.isSingleOrDualCamShaft = isSingleOrDualCamShaft;

    }

    /**
     * retrieves and returns the threshold value of a given engine's speed.
     *
     * @return the maximum allowable speed via a threshold value spec.
     */
    public abstract double getSpeedThreshold();

    /**
     * retrieves and returns the threshold value of a given engine's power.
     *
     * @return the maximum allowable power via a threshold value spec.
     */
    public abstract double getPowerThreshold();

    /**
     * accessor method for getting the name of a given engine.
     *
     * @return the engine's name.
     */
    public String getName() {
        return name;
    }

    /**
     * accessor method for getting the number of pistons contained within the engine.
     *
     * @return the total number of pistons.
     */
    public int getNumOfCylinders() {
        return numOfCylinders;
    }

    /**
     * accessor method for camshaft determination.
     *
     * @return a boolean denoting whether there are 1 or 2 camshafts.
     */
    public boolean isSingleOrDualCamShaft() {
        return isSingleOrDualCamShaft;
    }

    /**
     * represents a sequence of characters describing meaningful content (i.e., attributes).
     *
     * @return a concatenation of meaningful characters describing things like attributes.
     */
    @Override
    public String toString() {
        return "name: " + name + ", NO. of cylinders: " + numOfCylinders;
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

        Engine obj = (Engine)o;

        return getName().compareTo(obj.getName()) == 0
          &&   getNumOfCylinders() - obj.getNumOfCylinders() == 0
          &&   isSingleOrDualCamShaft() == obj.isSingleOrDualCamShaft();
    }

    /**
     *   a unique hash code value that can be used for indexing
     *   within an associative array data structure such as a HashMap.
     *
     *   @return a unique integer value.
     */
    @Override
    public int hashCode() {
        return (31 * getName().hashCode()) + 57;
    }

}
