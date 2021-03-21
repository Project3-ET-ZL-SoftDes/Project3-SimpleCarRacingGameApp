import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

/*
      Zachary L. & Emolyn T.
      CS 225 Software Design
      3/17/2021
 */

//                                                                                                       (E.T & Z.L.)
// this class represents the main application as well as controller for the whole concept of a desktop racing game.
public class TheBigRaceGuiFx extends Application {

    /**                                                                                                        (Z.L.)
     * The main driver method that gets invoked first after the concrete sub-class
     * goes from basic console user interface app. to javaFx graphical user interface app.
     *
     * @param stage the main window of the entire application. All main scenes will be displayed on this window.
     */
    @Override
    public void start(Stage stage) {

        window = stage;
        window.setTitle("<- The Big Race! ->");
        window.setScene(welcomeScene.scene());
        window.setX(300.0);
        window.setY(100.0);
        setWindowIconImage();
        window.show();

    }

    private static Stage window;                                                // main javaFx gui application window.
    private static WelcomeScene welcomeScene = new WelcomeScene();                          // the introductory scene.
    private static CustomizationScene customizationScene = new CustomizationScene();           // the secondary scene.
    private static RacingScene racingScene = new RacingScene();                                    // the final scene.

    // default constructor.
    public TheBigRaceGuiFx() {
        // do nothing within.
    }

    /**                                                                                                        (Z.L.)
     * private helper method who's sole purpose is to apply the icon image onto the main application window.
     * This will be found in the top left of the main window.
     */
    private void setWindowIconImage() {

        InputStream is;
        Image image;
        ImageView iv;

        try {

            is = new FileInputStream("twocheckeredflags.jpg");

        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        image = new Image(is);
        iv = new ImageView();
        iv.setImage(image);
        iv.setPreserveRatio(true);

        window.getIcons().add(iv.getImage());

    }

    /**                                                                                                  (E.T & Z.L.)
     * the consumption method primarily handles signals to determine when to switch scenes, when to apply specific
     * data to specific methods, when to turn timers on and off as well as exiting the application, etc.
     *
     * @param event the reference to an arbitrarily fired event in which this reference will be referring to.
     */
    public static void consume(Event event) {

        // sequential flow.
        if(event.getSource() == welcomeScene.btnQuickRace()) {

            window.setScene(customizationScene.scene());
            window.setTitle("<- Customize Your Vehicle! ->");

        } else if(event.getSource() == customizationScene.btnOk()) {

            window.setScene(racingScene.scene(customizationScene.getUser(), customizationScene.getRaceTrack()));
            window.setTitle("<- Race Event ->");

            Timer timer = new Timer();
            final int DELAY = 120000;
            final int EXIT_STATUS = 0;
            final int TIME_TO_CHECK_RESULTS = 15000;

            TimerTask timerTask = new TimerTask() {

                @Override
                public void run() {
                    Platform.runLater(() -> {                  // gets put in the fx application event queue.  (Z.L.)
                        customizationScene.getRaceTrack().setEndTime(System.currentTimeMillis());
                        racingScene.setFinishedRaceState(true);
                        racingScene.displayResults( customizationScene.getRaceTrack().getWinner(),
                                                    customizationScene.getRaceTrack() );
                    });
                }

            };

            timer.schedule(timerTask, DELAY);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.exit(EXIT_STATUS);
                }
            }, DELAY + TIME_TO_CHECK_RESULTS);

            final int DELAY_TWO = 1000;
            Timer timer2 = new Timer();
            TimerTask timerTask2 = new TimerTask() {

                final int DELAY = 15000;
                final int EXIT_STATUS = 0;

                @Override
                public void run() {

                    if(racingScene.allRacersCrossedLine()) {

                        Platform.runLater(() -> {              // gets put in the fx application event queue.  (Z.L.)
                            customizationScene.getRaceTrack().setEndTime(System.currentTimeMillis());
                            racingScene.setFinishedRaceState(true);
                            racingScene.displayResults( customizationScene.getRaceTrack().getWinner(),
                                                        customizationScene.getRaceTrack() );
                        });

                        Timer timer = new Timer();
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                System.exit(EXIT_STATUS);
                            }
                        };

                        timer.schedule(timerTask, DELAY);
                        timer2.cancel();

                    }

                }

            };

            timer2.schedule(timerTask2, DELAY_TWO, DELAY_TWO);

        }

    }

    /**                                                                                                 (E.T. & Z.L.)
     * represents a sequence of characters describing meaningful content (i.e., attributes).
     *
     * @return a concatenation of meaningful characters describing things like attributes.
     */
    @Override
    public String toString() {
        return "~ TheBigRaceGuiFx ~";
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

        // assert both objects are not equal to one another.
        return false;

    }

    /**                                                                                                 (E.T. & Z.L.)
     *
     * the main driver method that will be executed by the JVM once the "TheBigRaceGuiFx.java" class is loaded.
     * the JVM invokes an executable so this is when "TheBigRaceGuiFx.main(String[] arguments);" will be called.
     * From there, the rest...is defined all around the remaining source code files in conjunction with this
     * project.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
