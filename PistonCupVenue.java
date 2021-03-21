/* Emolyn Tumwebaze
   3/15/2021
   This class creates the Piston Cup Venue one of which the user can pick to play the game.
* */

import java.util.HashMap;

public class PistonCupVenue extends RaceVenue {

    private int WEATHER_CONDITION;      //Represents the weather condition of a particular race venue

    public PistonCupVenue() {           //Default constructor
        super();
        WEATHER_CONDITION = 5;
    }

    // initializes the following attributes for the construction of a piston cup venue.
    public PistonCupVenue(String venueName, double trackSize, int weather) {
        super(venueName, trackSize);
        WEATHER_CONDITION = weather;
    }

    // logically places paths and length in terms of miles for each path in a HashMap data structure.
    @Override
    public void determineCarPosition() {

        HashMap<Character, Double> paths = getAvailablePaths();
        char start = 'X';
        final int TWO_PATHS = 2;
        double trackSize = 3.41;

        for(int i = 0; i < TWO_PATHS; i++) {
            paths.put(start--, trackSize);
            trackSize += 1.3;
        }

    }

    /**
     * represents a sequence of characters describing meaningful content (i.e., attributes).
     *
     * @return a concatenation of meaningful characters describing things like attributes.
     */
    @Override
    public String toString() {
        return super.toString() + "Weather Condition: " + WEATHER_CONDITION + "\n\n";
    }

    /**
     * checks if two objects are equal to one another.
     *
     * @param o the object being compared for equality with "this" object.
     * @return true if both objects are equal and false if not.
     */
    @Override
    public boolean equals(Object o) {

        if (super.equals(o)) {
            return true;
        }

        PistonCupVenue obj = (PistonCupVenue) o;
        return WEATHER_CONDITION - obj.WEATHER_CONDITION == 0;
    }

}
