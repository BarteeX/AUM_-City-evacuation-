package sample.structure.logic;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class StaticPoint {
    private int x;
    private int y;
    public float weight;

    protected Color tileColor;
    protected List<ActionType> actionTypeList;

    public void setX(int x) {
        if(x >= 0) {
            this.x = x;
        } else {
            throw new IllegalArgumentException("In Point x was below 0");
        }
    }

    public void setY(int y) {
        if(y >= 0) {
            this.y = y;
        } else {
            throw new IllegalArgumentException("In Point y was below 0");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getTileColor() {
        return tileColor;
    }

    public abstract void tileColor();
    public abstract void setActionList();
    public abstract List<ActionType> getPossibleActions();
    public abstract boolean interact();

    public StaticPoint(int x, int y, float weight) {
        this.setX(x);
        this.setY(y);
        this.tileColor();
        this.actionTypeList = new ArrayList<>();
        this.setActionList();
        this.weight = weight;
    }
}
