package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

public class Player extends Entity {
    private final Inventory inventory = new Inventory();
    private int levitatingStepsLeft = 0;
    private PlayerDirection playerDirection = PlayerDirection.RIGHT;

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

    public void setDirection(PlayerDirection playerDirection) {
        this.playerDirection = playerDirection;
    }

    @Override
    public Object getSubType() {
        return playerDirection;
    }
}
