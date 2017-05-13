package sample.structure.points.permeable;

import sample.structure.logic.ActionType;
import sample.structure.logic.StaticPoint;

import java.util.List;

import static sample.structure.logic.ActionType.NONE;
import static sample.structure.logic.ActionType.WALK_IN;
import static sample.structure.logic.TileColors.ROAD_COLOR;

public class Road extends StaticPoint {

    @Override
    public void tileColor() {
        super.tileColor = ROAD_COLOR;
    }

    @Override
    public void setActionList() {
        super.actionTypeList.add(NONE);
        super.actionTypeList.add(WALK_IN);
    }

    @Override
    public List<ActionType> getPossibleActions() {
        return super.actionTypeList;
    }

    public Road(int x, int y) {
        super(x, y);
    }
}