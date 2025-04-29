package cz.cvut.fel.pjv.skabaro2.frozen_alchemist;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Inventory;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.ItemType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class InventoryTests {
    Inventory inventory;

    @BeforeEach
    public void setup() {
        inventory = new Inventory();
    }

    @Test
    public void addAddsItemType() {
        ItemType typeToAdd = ItemType.Feather;

        inventory.add(typeToAdd);

        boolean hasItem = inventory.has(typeToAdd, 1);

        Assertions.assertTrue(hasItem);
    }

    @Test
    public void removeRemovesItemType() {
        ItemType typeToRemove = ItemType.Feather;

        int initialAmount = 3;
        for (int i = 0; i < initialAmount; i++) {
            inventory.add(typeToRemove);
        }
        inventory.remove(typeToRemove, 1);

        boolean hasItem = inventory.has(typeToRemove, initialAmount - 1);

        Assertions.assertTrue(hasItem);
    }

    @Test
    public void hasChecksIfHasSetAmountOfItemTypes() {
        ItemType typeToCheck = ItemType.Feather;

        int initialAmount = 3;
        for (int i = 0; i < initialAmount; i++) {
            inventory.add(typeToCheck);
        }

        boolean hasItems = inventory.has(typeToCheck, initialAmount);

        Assertions.assertTrue(hasItems);
    }

    @Test
    public void equippingItemTypeEquipsItemType() {
        ItemType typeToEquip = ItemType.Feather;

        inventory.setEquippedItemType(typeToEquip);

        ItemType equippedItem = inventory.getEquippedItemType();

        Assertions.assertEquals(typeToEquip, equippedItem);
    }

    @Test
    public void settingContentSetsContent() {
        Map<ItemType, Integer> contentToSet = Map.of(
            ItemType.Feather, 5,
            ItemType.MysticVine, 10
        );

        inventory.setContent(contentToSet);

        Assertions.assertEquals(contentToSet, inventory.getContent());
    }
}
