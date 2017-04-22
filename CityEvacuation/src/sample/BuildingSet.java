package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class BuildingSet extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Building");
        String widthText = "Width : ";
        String heightText = "Height : ";
        String amountText = "Number of flats : ";

        Label widthLabel = new Label(widthText);
        Label heightLabel = new Label(heightText);
        Label amountLabel = new Label(amountText);
        amountLabel.setMinWidth(110);

        Slider widthSlider = new Slider();
        Slider heightSlider = new Slider();
        Slider amountSlider = new Slider();

        Slider[] sliders = new Slider[]{widthSlider, heightSlider, amountSlider};

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
                widthLabel.setText(widthText + (int) widthSlider.getValue());
                heightLabel.setText(heightText + (int) heightSlider.getValue());
                amountLabel.setText(amountText + (int) amountSlider.getValue());
            });
        }
        amountSlider.setMin(1);
        amountSlider.setMax(20);
        amountSlider.setValue(10);
        amountSlider.setBlockIncrement(5);

        Button buildButton = new Button("Build");

        GridPane gridPane = new GridPane();
        gridPane.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, null, null)));
        gridPane.add(widthLabel, 0, 0);
        gridPane.add(heightLabel, 0, 1);
        gridPane.add(amountLabel, 0, 2);
        gridPane.add(buildButton, 0, 3);

        gridPane.add(widthSlider, 1, 0);
        gridPane.add(heightSlider, 1, 1);
        gridPane.add(amountSlider, 1, 2);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(15, 15, 15, 15));
        gridPane.setVgap(10);
        gridPane.setHgap(10);


        primaryStage.setScene(new Scene(gridPane));
        primaryStage.show();
    }

    BuildingSet() {
        start(new Stage());
    }
}
