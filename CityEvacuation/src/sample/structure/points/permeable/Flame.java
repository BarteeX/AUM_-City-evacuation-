package sample.structure.points.permeable;

import sample.structure.logic.ActionType;
import sample.structure.logic.StaticPoint;
import sample.structure.logic.TileColors;
import sample.structure.logic.Weight;
import sample.structure.map.CityMap;

import java.util.List;

/**
 * Created by Kuba on 16.05.2017.
 */
public class Flame extends StaticPoint {

    public Flame(int x, int y, CityMap map, float [][] mapOfFire) {
        super(x, y, Weight.FLAME, map);
        mapOfFire[x][y] = 5;
    }

    @Override
    public void tileColor() {
        super.tileColor = TileColors.FLAME_COLOR;
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

    @Override
    public boolean interact() {
        return false;
    }

}
