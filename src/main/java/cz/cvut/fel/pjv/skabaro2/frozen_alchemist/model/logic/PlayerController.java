package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.logic;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Direction;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical.InventoryItem;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical.ItemFunctions;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.GameMap;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.ItemCategory;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.ItemType;

import java.util.HashMap;
import java.util.Map;

public class PlayerController {
    private final Player player;
    private final ItemFunctions itemFunctions = new ItemFunctions();

    private GameMap gameMap;

    private final Map<BlockType, Runnable> onBlockEnterEvents = new HashMap<>();
    private final Runnable onItemUse;

    public PlayerController(GameMap gameMap, Player player, Controls controls, Runnable onItemUse) {
        this.gameMap = gameMap;
        this.player = player;
        this.onItemUse = onItemUse;

        // bind movement to controls
        controls.bindFunction("W", () -> this.movePlayer(Direction.UP));
        controls.bindFunction("S", () -> this.movePlayer(Direction.DOWN));
        controls.bindFunction("A", () -> this.movePlayer(Direction.LEFT));
        controls.bindFunction("D", () -> this.movePlayer(Direction.RIGHT));

        controls.bindFunction("E", this::useItem);
    }

    private boolean canMoveTo(BlockType block) {
        return switch (block) {
            case Rubble, MeltableIce, RegularIce, Water -> false;
            case Chasm -> player.isLevitating();
            default -> true;
        };
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

    public void movePlayer(Direction direction) {
        player.setFacingDirection(direction);

        Position position = player.getPosition().getPositionWithDirection(direction);
        if (!gameMap.isPositionInMap(position)) return;

        BlockType blockOnPosition = (BlockType) gameMap.getBlockOnPosition(position).getSubtype();
        if (!canMoveTo(blockOnPosition)) return;
        if (blockOnPosition == BlockType.Chasm) player.useLevitationStep();

        player.setPosition(position);
        blockEntered(blockOnPosition);
    }

    private void useItem() {
        InventoryItem equippedItem = player.getInventory().getEquippedItem();
        if (equippedItem == null) return;
        ItemType itemType = equippedItem.getItemType();

        if (itemType.getCategory() != ItemCategory.POTION) throw new IllegalStateException("Player should not have had item that is not potion equipped.");

        switch (itemType) {
            case ItemType.PotionOfLevitation -> itemFunctions.grantLevitation(player);
            case ItemType.PotionOfFrost -> itemFunctions.freezeWater(gameMap, player.getPosition().getPositionWithDirection(player.getFacingDirection()));
            case ItemType.PotionOfPulverization -> itemFunctions.pulverizeRubble(gameMap, player.getPosition().getPositionWithDirection(player.getFacingDirection()));
            case ItemType.PotionOfMelting -> itemFunctions.meltIce(gameMap, player.getPosition().getPositionWithDirection(player.getFacingDirection()));
            default -> System.err.println("Equipped item has no use: " + itemType);
        }

        player.getInventory().remove(equippedItem, 1);
        onItemUse.run();
    }
}
