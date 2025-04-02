package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.logic;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical.Inventory;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.GameMap;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.MapLoader;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Item;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.BlockType;

import java.util.ArrayList;

public class Game {
    private final Runnable onGameEnded;
    private final Runnable onItemPickup;
    private final Runnable onItemUsed;
    private final Controls controls;

    private final Player player = new Player(new Position(0, 0)); // lets assume he will just setPosition on new level
    private final MapLoader mapLoader = new MapLoader();

    private int currentLevel;
    private GameMap currentMap;
    private PlayerController playerController;

    public Game(Controls controls, Runnable onGameEnded, Runnable onItemPickup, Runnable onItemUsed) {
        this.controls = controls;
        this.onGameEnded = onGameEnded;
        this.onItemPickup = onItemPickup;
        this.onItemUsed = onItemUsed;

        setupGameLogic();

        setStartingLevel();
        loadLevel();
    }

    public Entity[] getEntities() {
        ArrayList<Entity> entities = currentMap.getEntities();
        return entities.toArray(new Entity[0]);
    }

    public Inventory getPlayerInventory() {
        return player.getInventory();
    }

    private void setStartingLevel() {
        // TODO Load from progress file at which level Player should start
        currentLevel = 1;
    }

    private void loadLevel() {
        currentMap = mapLoader.buildMap(currentLevel);
        currentMap.setPlayer(player);
        playerController.updateGameMap(currentMap);
    }

    private void goToNextLevel() {
        currentLevel++;
        if (currentLevel > mapLoader.getAmountOfLevels()) {
            onGameEnded.run();
            return;
        }

        loadLevel();
    }

    private void setupGameLogic() {
        playerController = new PlayerController(currentMap, player, controls, onItemUsed);

        playerController.onBlockEnter(BlockType.Exit, () -> {
            System.out.println("Exit!!");
            goToNextLevel();
        });
        playerController.onBlockEnter(BlockType.Floor, () -> {
            // item picking up logic (no connection to view? perhaps in model?)
            Item itemOnFloor = currentMap.getItemOnPosition(player.getPosition());
            if (itemOnFloor == null) return;
            player.receiveItem(itemOnFloor);
            currentMap.remove(itemOnFloor);
            onItemPickup.run();
        });
    }
}
