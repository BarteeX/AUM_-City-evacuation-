package sample.structure.points.permeable;

import sample.structure.logic.ActionType;
import sample.structure.logic.StaticPoint;
import sample.structure.logic.TileColors;

import java.util.List;

/**
 * Created by Kuba on 16.05.2017.
 */
public class Smoke extends StaticPoint {

    public Smoke(int x, int y) {
        super(x, y);
    }

    @Override
    public void setActionList() {

    }

    @Override
    public void tileColor() {
        super.tileColor = TileColors.SMOKE_COLOR;
    }

    @Override
    public boolean interact() {
        return true;
    }

    @Override
    public List<ActionType> getPossibleActions() {
        return null;
    }

}
