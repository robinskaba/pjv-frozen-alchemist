package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Item;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.EntityType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.ItemType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final ArrayList<Entity> entities = new ArrayList<>();

    private final int widthInTiles = Config.getInt("width_in_tiles");
    private final int heightInTiles = Config.getInt("height_in_tiles");

    public GameMap(Player player, Block[] blocks, Item[] items) {
        entities.addAll(List.of(blocks));
        entities.addAll(List.of(items));
        entities.add(player);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public boolean isPositionInMap(Position position) {
        return position.getX() >= 0 && position.getX() < widthInTiles && position.getY() >= 0 && position.getY() < heightInTiles;
    }

    public BlockType getBlockTypeOnPosition(Position position) {
        for (Entity entity : entities) {
            if (entity.getPosition().equals(position) && entity.getEntityType().equals(EntityType.BLOCK)) return (BlockType) entity.getSubtype();
        }
        return null;
    }
}
