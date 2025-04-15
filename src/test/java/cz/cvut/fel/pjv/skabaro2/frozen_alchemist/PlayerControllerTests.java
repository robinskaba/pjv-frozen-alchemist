package cz.cvut.fel.pjv.skabaro2.frozen_alchemist;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.Controls;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.GameMap;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.PlayerController;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.*;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerControllerTests {
    Player player;
    PlayerController playerController;
    GameMap gameMap;

    @BeforeEach
    public void setup() {
        player = new Player(new Position(5, 5));

        int width = Config.getInt("width_in_tiles");
        int height = Config.getInt("height_in_tiles");
        Block[] blocks = new Block[width * height];
        int index = 0;
        for (int x = 0; x <width; x++) {
            for (int y = 0; y < height; y++) {
                blocks[index++] = new Block(BlockType.FloorBlock, new Position(x, y));
            }
        }

        gameMap = new GameMap(blocks, new Item[0]);
        Controls controls = new Controls();

        playerController = new PlayerController(
            gameMap, player, controls,
            () -> {}, () -> {}
        );
    }

    @Test
    public void movePlayerMovesPlayer() {
        Position initialPosition = player.getPosition();

        playerController.movePlayer(Direction.UP);
        Position newPosition = player.getPosition();

        Position expectedPosition = new Position(initialPosition.getX(), initialPosition.getY() - 1);
        Assertions.assertEquals(expectedPosition, newPosition);
    }

    @Test
    public void movePlayerGetsBlockedByWall() {
        Position initialPosition = player.getPosition();
        Position nextPosition = new Position(initialPosition.getX(), initialPosition.getY() - 1);

        Block oldBlock = gameMap.getBlockOnPosition(nextPosition);
        gameMap.remove(oldBlock);
        Block wallBlock = new Block(BlockType.WallBlock, nextPosition);
        gameMap.add(wallBlock);

        playerController.movePlayer(Direction.UP);
        Position newPosition = player.getPosition();

        Assertions.assertEquals(initialPosition, newPosition);
    }

    @Test
    public void movePlayerPicksUpItem() {
        Position initialPosition = player.getPosition();
        Position itemPosition = new Position(initialPosition.getX(), initialPosition.getY() - 1);

        ItemType placedItemType = ItemType.PotionOfFrost;
        Item item = new Item(placedItemType, itemPosition);
        gameMap.add(item);

        playerController.movePlayer(Direction.UP);

        boolean hasItem = player.getInventory().has(placedItemType, 1);

        Assertions.assertTrue(hasItem);
    }
}
