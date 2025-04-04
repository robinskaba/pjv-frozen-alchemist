package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import java.util.*;

public class Inventory {
    private final Map<ItemType, Integer> content = new HashMap<>();
    private ItemType equippedItemType;

    public void add(ItemType itemType) {
        int amount = content.getOrDefault(itemType, 0);
        content.put(itemType, amount + 1);
        System.out.println("Inventory item " + itemType + " added to the content");
    }

    public void remove(ItemType itemType, int amount) {
        int currentAmount = content.getOrDefault(itemType, 0);
        int newAmount = currentAmount - amount;

        if (newAmount == 0) {
            content.remove(itemType);
        } else {
            content.put(itemType, newAmount);
        }
    }

    public Map<ItemType, Integer> getContent() {
        return content;
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
