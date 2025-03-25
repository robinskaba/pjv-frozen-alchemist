package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;

public class ProgressFileManager {
    public static Player loadPlayer() {
        return new Player(new Position(0, 0));
    }

    public static void saveInventory(Inventory inventory) {

    }

    public static Inventory loadInventory() {
        return null; // temp
    }

    public static void resetFile() {

    }
}
