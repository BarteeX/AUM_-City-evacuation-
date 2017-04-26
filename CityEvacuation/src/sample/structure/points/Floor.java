package sample.structure.points;

import java.util.List;

public class Floor extends StaticPoint{

    @Override
    public void tileColor() {
        super.tileColor = TileColors.FLOOR_COLOR;
    }

    @Override
    public void setActionList() {
        actionTypeList.add(ActionType.NONE);
        actionTypeList.add(ActionType.WALK_IN);
    }

    @Override
    public List<ActionType> getPossibleActions() {
        return super.actionTypeList;
    }

    public Floor(int x, int y) {
        super(x, y);
    }
}
