package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapEditor extends Application{

    private int width;
    private int height;
    private int pixelSize;
    private int xPrevious = 0, yPrevious = 0;
    private int numberOfLayers = 1;
    private int actualLayerIndex = 0;

    private Color actual, previous;
    private Color underColor;

    private List<Canvas> editorCanvasList = new ArrayList<>();
    private Canvas actualColorCanvas;

    private List<GraphicsContext> gcList = new ArrayList<>();
    private GraphicsContext gca;

    private Stage primaryStage;

    private Button saveButton;
    private Button backButton;

    private ChoiceBox elementChoiceBox;
    private ChoiceBox layerChoiceBox;

    private GridPane mapGrid;

    private WritableImage writableImage;
    private PixelReader pixelReader;

    private void setActualColor(Color actual) {
        this.actual = actual;
    }

    private void setPreviousColor(Color previous) {
        this.previous = previous;
    }

    private void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void setCanvas() {
        editorCanvasList.add(new Canvas(width*pixelSize, height*pixelSize));
        gcList.add(editorCanvasList.get(actualLayerIndex).getGraphicsContext2D());

        gcList.get(actualLayerIndex).setFill(Color.DARKGREEN);
        gcList.get(actualLayerIndex).fillRect(0, 0, width*pixelSize, height*pixelSize);

        writableImage = new WritableImage((int)editorCanvasList.get(0).getWidth(), (int) editorCanvasList.get(actualLayerIndex).getHeight());
        pixelReader = writableImage.getPixelReader();

        editorCanvasList.get(actualLayerIndex).setOnMouseDragged(event -> {
            int x = (int) (event.getX()/pixelSize)*pixelSize;
            int y = (int) (event.getY()/pixelSize)*pixelSize;

            if(event.isPrimaryButtonDown()) {
                gcList.get(actualLayerIndex).setFill(actual);
                gcList.get(actualLayerIndex).fillRect(x, y, pixelSize, pixelSize);
            }
            if(event.isMiddleButtonDown()) {
                gcList.get(actualLayerIndex).setFill(Color.DARKGREEN);
                gcList.get(actualLayerIndex).fillRect(0, 0, width*pixelSize, height*pixelSize);
            }
            if(event.isSecondaryButtonDown()) {
                gcList.get(actualLayerIndex).setFill(previous);
                gcList.get(actualLayerIndex).fillRect(x, y, pixelSize, pixelSize);
            }
        });

        editorCanvasList.get(actualLayerIndex).setOnMousePressed(event -> {
            int x = (int) (event.getX()/pixelSize)*pixelSize;
            int y = (int) (event.getY()/pixelSize)*pixelSize;

            if(event.isPrimaryButtonDown()) {
                gcList.get(actualLayerIndex).setFill(actual);
                gcList.get(actualLayerIndex).fillRect(x, y, pixelSize, pixelSize);
            }
            if(event.isMiddleButtonDown()) {
                gcList.get(actualLayerIndex).setFill(Color.DARKGREEN);
                gcList.get(actualLayerIndex).fillRect(0, 0, width*pixelSize, height*pixelSize);
            }
            if(event.isSecondaryButtonDown()) {
                gcList.get(actualLayerIndex).setFill(previous);
                gcList.get(actualLayerIndex).fillRect(x, y, pixelSize, pixelSize);
            }
        });

        // poruszanie kursorem
        /*editorCanvas.setOnMouseMoved(event -> {
            int x = (int) (event.getX()/pixelSize)*pixelSize;
            int y = (int) (event.getY()/pixelSize)*pixelSize;

            if(event.isPrimaryButtonDown()) {
                gc.setFill(actual);
                gc.fillRect((event.getX()/pixelSize)*pixelSize, (event.getY()/pixelSize)*pixelSize, pixelSize, pixelSize);
            } else if(event.isMiddleButtonDown()) {
                gc.setFill(Color.DARKGREEN);
                gc.fillRect(0, 0, width*pixelSize, height*pixelSize);
            } else if(event.isSecondaryButtonDown()) {
                gc.setFill(previous);
                gc.fillRect(x, y, pixelSize, pixelSize);
            } else if ((x != xPrevious || y !=yPrevious)) {
                editorCanvas.snapshot(null, writableImage);
                gc.setFill(underColor);
                gc.fillRect(xPrevious, yPrevious, pixelSize, pixelSize);
                underColor = writableImage.getPixelReader().getColor(x, y);
                gc.setFill(actual);
                gc.fillRect(x, y, pixelSize, pixelSize);
                xPrevious = x;
                yPrevious = y;
                if(event.isPrimaryButtonDown()) gc.fillRect(x,y,pixelSize,pixelSize);
            }
        });*/
    }

    private void refreshActualCanvas() {
        gca.setFill(actual);
        gca.fillRect(0,0, actualColorCanvas.getWidth()/2, 50);
        gca.setFill(previous);
        gca.fillRect(actualColorCanvas.getWidth()/2,0, actualColorCanvas.getWidth()/2, 50);
    }

    private void setActualColorCanvas() {
        actualColorCanvas = new Canvas(100, 50);
        gca = actualColorCanvas.getGraphicsContext2D();
        refreshActualCanvas();
    }

    private void setSaveButton() {
        saveButton = new Button("Save");
        saveButton.setOnMousePressed(event -> {
            FileChooser fC = new FileChooser();
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
            fC.getExtensionFilters().add(extFilter);
            File file = fC.showSaveDialog(primaryStage);
            WritableImage wI = new WritableImage(width*pixelSize, height*pixelSize);

            editorCanvasList.get(0).snapshot(null, wI);

            if(file != null){
                try {
                    WritableImage writableImage = new WritableImage(width*pixelSize, height*pixelSize);
                    editorCanvasList.get(0).snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        saveButton.setMinSize(100, 30);
    }

    private void setBackButton() {
        backButton = new Button("Back");
        backButton.setOnMouseClicked(event -> new MainWindow(primaryStage));
        backButton.setMinSize(100, 30);

    }

    private void setElementChoiceBox() {
        elementChoiceBox = new ChoiceBox(FXCollections.observableArrayList(
                "Wall",
                "Road",
                "Floor",
                "Window",
                "Door",
                "Furniture",
                "Upstairs",
                "Downstairs",
                "SafeZone"
        ));
        elementChoiceBox.getSelectionModel().selectFirst();
        setPreviousColor(Color.BLACK == previous ? previous : actual);
        setActualColor(Color.BLACK);
        refreshActualCanvas();

        elementChoiceBox.setOnAction(event -> {
            Color chooseColor;
            switch(elementChoiceBox.getSelectionModel().getSelectedIndex()) {
                case 0 :
                    chooseColor = Color.BLACK;
                    break;
                case 1 :
                    chooseColor = Color.GREY;
                    break;
                case 2 :
                    chooseColor = Color.WHITE;
                    break;
                case 3 :
                    chooseColor = Color.BLUE;
                    break;
                case 4 :
                    chooseColor = Color.BROWN;
                    break;
                case 5 :
                    chooseColor = Color.LAWNGREEN;
                    break;
                case 6 :
                    chooseColor = Color.YELLOW;
                    break;
                case 7 :
                    chooseColor = Color.PURPLE;
                    break;
                case 8 :
                    chooseColor = Color.LIGHTPINK;
                    break;
                default:
                    chooseColor = Color.LIGHTGREEN;
            }
            setPreviousColor(chooseColor == previous ? previous : actual);
            setActualColor(chooseColor);
            refreshActualCanvas();
        });

    }

    private void setLayersChoiceBox() {
        layerChoiceBox = new ChoiceBox(FXCollections.observableArrayList(
                "1. Layers",
                "Add new layer..."
        ));
        layerChoiceBox.getSelectionModel().selectFirst();
        layerChoiceBox.setOnAction(event -> {
            if(layerChoiceBox.getSelectionModel().getSelectedItem().toString().equals("Add new layer...")) {
                numberOfLayers++;
                actualLayerIndex = numberOfLayers - 1;
                layerChoiceBox.getItems().add(actualLayerIndex, numberOfLayers + ".Layer");
                setCanvas();
                mapGrid.add(editorCanvasList.get(actualLayerIndex), 0, 0, 1, 7);
            } else {
                actualLayerIndex = layerChoiceBox.getSelectionModel().getSelectedIndex();
                editorCanvasList.get(actualLayerIndex).toFront();
            }
        });
    }

    private void setGridPane() {
        mapGrid = new GridPane();
        mapGrid.add(editorCanvasList.get(actualLayerIndex), 0, 0, 1, 7);
        mapGrid.add(elementChoiceBox, 1, 0);
        mapGrid.add(saveButton, 1, 1);
        mapGrid.add(actualColorCanvas, 1, 2);
        mapGrid.add(backButton, 1, 3);
        mapGrid.add(layerChoiceBox, 1, 4);
        mapGrid.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.LIGHTSALMON,
                                CornerRadii.EMPTY,
                                null
                        )
                )
        );
        mapGrid.setPadding(new Insets(20, 20 ,20 ,20));
        mapGrid.setVgap(10);
    }

    @Override
    public void start(Stage primaryStage){
        setPrimaryStage(primaryStage);

        setCanvas();

        setSaveButton();
        setBackButton();

        setActualColorCanvas();
        setElementChoiceBox();
        setLayersChoiceBox();
        setGridPane();

        primaryStage.setWidth(width*pixelSize + 160);
        primaryStage.setHeight(height*pixelSize + 60);
        primaryStage.setScene(new Scene(mapGrid));
        primaryStage.show();
    }

    private void setSize(int width, int height, int pixelSize) {
        this.height = height;
        this.width = width;
        this.pixelSize = pixelSize;
    }

    MapEditor(Stage primaryStage, int width, int height, int pixelSize) {
        setSize(width, height, pixelSize);
        start(primaryStage);
    }

    MapEditor(Stage primaryStage, int width, int height, int pixelSize, Image loadedImage) {
        this(primaryStage, width, height, pixelSize);
        gcList.get(0).drawImage(loadedImage, 0, 0, width*pixelSize, height*pixelSize);
    }

}
