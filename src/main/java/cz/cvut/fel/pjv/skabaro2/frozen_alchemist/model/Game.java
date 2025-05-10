package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.LevelData;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.ProgressSave;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Player;

import java.util.List;
import java.util.logging.Logger;

/**
 * Represents the main game logic and state management for the Frozen Alchemist game.
 */
public class Game {
    private final static Logger LOGGER = Logger.getLogger(Game.class.getName());

    private final Runnable onGameEnd;
    private final Runnable onItemUse;
    private final Runnable onItemPickup;

    private final Controls controls;
    private final Player player = new Player(new Position(0, 0));
    private final ProgressSave progressSave = ProgressFileManager.load();

    private GameMap gameMap;
    private PlayerController playerController;
    private int currentLevel = 0;
    private boolean isRunning = true;

    /**
     * Constructs a new Game instance.
     *
     * @param controls     The controls for player input.
     * @param onGameEnd    Callback to execute when the game ends.
     * @param onItemUse    Callback to execute when an item is used.
     * @param onItemPickup Callback to execute when an item is picked up.
     */
    public Game(Controls controls, Runnable onGameEnd, Runnable onItemUse, Runnable onItemPickup) {
        this.controls = controls;
        this.onGameEnd = onGameEnd;
        this.onItemUse = onItemUse;
        this.onItemPickup = onItemPickup;

        if(controls == null || onGameEnd == null || onItemUse == null || onItemPickup == null) {
            LOGGER.warning("Game initialized without controls or a callback.");
        }

        // load progress if available and initialize the game state
        if (progressSave != null) {
            currentLevel = progressSave.getLevel() - 1;
            player.getInventory().setContent(progressSave.getInventoryContent());

            LOGGER.info("Game used a progress save.");
        }

        setupPlayerController();
        loadNextLevel();

        LOGGER.info("Game initialized successfully.");
    }

    /**
     * Sets up the player controller and binds loading next level to entering ExitBlock.
     */
    private void setupPlayerController() {
        playerController = new PlayerController(gameMap, player, controls, onItemUse, onItemPickup);
        playerController.onBlockEnter(BlockType.ExitBlock, this::loadNextLevel);
    }

    /**
     * Loads the next level in the game. Ends the game if there are no more levels.
     */
    private void loadNextLevel() {
        currentLevel++;
        if (currentLevel > MapLoader.getAmountOfLevels()) {
            isRunning = false;
            onGameEnd.run();
            return;
        }

        // load the level data based on progress or the current level
        LevelData levelData;
        if (progressSave != null && progressSave.getLevel() == currentLevel) {
            levelData = MapLoader.getLevelData(progressSave.getLevelTextRepresentation());
        } else {
            levelData = MapLoader.getLevelData(currentLevel);
        }

        // initialize the game map and player position for the new level
        gameMap = new GameMap(levelData.mapWidth(), levelData.mapHeight(), levelData.blocks(), levelData.items());
        player.setPosition(levelData.initialPlayerPosition());

        playerController.updateGameMap(gameMap);

        LOGGER.info(String.format("Level %d loaded into game.", currentLevel));
    }

    /**
     * Retrieves all entities currently in the game, including the player.
     *
     * @return An array of all entities in the game, or null if the game map is not initialized.
     */
    public Entity[] getEntities() {
        if (gameMap == null) return null;
        List<Entity> entities = gameMap.getEntities();
        entities.add(player);
        return entities.toArray(new Entity[0]);
    }

    /**
     * Retrieves the player entity.
     *
     * @return The player entity.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Saves the current game progress to a file.
     */
    public void save() {
        ProgressFileManager.save(currentLevel, player, gameMap);
    }

    /**
     * Checks if the game is currently running.
     *
     * @return True if the game is running, false otherwise.
     */
    public boolean isRunning() {
        return isRunning;
    }

    public int getMapWidth() {
        return gameMap.getWidth();
    }
}