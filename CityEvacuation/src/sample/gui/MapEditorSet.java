package sample.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MapEditorSet extends Application {
    private Stage primaryStage;

    private String widthTextButton;
    private String heightTextButton ;
    private String pixelTextButton;

    private Label widthLabel;
    private Label heightLabel;
    private Label pixelLabel;

    private Slider widthSlider;
    private Slider heightSlider;
    private Slider pixelSlider;
    private Slider[] sliders;

    private Button buildButton;
    private Button backButton;

    private GridPane grid;

    private void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("City Evacuation");
        primaryStage.setHeight(200);
        primaryStage.setWidth(300);
    }

    private void setLabels() {
        widthTextButton = "Width = \t";
        heightTextButton = "Height = \t";
        pixelTextButton = "Pixel = \t";
        widthLabel = new Label(widthTextButton + "20");
        heightLabel = new Label(heightTextButton + "20");
        pixelLabel = new Label(pixelTextButton + "10");
    }

    private void setSliders() {
        widthSlider = new Slider();
        heightSlider = new Slider();
        pixelSlider = new Slider();

        sliders = new Slider[]{widthSlider, heightSlider, pixelSlider};

        for(Slider slider : sliders) {
            slider.setMin(20);
            slider.setMax(200);
            slider.setValue(20);
            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
            slider.setMajorTickUnit(50);
            slider.setMinorTickCount(1);
            slider.setBlockIncrement(10);
            slider.setOnMouseDragged(event -> {
                widthLabel.setText(widthTextButton + (int) widthSlider.getValue());
                heightLabel.setText(heightTextButton + (int) heightSlider.getValue());
                pixelLabel.setText(pixelTextButton + (int) pixelSlider.getValue());
            });
        }
        pixelSlider.setMin(1);
        pixelSlider.setMax(20);
        pixelSlider.setValue(10);
        pixelSlider.setBlockIncrement(5);
    }

    private void setButtons() {
        buildButton = new Button("Build");
        buildButton.setAlignment(Pos.CENTER);
        buildButton.setOnMouseClicked(event -> {
            int width = (int) widthSlider.getValue();
            int height = (int) heightSlider.getValue();
            int pixelSize = (int) pixelSlider.getValue();

            new MapEditor(primaryStage, width, height, pixelSize);
        });

        backButton = new Button("Back");
        backButton.setAlignment(Pos.CENTER_RIGHT);
        backButton.setOnMouseClicked(event -> new MainWindow(primaryStage));
    }

    private void setGridPane() {
        grid = new GridPane();
        grid.add(widthLabel, 0, 0);
        grid.add(heightLabel,0, 1);
        grid.add(pixelLabel,0, 2);
        grid.add(widthSlider, 1, 0);
        grid.add(heightSlider, 1, 1);
        grid.add(pixelSlider, 1, 2);
        grid.add(buildButton, 0, 3);
        grid.add(backButton, 1, 3);
        grid.setAlignment(Pos.CENTER);
        grid.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.LIGHTSALMON,
                                CornerRadii.EMPTY,
                                null
                        )
                )
        );
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setLabels();
        setSliders();
        setButtons();
        setGridPane();

        primaryStage.setScene(new Scene(grid));
        primaryStage.show();
    }

    MapEditorSet(Stage primaryStage) {
        start(primaryStage);
    }
}
