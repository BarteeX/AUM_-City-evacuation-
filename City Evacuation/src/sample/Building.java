package sample;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BarteeX on 2017-04-06.
 */
public class Building extends Place {
    Floor firstFloor;
    List<Floor> floorsList;


    public void setFirstFloor(Floor firstFloor) {
        this.firstFloor = firstFloor;
    }

    public void setFloors(List<Floor> floorsList) {
        this.floorsList = floorsList;
    }

    public void addFloor(Floor floor) {
        this.floorsList.add(floor);
    }

    public void addFloors(List<Floor> floorsList) {
        for(Floor floor : floorsList) {
            this.floorsList.add(floor);
        }
    }

    public Floor getFloor(int idx) {
        if(idx > 0) {
            return this.floorsList.get(idx);
        } else if (idx == 0){
            return firstFloor;
        } else {
            throw new IllegalArgumentException("index was negative");
        }
    }

    private void setupFloors(int height) {
        firstFloor = new Floor(true, super.getSize());
        floorsList = new ArrayList<>();
        for(int i = 0; i < height; ++i) {
            floorsList.add(new Floor(false, super.getSize()));
        }
    }

    public Building(int width, int length, int height) {
        super.setSize(width,length);
        setupFloors(height);
    }

    /**
     *
     * @param size
     * @param height
     */
    public Building(Pair<Integer, Integer> size, int height) {
        super.setSize(size);
        setupFloors(height);
    }

}
