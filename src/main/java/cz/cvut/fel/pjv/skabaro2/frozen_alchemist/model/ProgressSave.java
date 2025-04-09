package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import java.util.Map;

public class ProgressSave {
    private final int level;
    private final Map<ItemType, Integer> inventoryContent;
    private final String levelTextRepresentation;

    public ProgressSave(int level, Map<ItemType, Integer> inventoryContent, String levelTextRepresentation) {
        this.level = level;
        this.inventoryContent = inventoryContent;
        this.levelTextRepresentation = levelTextRepresentation;
    }

    public int getLevel() {
        return level;
    }

    public Map<ItemType, Integer> getInventoryContent() {
        return inventoryContent;
    }

    public String getLevelTextRepresentation() {
        return levelTextRepresentation;
    }
}
