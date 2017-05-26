package sample.structure.map;

import javafx.scene.paint.Color;
import sample.structure.logic.Genotype;
import sample.structure.logic.StaticPoint;
import sample.structure.logic.TileColors;
import sample.structure.logic.Weight;

import java.io.*;
import java.util.Scanner;

public class CityMap {
    private StaticPoint[][][] cityMap;
    static final int WIDTH = 100;
    static final int HEIGHT = 100;
    public float mapOfWindows[][] = new float[WIDTH][HEIGHT];
    public float mapOfDoors[][] = new float[WIDTH][HEIGHT];
    public float mapOfSafeZones[][] = new float[WIDTH][HEIGHT];
    public float mapOfSmoke[][] = new float[WIDTH][HEIGHT];
    public float mapOfFire[][] = new float[WIDTH][HEIGHT];
    public boolean mapOfAgents[][] = new boolean[WIDTH][HEIGHT];
    public void addPoint(int layers, StaticPoint point) {
        cityMap[layers][point.getX()][point.getY()] = point;
    }

    public StaticPoint getPoint(int x, int y, int layers){
        if(x < 0 || y < 0 || layers < 0) {
            throw new IllegalArgumentException("In CityMap while adding element size was wrong");
        } else {
            return cityMap[layers][x][y];
        }
    }

    public StaticPoint[][][] getMap() {
        return cityMap;
    }

    public float[][] getMapOfWindows(){
        return mapOfWindows;
    }

    public float[][] getMapOfDoors(){
        return mapOfDoors;
    }

    public float[][] getMapOfSmoke(){
        return mapOfSmoke;
    }

    public float[][] getMapOfFire(){
        return mapOfFire;
    }

    public float[][] getMapOfSafeZones(){
        return mapOfSafeZones;
    }

    public boolean[][] getMapOfAgents(){
        return mapOfAgents;
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

    public void weightFactor(StaticPoint[][][] map, int i, int j,float weight, Color color, float weightChange) {
        boolean possibleX1 = false;
        boolean possibleX2 = false;
        boolean possibleY1 = false;
        boolean possibleY2 = false;
        map[0][i][j].weight = weight;

        if(i-1 >= 0 && map[0][i-1][j].weight > weight && map[0][i-1][j].getTileColor() == color)
            possibleX1 = true;
        if(i+1 < WIDTH && map[0][i+1][j].weight > weight && map[0][i+1][j].getTileColor() == color)
            possibleX2 = true;
        if(j-1 >= 0 && map[0][i][j-1].weight > weight && map[0][i][j-1].getTileColor() == color)
            possibleY1 = true;
        if(j+1 < HEIGHT && map[0][i][j+1].weight > weight && map[0][i][j+1].getTileColor() == color)
            possibleY2 = true;

        if(possibleX1 && possibleX2 && possibleY1 && possibleY2){
            weightFactor(map, i-1, j, weight + weightChange, color, weightChange);
            weightFactor(map, i+1, j, weight + weightChange, color, weightChange);
            weightFactor(map, i, j-1, weight + weightChange, color, weightChange);
            weightFactor(map, i, j+1, weight + weightChange, color, weightChange);
        }else if(possibleX1 && possibleX2 && possibleY1){
            weightFactor(map, i-1, j, weight + weightChange, color, weightChange);
            weightFactor(map, i+1, j, weight + weightChange, color, weightChange);
            weightFactor(map, i, j-1, weight + weightChange, color, weightChange);
        }else if(possibleX1 && possibleX2 && possibleY2){
            weightFactor(map, i-1, j, weight+ weightChange, color, weightChange);
            weightFactor(map, i+1, j, weight+ weightChange, color, weightChange);
            weightFactor(map, i, j+1, weight+ weightChange, color, weightChange);
        }else if(possibleX1 && possibleY1 && possibleY2){
            weightFactor(map, i-1, j, weight+ weightChange, color, weightChange);
            weightFactor(map, i, j-1, weight+ weightChange, color, weightChange);
            weightFactor(map, i, j+1, weight+ weightChange, color, weightChange);
        }else if(possibleX2 && possibleY1 && possibleY2){
            weightFactor(map, i+1, j, weight+ weightChange, color, weightChange);
            weightFactor(map, i, j-1, weight+ weightChange, color, weightChange);
            weightFactor(map, i, j+1, weight+ weightChange, color, weightChange);
        }else if(possibleX1 && possibleX2){
            weightFactor(map, i-1, j, weight+ weightChange, color, weightChange);
            weightFactor(map, i+1, j, weight+ weightChange, color, weightChange);
        }else if(possibleX1 && possibleY1){
            weightFactor(map, i-1, j, weight+ weightChange, color, weightChange);
            weightFactor(map, i, j-1, weight+ weightChange, color, weightChange);
        }else if(possibleX1 && possibleY2){
            weightFactor(map, i-1, j, weight+ weightChange, color, weightChange);
            weightFactor(map, i, j+1, weight+ weightChange, color, weightChange);
        }else if(possibleX2 && possibleY1){
            weightFactor(map, i+1, j, weight+ weightChange, color, weightChange);
            weightFactor(map, i, j-1, weight+ weightChange, color, weightChange);
        }else if(possibleX2 && possibleY2){
            weightFactor(map, i+1, j, weight+ weightChange, color, weightChange);
            weightFactor(map, i, j+1, weight+ weightChange, color, weightChange);
        }else if(possibleY1 && possibleY2){
            weightFactor(map, i, j-1, weight+ weightChange, color, weightChange);
            weightFactor(map, i, j+1, weight+ weightChange, color, weightChange);
        }else if(possibleX1){
            weightFactor(map, i-1, j, weight+ weightChange, color, weightChange);
        }else if(possibleX2){
            weightFactor(map, i+1, j, weight+ weightChange, color, weightChange);
        }else if(possibleY1){
            weightFactor(map, i, j-1, weight+ weightChange, color, weightChange);
        }else if(possibleY2){
            weightFactor(map, i, j+1, weight+ weightChange, color, weightChange);
        }
    }
    public void weightInit(CityMap cityMap){
        StaticPoint[][][] map = cityMap.getMap();
        File f = new File("weights.txt");
        File f1 = new File("mapOfDoors.txt");
        File f2 = new File("mapOfWindows.txt");
        File f3 = new File("mapOfSafeZones.txt");
        if(f.exists() && f1.exists() && f2.exists() && f3.exists() && !f.isDirectory() && !f1.isDirectory() && !f2.isDirectory() && !f3.isDirectory()) {
            try{
                FileInputStream  fis = new FileInputStream("weights.txt");
                DataInputStream dis = new DataInputStream(fis);
                for (int i = 0; i < cityMap.getHeight(); i++)
                    for (int j = 0; j < cityMap.getWidth(); j++) {
                        map[0][i][j].weight = dis.readFloat();
                    }

                fis = new FileInputStream("mapOfDoors.txt");
                dis = new DataInputStream(fis);
                for (int i = 0; i < cityMap.getHeight(); i++)
                    for (int j = 0; j < cityMap.getWidth(); j++) {
                        mapOfDoors[i][j] = dis.readFloat();
                    }
                fis = new FileInputStream("mapOfWindows.txt");
                dis = new DataInputStream(fis);
                for (int i = 0; i < cityMap.getHeight(); i++)
                    for (int j = 0; j < cityMap.getWidth(); j++) {
                        mapOfWindows[i][j] = dis.readFloat();
                    }
                fis = new FileInputStream("mapOfSafeZones.txt");
                dis = new DataInputStream(fis);
                for (int i = 0; i < cityMap.getHeight(); i++)
                    for (int j = 0; j < cityMap.getWidth(); j++) {
                        mapOfSafeZones[i][j] = dis.readFloat();
                    }


            } catch(Exception e){

            }
        }else{
            for (int i = 0; i < cityMap.getHeight(); i++) {
                for (int j = 0; j < cityMap.getWidth(); j++) {
                    if (map[0][i][j].getTileColor() == TileColors.DOOR_CLOSE_COLOR) {
                        weightFactor(map, i, j, Weight.DOOR, TileColors.FLOOR_COLOR, 1.5f);
                    }
                }
            }
            for (int x = 0; x < cityMap.getHeight(); x++)
                for (int y = 0; y < cityMap.getWidth(); y++){
                    mapOfDoors[x][y] = map[0][x][y].weight;

                    if(map[0][x][y].getTileColor() == TileColors.FLOOR_COLOR)
                        map[0][x][y].weight = Weight.FLOOR;
                    if(map[0][x][y].getTileColor() == TileColors.WALL_COLOR)
                        map[0][x][y].weight = Weight.WALL;
                    if(map[0][x][y].getTileColor() == TileColors.WINDOW_CLOSE_COLOR)
                        map[0][x][y].weight = Weight.WINDOW;
                    if(map[0][x][y].getTileColor() == TileColors.DOOR_CLOSE_COLOR)
                        map[0][x][y].weight = Weight.DOOR;
                    if(map[0][x][y].getTileColor() == TileColors.LAWN_COLOR)
                        map[0][x][y].weight = Weight.LAWN;
                    if(map[0][x][y].getTileColor() == TileColors.SAFE_ZONE_COLOR)
                        map[0][x][y].weight = Weight.SAFE_ZONE;

                }

            for (int i = 0; i < cityMap.getHeight(); i++) {
                for (int j = 0; j < cityMap.getWidth(); j++) {
                    if (map[0][i][j].getTileColor() == TileColors.WINDOW_CLOSE_COLOR) {
                        weightFactor(map, i, j, Weight.WINDOW, TileColors.FLOOR_COLOR, 1f);
                    }
                }
            }
            for (int x = 0; x < cityMap.getHeight(); x++)
                for (int y = 0; y < cityMap.getWidth(); y++){
                    mapOfWindows[x][y] = map[0][x][y].weight;

                    if(map[0][x][y].getTileColor() == TileColors.FLOOR_COLOR)
                        map[0][x][y].weight = Weight.FLOOR;
                    if(map[0][x][y].getTileColor() == TileColors.WALL_COLOR)
                        map[0][x][y].weight = Weight.WALL;
                    if(map[0][x][y].getTileColor() == TileColors.WINDOW_CLOSE_COLOR)
                        map[0][x][y].weight = Weight.WINDOW;
                    if(map[0][x][y].getTileColor() == TileColors.DOOR_CLOSE_COLOR)
                        map[0][x][y].weight = Weight.DOOR;
                    if(map[0][x][y].getTileColor() == TileColors.LAWN_COLOR)
                        map[0][x][y].weight = Weight.LAWN;
                    if(map[0][x][y].getTileColor() == TileColors.SAFE_ZONE_COLOR)
                        map[0][x][y].weight = Weight.SAFE_ZONE;

                }

            for (int i = 0; i < cityMap.getHeight(); i++) {
                for (int j = 0; j < cityMap.getWidth(); j++) {
                    if (map[0][i][j].getTileColor() == TileColors.SAFE_ZONE_COLOR) {
                        weightFactor(map, i, j, Weight.SAFE_ZONE, TileColors.LAWN_COLOR, 0.5f);
                    }
                }
            }
            for (int x = 0; x < cityMap.getHeight(); x++)
                for (int y = 0; y < cityMap.getWidth(); y++){
                    mapOfSafeZones[x][y] = map[0][x][y].weight;

                    if(map[0][x][y].getTileColor() == TileColors.FLOOR_COLOR)
                        map[0][x][y].weight = Weight.FLOOR;
                    if(map[0][x][y].getTileColor() == TileColors.WALL_COLOR)
                        map[0][x][y].weight = Weight.WALL;
                    if(map[0][x][y].getTileColor() == TileColors.WINDOW_CLOSE_COLOR)
                        map[0][x][y].weight = Weight.WINDOW;
                    if(map[0][x][y].getTileColor() == TileColors.DOOR_CLOSE_COLOR)
                        map[0][x][y].weight = Weight.DOOR;
                    if(map[0][x][y].getTileColor() == TileColors.LAWN_COLOR)
                        map[0][x][y].weight = Weight.LAWN;
                    if(map[0][x][y].getTileColor() == TileColors.SAFE_ZONE_COLOR)
                        map[0][x][y].weight = Weight.SAFE_ZONE;

                }

            for (int x = 0; x < cityMap.getHeight(); x++)
                for (int y = 0; y < cityMap.getWidth(); y++){
                    mapOfSafeZones[x][y] /= 3f;
                    mapOfDoors[x][y] /= 3f;
                    mapOfWindows[x][y] /= 3f;
                }

            for (int i = 0; i < cityMap.getHeight(); i++)
                for (int j = 0; j < cityMap.getWidth(); j++) {
                    map[0][i][j].weight = mapOfDoors[i][j] + mapOfWindows[i][j] + mapOfSafeZones[i][j];
                }
            for (int i = 0; i < cityMap.getHeight(); i++){
                for (int j = 0; j < cityMap.getWidth(); j++) {
                    //System.out.print(mapOfDoors[j][i] + mapOfWindows[j][i] + mapOfSafeZones[j][i]);
                    System.out.printf("%.2f", mapOfSafeZones[j][i] + mapOfWindows[j][i] + mapOfDoors[j][i] * 0.5f);
                    System.out.print("\t\t");
                }
                System.out.println();
            }
            try{
                FileOutputStream fos = new FileOutputStream("weights.txt");
                DataOutputStream file = new DataOutputStream(fos);
                for (int i = 0; i < cityMap.getHeight(); i++)
                    for (int j = 0; j < cityMap.getWidth(); j++) {
                        file.writeFloat(map[0][i][j].weight);
                    }
                file.flush();

                fos = new FileOutputStream("mapOfDoors.txt");
                file = new DataOutputStream(fos);
                for (int i = 0; i < cityMap.getHeight(); i++)
                    for (int j = 0; j < cityMap.getWidth(); j++) {
                        file.writeFloat(mapOfDoors[i][j]);
                    }
                file.flush();

                fos = new FileOutputStream("mapOfWindows.txt");
                file = new DataOutputStream(fos);
                for (int i = 0; i < cityMap.getHeight(); i++)
                    for (int j = 0; j < cityMap.getWidth(); j++) {
                        file.writeFloat(mapOfWindows[i][j]);
                    }
                file.flush();

                fos = new FileOutputStream("mapOfSafeZones.txt");
                file = new DataOutputStream(fos);
                for (int i = 0; i < cityMap.getHeight(); i++)
                    for (int j = 0; j < cityMap.getWidth(); j++) {
                        file.writeFloat(mapOfSafeZones[i][j]);
                    }
                file.flush();


            } catch(Exception e){

            }

        }
    }

    public CityMap(int width, int height, int layers) {
        setCityMap(width, height, layers);
    }
}
