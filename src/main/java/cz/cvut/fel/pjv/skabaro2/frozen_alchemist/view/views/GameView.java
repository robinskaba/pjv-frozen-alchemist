package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.views;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.data.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Represents the main game view, including menus, inventory, crafting, and rendering logic.
 */
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

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Constructs a new GameView instance.
     *
     * @param fetchMenuData A runnable to fetch the menu data when required.
     */
    public GameView(int width, int height, Runnable fetchMenuData) {
        super(width, height);
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/game_scene.css")).toExternalForm());

        // saves event for fetching menu data from controller
        this.fetchMenuData = fetchMenuData;

        // setups ui
        setupInventoryButton();
        setupInventoryAndCraftingMenu();
        showMenus(false);
    }

    /**
     * Renders the given textures on the game canvas.
     *
     * @param renderedTextures An array of RenderedTexture objects to be drawn.
     */
    public void render(RenderedTexture[] renderedTextures) {
        // clear gc
        gc.clearRect(0, 0, width, height);

        // fill gc with images from rendered textures
        for (RenderedTexture renderedTexture : renderedTextures) {
            Texture texture = renderedTexture.texture();
            PixelPosition pixelPosition = renderedTexture.pixelPosition();
            double size = texture.getSize();

            gc.drawImage(texture.getImage(), pixelPosition.x(), pixelPosition.y(), size, size);
        }
    }

    /**
     * Sets up the inventory button and its overlay image.
     */
    private void setupInventoryButton() {
        double size = 100;

        // get inventory button ui
        InputStream inputStream = getClass().getResourceAsStream("/ui/action_button.png");
        if (inputStream == null) {
            LOGGER.warning("Failed to load action button image.");
            inputStream = getClass().getResourceAsStream("/textures/missing_texture.png");
        }
        Image defaultImage = new Image(Objects.requireNonNull(inputStream));

        ImageView buttonImageView = new ImageView(defaultImage);

        // main button ui settings
        mainMenuButton = new Button();
        mainMenuButton.getStyleClass().add("main-button");
        mainMenuButton.setGraphic(buttonImageView);
        mainMenuButton.setLayoutX(width - 150);
        mainMenuButton.setLayoutY(height - 175);
        mainMenuButton.setPrefWidth(size);
        mainMenuButton.setPrefHeight(size);
        mainMenuButton.setPadding(Insets.EMPTY);

        double overlayImageSizeRatio = 0.75;

        // overlay settings
        mainMenuButtonOverlayImage = new ImageView();
        mainMenuButtonOverlayImage.setFitWidth(size * overlayImageSizeRatio);
        mainMenuButtonOverlayImage.setFitHeight(size * overlayImageSizeRatio);
        mainMenuButtonOverlayImage.setVisible(false);
        // centering overlay
        mainMenuButtonOverlayImage.setLayoutX(mainMenuButton.getLayoutX() + size * (1 - overlayImageSizeRatio) / 2);
        mainMenuButtonOverlayImage.setLayoutY(mainMenuButton.getLayoutY() + size * (1 - overlayImageSizeRatio) / 2);
        // !! prevents blocking mouse input to the button
        mainMenuButtonOverlayImage.setMouseTransparent(true);

        // binds showing menus to button click
        mainMenuButton.setOnAction(e -> showMenus(true));

        this.getChildren().addAll(mainMenuButton, mainMenuButtonOverlayImage);
    }

    /**
     * Creates a menu region with the specified dimensions.
     *
     * @param height The height of the menu region.
     * @return A Region object styled as a menu.
     */
    private Region createMenuRegion(double height) {
        Region background = new Region();
        background.setPrefSize(menuWidth, height);
        background.getStyleClass().add("menu");
        return background;
    }

    /**
     * Creates a menu panel with a title and a grid for items.
     *
     * @param title    The title of the menu panel.
     * @param itemGrid The grid containing the menu items.
     * @return A StackPane representing the menu panel.
     */
    private StackPane createMenuPanel(String title, GridPane itemGrid) {
        StackPane panel = new StackPane();

        Region background = createMenuRegion(height - 100);

        // menu title label
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("menu-title");

        // menu grid
        itemGrid.getStyleClass().add("menu-grid");
        itemGrid.setAlignment(Pos.TOP_LEFT);

        // putting it in a vbox layout
        VBox layout = new VBox(10, titleLabel, itemGrid);
        layout.setLayoutY(100);
        layout.setAlignment(Pos.TOP_CENTER);

        panel.getChildren().addAll(background, layout);

        return panel;
    }

    /**
     * Creates the panel for displaying item information.
     *
     * @return A StackPane representing the item information panel.
     */
    private StackPane createItemInfoPanel() {
        int panelHeight = 100;

        StackPane pane = new StackPane();
        pane.setMaxSize(menuWidth, panelHeight);

        // using same background region
        Region background = createMenuRegion(panelHeight);

        // using vbox layout for item info panel
        VBox layout = new VBox();
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setPadding(new Insets(10, 15, 10, 15));

        // item title
        itemTitle = new Label();
        itemTitle.getStyleClass().add("item-title");

        // item description
        itemDescription = new Label();
        itemDescription.setMaxWidth(width);
        itemDescription.getStyleClass().add("item-description");

        layout.getChildren().addAll(itemTitle, itemDescription);
        pane.getChildren().addAll(background, layout);

        return pane;
    }

    /**
     * Sets up the inventory and crafting menus, including their layout and behavior.
     */
    private void setupInventoryAndCraftingMenu() {
        double topPosition = 25;

        // setups component menus
        StackPane inventoryPanel = createMenuPanel("Inventory", inventoryGridPane);
        StackPane craftingPanel = createMenuPanel("Crafting", craftingGridPane);
        itemInfoPanel = createItemInfoPanel();

        // putting them next to each other
        menus = new HBox();
        menus.setAlignment(Pos.TOP_CENTER);
        menus.setSpacing(25);
        menus.setLayoutX(250);
        menus.setLayoutY(topPosition);

        menus.getChildren().addAll(itemInfoPanel, craftingPanel, inventoryPanel);

        // hides the menus when clicking outside the menus
        this.setOnMousePressed(event -> {
            if (!menus.isVisible()) return; // do nothing if already hidden

            Node target = (Node) event.getTarget(); // conversion to base JavaFX UI component
            while (target != null) {
                // return (don't hide) if target was one of the menus
                if (target == inventoryPanel || target == craftingPanel || target == itemInfoPanel) return;

                // try ancestor
                target = target.getParent();
            }

            // if loop passes, then click was outside all menus
            showMenus(false);
        });

        this.getChildren().addAll(menus);
    }

    /**
     * Sets the overlay image for the main menu button.
     *
     * @param image The image to set as the overlay.
     */
    public void setButtonOverlayImage(Image image) {
        // sets buttons overlay image
        mainMenuButtonOverlayImage.setImage(image);
        mainMenuButtonOverlayImage.setVisible(true);
    }

    /**
     * Shows or hides the menus.
     *
     * @param show True to show the menus, false to hide them.
     */
    public void showMenus(boolean show) {
        itemInfoPanel.setVisible(false); // always hide before the first hint click
        mainMenuButton.setVisible(!show);
        menus.setVisible(show);

        if (mainMenuButtonOverlayImage.getImage() != null) mainMenuButtonOverlayImage.setVisible(!show);

        // update menus with current data on show
        if (show) {
            fetchMenuData.run();
            updateMenus();
        }
    }

    /**
     * Displays item information in the item info panel.
     *
     * @param name        The name of the item.
     * @param description The description of the item.
     */
    public void showItemInfo(String name, String description) {
        itemInfoPanel.setVisible(true);
        itemTitle.setText(name);
        itemDescription.setText(description);
    }

    /**
     * Updates the menu with the given items and grid layout.
     *
     * @param items    An array of MenuItem objects to populate the menu.
     * @param menuGrid The GridPane to populate with menu items.
     */
    private void updateMenu(MenuItem[] items, GridPane menuGrid) {
        int i = 0;

        // getting hint icon from resources
        Image hintIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ui/hint.png")));

        // puts each menu item in the grid
        for (MenuItem menuItem : items) {
            // fetching functions
            Image itemImage = menuItem.image();
            Runnable onItemClick = menuItem.onItemClick();
            Runnable onHintClick = menuItem.onHintClick();

            ImageView itemImageView = new ImageView(itemImage);

            // limiting image size
            itemImageView.setPreserveRatio(true);
            itemImageView.setFitWidth((double) menuWidth / 5);
            itemImageView.setFitHeight((double) menuWidth / 5);

            // building item slot
            Button itemSlot = new Button();
            itemSlot.getStyleClass().add("menu-item");
            itemSlot.setGraphic(itemImageView);
            itemSlot.setOnAction(event -> onItemClick.run());

            // setting up hint button
            ImageView hintImageView = new ImageView(hintIcon);
            hintImageView.setPreserveRatio(true);
            hintImageView.setFitWidth((double) menuWidth / 15);
            hintImageView.setFitHeight((double) menuWidth / 15);
            Button hintButton = new Button();
            hintButton.setGraphic(hintImageView);
            hintButton.setPadding(new Insets(0));
            hintButton.getStyleClass().add("hint-button");

            // show item info on hint button click
            hintButton.setOnAction(e -> onHintClick.run());

            // putting it in the top right corner
            StackPane slot = new StackPane();
            slot.setAlignment(Pos.TOP_RIGHT);

            slot.getChildren().addAll(itemSlot, hintButton);

            // calculates spot in grid with 3 columns
            menuGrid.add(slot, i % 3, i / 3);

            i++;
        }
    }

    /**
     * Updates the inventory and crafting menus with the current menu data.
     */
    public void updateMenus() {
        // clears old menu items
        craftingGridPane.getChildren().clear();
        inventoryGridPane.getChildren().clear();

        // loads new menu items
        updateMenu(menuData.craftingData(), craftingGridPane);
        updateMenu(menuData.inventoryData(), inventoryGridPane);

        LOGGER.finer("Updated menus with new data.");
    }

    /**
     * Sets the menu data for the inventory and crafting menus.
     *
     * @param menuData The MenuData object containing inventory and crafting data.
     */
    public void setMenuData(MenuData menuData) {
        this.menuData = menuData;
    }
}