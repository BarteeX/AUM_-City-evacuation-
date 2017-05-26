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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.structure.logic.*;
import sample.structure.map.CityMap;
import sample.structure.map.Fire;
import sample.structure.points.impenetrable.Furniture;
import sample.structure.points.impenetrable.Wall;
import sample.structure.points.permeable.*;
import sample.structure.points.permeable.Window;


import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private Fire fire;
    private static final int FINISH_BONUS = 1000;
    private static final int ITERATION_PUNISHMENT = -1;
    private List<Agent> deadAgents = new ArrayList<>();
    private List<Agent> winners = new ArrayList<>();

    private File directory;
    private Stage primaryStage;

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
        fire = new Fire(10);
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
        gridPane.add(new Label("Adding: "), 0, 0);
        gridPane.add(agentCheckbox, 1, 0 );
        gridPane.add(new Label("Choose: "), 0, 1);
        gridPane.add(layerListChoiceBox, 1, 1);
        gridPane.add(iterationButton, 0, 2);
        gridPane.add(backButton, 1, 2);
        gridPane.add(new Label("Legend"), 0, 3, 2, 1);
        gridPane.add(new Label("Wall: "), 0, 4);
        gridPane.add(new Rectangle(20, 20, WALL_COLOR), 1, 4);
        gridPane.add(new Label("Road: "), 0, 5);
        gridPane.add(new Rectangle(20, 20, ROAD_COLOR), 1, 5);
        gridPane.add(new Label("Floor: "), 0, 6);
        gridPane.add(new Rectangle(20, 20, FLOOR_COLOR), 1, 6);
        gridPane.add(new Label("Window(close): "), 0, 7);
        gridPane.add(new Rectangle(20, 20, WINDOW_CLOSE_COLOR), 1, 7);
        gridPane.add(new Label("Window(open): "), 0, 8);
        gridPane.add(new Rectangle(20, 20, WINDOW_OPEN_COLOR), 1, 8);
        gridPane.add(new Label("Door(open): "), 0, 9);
        gridPane.add(new Rectangle(20, 20, DOOR_OPEN_COLOR), 1, 9);
        gridPane.add(new Label("Door(close): "), 0, 10);
        gridPane.add(new Rectangle(20, 20, DOOR_CLOSE_COLOR), 1, 10);
        gridPane.add(new Label("Furniture: "), 0, 11);
        gridPane.add(new Rectangle(20, 20, FURNITURE_COLOR), 1, 11);
        gridPane.add(new Label("Upstairs: "), 0, 12);
        gridPane.add(new Rectangle(20, 20, UPSTAIRS_COLOR), 1, 12);
        gridPane.add(new Label("Downstairs: "), 0, 13);
        gridPane.add(new Rectangle(20, 20, DOWNSTAIRS_COLOR), 1, 13);
        gridPane.add(new Label("SafeZone: "), 0, 14);
        gridPane.add(new Rectangle(20, 20, SAFE_ZONE_COLOR), 1, 14);
        gridPane.setAlignment(Pos.CENTER);
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
                GraphicsContext gC = agentsCanvas.getGraphicsContext2D();
                int x = (int) event.getX()/TILE_SIZE;
                int y = (int) event.getY()/TILE_SIZE;
                if(addingAgents && event.isPrimaryButtonDown()) {
                    gC.setFill(AGENT_COLOR);
                    if(map.getPoint(x, y, actualNumLayer).getPossibleActions().contains(WALK_IN)) {
                        gC.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        agentList.get(actualNumLayer).add(new Agent(x, y, map.getSize()));
                    }
                }else if (event.isSecondaryButtonDown()) {
                    gC.clearRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    List<Agent> agentsLayerList = agentList.get(actualNumLayer);
                    for(int i = 0; i < agentsLayerList.size(); ++i) {
                        if(agentsLayerList.get(i).getActualPosition().equals(new Point(x, y))) {
                            agentsLayerList.remove(i);
                        }
                    }
                }
            });
        }
    }

    private void setIterationButton(Stage primaryStage) {
        iterationButton = new Button("Iterate");
        iterationButton.setOnKeyPressed(event -> {
            for(int i = 0; i < 1; ++i) {
                iteration();
            }

            if(winners.size() + deadAgents.size() == 80){   //If 80 agents are dead or won, restart evacuation calling method
                restart(primaryStage, directory);
            }
        });
    }

    private void redrawCanvas(int layer) {
        GraphicsContext gC = layersCanvasList.get(layer).getGraphicsContext2D();
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                Color paintingColor = map.getPointColor(x, y, layer);
                gC.setFill(paintingColor);
                gC.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void iteration() { // need to another way
        fire.fireUpdate(map);
        redrawCanvas(0);
        int index = 999;
        int index2 = 999;
        for(Canvas agentsCanvas : agentsCanvasList) {
            agentsCanvas.getGraphicsContext2D().clearRect(0,0, agentsCanvas.getWidth(), agentsCanvas.getHeight());
            for(List<Agent> agents : agentList) {
                for(Agent agent : agents) {
                    Point lookingAt = agent.movementAlgorithm(map.getMap(), map.getMapOfWindows(), map.getMapOfDoors(), map.getMapOfSafeZones(), map.getMapOfSmoke(), map.getMapOfFire(), map.getMapOfAgents());
                    int layerIterator = agentsCanvasList.indexOf(agentsCanvas);
                    map.addPoint(agentsCanvasList.indexOf(agentsCanvas), map.getPoint(lookingAt.x, lookingAt.y, layerIterator));
                    redrawCanvas(layerIterator);
                    agentsCanvas.getGraphicsContext2D().setFill(AGENT_COLOR);
                    agentsCanvas.getGraphicsContext2D()
                            .fillRect(
                                    agent.getActualPosition().x * TILE_SIZE,
                                    agent.getActualPosition().y * TILE_SIZE,
                                    TILE_SIZE,
                                    TILE_SIZE
                            );
                    agent.setScore(agent.getScore()+ITERATION_PUNISHMENT);
                    if(agent.finished){
                        index = agents.indexOf(agent);
                        agents.get(index).setScore(agents.get(index).getScore()+FINISH_BONUS);
                    }
                    if(agent.health < 0){
                        index2 = agents.indexOf(agent);
                    }
                }
                if(index != 999){
                    map.mapOfAgents[agents.get(index).actualPosition.x][agents.get(index).actualPosition.y] = false;
                    winners.add(agents.get(index));
                    agents.remove(index);
                }
                if(index2 != 999){
                    map.mapOfAgents[agents.get(index2).actualPosition.x][agents.get(index2).actualPosition.y] = false;
                    deadAgents.add(agents.get(index2));
                    agents.remove(index2);
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
        setIterationButton(primaryStage);
        setGridPane();
        int first = 0;
        layersCanvasList.get(first).toFront();
        agentsCanvasList.get(first).toFront();
        setPrimaryStage(primaryStage);
    }

    private StaticPoint checker(int x, int y, Color tileColor, CityMap map) {
        if(tileColor.equals(WALL_COLOR)) return new Wall(x, y, map);
        else if(tileColor.equals(ROAD_COLOR)) return new Road(x, y, map);
        else if(tileColor.equals(FLOOR_COLOR)) return new Floor(x, y, map);
        else if(tileColor.equals(WINDOW_CLOSE_COLOR)) return new Window(x, y, map);
        else if(tileColor.equals(DOOR_CLOSE_COLOR)) return new Door(x, y, map);
        else if(tileColor.equals(FURNITURE_COLOR)) return new Furniture(x, y, map);
        else if(tileColor.equals(UPSTAIRS_COLOR)) return new Upstairs(x, y, map);
        else if(tileColor.equals(DOWNSTAIRS_COLOR)) return new Downstairs(x, y, map);
        else if(tileColor.equals(SAFE_ZONE_COLOR)) return new SafeZone(x, y, map);
        else if(tileColor.equals(FLAME_COLOR)) return new Flame(x, y, map, map.getMapOfFire());
        else if(tileColor.equals(SMOKE_COLOR)) return new Flame(x, y, map, map.getMapOfFire());
        else return new Lawn(x, y, map);
    }

    private void addAllPointToMap(Image layerImage, int layer) {
        PixelReader pR = layerImage.getPixelReader();
        for(int y = 0; y < this.height; ++y) {
            for(int x = 0; x < this.width; ++x) {
                Color tileColor = pR.getColor(x, y);
                StaticPoint toAdd = checker(x, y, tileColor, map);
                map.addPoint(layer, toAdd);
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

    public Evacuation(Stage primaryStage, File directory){
        this.primaryStage = primaryStage;
        this.directory = directory;
        setupMap(directory);
        start(primaryStage);
        map.weightInit(map);
        Random rand = new Random();
        int x;
        int y;
        for(int i = 0; i < 80;i ++){
            do{
                x = rand.nextInt(100 - 2) + 1;
                y = rand.nextInt(100 - 2) + 1;
            }while(map.getPoint(x, y, actualNumLayer).getTileColor() == TileColors.SAFE_ZONE_COLOR ||map.getPoint(x, y, actualNumLayer).getTileColor() == TileColors.LAWN_COLOR || !map.getPoint(x, y, actualNumLayer).getPossibleActions().contains(WALK_IN));
            agentList.get(actualNumLayer).add(new Agent(x, y, map.getSize()));
        }
        iteration();
    }

    public void restart(Stage primaryStage, File directory){
        System.out.println("Winners: " + winners.size());
        System.out.println("Losers: " + deadAgents.size());
        System.out.print("Survived: ");
        System.out.printf("%.2f", (float)((float)winners.size() / (float)(winners.size() + (float)deadAgents.size()) * 100f));
        System.out.println("%");
        setupMap(directory);
        start(primaryStage);
        map.weightInit(map);
        Random rand = new Random();
        int x;
        int y;
        //GENES
        for(int i = deadAgents.size()-1; i > 0; i--)
            winners.add(deadAgents.get(i));
        int yy = 1;
        for(int i = 0; i < 80; i ++){
            do{
                x = rand.nextInt(100 - 2) + 1;
                y = rand.nextInt(100 - 2) + 1;
            }while(map.getPoint(x, y, actualNumLayer).getTileColor() == TileColors.SAFE_ZONE_COLOR ||map.getPoint(x, y, actualNumLayer).getTileColor() == TileColors.LAWN_COLOR || !map.getPoint(x, y, actualNumLayer).getPossibleActions().contains(WALK_IN));
            int MUTATION_CHANCE = 50;
            Genotype genotype = new Genotype();
            Random random = new Random();
            int gene;
            int mutation;
            gene = random.nextInt(4);
            mutation = random.nextInt(MUTATION_CHANCE);
            switch(gene){
                case 0:
                    genotype.calm = winners.get(yy).getGenotype().calm;
                    break;
                case 1:
                    genotype.calm = winners.get((yy) + 1).getGenotype().calm;
                    break;
                default:
                    genotype.calm = (winners.get(yy).getGenotype().calm + winners.get((yy) + 1).getGenotype().calm) / 2;
                    break;
            }
            if(mutation == gene)
                genotype.calm = random.nextFloat();

            gene = random.nextInt(4);
            mutation = random.nextInt(MUTATION_CHANCE);
            switch(gene){
                case 0:
                    genotype.stamina = winners.get(yy).getGenotype().stamina;
                    break;
                case 1:
                    genotype.stamina = winners.get((yy) + 1).getGenotype().stamina;
                    break;
                default:
                    genotype.stamina = (winners.get(yy).getGenotype().stamina + winners.get((yy) + 1).getGenotype().stamina) / 2;
                    break;
            }
            if(mutation == gene)
                genotype.stamina = random.nextFloat();

            gene = random.nextInt(4);
            mutation = random.nextInt(MUTATION_CHANCE);
            switch(gene){
                case 0:
                    genotype.iLikeWindows = winners.get(yy).getGenotype().iLikeWindows;
                    break;
                case 1:
                    genotype.iLikeWindows = winners.get((yy) + 1).getGenotype().iLikeWindows;
                    break;
                default:
                    genotype.iLikeWindows = (winners.get(yy).getGenotype().iLikeWindows + winners.get((yy) + 1).getGenotype().iLikeWindows) / 2;
                    break;
            }
            if(mutation == gene)
                genotype.iLikeWindows = random.nextFloat();

            gene = random.nextInt(4);
            mutation = random.nextInt(MUTATION_CHANCE);
            switch(gene){
                case 0:
                    genotype.iLikeDoors = winners.get(yy).getGenotype().iLikeDoors;
                    break;
                case 1:
                    genotype.iLikeDoors = winners.get((yy) + 1).getGenotype().iLikeDoors;
                    break;
                default:
                    genotype.iLikeDoors = (winners.get(yy).getGenotype().iLikeDoors + winners.get((yy) + 1).getGenotype().iLikeDoors) / 2;
                    break;
            }
            if(mutation == gene)
                genotype.iLikeDoors = random.nextFloat();

            gene = random.nextInt(4);
            mutation = random.nextInt(MUTATION_CHANCE);
            switch(gene){
                case 0:
                    genotype.iLikeSafeZone = winners.get(yy).getGenotype().iLikeSafeZone;
                    break;
                case 1:
                    genotype.iLikeSafeZone = winners.get((yy) + 1).getGenotype().iLikeSafeZone;
                    break;
                default:
                    genotype.iLikeSafeZone = (winners.get(yy).getGenotype().iLikeSafeZone + winners.get((yy) + 1).getGenotype().iLikeSafeZone) / 2;
                    break;
            }
            if(mutation == gene)
                genotype.iLikeSafeZone = random.nextFloat();

            gene = random.nextInt(4);
            mutation = random.nextInt(MUTATION_CHANCE);
            switch(gene){
                case 0:
                    genotype.iDontLikeFire = winners.get(yy).getGenotype().iDontLikeFire;
                    break;
                case 1:
                    genotype.iDontLikeFire = winners.get((yy) + 1).getGenotype().iDontLikeFire;
                    break;
                default:
                    genotype.iDontLikeFire = (winners.get(yy).getGenotype().iDontLikeFire + winners.get((yy) + 1).getGenotype().iDontLikeFire) / 2;
                    break;
            }
            if(mutation == gene)
                genotype.iDontLikeFire = random.nextFloat();

            gene = random.nextInt(4);
            mutation = random.nextInt(MUTATION_CHANCE);
            switch(gene){
                case 0:
                    genotype.iDontLikeSmoke = winners.get(yy).getGenotype().iDontLikeSmoke;
                    break;
                case 1:
                    genotype.iDontLikeSmoke = winners.get((yy) + 1).getGenotype().iDontLikeSmoke;
                    break;
                default:
                    genotype.iDontLikeSmoke = (winners.get(yy).getGenotype().iDontLikeSmoke + winners.get((yy) + 1).getGenotype().iDontLikeSmoke) / 2;
                    break;
            }
            if(mutation == gene)
                genotype.iDontLikeSmoke = random.nextFloat();

            gene = random.nextInt(4);
            mutation = random.nextInt(MUTATION_CHANCE);
            switch(gene){
                case 0:
                    genotype.laziness = winners.get(yy).getGenotype().laziness;
                    break;
                case 1:
                    genotype.laziness = winners.get((yy) + 1).getGenotype().laziness;
                    break;
                default:
                    genotype.laziness = (winners.get(yy).getGenotype().laziness + winners.get((yy) + 1).getGenotype().laziness) / 2;
                    break;
            }
            if(mutation == gene)
                genotype.laziness = random.nextFloat();

            agentList.get(actualNumLayer).add(new Agent(x, y, map.getSize(), genotype));
            if(i != 0 && i % 3 == 0)
                yy+=2;
        }
        winners = new ArrayList<>();
        deadAgents = new ArrayList<>();
        iteration();
    }
}
