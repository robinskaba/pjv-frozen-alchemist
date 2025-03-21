package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

public class Item {
    protected final String name;
    protected final String description;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
