package sample.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class About extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setWidth(500);
        primaryStage.setHeight(350);

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://drive.google.com/file/d/0B3THBykTbnecOUlLT0hPZk10VTg/view");



        primaryStage.setScene(new Scene(webView));
        primaryStage.show();
    }
}
