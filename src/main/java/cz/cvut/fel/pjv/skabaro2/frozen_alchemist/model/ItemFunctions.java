package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.BlockType;

/**
 * Provides utility functions for item interactions in the game.
 */
public class ItemFunctions {
    /**
     * Grants the player the ability to levitate for a limited number of steps.
     *
     * @param player The player to grant levitation to.
     * @return True if the levitation was successfully granted.
     */
    public static boolean grantLevitation(Player player) {
        player.setLevitationSteps(2);
        return true;
    }

    /**
     * Melts an ice block at the specified position on the game map.
     *
     * @param gameMap  The game map where the block is located.
     * @param position The position of the block to melt.
     * @return True if the ice block was successfully melted, false otherwise.
     */
    public static boolean meltIce(GameMap gameMap, Position position) {
        Block blockOnPosition = gameMap.getBlockOnPosition(position);
        if (blockOnPosition.getSubType() != BlockType.MeltableWallBlock) return false;

        gameMap.remove(blockOnPosition);
        gameMap.add(new Block(BlockType.FloorBlock, position));

        return true;
    }

    /**
     * Freezes a water block at the specified position on the game map.
     *
     * @param gameMap  The game map where the block is located.
     * @param position The position of the block to freeze.
     * @return True if the water block was successfully frozen, false otherwise.
     */
    public static boolean freezeWater(GameMap gameMap, Position position) {
        Block blockOnPosition = gameMap.getBlockOnPosition(position);
        if (blockOnPosition.getSubType() != BlockType.Water) return false;

        gameMap.remove(blockOnPosition);
        gameMap.add(new Block(BlockType.FloorBlock, position));

        return true;
    }

    /**
     * Pulverizes a rubble block at the specified position on the game map.
     *
     * @param gameMap  The game map where the block is located.
     * @param position The position of the block to pulverize.
     * @return True if the rubble block was successfully pulverized, false otherwise.
     */
    public static boolean pulverizeRubble(GameMap gameMap, Position position) {
        Block blockOnPosition = gameMap.getBlockOnPosition(position);
        if (blockOnPosition.getSubType() != BlockType.RubbleBlock) return false;

        gameMap.remove(blockOnPosition);
        gameMap.add(new Block(BlockType.FloorBlock, position));

        return true;
    }
}