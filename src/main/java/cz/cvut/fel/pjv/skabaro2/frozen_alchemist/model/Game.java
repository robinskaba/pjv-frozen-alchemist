package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import java.util.List;

public class Game {
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

    public Game(Controls controls, Runnable onGameEnd, Runnable onItemUse, Runnable onItemPickup) {
        this.controls = controls;
        this.onGameEnd = onGameEnd;
        this.onItemUse = onItemUse;
        this.onItemPickup = onItemPickup;

        if (progressSave != null) {
            currentLevel = progressSave.getLevel() - 1;
            player.getInventory().setContent(progressSave.getInventoryContent());
        }

        setupPlayerController();
        loadNextLevel();
    }

    private void setupPlayerController() {
        playerController = new PlayerController(gameMap, player, controls, onItemUse, onItemPickup);
        playerController.onBlockEnter(BlockType.ExitBlock, this::loadNextLevel);
    }

    private void loadNextLevel() {
        currentLevel++;
        if (currentLevel > MapLoader.getAmountOfLevels()) {
            isRunning = false;
            onGameEnd.run();
            return;
        }

        LevelData levelData;
        if (progressSave != null && progressSave.getLevel() == currentLevel) {
            levelData = MapLoader.getLevelData(progressSave.getLevelTextRepresentation());
        } else {
            levelData = MapLoader.getLevelData(currentLevel);
        }

        gameMap = new GameMap(levelData.getBlocks(), levelData.getItems());
        player.setPosition(levelData.getInitialPlayerPosition());

        playerController.updateGameMap(gameMap);
    }

    public Entity[] getEntities() {
        if (gameMap == null) return null;
        List<Entity> entities = gameMap.getEntities();
        entities.add(player);
        return entities.toArray(new Entity[0]);
    }

    public Player getPlayer() {
        return player;
    }

    public void save() {
        ProgressFileManager.save(currentLevel, player, gameMap);
    }

    public boolean isRunning() {
        return isRunning;
    }
}
