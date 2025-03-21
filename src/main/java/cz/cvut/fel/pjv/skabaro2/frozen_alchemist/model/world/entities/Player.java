package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical.Inventory;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.EntityType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.types.Direction;

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

    public void move(Direction direction) {
        switch (direction) {
            case UP: position.setY(position.getY() - 1); break;
            case DOWN: position.setY(position.getY() + 1); break;
            case LEFT: position.setX(position.getX() - 1); break;
            case RIGHT: position.setX(position.getX() + 1); break;
        }
        this.facingDirection = direction;
    }
}
