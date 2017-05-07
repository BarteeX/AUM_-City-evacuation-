package sample.structure.points;

import java.util.List;

import static sample.structure.points.ActionType.NONE;
import static sample.structure.points.ActionType.WALK_IN;
import static sample.structure.points.TileColors.LAWN_COLOR;

/**
 * Created by BarteeX on 2017-04-26.
 */
public class Lawn extends StaticPoint {
    @Override
    public void tileColor() {
        super.tileColor = LAWN_COLOR;
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

    public Lawn(int x, int y) {
        super(x, y);
    }
}
