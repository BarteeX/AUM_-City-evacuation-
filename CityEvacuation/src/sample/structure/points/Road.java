package sample.structure.points;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by BarteeX on 2017-04-25.
 */
public class Road extends StaticPoint {

    @Override
    public void tileColor() {
        super.tileColor = Color.GRAY;
    }

    @Override
    public void interaction() {
        super.actionTypeList = new ArrayList<>();
        super.actionTypeList.add(ActionType.WALK_IN);
    }

    public Road(int x, int y) {
        super(x, y);
    }
}
