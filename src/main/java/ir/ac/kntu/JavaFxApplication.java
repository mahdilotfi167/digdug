package ir.ac.kntu;

import javafx.application.Application;
import javafx.stage.Stage;
public class JavaFxApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        //*====================================|
        MenuHandler mh = new MenuHandler(stage);
        //*====================================|
        // Setting stage properties
        // stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("DigDig");
        stage.show();
    }
}