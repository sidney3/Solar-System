package Animation;

import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private PerspectiveCamera camera;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        stage.setTitle("Galaxy");

        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(organizer.getRoot(), Constants.FULL_SCREEN_WIDTH, Constants.FULL_SCREEN_HEIGHT);

        /*In a 3d plane, we need to set a camera at a given position (rather than the implied camera
        of a 2d plane), so this is created.

        The scene gets passed in the camera, so this is the small bit of code that exists in the app class
         */
        cameraInit();
        scene.setCamera(camera);
        stage.setScene(scene);
        stage.show();
    }

    private void cameraInit() {
        camera = new PerspectiveCamera(false);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(0); //these are default camera values. Code is written out here for clarity
    }
}