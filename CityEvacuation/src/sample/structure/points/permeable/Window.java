package sample.structure.points.permeable;

import sample.structure.logic.ActionType;
import sample.structure.logic.StaticPoint;

import java.util.List;

import static sample.structure.logic.ActionType.*;
import static sample.structure.logic.TileColors.WINDOW_COLOR;

public class Window extends StaticPoint {
    private boolean isOpen;

    private boolean isOpen() {
        return isOpen;
    }

    public void setClose() {
        isOpen = false;
    }

    public void setOpen() {
        isOpen = true;
    }

    @Override
    public void tileColor() {
        super.tileColor = WINDOW_COLOR;
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

    public Window(int x, int y) {
        super(x, y);
        setClose();
    }
}
