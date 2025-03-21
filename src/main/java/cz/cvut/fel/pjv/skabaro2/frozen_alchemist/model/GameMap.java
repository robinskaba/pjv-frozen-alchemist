package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import java.util.ArrayList;

public class GameMap {
    // width and height in tiles
    private final int width;
    private final int height;

    private final ArrayList<Entity> entities = new ArrayList<>();

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
