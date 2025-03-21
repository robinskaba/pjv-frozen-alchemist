package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

public class Potion extends Item implements Craftable, Usable {
    public Potion(String name, String description) {
        super(name, description);
    }

    @Override
    public void use() {

    }
}
