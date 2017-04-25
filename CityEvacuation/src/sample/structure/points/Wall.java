package sample.structure.points;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by BarteeX on 2017-04-25.
 */
public class Wall extends StaticPoint {

    @Override
    public void tileColor() {
        super.tileColor = Color.BLACK;
    }

    @Override
    public void interaction() {
        super.actionTypeList = new ArrayList<>();
        actionTypeList.add(ActionType.NONE);
    }

    public Wall(int x, int y) {
        super(x, y);
    }
}
