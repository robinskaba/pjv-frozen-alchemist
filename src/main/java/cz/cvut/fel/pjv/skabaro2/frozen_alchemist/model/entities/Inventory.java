package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities;

import java.util.*;
import java.util.logging.Logger;

/**
 * The Inventory class represents a player's inventory in the game.
 * It manages the items, their quantities, and the currently equipped item.
 */
public class Inventory {
    private Map<ItemType, Integer> content = new HashMap<>();
    private ItemType equippedItemType;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Adds an item to the inventory. If the item already exists, its quantity is incremented.
     *
     * @param itemType The type of item to add.
     */
    public void add(ItemType itemType) {
        int amount = content.getOrDefault(itemType, 0);
        content.put(itemType, amount + 1);

        LOGGER.finer(String.format("Added %s to inventory. New amount: %d", itemType, amount + 1));
    }

    /**
     * Removes a specified amount of an item from the inventory. If the quantity reaches zero,
     * the item is removed entirely. If the removed item is equipped, it is unequipped.
     *
     * @param itemType The type of item to remove.
     * @param amount   The amount of the item to remove.
     */
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

    /**
     * Retrieves the current content of the inventory.
     *
     * @return A map containing item types and their respective quantities.
     */
    public Map<ItemType, Integer> getContent() {
        return content;
    }

    /**
     * Sets the content of the inventory.
     *
     * @param content A map containing item types and their respective quantities.
     */
    public void setContent(Map<ItemType, Integer> content) {
        this.content = content;
    }

    /**
     * Retrieves the currently equipped item type.
     *
     * @return The equipped item type, or null if no item is equipped.
     */
    public ItemType getEquippedItemType() {
        return equippedItemType;
    }

    /**
     * Sets the currently equipped item type.
     *
     * @param equippedItemType The item type to equip.
     */
    public void setEquippedItemType(ItemType equippedItemType) {
        this.equippedItemType = equippedItemType;
    }

    /**
     * Checks if the inventory contains at least a specified amount of a given item type.
     *
     * @param itemType The type of item to check.
     * @param amount   The required amount of the item.
     * @return True if the inventory contains the required amount, false otherwise.
     */
    public boolean has(ItemType itemType, int amount) {
        int currentAmount = content.getOrDefault(itemType, 0);
        return currentAmount >= amount;
    }

    /**
     * Crafts an item by consuming the required components from the inventory
     * and adding the crafted item to the inventory.
     *
     * @param itemType The type of item to craft.
     */
    public void craft(ItemType itemType) {
        // use up items in inventory
        for (Map.Entry<ItemType, Integer> component : itemType.getRecipe().entrySet()) {
            remove(component.getKey(), component.getValue());
        }

        // add crafted item to inventory
        add(itemType);

        LOGGER.info("Crafted " + itemType.getName() + ".");
    }

    /**
     * Checks if the inventory has enough components to craft a given item.
     *
     * @param itemType The type of item to check.
     * @return True if the item can be crafted, false otherwise.
     */
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