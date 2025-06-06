package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.ProgressSave;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Manages saving and loading game progress to and from a JSON file.
 * Handles serialization and deserialization of game state, including level, inventory, and map data.
 */
public class ProgressFileManager {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final static String PROGRESS_FILE_URL = "progress.json"; // changes, so outside resources!!

    private final static String LEVEL_KEY = "level";
    private final static String INVENTORY_KEY = "inventory";
    private final static String MAP_KEY = "map";

    private final static String ITEM_OBJ_CODE_KEY = "itemCode";
    private final static String ITEM_OBJ_AMOUNT_KEY = "amount";

    private final static String MAP_CONTENT_KEY = "text";
    private final static String LEVEL_VALUE_KEY = "value";

    /**
     * Retrieves the path to the progress file.
     *
     * @return The path to the progress file.
     */
    private static Path getProgressFileUrl() {
        return Paths.get(PROGRESS_FILE_URL);
    }

    /**
     * Saves the current game progress to a JSON file.
     *
     * @param level    The current level of the game.
     * @param player   The player entity, including inventory data.
     * @param gameMap  The current game map.
     */
    public static void save(int level, Player player, GameMap gameMap) {
        LOGGER.info("Saving progress...");

        // building base json components
        JsonObject levelObj = buildLevelSave(level);
        JsonObject inventoryObj = buildInventorySave(player.getInventory());
        JsonObject mapObj = buildMapSave(gameMap, player);
        JsonObject saveObj = new JsonObject();
        saveObj.add(LEVEL_KEY, levelObj);
        saveObj.add(INVENTORY_KEY, inventoryObj);
        saveObj.add(MAP_KEY, mapObj);

        // saving using gson
        Gson gson = new Gson();
        Path path = Paths.get(PROGRESS_FILE_URL);

        try (Writer writer = Files.newBufferedWriter(path)) {
            gson.toJson(saveObj, writer);
        } catch (Exception e) {
            LOGGER.warning("Failed to save progress to a file.");
            throw new RuntimeException("Failed to save progress file at " + PROGRESS_FILE_URL, e);
        } finally {
            LOGGER.info("Progress saved successfully.");
        }
    }

    /**
     * Builds a JSON object representing the level data.
     *
     * @param level The current level of the game.
     * @return A JSON object containing the level data.
     */
    private static JsonObject buildLevelSave(int level) {
        JsonObject levelObj = new JsonObject();
        levelObj.addProperty(LEVEL_VALUE_KEY, level);
        return levelObj;
    }

    /**
     * Builds a JSON object representing the player's inventory.
     *
     * @param inventory The player's inventory.
     * @return A JSON object containing the inventory data.
     */
    private static JsonObject buildInventorySave(Inventory inventory) {
        JsonObject inventoryObj = new JsonObject();
        int i = 0; // need unique name for item record
        for (Map.Entry<ItemType, Integer> item : inventory.getContent().entrySet()) {
            ItemType itemType = item.getKey();
            Integer amount = item.getValue();

            // building json object for an item in the inventory
            JsonObject itemObj = new JsonObject();
            itemObj.addProperty(ITEM_OBJ_CODE_KEY, itemType.getSaveConfig().code());
            itemObj.addProperty(ITEM_OBJ_AMOUNT_KEY, amount);

            inventoryObj.add(String.valueOf(i), itemObj);
            i++;
        }

        LOGGER.finest("Inventory save built.");
        return inventoryObj;
    }

    /**
     * Builds a JSON object representing the game map and player position.
     *
     * @param gameMap The current game map.
     * @param player  The player entity, including position data.
     * @return A JSON object containing the map data.
     */
    private static JsonObject buildMapSave(GameMap gameMap, Player player) {
        StringBuilder textMapBuilder = new StringBuilder();

        // build blocks: format CODE + SPACE + CODE ... \n
        // iterating over game map positions, so it's easy to format it in txt format
        for (int y = 0; y < gameMap.getHeight(); y++) {
            for (int x = 0; x < gameMap.getWidth(); x++) {
                // fetching save code based on the block
                Block block = gameMap.getBlockOnPosition(new Position(x, y));
                BlockType blockType = (BlockType) block.getSubType();
                String code = blockType.getSaveConfig().code();

                textMapBuilder.append(String.format("%s ", code));
            }
            textMapBuilder.deleteCharAt(textMapBuilder.length() - 1); // remove last space
            textMapBuilder.append("\n"); // new row
        }
        textMapBuilder.append("%\n"); // end of blocks

        // build items: format CODE + SPACE + CODE ... \n
        for (Entity entity : gameMap.getEntities()) {
            if (entity.getEntityType() == EntityType.ITEM) {
                // fetching item save code
                Item item = (Item) entity;
                ItemType itemType = (ItemType) item.getSubType();
                String code = itemType.getSaveConfig().code();

                // fetching position
                Position position = item.getPosition();

                // saving in the used item save format
                String saveFormat = String.format("%s(%d,%d)\n", code, position.getX(), position.getY());
                textMapBuilder.append(saveFormat);
            }
        }
        textMapBuilder.append("%\n"); // end of items

        // set starting position as the last player position
        String positionFormat = String.format("%d,%d", player.getPosition().getX(), player.getPosition().getY());
        textMapBuilder.append(positionFormat);
        textMapBuilder.append("\n"); // end of save file

        // build map JSON
        JsonObject mapObj = new JsonObject();
        mapObj.addProperty(MAP_CONTENT_KEY, textMapBuilder.toString());

        LOGGER.finest("Map save built.");
        return mapObj;
    }

    /**
     * Loads the game progress from the JSON file.
     *
     * @return A ProgressSave object containing the loaded game state, or null if the file does not exist.
     */
    public static ProgressSave load() {
        Path filePath = getProgressFileUrl();
        Gson gson = new Gson();

        // no file exists, return null
        if (!Files.exists(filePath)) return null;

        int level;
        Map<ItemType, Integer> inventoryContent = new HashMap<>();
        String mapString;

        // reads json objects from the file and builds a ProgressSave object
        try (Reader reader = Files.newBufferedReader(filePath)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            // current level
            level = jsonObject.get(LEVEL_KEY).getAsJsonObject().get(LEVEL_VALUE_KEY).getAsInt();

            // inventory
            JsonObject inventoryObj = jsonObject.get(INVENTORY_KEY).getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : inventoryObj.entrySet()) {
                JsonObject itemObj = entry.getValue().getAsJsonObject();
                ItemType itemType = ItemType.fromSaveCode(itemObj.get(ITEM_OBJ_CODE_KEY).getAsString());
                int amount = itemObj.get(ITEM_OBJ_AMOUNT_KEY).getAsInt();
                inventoryContent.put(itemType, amount);
            }

            // map (includes items and initial player position)
            JsonObject mapObj = jsonObject.get(MAP_KEY).getAsJsonObject();
            mapString = mapObj.get(MAP_CONTENT_KEY).getAsString();

        } catch (IOException e) {
            LOGGER.warning("Failed to load progress from a file.");
            throw new RuntimeException("Failed to load progress file at " + PROGRESS_FILE_URL, e);
        } finally {
            LOGGER.info("Progress loaded successfully.");
        }

        return new ProgressSave(level, inventoryContent, mapString);
    }

    /**
     * Resets the game progress by deleting the progress file.
     */
    public static void resetProgress() {
        Path filePath = getProgressFileUrl();

        try {
            // removes file if exists (= reset)
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            LOGGER.warning("Failed to delete progress file.");
            throw new RuntimeException("Failed to delete progress file at " + PROGRESS_FILE_URL, e);
        } finally {
            LOGGER.info("Progress reset successfully.");
        }
    }
}