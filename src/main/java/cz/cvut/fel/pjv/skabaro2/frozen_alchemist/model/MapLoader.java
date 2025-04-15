package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.LevelData;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Item;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.ItemType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Class for loading game levels from text files.
 * Responsible for creating entities (blocks, items, etc.) based on the level's text representation.
 */
public class MapLoader {
    private static final String LEVELS = "/levels";

    /**
     * Counts the total number of levels available in the levels directory.
     *
     * @return The number of level files in the levels directory.
     */
    public static int getAmountOfLevels() {
        Path levelsPath;

        try {
            // get the path to the levels directory in resources.
            URL levelsUrl = MapLoader.class.getResource(LEVELS);
            if (levelsUrl == null) return 0; // avoid NullPointerException if the directory is not found
            levelsPath = Paths.get(levelsUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // count the number of files in the levels directory
        try (Stream<Path> files = Files.list(levelsPath)) {
            return (int) files.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the level data based on its text representation.
     *
     * @param textRepresentation The text representation of the level.
     * @return A LevelData object containing the blocks, items, and initial player position.
     */
    public static LevelData getLevelData(String textRepresentation) {
        return buildLevelData(textRepresentation);
    }

    /**
     * Retrieves the level data for a specific level number.
     *
     * @param level The level number to load.
     * @return A LevelData object containing the blocks, items, and initial player position.
     */
    public static LevelData getLevelData(int level) {
        String textRepresentation = readLevelAsString(level);
        return buildLevelData(textRepresentation);
    }

    /**
     * Builds the LevelData object from the text representation of the level.
     *
     * @param textRepresentation The text representation of the level.
     * @return A LevelData object containing the blocks, items, and initial player position.
     */
    private static LevelData buildLevelData(String textRepresentation) {
        // replace all line endings with '\n' for consistency across CRLF and LF
        textRepresentation = textRepresentation.replace("\r\n", "\n");

        Block[] blocks = getBlocks(textRepresentation);
        Item[] items = getItems(textRepresentation);
        Position initial = getInitialPlayerPosition(textRepresentation);
        return new LevelData(blocks, items, initial);
    }

    /**
     * Reads the text representation of a level from a file.
     *
     * @param level The level number to read.
     * @return The text representation of the level.
     */
    private static String readLevelAsString(int level) {
        try {
            URL resource = MapLoader.class.getResource("/levels/level" + level + ".txt");
            if (resource == null) throw new RuntimeException("Level file not found: /levels/level" + level + ".txt");
            return Files.readString(Path.of(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            // TODO Logging
            throw new RuntimeException("Failed to read file for level " + level, e);
        }
    }

    /**
     * Parses the blocks from the text representation of the level.
     *
     * @param textRepresentation The text representation of the level.
     * @return An array of Block objects.
     */
    private static Block[] getBlocks(String textRepresentation) {
        ArrayList<Block> blocks = new ArrayList<>();
        int i = 0;
        int x = 0, y = 0; // for specifying block's position

        while (true) {
            char code = textRepresentation.charAt(i);

            switch (code) {
                case '%': return blocks.toArray(new Block[0]); // end of block declaration
                case '\n': {
                    y++;
                    x = 0;
                    break;
                }
                case ' ': break;
                default: {
                    Position position = new Position(x, y);
                    BlockType blockType = BlockType.fromSaveCode(String.valueOf(code));
                    Block block = new Block(blockType, position);
                    blocks.add(block);
                    x++;
                }
            }

            i++;
        }
    }

    /**
     * Parses the items from the text representation of the level.
     *
     * @param textRepresentation The text representation of the level.
     * @return An array of Item objects.
     */
    private static Item[] getItems(String textRepresentation) {
        ArrayList<Item> items = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        String name = null;
        int x = -1;
        int y = -1;

        int i = textRepresentation.indexOf("%") + 2;
        while (true) {
            char readChar = textRepresentation.charAt(i);

            switch (readChar) {
                case '%': return items.toArray(new Item[0]); // end of item declaration
                case '\n': {
                    // finished reading an item, create a new item
                    if (name == null || x == -1 || y == -1) throw new RuntimeException("Error reading name/x/y of entity.");

                    ItemType itemType = ItemType.fromSaveCode(name);
                    Position position = new Position(x, y);
                    Item newItem = new Item(itemType, position);
                    items.add(newItem);
                    break;
                }
                case '(': {
                    name = buffer.toString();
                    buffer.delete(0, buffer.length());
                    break;
                }
                case ',': {
                    x = Integer.parseInt(buffer.toString());
                    buffer.delete(0, buffer.length());
                    break;
                }
                case ')': {
                    y = Integer.parseInt(buffer.toString());
                    buffer.delete(0, buffer.length());
                    break;
                }
                default: {
                    buffer.append(readChar);
                }
            }

            i++;
        }
    }

    /**
     * Retrieves the initial player position from the text representation of the level.
     *
     * @param textRepresentation The text representation of the level.
     * @return The initial Position of the player.
     */
    private static Position getInitialPlayerPosition(String textRepresentation) {
        StringBuilder buffer = new StringBuilder();
        int x = -1;
        int y = -1;

        int i = textRepresentation.lastIndexOf("%") + 2;
        while (true) {
            char readChar = textRepresentation.charAt(i);

            switch (readChar) {
                case '\n': {
                    y = Integer.parseInt(buffer.toString());
                    buffer.delete(0, buffer.length());

                    if (x == -1 || y == -1) throw new RuntimeException("Error reading player initial position declaration.");

                    return new Position(x, y);
                }
                case ',': {
                    x = Integer.parseInt(buffer.toString());
                    buffer.delete(0, buffer.length());
                    break;
                }
                default: buffer.append(readChar);
            }

            i++;
        }
    }
}