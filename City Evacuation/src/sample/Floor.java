package sample;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.FileReader;

/**
 * Created by BarteeX on 2017-04-06.
 */
public class Floor extends Place {
    private boolean isFirst;

    public void add(Element elem) {
        super.areaTab[elem.getX()][elem.getY()] = elem;
    }

    public Element get(int x, int y) {
        return super.areaTab[x][y];
    }

    public void loadFromFile(String path) {
        Image temp = new Image(path);
        PixelReader pr = temp.getPixelReader();
        for(int y = 0; y < super.sizePair.getValue(); ++y) {
            for(int x = 0; x < super.sizePair.getKey(); ++x) {
                super.areaTab[x][y] = new Element(pr.getColor(x, y));
            }
        }

    }

    public Floor(boolean isFirst, int width, int lenght) {
        super.setSize(width, lenght);
        this.isFirst = isFirst;
    }

    public Floor(boolean isFirst, Pair<Integer, Integer> size) {
        super.setSize(size);
        this.isFirst = isFirst;
    }
}
