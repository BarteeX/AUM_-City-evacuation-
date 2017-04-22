package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MainWindow extends Application {

    private GridPane gridPane;
    private double width;
    private double height;
    private Stage mainStage;


    private Label nameLabel, menuLabel;
    private Button  evacuationButton, mapButton,
                    agentButton, settingsButton,
                    aboutButton, exitButton;


    public void setButtonsSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    private void setLabels() {
        this.nameLabel = new Label("City Evacuation");
        this.nameLabel.setAlignment(Pos.CENTER);
        this.nameLabel.setMinWidth(this.width);
        this.nameLabel.setMinHeight(this.height);

        this.menuLabel = new Label("Menu:");
        this.menuLabel.setMinWidth(this.width);
        this.menuLabel.setMinHeight(this.height);
        this.menuLabel.setAlignment(Pos.CENTER);
        this.menuLabel.setTextAlignment(TextAlignment.CENTER);
    }

    private void setEvacuationButton() {
        this.evacuationButton = new Button("Evacuation");
        this.evacuationButton.setAlignment(Pos.CENTER);
        this.evacuationButton.setMinWidth(this.width);
        this.evacuationButton.setMinHeight(this.height);
    }

    private void setMapButton() {
        this.mapButton = new Button("Map Creator");
        this.mapButton.setAlignment(Pos.CENTER);
        this.mapButton.setMinWidth(this.width);
        this.mapButton.setMinHeight(this.height);
        this.mapButton.setOnMouseClicked(event -> {
            new MapEditorSet(mainStage);
        });
    }

    private void setAgentButton() {
        this.agentButton = new Button("Train Agents");
        this.agentButton.setAlignment(Pos.CENTER);
        this.agentButton.setMinWidth(this.width);
        this.agentButton.setMinHeight(this.height);
    }

    private void setSettingsButton() {
        this.settingsButton = new Button("Train Settings");
        this.settingsButton.setAlignment(Pos.CENTER);
        this.settingsButton.setMinWidth(this.width);
        this.settingsButton.setMinHeight(this.height);
    }

    private void setAboutButton() {
        this.aboutButton = new Button("About");
        this.aboutButton.setAlignment(Pos.CENTER);
        this.aboutButton.setMinWidth(this.width);
        this.aboutButton.setMinHeight(this.height);
    }

    private void setExitButton() {
        this.exitButton = new Button("Exit");
        this.exitButton.setAlignment(Pos.CENTER);
        this.exitButton.setMinWidth(this.width);
        this.exitButton.setMinHeight(this.height);
        this.exitButton.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }

    public Button getEvacuationButton() {
        return this.evacuationButton;
    }

    public Button getMapButton() {
        return this.mapButton;
    }

    public Button getAgentButton() {
        return this.agentButton;
    }

    public Button getSettingsButton() {
        return this.settingsButton;
    }

    public Button getAboutButton() {
        return this.aboutButton;
    }

    public Button getExitButton() {
        return this.exitButton;
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
        this.gridPane.add(this.agentButton, 0 ,4);
        this.gridPane.add(this.settingsButton, 0 ,5);
        this.gridPane.add(this.aboutButton, 0 ,6);
        this.gridPane.add(this.exitButton, 0 ,7);
    }


    @Override
    public void start(Stage primaryStage){
        this.mainStage = primaryStage;
        this.mainStage.setTitle("City Evacuation");
        this.mainStage.setHeight(420);
        this.mainStage.setWidth(130);

        width = 100;
        height = 30;

        setButtonsSize(width, height);
        setEvacuationButton();
        setMapButton();
        setAgentButton();
        setSettingsButton();
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

   public MainWindow(Stage primaryStage) {
        start(primaryStage);
   }
}

