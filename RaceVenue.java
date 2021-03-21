import javafx.scene.paint.Color;

import java.util.*;

/**
 *   zachary lavoie
 *   3/16/2021
 *   CS 225 Software Design
 *
 *   Description: this class represents a race venue with simple constraints like track length in terms of miles
 *                & a name for the venue. Intended to create a somewhat realistic racing environment for the user
 *                to race other computer racers (bot racers).
 */
public abstract class RaceVenue {

    private HashMap<Car, Long> carLapTimeMap;               // holds entries of cars and their overall finish time.
    private final Car[] BOT_RACE_CARS;                      // computer racers that will race the user.
    private double trackSizeInMiles;                        // total length of the race track in terms of miles.
    private long startTime;                                 // the time representing when the race has STARTED.
    private long endTime;                                   // the time representing when the race has ENDED.
    private HashMap<Character, Double>  paths;              // holds entries of paths and their total length in miles.
    private Car winner;                                     // reference to the winner with the smallest total time.
    private String venueName;       // reference to the sequence of meaningful characters representing the venue name.

    // default constructor for the basic construction of a race venue.
    public RaceVenue() {
        this("QuickVenue", 4.5);
    }

    /**
     * the following attributes are initialized for the construction of a race venue.
     *
     * @param venueName an arbitrary name to call the venue.
     * @param trackSize an arbitrary track size in terms of miles (i.e., how long the overall venue is with paths).
     */
    public RaceVenue(String venueName, double trackSize) {

        this.venueName = venueName;
        trackSizeInMiles = trackSize;
        winner = null;
        carLapTimeMap = new HashMap<>();
        paths = new HashMap<>();
        startTime = 0;
        endTime = 0;
        BOT_RACE_CARS = createBotCars();

    }

    /**
     * responsible for creating paths and overall lengths in terms of miles for those paths.
     * This will allow the programmer to create randomization and with this the cars can then
     * be randomly placed at a given created path that was created with this method (i.e., this method
     * creates paths and fills up the HashMap data structure accordingly. Then cars are randomly placed
     * at those path starting locations, of the paths that were created, so path A, B, and so on.).
     */
    public abstract void determineCarPosition();

    /**
     * retrieves and returns the current time
     * as a long value which can be casted to
     * something like a byte, short, or int for
     * memory efficiency if desired. The current
     * time in terms of how much time has so far
     * been spent on the track without reaching the finish line yet.
     *
     * @param car a car in request of having its current time on the track checked.
     * @return the current total time that a given car has been on the track without crossing the finish line yet
     *         in the form of a "long" primitive data type.
     */
    public long getCurrentTrackTimeOf(Car car) {

        HashMap<Car, Long> racers = getAllCarLapTimes();

        if(racers.size() == 0 || car == null) {
            return -1;
        }

        long startTime = getStartTime();
        long currTime = System.currentTimeMillis();

        for(Map.Entry<Car, Long> carLongEntry : racers.entrySet()) {

            if(carLongEntry.getKey().equals(car)) {
                return currTime - startTime;
            }

        }

        // assert that no car was found to check its current running track time.
        return -1;

    }

    /**
     * private helper method designed to create FIVE computer cars for the intention of racing the user's car.
     *
     * @return an array of cars that will represent the bot (or computer) racers.
     */
    private Car[] createBotCars() {

        final int ELEMENTS = 5;
        Car[] cars = new Car[ELEMENTS];

        cars[0] = new Car();

        cars[1] = new Car( 18, 21.5, 34.3,
                "Spok", "Crusher", Color.LIGHTSALMON,
                new OneHundredCCEngine(), new ManualTransmission() );

        cars[2] = new Car( 21, 15.6, 54.444,
                "James", "Riker", Color.DEEPPINK,
                new OneHundredFiftyCCEngine(),
                new AutomaticTransmission("Auto_Porter", 33.55, 50.21, 28) );

        cars[3] = new Car(32, 12.45, 17.67,
                          "Gemani", "Paris", Color.LIGHTCORAL,
                           new FiftyCCEngine("50 Winzz", 16, true),
                           new ManualTransmission("Spider", 29.50, 55.72, 15) );

        cars[4] = new Car(23, 50.44, 62.57,
                          "Siren", "Blaze", Color.DARKCYAN,
                          new OneHundredCCEngine(), new AutomaticTransmission() );

        addBots(cars);

        return cars;

    }

    /**
     * private helper method for simply adding all bot cars to an associative array that will map
     * each car with their corresponding track time value so when looking up a car the value returned will
     * be the car's overall track time.
     *
     * @param cars the array of bot cars being added.
     */
    private void addBots(Car[] cars) {

        for(Car car : cars) {
            carLapTimeMap.put(car, 0L);
        }

    }

    /**
     * accessor method for retrieving and returning the race venue's tracked START TIME.
     *
     * @return the START time value.
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * accessor method for retrieving and returning the race venue's tracked END TIME.
     *
     * @return the END time value.
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * mutator method for setting the race venue's initial START TIME.
     *
     * @param startTimer the STARTING time value.
     */
    public void setStartTime(long startTimer) {
        startTime = startTimer;
    }

    /**
     * mutator method for setting the race venue's initial END TIME.
     *
     * @param endTimer the ENDING time value.
     */
    public void setEndTime(long endTimer) {
        endTime = endTimer;
    }

    /**
     * accessor method for retrieving and returning the race venue's overall track length in terms of miles.
     *
     * @return the track length including all paths.
     */
    public double getTrackSizeInMiles() {
        return trackSizeInMiles;
    }

    /**
     * accessor method for retrieving and returning an array copy of the race venue's bot racers. This is
     * so that any mutations that occur will occur on the returned copy of the object.
     *
     * @return a copy of the original array's length and elements within it.
     */
    public Car[] getBotCars() {
        return Arrays.copyOf(BOT_RACE_CARS, BOT_RACE_CARS.length);
    }

    /**
     * accessor method for retrieving and returning the name of the race venue.
     *
     * @return the sequence of meaningful characters that represent the name of "this" venue.
     */
    public String getVenueName() {
        return venueName;
    }

    /**
     * retrieves and returns the total time
     * as a long value which can be casted to
     * something like a byte, short, or int for
     * memory efficiency if desired. The total
     * time in terms of how much time was
     * spent on the track before the finish line had been reached.
     *
     * @param car a car in request of having its total time on the track checked.
     * @return the total time that a given car had been on the track in the form of a "long" primitive data type.
     */
    public long getTotalTimeTakenOf(Car car) {

        if(carLapTimeMap.size() == 0 || car == null) {
            return -1;
        }

        Car currCar;
        long totalLapTime;

        for(Map.Entry<Car, Long> carLongEntry : carLapTimeMap.entrySet()) {

            currCar = carLongEntry.getKey();
            totalLapTime = carLongEntry.getValue();

            if(currCar.equals(car)) {
                return totalLapTime;
            }

        }

        // assert parameter passed to this method does not exist within the collection.
        return -1;

    }

    /**
     * accessor method for retrieving and returning the reference to the available paths within the race venue.
     *
     * @return a HashMap of paths denoted as characters to represent meaning like Path A, Path B, Path C, and so on.
     *         The associative values will represent the total length in terms of miles of a given path
     *         like Path A for example.
     */
    public HashMap<Character, Double> getAvailablePaths() {
        return paths;
    }

    /**
     * accessor method for retrieving and returning the reference to the cars within the race venue and their total
     * lap times.
     *
     * @return a HashMap of cars and their corresponding total lap times.
     */
    public HashMap<Car, Long> getAllCarLapTimes() {
        return carLapTimeMap;
    }

    /**
     * ***************************************************************************************************************
     * 1st off - more attention to memory space complexity could heavily improve this method overall. personally i   *
     * feel that it could be done simpler however this is one of many ways to achieve retrieving and returning the   *
     * winner of the race who would have to have the smallest overall lap time than the other racers.                *
     * Kind of redundant toward the end of the method definition.                                                    *
     * ***************************************************************************************************************
     *
     * accessor method for retrieving and returning the racer who had the smallest total lap time than the rest.
     *
     * @return a reference to a car object that happens to also have a method for retrieving and returning the name
     *         of the driver of the winning car object (i.e., this car reference can allow the programmer to create
     *         any meaningful messages with its attributes.).
     */
    public Car getWinner() {

        if(carLapTimeMap.size() == 0) {
            return null;
        }

        winner = null;
        Set<Map.Entry<Car, Long>> carLapTimes = Collections.unmodifiableSet(carLapTimeMap.entrySet());
        long[] extractedCarLapTimes = new long[carLapTimes.size()];
        int index = 0;

        for(Map.Entry<Car, Long> entry : carLapTimes) {
            extractedCarLapTimes[index++] = entry.getValue();
        }

        long smallestCarLapTime = extractedCarLapTimes[0];

        for(long lapTime : extractedCarLapTimes) {

            if(lapTime < smallestCarLapTime) {
                smallestCarLapTime = lapTime;
            }

        }

        for(Map.Entry<Car, Long> finalEntryCheck : carLapTimeMap.entrySet()) {

            if(finalEntryCheck.getValue() == smallestCarLapTime) {
                winner = finalEntryCheck.getKey();
                break;
            }

        }

        return winner;

    }

    /**
     * represents a sequence of characters describing meaningful content (i.e., attributes).
     *
     * @return a concatenation of meaningful characters describing things like attributes.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Venue :");
        sb.append(getVenueName());
        sb.append("\tTrack Size :");
        sb.append(getTrackSizeInMiles());
        sb.append(" Mile(s)");
        sb.append("\n--------------------------------------------------------------**\n");

        Car car;
        long lapTime;

        for(Map.Entry<Car, Long> carLongEntry : carLapTimeMap.entrySet()) {

            lapTime = carLongEntry.getValue();
            sb.append("_> Lap Time :");
            sb.append(lapTime);
            car = carLongEntry.getKey();
            sb.append(" millisecond(s)\n");
            sb.append(car);
            sb.append("\n");

        }

        return sb.toString();

    }

    /**
     * checks if two objects are equal to one another.
     *
     * @param o the object being compared for equality with "this" object.
     * @return true if both objects are equal and false if not.
     */
    @Override
    public boolean equals(Object o) {

        if(this == o) {
            return true;
        } else if(o == null || getClass() != o.getClass()) {
            return false;
        }

        RaceVenue raceVenueObj = (RaceVenue) o;
        final double EPSILON_THRESHOLD_VALUE = 0.0001;

        return getVenueName().compareTo(raceVenueObj.getVenueName()) == 0
            && getTrackSizeInMiles() - raceVenueObj.getTrackSizeInMiles() <= EPSILON_THRESHOLD_VALUE;

    }

}
