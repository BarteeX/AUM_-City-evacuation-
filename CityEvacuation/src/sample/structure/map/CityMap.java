package sample.structure.map;

import sample.structure.points.StaticPoint;

public class CityMap {
    private StaticPoint[][][] cityMap;

    public void set(int x, int y, int z, StaticPoint point) {
        if(x < 0 || y < 0 || z < 0) {
            throw new IllegalArgumentException("In CityMap while adding element size was wrong");
        } else {
            cityMap[x][y][z] = point;
        }
    }

    public void setCityMap(int width, int height, int layers) {
        if(width < 0 || height < 0 || layers < 0) {
            throw new IllegalArgumentException("In CityMap while setting map size, size was wrong");
        } else {
            cityMap = new StaticPoint[width][height][layers];
        }
    }

    public CityMap(int width, int height, int layers) {
        setCityMap(width, height, layers);
    }
}
