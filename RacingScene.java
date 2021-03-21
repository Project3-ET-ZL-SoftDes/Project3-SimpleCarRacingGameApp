import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

//                                                                                                      (E.T. & Z.L.)
// represents a scene that displays a race-event concept along with an interactive controller to the user for racing.
public class RacingScene implements EventHandler<KeyEvent> {

    private Pane pnMainLayout;                         // primary layout manager.
    private ArrayList<Rectangle> cars;                 // rectangular representation of all 6 racers (user incl.).
    private ArrayList<Line> boundaries;                // linear representation of all walls and starting/ending lines.
    private boolean raceCompleted;                     // a flag to alert the class of when the race has finished.
    private Color weather;                             // a reference to the color of the background of the scene.
    private double velX;                               // movement on the x-coordinate graph from the origin.
    private double velY;                               // movement on the y-coordinate graph from the origin.
    private String currentVenueInUse;                  // a reference to the name of the venue being used in the scene.
    private boolean allSet = false;                    // a flag to alert the class of when the user has finished.
    private boolean botsAllSet = false;                // a flag to alert the class of when all comp. racers finished.
    private int fullCount;                             // a number that represents the total # of racers who finished.
    private RaceVenue currentVenueObject;              // a reference to the race venue being used in the scene.
    private Car currentUser;                           // a reference to the user's car being used in the scene.

    //                                                                                                  (E.T. & Z.L.)
    // default constructor that deals with initializing the following attributes for construction of the racing scene.
    public RacingScene() {

        pnMainLayout = new Pane();

        Text message = new Text();
        message.setText("\t\t\t\t\t\t\t\tREADY? SET?! GO!\n\"<\" - Left movement, \">\" - right movement, "
                      + "\"^\" - acceleration, & \"DOWN Arrow Key\" - to brake/reverse.");
        message.setFont(Font.font("Times New Roman", 14.0));
        message.setStroke(Color.LIGHTYELLOW);
        message.setFill(Color.WHITE);
        pnMainLayout.getChildren().add(message);
        pnMainLayout.getChildren().get(0).setLayoutX(200.0);
        pnMainLayout.getChildren().get(0).setLayoutY(25.0);

        cars = new ArrayList<>();

        boundaries = new ArrayList<>();

        raceCompleted = false;
        weather = null;

        velX = 2.0;
        velY = 2.0;

        fullCount = 0;

    }

    /**                                                                                                        (E.T.)
     * mutator method for setting the "raceCompleted" attribute of "this" instance.
     *
     * @param state the true or false value to indicate the overall completion state of the race.
     */
    public void setFinishedRaceState(boolean state) {
        raceCompleted = state;
    }

    /**                                                                                                        (E.T.)
     * accessor method for retrieving and returning the overall completion state of the race.
     *
     * @return true if race is complete and false if not.
     */
    public boolean checkRaceState() {
        return raceCompleted;
    }

    /**                                                                                                 (E.T. & Z.L.)
     *
     * responsible for creating and returning a new racing scene to be displayed on the main application window.
     *
     * @param car        the user's created car.
     * @param raceVenue  the user's desired race venue selection.
     * @return a new scene with the somewhat look and feel of a racing event in which the user will be able to race.
     */
    public Scene scene(Car car, RaceVenue raceVenue) {

        currentUser = car;
        currentVenueInUse = raceVenue.getVenueName();
        currentVenueObject = raceVenue;

        Scene scene;

        setWeatherConditions(currentVenueInUse);
        setUpRespectivePaths(currentVenueObject);
        populateRacers(currentUser, currentVenueObject);

        scene = new Scene(pnMainLayout, 1000, 500);
        scene.setOnKeyTyped(this);
        scene.setOnKeyPressed(this);
        scene.setOnKeyReleased(this);

        raceVenue.setStartTime(System.currentTimeMillis());
        return scene;

    }

    /**                                                                                                        (Z.L.)
     * Responsible for handling all key-related events within the racing scene object.
     *
     * @param keyEvent an arbitrarily fired key event from a graphical component within the racing scene
     *                 or the racing scene object itself.
     */
    @Override
    public void handle(KeyEvent keyEvent) {

        // deal with user.   Z.L.
        double velX;
        double velY;

        for(Rectangle car : cars) {

            velX = car.getX();
            velY = car.getY();

            if(car.getFill() == Color.RED || car.getFill() == Color.GREEN || car.getFill() == Color.BLUE) {

                String[] data;
                data = currentVenueInUse.split(" ");
                char specialCharacter = data[1].charAt(0);

                switch(specialCharacter) {

                    case 'T':
                        optionT(keyEvent, car, velX, velY);
                        simulateComps('T');      // deal with comps. in tropical island venue.     Z.L.
                        break;
                    case 'S':
                        optionS(keyEvent, car, velX, velY);
                        simulateComps('S');      // deal with comps. in main strip venue.          Z.L.
                        break;
                    case 'C':
                        optionC(keyEvent, car, velX, velY);
                        simulateComps('C');      // deal with comps. in piston cup venue.          Z.L.
                        break;

                }

                break;

            }

        }

    }

    //                                                                                                         (Z.L.)
    // private helper method to place the cars in random locations within the existing paths to the current race venue.
    private void setUpRespectivePaths(RaceVenue raceVenue) {

        final int NUM_BOUNDARIES = 8;

        for(int i = 0; i < NUM_BOUNDARIES; i++) {
            boundaries.add(new Line());
        }

        String[] data;
        data = raceVenue.getVenueName().split(" ");
        char specialCharacter = data[1].charAt(0);

        switch(specialCharacter) {

            case 'T':
                optionT();
                break;
            case 'S':
                optionS();
                break;
            case 'C':
                optionC();
                break;

        }

        for(Line l : boundaries) {
            pnMainLayout.getChildren().add(l);
        }

    }

    /* private helper method for creating and populating the                                                   (Z.L.)
       cars from the passed in cars essentially defined by the user and programmer. */
    private void populateRacers(Car car, RaceVenue raceVenue) {

        raceVenue.getAllCarLapTimes().put(car, 0L);               // add the user.                               Z.L.

        Map<Integer, Car> numberCarMap = new HashMap<>();

        numberCarMap.put(1, car);

        Set<Map.Entry<Car, Long>> carLongSet = raceVenue.getAllCarLapTimes().entrySet();

        int i = 2;
        for(Map.Entry<Car, Long> carLongEntry : carLongSet) {

            if(carLongEntry.getKey().equals(car)) {
                continue;
            }

            numberCarMap.put(i++, carLongEntry.getKey());

        }

        Rectangle rectangle;
        final double PREF_WIDTH = 75.0;
        final double PREF_HEIGHT = 20.0;

        // taking care of user first.
        rectangle = new Rectangle();
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(car.getColor());
        rectangle.setWidth(PREF_WIDTH);
        rectangle.setHeight(PREF_HEIGHT);

        cars.add(rectangle);

        // then taking care of bot racers.
        for(int loopVariable = 1; loopVariable < numberCarMap.size(); loopVariable++) {

            rectangle = new Rectangle();
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(Color.MAGENTA);
            rectangle.setWidth(PREF_WIDTH);
            rectangle.setHeight(PREF_HEIGHT);

            cars.add(rectangle);

        }

        String[] data;
        data = raceVenue.getVenueName().split(" ");
        char specialCharacter = data[1].charAt(0);

        switch(specialCharacter) {

            case 'T':
                optionT(cars);
                break;
            case 'S':
                optionS(cars);
                break;
            case 'C':
                optionC(cars);
                break;

        }

        for(Rectangle r : cars) {
            pnMainLayout.getChildren().add(r);
        }

    }

    //                                                                                                         (Z.L.)
    // logical method intended to be used to check if all racers (user incl.) have completely crossed the finish line.
    public boolean allRacersCrossedLine() {
        return fullCount != 1 && allSet || checkRaceState();
    }

    // private helper method intended to set the background of the scene to a specific setting color.          (Z.L.)
    private void setWeatherConditions(String venueName) {

        String[] data;
        data = venueName.split(" ");
        char specialCharacter = data[1].charAt(0);

        switch(specialCharacter) {

            case 'T':
                weather = Color.BLACK;
                break;
            case 'S':
                weather = Color.LIGHTGREY;
                break;
            case 'C':
                weather = Color.LIGHTSALMON;
                break;

        }

        BackgroundFill bgf = new BackgroundFill(weather, CornerRadii.EMPTY, Insets.EMPTY);
        Background bg = new Background(bgf);
        pnMainLayout.setBackground(bg);

    }

    // shows the results of the race when race has completed and the winner is also shown at the bottom.       (Z.L.)
    public void displayResults(Car winningCar, RaceVenue raceVenue) {

        InputStream is;
        Image image;
        ImageView iv;

        try {

            is = new FileInputStream("checkeredflag.jpg");

        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        image = new Image(is);
        iv = new ImageView();
        iv.setImage(image);
        iv.setPreserveRatio(true);

        Scene scene;
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("~ Race Results ~");
        window.getIcons().add(iv.getImage());

        Set<Map.Entry<Car, Long>> carLapTimeEntrySet = raceVenue.getAllCarLapTimes().entrySet();
        VBox layout = new VBox(20);
        Text message = new Text();
        StringBuilder sb = new StringBuilder();
        int overallTime = (int)( ( raceVenue.getEndTime() - raceVenue.getStartTime() ) / 1000 );
        byte minutes = (byte)(overallTime / 60);   // expected to be a small range of minute values for simple game.

        sb.append("\nOverall Time on Track: ");
        sb.append(overallTime);
        sb.append(" second(s) (i.e., ");
        sb.append(minutes);
        sb.append(" minute(s)).\n");
        sb.append("  Racers & Scores\n+----------------------------------+\n");
        for(Map.Entry<Car, Long> entry : carLapTimeEntrySet) {
            sb.append("Driver: ");
            sb.append(entry.getKey().getNameOfDriver());
            sb.append("\tTime: ");
            sb.append(entry.getValue());
            sb.append("  seconds");
            sb.append("\n-------------\n");
        }

        sb.append("WINNER: ");
        sb.append(winningCar.getNameOfDriver());
        sb.append("\t Time: ");
        sb.append(raceVenue.getAllCarLapTimes().get(winningCar));
        sb.append("  seconds");
        sb.append("\n-------------\n");

        message.setText(sb.toString());
        message.setTextAlignment(TextAlignment.CENTER);
        message.setLineSpacing(14.0);
        message.setFont(Font.font("Times New Roman", 14.0));
        message.setFill(Color.BLACK);
        message.setStroke(Color.LIGHTGREEN);

        layout.getChildren().add(message);
        layout.setAlignment(Pos.CENTER);

        BackgroundFill bgf = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        Background bg = new Background(bgf);

        layout.setBackground(bg);

        scene = new Scene(layout, 500, 550);

        window.setScene(scene);
        window.setResizable(false);
        window.show();

    }

    /**                                                                                                 (E.T. & Z.L.)
     * represents a sequence of characters describing meaningful content (i.e., attributes).
     *
     * @return a concatenation of meaningful characters describing things like attributes.
     */
    @Override
    public String toString() {
        return "~ Racing Scene ~";
    }

    /**                                                                                                 (E.T. & Z.L.)
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

        RacingScene racingSceneObj = (RacingScene) o;

        return verify(cars, racingSceneObj.cars) && verify(boundaries, racingSceneObj.boundaries);

    }

    // generic private helper method used to check if two lists of type T have the same content within.        (Z.L.)
    private <T> boolean verify(List<T> arbListOne, List<T> arbListTwo) {

        if(arbListOne.size() != arbListTwo.size()) {
            return false;
        }

        int size = 0;

        for(int i = 0; i < arbListOne.size(); i++) {

            if(arbListOne.get(i) == arbListTwo.get(i)) {
                size++;
            }

        }

        return size == arbListOne.size();

    }

    // private helper method for creating boundary graphical components.                                       (Z.L.)
    private void optionT() {

        Line line;

        line = boundaries.get(0);
        line.setFill(Color.YELLOW);
        line.setStroke(Color.GOLD);
        line.setStartX(100.0);
        line.setStartY(100.0);
        line.setEndX(100.0);
        line.setEndY(250.0);

        line = boundaries.get(1);
        line.setFill(Color.DARKORANGE);
        line.setStroke(Color.DARKRED);
        line.setStartX(850.0);
        line.setStartY(100.0);
        line.setEndX(850.0);
        line.setEndY(250.0);

        line = boundaries.get(2);
        line.setFill(Color.GHOSTWHITE);
        line.setStroke(Color.GHOSTWHITE);
        line.setStartX(100.0);
        line.setStartY(100.0);
        line.setEndX(850.0);
        line.setEndY(100.0);

        line = boundaries.get(3);
        line.setFill(Color.GHOSTWHITE);
        line.setStroke(Color.GHOSTWHITE);
        line.setStartX(100.0);
        line.setStartY(250.0);
        line.setEndX(850.0);
        line.setEndY(250.0);

        line = boundaries.get(4);
        line.setFill(Color.YELLOW);
        line.setStroke(Color.GOLD);
        line.setStartX(100.0);
        line.setStartY(300.0);
        line.setEndX(100.0);
        line.setEndY(450.0);

        line = boundaries.get(5);
        line.setFill(Color.DARKORANGE);
        line.setStroke(Color.DARKRED);
        line.setStartX(850.0);
        line.setStartY(300.0);
        line.setEndX(850.0);
        line.setEndY(450.0);

        line = boundaries.get(6);
        line.setFill(Color.GHOSTWHITE);
        line.setStroke(Color.GHOSTWHITE);
        line.setStartX(100.0);
        line.setStartY(300.0);
        line.setEndX(850.0);
        line.setEndY(300.0);

        line = boundaries.get(7);
        line.setFill(Color.GHOSTWHITE);
        line.setStroke(Color.GHOSTWHITE);
        line.setStartX(100.0);
        line.setStartY(450.0);
        line.setEndX(850.0);
        line.setEndY(450.0);

    }

    // private helper method for creating boundary graphical components (1st track).                           (Z.L.)
    private void optionS() {

        Line line;

        line = boundaries.get(0);
        line.setFill(Color.YELLOW);
        line.setStroke(Color.GOLD);
        line.setStartX(850.0);
        line.setStartY(100.0);
        line.setEndX(850.0);
        line.setEndY(250.0);

        line = boundaries.get(1);
        line.setFill(Color.DARKORANGE);
        line.setStroke(Color.DARKRED);
        line.setStartX(100.0);
        line.setStartY(100.0);
        line.setEndX(100.0);
        line.setEndY(250.0);

        line = boundaries.get(2);
        line.setFill(Color.GHOSTWHITE);
        line.setStroke(Color.GHOSTWHITE);
        line.setStartX(100.0);
        line.setStartY(100.0);
        line.setEndX(850.0);
        line.setEndY(100.0);

        line = boundaries.get(3);
        line.setFill(Color.GHOSTWHITE);
        line.setStroke(Color.GHOSTWHITE);
        line.setStartX(100.0);
        line.setStartY(250.0);
        line.setEndX(850.0);
        line.setEndY(250.0);

        line = boundaries.get(4);
        line.setFill(Color.YELLOW);
        line.setStroke(Color.GOLD);
        line.setStartX(850.0);
        line.setStartY(300.0);
        line.setEndX(850.0);
        line.setEndY(450.0);

        line = boundaries.get(5);
        line.setFill(Color.DARKORANGE);
        line.setStroke(Color.DARKRED);
        line.setStartX(100.0);
        line.setStartY(300.0);
        line.setEndX(100.0);
        line.setEndY(450.0);

        line = boundaries.get(6);
        line.setFill(Color.GHOSTWHITE);
        line.setStroke(Color.GHOSTWHITE);
        line.setStartX(100.0);
        line.setStartY(300.0);
        line.setEndX(850.0);
        line.setEndY(300.0);

        line = boundaries.get(7);
        line.setFill(Color.GHOSTWHITE);
        line.setStroke(Color.GHOSTWHITE);
        line.setStartX(100.0);
        line.setStartY(450.0);
        line.setEndX(850.0);
        line.setEndY(450.0);

    }

    // private helper method for creating boundary graphical components (2nd track).                           (Z.L.)
    private void optionC() {

        Line line;

        line = boundaries.get(0);
        line.setFill(Color.YELLOW);
        line.setStroke(Color.GOLD);
        line.setStartX(100.0);
        line.setStartY(125.0);
        line.setEndX(250.0);
        line.setEndY(125.0);

        line = boundaries.get(1);
        line.setFill(Color.DARKORANGE);
        line.setStroke(Color.DARKRED);
        line.setStartX(100.0);
        line.setStartY(480.0);
        line.setEndX(250.0);
        line.setEndY(480.0);

        line = boundaries.get(2);
        line.setFill(Color.GHOSTWHITE);
        line.setStroke(Color.GHOSTWHITE);
        line.setStartX(100.0);
        line.setStartY(125.0);
        line.setEndX(100.0);
        line.setEndY(480.0);

        line = boundaries.get(3);
        line.setFill(Color.GHOSTWHITE);
        line.setStroke(Color.GHOSTWHITE);
        line.setStartX(250.0);
        line.setStartY(125.0);
        line.setEndX(250.0);
        line.setEndY(480.0);

        line = boundaries.get(4);
        line.setFill(Color.YELLOW);
        line.setStroke(Color.GOLD);
        line.setStartX(500.0);
        line.setStartY(125.0);
        line.setEndX(650.0);
        line.setEndY(125.0);

        line = boundaries.get(5);
        line.setFill(Color.DARKORANGE);
        line.setStroke(Color.DARKRED);
        line.setStartX(500.0);
        line.setStartY(480.0);
        line.setEndX(650.0);
        line.setEndY(480.0);

        line = boundaries.get(6);
        line.setFill(Color.GHOSTWHITE);
        line.setStroke(Color.GHOSTWHITE);
        line.setStartX(500.0);
        line.setStartY(125.0);
        line.setEndX(500.0);
        line.setEndY(480.0);

        line = boundaries.get(7);
        line.setFill(Color.GHOSTWHITE);
        line.setStroke(Color.GHOSTWHITE);
        line.setStartX(650.0);
        line.setStartY(125.0);
        line.setEndX(650.0);
        line.setEndY(480.0);

    }

    // private helper method for creating cars for option "TropicalIslandVenue."                               (Z.L.)
    private void optionT(List<Rectangle> cars) {

        Rectangle rectangle;

        rectangle = cars.get(0);
        rectangle.setX(25.0);
        rectangle.setY(112.0);

        rectangle = cars.get(1);
        rectangle.setX(25.0);
        rectangle.setY(165.0);

        rectangle = cars.get(2);
        rectangle.setX(25.0);
        rectangle.setY(220.0);

        rectangle = cars.get(3);
        rectangle.setX(25.0);
        rectangle.setY(310.0);

        rectangle = cars.get(4);
        rectangle.setX(25.0);
        rectangle.setY(365.0);

        rectangle = cars.get(5);
        rectangle.setX(25.0);
        rectangle.setY(420.0);

    }

    // private helper method for creating cars for option "MainStripVenue."                                    (Z.L.)
    private void optionS(List<Rectangle> cars) {

        Rectangle rectangle;

        rectangle = cars.get(0);
        rectangle.setX(850.0);
        rectangle.setY(112.0);

        rectangle = cars.get(1);
        rectangle.setX(850.0);
        rectangle.setY(165.0);

        rectangle = cars.get(2);
        rectangle.setX(850.0);
        rectangle.setY(220.0);

        rectangle = cars.get(3);
        rectangle.setX(850.0);
        rectangle.setY(310.0);

        rectangle = cars.get(4);
        rectangle.setX(850.0);
        rectangle.setY(365.0);

        rectangle = cars.get(5);
        rectangle.setX(850.0);
        rectangle.setY(420.0);

    }

    // private helper method for creating cars for option "PistonCupVenue."                                    (Z.L.)
    private void optionC(List<Rectangle> cars) {

        Rectangle rectangle;

        rectangle = cars.get(0);
        rectangle.setRotate(90.0);
        rectangle.setX(85.0);
        rectangle.setY(76.0);

        rectangle = cars.get(1);
        rectangle.setRotate(90.0);
        rectangle.setX(135.0);
        rectangle.setY(76.0);

        rectangle = cars.get(2);
        rectangle.setRotate(90.0);
        rectangle.setX(185.0);
        rectangle.setY(76.0);

        rectangle = cars.get(3);
        rectangle.setRotate(90.0);
        rectangle.setX(485.0);
        rectangle.setY(76.0);

        rectangle = cars.get(4);
        rectangle.setRotate(90.0);
        rectangle.setX(535.0);
        rectangle.setY(76.0);

        rectangle = cars.get(5);
        rectangle.setRotate(90.0);
        rectangle.setX(585.0);
        rectangle.setY(76.0);

    }

    // private helper method for dealing with movement within option "TropicalIslandVenue."                    (Z.L.)
    private void optionT( KeyEvent keyEvent, Rectangle rectangle,
                              double currentVelocityInXDirection,
                              double currentVelocityInYDirection )
    {

        String keyCode = keyEvent.getCode().getName();

        if(keyCode.compareToIgnoreCase("UP") == 0) {

            if(currentVelocityInXDirection >= 775.0) {

                while(!allSet) {
                    fullCount++;
                    currentVenueObject.getAllCarLapTimes().put(currentUser,
                                       (System.currentTimeMillis() - currentVenueObject.getStartTime()) / 1000);
                    allSet = true;
                }

                return;

            }

            rectangle.setX(currentVelocityInXDirection + velX);

        } else if(keyCode.compareToIgnoreCase("DOWN") == 0) {

            if(currentVelocityInXDirection <= 0.0) {
                return;
            }

            rectangle.setX(currentVelocityInXDirection - velX);

        } else if(keyCode.compareToIgnoreCase("LEFT") == 0) {

            if(currentVelocityInYDirection == 100.0) {
                return;
            }

            rectangle.setY(currentVelocityInYDirection - velY);

        } else if(keyCode.compareToIgnoreCase("RIGHT") == 0) {

            if(currentVelocityInYDirection >= 230.0) {
                return;
            }

            rectangle.setY(currentVelocityInYDirection + velY);

        }

    }

    // private helper method for dealing with movement within option "MainStripVenue."                         (Z.L.)
    private void optionS( KeyEvent keyEvent, Rectangle rectangle,
                              double currentVelocityInXDirection,
                              double currentVelocityInYDirection )
    {

        String keyCode = keyEvent.getCode().getName();

        if(keyCode.compareToIgnoreCase("UP") == 0) {

            if(currentVelocityInXDirection <= 100.0) {

                while(!allSet) {
                    fullCount++;
                    currentVenueObject.getAllCarLapTimes().put(currentUser,
                                       (System.currentTimeMillis() - currentVenueObject.getStartTime()) / 1000);
                    allSet = true;
                }

                return;

            }

            rectangle.setX(currentVelocityInXDirection - velX);

        } else if(keyCode.compareToIgnoreCase("DOWN") == 0) {

            if(currentVelocityInXDirection >= 923.0) {
                return;
            }

            rectangle.setX(currentVelocityInXDirection + velX);

        } else if(keyCode.compareToIgnoreCase("LEFT") == 0) {

            if(currentVelocityInYDirection >= 229.0) {
                return;
            }

            rectangle.setY(currentVelocityInYDirection + velY);

        } else if(keyCode.compareToIgnoreCase("RIGHT") == 0) {

            if(currentVelocityInYDirection <= 100.0) {
                return;
            }

            rectangle.setY(currentVelocityInYDirection - velY);

        }

    }

    // private helper method for dealing with movement within option "PistonCupVenue."                         (Z.L.)
    private void optionC( KeyEvent keyEvent, Rectangle rectangle,
                              double currentVelocityInXDirection,
                              double currentVelocityInYDirection )
    {

        String keyCode = keyEvent.getCode().getName();

        if(keyCode.compareToIgnoreCase("UP") == 0) {

            if(currentVelocityInYDirection >= 431.0) {

                while(!allSet) {
                    fullCount++;
                    currentVenueObject.getAllCarLapTimes().put(currentUser,
                                       (System.currentTimeMillis() - currentVenueObject.getStartTime()) / 1000);
                    allSet = true;
                }

                return;

            }

            rectangle.setY(currentVelocityInYDirection + velY);

        } else if(keyCode.compareToIgnoreCase("DOWN") == 0) {

            if(currentVelocityInYDirection <= 29.0) {
                return;
            }

            rectangle.setY(currentVelocityInYDirection - velY);

        } else if(keyCode.compareToIgnoreCase("LEFT") == 0) {

            if(currentVelocityInXDirection >= 202.0) {
                return;
            }

            rectangle.setX(currentVelocityInXDirection + velX);

        } else if(keyCode.compareToIgnoreCase("RIGHT") == 0) {

            if(currentVelocityInXDirection <= 73.0) {
                return;
            }

            rectangle.setX(currentVelocityInXDirection - velX);

        }

    }

    // private helper method for dealing with simulating computer movement throughout a given race venue.      (Z.L.)
    private void simulateComps(char specialCharacter) {

        final double VELOCITY_X = 1.8;

        // there are three choices to deal with because there are three different race venue choices to choose from.
        switch(specialCharacter) {

            case 'T':

                int index = 0;

                for (Rectangle car : cars) {

                    if (  car.getFill() == Color.RED
                       || car.getFill() == Color.GREEN
                       || car.getFill() == Color.BLUE  )
                    {
                        continue;
                    }

                    if(botsAllSet) {
                        return;
                    }

                    if (car.getX() >= 775.0) {

                        for( ; index < cars.size(); ) {

                            currentVenueObject.getAllCarLapTimes().put(currentVenueObject.getBotCars()[index++],
                                     ((System.currentTimeMillis() - currentVenueObject.getStartTime()) / 1000));
                            fullCount++;
                            botsAllSet = true;

                            if(index + 1 == cars.size()) {
                                break;
                            }

                        }

                        break;

                    }

                    car.setX(car.getX() + VELOCITY_X);

                }

                break;

            case 'S':

                int indexS = 0;

                for (Rectangle car : cars) {

                    if (       car.getFill() == Color.RED
                            || car.getFill() == Color.GREEN
                            || car.getFill() == Color.BLUE  )
                    {
                        continue;
                    }

                    if(botsAllSet) {
                        return;
                    }

                    if (car.getX() <= 100.0) {

                        for( ; indexS < cars.size(); ) {

                            currentVenueObject.getAllCarLapTimes().put(currentVenueObject.getBotCars()[indexS++],
                                      ((System.currentTimeMillis() - currentVenueObject.getStartTime()) / 1000));
                            fullCount++;
                            botsAllSet = true;

                            if(indexS + 1 == cars.size()) {
                                break;
                            }

                        }

                        break;

                    }

                    car.setX(car.getX() - VELOCITY_X);

                }

                break;

            case 'C':

                final double VELOCITY_Y = 1.8;
                int indexSP = 0;

                for (Rectangle car : cars) {

                    if (       car.getFill() == Color.RED
                            || car.getFill() == Color.GREEN
                            || car.getFill() == Color.BLUE  )
                    {
                        continue;
                    }

                    if(botsAllSet) {
                        return;
                    }

                    if (car.getY() >= 431.0) {

                        for( ; indexSP < cars.size(); ) {

                            currentVenueObject.getAllCarLapTimes().put(currentVenueObject.getBotCars()[indexSP++],
                                       ((System.currentTimeMillis() - currentVenueObject.getStartTime()) / 1000));
                            fullCount++;
                            botsAllSet = true;

                            if(indexSP + 1 == cars.size()) {
                                break;
                            }

                        }

                        break;

                    }

                    car.setY(car.getY() + VELOCITY_Y);

                }

                break;

        }

    }

}
