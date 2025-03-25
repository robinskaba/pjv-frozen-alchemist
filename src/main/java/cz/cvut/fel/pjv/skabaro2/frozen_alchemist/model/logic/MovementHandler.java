package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.logic;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Direction;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.GameMap;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.BlockType;

import java.util.HashMap;
import java.util.Map;

public class MovementHandler {
    private final Player player;

    private GameMap gameMap;

    private final Map<BlockType, Runnable> onBlockEnterEvents = new HashMap<>();

    public MovementHandler(GameMap gameMap, Player player, Controls controls) {
        this.gameMap = gameMap;
        this.player = player;

        // bind movement to controls
        controls.bindFunction("W", () -> this.movePlayer(Direction.UP));
        controls.bindFunction("S", () -> this.movePlayer(Direction.DOWN));
        controls.bindFunction("A", () -> this.movePlayer(Direction.LEFT));
        controls.bindFunction("D", () -> this.movePlayer(Direction.RIGHT));
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

        player.setPosition(position);
        blockEntered(blockOnPosition);
    }
}
