package sample.structure.points;

import java.util.List;

public class SafeZone extends StaticPoint {
    @Override
    public void tileColor() {
        super.tileColor = TileColors.SAFE_ZONE_COLOR;
    }

    @Override
    public void setActionList() {
        super.actionTypeList.add(ActionType.NONE);
        super.actionTypeList.add(ActionType.WALK_IN);
    }

    @Override
    public List<ActionType> getPossibleActions() {
        return super.actionTypeList;
    }

    public SafeZone(int x, int y) {
        super(x, y);
    }
}
