package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import java.util.HashMap;
import java.util.Map;

public interface Craftable {
    Map<Item, Integer> recipe = new HashMap<>();
}
