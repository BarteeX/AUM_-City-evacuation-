package sample.gui;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.structure.points.Agent;
import sample.structure.map.CityMap;
import sample.structure.points.*;
import sample.structure.points.Window;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
import static sample.structure.points.TileColors.*;

public class Evacuation extends Application {
    GridPane gridPane;
    CityMap map;
    Button backButton;
    CheckBox agentCheckbox;
    boolean addingAgents;
    Button iterationButton;
    int width, height, numberOfLayers, actualNumLayer;
    List<Canvas> layersCanvas;
    Canvas agentsCanvas;
    List<Agent> agentList;
    ChoiceBox layerListChoiceBox;

    private void initVar() {
        gridPane = new GridPane();
        layersCanvas = new ArrayList<>();
        layerListChoiceBox = new ChoiceBox();
        agentList = new ArrayList<>();
        addingAgents= false;
    }

    private void setPrimaryStage(Stage primaryStage) {
        primaryStage.close();
        primaryStage.setTitle("Evacuation");
        primaryStage.show();
        primaryStage.setWidth(this.width*TILE_SIZE + 120);
        primaryStage.setHeight(this.height*TILE_SIZE + 60);
        primaryStage.setScene(new Scene(gridPane));
    }

    private void drawCanvas() {
        for(int layer = 0; layer < this.numberOfLayers; ++layer) {
            Canvas toAddCanvas = new Canvas(this.width*TILE_SIZE, this.height*TILE_SIZE);
            GraphicsContext gC = toAddCanvas.getGraphicsContext2D();
            for(int y = 0; y < this.height; ++y) {
                for(int x = 0; x < this.width; ++x) {
                    Color tileFromMap = map.getTileColor(x, y, layer);
                    gC.setFill(tileFromMap);
                    gC.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
            layersCanvas.add(toAddCanvas);
        }
        agentsCanvas = new Canvas(width*TILE_SIZE, height*TILE_SIZE);
    }

    private void setLayerListChoiceBox() {
        for(int layer = 1; layer <= this.numberOfLayers; ++layer) {
            layerListChoiceBox.getItems().add(layer + ". layer");
        }
        layerListChoiceBox.getSelectionModel().selectFirst();
        layerListChoiceBox.setMinWidth(100);
        layersCanvas.get(0).toFront();
        layerListChoiceBox.setOnAction(event -> {
            actualNumLayer = layerListChoiceBox.getSelectionModel().getSelectedIndex();
            layersCanvas.get(actualNumLayer).toFront();
        });
    }

    private void setGridPane() {
        for (Canvas layer : layersCanvas) {
            gridPane.add(layer, 0, 0, 6, 6);
        }
        gridPane.add(agentsCanvas, 0, 0, 6, 6);
        gridPane.add(layerListChoiceBox, 6, 0 , 1, 1);
        gridPane.add(agentCheckbox, 6, 1, 1, 1);
        gridPane.add(iterationButton, 6, 2, 1, 1);
        gridPane.add(backButton, 6, 5, 1, 1);
    }

    private void setBackButton(Stage primaryStage) {
        backButton = new Button("Back");
        backButton.setOnAction(event -> {
            new MainWindow(primaryStage);
        });
    }

    private void setAgentCheckBox() {
        agentCheckbox = new CheckBox("Add Agent");
        agentCheckbox.setSelected(addingAgents);
        agentCheckbox.setOnAction(event -> {
            addingAgents = agentCheckbox.isSelected();
        });
        agentsCanvas.setOnMousePressed(event -> {
            if(addingAgents) {
                agentList.add(new Agent((int) event.getX()/TILE_SIZE, (int) event.getY()/TILE_SIZE));
            }
        });
    }

    private void setIterationButton() {
        iterationButton = new Button("Iterate");
        iterationButton.setOnAction(event -> {
            for(int i = 0; i < 10; ++i) {
                iteration();
            }
        });
    }

    private void iteration() {
        agentsCanvas.getGraphicsContext2D().clearRect(0,0,agentsCanvas.getWidth(), agentsCanvas.getHeight());
        for(Agent agent : agentList) {
            Point toGo = agent.dumbMove(new Point(5, 5));
            StaticPoint toGoPoint = map.get(toGo.x, toGo.y, actualNumLayer);
            if(agent.tryToMove(toGoPoint)) {
                GraphicsContext gC = agentsCanvas.getGraphicsContext2D();
                gC.setFill(AGENT_COLOR);
                int x = agent.getActualPosition().x;
                int y = agent.getActualPosition().y;
                gC.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        agentsCanvas.toFront();
    }

    @Override
    public void start(Stage primaryStage) {
        initVar();
        drawCanvas();
        setLayerListChoiceBox();
        setBackButton(primaryStage);
        setAgentCheckBox();
        setIterationButton();
        setGridPane();
        int first = 0;
        layersCanvas.get(first).toFront();
        agentsCanvas.toFront();
        setPrimaryStage(primaryStage);
    }

    private StaticPoint checker(int x, int y, Color tileColor) {
        if(tileColor.equals(WALL_COLOR)) return new Wall(x, y);
        else if(tileColor.equals(ROAD_COLOR)) return new Road(x, y);
        else if(tileColor.equals(FLOOR_COLOR)) return new Floor(x, y);
        else if(tileColor.equals(WINDOW_COLOR)) return new Window(x, y);
        else if(tileColor.equals(DOOR_COLOR)) return new Door(x, y);
        else if(tileColor.equals(FURNITURE_COLOR)) return new Furniture(x, y);
        else if(tileColor.equals(UPSTAIRS_COLOR)) return new Upstairs(x, y);
        else if(tileColor.equals(DOWNSTAIRS_COLOR)) return new Downstairs(x, y);
        else if(tileColor.equals(SAFE_ZONE_COLOR)) return new SafeZone(x, y);
        else return new Lawn(x, y);
    }

    private void addAllPointToMap(Image layerImage, int layer) {
        PixelReader pR = layerImage.getPixelReader();
        for(int y = 0; y < this.height; ++y) {
            for(int x = 0; x < this.width; ++x) {
                Color tileColor = pR.getColor(x, y);
                StaticPoint toAdd = checker(x, y, tileColor);
                map.add(x, y, layer, toAdd);
            }
        }
    }

    private void initMap(Image layerImage) {
        this.width = (int) layerImage.getWidth();
        this.height = (int) layerImage.getHeight();
        map = new CityMap(this.width, this.height, this.numberOfLayers);
    }

    private void setupMap(File directory) {
        File[] layersFile = directory.listFiles();
        this.numberOfLayers = layersFile.length;
        for(int layer = 0; layer < this.numberOfLayers; ++layer) {
            Image layerImage = new Image(layersFile[layer].toURI().toString());
            if(layer == 0) {
                initMap(layerImage);
            }
            addAllPointToMap(layerImage, layer);
        }
    }

    public Evacuation(Stage primaryStage, File directory) {
        setupMap(directory);
        start(primaryStage);
    }
}
