package sample.structure.logic;

import sample.structure.map.CityMap;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import static sample.structure.logic.ActionType.*;

public class Agent{
    public Point actualPosition;
    public Point lookingAt;
    private float score;
    private int widthBorder;
    private int heightBorder;
    public float health;
    private Genotype genotype;
    private float randomWeight;
    public boolean finished = false;

    public Agent(int x, int y, int[] mapSize) {
        super();
        genotype = new Genotype();
        if(x < 0 || y < 0) {
            throw new IllegalArgumentException("Position of agent was invalid");
        } else {
            actualPosition = new Point(x, y);
            setBorders(mapSize);
        }
        health = 100 * genotype.stamina;
        score = 0;
    }
    public Agent(int x, int y, int[] mapSize, Genotype inheritedGenotype) {
        super();
        genotype = new Genotype();
        genotype = inheritedGenotype;
        if(x < 0 || y < 0) {
            throw new IllegalArgumentException("Position of agent was invalid");
        } else {
            actualPosition = new Point(x, y);
            setBorders(mapSize);
        }
        health = 100 * genotype.stamina;
        score = 0;
    }

    public Point movementAlgorithm(StaticPoint[][][] map, float[][] mapOfWindows, float[][] mapOfDoors, float[][] mapOfSafeZones, float[][] mapOfSmoke, float[][] mapOfFire, boolean[][] mapOfAgents){
        ActionType action = getAction(map[0][actualPosition.x][actualPosition.y]);
        if(action.equals(NONE)) {
            if(map[0][actualPosition.x][actualPosition.y].getTileColor() == TileColors.SMOKE_COLOR)
                health -= 5;

            if(map[0][actualPosition.x][actualPosition.y].getTileColor() == TileColors.FLAME_COLOR)
                health -= 10;

            if(map[0][actualPosition.x][actualPosition.y].getTileColor() == TileColors.SAFE_ZONE_COLOR)
                finished = true;
            return actualPosition;
        } else if(action.equals(WALK_IN)) {
            float minWeight = 9999; //magic number
            Point point;
            List<Point> possibleMoves = new ArrayList<>();
            Point [] moves = {
                    new Point(-1, 0),
                    new Point(1, 0),
                    new Point(0, -1),
                    new Point(0, 1)
            };
            Random random = new Random();
            for(int i = 0; i < 4 ;i++){
                randomWeight = random.nextFloat() + getGenotype().calm;
                if(randomWeight > 1)
                    randomWeight = 1;

                if(mapOfAgents[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] != true && map[0][actualPosition.x + moves[i].x][actualPosition.y + moves[i].y].getTileColor() != TileColors.WALL_COLOR){
                    if((mapOfWindows[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iLikeWindows +
                            mapOfDoors[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iLikeDoors +
                            mapOfSafeZones[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iLikeSafeZone +
                            mapOfSmoke[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iDontLikeSmoke +
                            mapOfFire[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iDontLikeFire) *
                            randomWeight <= minWeight){

                        minWeight = mapOfWindows[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iLikeWindows +
                                mapOfDoors[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iLikeDoors +
                                mapOfSafeZones[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iLikeSafeZone +
                                mapOfSmoke[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iDontLikeSmoke +
                                mapOfFire[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iDontLikeFire;
                    }
                }
            }
            for(int i = 0; i < 4 ;i++){
                if(mapOfAgents[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] != true && map[0][actualPosition.x + moves[i].x][actualPosition.y + moves[i].y].getTileColor() != TileColors.WALL_COLOR){
                    if(mapOfWindows[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iLikeWindows +
                            mapOfDoors[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iLikeDoors +
                            mapOfSafeZones[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iLikeSafeZone +
                            mapOfSmoke[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iDontLikeSmoke +
                            mapOfFire[actualPosition.x + moves[i].x][actualPosition.y + moves[i].y] * getGenotype().iDontLikeFire == minWeight){

                        possibleMoves.add(moves[i]);
                    }
                }
            }
            Random rand = new Random();
            if(possibleMoves.size() > 0){
                point = possibleMoves.get(rand.nextInt(possibleMoves.size()));
                mapOfAgents[actualPosition.x][actualPosition.y] = false;
                lookingAt = new Point(actualPosition.x + point.x, actualPosition.y + point.y);
                actualPosition = lookingAt;
                mapOfAgents[actualPosition.x][actualPosition.y] = true;
            }else{
                lookingAt = new Point(actualPosition.x, actualPosition.y);
                //actualPosition = lookingAt;
                mapOfAgents[actualPosition.x][actualPosition.y] = true;
            }
        } else if(action.equals(OPEN)) {
            map[0][actualPosition.x][actualPosition.y].interact();
        } else if(action.equals(CLOSE)&& (actualPosition != lookingAt)) {
            map[0][actualPosition.x][actualPosition.y].interact();
        } else if(action.equals(MOVE_IT)) {
            map[0][actualPosition.x][actualPosition.y].interact();
        }
        if(map[0][actualPosition.x][actualPosition.y].getTileColor() == TileColors.SMOKE_COLOR)
            health -= 5;

        if(map[0][actualPosition.x][actualPosition.y].getTileColor() == TileColors.FLAME_COLOR)
            health -= 10;

        if(map[0][actualPosition.x][actualPosition.y].getTileColor() == TileColors.SAFE_ZONE_COLOR)
            finished = true;

        return actualPosition;
    }
    public float getScore(){
        return score;
    }
    public void setScore(float score){
         this.score = score;
    }
    public Genotype getGenotype(){
        return genotype;
    }
    /**
     * Function looking for index of max element in @param tab
     * @param tab searched tab
     * @return idext of maximum element in table
     */
    private int indexOfMax(int[] tab) {
        int max = 0;
        for(int i = 0; i < tab.length; ++i) {
            if(tab[i] > tab[max]) max = i;
        }
        return max;
    }

    /**
     * Function draw values for each possible action, and take action with the biggest number.
     *
     * @param point interaction were checking on this point
     * @return randomly choose ActionType from possible actions from point
     */
    public ActionType getAction(StaticPoint point) {
        List<ActionType> possibleActions = point.getPossibleActions();
        int[] randChooseTab = new int[possibleActions.size()];
        for(ActionType action : possibleActions) {
            int iterator = possibleActions.indexOf(action);
            randChooseTab[iterator] = new Random().nextInt()%10;
            if(action == WALK_IN)
                randChooseTab[iterator] -= 10 * genotype.laziness;
        }
        return possibleActions.get(indexOfMax(randChooseTab));
    }

    public Point getActualPosition() {
        return actualPosition;
    }

    public void setBorders(int[] mapSize) {
        this.widthBorder = mapSize[0];
        this.heightBorder = mapSize[1];
    }

}
