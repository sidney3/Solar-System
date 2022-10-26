package Animation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.PointLight;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.io.FileNotFoundException;

public class Cartoon {

    private Label planetInfo;
    private Label planetOrbits;
    private Label planetRotations;
    private Pane mainPane;
    private HBox commandBox;
    private BorderPane root;
    private Timeline planetInfoTimeline;
    private PaneOrganizer paneOrganizer;


    public Cartoon(BorderPane inputRoot, PaneOrganizer inputPaneOrganizer) throws FileNotFoundException {

        this.root = inputRoot;

        //associate Cartoon to the paneOrganizer to allow for the restart of the tutorial button
        this.paneOrganizer = inputPaneOrganizer;

        //Main pane initialized
        //This to contain planets & light source
        this.mainPane = new Pane();

        //Container for command buttons
        //Quit, Display planet info, etc.
        this.setUpCommandBox();

        //Earth orbit facts (this is the updating label!)
        this.setUpInfoDisplay();

        //Planet facts from mousing over planets
        this.addPlanetFacts();

        //The planets
        this.addPlanets(false);

        this.root.setCenter(mainPane);
        this.root.setBottom(commandBox);

    }

    private void addPlanets(boolean isAndy) throws FileNotFoundException {
        /***Call in all of our planets
         Note that the actual creation of the planets is outsourced out to
         method addPlanet. However, the process of creating a planet is
         meaty enough that we have divided it up over these two methods.
         */

        //Map to current directory (inside of which there are paths mapping to the images for planet skins)
        String localDir = System.getProperty("user.dir");

        //See addPlanet method for elaboration on these calls of this method.
        this.addPlanet( //Jupiter
                Constants.JUPITER_FACTS, localDir + Constants.JUPITER_PATH,
                0, Constants.JUPITER_SIZE, false, false, isAndy);
        this.addPlanet( //Earth
                Constants.EARTH_FACTS, localDir + Constants.EARTH_PATH,
                94, Constants.EARTH_SIZE, false, true, isAndy);
        this.addPlanet( //Mars
                Constants.MARS_FACTS, localDir + Constants.MARS_PATH,
                187, Constants.MARS_SIZE, false, false, isAndy);
        this.addPlanet( //Venus
                Constants.VENUS_FACTS, localDir + Constants.VENUS_PATH,
                280, Constants.VENUS_SIZE, false, false, isAndy);
        this.addPlanet( //Neptune
                Constants.NEPTUNE_FACTS, localDir + Constants.NEPTUNE_PATH,
                374, Constants.NEPTUNE_SIZE, false, false, isAndy);
        this.addPlanet( //Uranus
                Constants.URANUS_FACTS, localDir + Constants.URANUS_PATH,
                467, Constants.URANUS_SIZE, false, false, isAndy);
        this.addPlanet( //Saturn
                Constants.SATURN_FACTS, localDir + Constants.SATURN_PATH,
                561, Constants.SATURN_SIZE, false, false, isAndy);
        this.addPlanet( //Mercury
                Constants.MERCURY_FACTS, localDir + Constants.MERCURY_PATH,
                654, Constants.MERCURY_SIZE, false, false, isAndy);
        this.addPlanet( //Sun
                Constants.SUN_FACTS, localDir + Constants.SUN_PATH,
                0, Constants.SUN_SIZE, true, false, isAndy);

        this.addLight();
    }

    private void addLight() {
        /***
         With our 3 dimensional world, we need to add in
         a light to clarify how we want elements illuminated

         */
        PointLight light = new PointLight();
        light.setTranslateX(0);
        light.setTranslateY(300);
        light.setTranslateZ(200);


        this.mainPane.getChildren().add(light);
    }

    private void addPlanetFacts() {
        /*** Establish the text box where fun facts on the planet are displayed
         The job of updating this text box is delegated to the planets themselves,
         an assignment which is done in the addPlanet method.
         */
        //Make constant the size of textBox (used to display planet facts)
        //Though this textBox is never visible, its removal means that mousing
        //Over a planet to get information about it will cause the text
        //to displace the planets
        VBox textBox = new VBox();
        textBox.setMinWidth(Constants.VBOX_WIDTH);
        textBox.setMaxWidth(Constants.VBOX_WIDTH);
        textBox.setMaxHeight(Constants.VBOX_HEIGHT);
        textBox.setMinHeight(Constants.VBOX_HEIGHT);
        textBox.setTranslateY(50);

        this.planetInfo = new Label();
        this.planetInfo.setTextFill(Color.WHITE);
        this.planetInfo.setFont(new Font("Monaco", 10));
        this.planetInfo.setScaleShape(true);
        this.planetInfo.setTranslateX(10);
        //Planet info is a local variable so that it can be updated
        //(textbox has no need to be updated)

        textBox.getChildren().add(planetInfo);
        this.root.setLeft(textBox);

    }

    private void addPlanet(String inputPlanetFacts, String planetTexturePath,
                           int planetPositionIndex, double planetRadius, boolean isSun, boolean isEarth, boolean isAndy) throws FileNotFoundException {
        /***
         The meat of this class. This class serves two jobs. The first
         is useless - passing in the values that were used to call each planet in addPlanets

         However, the below code is the more pressing, this is setting up the planet skin (what makes
         the planets more than just spinning spheres), and tying the planets to their planet facts.

         The "Set on Mouse Entered" and "Set On Mouse Exits" hides and
         shows the passed in "planet_facts." It now becomes clear why we need a constant textBox
         size (in method addPlanetFacts). The contents of this textbox are changing every time
         we mouse over a planet.
         */

        //Rather than just passing in "planetFacts" into Planet, we format it like this
        //So that we can update the planetFacts from this method (in the case of AndyMode)
        String planetFacts = inputPlanetFacts;

        //Easter egg :)
        if (isAndy) {
            planetTexturePath = System.getProperty("user.dir") + Constants.ANDY_PATH;
             planetFacts = Constants.ANDY_FACTS;
        }

        Planet planet = new Planet(planetTexturePath, //See Planets class for use of these parameters
                planetPositionIndex, Constants.PLANET_Y, this.mainPane, planetRadius, isSun);

        String finalPlanetFacts = planetFacts;
        planet.getPlanet().setOnMouseEntered(event -> { //When mousing over planet, display the inputted planet facts
            this.inputPlanetInfo(finalPlanetFacts);
            event.consume();
        });

        //Here, we will also put the info about planet stats

        planet.getPlanet().setOnMouseExited(event -> { //When mouse leaves, hide those facts

            this.inputPlanetInfo("");
            event.consume();
        });

        if(isEarth) {
            //Set up an updating label to show stats about earth's progress in the solar system!

            KeyFrame kf = new KeyFrame(
                    Duration.seconds(.5),

                    (ActionEvent e) -> {
                        //update stats for our favorite planet
                        this.planetOrbits.setText("Earth's trips around the sun: " + planet.getOrbits());
                        this.planetRotations.setText("Earth's Rotations: " + planet.getRotations());
                    });
            this.planetInfoTimeline = new Timeline(kf);
            this.planetInfoTimeline.setCycleCount(Animation.INDEFINITE);
            this.planetInfoTimeline.play();
        }
    }

    private void inputPlanetInfo(String planet_description){
        this.planetInfo.setText(planet_description);
    }

    public void setUpCommandBox(){
        /***
         Sets up the bottom display where input buttons
         are displayed.

         Very standard code here, simply creating buttons
         and assigning events to their press
         */

        this.commandBox = new HBox();

        //quit button set up and tie to system exit
        Button quitButton = new Button();
        quitButton.setText("Quit");
        quitButton.setFocusTraversable(false);
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        quitButton.setTranslateX(5);
        quitButton.setTranslateY(-10);


        //Restart the instructions tab
        //This is why we pass in PaneOrganizer to Cartoon
        //The main method in PaneOrganizer, initializeInstructions,
        //can just be called to restart the tutorial
        Button tutorialButton = new Button();
        tutorialButton.setText("Restart Tutorial");
        tutorialButton.setFocusTraversable(false);
        tutorialButton.setOnAction((ActionEvent e) -> {
            try {
                this.paneOrganizer.initializeInstructions();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        tutorialButton.setTranslateY(-10);
        tutorialButton.setTranslateX(-221);

        //set global pause variable to true (stop program)
        Button pauseButton = new Button();
        pauseButton.setText("Pause");
        pauseButton.setFocusTraversable(false);
        pauseButton.setOnAction((ActionEvent e) -> {
            Universals.paused = true; //a value that each planet knows about and is checking for
            //Therefore, activating it stops the planets movement
        });
        pauseButton.setTranslateX(635);
        pauseButton.setTranslateY(-10);

        //set global pause variable to false (letting program run)
        Button resumeButton = new Button();
        resumeButton.setText("Resume");
        resumeButton.setFocusTraversable(false);
        resumeButton.setOnAction((ActionEvent e) ->
            Universals.paused = false);
        resumeButton.setTranslateX(640);
        resumeButton.setTranslateY(-10);


        //Button to switch between "ANDY MODE" (Easter egg) :)
        Button andyButton = new Button();
        andyButton.setTranslateY(-10);
        andyButton.setTranslateX(400);
        andyButton.setText("Andy Mode [OFF]");
        andyButton.setFocusTraversable(false);
        andyButton.setOnAction((ActionEvent e) -> {
            if(andyButton.getText() == "Andy Mode [OFF]") {
                andyButton.setText("Andy Mode [ON]");
                this.mainPane.getChildren().setAll();
                this.planetInfoTimeline.stop(); //stop timeline of planet system that no longer exists
                //This is why planetInfoTimeline is created as an instance variable
                try {
                    this.addPlanets(true);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

            }
            else {
                andyButton.setText("Andy Mode [OFF]");
                this.planetInfoTimeline.stop(); //stop timeline of planet system that no longer exists
                this.mainPane.getChildren().setAll();
                try {
                    this.addPlanets(false);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });


        this.commandBox.getChildren().addAll(quitButton, pauseButton, resumeButton, andyButton, tutorialButton);

    }
    private void setUpInfoDisplay(){
        //display information about planets
        //rotations of earth around the sun (this is controlled BY earth) - do we really want this?
        //Perhaps mousing over the planet gives some stats about rotations and orbits of the sun
        HBox infoBox = new HBox();

        //Labels tracking orbits of the sun and rotations
        //The assignment of these labels is handled in the createPlanets method
        this.planetOrbits = new Label();
        this.planetOrbits.setText("");
        this.planetOrbits.setTranslateX(450);
        this.planetOrbits.setTranslateY(15);
        this.planetOrbits.setFont(Font.font("Monaco", 15));
        this.planetOrbits.setTextFill(Color.WHITE);

        this.planetRotations = new Label();
        this.planetRotations.setText("");
        this.planetRotations.setTranslateX(-200);
        this.planetRotations.setTranslateY(15);
        this.planetRotations.setFont(Font.font("Monaco", 15));
        this.planetRotations.setTextFill(Color.WHITE);

        infoBox.getChildren().addAll(this.planetOrbits, this.planetRotations);

        this.root.setTop(infoBox);
    }

    public Pane getRoot(){
        return this.root;
    }

}
