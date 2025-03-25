package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical;

import java.util.HashMap;
import java.util.Map;

public abstract class CraftableItem {
    protected final Map<InventoryItem, Integer> recipe;

    public CraftableItem(Map<InventoryItem, Integer> recipe) {
        this.recipe = recipe;
    }

    public boolean canAfford(Map<InventoryItem, Integer> inventoryItems) {
        for (Map.Entry<InventoryItem, Integer> recipeComponent : recipe.entrySet()) {
            int hasInInventory = inventoryItems.getOrDefault(recipeComponent.getKey(), 0);
            if (recipeComponent.getValue() > hasInInventory) return false;
        }

        return true;
    }
}
