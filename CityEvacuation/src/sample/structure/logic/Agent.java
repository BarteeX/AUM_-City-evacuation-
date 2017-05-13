package sample.structure.logic;

import java.awt.*;
import java.util.Random;

public class Agent {
    private Point actualPosition;
    private Point walkInPosition;

    public Point dumbMove(Point mapSize) {
        Random dumb = new Random();
        int xMove = 0, yMove = 0;
        int switcher = dumb.nextInt()%8;
        switch(switcher) {
            case 0:
                if (mapSize.x > actualPosition.x + 1)
                    xMove = 1;
                break;
            case 1:
                if (actualPosition.x > 0)
                    xMove = -1;
                break;
            case 2:
                if (mapSize.y > actualPosition.y + 1)
                    yMove = 1;
                break;
            case 3:
                if (actualPosition.y > 0)
                    yMove = -1;
                break;
            case 4:
                if (actualPosition.x > 0 && actualPosition.y > 0) {
                    xMove = -1;
                    yMove = -1;
                }
                break;
            case 5:
                if (mapSize.x > actualPosition.x + 1 && mapSize.y > actualPosition.y + 1) {
                    xMove = 1;
                    yMove = 1;
                }
                break;
            case 6:
                if (actualPosition.x > 0 && mapSize.y > actualPosition.y + 1) {
                    xMove = -1;
                    yMove = 1;
                }
                break;
            case 7:
                if (mapSize.x > actualPosition.x + 1 && actualPosition.y > 0) {
                    xMove = 1;
                    yMove = -1;
                }
                break;
        }
        if(actualPosition.x + xMove < 0 || actualPosition.y + yMove < 0) {
            return actualPosition;
        } else {
            return walkInPosition = new Point(actualPosition.x + xMove, actualPosition.y + yMove);
        }
    }

    public boolean tryToMove(StaticPoint point) {
        if(point.getPossibleActions().contains(ActionType.WALK_IN)) {
            actualPosition = walkInPosition;
            return true;
        }
        else return false;
    }

    public boolean tryToInteract(StaticPoint point) {
        return false;
    }

    public Point getActualPosition() {
        return actualPosition;
    }

    public Agent(int x, int y) {
        if(x < 0 || y < 0) {
            throw new IllegalArgumentException("Position of agent was invalid");
        } else {
            actualPosition = new Point(x, y);
        }
    }
}
