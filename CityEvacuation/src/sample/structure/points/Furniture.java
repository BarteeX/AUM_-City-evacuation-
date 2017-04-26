package sample.structure.points;

import java.util.List;

public class Furniture extends StaticPoint {
    @Override
    public void tileColor() {
        super.tileColor = TileColors.FURNITURE_COLOR;
    }

    @Override
    public void setActionList() {
        super.actionTypeList.add(ActionType.NONE);
        super.actionTypeList.add(ActionType.MOVE_IT);
    }

    @Override
    public List<ActionType> getPossibleActions() {
        return super.actionTypeList;
    }

    public Furniture(int x, int y) {
        super(x, y);
    }
}
