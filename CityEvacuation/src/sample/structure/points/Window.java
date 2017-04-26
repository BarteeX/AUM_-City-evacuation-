package sample.structure.points;

import java.util.ArrayList;
import java.util.List;

public class Window extends StaticPoint {
    private boolean isOpen;

    public boolean isOpen() {
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
        super.tileColor = TileColors.WINDOW_COLOR;
    }

    @Override
    public void setActionList() {
        super.actionTypeList.add(ActionType.NONE);
        super.actionTypeList.add(ActionType.OPEN);
        super.actionTypeList.add(ActionType.CLOSE);
        super.actionTypeList.add(ActionType.WALK_IN);
    }

    @Override
    public List<ActionType> getPossibleActions() {
        List<ActionType> actionTypeList = this.actionTypeList;
        if(isOpen()) {
            actionTypeList.remove(ActionType.OPEN);
        } else {
            actionTypeList.remove(ActionType.CLOSE);
            actionTypeList.remove(ActionType.WALK_IN);
        }
        return actionTypeList;
    }

    public Window(int x, int y) {
        super(x, y);
        setClose();
    }

    public Window(int x, int y, boolean isOpen) {
        super(x, y);
        if (isOpen) {
            setOpen();
        } else {
            setClose();
        }
    }
}
