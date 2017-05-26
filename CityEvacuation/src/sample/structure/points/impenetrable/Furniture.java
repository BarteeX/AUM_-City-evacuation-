package sample.structure.points.impenetrable;

import sample.structure.logic.ActionType;
import sample.structure.logic.StaticPoint;
import sample.structure.logic.TileColors;
import sample.structure.logic.Weight;
import sample.structure.map.CityMap;

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

    @Override
    public boolean interact() {
        return false;
    }

    public Furniture(int x, int y, CityMap map) {
        super(x, y, Weight.FURNITURE, map);
    }
}
