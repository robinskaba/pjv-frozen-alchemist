package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;


import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.BlockType;

public class ItemFunctions {
    public static boolean grantLevitation(Player player) {
        player.setLevitationSteps(2);
        return true;
    }

    public static boolean meltIce(GameMap gameMap, Position position) {
        Block blockOnPosition = gameMap.getBlockOnPosition(position);
        if (blockOnPosition.getSubType() != BlockType.MeltableWallBlock) return false;

        gameMap.remove(blockOnPosition);
        gameMap.add(new Block(BlockType.FloorBlock, position));

        return true;
    }

    public static boolean freezeWater(GameMap gameMap, Position position) {
        Block blockOnPosition = gameMap.getBlockOnPosition(position);
        if (blockOnPosition.getSubType() != BlockType.Water) return false;

        gameMap.remove(blockOnPosition);
        gameMap.add(new Block(BlockType.FloorBlock, position));

        return true;
    }

    public static boolean pulverizeRubble(GameMap gameMap, Position position) {
        Block blockOnPosition = gameMap.getBlockOnPosition(position);
        if (blockOnPosition.getSubType() != BlockType.RubbleBlock) return false;

        gameMap.remove(blockOnPosition);
        gameMap.add(new Block(BlockType.FloorBlock, position));

        return true;
    }
}
