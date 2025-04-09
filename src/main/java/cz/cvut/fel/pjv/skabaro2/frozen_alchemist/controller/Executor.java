package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.*;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Entity;
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

public class Executor {
    private final Stage stage;
    private final Screen screen;

    private Game game;
    private GameView gameView;

    public Executor(Stage stage) {
        this.stage = stage;

        screen = new Screen(stage, "Frozen Alchemist", "/ui/game_icon.png");

        loadLobby();
    }

    private void loadLobby() {
        MenuView lobby = new MenuView("/ui/lobby_background.png");
        lobby.addButton("Play Game", this::loadGame);
        lobby.addButton("Reset Progress", this::resetProgress);

        Scene scene = new Scene(lobby);
        stage.setScene(scene);
    }

    private void loadGame() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                RenderedTexture[] renderedTextures = getRenderedData(game.getEntities());
                gameView.render(renderedTextures);
            }
        };

        Controls controls = new Controls();
        TextureManager.load();

        game = new Game(
            controls,
            this::loadEnd,
            this::updateInventoryButtonOverlay,
            this::getMenuData
        );

        gameView = new GameView(this::getMenuData);
        Scene gameScene = new Scene(gameView);

        // bind registering keystrokes
        gameScene.setOnKeyPressed(e -> {
            controls.keyPressed(e.getCode().toString());
        });
        gameScene.setOnKeyReleased(e -> {
            controls.keyReleased(e.getCode().toString());
        });

        // only perform if game is running (is not if game won is saved)
        if (game.isRunning()) {
            // bind saving progress
            stage.setOnCloseRequest(e -> game.save());
            gameLoop.start();
            screen.setScene(gameScene);
        }
    }

    private void loadEnd() {
        MenuView endView = new MenuView("/ui/game_won_background.png");
        Scene scene = new Scene(endView);
        stage.setScene(scene);
    }

    public RenderedTexture[] getRenderedData(Entity[] entities) {
        List<RenderedTexture> renderedTextures = new LinkedList<>();

        for (Entity entity : entities) {
            Object subtype = entity.getSubType();
            Texture texture = TextureManager.getTexture(subtype);

            // calculating offset because some textures are too large
            int tileSizeInPixels = 64;
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

    private void getMenuData() {
        ArrayList<MenuItem> craftingData = new ArrayList<>();
        ArrayList<MenuItem> inventoryData = new ArrayList<>();

        Inventory inventory = game.getPlayer().getInventory();
        Map<ItemType, Integer> inventoryItems = inventory.getContent();
        for (Map.Entry<ItemType, Integer> entry : inventoryItems.entrySet()) {
            ItemType itemType = entry.getKey();
            int amount = entry.getValue();

            Image image = TextureManager.getTexture(itemType).getImage();

            for (int i = 0; i < amount; i++) {
                Runnable equipItem = () -> {
                    inventory.setEquippedItemType(itemType); // equip selected item
                    gameView.showMenus(false);
                    gameView.setButtonOverlayImage(image);
                };
                Runnable onHintClick = () -> gameView.showItemInfo(itemType.getName(), itemType.getDescription());
                MenuItem slot = new MenuItem(image, equipItem, onHintClick);

                inventoryData.add(slot);
            }
        }

        for (ItemType itemType : ItemType.values()) {
            Map<ItemType, Integer> recipe = itemType.getRecipe();
            if (recipe == null) continue; // non-craftable items

            // calculating if he can afford the item
            boolean hasAll = true;
            for (Map.Entry<ItemType, Integer> component : recipe.entrySet()) {
                ItemType componentType = component.getKey();
                int amount = component.getValue();
                if (!inventory.has(componentType, amount)) {
                    hasAll = false;
                    break;
                }
            }

            if (hasAll) {
                Image image = TextureManager.getTexture(itemType).getImage();
                Runnable craftItem = () -> {
                    // use up items in inventory
                    for (Map.Entry<ItemType, Integer> component : recipe.entrySet()) {
                        inventory.remove(component.getKey(), component.getValue());
                    }

                    // add crafted item to inventory
                    inventory.add(itemType);
                    if (inventory.getEquippedItemType() == null) gameView.setButtonOverlayImage(null);

                    // refresh ui
                    getMenuData();
                };
                Runnable showItemInfo = () -> gameView.showItemInfo(itemType.getName(), itemType.getDescription());

                MenuItem slot = new MenuItem(image, craftItem, showItemInfo);
                craftingData.add(slot);
            }
        }

        MenuData menuData = new MenuData(
            inventoryData.toArray(new MenuItem[0]),
            craftingData.toArray(new MenuItem[0])
        );

        gameView.setMenuData(menuData);
        gameView.updateMenus();
    }

    private void updateInventoryButtonOverlay() {
        gameView.setButtonOverlayImage(null);
    }

    private void resetProgress() {
        ProgressFileManager.resetProgress();
        GameAlert gameAlert = new GameAlert(stage.getScene(), Alert.AlertType.CONFIRMATION);
        gameAlert.setTitle("Progress Reset");
        gameAlert.setHeaderText(null);
        gameAlert.setContentText("Your progress has been reset.");
        gameAlert.showAndWait();
    }
}
