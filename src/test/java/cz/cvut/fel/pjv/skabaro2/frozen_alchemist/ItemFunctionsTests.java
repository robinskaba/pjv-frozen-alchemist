package cz.cvut.fel.pjv.skabaro2.frozen_alchemist;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.GameMap;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.ItemFunctions;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemFunctionsTests {
    Player player;
    GameMap gameMap;

    @BeforeEach
    public void setup() {
        int width = 16;
        int height = 19;

        Block[] blocks = new Block[width * height];
        int index = 0;
        for (int x = 0; x <width; x++) {
            for (int y = 0; y < height; y++) {
                blocks[index++] = new Block(BlockType.FloorBlock, new Position(x, y));
            }
        }

        gameMap = new GameMap(width, height, blocks, new Item[0]);
        player = new Player(new Position(0, 0));
    }

    @Test
    public void grantLevitationEnablesLevitating() {
        ItemFunctions.grantLevitation(player);

        Assertions.assertTrue(player.isLevitating());
    }

    @Test
    public void meltIceMeltsIce() {
        Position position = new Position(1, 1);

        // set ice block
        Block iceBlock = new Block(BlockType.MeltableWallBlock, position);
        Block oldBlock = gameMap.getBlockOnPosition(position);
        gameMap.remove(oldBlock);
        gameMap.add(iceBlock);

        // make player face the block
        player.setPosition(new Position(position.getX() - 1, position.getY()));
        player.setDirection(Direction.RIGHT);

        boolean result = ItemFunctions.meltIce(gameMap, position);
        Assertions.assertTrue(result); // item function needs to pass

        // check if the block was melted (changed to floor block)
        Block meltedBlock = gameMap.getBlockOnPosition(position);
        BlockType meltedBlockType = (BlockType) meltedBlock.getSubType();

        Assertions.assertEquals(BlockType.FloorBlock, meltedBlockType);
    }

    @Test
    public void freezeWaterFreezesWater() {
        Position position = new Position(1, 1);

        // set water block
        Block water = new Block(BlockType.Water, position);
        Block oldBlock = gameMap.getBlockOnPosition(position);
        gameMap.remove(oldBlock);
        gameMap.add(water);

        // make player face the block
        player.setPosition(new Position(position.getX() - 1, position.getY()));
        player.setDirection(Direction.RIGHT);

        boolean result = ItemFunctions.freezeWater(gameMap, position);
        Assertions.assertTrue(result); // item function needs to pass

        // check if the block was frozen (changed to floor block)
        Block frozenBlock = gameMap.getBlockOnPosition(position);
        BlockType frozenBlockSubType = (BlockType) frozenBlock.getSubType();

        Assertions.assertEquals(BlockType.FloorBlock, frozenBlockSubType);
    }

    @Test
    public void pulverizeRubblePulverizesRubble() {
        Position position = new Position(1, 1);

        // set rubble block
        Block rubble = new Block(BlockType.RubbleBlock, position);
        Block oldBlock = gameMap.getBlockOnPosition(position);
        gameMap.remove(oldBlock);
        gameMap.add(rubble);

        // make player face the block
        player.setPosition(new Position(position.getX() - 1, position.getY()));
        player.setDirection(Direction.RIGHT);

        boolean result = ItemFunctions.pulverizeRubble(gameMap, position);
        Assertions.assertTrue(result); // item function needs to pass

        // check if the block was frozen (changed to floor block)
        Block pulverizedBlock = gameMap.getBlockOnPosition(position);
        BlockType pulverizedBlockType = (BlockType) pulverizedBlock.getSubType();

        Assertions.assertEquals(BlockType.FloorBlock, pulverizedBlockType);
    }
}
