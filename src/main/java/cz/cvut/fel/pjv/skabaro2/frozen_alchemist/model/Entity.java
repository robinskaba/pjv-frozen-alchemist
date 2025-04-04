package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

// Entities are things on the map
public abstract class Entity {
    protected EntityType entityType;
    protected Position position;

    public Entity(EntityType entityType, Position position) {
        this.entityType = entityType;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public abstract Object getSubType();

    @Override
    public String toString() {
        return "Entity{" +
                "type=" + entityType +
                ", position=" + position +
                '}';
    }
}
