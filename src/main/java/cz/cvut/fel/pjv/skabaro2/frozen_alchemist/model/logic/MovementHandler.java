package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.logic;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Direction;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.GameMap;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.BlockType;

public class MovementHandler {
    private final GameMap gameMap;
    private final Player player;

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
            case Rubble, MeltableIce, RegularIce -> false;
            case Chasm -> player.isLevitating();
            default -> true;
        };
    }

    public void movePlayer(Direction direction) {
        Position position = player.getPosition().getPositionWithDirection(direction);
        if (!gameMap.isPositionInMap(position)) return;

        BlockType blockOnPosition = gameMap.getBlockTypeOnPosition(position);
        if (!canMoveTo(blockOnPosition)) return;

        player.setPosition(position);
        player.setFacingDirection(direction);
    }
}
