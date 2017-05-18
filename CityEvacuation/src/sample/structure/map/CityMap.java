package sample.structure.map;

import javafx.scene.paint.Color;
import sample.structure.logic.StaticPoint;
import sample.structure.logic.TileColors;
import sample.structure.logic.Weight;

import java.io.Serializable;

public class CityMap {
    private StaticPoint[][][] cityMap;
    boolean visitedPoints[][] = new boolean[50][50];

    public void addPoint(int layers, StaticPoint point) {
        cityMap[layers][point.getX()][point.getY()] = point;
    }

    public StaticPoint getPoint(int x, int y, int layers) {
        if(x < 0 || y < 0 || layers < 0) {
            throw new IllegalArgumentException("In CityMap while adding element size was wrong");
        } else {
            return cityMap[layers][x][y];
        }
    }

    public StaticPoint[][][] getMap() {
        return cityMap;
    }

    public int getNumberOfLayers() {
        return cityMap.length;
    }

    public int getHeight() {
        return cityMap[0].length;
    }

    public int getWidth() {
        return cityMap[0][0].length;
    }

    public int[] getSize() {
        return new int[]{getWidth(), getHeight(), getNumberOfLayers()};
    }

    public Color getPointColor(int x, int y, int layer) {
        return cityMap[layer][x][y].getTileColor();
    }


    private void setCityMap(int width, int height, int layers) {
        if(width < 0 || height < 0 || layers < 0) {
            throw new IllegalArgumentException("In CityMap while setting map size, size was wrong");
        } else {
            cityMap = new StaticPoint[layers][width][height];
        }
    }

    public void clean(){
        for(int i = 0; i< 50; i++)
            for(int j = 0; j < 50; j++)
        visitedPoints[i][j] = false;
    }
    public void weightFactor(StaticPoint[][][] map, int i, int j,float weight, Color color){
        map[0][i][j].weight=weight;
        visitedPoints[i][j] = true;
        if (i-1 >= 0){
            if(map[0][i-1][j].getTileColor() == color && map[0][i-1][j].weight >= weight && !visitedPoints[i-1][j]){
                weightFactor(map, i-1, j, weight + 0.1f, color);
            }
        }
        if (i+1 < 50){
            if(map[0][i+1][j].getTileColor() == color && map[0][i+1][j].weight >= weight && !visitedPoints[i+1][j])
                weightFactor(map, i+1, j, weight + 0.1f, color);
        }
        if (j-1 >= 0) {
            if (map[0][i][j - 1].getTileColor() == color && map[0][i][j - 1].weight >= weight && !visitedPoints[i][j-1])
               weightFactor(map, i, j - 1, weight + 0.1f, color);
        }
        if (j+1 < 50) {
            if (map[0][i][j + 1].getTileColor() == color && map[0][i][j + 1].weight >= weight && !visitedPoints[i][j+1])
                weightFactor(map, i, j + 1, weight + 0.1f, color);
        }

    }

    public int gowno(StaticPoint[][][] map, int i, int j,float weight, Color color){
        map[0][i][j].weight=weight;
        if(i <= 0 || i > 97 || j <= 0 || j > 39){
            return 1;
        }
        if ((map[0][i-1][j].weight >= weight && map[0][i+1][j].weight >= weight  &&map[0][i][j-1].weight >= weight && map[0][i][j+1].weight >= weight) && color != TileColors.WALL_COLOR)
            return gowno(map, i-1, j, weight + 0.1f, color) + gowno(map, i+1, j, weight + 0.1f, color) +
                    gowno(map, i, j - 1, weight + 0.1f, color) + gowno(map, i, j + 1, weight + 0.1f, color);
        return 1;
    }
    public void weightInit(CityMap cityMap) {
        StaticPoint[][][] map = cityMap.getMap();
        for (int i = 0; i < cityMap.getHeight(); i++) {
            for (int j = 0; j < cityMap.getWidth(); j++) {
                if (map[0][i][j].getTileColor() == TileColors.SAFE_ZONE_COLOR) {
                    clean();
                    weightFactor(map, i, j, Weight.safeZone, TileColors.LAWN_COLOR);
                }
                if (map[0][i][j].getTileColor() == TileColors.WINDOW_CLOSE_COLOR) {
                    clean();
                    weightFactor(map, i, j, Weight.windows, TileColors.FLOOR_COLOR);
                }
                if (map[0][i][j].getTileColor() == TileColors.DOOR_CLOSE_COLOR) {
                    clean();
                    weightFactor(map, i, j, Weight.door, TileColors.FLOOR_COLOR);
                }
            }
        }
    }
    public CityMap(int width, int height, int layers) {
        setCityMap(width, height, layers);
    }
}
