package sample.structure.map;

import javafx.scene.paint.Color;
import sample.structure.logic.StaticPoint;
import sample.structure.logic.TileColors;
import sample.structure.logic.Weight;
import sample.structure.points.permeable.Flame;
import sample.structure.points.permeable.Smoke;

import java.util.Random;

/**
 * Created by Kuba on 16.05.2017.
 */
public class Fire {
    private static double CHANCE_TO_FLOOR_IGNITED;
    private static double CHANCE_TO_SMOKE_IGNITED;
    private static double CHANCE_TO_SPREAD_SMOKE;
    private static double CHANCE_TO_WALL_IGNITED;
    private static float SMOKE_WEIGHT_REDUCE;
    Random stepExe;
    Random stepDirection;
    public Fire(double speed) {
        CHANCE_TO_FLOOR_IGNITED = 0.00001 * speed;
        CHANCE_TO_SMOKE_IGNITED = 0.001 * speed;
        CHANCE_TO_SPREAD_SMOKE = 0.01 * speed;
        CHANCE_TO_WALL_IGNITED = 0.01;
        SMOKE_WEIGHT_REDUCE = 10;
        stepExe = new Random();
        stepDirection = new Random();
    }
    public void fireUpdate(CityMap cityMap) {
        StaticPoint[][][] map = cityMap.getMap();
        for (int i = 0; i < cityMap.getHeight(); i++) {
            for (int j = 0; j < cityMap.getWidth(); j++) {
                if (map[0][i][j].getTileColor() == TileColors.FLOOR_COLOR && map[0][i][j].getTileColor() != TileColors.FLAME_COLOR) {
                    if (stepExe.nextInt((int) (1 / CHANCE_TO_FLOOR_IGNITED )) < 1)
                        map[0][i][j] = new Flame(i, j, cityMap, cityMap.getMapOfFire());
                } else if(map[0][i][j].getTileColor() == TileColors.FLAME_COLOR || map[0][i][j].getTileColor() == TileColors.SMOKE_COLOR) {
                    if (stepExe.nextInt((int) (1 / CHANCE_TO_SPREAD_SMOKE)) < 1) {
                        int x = stepDirection.nextInt(3) -1;
                        int y =stepDirection.nextInt(3) -1;
                        if(i+x >= 0 && i+x < cityMap.getHeight() && j+y >=0 && j+y <  cityMap.getWidth()){
                            if(x+y !=0 && ((i+x) <= cityMap.getHeight()) && ((i+x) >= 0)&& ((j+y) <= cityMap.getWidth()) && ((j+y) >= 0)) {
                                if(map[0][i+x][j+y].getTileColor() != TileColors.WALL_COLOR && map[0][i+x][j+y].getTileColor() != TileColors.FLAME_COLOR){
                                    map[0][i + x][j + y] = new Smoke(i + x, j + y, cityMap, cityMap.getMapOfSmoke());
                                }
                                else
                                {
                                    if (stepExe.nextInt((int) (1 /  CHANCE_TO_WALL_IGNITED)) < 1 && map[0][i+x][j+y].getTileColor() != TileColors.FLAME_COLOR)
                                        map[0][i + x][j + y] = new Smoke(i + x, j + y, cityMap, cityMap.getMapOfSmoke());
                                }
                                if(map[0][i][j].getTileColor() == TileColors.SMOKE_COLOR) {
                                    if (stepExe.nextInt((int) (1 / CHANCE_TO_SMOKE_IGNITED)) < 1)
                                        map[0][i][j] = new Flame(i, j, cityMap, cityMap.getMapOfFire());
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
