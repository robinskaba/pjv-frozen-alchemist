package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.ItemType;

import java.util.*;

public class Inventory {
    private final Map<ItemType, Integer> content = new HashMap<>();
    private ItemType equippedItemType;

    public void add(ItemType itemType) {
        int amount = content.getOrDefault(itemType, 0);
        content.put(itemType, amount + 1);
    }

    public void remove(ItemType itemType, int amount) {
        int currentAmount = content.getOrDefault(itemType, 0);
        int newAmount = currentAmount - amount;

        if (newAmount == 0) {
            content.remove(itemType);
            if (itemType == equippedItemType) equippedItemType = null;
        } else {
            content.put(itemType, newAmount);
        }
    }

    public Map<ItemType, Integer> getContent() {
        return content;
    }

    public void setContent(Map<ItemType, Integer> content) {
        this.content.clear();
        this.content.putAll(content);
    }

    public ItemType getEquippedItemType() {
        return equippedItemType;
    }

    public void setEquippedItemType(ItemType equippedItemType) {
        this.equippedItemType = equippedItemType;
    }

    public boolean has(ItemType itemType, int amount) {
        int currentAmount = content.getOrDefault(itemType, 0);
        return currentAmount >= amount;
    }
}
