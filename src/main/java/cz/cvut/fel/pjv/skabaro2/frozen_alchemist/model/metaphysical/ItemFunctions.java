package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.GameMap;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.BlockType;

public class ItemFunctions {
    public void grantLevitation(Player player) {
        player.setStepsWithLevitationLeft(2);
    }

    public void meltIce(GameMap gameMap, Position position) {
        Block blockOnPosition = gameMap.getBlockOnPosition(position);
        if (blockOnPosition.getSubtype() != BlockType.MeltableIce) return;

        gameMap.remove(blockOnPosition);
        gameMap.add(new Block(BlockType.Floor, position));
    }

    public void freezeWater(GameMap gameMap, Position position) {
        Block blockOnPosition = gameMap.getBlockOnPosition(position);
        if (blockOnPosition.getSubtype() != BlockType.Water) return;

        gameMap.remove(blockOnPosition);
        gameMap.add(new Block(BlockType.Floor, position));
    }

    public void pulverizeRubble(GameMap gameMap, Position position) {
        Block blockOnPosition = gameMap.getBlockOnPosition(position);
        if (blockOnPosition.getSubtype() != BlockType.Rubble) return;

        gameMap.remove(blockOnPosition);
        gameMap.add(new Block(BlockType.Floor, position));
    }
}
