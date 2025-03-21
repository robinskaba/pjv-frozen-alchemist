package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.EntityType;

// Parent class for things that appear on the map!!
public abstract class Entity {
    private final EntityType entityType;

    protected Position position;

    public Entity(EntityType entityType, Position position) {
        this.entityType = entityType;
        setPosition(position);
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract Object getSubtype();
}
