package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.EntityType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game map, which contains entities such as blocks and items.
 * Provides methods to interact with and manage entities on the map.
 */
public class GameMap {
    private final ArrayList<Entity> entities = new ArrayList<>();

    private final int width;
    private final int height;

    /**
     * Constructs a new GameMap with the specified blocks and items.
     *
     * @param width Width of map in tiles (blocks).
     * @param height Height of map in tiles (blocks).
     * @param blocks Array of blocks to be added to the map.
     * @param items  Array of items to be added to the map.
     */
    public GameMap(int width, int height, Block[] blocks, Item[] items) {
        this.width = width;
        this.height = height;

        entities.addAll(List.of(blocks));
        entities.addAll(List.of(items));
    }

    /**
     * Retrieves all entities currently on the map.
     *
     * @return A list of all entities on the map.
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Retrieves the block located at the specified position.
     *
     * @param position The position to check for a block.
     * @return The block at the specified position, or null if no block is present.
     */
    public Block getBlockOnPosition(Position position) {
        for (Entity e : entities) {
            if (e.getEntityType() == EntityType.BLOCK && e.getPosition().equals(position)) return (Block) e;
        }
        return null;
    }

    /**
     * Retrieves the item located at the specified position.
     *
     * @param position The position to check for an item.
     * @return The item at the specified position, or null if no item is present.
     */
    public Item getItemOnPosition(Position position) {
        for (Entity e : entities) {
            if (e.getEntityType() == EntityType.ITEM && e.getPosition().equals(position)) return (Item) e;
        }
        return null;
    }

    /**
     * Checks if the specified position is within the bounds of the map.
     *
     * @param position The position to check.
     * @return True if the position is within the map bounds, false otherwise.
     */
    public boolean isPositionInMap(Position position) {
        return position.getX() >= 0 && position.getX() < width && position.getY() >= 0 && position.getY() < height;
    }

    /**
     * Removes the specified entity from the map.
     *
     * @param entity The entity to remove.
     */
    public void remove(Entity entity) {
        entities.remove(entity);
    }

    /**
     * Adds the specified entity to the map.
     *
     * @param entity The entity to add.
     */
    public void add(Entity entity) {
        entities.add(entity);
    }

    /**
     * Retrieves the width of the map in tiles.
     *
     * @return The width of the map.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retrieves the height of the map in tiles.
     *
     * @return The height of the map.
     */
    public int getHeight() {
        return height;
    }
}