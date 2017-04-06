package sample;

/**
 * Created by BarteeX on 2017-04-05.
 */
public abstract class Point {
    private int x;
    private int y;

    public void setX(int x) {
        if(x >= 0) {
            this.x = x;
        } else {
            throw new IllegalArgumentException("in Point x was below 0");
        }
    }
    public void setY(int y) {
        if(y >= 0) {
            this.y = y;
        } else {
            throw new IllegalArgumentException("in Point y was below 0");
        }
    }

    public int getX(){
        if(this.x >= 0) {
            return this.x;
        } else {
            return -1;
        }
    }

    public int getY(){
        if(this.y >= 0) {
            return this.y;
        } else {
            return -1;
        }
    }
}
