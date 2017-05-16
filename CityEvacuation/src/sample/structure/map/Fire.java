package sample.structure.map;

import sample.structure.logic.StaticPoint;
import sample.structure.logic.TileColors;
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

    Random stepExe;
    Random stepDirection;
    public Fire(double speed) {
        CHANCE_TO_FLOOR_IGNITED = 0.00001 * speed;
        CHANCE_TO_SMOKE_IGNITED = 0.001* speed;
        CHANCE_TO_SPREAD_SMOKE = 0.01 * speed;
        CHANCE_TO_WALL_IGNITED = 0.1;
        stepExe = new Random();
        stepDirection = new Random();
    }
        public void fireUpdate(CityMap cityMap) {
            StaticPoint[][][] map = cityMap.getMap();



            for (int i = 0; i < cityMap.getHeight(); i++) {
                for (int j = 0; j < cityMap.getWidth(); j++) {

                    if (map[0][i][j].getTileColor() == TileColors.FLOOR_COLOR) {
                        if (stepExe.nextInt((int) (1 / CHANCE_TO_FLOOR_IGNITED)) < 1)
                            map[0][i][j] = new Flame(i, j);
                    } else if(map[0][i][j].getTileColor() == TileColors.FLAME_COLOR) {
                        if (stepExe.nextInt((int) (1 / CHANCE_TO_SPREAD_SMOKE)) < 1) {
                            int x = stepDirection.nextInt(3) -1;
                            int y =stepDirection.nextInt(3) -1;

                            if(x+y !=0 && ((i+x) <= cityMap.getHeight()) && ((i+x) >= 0)&& ((j+y) <= cityMap.getWidth()) && ((j+y) >= 0)) {
                                if(map[0][i+x][j+y].getTileColor() == TileColors.WALL_COLOR){
                                    if (stepExe.nextInt((int) (1 / CHANCE_TO_WALL_IGNITED)) < 1)
                                        map[0][i + x][j + y] = new Smoke(i + x, j + y);
                                } else {
                                    map[0][i + x][j + y] = new Smoke(i + x, j + y);
                                }
                            }
                        }
                    } else if(map[0][i][j].getTileColor() == TileColors.SMOKE_COLOR) {
                        if (stepExe.nextInt((int) (1 / CHANCE_TO_SMOKE_IGNITED)) < 1)
                            map[0][i][j] = new Flame(i, j);
                    }
                }

            }
        }
}
