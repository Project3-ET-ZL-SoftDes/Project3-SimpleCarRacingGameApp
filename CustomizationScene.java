import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

 /*
      Zachary L. & Emolyn T.
      CS 225 Software Design
      3/17/2021
 */

/**
 * This class is a representation of a programmer-created customization scene that was intended
 * for being part of a desktop application designed to allow a user to customize variant components
 * that make up an object of type Car as well as an object of type RaceVenue. These two objects are
 * the essential piece to a running simulation of racing amongst a user and bot (computer) racers.
 */
public class CustomizationScene implements EventHandler<ActionEvent> {

    private Car user;                                            // an arbitrary user playing the racing game.
    private RaceVenue raceVenue;                                 // an arbitrary race venue selected by the user.
    private BorderPane mainLayout;                               // the MAIN layout manager for all gui components.
    private ArrayList<ChoiceBox<String>> selectionNodes;         // a bunch of pull-down menus for selection.
    private Button btnOk;                                        // a confirmation button for finished customization.
    private ArrayList<Text> informationNodes;                    // a bunch of text for directing the user.

    /**
     * The following attributes are initialized for the construction of a customization scene.                 (E.T.)
     */
    public CustomizationScene() {

        BackgroundFill bgf = new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY);
        Background bg = new Background(bgf);

        user = null;
        raceVenue = null;

        mainLayout = new BorderPane();

        selectionNodes = new ArrayList<>();
        informationNodes = new ArrayList<>();

        btnOk = new Button("OK");
        btnOk.setOnAction(this);
        btnOk.setOnMouseEntered(e -> btnOk.setTextFill(Color.DARKMAGENTA));
        btnOk.setOnMouseExited(e -> btnOk.setTextFill(Color.FLORALWHITE));
        btnOk.setBackground(bg);
        btnOk.setTextFill(Color.FLORALWHITE);
        btnOk.setFont(Font.font("Times New Roman"));

        buildUIForCustomization();

    }

    /**                                                                                                        (E.T.)
     * accessor method for retrieving and returning a reference to the object of type Car, i.e., the user's car.
     *
     * @return the car that will be represented by the user-defined
     *         preferences offered from within the customization scene.
     */
    public Car getUser() {
        return user;
    }

    /**                                                                                                        (E.T.)
     * accessor method for retrieving and returning a reference
     * to the object of type RaceVenue, i.e., the user-selected race venue.
     *
     * @return the race venue that will be used for the user and bot racers to race on.
     */
    public RaceVenue getRaceTrack() {
        return raceVenue;
    }

    /**
     * private helper method for setting up the pull-down menus within the customization scene.                (Z.L.)
     */
    private void getChoicesReady() {

        final int ACTUAL_COUNT = 8;

        for(int i = 0; i < ACTUAL_COUNT; i++) {
            selectionNodes.add(new ChoiceBox<>());
        }

        ChoiceBox<String> choiceBox = selectionNodes.get(0);
        choiceBox.getItems().addAll("Automatic Transmission", "Manual Transmission");
        choiceBox.setValue(choiceBox.getItems().get(0));

        choiceBox = selectionNodes.get(1);
        choiceBox.getItems().addAll("50 cc", "100 cc", "150 cc");
        choiceBox.setValue(choiceBox.getItems().get(0));

        choiceBox = selectionNodes.get(2);
        choiceBox.getItems().addAll("Blaze Fielding", "Axel Stone", "Adam Hunter", "Skate Crusher");
        choiceBox.setValue(choiceBox.getItems().get(0));

        choiceBox = selectionNodes.get(3);
        choiceBox.getItems().addAll("Standard", "Hyper", "Luxury");
        choiceBox.setValue(choiceBox.getItems().get(0));

        choiceBox = selectionNodes.get(4);
        choiceBox.getItems().addAll("2", "3", "4");
        choiceBox.setValue(choiceBox.getItems().get(0));

        choiceBox = selectionNodes.get(5);
        choiceBox.getItems().addAll("Red", "Green", "Blue");
        choiceBox.setValue(choiceBox.getItems().get(0));

        choiceBox = selectionNodes.get(6);
        choiceBox.getItems().addAll("5.0", "7.50", "10.0");
        choiceBox.setValue(choiceBox.getItems().get(0));

        choiceBox = selectionNodes.get(7);
        choiceBox.getItems().addAll("11.0", "12.0", "14.0");
        choiceBox.setValue(choiceBox.getItems().get(0));

    }

    /**                                                                                                        (Z.L.)
     * private helper method for building the front end user interface so the user will be able to interact with
     * the source code via the use of user-friendly graphical components (e.g., buttons, text boxes, menus, etc.).
     */
    private void buildUIForCustomization() {

        /* creating a simple separator to denote the distinction
           between the attributes to choose from pertaining to a car and race venue. */

        Line separatorLine = new Line();
        separatorLine.setFill(Color.BLACK);
        separatorLine.setStroke(Color.GREENYELLOW);
        separatorLine.setStartX(355.0);
        separatorLine.setStartY(40.0);
        separatorLine.setEndX(355.0);
        separatorLine.setEndY(440.0);

        // setting up the pull-down menus.
        getChoicesReady();

        AnchorPane anchorPaneLayout = new AnchorPane();

        informationNodes.add(new Text("Car"));
        informationNodes.add(new Text("Transmission"));
        informationNodes.add(new Text("Engine"));
        informationNodes.add(new Text("Driver"));
        informationNodes.add(new Text("Wheel Type"));
        informationNodes.add(new Text("No. of tires"));
        informationNodes.add(new Text("Color"));
        informationNodes.add(new Text("Speed"));
        informationNodes.add(new Text("Power"));

        for(Text text : informationNodes) {

            if(text.getText().compareTo("Car") == 0) {
                text.setFont(Font.font("Broadway", 25.0));
                text.setFill(Color.GOLD);
                continue;
            }

            text.setFont(Font.font("Times New Roman", 15.0));
            text.setFill(Color.BLACK);

        }

        VBox vBoxTextLayout = new VBox(27.0);
        VBox vBoxChoiceBoxLayout = new VBox(19.0);
        VBox vBoxTextLayoutTwo = new VBox(27.0);
        VBox vBoxChoiceBoxLayoutTwo = new VBox(19.0);

        for(Text text : informationNodes) {
            vBoxTextLayout.getChildren().add(text);
        }

        for(ChoiceBox<String> choiceBox : selectionNodes) {
            vBoxChoiceBoxLayout.getChildren().add(choiceBox);
        }

        AnchorPane.setLeftAnchor(vBoxTextLayout, 50.0);
        AnchorPane.setTopAnchor(vBoxTextLayout, 50.0);

        AnchorPane.setLeftAnchor(vBoxChoiceBoxLayout, 150.0);
        AnchorPane.setTopAnchor(vBoxChoiceBoxLayout, 95.0);

        informationNodes.add(new Text("Race Venue"));
        informationNodes.add(new Text("Track Size"));
        informationNodes.add(new Text("Venue Name"));
        informationNodes.add(new Text("Weather Value"));

        for(int i = 9; i < informationNodes.size(); i++) {

            if(informationNodes.get(i).getText().compareTo("Race Venue") == 0) {
                informationNodes.get(i).setFont(Font.font("Broadway", 25.0));
                informationNodes.get(i).setFill(Color.GOLD);
                continue;
            }

            informationNodes.get(i).setFont(Font.font("Times New Roman", 15.0));
            informationNodes.get(i).setFill(Color.BLACK);

        }

        final int ACTUAL_COUNT = 3;

        for(int i = 0; i < ACTUAL_COUNT; i++) {
            selectionNodes.add(new ChoiceBox<>());
        }

        ChoiceBox<String> choiceBox = selectionNodes.get(8);
        choiceBox.getItems().addAll("2.5", "3.5", "4.5");
        choiceBox.setValue(choiceBox.getItems().get(0));

        choiceBox = selectionNodes.get(9);
        choiceBox.getItems().addAll("Tropical Island Venue", "Main Strip Venue", "Piston Cup Venue");
        choiceBox.setValue(choiceBox.getItems().get(0));

        choiceBox = selectionNodes.get(10);
        choiceBox.getItems().addAll("5", "7", "9");
        choiceBox.setValue(choiceBox.getItems().get(0));

        for(int i = 9; i < informationNodes.size(); i++) {
            vBoxTextLayoutTwo.getChildren().add(informationNodes.get(i));
        }

        for(int i = 8; i < selectionNodes.size(); i++) {
            vBoxChoiceBoxLayoutTwo.getChildren().add(selectionNodes.get(i));
        }

        AnchorPane.setLeftAnchor(vBoxTextLayoutTwo, 415.0);
        AnchorPane.setTopAnchor(vBoxTextLayoutTwo, 50.0);

        AnchorPane.setLeftAnchor(vBoxChoiceBoxLayoutTwo, 525.0);
        AnchorPane.setTopAnchor(vBoxChoiceBoxLayoutTwo, 95.0);

        anchorPaneLayout.getChildren().addAll(vBoxTextLayout, vBoxChoiceBoxLayout, separatorLine,
                                              vBoxTextLayoutTwo, vBoxChoiceBoxLayoutTwo);

        HBox hBoxLayout = new HBox();
        hBoxLayout.getChildren().add(btnOk());
        hBoxLayout.setAlignment(Pos.CENTER);
        hBoxLayout.setPadding(new Insets(10));

        BackgroundFill bgf = new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY);
        Background bg = new Background(bgf);
        mainLayout.setBackground(bg);

        bgf = new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY);
        bg = new Background(bgf);
        hBoxLayout.setBackground(bg);

        mainLayout.setBottom(hBoxLayout);
        mainLayout.setCenter(anchorPaneLayout);

        btnOk().setPrefSize(250.0, 25.0);

    }

    /**                                                                                                 (E.T. & Z.L.)
     * responsible for returning a scene object that contains
     * all the graphical components for the user to interact with
     * along with making sure the components are constrained to
     * the constraints set forth by the variant layout (or "object orientation") managers.
     *
     * @return the customization scene in its entirety.
     */
    public Scene scene() {
        return new Scene(mainLayout, 700, 500);
    }

    /**
     * the main handler responsible for handling all action events related to the customization scene's components.
     *
     * @param actionEvent a reference to the fired event from an arbitrary node within the customization scene.
     */
    @Override
    public void handle(ActionEvent actionEvent) {

        // extraction first.
        String[] items = new String[getSelectionNodes().size()];
        String item;
        int index = 0;

        for(ChoiceBox<String> choiceBoxChoices : getSelectionNodes()) {

            item = choiceBoxChoices.getValue();
            items[index++] = item;

        }

        // content creation for user and their car.
        Transmission transmission = (items[0].compareTo("Automatic Transmission") == 0) ?
                                     new AutomaticTransmission() : new ManualTransmission();
        Engine engine = null;
        String user = null;
        String wheelType = null;
        int numberOfTires = 0;
        Color color = null;
        double speed = 0.0;
        double power = 0.0;

        // content creation for user and their selected race venue.
        double trackSizeInMiles = 0.0;
        String venueName = null;
        int weatherValue = 0;

        for(int i = 1; i < items.length; i++) {

            switch(items[i]) {

                case "50 cc":
                    engine = new FiftyCCEngine();
                    break;
                case "100 cc":
                    engine = new OneHundredCCEngine();
                    break;
                case "150 cc":
                    engine = new OneHundredFiftyCCEngine();
                    break;
                case "Blaze Fielding":
                    user = "Blaze Fielding";
                    break;
                case "Axel Stone":
                    user = "Axel Stone";
                    break;
                case "Adam Hunter":
                    user = "Adam Hunter";
                    break;
                case "Skate Crusher":
                    user = "Skate Crusher";
                    break;
                case "Standard":
                    wheelType = "Standard";
                    break;
                case "Hyper":
                    wheelType = "Hyper";
                    break;
                case "Luxury":
                    wheelType = "Luxury";
                    break;
                case "2":
                    numberOfTires = 2;
                    break;
                case "3":
                    numberOfTires = 3;
                    break;
                case "4":
                    numberOfTires = 4;
                    break;
                case "Red":
                    color = Color.RED;
                    break;
                case "Green":
                    color = Color.GREEN;
                    break;
                case "Blue":
                    color = Color.BLUE;
                    break;
                case "5.0":
                    speed = 5.0;
                    break;
                case "7.50":
                    speed = 7.50;
                    break;
                case "10.0":
                    speed = 10.0;
                    break;
                case "11.0":
                    power = 11.0;
                    break;
                case "12.0":
                    power = 12.0;
                    break;
                case "14.0":
                    power = 14.0;
                    break;
                case "2.5":
                    trackSizeInMiles = 2.5;
                    break;
                case "3.5":
                    trackSizeInMiles = 3.5;
                    break;
                case "4.5":
                    trackSizeInMiles = 4.5;
                    break;
                case "5":
                    weatherValue = 5;
                    break;
                case "7":
                    weatherValue = 7;
                    break;
                case "9":
                    weatherValue = 9;
                    break;
                case "Tropical Island Venue":
                    venueName = "The Tropics";
                    break;
                case "Main Strip Venue":
                    venueName = "The Strip";
                    break;
                case "Piston Cup Venue":
                    venueName = "Championship Cup";
                    break;

            }

        }

        // a user is then created.
        this.user = new Car(numberOfTires, speed, power, wheelType, user, color, engine, transmission);

        // a race venue is then created.
        if(venueName != null) {

            switch(venueName) {

                case "The Tropics":
                    raceVenue = new TropicalIslandVenue(venueName, trackSizeInMiles, weatherValue);
                    break;
                case "The Strip":
                    raceVenue = new MainStripVenue(venueName, trackSizeInMiles, weatherValue);
                    break;
                case "Championship Cup":
                    raceVenue = new PistonCupVenue(venueName, trackSizeInMiles, weatherValue);
                    break;

            }

        }

        // the action event reference is then passed to the "TheBigRaceGuiFx" class for consumption.
        TheBigRaceGuiFx.consume(actionEvent);

    }

    /**                                                                                                        (Z.L.)
     * creates an immutable list object of pull-down menus
     * so whatever mutations are made to this reference will
     * not affect the original reference to the pull-down
     * menus created within the customization scene class definition.
     *
     * @return an immutable list of pull-down menus for temporary use
     *         (i.e., can be used to check but NOT necessarily modify values on the collection).
     */
    public List<ChoiceBox<String>> getSelectionNodes() {
        return Collections.unmodifiableList(selectionNodes);
    }

    /**                                                                                                        (E.T.)
     * accessor method for retrieving and returning the confirmation-related button component.
     *
     * @return the reference to the button component within the customization scene.
     */
    public Button btnOk() {
        return btnOk;
    }

    /**                                                                                                 (E.T. & Z.L.)
     * represents a sequence of characters describing meaningful content (i.e., attributes).
     *
     * @return a concatenation of meaningful characters describing things like attributes.
     */
    @Override
    public String toString() {
        return "~ CustomizationScene ~";
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

        CustomizationScene customizationSceneObj = (CustomizationScene) o;

        return user.equals(customizationSceneObj.user)
            && raceVenue.equals(customizationSceneObj.raceVenue);

    }

}
