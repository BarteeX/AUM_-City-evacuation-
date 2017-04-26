package sample.gui;

import javafx.application.Application;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sample.structure.map.CityMap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Evacuation extends Application {
    Button loadButton;
    GridPane gridPane;
    Stage primaryStage;
    CityMap map;
    int width, height, numberOfLayers;
    List<Canvas> layersCanvas;
    ChoiceBox layerListChoiceBox;

    private void setPrimaryStage() {
        primaryStage.close();
        primaryStage.setTitle("Evacuation");
        primaryStage.show();
        primaryStage.setWidth(400);
        primaryStage.setHeight(300);
        primaryStage.setScene(new Scene(gridPane));

    }

    private void setButtons() {
        loadButton = new Button("Load Map");

        loadButton.setOnMousePressed(event -> {
            DirectoryChooser dC = new DirectoryChooser();
            dC.setInitialDirectory(new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop"));
            File directory = dC.showDialog(primaryStage);
            List<File> fileTab = new ArrayList<>();
            for (File file : directory.listFiles()) {
                fileTab.add(file);
            }
            layersCanvas = new ArrayList<>();
            layerListChoiceBox = new ChoiceBox();
            numberOfLayers = 0;
            for (File file : fileTab) {
                if(file.getName().endsWith(".png")) {
                    Image layerImage = new Image(file.toURI().toString());
                    int imageWidth = (int) layerImage.getWidth();
                    int imageHeight = (int) layerImage.getHeight();
                    Canvas layerCanvas = new Canvas(imageWidth, imageHeight);
                    layerCanvas.getGraphicsContext2D().drawImage(layerImage, 0, 0,imageWidth, imageHeight);
                    layersCanvas.add(layerCanvas);
                    numberOfLayers++;
                    int index = fileTab.indexOf(file);
                    if(index == 0){
                        resize(imageWidth, imageHeight);
                        width = imageWidth;
                        height = imageHeight;
                    }
                    gridPane.add(layersCanvas.get(index), 0, 1, 2, 1);
                    layerListChoiceBox.getItems().add((index+1) + ".layer");
                }
            }
            layersCanvas.get(0).toFront();
            gridPane.add(layerListChoiceBox, 1, 0);
            setLayerListChoiceBox();
        });
    }

    private void resize(int x, int y) {
        primaryStage.setWidth(x + 80);
        primaryStage.setHeight(y + 120);
    }

    private void setLayerListChoiceBox() {
        layerListChoiceBox.getSelectionModel().selectFirst();
        layerListChoiceBox.setOnAction(event -> {
            int position = layerListChoiceBox.getSelectionModel().getSelectedIndex();
            layersCanvas.get(position).toFront();
        });
    }

    private void buildCityMap() {
        map = new CityMap(width, height, numberOfLayers);
        for(int i = 0 ; i < layersCanvas.size(); ++i) {
            WritableImage wI = new WritableImage(width,height);
            layersCanvas.get(i).snapshot(null, wI);
            PixelReader pR = wI.getPixelReader();
            for(int y = 0 ; y < height; ++y) {
                for(int x = 0; x < width; ++x) {
                    Color tileColor = pR.getColor(x, y);
                    switch(tileColor) {

                    }
                }
            }

        }
    }

    private void setGridPane() {
        gridPane = new GridPane();
        gridPane.add(loadButton, 0, 0);
        gridPane.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.LIGHTSALMON,
                                null,
                                null
                        )
                )
        );
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setButtons();
        setGridPane();
        setPrimaryStage();
    }

    public Evacuation(Stage primaryStage) {
        start(primaryStage);
    }
}
