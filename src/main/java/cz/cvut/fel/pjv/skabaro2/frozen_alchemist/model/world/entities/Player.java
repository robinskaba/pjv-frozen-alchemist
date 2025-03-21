package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical.Inventory;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.EntityType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Direction;

public class Player extends Entity {
    private Direction facingDirection;
    private final Inventory inventory = new Inventory();

    public Player(Direction startingDirection, Position startingPosition) {
        super(EntityType.PLAYER, startingPosition);
        this.facingDirection = startingDirection;
    }

    @Override
    public Object getSubtype() {
        return facingDirection;
    }

    public void setFacingDirection(Direction direction) {
        this.facingDirection = direction;
    }
}
