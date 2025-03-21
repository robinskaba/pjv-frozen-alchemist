package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Item;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.types.Direction;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.ItemType;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameMap {
    private final ArrayList<Entity> entities = new ArrayList<>();

    public GameMap(Player player) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
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

    public void loadLevel(int level) {
        InputStream inputStream = getClass().getResourceAsStream("/levels/" + level + ".txt");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
