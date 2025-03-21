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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameMap {
    private final ArrayList<Entity> entities = new ArrayList<>();

    private final int widthInTiles = Config.getInt("width_in_tiles");
    private final int heightInTiles = Config.getInt("height_in_tiles");

    public GameMap(Player player) {
        for (int i = 0; i < widthInTiles; i++) {
            for (int j = 0; j < heightInTiles; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    entities.add(new Block(BlockType.RegularIce, new Position(i, j)));
                } else if (i % 2 == 0 && j % 2 == 1) {
                    entities.add(new Block(BlockType.Rubble, new Position(i, j)));
                } else if (i % 2 == 0) {
                    entities.add(new Block(BlockType.Chasm, new Position(i, j)));
                } else {
                    entities.add(new Block(BlockType.Exit, new Position(i, j)));
                }
            }
        }

        entities.add(new Item(ItemType.EmberFlower, new Position(2, 4)));
        entities.add(player);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public boolean isPositionInMap(Position position) {
        return position.getX() >= 0 && position.getX() < widthInTiles && position.getY() >= 0 && position.getY() < heightInTiles;
    }
}
