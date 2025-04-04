package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final ArrayList<Entity> entities = new ArrayList<>();

    private final int widthInTiles = Config.getInt("width_in_tiles");
    private final int heightInTiles = Config.getInt("height_in_tiles");

    public GameMap(Block[] blocks, Item[] items) {
        entities.addAll(List.of(blocks));
        entities.addAll(List.of(items));
        for (Entity e : entities) {
            System.out.println(e.toString());
        }
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Block getBlockOnPosition(Position position) {
        for (Entity e : entities) {
            if (e.getEntityType() == EntityType.BLOCK && e.getPosition().equals(position)) return (Block) e;
        }
        return null;
    }

    public Item getItemOnPosition(Position position) {
        for (Entity e : entities) {
            if (e.getEntityType() == EntityType.ITEM && e.getPosition().equals(position)) return (Item) e;
        }
        return null;
    }

    public boolean isPositionInMap(Position position) {
        return position.getX() >= 0 && position.getX() < widthInTiles && position.getY() >= 0 && position.getY() < heightInTiles;
    }

    public void remove(Entity entity) {
        entities.remove(entity);
    }

    public void add(Entity entity) {
        entities.add(entity);
    }
}
