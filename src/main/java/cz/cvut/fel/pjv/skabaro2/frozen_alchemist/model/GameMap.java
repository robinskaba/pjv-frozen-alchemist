package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.EntityType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Item;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final ArrayList<Entity> entities = new ArrayList<>();

    private final int width = Config.getInt("width_in_tiles");
    private final int height = Config.getInt("height_in_tiles");

    public GameMap(Block[] blocks, Item[] items) {
        entities.addAll(List.of(blocks));
        entities.addAll(List.of(items));
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
        return position.getX() >= 0 && position.getX() < width && position.getY() >= 0 && position.getY() < height;
    }

    public void remove(Entity entity) {
        entities.remove(entity);
    }

    public void add(Entity entity) {
        entities.add(entity);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
