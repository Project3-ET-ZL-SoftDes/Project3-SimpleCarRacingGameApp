import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/*
      Zachary L. & Emolyn T.
      CS 225 Software Design
      3/17/2021
 */

// a representation of a scene that will act as a central home for the user to make all INITIAL interactions with.
public class WelcomeScene implements EventHandler<ActionEvent> {

    private Button btnQuickRace;                             // a button to allow the user to play a quick race.
    private Pane pane;                                       // a central layout manager for orienting gui components.

    //                                                                                                         (Z.L.)
    // the default constructor for the essential creation of a scene that will "welcome" the user to anything on it.
    public WelcomeScene() {

        pane = new Pane();
        Text text = new Text("Welcome to The Big Racing Event!");

        text.setFill(Color.LIGHTSTEELBLUE);
        text.setStroke(Color.DARKRED);
        text.setFont(Font.font("Times New Roman", 22.0));

        BackgroundFill bgf = new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY);
        Background bg = new Background(bgf);

        btnQuickRace = new Button("Quick Race");
        btnQuickRace.setOnAction(this);
        btnQuickRace.setBackground(bg);
        btnQuickRace.setTextFill(Color.ORANGE);
        btnQuickRace.setFont(Font.font("Arial Black"));
        btnQuickRace.setPadding(new Insets(10.0));
        btnQuickRace.setOnMouseEntered(e -> btnQuickRace.setTextFill(Color.WHITE));
        btnQuickRace.setOnMouseExited(e -> btnQuickRace.setTextFill(Color.ORANGE));

        pane.getChildren().addAll(text, btnQuickRace);

        bgf = new BackgroundFill(Color.LIMEGREEN, CornerRadii.EMPTY, Insets.EMPTY);
        bg = new Background(bgf);

        pane.setBackground(bg);

        pane.getChildren().get(0).setLayoutX(100.0);
        pane.getChildren().get(0).setLayoutY(150.0);

        pane.getChildren().get(1).setLayoutX(200.0);
        pane.getChildren().get(1).setLayoutY(200.0);

    }

    //                                                                                                   (E.T & Z.L.)
    // creates and returns a brand new welcome scene object with the programmer-defined constraints given to it.
    public Scene scene() {
        return new Scene(pane, 500,500);
    }

    //                                                                                                         (E.T.)
    // accessor method for retrieving and returning the reference to the button gui component within the scene object.
    public Button btnQuickRace() {
        return btnQuickRace;
    }

    /**                                                                                                 (E.T. & Z.L.)
     * represents a sequence of characters describing meaningful content (i.e., attributes).
     *
     * @return a concatenation of meaningful characters describing things like attributes.
     */
    @Override
    public String toString() {
        return "~ WelcomeScene ~";
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

        WelcomeScene welcomeSceneObj = (WelcomeScene) o;

        return btnQuickRace() == welcomeSceneObj.btnQuickRace()
            &&           pane == welcomeSceneObj.pane;

    }

    /**                                                                                                        (Z.L.)
     * the main handler responsible for handling all action events related to the welcome scene's components.
     *
     * @param actionEvent a reference to the fired event from an arbitrary node within the customization scene.
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        TheBigRaceGuiFx.consume(actionEvent);            // event consumption.
    }

}
