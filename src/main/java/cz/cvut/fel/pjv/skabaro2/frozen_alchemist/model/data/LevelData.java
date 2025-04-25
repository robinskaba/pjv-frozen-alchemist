package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Item;

/**
 * Represents the loaded data for a level in the game, including blocks, items, and the player's initial position.
 */
public record LevelData(int mapWidth, int mapHeight, Block[] blocks, Item[] items, Position initialPlayerPosition) {
    /**
     * Creates a new LevelData object.
     *
     * @param mapWidth              Width of level map in tiles (blocks).
     * @param mapHeight             Width of level map in tiles (blocks).
     * @param blocks                An array of blocks present in the level.
     * @param items                 An array of items present in the level.
     * @param initialPlayerPosition The initial position of the player in the level.
     */
    public LevelData {}
}