package Animation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class PaneOrganizer{
    private BorderPane root;
    private Label introlabel;
    private Astronaut astro;
    private int dialogueNum;
    private int direction;

    public PaneOrganizer() throws FileNotFoundException { //PaneOrganizer creates the introduction sequence the animation

    //FileNotFoundException required for any method calling ImportRawImage (in the case of the file not being found)

        this.root = new BorderPane();
        this.initializeInstructions();

    }

    private void Body() throws FileNotFoundException {
        /*** The body of the program
         initializes class Cartoon - creating the solar system)
         Ran upon completion of the introduction (method initializeInstructions)

         First empties mainPane, and then fills it with
         planets as documented in class Cartoon
         */

        this.root.setCenter(null);
        new Cartoon(this.root, this);


        //setting up background (this exists independently of the objects in this pane, so we do it at a higher level)

        //Pass in image, turn into image of background type, and then turn into actual background
        Image starsImport = importRawImage(System.getProperty("user.dir") + "/src/Images/stars.jpg");
        BackgroundImage stars = new BackgroundImage(starsImport, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(
                        100, 100, true, true, false, true)
        );
        this.root.setBackground(new Background(stars));

    }
    private Image importRawImage(String path) throws FileNotFoundException {
        //returns the image file format from a string input
        return(new Image(new FileInputStream(path)));

    }

    public void initializeInstructions() throws FileNotFoundException {
        /***The body of PaneOrganizer
         Displays an introduction sequence by spawning
         in a character "Astro." This introduction
         sequence is navigated with the space bar, moving
         through each line of the Constant "ASTRONAUT_DIALOGUE,"
         until it has displayed each, after which it runs method
         Body (continuing the program)
         ***/

        //clear out root (allows us to call initializeInstructions
        // from cartoon with button "tutorial")
        this.root.setCenter(null);
        this.root.setTop(null);
        this.root.setBottom(null);
        this.root.setLeft(null);
        this.root.setBackground(null);

        Pane mainPane = new Pane();

        //set background image in same style as in Body() method
        Image topBackgroundImport = importRawImage(System.getProperty("user.dir") + "/src/Images/spacestation.jpeg");
        BackgroundImage topBackground = new BackgroundImage(topBackgroundImport, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        mainPane.setBackground(new Background(topBackground));

        /*Create the "introduction bot"
        This serves no mechanical purpose, just moving up
        and down as if it is giving the written dialogue
         */
        this.astro = new Astronaut(mainPane);

        //Initialize dialogue variable - dialogue is
        // referenced through Constant array ASTRONAUT_DIALOGUE,
        // so this is used to index where we are (beginning on dialogue 0)
        this.dialogueNum = 0;

        //initialize direction (used for creating movement in sprite, starts at 1 and
        //gets continually multiplied by -1 to change direction)
        this.direction = 1;


        //give directions to user
        Label directiveLabel = new Label();
        directiveLabel.setText(">[SPACE] to scroll dialogue\n" +
                ">[S] to skip dialogue");
        directiveLabel.setFont(Font.font("Helvetica", 20));
        //helvetica used throughout intro pane
        directiveLabel.setTranslateX(Constants.MIDDLE_CENTER_PANE);
        directiveLabel.setTranslateY(640);
        directiveLabel.setTextFill(Color.WHITE);

        //Format label used to display astronaut dialogue
        this.introlabel = new Label();
        this.introlabel.setTranslateX(330);
        this.introlabel.setTranslateY(150);
        this.introlabel.setFont(Font.font("Helvetica", 20));
        this.introlabel.setTextFill(Color.WHITE);


        //set up initial text
        //Move dialogue just sets dialogue based on "dialogueNum," so when dialogueNum = 0
        //(as set above), it just displays the start of Constants.ASTRONAUT_DIALOGUE
        this.moveDialogue();
        mainPane.getChildren().addAll(this.introlabel, directiveLabel);

        //move sprite Astro back and forth on a timeline
        this.runIntroduction();

        /*Set up how user navigates introduction dialogue
         On pressing the space bar, the astro sprite moves up,
         and upon releasing it, the sprite moves down and new dialogue
         is displayed. This creates the visual effect of responding
         to the spacebar.
         */
        mainPane.setFocusTraversable(true);
        mainPane.setOnKeyPressed((KeyEvent e) -> this.moveAstroVert(e, 1)); //spacebar pressed
        //direction = 1 as Astro moves up

        mainPane.setOnKeyReleased((KeyEvent e) -> {
            try {
                moveIntroduction(e);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }); //Spacebar released. Continue dialogue and move astro back down.
        // Note that moving astroDown is contained in method moveIntroduction

        this.root.setCenter(mainPane);


    }

    private void runIntroduction(){
        KeyFrame kf = new KeyFrame(
                Duration.seconds(1),
                (ActionEvent e) -> {
                    //rotate direction moving
                    this.direction *= -1;
                    this.moveAstroHoriz();
                });
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void moveIntroduction(KeyEvent e) throws FileNotFoundException {
        KeyCode keypressed = e.getCode();
        switch (keypressed) {
            case SPACE:
                this.moveAstroVert(e, -1);//Move Astro back down (see initializeInstructions comments)

                //check if dialogue sequence is done - if this is the case, run "this.Body"
                //(move into the main part of the program)
                if ((this.dialogueNum < Constants.ASTRONAUT_DIALOGUE.length/2 + 1)) {
                    this.dialogueNum += 2;
                    this.moveDialogue();
                } else {
                    this.Body();
                }
                break;
            case S:
                this.Body();
            default:
                break;
        }
    }

    private void moveAstroHoriz(){
        double pos = this.astro.getTranslateX();
        this.astro.setTranslateX(pos + this.direction * 5);

    }

    private void moveAstroVert(KeyEvent e, int direction){
        if(e.getCode() == KeyCode.SPACE) {
            double pos = this.astro.getTranslateY();

            //Below if clause is to prevent an interesting bug: if this code is left without the if
            //clause, one can hold down the space bar to run moveAstro with int direction negative
            //infinitely, moving the sprite down to the bottom of the screen
            if (pos + direction * 15 < 16) this.astro.setTranslateY(pos + direction * 15);
        }
    }


    private void moveDialogue(){
        /***
         This works to create the visual effect of scrolling down upon pressing the space bar
         The displayed dialogue (introlabel) gets updated with 4 new dialogues, starting at dialogue
         number n (dialogueNum), which is incremented up outside the functions
         */
        this.introlabel.setText(Constants.ASTRONAUT_DIALOGUE[dialogueNum] +
                Constants.ASTRONAUT_DIALOGUE[dialogueNum + 1] +
                Constants.ASTRONAUT_DIALOGUE[dialogueNum + 2] +
                Constants.ASTRONAUT_DIALOGUE[dialogueNum + 3] +
                Constants.ASTRONAUT_DIALOGUE[dialogueNum + 4]);
    }

    public BorderPane getRoot() {
        return this.root;
    }

}
