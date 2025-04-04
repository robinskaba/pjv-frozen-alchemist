package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.*;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.*;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

public class Executor {
    private final Stage stage;
    private final Screen screen;

    private Game game;
    private GameView gameView;

    public Executor(Stage stage) {
        this.stage = stage;

        screen = new Screen(stage, "Frozen Alchemist", "/textures/potion_of_levitation.png");

        loadLobby();
    }

    private void loadLobby() {
        MenuView lobby = new MenuView("/ui/background_w_title.png");
        lobby.addButton("Play Game", this::loadGame);
        lobby.addButton("Reset Progress", () -> System.out.println("Reset Progress"));

        Scene scene = new Scene(lobby);
        stage.setScene(scene);
    }

    private void loadGame() {
        Controls controls = new Controls();

        game = new Game(
            controls,
            this::loadEnd,
            this::updateInventoryButtonOverlay,
            this::getMenuData
        );

        TextureManager.load();
        gameView = new GameView(this::getMenuData);
        Scene gameScene = new Scene(gameView);

        // bind registering keystrokes
        gameScene.setOnKeyPressed(e -> {
            controls.keyPressed(e.getCode().toString());
        });
        gameScene.setOnKeyReleased(e -> {
            controls.keyReleased(e.getCode().toString());
        });

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                RenderedTexture[] renderedTextures = Visualizer.getRenderedData(game.getEntities());
                gameView.render(renderedTextures);
            }
        };

        gameLoop.start();

        screen.setScene(gameScene);
    }

    private void loadEnd() {
        MenuView endView = new MenuView("/ui/end_screen_background.png");
        Scene scene = new Scene(endView);
        stage.setScene(scene);
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
                inventoryData.add(
                    new MenuItem(
                        image,
                        () -> {
                            inventory.setEquippedItemType(itemType); // equip selected item
                            gameView.showMenus(false);
                            gameView.setButtonOverlayImage(image);
                        },
                        () -> gameView.showItemInfo(itemType.getName(), itemType.getDescription())
                    )
                );
            }
        }

        for (ItemType itemType : ItemType.values()) {
            Map<ItemType, Integer> recipe = itemType.getRecipe();
            if (recipe == null) continue; // non-craftable items

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
                MenuItem craftableItem = new MenuItem(
                    image,
                    () -> {
                        // use up items in inventory
                        for (Map.Entry<ItemType, Integer> component : recipe.entrySet()) {
                            inventory.remove(component.getKey(), component.getValue());
                        }

                        // add crafted item to inventory
                        inventory.add(itemType);
                        if (inventory.getEquippedItemType() == null) gameView.setButtonOverlayImage(null);
                        getMenuData();
                    },
                    () -> gameView.showItemInfo(itemType.getName(), itemType.getDescription())
                );
                craftingData.add(craftableItem);
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
}
