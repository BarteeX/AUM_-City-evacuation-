package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("City Evacuation");
        Group root = new Group();
        Canvas canvas = new Canvas(300,500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);

        Element []tab = new Element[] {
                new Element(Color.BLACK), // sciana     - 0
                new Element(Color.GREEN), // drzwi      - 1
                new Element(Color.LIGHTGRAY),  // podloga    - 2
                new Element(Color.RED),   // ogien      - 3
                new Element(Color.DARKGREY)//dym        - 4
        };

        int width = 17, length = 13;
        int sizePixel = 15;

        Floor floor = new Floor(true, width, length );

        floor.loadFromFile("ImgsFloors\\firstFloor.bmp");

        for(int y = 0; y < length; ++y) {
            for(int x = 0; x < width; ++x) {
                gc.setFill(floor.get(x, y).getColor());
                gc.fillRect(x* sizePixel,y * sizePixel, sizePixel, sizePixel);
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
