/* Emolyn Tumwebaze
   3/15/2021
   This class creates the Tropical Island Venue the user to pick to play the game.
* */

import java.util.HashMap;

public class TropicalIslandVenue extends RaceVenue {

    private int WEATHER_CONDITION;      // Represents the weather condition of a particular race venue

    // Default constructor
    public TropicalIslandVenue() {
        this("TropicalIsland", 5.0, 7);
    }

    // initializes the following attributes for the construction of a tropical island venue.
    public TropicalIslandVenue(String venueName, double trackSize, int weather) {
        super(venueName, trackSize);
        WEATHER_CONDITION = weather;
    }

    // logically places paths and length in terms of miles for each path in a HashMap data structure.
    @Override
    public void determineCarPosition() {

        HashMap<Character, Double> paths = getAvailablePaths();
        char start = 'Q';
        final int THREE_PATHS = 3;
        double trackSize = 1.04;

        for(int i = 0; i < THREE_PATHS; i++) {
            paths.put(start--, trackSize);
            trackSize += 2.79;
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

        TropicalIslandVenue obj = (TropicalIslandVenue) o;
        return WEATHER_CONDITION - obj.WEATHER_CONDITION == 0;

    }

}
