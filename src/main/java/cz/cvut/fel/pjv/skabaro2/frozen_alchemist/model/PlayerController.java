package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PlayerController {
    private final Map<BlockType, Runnable> onBlockEnterEvents = new HashMap<>();
    private final Runnable onItemUse;
    private final Runnable onItemPickup;
    private final Player player;

    private GameMap gameMap;

    public PlayerController(GameMap gameMap, Player player, Controls controls, Runnable onItemUse, Runnable onItemPickup) {
        this.gameMap = gameMap;
        this.player = player;
        this.onItemUse = onItemUse;
        this.onItemPickup = onItemPickup;

        // bind movement to controls
        controls.bindFunction("W", () -> this.movePlayer(PlayerDirection.UP));
        controls.bindFunction("S", () -> this.movePlayer(PlayerDirection.DOWN));
        controls.bindFunction("A", () -> this.movePlayer(PlayerDirection.LEFT));
        controls.bindFunction("D", () -> this.movePlayer(PlayerDirection.RIGHT));

        controls.bindFunction("E", this::useItem);
    }

    public void updateGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void onBlockEnter(BlockType blockType, Runnable executedFunc) {
        onBlockEnterEvents.put(blockType, executedFunc);
    }

    private void blockEntered(BlockType blockType) {
        Runnable assignedFunc = onBlockEnterEvents.get(blockType);
        if (assignedFunc != null) assignedFunc.run();
    }

    public void movePlayer(PlayerDirection playerDirection) {
        player.setDirection(playerDirection);

        Position currentPosition = player.getPosition();
        Position newPosition = new Position(currentPosition.getX(), currentPosition.getY());
        switch (playerDirection) {
            case UP: newPosition.decrementY(); break;
            case DOWN: newPosition.incrementY(); break;
            case LEFT: newPosition.decrementX(); break;
            case RIGHT: newPosition.incrementX(); break;
        }

        if (!gameMap.isPositionInMap(newPosition)) return;
        Block blockOnPosition = gameMap.getBlockOnPosition(newPosition);
        if (blockOnPosition == null) throw new RuntimeException("No block on position " + newPosition);

        BlockType blockTypeOnPosition = (BlockType) blockOnPosition.getSubType();
        switch (blockTypeOnPosition) {
            case BlockType.MeltableWallBlock, BlockType.RubbleBlock, BlockType.WallBlock, BlockType.Water -> {
                return;
            }
            case BlockType.ChasmBlock -> {
                if(player.isLevitating()) {
                    player.useLevitationStep();
                } else return;
            }
        };

        player.setPosition(newPosition);
        blockEntered(blockTypeOnPosition);

        Item itemOnPosition = gameMap.getItemOnPosition(newPosition);
        if (itemOnPosition != null) {
            player.getInventory().add((ItemType) itemOnPosition.getSubType());
            gameMap.remove(itemOnPosition);
            onItemPickup.run();
        }
    }

    private void useItem() {
        ItemType equippedItemType = player.getInventory().getEquippedItemType();
        if (equippedItemType == null) return;

        Position facingPosition = player.getPosition().copy();
        switch (player.getSubType()) {
            case PlayerDirection.UP -> facingPosition.decrementY();
            case PlayerDirection.DOWN -> facingPosition.incrementY();
            case PlayerDirection.LEFT -> facingPosition.decrementX();
            case PlayerDirection.RIGHT -> facingPosition.incrementX();
            default -> throw new IllegalStateException("Unexpected value: " + player.getSubType());
        }

        // supplier does not lose the return value of the functions while keeping easy function select
        Supplier<Boolean> use = switch (equippedItemType) {
            case ItemType.PotionOfLevitation -> () -> ItemFunctions.grantLevitation(player);
            case ItemType.PotionOfFrost -> () -> ItemFunctions.freezeWater(gameMap, facingPosition);
            case ItemType.PotionOfPulverization -> () -> ItemFunctions.pulverizeRubble(gameMap, facingPosition);
            case ItemType.PotionOfMelting -> () -> ItemFunctions.meltIce(gameMap, facingPosition);
            default -> null;
        };

        if (use != null && use.get()) {
            player.getInventory().remove(equippedItemType, 1);
            onItemUse.run();
        }
    }
}
