package sample;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Martic on 2017-04-11.
 */
public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(true);
        new MainWindow(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
