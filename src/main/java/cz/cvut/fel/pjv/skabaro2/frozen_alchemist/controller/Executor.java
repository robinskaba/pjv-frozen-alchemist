package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.*;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Inventory;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.ItemType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.Game;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.*;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.common.GameAlert;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.data.*;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.views.GameView;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.views.MenuView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The Executor class serves as the main controller for the game. It manages the game lifecycle,
 * including loading the lobby, game, and end scenes, as well as handling rendering and user interactions.
 */
public class Executor {
    private final Stage stage;
    private final Screen screen;

    private Game game;
    private GameView gameView;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Constructor for the Executor class.
     *
     * @param stage The primary stage for the application.
     */
    public Executor(Stage stage) {
        this.stage = stage;

        // initiating main window (via Screen class)
        screen = new Screen(stage, 1038, 612, "Frozen Alchemist", "/ui/game_icon.png");
        MapLoader.setAllowedMapDimensions(16, 9);

        loadLobby();
    }

    /**
     * Loads the game lobby scene.
     */
    private void loadLobby() {
        // creates game lobby using the MenuView class
        MenuView lobby = new MenuView(screen.getWidth(), screen.getHeight(), "/ui/lobby_background.png");

        // binds controller functions to lobby buttons
        lobby.addButton("Play Game", this::loadGame);
        lobby.addButton("Reset Progress", this::resetProgress);

        // sets scene to lobby
        Scene scene = new Scene(lobby);
        screen.setScene(scene);

        LOGGER.info("Loaded lobby.");
    }

    /**
     * Loads the main game scene and initializes the game loop.
     */
    private void loadGame() {
        // prepares game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                RenderedTexture[] renderedTextures = getRenderedData(game.getEntities(), game.getMapWidth());
                gameView.render(renderedTextures);
            }
        };

        // prepares controls and loads textures to TextureManager
        Controls controls = new Controls();
        TextureManager.load();

        // creates game and passes controller functions for certain 'events'
        game = new Game(
                controls,
                this::loadEnd, // triggered when game is won
                this::emptyEquippedItemOverlay,
                this::getMenuData
        );

        // initiates game scene
        gameView = new GameView(screen.getWidth(), screen.getHeight(), this::getMenuData);
        Scene gameScene = new Scene(gameView);

        // bind registering keystrokes
        gameScene.setOnKeyPressed(e -> controls.keyPressed(e.getCode().toString()));
        gameScene.setOnKeyReleased(e -> controls.keyReleased(e.getCode().toString()));

        // only perform if game is running (it wouldn't run if last save is after game was won)
        if (game.isRunning()) {
            // bind saving progress
            stage.setOnCloseRequest(e -> game.save());

            // show game
            gameLoop.start();
            stage.setScene(gameScene);
        }

        LOGGER.info("Loaded game.");
    }

    /**
     * Loads the end scene, which is displayed when the game is won.
     */
    private void loadEnd() {
        // loads a game won scene using MenuView class
        MenuView endView = new MenuView(screen.getWidth(), screen.getHeight(), "/ui/game_won_background.png");
        Scene scene = new Scene(endView);
        stage.setScene(scene);

        LOGGER.info("Loaded end.");
    }

    /**
     * Retrieves the rendered data for all entities in the game.
     *
     * @param entities     The array of entities to render.
     * @param widthInTiles The width of the map in tiles.
     * @return An array of RenderedTexture objects representing the rendered entities.
     */
    public RenderedTexture[] getRenderedData(Entity[] entities, int widthInTiles) {
        List<RenderedTexture> renderedTextures = new LinkedList<>();

        // for every entity it calculates it's pixel position on the screen based off
        // it's game position and screen dimensions
        for (Entity entity : entities) {
            // subtype matters because some object textures are supposed to be smaller than a tile
            Object subtype = entity.getSubType();
            Texture texture = TextureManager.getTexture(subtype);

            // calculating offsets to center the texture in the tile
            int tileSizeInPixels = screen.getWidth() / widthInTiles;
            Position position = entity.getPosition();
            double offset = texture.getScale() < 1f ?
                    (tileSizeInPixels - texture.getScale() * tileSizeInPixels) / 2f : 0;
            double x = position.getX() * tileSizeInPixels + offset;
            double y = position.getY() * tileSizeInPixels + offset;

            PixelPosition pixelPosition = new PixelPosition(x, y);
            renderedTextures.add(new RenderedTexture(texture, pixelPosition));
        }

        return renderedTextures.toArray(new RenderedTexture[0]);
    }

    /**
     * Updates the menu data for the game view, including inventory and crafting items.
     */
    private void getMenuData() {
        ArrayList<MenuItem> inventoryData = new ArrayList<>();
        ArrayList<MenuItem> craftingData = new ArrayList<>();

        // prepares MenuItem object for every item in player's inventory
        Inventory inventory = game.getPlayer().getInventory();
        Map<ItemType, Integer> inventoryItems = inventory.getContent();
        for (Map.Entry<ItemType, Integer> entry : inventoryItems.entrySet()) {
            ItemType itemType = entry.getKey();
            int amount = entry.getValue();

            // fetches image of item via TextureManager
            Image image = TextureManager.getTexture(itemType).getImage();

            for (int i = 0; i < amount; i++) {
                // binding functions to when player click's the item (he wants to equip it)
                Runnable equipItem = () -> {
                    inventory.setEquippedItemType(itemType); // equip selected item
                    gameView.showMenus(false);
                    gameView.setButtonOverlayImage(image);
                };

                // controller function for when player click's the
                Runnable onHintClick = () -> gameView.showItemInfo(itemType.getName(), itemType.getDescription());
                MenuItem slot = new MenuItem(image, equipItem, onHintClick);

                inventoryData.add(slot);
            }
        }

        // creates MenuItem slots for items player can craft
        for (ItemType itemType : ItemType.values()) {
            Map<ItemType, Integer> recipe = itemType.getRecipe();
            if (recipe == null) continue; // non-craftable items

            // calculating if he can afford the item
            boolean canCraft = inventory.canCraft(itemType);

            if (canCraft) {
                Image image = TextureManager.getTexture(itemType).getImage();

                // bindings events for when player click's the item (he wants to craft it)
                Runnable craftItem = () -> {
                    inventory.craft(itemType);

                    // refresh ui
                    if (inventory.getEquippedItemType() == null) gameView.setButtonOverlayImage(null);
                    getMenuData();
                };

                Runnable showItemInfo = () -> gameView.showItemInfo(itemType.getName(), itemType.getDescription());
                MenuItem slot = new MenuItem(image, craftItem, showItemInfo);

                craftingData.add(slot);
            }
        }

        // creating MenuData object for the view
        MenuData menuData = new MenuData(
                inventoryData.toArray(new MenuItem[0]),
                craftingData.toArray(new MenuItem[0])
        );

        // updating view with these data
        gameView.setMenuData(menuData);
        gameView.updateMenus();
    }

    /**
     * Removes the equipped item overlay from the game view.
     */
    private void emptyEquippedItemOverlay() {
        // removes equipped item overlay
        gameView.setButtonOverlayImage(null);
    }

    /**
     * Resets the player's progress and displays a confirmation alert.
     */
    private void resetProgress() {
        LOGGER.info("Triggering progress reset.");

        ProgressFileManager.resetProgress();

        // alert player using GameAlert
        GameAlert gameAlert = new GameAlert(stage.getScene(), Alert.AlertType.CONFIRMATION);
        gameAlert.setTitle("Progress Reset");
        gameAlert.setHeaderText(null);
        gameAlert.setContentText("Your progress has been reset.");
        gameAlert.showAndWait();
    }
}
