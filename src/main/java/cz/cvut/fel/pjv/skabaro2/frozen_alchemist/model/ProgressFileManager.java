package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ProgressFileManager {
    private final static String RELATIVE_PROGRESS_FILE_PATH = "src/main/resources/progress.json";

    private static Path getProgressFilePath() {
        return Paths.get(RELATIVE_PROGRESS_FILE_PATH);
    }

    public static void save(int level, Player player, GameMap gameMap) {
        JsonObject levelObj = buildLevelSave(level);
        JsonObject inventoryObj = buildInventorySave(player.getInventory());
        JsonObject mapObj = buildMapSave(gameMap, player);

        JsonObject saveObj = new JsonObject();
        saveObj.add("level", levelObj);
        saveObj.add("inventory", inventoryObj);
        saveObj.add("map", mapObj);

        Gson gson = new Gson();
        Path path = Paths.get(RELATIVE_PROGRESS_FILE_PATH);

        try (Writer writer = Files.newBufferedWriter(path)) {
            gson.toJson(saveObj, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JsonObject buildLevelSave(int level) {
        JsonObject levelObj = new JsonObject();
        levelObj.addProperty("value", level);
        return levelObj;
    }

    private static JsonObject buildInventorySave(Inventory inventory) {
        JsonObject inventoryObj = new JsonObject();
        int i = 0; // pseudo name for the item record
        for (Map.Entry<ItemType, Integer> item : inventory.getContent().entrySet()) {
            ItemType itemType = item.getKey();
            Integer amount = item.getValue();

            JsonObject itemObj = new JsonObject();
            itemObj.addProperty("itemCode", itemType.getSaveConfig().getCode());
            itemObj.addProperty("amount", amount);

            inventoryObj.add(String.valueOf(i), itemObj);
            i++;
        }
        return inventoryObj;
    }

    private static JsonObject buildMapSave(GameMap gameMap, Player player) {
        StringBuilder textMapBuilder = new StringBuilder();

        // build blocks: format CODE + SPACE + CODE ... \n
        for (int y = 0; y < gameMap.getHeight(); y++) {
            for (int x = 0; x < gameMap.getWidth(); x++) {
                Block block = gameMap.getBlockOnPosition(new Position(x, y));
                BlockType blockType = (BlockType) block.getSubType();
                String code = blockType.getSaveConfig().getCode();
                textMapBuilder.append(String.format("%s ", code));
            }
            textMapBuilder.deleteCharAt(textMapBuilder.length()-1); // remove last space
            textMapBuilder.append("\n");
        }
        textMapBuilder.append("%\n"); // end of blocks

        // build items: format CODE + SPACE + CODE ... \n
        for (Entity entity : gameMap.getEntities()) {
            if (entity.getEntityType() == EntityType.ITEM) {
                Item item = (Item) entity;
                ItemType itemType = (ItemType) item.getSubType();
                String code = itemType.getSaveConfig().getCode();
                Position position = item.getPosition();
                String saveFormat = String.format("%s(%d,%d)\n", code, position.getX(), position.getY());
                textMapBuilder.append(saveFormat);
            }
        }
        textMapBuilder.append("%\n"); // end of items

        // set starting position as last player position
        String positionFormat = String.format("%d,%d", player.getPosition().getX(), player.getPosition().getY());
        textMapBuilder.append(positionFormat);
        textMapBuilder.append("\n"); // end of save file

        // build map json
        JsonObject mapObj = new JsonObject();
        mapObj.addProperty("text", textMapBuilder.toString());

        return mapObj;
    }

    public static ProgressSave load() {
        Path filePath = getProgressFilePath();
        Gson gson = new Gson();

        if (!Files.exists(filePath)) {
            System.out.println("Progress file not found.");
            return null;
        }

        int level;
        Map<ItemType, Integer> inventoryContent = new HashMap<>();
        String mapString;

        try (Reader reader = Files.newBufferedReader(filePath)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            level = jsonObject.get("level").getAsJsonObject().get("value").getAsInt();

            JsonObject inventoryObj = jsonObject.get("inventory").getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : inventoryObj.entrySet()) {
                JsonObject itemObj = entry.getValue().getAsJsonObject();
                ItemType itemType = ItemType.fromSaveCode(itemObj.get("itemCode").getAsString());
                int amount = itemObj.get("amount").getAsInt();
                inventoryContent.put(itemType, amount);
            }

            JsonObject mapObj = jsonObject.get("map").getAsJsonObject();
            mapString = mapObj.get("text").getAsString();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load progress file at " + RELATIVE_PROGRESS_FILE_PATH, e);
        }
        return new ProgressSave(level, inventoryContent, mapString);
    }

    public static void resetProgress() {
        Path filePath = getProgressFilePath();

        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete progress file at " + RELATIVE_PROGRESS_FILE_PATH, e);
        }
    }
}
