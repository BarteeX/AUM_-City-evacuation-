package sample.structure.points.permeable;

import sample.structure.logic.ActionType;
import sample.structure.logic.StaticPoint;
import sample.structure.logic.Weight;

import java.util.List;

import static sample.structure.logic.ActionType.*;
import static sample.structure.logic.TileColors.WINDOW_CLOSE_COLOR;
import static sample.structure.logic.TileColors.WINDOW_OPEN_COLOR;

public class Window extends StaticPoint {
    private boolean isOpen;

    private boolean isOpen() {
        return isOpen;
    }

    public void setClose() {
        isOpen = false;
        super.tileColor = WINDOW_CLOSE_COLOR;
        super.actionTypeList.remove(CLOSE);
        super.actionTypeList.remove(WALK_IN);
        super.actionTypeList.add(OPEN);
    }

    public void setOpen() {
        isOpen = true;
        super.tileColor = WINDOW_OPEN_COLOR;
        super.actionTypeList.remove(OPEN);
        super.actionTypeList.add(CLOSE);
        super.actionTypeList.add(WALK_IN);
    }

    @Override
    public void tileColor() {
        super.tileColor = WINDOW_CLOSE_COLOR;
    }

    @Override
    public void setActionList() {
        super.actionTypeList.add(NONE);
        super.actionTypeList.add(OPEN);
    }

    @Override
    public List<ActionType> getPossibleActions() {
        return this.actionTypeList;
    }

    @Override
    public boolean interact() {
        if(isOpen) setClose();
        else setOpen();
        return true;
    }

    public Window(int x, int y) {
        super(x, y, Weight.windows);
        setClose();
    }
}
