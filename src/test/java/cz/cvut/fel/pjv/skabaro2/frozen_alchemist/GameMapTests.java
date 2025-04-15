package cz.cvut.fel.pjv.skabaro2.frozen_alchemist;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.GameMap;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Item;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.ItemType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameMapTests {
    GameMap gameMap;

    @BeforeEach
    public void setup() {
        int width = Config.getInt("width_in_tiles");
        int height = Config.getInt("height_in_tiles");
        Block[] blocks = new Block[width * height];
        int index = 0;
        for (int x = 0; x <width; x++) {
            for (int y = 0; y < height; y++) {
                blocks[index++] = new Block(BlockType.FloorBlock, new Position(x, y));
            }
        }

        // generate a row of frost potions
        Item[] items = new Item[width];
        for (int x = 0; x < width; x++) {
            items[x] = new Item(ItemType.PotionOfFrost, new Position(x, 0));
        }

        gameMap = new GameMap(blocks, items);
    }

    @Test
    public void removeRemovesItem() {
        Position position = new Position(0, 0);
        Item item = gameMap.getItemOnPosition(position);
        Assertions.assertNotNull(item); // item should be there cause of setup

        gameMap.remove(item);

        Item removedItem = gameMap.getItemOnPosition(position);
        Assertions.assertNull(removedItem); // item should be removed
    }

    @Test
    public void removeRemovesBlock() {
        Position position = new Position(0, 1);
        Block block = gameMap.getBlockOnPosition(position);
        Assertions.assertNotNull(block); // block should be there cause of setup

        gameMap.remove(block);

        Block removedBlock = gameMap.getBlockOnPosition(position);
        Assertions.assertNull(removedBlock); // block should be removed
    }

    @Test
    public void addAddsItem() {
        Position position = new Position(1, 1);
        Item item = new Item(ItemType.PotionOfFrost, position);

        gameMap.add(item);

        Item addedItem = gameMap.getItemOnPosition(position);
        Assertions.assertEquals(item, addedItem); // item should be added
    }

    @Test
    public void addAddsBlock() {
        Position position = new Position(1, 2);
        gameMap.remove(gameMap.getBlockOnPosition(position)); // remove old block

        Block block = new Block(BlockType.FloorBlock, position);

        gameMap.add(block);

        Block addedBlock = gameMap.getBlockOnPosition(position);
        Assertions.assertEquals(block, addedBlock); // block should be added
    }

    @Test
    public void isPositionInMapReturnsTrueForValidPosition() {
        Position position = new Position(1, 1);
        Assertions.assertTrue(gameMap.isPositionInMap(position)); // position should be in map
    }

    @Test
    public void isPositionInMapReturnsFalseForInvalidPosition() {
        Position position = new Position(-1, -1);
        Assertions.assertFalse(gameMap.isPositionInMap(position)); // position should be out of map
    }

    @Test
    public void getItemOnPositionReturnsItemOnPosition() {
        Position position = new Position(5, 5);

        Item item = new Item(ItemType.EmberFlower, position);
        gameMap.add(item);

        Item itemOnPosition = gameMap.getItemOnPosition(position);
        Assertions.assertEquals(item, itemOnPosition);
    }

    @Test
    public void getBlockOnPositionReturnsBlockOnPosition() {
        Position position = new Position(10, 5);
        Block block = new Block(BlockType.RubbleBlock, position);
        gameMap.remove(gameMap.getBlockOnPosition(position)); // remove old block

        gameMap.add(block);

        Block blockOnPosition = gameMap.getBlockOnPosition(position);
        Assertions.assertEquals(block, blockOnPosition); // block should be added
    }
}
