package sample.structure.points.permeable;

import sample.structure.logic.ActionType;
import sample.structure.logic.StaticPoint;

import java.util.List;

import static sample.structure.logic.ActionType.*;
import static sample.structure.logic.TileColors.DOOR_CLOSE_COLOR;
import static sample.structure.logic.TileColors.DOOR_OPEN_COLOR;

public class Door extends StaticPoint {
    private boolean isOpen;

    private boolean isOpen() {
        return isOpen;
    }

    public void setClose() {
        isOpen = false;
        super.tileColor = DOOR_CLOSE_COLOR;
    }

    public void setOpen() {
        isOpen = true;
        super.tileColor = DOOR_OPEN_COLOR;
    }

    @Override
    public void tileColor() {
        super.tileColor = DOOR_CLOSE_COLOR;
    }

    @Override
    public void setActionList() {
        super.actionTypeList.add(NONE);
        super.actionTypeList.add(OPEN);
        super.actionTypeList.add(CLOSE);
        super.actionTypeList.add(WALK_IN);
    }

    @Override
    public List<ActionType> getPossibleActions() {
        List<ActionType> actionTypeList = this.actionTypeList;
        if(isOpen()) {
            actionTypeList.remove(OPEN);
        } else {
            actionTypeList.remove(CLOSE);
            actionTypeList.remove(WALK_IN);
        }
        return actionTypeList;
    }

    public Door(int x, int y) {
        super(x, y);
        setClose();
    }

    public Door(int x, int y, boolean isOpen) {
        super(x, y);
        if (isOpen) {
            setOpen();
        } else {
            setClose();
        }
    }
}
