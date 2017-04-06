package sample;

import javafx.scene.paint.Color;

/**
 * Created by Martic on 2017-04-06.
 */
public class Element extends Point{
    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public Element(Color c) {
        setColor(c);
    }
}
