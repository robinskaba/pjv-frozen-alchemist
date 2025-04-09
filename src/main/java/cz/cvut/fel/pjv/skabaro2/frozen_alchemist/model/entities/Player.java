package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.Inventory;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;

public class Player extends Entity {
    private final Inventory inventory = new Inventory();
    private int levitatingStepsLeft = 0;
    private Direction direction = Direction.RIGHT;

    public Player(Position position) {
        super(EntityType.PLAYER, position);
    }

    public boolean isLevitating() {
        return levitatingStepsLeft > 0;
    }

    public void useLevitationStep() {
        levitatingStepsLeft--;
    }

    public void setLevitationSteps(int steps) {
        levitatingStepsLeft = steps;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Object getSubType() {
        return direction;
    }
}
