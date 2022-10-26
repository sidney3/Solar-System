package Animation;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Astronaut {

    private Group astro;

    public Astronaut(Pane root) throws FileNotFoundException {
        /*** The sprite for the introduction to the
         Cartoon. Simply a collection of shapes.

         Note that the translations are done outside Astronaut,
         in PaneOrganizer, so we simply offer getter and setter
         values to allow these to be changed.
         */

        //Larger helmet for astronaut
        Ellipse Helmet = new Ellipse();
        Helmet.setCenterX(Constants.ASTRO_BODY_X_VALUE);
        Helmet.setCenterY(200);
        Helmet.setRadiusX(95);
        Helmet.setRadiusY(83.5);
        Helmet.setFill(Color.WHITE);

        Ellipse Shades = new Ellipse();
        Shades.setCenterX(Constants.ASTRO_BODY_X_VALUE);
        Shades.setCenterY(225);
        Shades.setRadiusY(68.5);
        Shades.setRadiusX(80);
        Shades.setFill(Color.BLACK);

        Ellipse Body = new Ellipse();
        Body.setCenterX(Constants.ASTRO_BODY_X_VALUE);
        Body.setCenterY(325);
        Body.setRadiusY(125);
        Body.setRadiusX(95);
        Body.setFill(Color.WHITE);

        Ellipse arm1 = new Ellipse();
        arm1.setCenterX(Constants.ASTRO_BODY_X_VALUE + 100);
        arm1.setCenterY(330);
        arm1.setRadiusX(22);
        arm1.setRadiusY(12);
        arm1.setFill(Color.WHITE);
        arm1.setRotate(18);


        Ellipse arm2 = new Ellipse();
        arm2.setCenterX(Constants.ASTRO_BODY_X_VALUE - 100);
        arm2.setCenterY(330);
        arm2.setRadiusX(22);
        arm2.setRadiusY(12);
        arm2.setFill(Color.WHITE);
        arm2.setRotate(-18);

        Rectangle nasaBadge = new Rectangle();
        nasaBadge.setX(Constants.ASTRO_BODY_X_VALUE - Constants.NASA_BADGE_WIDTH/2);
        //rectangle coords determined by top length, so if we want it in the middle of our astronaut,
        //we want the x coord offset by half the width of the nasa badge
        nasaBadge.setY(310);
        nasaBadge.setWidth(Constants.NASA_BADGE_WIDTH);
        nasaBadge.setHeight(Constants.NASA_BADGE_HEIGHT);

        //import image for NASA badge
        Image image = new Image(new FileInputStream(System.getProperty("user.dir") + Constants.NASA_LOGO_PATH));
        nasaBadge.setFill(new ImagePattern(image));

        //Define sprite astro as the group of all it's elements. This allows for movement of "astro" to move
        //All elements
        this.astro = new Group(Helmet,Body,Shades,arm1,arm2, nasaBadge);

        root.getChildren().add(astro);

    }


     //Getter and setter function for updating sprite position

    public double getTranslateY(){
        return(this.astro.getTranslateY());
    }
    public void setTranslateY(double newYPos){
        this.astro.setTranslateY(newYPos);
    }
    public double getTranslateX(){
        return(this.astro.getTranslateX());
    }
    public void setTranslateX(double newXPos){
        this.astro.setTranslateX(newXPos);
    }

}