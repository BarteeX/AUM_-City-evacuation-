package sample;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Martic on 2017-04-09.
 */
public class MapEditor extends Application{

    private int width;
    private int height;
    private int pixelSize;
    private int xPrevious = 0, yPrevious = 0;

    private Color actual, previous;
    private Color underColor;

    private Canvas editorCanvas;
    private Canvas actualColorCanvas;

    private GraphicsContext gc;
    private GraphicsContext gca;

    private Stage primaryStage;

    private Button roadButton;
    private Button buildingButton;
    private Button safeZoneButton;
    private Button saveButton;
    private Button backButton;

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

    public void setCanvas() {
        editorCanvas = new Canvas(width*pixelSize, height*pixelSize);
        gc = editorCanvas.getGraphicsContext2D();
        gc.setFill(Color.DARKGREEN);
        gc.fillRect(0, 0, width*pixelSize, height*pixelSize);

        writableImage = new WritableImage((int)editorCanvas.getWidth(), (int) editorCanvas.getHeight());
        pixelReader = writableImage.getPixelReader();

        editorCanvas.setOnMouseDragged(event -> {
            int x = (int) (event.getX()/pixelSize)*pixelSize;
            int y = (int) (event.getY()/pixelSize)*pixelSize;

            if(event.isPrimaryButtonDown()) {
                gc.setFill(actual);
                gc.fillRect(x, y, pixelSize, pixelSize);
            }
            if(event.isMiddleButtonDown()) {
                gc.setFill(Color.DARKGREEN);
                gc.fillRect(0, 0, width*pixelSize, height*pixelSize);
            }
            if(event.isSecondaryButtonDown()) {
                gc.setFill(previous);
                gc.fillRect(x, y, pixelSize, pixelSize);
            }
        });

        editorCanvas.setOnMousePressed(event -> {
            int x = (int) (event.getX()/pixelSize)*pixelSize;
            int y = (int) (event.getY()/pixelSize)*pixelSize;

            if(event.isPrimaryButtonDown()) {
                gc.setFill(actual);
                gc.fillRect(x, y, pixelSize, pixelSize);
            }
            if(event.isMiddleButtonDown()) {
                gc.setFill(Color.DARKGREEN);
                gc.fillRect(0, 0, width*pixelSize, height*pixelSize);
            }
            if(event.isSecondaryButtonDown()) {
                gc.setFill(previous);
                gc.fillRect(x, y, pixelSize, pixelSize);
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

    public void refreshActualCanvas() {
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

    private void setRoadButton() {
        roadButton = new Button("Road");
        roadButton.setMinSize(100, 30);
        roadButton.setOnMousePressed(event -> {
            setPreviousColor(Color.DARKGRAY == previous ? previous : actual);
            setActualColor(Color.DARKGRAY);
            refreshActualCanvas();
        });
    }

    private void setBuildingButton() {
        buildingButton = new Button("Building");
        buildingButton.setMinSize(100, 30);
        buildingButton.setOnMousePressed(event -> {
            setPreviousColor(Color.DARKBLUE == previous ? previous : actual);
            setActualColor(Color.DARKBLUE);
            refreshActualCanvas();
            new BuildingSet();
        });
    }

    private void setSafeZoneButton() {
        safeZoneButton = new Button("Safe Zone");
        safeZoneButton.setMinSize(100, 30);
        safeZoneButton.setOnMousePressed(event -> {
            setPreviousColor(Color.SPRINGGREEN == previous ? previous : actual);
            setActualColor(Color.SPRINGGREEN);
            refreshActualCanvas();
        });
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

            editorCanvas.snapshot(null, wI);

            if(file != null){
                try {
                    WritableImage writableImage = new WritableImage(width*pixelSize, height*pixelSize);
                    editorCanvas.snapshot(null, writableImage);
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
        backButton.setOnMouseClicked(event -> {
            new MainWindow(primaryStage);
        });
        backButton.setMinSize(100, 30);

    }

    private void setGridPane() {
        mapGrid = new GridPane();
        mapGrid.add(editorCanvas, 0, 0, 1, 7);
        mapGrid.add(roadButton, 1, 0);
        mapGrid.add(buildingButton, 1, 1);
        mapGrid.add(safeZoneButton, 1, 2);
        mapGrid.add(saveButton, 1, 3);
        mapGrid.add(actualColorCanvas, 1, 4);
        mapGrid.add(backButton, 1, 6);
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

        setRoadButton();
        setBuildingButton();
        setSafeZoneButton();
        setSaveButton();
        setBackButton();

        setActualColorCanvas();

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

    public MapEditor(Stage primaryStage, int width, int height, int pixelSize) {
        setSize(width, height, pixelSize);
        start(primaryStage);
    }

    public MapEditor(Stage primaryStage, int width, int height, int pixelSize, Image loadedImage) {
        this(primaryStage, width, height, pixelSize);
        gc.drawImage(loadedImage, 0, 0, width*pixelSize, height*pixelSize);
    }

}