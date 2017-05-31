package sample.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainWindow extends Application {

    private GridPane gridPane;
    private double width;
    private double height;
    private Stage mainStage;


    private Label nameLabel, menuLabel;
    private Button  evacuationButton, mapButton,
                    aboutButton, exitButton;


    private void setButtonsSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    private void setLabels() {
        this.nameLabel = new Label("City Environment");
        this.nameLabel.setAlignment(Pos.CENTER);
        this.nameLabel.setMinWidth(this.width);
        this.nameLabel.setMinHeight(this.height);

        this.menuLabel = new Label("Menu:");
        this.menuLabel.setMinWidth(this.width);
        this.menuLabel.setMinHeight(this.height);
        this.menuLabel.setAlignment(Pos.CENTER);
        this.menuLabel.setTextAlignment(TextAlignment.CENTER);
    }

    private void setEvacuationButton(){
        this.evacuationButton = new Button("Environment");
        this.evacuationButton.setAlignment(Pos.CENTER);
        this.evacuationButton.setMinWidth(this.width);
        this.evacuationButton.setMinHeight(this.height);
        this.evacuationButton.setOnMousePressed(event ->{
            DirectoryChooser dC = new DirectoryChooser();
            dC.setInitialDirectory(new File("C:\\Users\\" + System.getProperty("user.name") + "\\IdeaProjects\\AUM_-City-evacuation-"));
            File directory = dC.showDialog(mainStage);
            if(directory != null) {
                new Evacuation(mainStage, directory);
            }
        });
    }

    private void setMapButton() {
        this.mapButton = new Button("Map Creator");
        this.mapButton.setAlignment(Pos.CENTER);
        this.mapButton.setMinWidth(this.width);
        this.mapButton.setMinHeight(this.height);
        this.mapButton.setOnMouseClicked(event -> new MapEditorSet(mainStage));
    }

    private void setAboutButton() {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();

        this.aboutButton = new Button("About");
        this.aboutButton.setOnAction(event -> {
            Application about = new Application(){
                @Override
                public void start(Stage primaryStage) throws Exception {
                    primaryStage.setScene(new Scene(browser));
                    primaryStage.setWidth(browser.getWidth());
                    primaryStage.setHeight(browser.getHeight());
                    webEngine.load("https://drive.google.com/file/d/0B3THBykTbnecOUlLT0hPZk10VTg/view");
                    primaryStage.show();
                }
            };
            //about.start(new Stage());
        });
        this.aboutButton.setAlignment(Pos.CENTER);
        this.aboutButton.setMinWidth(this.width);
        this.aboutButton.setMinHeight(this.height);
    }

    private void setExitButton() {
        this.exitButton = new Button("Exit");
        this.exitButton.setAlignment(Pos.CENTER);
        this.exitButton.setMinWidth(this.width);
        this.exitButton.setMinHeight(this.height);
        this.exitButton.setOnMouseClicked(event -> System.exit(0));
    }

    private void setGridPane() {
        this.gridPane = new GridPane();
        this.gridPane.setHgap(15);
        this.gridPane.setVgap(15);
        this.gridPane.setAlignment(Pos.CENTER);
        this.gridPane.setBackground(new Background(new BackgroundFill(Color.LIGHTSALMON, CornerRadii.EMPTY, null)));
        this.gridPane.setPadding(new Insets(20, 20, 20, 20));
    }

    private void addLabelsToGrid() {
        this.gridPane.add(nameLabel, 0, 0);
        this.gridPane.add(menuLabel, 0, 1);
    }

    private void addButtonsToGrid() {
        this.gridPane.add(this.evacuationButton, 0 ,2);
        this.gridPane.add(this.mapButton, 0 ,3);
        this.gridPane.add(this.aboutButton, 0 ,4);
        this.gridPane.add(this.exitButton, 0 ,5);
    }


    @Override
    public void start(Stage primaryStage){
        this.mainStage = primaryStage;
        this.mainStage.setTitle("City Environment");
        primaryStage.setHeight(420);
        primaryStage.setWidth(130);
        primaryStage.setMinHeight(420);
        primaryStage.setMinWidth(130);

        width = 100;
        height = 30;

        setButtonsSize(width, height);
        setEvacuationButton();
        setMapButton();
        setAboutButton();
        setExitButton();

        setLabels();

        setGridPane();

        addLabelsToGrid();
        addButtonsToGrid();



        Scene menuScene = new Scene(gridPane);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    MainWindow(Stage primaryStage) {
        start(primaryStage);
   }
}

