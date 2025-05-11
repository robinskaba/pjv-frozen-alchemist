package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities;

import java.util.*;
import java.util.logging.Logger;

public class Inventory {
    private Map<ItemType, Integer> content = new HashMap<>();
    private ItemType equippedItemType;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void add(ItemType itemType) {
        int amount = content.getOrDefault(itemType, 0);
        content.put(itemType, amount + 1);

        LOGGER.finer(String.format("Added %s to inventory. New amount: %d", itemType, amount + 1));
    }

    public void remove(ItemType itemType, int amount) {
        int currentAmount = content.getOrDefault(itemType, 0);
        int newAmount = currentAmount - amount;

        if (newAmount == 0) {
            // removing item from inventory all together
            content.remove(itemType);
            if (itemType == equippedItemType) equippedItemType = null;
        } else {
            // updating amount
            content.put(itemType, newAmount);
        }

        LOGGER.finer(String.format("Removed %d of %s from inventory. New amount: %d", amount, itemType, newAmount));
    }

    public Map<ItemType, Integer> getContent() {
        return content;
    }

    public void setContent(Map<ItemType, Integer> content) {
        this.content = content;
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

    public void craft(ItemType itemType) {
        // use up items in inventory
        for (Map.Entry<ItemType, Integer> component : itemType.getRecipe().entrySet()) {
            remove(component.getKey(), component.getValue());
        }

        // add crafted item to inventory
        add(itemType);

        LOGGER.info("Crafted " + itemType.getName() + ".");
    }

    public boolean canCraft(ItemType itemType) {
        // calculating if he can afford the item
        for (Map.Entry<ItemType, Integer> component : itemType.getRecipe().entrySet()) {
            ItemType componentType = component.getKey();
            int amount = component.getValue();
            if (!has(componentType, amount)) {
                return false;
            }
        }

        return true;
    }
}
