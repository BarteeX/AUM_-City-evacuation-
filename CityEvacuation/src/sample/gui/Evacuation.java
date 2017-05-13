package sample.gui;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.structure.logic.Agent;
import sample.structure.logic.StaticPoint;
import sample.structure.map.CityMap;
import sample.structure.points.impenetrable.Furniture;
import sample.structure.points.impenetrable.Wall;
import sample.structure.points.permeable.*;
import sample.structure.points.permeable.Window;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static sample.structure.logic.ActionType.WALK_IN;
import static sample.structure.logic.TileColors.*;

public class Evacuation extends Application {
    private GridPane gridPane;
    private Button backButton;
    private Button iterationButton;
    private List<Canvas> layersCanvasList;
    private List<Canvas> agentsCanvasList;
    private CheckBox agentCheckbox;
    private ChoiceBox layerListChoiceBox;
    private CityMap map;

    private int width, height, numberOfLayers, actualNumLayer;
    private boolean addingAgents;
    private List<List<Agent>> agentList;

    private void initVar() {
        gridPane = new GridPane();
        layersCanvasList = new ArrayList<>();
        agentsCanvasList = new ArrayList<>();
        layerListChoiceBox = new ChoiceBox();
        agentList = new ArrayList<>();
        for(int layer = 0; layer < numberOfLayers; ++layer) {
            agentList.add(new ArrayList<>());
        }
    }

    private void setPrimaryStage(Stage primaryStage) {
        primaryStage.close();
        primaryStage.setTitle("Evacuation");
        primaryStage.show();
        primaryStage.setMinWidth(this.width*TILE_SIZE + 250);
        primaryStage.setMinHeight(this.height*TILE_SIZE + 60);
        primaryStage.setWidth(this.width*TILE_SIZE + 250);
        primaryStage.setHeight(this.height*TILE_SIZE + 60);
        primaryStage.setScene(new Scene(gridPane));
    }

    private void drawCanvas() {
        for(int layer = 0; layer < this.numberOfLayers; ++layer) {
            Canvas toAddCanvas = new Canvas(this.width*TILE_SIZE, this.height*TILE_SIZE);
            GraphicsContext gC = toAddCanvas.getGraphicsContext2D();
            for(int y = 0; y < this.height; ++y) {
                for(int x = 0; x < this.width; ++x) {
                    Color tileFromMap = map.getPointColor(x, y, layer);
                    gC.setFill(tileFromMap);
                    gC.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
            layersCanvasList.add(toAddCanvas);
            agentsCanvasList.add(new Canvas(width*TILE_SIZE, height*TILE_SIZE));
        }
    }

    private void setLayerListChoiceBox() {
        for(int layer = 1; layer <= this.numberOfLayers; ++layer) {
            layerListChoiceBox.getItems().add(layer + ". layer");
        }
        layerListChoiceBox.getSelectionModel().selectFirst();
        layerListChoiceBox.setMinWidth(100);
        layersCanvasList.get(0).toFront();
        layerListChoiceBox.setOnAction(event -> {
            actualNumLayer = layerListChoiceBox.getSelectionModel().getSelectedIndex();
            layersCanvasList.get(actualNumLayer).toFront();
            agentsCanvasList.get(actualNumLayer).toFront();
        });
    }

    private GridPane rightBar() {
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Adding: "), 6, 0);
        gridPane.add(agentCheckbox, 7, 0 );
        gridPane.add(new Label("Choose: "), 6, 1);
        gridPane.add(layerListChoiceBox, 7, 1);
        gridPane.add(iterationButton, 6, 2);
        gridPane.add(backButton, 7, 2);
        gridPane.setPadding(new Insets(15, 15, 15, 15));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setStyle("-fx-border-color: black; -fx-border-radius: 2;");
        return gridPane;
    }

    private void setGridPane() {
        for (Canvas layer : layersCanvasList) {
            gridPane.add(layer, 0, 0, 6, 6);
        }
        for (Canvas layer : agentsCanvasList) {
            gridPane.add(layer, 0, 0, 6, 6);
        }
        gridPane.add(rightBar(), 6, 0, 2, 6);
        gridPane.setBackground(new Background(new BackgroundFill(Color.SALMON, null, null)));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
    }

    private void setBackButton(Stage primaryStage) {
        backButton = new Button("Back");
        backButton.setOnAction(event -> new MainWindow(primaryStage));
    }

    private void setAgentCheckBox() {
        agentCheckbox = new CheckBox("Add Agent");
        agentCheckbox.setSelected(addingAgents);
        agentCheckbox.setOnAction(event -> addingAgents = agentCheckbox.isSelected());
        for(Canvas agentsCanvas : agentsCanvasList) {
            agentsCanvas.setOnMousePressed(event -> {
                if(addingAgents) {
                    agentsCanvas.getGraphicsContext2D().setFill(AGENT_COLOR);
                    int x = (int) event.getX()/TILE_SIZE;
                    int y = (int) event.getY()/TILE_SIZE;
                    if(map.getPoint(x, y, actualNumLayer).getPossibleActions().contains(WALK_IN)) {
                        agentsCanvas.getGraphicsContext2D().fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        agentList.get(actualNumLayer).add(new Agent(x, y));
                    }
                }
            });
        }
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
        for(Canvas agentsCanvas : agentsCanvasList) {
            agentsCanvas.getGraphicsContext2D().clearRect(0,0, agentsCanvas.getWidth(), agentsCanvas.getHeight());
            for(List<Agent> agents : agentList) {
                for(Agent agent : agents) {
                    Point toGo = agent.dumbMove(new Point(5, 5));
                    StaticPoint toGoPoint = map.getPoint(toGo.x, toGo.y, actualNumLayer);
                    if(agent.tryToMove(toGoPoint)) {
                        GraphicsContext gC = agentsCanvas.getGraphicsContext2D();
                        gC.setFill(AGENT_COLOR);
                        int x = agent.getActualPosition().x;
                        int y = agent.getActualPosition().y;
                        gC.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
            agentsCanvas.toFront();
        }

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
        layersCanvasList.get(first).toFront();
        agentsCanvasList.get(first).toFront();
        setPrimaryStage(primaryStage);
    }

    private StaticPoint checker(int x, int y, Color tileColor) {
        if(tileColor.equals(WALL_COLOR)) return new Wall(x, y);
        else if(tileColor.equals(ROAD_COLOR)) return new Road(x, y);
        else if(tileColor.equals(FLOOR_COLOR)) return new Floor(x, y);
        else if(tileColor.equals(WINDOW_CLOSE_COLOR)) return new Window(x, y);
        else if(tileColor.equals(DOOR_CLOSE_COLOR)) return new Door(x, y);
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
                map.addPoint(x, y, layer, toAdd);
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
        if(layersFile != null) {
            this.numberOfLayers = layersFile.length;
            for(int layer = 0; layer < this.numberOfLayers; ++layer) {
                Image layerImage = new Image(layersFile[layer].toURI().toString());
                if(layer == 0) {
                    initMap(layerImage);
                }
                addAllPointToMap(layerImage, layer);
            }
        }
    }

    public Evacuation(Stage primaryStage, File directory) {
        setupMap(directory);
        start(primaryStage);
    }
}
