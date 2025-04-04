package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class GameView extends View {
    private final int menuWidth = 225;

    private Button mainMenuButton;
    private ImageView mainMenuButtonOverlayImage;
    private HBox menus;
    private StackPane itemInfoPanel;
    private Label itemTitle;
    private Label itemDescription;
    private final GridPane craftingGridPane = new GridPane();
    private final GridPane inventoryGridPane = new GridPane();

    private MenuData menuData;
    private final Runnable fetchMenuData;

    public GameView(Runnable fetchMenuData) {
        super();
        getStylesheets().add(getClass().getResource("/styles/game_scene.css").toExternalForm());

        this.fetchMenuData = fetchMenuData;
        setupInventoryButton();
        setupInventoryAndCraftingMenu();
        showMenus(false);
    }

    public void render(RenderedTexture[] renderedTextures) {
        gc.clearRect(0, 0, Config.getInt("window_width"), Config.getInt("window_height"));

        for (RenderedTexture renderedTexture : renderedTextures) {
            Texture texture = renderedTexture.getTexture();
            PixelPosition pixelPosition = renderedTexture.getPixelPosition();
            double size = texture.getSize();

            gc.drawImage(texture.getImage(), pixelPosition.getX(), pixelPosition.getY(), size, size);
        }
    }

    private void setupInventoryButton() {
        double size = 100;

        Image defaultImage = new Image(GameView.class.getResourceAsStream("/ui/crafting_overlay.png"));

        ImageView buttonImageView = new ImageView(defaultImage);

        mainMenuButton = new Button();
        mainMenuButton.getStyleClass().add("main-button");
        mainMenuButton.setGraphic(buttonImageView);
        mainMenuButton.setLayoutX(Config.getInt("window_width") - 150);
        mainMenuButton.setLayoutY(Config.getInt("window_height") - 175);
        mainMenuButton.setPrefWidth(size);
        mainMenuButton.setPrefHeight(size);
        mainMenuButton.setPadding(Insets.EMPTY);

        double overlayImageSizeRatio = 0.75;

        mainMenuButtonOverlayImage = new ImageView();
        mainMenuButtonOverlayImage.setFitWidth(size * overlayImageSizeRatio);
        mainMenuButtonOverlayImage.setFitHeight(size * overlayImageSizeRatio);
        mainMenuButtonOverlayImage.setVisible(false);

        mainMenuButtonOverlayImage.setLayoutX(mainMenuButton.getLayoutX() + size * (1 - overlayImageSizeRatio) / 2);
        mainMenuButtonOverlayImage.setLayoutY(mainMenuButton.getLayoutY() + size * (1 - overlayImageSizeRatio) / 2);

        mainMenuButtonOverlayImage.setMouseTransparent(true); // prevent blocking mouse input to button

        mainMenuButton.setOnAction(e -> showMenus(true));

        this.getChildren().addAll(mainMenuButton, mainMenuButtonOverlayImage);
    }

    private Region createMenuRegion(double width, double height) {
        Region background = new Region();
        background.setPrefSize(width, height);
        background.getStyleClass().add("menu");
        return background;
    }

    private StackPane createMenuPanel(String title, GridPane itemGrid) {
        double height = Config.getDouble("window_height") - 100;

        StackPane panel = new StackPane();

        Region background = createMenuRegion(menuWidth, height);

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("menu-title");

        itemGrid.getStyleClass().add("menu-grid");
        itemGrid.setAlignment(Pos.TOP_LEFT);

        VBox layout = new VBox(10, titleLabel, itemGrid);
        layout.setLayoutY(100);
        layout.setAlignment(Pos.TOP_CENTER);
        panel.getChildren().addAll(background, layout);

        return panel;
    }

    private StackPane createItemInfoPanel() {
        double width = menuWidth, height = 100;

        StackPane pane = new StackPane();
        pane.setMaxSize(width, height);

        Region background = createMenuRegion(width, height);

        VBox layout = new VBox();
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setPadding(new Insets(10, 15, 10, 15));

        itemTitle = new Label("ITEM NAME");
        itemTitle.getStyleClass().add("item-title");

        itemDescription = new Label("Description Description Description Description Description Description ");
        itemDescription.setMaxWidth(width);
        itemDescription.getStyleClass().add("item-description");

        layout.getChildren().addAll(itemTitle, itemDescription);
        pane.getChildren().addAll(background, layout);

        return pane;
    }

    private void setupInventoryAndCraftingMenu() {
        double topPosition = 25;

        StackPane inventoryPanel = createMenuPanel("Inventory", inventoryGridPane);
        StackPane craftingPanel = createMenuPanel("Crafting", craftingGridPane);
        itemInfoPanel = createItemInfoPanel();

        menus = new HBox();
        menus.setAlignment(Pos.TOP_CENTER);
        menus.setSpacing(25);
        menus.setLayoutX(250);
        menus.setLayoutY(topPosition);

        menus.getChildren().addAll(itemInfoPanel, craftingPanel, inventoryPanel);

        // HIDING UI WHEN CLICKING OUTSIDE MENUS
        this.setOnMousePressed(event -> {
            if (!menus.isVisible()) return; // do nothing if already hidden

            Node target = (Node) event.getTarget(); // base javafx ui component
            while (target != null) {
                if (target == inventoryPanel || target == craftingPanel || target == itemInfoPanel) return;
                target = target.getParent(); // try ancestor
            }

            // if loop passes then click was outside all menus
            showMenus(false);
        });

        this.getChildren().addAll(menus);
    }

    public void setButtonOverlayImage(Image image) {
        mainMenuButtonOverlayImage.setImage(image);
        mainMenuButtonOverlayImage.setVisible(true);
    }

    public void showMenus(boolean show) {
        itemInfoPanel.setVisible(false); // always hide before first hint click
        mainMenuButton.setVisible(!show);
        menus.setVisible(show);

        if (mainMenuButtonOverlayImage.getImage() != null) mainMenuButtonOverlayImage.setVisible(!show);
        if (show) {
            fetchMenuData.run();
            updateMenus();
        }
    }

    public void showItemInfo(String name, String description) {
        itemInfoPanel.setVisible(true);
        itemTitle.setText(name);
        itemDescription.setText(description);
    }

    private void updateMenu(MenuItem[] items, GridPane menuGrid) {
        int i = 0;

        Image hintIcon = new Image(getClass().getResourceAsStream("/ui/hint.png"));

        for (MenuItem menuItem : items) {
            Image itemImage = menuItem.getImage();
            Runnable onItemClick = menuItem.getOnItemClick();
            Runnable onHintClick = menuItem.getOnHintClick();

            ImageView itemImageView = new ImageView(itemImage);

            // limiting image size
            itemImageView.setPreserveRatio(true);
            itemImageView.setFitWidth(menuWidth / 5);
            itemImageView.setFitHeight(menuWidth / 5);

            Button itemSlot = new Button();
            itemSlot.getStyleClass().add("menu-item");
            itemSlot.setGraphic(itemImageView);
            itemSlot.setOnAction(event -> onItemClick.run());

            ImageView hintImageView = new ImageView(hintIcon);

            hintImageView.setPreserveRatio(true);
            hintImageView.setFitWidth(menuWidth / 15);
            hintImageView.setFitHeight(menuWidth / 15);

            Button hintButton = new Button();
            hintButton.setGraphic(hintImageView);
            hintButton.setPadding(new Insets(0));
            hintButton.getStyleClass().add("hint-button");
            hintButton.setOnAction(e -> onHintClick.run());

            StackPane slot = new StackPane();
            slot.setAlignment(Pos.TOP_RIGHT);

            slot.getChildren().addAll(itemSlot, hintButton);

            menuGrid.add(slot, i % 3, i / 3);

            i++;
        }
    }

    public void updateMenus() {
        craftingGridPane.getChildren().clear();
        inventoryGridPane.getChildren().clear();

        updateMenu(menuData.getCraftingData(), craftingGridPane);
        updateMenu(menuData.getInventoryData(), inventoryGridPane);
    }

    public void setMenuData(MenuData menuData) {
        this.menuData = menuData;
    }
}
