package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Item;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final ArrayList<Entity> entities = new ArrayList<>();

    private final ArrayList<Block> blocks = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();
    private Player player = null;

    private final int widthInTiles = Config.getInt("width_in_tiles");
    private final int heightInTiles = Config.getInt("height_in_tiles");

    public GameMap(Block[] blocks, Item[] items) {
        this.blocks.addAll(List.of(blocks));
        this.items.addAll(List.of(items));

//        entities.addAll(List.of(blocks));
//        entities.addAll(List.of(items));
    }

    public ArrayList<Entity> getEntities() {
        ArrayList<Entity> entities = new ArrayList<>();
        entities.addAll(blocks);
        entities.addAll(items);
        entities.add(player);
        return entities;
    }

    public boolean isPositionInMap(Position position) {
        return position.getX() >= 0 && position.getX() < widthInTiles && position.getY() >= 0 && position.getY() < heightInTiles;
    }

    public Block getBlockOnPosition(Position position) {
        for (Block block : blocks) {
            if (block.getPosition().equals(position)) return block;
        }
        return null;
    }

    public Item getItemOnPosition(Position position) {
        for (Item item : items) {
            if (item.getPosition().equals(position)) return item;
        }
        return null;
    }

    public void remove(Block block) {
        blocks.remove(block);
    }

    public void remove(Item item) {
        items.remove(item);
    }

    public void add(Block block) {
        blocks.add(block);
    }

    public void add(Item item) {
        items.add(item);
    }

    public void setPlayer(Player player) {
        if (this.player != null) throw new RuntimeException("Player is already set.");
        this.player = player;
    }
}
