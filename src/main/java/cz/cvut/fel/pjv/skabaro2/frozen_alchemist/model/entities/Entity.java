package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;

/**
 * Represents an abstract entity in the game, which is any object that can exist on the map.
 */
public abstract class Entity {
    protected EntityType entityType;
    protected Position position;

    /**
     * Constructs a new Entity object.
     *
     * @param entityType The type of the entity.
     * @param position The position of the entity on the map.
     */
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

    /**
     * Gets the subtype of the entity. This method must be implemented by subclasses.
     *
     * @return The subtype of the entity.
     */
    public abstract Object getSubType();

    @Override
    public String toString() {
        return "Entity{" +
                "type=" + entityType +
                ", position=" + position +
                '}';
    }
}
