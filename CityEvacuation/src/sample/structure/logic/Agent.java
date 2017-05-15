package sample.structure.logic;

import java.awt.*;
import java.util.Random;
import java.util.List;

import static sample.structure.logic.ActionType.*;

public class Agent {
    private Point actualPosition;
    private Point lookingAt;

    private int widthBorder;
    private int heightBorder;

    public Point lookingAt() {
        Random dumb = new Random();
        int xMove = dumb.nextInt()%2;// - 1;
        int yMove = dumb.nextInt()%2;// - 1;
        int newPositionX = actualPosition.x + xMove;
        int newPositionY = actualPosition.y + yMove;
        if(newPositionX >= 0 && newPositionX < widthBorder
                && newPositionY >= 0 && newPositionY < heightBorder) {
            return lookingAt = new Point(newPositionX, newPositionY);
        } else {
            return lookingAt = actualPosition;
        }
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
        }
        return possibleActions.get(indexOfMax(randChooseTab));
    }

    public StaticPoint doAction(StaticPoint point) {
        ActionType action = getAction(point);
        if(action.equals(NONE)) {
            return point;
        } else if(action.equals(WALK_IN)) {
            actualPosition = lookingAt;
        } else if(action.equals(OPEN)) {
            point.interact();
        } else if(action.equals(CLOSE)&& (actualPosition != lookingAt)) {
            point.interact();
        } else if(action.equals(MOVE_IT)) {
            point.interact();
        }
        return point;
    }

    public Point getActualPosition() {
        return actualPosition;
    }

    public void setBorders(int[] mapSize) {
        this.widthBorder = mapSize[0];
        this.heightBorder = mapSize[1];
    }

    public Agent(int x, int y, int[] mapSize) {
        if(x < 0 || y < 0) {
            throw new IllegalArgumentException("Position of agent was invalid");
        } else {
            actualPosition = new Point(x, y);
            setBorders(mapSize);
        }
    }
}
