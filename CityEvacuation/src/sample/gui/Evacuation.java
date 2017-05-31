package sample.gui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private Slider speedSlider;
    private List<Canvas> layersCanvasList;
    private List<Canvas> agentsCanvasList;
    private CheckBox agentCheckbox;
    private ChoiceBox<String> layerListChoiceBox;
    private CityMap map;
    private Fire fire;
    private static final int FINISH_BONUS = 1000;
    private static final int ITERATION_PUNISHMENT = -1;
    private List<Agent> deadAgents = new ArrayList<>();
    private List<Agent> winners = new ArrayList<>();
    private boolean isRestart = false;
    private AnimationTimer aT;
    private int iterationCounter = 1;
    private File directory;
    private int evacuated, died;
    private float statistic;

    private XYChart.Series series;
    private LineChart<Number, Number> lineChart;

    private Label iterationLabel;
    private Label winnerLabel;
    private Label losesLabel;
    private Label evacuatedPercent;
    private Label iterationCounterLabel;
    private Label winnerCounterLabel;
    private Label losesCounterLabel;
    private Label evacuatedCounterPercent;


    private int width, height, numberOfLayers, actualNumLayer;
    private boolean addingAgents, animationStarted = false;
    private List<List<Agent>> agentList;

    private void initVar() {
        gridPane = new GridPane();
        layersCanvasList = new ArrayList<>();
        agentsCanvasList = new ArrayList<>();
        layerListChoiceBox = new ChoiceBox<>();
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
        primaryStage.setWidth(this.width*TILE_SIZE + 280);
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

    private void setSpeedSlider() {
        speedSlider = new Slider(0, 2000, 20);
        speedSlider.setPrefWidth(80);
        speedSlider.setValue(0);
    }

    private void setStatisticLabels() {
        iterationLabel = new Label("Iteration: ");
        winnerLabel = new Label("Evacuated: ");
        losesLabel = new Label("Died: ");
        evacuatedPercent = new Label("Efficiency: ");

        iterationCounterLabel = new Label(iterationCounter + "");
        winnerCounterLabel = new Label(evacuated + "");
        losesCounterLabel = new Label(died + "");
        evacuatedCounterPercent = new Label((float)((int)(statistic*100))/100 + "%");
    }

    private void setLineChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Iteration");
        lineChart = new LineChart<>(xAxis, yAxis);
        series = new XYChart.Series();
        lineChart.setMaxSize(100, 100);
    }

    private void updateLineChart() {
        series.getData().add(new XYChart.Data<>(iterationCounter, statistic));
        lineChart.getData().add(series);
    }

    private GridPane statisticGrid() {
        GridPane statisticGrid = new GridPane();

        statisticGrid.add(iterationLabel, 0 ,0);
        statisticGrid.add(iterationCounterLabel, 1 ,0);
        statisticGrid.add(winnerLabel, 0 ,1);
        statisticGrid.add(winnerCounterLabel, 1 ,1);
        statisticGrid.add(losesLabel, 0 ,2);
        statisticGrid.add(losesCounterLabel, 1 ,2);
        statisticGrid.add(evacuatedPercent, 0 ,3);
        statisticGrid.add(evacuatedCounterPercent, 1 ,3);
        statisticGrid.add(lineChart, 0, 4, 2, 4);
        statisticGrid.setAlignment(Pos.CENTER);
        statisticGrid.setPadding(new Insets(15, 15, 15, 15));
        statisticGrid.setHgap(5);
        statisticGrid.setVgap(5);
        statisticGrid.setStyle("-fx-border-color: black; -fx-border-radius: 2; -fx-font-size : 15px;");

        return statisticGrid;
    }

    private GridPane rightBar() {
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Adding: "), 0, 0);
        gridPane.add(agentCheckbox, 1, 0 );
        gridPane.add(new Label("Choose: "), 0, 1);
        gridPane.add(layerListChoiceBox, 1, 1);
        gridPane.add(iterationButton, 0, 2);
        gridPane.add(backButton, 1, 2);
        gridPane.add(new Label("Speed: "), 0, 3);
        gridPane.add(speedSlider, 1, 3);

        gridPane.add(new Label("Legend"), 0, 5, 2, 1);
        gridPane.add(new Label("Wall: "), 0, 6);
        gridPane.add(new Rectangle(20, 20, WALL_COLOR), 1, 6);
        gridPane.add(new Label("Lawn: "), 0, 7);
        gridPane.add(new Rectangle(20, 20, LAWN_COLOR), 1, 7);
        gridPane.add(new Label("Floor: "), 0, 8);
        gridPane.add(new Rectangle(20, 20, FLOOR_COLOR), 1, 8);
        gridPane.add(new Label("Window(close): "), 0, 9);
        gridPane.add(new Rectangle(20, 20, WINDOW_CLOSE_COLOR), 1, 9);
        gridPane.add(new Label("Window(open): "), 0, 10);
        gridPane.add(new Rectangle(20, 20, WINDOW_OPEN_COLOR), 1, 10);
        gridPane.add(new Label("Door(open): "), 0, 11);
        gridPane.add(new Rectangle(20, 20, DOOR_OPEN_COLOR), 1, 11);
        gridPane.add(new Label("Door(close): "), 0, 12);
        gridPane.add(new Rectangle(20, 20, DOOR_CLOSE_COLOR), 1, 12);
        gridPane.add(new Label("SafeZone: "), 0, 13);
        gridPane.add(new Rectangle(20, 20, SAFE_ZONE_COLOR), 1, 13);
        gridPane.add(statisticGrid(), 0, 14, 2, 4);

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
        backButton.setOnAction(event -> {
            aT.stop();
            new MainWindow(primaryStage);
        });
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

    private void updateLabels() {
        iterationCounterLabel.setText(iterationCounter + "");
        winnerCounterLabel.setText(evacuated + "");
        losesCounterLabel.setText(died + "");
        evacuatedCounterPercent.setText(((int)statistic*100)/100 + "%");
    }

    private void setIterationButton() {
        iterationButton = new Button("Iterate");
        aT = new AnimationTimer() {
            @Override
            public void handle(long now) {
                iteration();
                redrawCanvas(actualNumLayer);
                updateLabels();
                try {
                    if(winners.size() + deadAgents.size() == 80 && !isRestart){   //If 80 agents are dead or won, restart evacuation calling method
                        isRestart = true;
                        updateLineChart();
                        restart(directory);
                        Thread.sleep(100);
                        isRestart = false;
                        iterationCounter++;
                    }
                    Thread.sleep(2000 - ((int) speedSlider.getValue()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        iterationButton.setOnAction(event -> {
            if(animationStarted) {
                aT.stop();
                animationStarted = false;
            } else {
                aT.start();
                animationStarted = true;
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
        evacuated = winners.size();
        died = deadAgents.size();
        statistic = ((float)evacuated /(evacuated + (float)died) * 100f);

        fire.fireUpdate(map);
        int index = 999;
        int index2 = 999;
        for(Canvas agentsCanvas : agentsCanvasList) {
            agentsCanvas.getGraphicsContext2D().clearRect(0,0, agentsCanvas.getWidth(), agentsCanvas.getHeight());
            for(List<Agent> agents : agentList) {
                for(Agent agent : agents) {
                    Point lookingAt = agent.movementAlgorithm(
                            map.getMap(),
                            map.getMapOfWindows(),
                            map.getMapOfDoors(),
                            map.getMapOfSafeZones(),
                            map.getMapOfSmoke(),
                            map.getMapOfFire(),
                            map.getMapOfAgents()
                    );
                    int layerIterator = agentsCanvasList.indexOf(agentsCanvas);
                    map.addPoint(
                            agentsCanvasList.indexOf(agentsCanvas),
                            map.getPoint(
                                    lookingAt.x,
                                    lookingAt.y,
                                    layerIterator
                            )
                    );
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
        setStatisticLabels();
        setSpeedSlider();
        setLineChart();
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
            }while(
                    map.getPoint(x, y, actualNumLayer).getTileColor() == TileColors.SAFE_ZONE_COLOR ||
                            map.getPoint(x, y, actualNumLayer).getTileColor() == TileColors.LAWN_COLOR ||
                            !map.getPoint(x, y, actualNumLayer).getPossibleActions().contains(WALK_IN)
                    );
            agentList.get(actualNumLayer).add(new Agent(x, y, map.getSize()));
        }
        iteration();
    }

    private void restart(File directory){
        setupMap(directory);
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
            }while(
                    map.getPoint(x, y, actualNumLayer).getTileColor() == TileColors.SAFE_ZONE_COLOR ||
                    map.getPoint(x, y, actualNumLayer).getTileColor() == TileColors.LAWN_COLOR ||
                    !map.getPoint(x, y, actualNumLayer).getPossibleActions().contains(WALK_IN)
                    );
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
            if(i != 0 && i % 4 == 0)
                yy+=2;
        }
        winners = new ArrayList<>();
        deadAgents = new ArrayList<>();
        iteration();
    }
}
