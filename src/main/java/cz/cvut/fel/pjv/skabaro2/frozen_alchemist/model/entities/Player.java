package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;

/**
 * Represents the player in the game.
 * The player has an inventory, can levitate for a limited number of steps, and has a direction in which he is facing.
 */
public class Player extends Entity {
    private final Inventory inventory = new Inventory();
    private int levitatingStepsLeft = 0;
    private Direction direction = Direction.RIGHT;

    /**
     * Constructs a new Player object with the specified position.
     *
     * @param position The initial position of the player.
     */
    public Player(Position position) {
        super(EntityType.PLAYER, position);
    }

    /**
     * Checks if the player is currently levitating.
     *
     * @return True if the player is levitating, false otherwise.
     */
    public boolean isLevitating() {
        return levitatingStepsLeft > 0;
    }

    /**
     * Decreases the number of levitation steps left by one.
     */
    public void useLevitationStep() {
        levitatingStepsLeft--;
    }

    /**
     * Sets the number of levitation steps the player can take.
     *
     * @param steps The number of levitation steps to set.
     */
    public void setLevitationSteps(int steps) {
        levitatingStepsLeft = steps;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Gets the subtype of the player, which is his current direction.
     *
     * @return The direction the player is facing.
     */
    @Override
    public Object getSubType() {
        return direction;
    }
}