package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.*;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * Controls the player's interactions with the game world, including movement, item usage,
 * and interactions with blocks and items on the map.
 */
public class PlayerController {
    private final Map<BlockType, Runnable> onBlockEnterEvents = new HashMap<>();
    private final Runnable onItemUse;
    private final Runnable onItemPickup;
    private final Player player;

    private GameMap gameMap;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Constructs a PlayerController instance.
     *
     * @param gameMap     The game map the player interacts with.
     * @param player      The player entity.
     * @param controls    The controls for player input.
     * @param onItemUse   Callback executed when an item is used.
     * @param onItemPickup Callback executed when an item is picked up.
     */
    public PlayerController(GameMap gameMap, Player player, Controls controls, Runnable onItemUse, Runnable onItemPickup) {
        this.gameMap = gameMap;
        this.player = player;
        this.onItemUse = onItemUse;
        this.onItemPickup = onItemPickup;

        // bind movement controls to player actions
        controls.bindFunction("W", () -> this.movePlayer(Direction.UP));
        controls.bindFunction("S", () -> this.movePlayer(Direction.DOWN));
        controls.bindFunction("A", () -> this.movePlayer(Direction.LEFT));
        controls.bindFunction("D", () -> this.movePlayer(Direction.RIGHT));

        // bind item usage to the "E" key
        controls.bindFunction("E", this::useItem);
    }

    /**
     * Updates the game map the player interacts with.
     *
     * @param gameMap The new game map.
     */
    public void updateGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * Registers a function to be executed when the player enters a specific block type.
     *
     * @param blockType    The block type to trigger the function.
     * @param executedFunc The function to execute.
     */
    public void onBlockEnter(BlockType blockType, Runnable executedFunc) {
        onBlockEnterEvents.put(blockType, executedFunc);
    }

    /**
     * Executes the function associated with entering a specific block type.
     *
     * @param blockType The block type the player entered.
     */
    private void blockEntered(BlockType blockType) {
        Runnable assignedFunc = onBlockEnterEvents.get(blockType);
        if (assignedFunc != null) assignedFunc.run();
    }

    /**
     * Moves the player in the specified direction, handling interactions with blocks and items.
     *
     * @param direction The direction to move the player.
     */
    public void movePlayer(Direction direction) {
        LOGGER.finest("Moving player in direction: " + direction);

        player.setDirection(direction);

        Position currentPosition = player.getPosition();
        Position newPosition = new Position(currentPosition.getX(), currentPosition.getY());
        switch (direction) {
            case UP -> newPosition.decrementY();
            case DOWN -> newPosition.incrementY();
            case LEFT -> newPosition.decrementX();
            case RIGHT -> newPosition.incrementX();
        }

        // check if the new position is within the map bounds
        if (!gameMap.isPositionInMap(newPosition)) return;

        Block blockOnPosition = gameMap.getBlockOnPosition(newPosition);
        if (blockOnPosition == null) throw new RuntimeException("No block on position " + newPosition);

        BlockType blockTypeOnPosition = (BlockType) blockOnPosition.getSubType();
        switch (blockTypeOnPosition) {
            case BlockType.MeltableWallBlock, BlockType.RubbleBlock, BlockType.WallBlock, BlockType.Water -> {
                return; // prevent movement into these block types
            }
            case BlockType.ChasmBlock -> {
                if (player.isLevitating()) {
                    player.useLevitationStep();
                } else return; // prevent movement if not levitating
            }
        }

        // update the player's position and handle block bound functions
        player.setPosition(newPosition);
        blockEntered(blockTypeOnPosition);

        // handle item pickup if an item is present at the new position
        Item itemOnPosition = gameMap.getItemOnPosition(newPosition);
        if (itemOnPosition != null) {
            LOGGER.fine("Picked up item: " + itemOnPosition.getSubType());
            player.getInventory().add((ItemType) itemOnPosition.getSubType());
            gameMap.remove(itemOnPosition);

            onItemPickup.run();
        }
    }

    /**
     * Uses the currently equipped item, applying its effect to the game map or player.
     */
    private void useItem() {
        LOGGER.fine("Attempting to use item");

        // fetches item type of equipped item
        ItemType equippedItemType = player.getInventory().getEquippedItemType();
        if (equippedItemType == null) return;

        // gets the position of the block the player is facing
        Position facingPosition = player.getPosition().copy();
        switch (player.getSubType()) {
            case Direction.UP -> facingPosition.decrementY();
            case Direction.DOWN -> facingPosition.incrementY();
            case Direction.LEFT -> facingPosition.decrementX();
            case Direction.RIGHT -> facingPosition.incrementX();
            default -> throw new IllegalStateException("Unexpected value: " + player.getSubType());
        }

        // Use the equipped item based on its type.
        Supplier<Boolean> use = switch (equippedItemType) {
            case ItemType.PotionOfLevitation -> () -> ItemFunctions.grantLevitation(player);
            case ItemType.PotionOfFrost -> () -> ItemFunctions.freezeWater(gameMap, facingPosition);
            case ItemType.PotionOfPulverization -> () -> ItemFunctions.pulverizeRubble(gameMap, facingPosition);
            case ItemType.PotionOfMelting -> () -> ItemFunctions.meltIce(gameMap, facingPosition);
            default -> null;
        };

        // apply the item's effect and remove it from the inventory if used successfully (return of true)
        if (use != null && use.get()) {
            LOGGER.info("Using " + equippedItemType);
            player.getInventory().remove(equippedItemType, 1);

            onItemUse.run();
        }
    }
}