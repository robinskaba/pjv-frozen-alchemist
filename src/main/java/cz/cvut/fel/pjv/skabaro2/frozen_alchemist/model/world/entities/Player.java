package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical.Inventory;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical.InventoryItem;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.EntityType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Direction;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.ItemType;

public class Player extends Entity {
    private Direction facingDirection = Direction.RIGHT;
    private final Inventory inventory = new Inventory();

    public Player(Position startingPosition) {
        super(EntityType.PLAYER, startingPosition);
    }

    @Override
    public Object getSubtype() {
        return facingDirection;
    }

    public void setFacingDirection(Direction direction) {
        this.facingDirection = direction;
    }

    public boolean isLevitating() {
        return true;
    }

    public void receiveItem(Item item) {
        ItemType itemType = (ItemType) item.getSubtype();
        InventoryItem newInventoryItem = new InventoryItem(
            itemType.getName(),
            itemType.getDescription(),
            (ItemType) item.getSubtype()
        );
        inventory.add(newInventoryItem);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
