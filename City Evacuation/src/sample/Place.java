package sample;

import javafx.util.Pair;

/**
 * Created by BarteeX on 2017-04-05.
 */
public abstract class Place {
    Pair<Integer, Integer> sizePair; // Width, Length
    Element[][] areaTab;

    public void setSize(Pair<Integer,Integer> size) {
        if(size.getKey() > 0 && size.getValue() > 0) {
            this.sizePair = size;
            areaTab = new Element[size.getKey()][size.getValue()];
        } else {
            throw new IllegalArgumentException("width or length was not positive number");
        }
    }

    public void setSize(int width, int length) {
        if(width > 0 && length > 0) {
            this.sizePair = new Pair<>(width, length);
            areaTab = new Element[width][length];
        } else {
            throw new IllegalArgumentException("width or length was not positive number");
        }
    }

    public Pair<Integer, Integer> getSize() {
        return this.sizePair;
    }

    public int getWidth() {
        return this.sizePair.getKey();
    }

    public int getLenght() {
        return this.sizePair.getValue();
    }
}
