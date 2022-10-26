package Animation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Planet {

    public Sphere planet;
    public int spinDirection;
    public int positionIndex;
    public boolean isSun;
    private Pane root;
    public int rotations;
    private int fullrotations;
    public int orbits;



    public Planet(String texturePath, int startingIndex, double Ypos, Pane inputroot, double planetRadius,
                  boolean isSunImport) throws FileNotFoundException {

        /***
         The most important part of the entire program:
         the creation of the planets.

         There are 2 things going on in this class, the creation
         and positioning of the sphere that becomes the planet,

         and the much more complex motion of the planets (spinning
         independently and orbiting the sun).

         note: The specific
         implementation for the orbiting of the sun is described
         in class circleCalculations - I find it quite interesting.
         TLDR: planets follow the path of a circle around the sun center,
         jumping from point to point that together make up this circle

         These two forms of motion are done in the same timeline

         Every 0.02 seconds the planets rotate by a set amount
         and their position updates to follow the orbital circle.

         The key implementation challenge, though, is ordering of the planets
         JavaFX does not change the order of the planets automatically,
         so in orbiting a single point, a planet "behind" another planet
         will appear in front of it.
         The solution for this is simply that planets with a negative Z-coordinate
         (closer to the camera) are automatically moved up every loop, while
         planets with a positive Z-coordinate (further away), are moved back
         every loop. One could only run this check at the start of each cycle,
         but this won't accommodate for the starting positions.
         */

        this.root = inputroot;

        //Starting position (our planets move along a path of 750 points for the orbital implementation, so this
        //determines the point they start on(
        this.positionIndex = startingIndex;

        //initialize rotation data
        this.fullrotations = 0;
        this.rotations = 0;

        this.isSun = isSunImport; //input sunException (detailed in README)


        //Set initial position (based off the starting index)
        double Zpos = Constants.PLANET_PATH[startingIndex][1];
        double Xpos = Constants.PLANET_PATH[startingIndex][0];


        //Set the planet into "orbit position"
        //See "Circle Calculations" File if confused here

        this.planet = new Sphere(planetRadius);



        //set position
        this.planet.setTranslateX(Xpos);
        this.planet.setTranslateY(Ypos);
        this.planet.setTranslateZ(Zpos);

        this.planet.setRotationAxis(Rotate.Y_AXIS);

        //set texture -> a phong material is simply a wrapped 2d map
        Image planetTexture = importRawImage(texturePath);
        PhongMaterial planetSurface = new PhongMaterial();
        planetSurface.setSelfIlluminationMap(planetTexture); //wrapped 2d map is assigned to an image
        this.planet.setMaterial(planetSurface); //and then tied to the planet

        //Spin direction is initialized. This can be changed by clicking
        //the planet.
        this.spinDirection = 1;


        this.planet.setOnMouseClicked(event -> { //tie clicking planets to changing the planets spin direction
            spinDirection *= -1;
            event.consume();
        });

        if(isSun) { //set up sun
            //As the sun isn't orbiting, it has its own special case with set coordinates
            //Could have been a child method of planets, but this is less code

            this.planet.setTranslateX(Constants.SUN_X_VALUE);
            this.planet.setTranslateZ(Constants.SUN_Z_VALUE);
            this.planet.setTranslateY(Constants.SUN_Y_VALUE);
            this.planet.toBack();
        }

        //set initial positions. Planets on the front half of their loop (in front of the sun) to the front
        //planets on the second half of their loop (behind the sun) to the back.


        setUpTimeLine();

        this.root.getChildren().add(this.planet);
    }

    public int getOrbits(){
        return this.orbits;
    }

    public int getRotations(){ return this.fullrotations;}


    public void spin(int direction) {
        double sphereRotation = this.planet.getRotate();

        //Different rotation speeds for sun and planets (more scientifically correct)
        if(!isSun) this.planet.setRotate(sphereRotation + direction*Constants.PLANET_ROTATION);
        else this.planet.setRotate(sphereRotation + direction*Constants.PLANET_ROTATION*.5);
    }

    public void setUpTimeLine() {

        KeyFrame kf = new KeyFrame(
                Duration.seconds(.02),


                (ActionEvent e) -> {
                    //These statements called even if paused (as the ANDYMODE button spawns new planets in with everything paused)
                    if(this.positionIndex > 0 && this.positionIndex < 376){ //if in back of loop, move to back
                        //one must call this every time for the initial case (if this is just called on looping around,
                        //then the first loop will have all planets behind the sun)
                        this.planet.toBack();
                    }
                    if(this.positionIndex > 376){//Furthermore, if coming back around the sun, move to front
                        this.planet.toFront();
                    }


                    //Spinning
                    if(!Universals.paused) {
                        this.spin(this.spinDirection); //Spin based on positivity/negativity of spindirection
                                                        //notice how this is linked to the setOnMouseClicked
                                                        //Lines in the constructor method
                        this.rotations ++;
                        if(this.rotations == 360/Constants.PLANET_ROTATION) {this.fullrotations++; this.rotations = 0;}
                        //Our rotations value is upticked every time earth rotates by a set angle (planet_rotation),
                        //however a full rotation = a 360 degree shift in rotations

                    }


                    //Orbiting
                    if (!this.isSun && !Universals.paused) { //Sun doesn't orbit.
                        //We need this rather than just not calling the sun on this method because we
                        //Do want sun rotation

                        if (this.positionIndex == 749) { //If at the end of the coordinate set, go back to start
                            this.positionIndex = 0;
                            this.orbits++; //update orbits
                        } else {
                            positionIndex++; //Move forward in 1 position index
                        }
                        //Refer to CircleCalculations Class ^^^^^


                        this.planet.setTranslateX(Constants.PLANET_PATH[this.positionIndex][0]);
                        this.planet.setTranslateZ(Constants.PLANET_PATH[this.positionIndex][1]);
                        //move planet 1 forward in conveyor belt of position
                    }
                });
                    Timeline timeline = new Timeline(kf);
                    timeline.setCycleCount(Animation.INDEFINITE);
                    timeline.play();
    }

    public Sphere getPlanet(){
        return this.planet;
    }
    Image importRawImage(String path) throws FileNotFoundException {return(new Image(new FileInputStream(path)));}

}

