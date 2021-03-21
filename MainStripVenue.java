/* Emolyn Tumwebaze
   3/15/2021
   Main Strip Venue for the user to pick.
* */

import java.util.HashMap;

public class MainStripVenue extends RaceVenue {

    private final int WEATHER_CONDITION;             // Represents the weather condition of a particular race venue

    // default constructor.
    public MainStripVenue() {
        this("MainStrip", 3.5, 3);
    }

    // initializes the following attributes for the construction of a tropical island venue.
    public MainStripVenue(String venueName, double trackSize, int weather) {
        super(venueName, trackSize);
        WEATHER_CONDITION = weather;
    }

    // logically places paths and length in terms of miles for each path in a HashMap data structure.
    @Override
    public void determineCarPosition() {

        HashMap<Character, Double> paths = getAvailablePaths();
        char start = 'A';
        final int FOUR_PATHS = 4;
        double trackSize = 2.8;

        for(int i = 0; i < FOUR_PATHS; i++) {
            paths.put(start++, trackSize);
            trackSize += 0.2;
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

        MainStripVenue obj = (MainStripVenue) o;
        return WEATHER_CONDITION - obj.WEATHER_CONDITION == 0;

    }

}
