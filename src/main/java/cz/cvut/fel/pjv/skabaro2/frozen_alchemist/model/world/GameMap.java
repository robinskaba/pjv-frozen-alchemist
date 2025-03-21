package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.types.Direction;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameMap {
    private final ArrayList<Entity> entities = new ArrayList<>();

    public GameMap(Player player) {
        entities.add(player);
        entities.add(new Block(BlockType.Rubble, new Position(0, 0)));
    }

    public void loadLevel(int level) {
        InputStream inputStream = getClass().getResourceAsStream("/levels/" + level + ".txt");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
