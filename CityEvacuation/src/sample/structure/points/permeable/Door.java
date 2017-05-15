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
        super.actionTypeList.remove(CLOSE);
        super.actionTypeList.remove(WALK_IN);
        super.actionTypeList.add(OPEN);
    }

    public void setOpen() {
        isOpen = true;
        super.tileColor = DOOR_OPEN_COLOR;
        super.actionTypeList.remove(OPEN);
        super.actionTypeList.add(CLOSE);
        super.actionTypeList.add(WALK_IN);
    }

    @Override
    public void tileColor() {
        super.tileColor = DOOR_CLOSE_COLOR;
    }

    @Override
    public void setActionList() {
        super.actionTypeList.add(NONE);
        super.actionTypeList.add(OPEN);
    }

    @Override
    public List<ActionType> getPossibleActions() {
        return super.actionTypeList;
    }

    @Override
    public boolean interact() {
        if(isOpen()) setClose();
        else setOpen();
        return true;
    }

    public Door(int x, int y) {
        super(x, y);
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
