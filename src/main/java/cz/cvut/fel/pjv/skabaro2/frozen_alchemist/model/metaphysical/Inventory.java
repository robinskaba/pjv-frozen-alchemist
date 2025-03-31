package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical;

import java.util.*;

public class Inventory {
    private final Map<InventoryItem, Integer> content = new HashMap<>();
    private InventoryItem equippedItem;

    public void add(InventoryItem inventoryItem) {
        int amount = content.getOrDefault(inventoryItem, 0);
        content.put(inventoryItem, amount + 1);
        System.out.println("Inventory item " + inventoryItem + " added to the content");
    }

    public void remove(InventoryItem inventoryItem, int amount) throws Exception {
        int currentAmount = content.getOrDefault(inventoryItem, 0);
        int newAmount = currentAmount - amount;

        if (newAmount < 0) throw new Exception("Item amount should not become negative.");
        if (newAmount == 0) {
            content.remove(inventoryItem);
        } else {
            content.put(inventoryItem, newAmount);
        }
    }

    public Map<InventoryItem, Integer> getContent() {
        return content;
    }

    public InventoryItem getEquippedItem() {
        return equippedItem;
    }

    public void setEquippedItem(InventoryItem equippedItem) {
        this.equippedItem = equippedItem;
    }
}
