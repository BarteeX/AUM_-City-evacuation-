package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
    private Button loadButton;

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
            slider.setMin(0);
            slider.setMax(100);
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
        pixelSlider.setMax(15);
        pixelSlider.setValue(10);
        pixelSlider.setBlockIncrement(2);
    }

    public void setButtons() {
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
        backButton.setOnMouseClicked(event -> {
            new MainWindow(primaryStage);
        });

        loadButton = new Button("Load");
        loadButton.setOnMousePressed(event -> {
            FileChooser fC = new FileChooser();
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
            fC.getExtensionFilters().add(extFilter);
            File file = fC.showOpenDialog(primaryStage);
            if(file.getName().endsWith(".png")) {
                Image image = new Image(file.toURI().toString());
                new MapEditor(
                        primaryStage,
                        (int) (image.getWidth()/pixelSlider.getValue()),
                        (int) (image.getHeight()/pixelSlider.getValue()),
                        (int) pixelSlider.getValue(),
                        image
                );
            }
        });
        loadButton.setMaxSize(100, 70);
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
        grid.add(loadButton, 2, 3);
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

    public MapEditorSet(Stage primaryStage) {
        start(primaryStage);
    }
}
