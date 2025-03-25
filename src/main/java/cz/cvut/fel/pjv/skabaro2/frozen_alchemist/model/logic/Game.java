package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.logic;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.GameMap;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.MapLoader;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Item;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.BlockType;

import java.util.ArrayList;

public class Game {
    private final Runnable onGameEnded;
    private final Controls controls;

    private final Player player = new Player(new Position(0, 0)); // lets assume he will just setPosition on new level
    private final MapLoader mapLoader = new MapLoader();

    private int currentLevel;
    private GameMap currentMap;
    private MovementHandler movementHandler;

    public Game(Controls controls, Runnable onGameEnded) {
        this.controls = controls;
        this.onGameEnded = onGameEnded;

        setupGameLogic();

        setStartingLevel();
        loadLevel();
    }

    public Entity[] getEntities() {
        ArrayList<Entity> entities = currentMap.getEntities();
        return entities.toArray(new Entity[0]);
    }

    private void setStartingLevel() {
        // TODO Load from progress file at which level Player should start
        currentLevel = 1;
    }

    private void loadLevel() {
        currentMap = mapLoader.buildMap(currentLevel);
        currentMap.setPlayer(player);
        movementHandler.updateGameMap(currentMap);
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
        movementHandler = new MovementHandler(currentMap, player, controls);

        movementHandler.onBlockEnter(BlockType.Exit, () -> {
            System.out.println("Exit!!");
            goToNextLevel();
        });
        movementHandler.onBlockEnter(BlockType.Floor, () -> {
            // item picking up logic (no connection to view? perhaps in model?)
            Item itemOnFloor = currentMap.getItemOnPosition(player.getPosition());
            if (itemOnFloor == null) return;
            player.receiveItem(itemOnFloor);
            currentMap.remove(itemOnFloor);
        });
    }
}
