package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.ItemType;

import java.util.Map;

/**
 * Represents the progress save data for a game, including the current level,
 * inventory content, and a text representation of the level.
 * It is used when Game is initiated, it gets this ProgressSave and loads the initial level,
 * inventory and the level using its text representation.
 */
public record ProgressSave(int level, Map<ItemType, Integer> inventoryContent, String levelTextRepresentation) {
    /**
     * Constructs a new ProgressSave object.
     *
     * @param level                   The current level of the game.
     * @param inventoryContent        A map representing the player's inventory content,
     *                                where the key is the item type and the value is the amount.
     * @param levelTextRepresentation A text representation of the current level.
     */
    public ProgressSave {
    }
}