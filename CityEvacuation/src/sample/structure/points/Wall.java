package sample.structure.points;

import java.util.List;

import static sample.structure.points.ActionType.NONE;

public class Wall extends StaticPoint {

    @Override
    public void tileColor() {
        super.tileColor = TileColors.WALL_COLOR;
    }

    @Override
    public void setActionList() {
        super.actionTypeList.add(NONE);
    }

    @Override
    public List<ActionType> getPossibleActions() {
        return super.actionTypeList;
    }

    public Wall(int x, int y) {
        super(x, y);
    }
}
