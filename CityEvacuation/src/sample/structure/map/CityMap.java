package sample.structure.map;

import javafx.scene.paint.Color;
import sample.structure.points.StaticPoint;

public class CityMap {
    private StaticPoint[][][] cityMap;

    public void add(int x, int y, int layers, StaticPoint point) {
        if(x < 0 || y < 0 || layers < 0) {
            throw new IllegalArgumentException("In CityMap while adding element size was wrong");
        } else {
            cityMap[layers][y][x] = point;
        }
    }

    public StaticPoint get(int x, int y, int layers) {
        if(x < 0 || y < 0 || layers < 0) {
            throw new IllegalArgumentException("In CityMap while adding element size was wrong");
        } else {
            return cityMap[layers][y][x];
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

    public Color getTileColor(int x, int y, int layer) {
        return cityMap[layer][y][x].getTileColor();
    }

    public void setCityMap(int width, int height, int layers) {
        if(width < 0 || height < 0 || layers < 0) {
            throw new IllegalArgumentException("In CityMap while setting map size, size was wrong");
        } else {
            cityMap = new StaticPoint[layers][height][width];
        }
    }

    public CityMap(int width, int height, int layers) {
        setCityMap(width, height, layers);
    }
}
