package Animation;

public class Constants {

    public static final double PLANET_Y = 350;
    //All planets rotate along the same y-value

    public static final double VBOX_WIDTH = 225;
    public static final double VBOX_HEIGHT = 400;
    public static final double[][] PLANET_PATH = CircleCalculations.circleCoords();
    public static final double MIDDLE_CENTER_PANE = 250;
    public static final double FULL_SCREEN_WIDTH = 800;
    public static final double FULL_SCREEN_HEIGHT = 800;

    public static final double ASTRO_BODY_X_VALUE=190;
    public static final double NASA_BADGE_WIDTH=50;
    public static final double NASA_BADGE_HEIGHT=.8278*Constants.NASA_BADGE_WIDTH;
    //formatting a 1237X1024 image, so this is the ratio of the width and the height

    public static final double PLANET_ROTATION = 2.5;

    //Fun facts displayed when mousing over planet
    public static final String EARTH_FACTS =
                    "Earth! Home to civilization \n" +
                    "and some of the finest pizza\n" +
                    "in all of outer space! Give \n" +
                    "it a visit!"; //28 characters a line
    public static final String MARS_FACTS =
                    "Mars! Humanity's future home?\n" +
                    "depends on who you ask, but\n" +
                    "don't forget your radiation\n" +
                    "suit!";
    public static final String JUPITER_FACTS =
                    "Jupiter! Can you spot the so\n" +
                    "called Great Red Spot? we've \n" +
                    "been watching this huge storm\n" +
                    "since as early as 1665! And \n" +
                    "you thought Providence was bad";
    public static final String NEPTUNE_FACTS =
                    "Pack your North Face Jacket? I\n" +
                    "really hope so. at -373 degrees\n" +
                    "Fahrenheit, Neptune is the cold-\n" +
                    "-est planet in our solar system!";
    public static final String VENUS_FACTS =
                    "This place is where I should be\n" +
                    "going for my homework! A single\n" +
                    "day on Venus lasts a year on ea-\n" +
                    "-rth. Almost enough for my PSets.";
    public static final String SATURN_FACTS =
                    "This one for the flat earthers in\n" +
                    "the world! Saturn is the flattest\n" +
                    "planet in our solar system. Maybe\n" +
                    "they should move here.";
    public static final String MERCURY_FACTS =
                    "Though I wish I were talking about\n" +
                    "Pluto, Mercury is the smallest planet\n" +
                    "in the solar system. Not to mention, \n" +
                    "its still shrinking!";
    public static final String URANUS_FACTS =
                    "Similar to the East Coast, Uranus has\n" +
                    "giant clouds of freezing gases that\n" +
                    "float around in it's atmosphere.";
    public static final String SUN_FACTS =
                    "Did you know a million earths\n" +
                    "could fit inside the sun? it \n" +
                    "might not look like it here, \n" +
                    "but I promise it's true";


    public static final double JUPITER_SIZE = 40; //jupiter is the largest planet, so everything is built around it
    public static final double MERCURY_SIZE = JUPITER_SIZE * .3;
    /*explanation of sizing ratio method - if true ratios are used, Jupiter is way too big
    Therefore, planet size ratios are modified for better viewing
     */

    //Planets
    public static final double VENUS_SIZE = JUPITER_SIZE * .3;
    public static final double EARTH_SIZE = JUPITER_SIZE * .5;
    public static final double MARS_SIZE = JUPITER_SIZE * .5;
    public static final double SATURN_SIZE = JUPITER_SIZE * .8;
    public static final double URANUS_SIZE = JUPITER_SIZE * .8;
    public static final double NEPTUNE_SIZE = JUPITER_SIZE * .8;
    public static final double SUN_SIZE = JUPITER_SIZE * 1.75;

    //sun stays in one spot, so we can store its values
    public static final double SUN_X_VALUE = 187.5;
    public static final double SUN_Y_VALUE = PLANET_Y;
    public static final double SUN_Z_VALUE = 0;



    public static final String[] ASTRONAUT_DIALOGUE = {
            "Hello and welcome to Space!\n" +
                    "How was the trip?\n",
"\n",
            "Before you go explore, let \n",
                    "me show you around!\n",
"\n",

            "Hover over each planet to get info \n",
"\n",
            "Feel like causing some chaos?\n" +
            "Click on a planet to change its orbit\n",

"\n",
            "Click the stop button to chill a bit\n",
            "and quit once you're done\n",
            "\n",
                    "Oh, and turn on Andy Mode at your own risk...\n"
    };



    //Paths of the planets
    public static final String VENUS_PATH = "/src/Images/venus.jpg";
    public static final String JUPITER_PATH = "/src/Images/jupiter.jpg";
    public static final String NEPTUNE_PATH = "/src/Images/neptune.jpg";
    public static final String SATURN_PATH = "/src/Images/saturn.jpg";
    public static final String EARTH_PATH = "/src/Images/earth.jpg";
    public static final String MARS_PATH = "/src/Images/mars.jpg";
    public static final String MERCURY_PATH = "/src/Images/mercury.jpg";
    public static final String URANUS_PATH = "/src/Images/uranus.jpg";
    public static final String SUN_PATH = "/src/Images/sun.jpg";


    public static final String NASA_LOGO_PATH = "/src/Images/nasalogo.jpg";

    //don't look at these
    public static final String ANDY_PATH = "/src/Images/andy.jpg";
    public static final String ANDY_FACTS =
            "One of the great figures of \n" +
            "computer science... \n\n" +
            "Andy Van Dam has long been \n" +
            "present in the world of \n" +
            "computer graphics, practi-\n" +
            "-cally creating HyperText, \n" +
            "writing world renowned text- \n" +
            "-book Computer Graphics: Pri- \n" +
            "-nciples and Practices, among \n" +
            "many other things\n\n" +
            "An influential figure in the \n" +
            "lives of many, particularly his\n" +
            "students, it's hard to think of \n" +
            "Professor Van Dam as anything short\n" +
            "of Amazing.";



}



