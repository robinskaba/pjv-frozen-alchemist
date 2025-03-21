package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical;

import java.util.*;

public class Inventory {
    private final Map<Item, Integer> content = new HashMap<>();

    public void add(Item item) {
        int amount = content.getOrDefault(item, 0);
        content.put(item, amount + 1);
    }

    public void remove(Item item, int amount) throws Exception {
        int currentAmount = content.getOrDefault(item, 0);
        int newAmount = currentAmount - amount;

        if (newAmount < 0) throw new Exception("Item amount should not become negative.");
        if (newAmount == 0) {
            content.remove(item);
        } else {
            content.put(item, newAmount);
        }
    }
}
