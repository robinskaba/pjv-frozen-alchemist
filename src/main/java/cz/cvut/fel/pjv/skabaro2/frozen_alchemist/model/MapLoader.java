package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.LevelData;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Item;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.ItemType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.exceptions.IllegalLevelStructure;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.exceptions.IllegalMapLoaderState;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Utility class for loading and parsing game levels from text files.
 * Handles the creation of game entities (blocks, items, etc.) and ensures
 * the level structure adheres to predefined constraints.
 */
public class MapLoader {
    /**
     * Represents the structure of the map, including its dimensions and blocks.
     */
    static class MapStructure {
        private final int width;
        private final int height;
        private final Block[] blocks;

        private MapStructure(int width, int height, Block[] blocks) {
            this.width = width;
            this.height = height;
            this.blocks = blocks;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final String LEVELS = "/levels";
    private static int allowedMapWidth = -1;
    private static int allowedMapHeight = -1;

    /**
     * Configures the allowed dimensions for all game levels.
     * Levels exceeding these dimensions will be considered invalid.
     *
     * @param width  The maximum allowed width of the map.
     * @param height The maximum allowed height of the map.
     */
    public static void setAllowedMapDimensions(int width, int height) {
        // sets allowed dimensions (all levels must fit these settings)
        allowedMapWidth = width;
        allowedMapHeight = height;

        LOGGER.fine(String.format("Allowed map dimensions set to %dx%d.", width, height));
    }

    /**
     * Determines the total number of level files available in the levels directory.
     *
     * @return The count of level files found in the levels directory.
     */
    public static int getAmountOfLevels() {
        Path levelsPath;

        try {
            // get the path to the levels directory in resources
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
     * Parses the level data from its text representation.
     *
     * @param textRepresentation The textual representation of the level.
     * @return A LevelData object containing the parsed blocks, items, and player position.
     */
    public static LevelData getLevelData(String textRepresentation) {
        return buildLevelData(textRepresentation);
    }

    /**
     * Loads and parses the level data for a specific level number.
     *
     * @param level The level number to load.
     * @return A LevelData object containing the parsed blocks, items, and player position.
     */
    public static LevelData getLevelData(int level) {
        String textRepresentation = readLevelAsString(level);
        return buildLevelData(textRepresentation);
    }

    /**
     * Constructs a LevelData object from the provided text representation of the level.
     *
     * @param textRepresentation The textual representation of the level.
     * @return A LevelData object containing the parsed blocks, items, and player position.
     */
    private static LevelData buildLevelData(String textRepresentation) {
        // checks that allowed dimensions are set
        if (allowedMapWidth == -1 || allowedMapHeight == -1) {
            throw new IllegalMapLoaderState("Map Loader requires allowed map width and height to be set before loading levels.");
        }

        // replace all line endings with '\n' for consistency across CRLF and LF (caused issues)
        textRepresentation = textRepresentation.replace("\r\n", "\n");

        // fetches map structure, items and player's initial position from the text
        MapStructure structure = getStructure(textRepresentation);
        Item[] items = getItems(textRepresentation);
        Position initial = getInitialPlayerPosition(textRepresentation);

        LOGGER.info(String.format(
                "Loaded level with dimensions: %dx%d, initial player position: %s, number of blocks: %d, number of items: %d.",
                structure.width, structure.height, initial, structure.blocks.length, items.length
        ));

        return new LevelData(structure.width, structure.height, structure.blocks, items, initial);
    }

    /**
     * Reads the text representation of a level from a file.
     *
     * @param level The level number to read.
     * @return The textual representation of the level.
     */
    private static String readLevelAsString(int level) {
        LOGGER.finer("Reading level " + level + " as string from file.");

        try {
            // find level in resources
            URL resource = MapLoader.class.getResource("/levels/level" + level + ".txt");
            if (resource == null) throw new RuntimeException("Level file not found: /levels/level" + level + ".txt");

            return Files.readString(Path.of(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to read file for level " + level, e);
        }
    }

    /**
     * Extracts the map structure (blocks and dimensions) from the text representation.
     *
     * @param textRepresentation The textual representation of the level.
     * @return A MapStructure object containing the blocks and map dimensions.
     */
    private static MapStructure getStructure(String textRepresentation) {
        ArrayList<Block> blocks = new ArrayList<>();
        int i = 0; // for counting chars
        int x = 0, y = 0; // for specifying block's position
        int width = -1;

        while (true) {
            char code = textRepresentation.charAt(i);

            switch (code) {
                case '%': {
                    // end of blocks declaration
                    if (width != allowedMapWidth || y != allowedMapHeight) throw new IllegalLevelStructure("Level dimensions do not equal set values.");

                    LOGGER.finest(String.format(
                            "Parsed blocks: %d, width: %d, height: %d.",
                            blocks.size(), width, y
                    ));

                    // returning map structure object
                    return new MapStructure(
                            width,
                            y,
                            blocks.toArray(new Block[0])
                    );
                }
                case '\n': {
                    y++;

                    if (width == -1) width = x;
                    else if (x != width) throw new IllegalLevelStructure("Width of rows of blocks is inconsistent.");

                    x = 0;
                    break;
                }
                case ' ': break;
                default: {
                    // code is valid -> creating block from the code
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
     * Extracts the items from the text representation of the level.
     *
     * @param textRepresentation The textual representation of the level.
     * @return An array of Item objects parsed from the text.
     */
    private static Item[] getItems(String textRepresentation) {
        ArrayList<Item> items = new ArrayList<>();
        StringBuilder buffer = new StringBuilder(); // for reading longer codes
        String name = null;
        int x = -1;
        int y = -1;

        int i = textRepresentation.indexOf("%") + 2;
        while (true) {
            char readChar = textRepresentation.charAt(i);

            switch (readChar) {
                case '%': return items.toArray(new Item[0]); // end of item declaration, returning fetched items
                case '\n': {
                    // finished reading an item, create a new item
                    if (name == null || x == -1 || y == -1) throw new RuntimeException("Error reading name/x/y of entity.");
                    if (x < 0 || x >= allowedMapWidth || y < 0 || y >= allowedMapHeight) throw new IllegalLevelStructure("Items can not be placed outside of the map.");

                    // new line -> fetching new item from the gathered data
                    ItemType itemType = ItemType.fromSaveCode(name);
                    Position position = new Position(x, y);
                    Item newItem = new Item(itemType, position);
                    items.add(newItem);

                    LOGGER.finest(String.format(
                            "Parsed item: %s, position: %s.",
                            itemType.getName(), new Position(x, y)
                    ));

                    break;
                }
                case '(': {
                    // fetching name from the buffer
                    name = buffer.toString();
                    buffer.delete(0, buffer.length());
                    break;
                }
                case ',': {
                    // fetching x position from the buffer
                    x = Integer.parseInt(buffer.toString());
                    buffer.delete(0, buffer.length());
                    break;
                }
                case ')': {
                    // fetching y position from the buffer
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
     * Extracts the initial player position from the text representation of the level.
     *
     * @param textRepresentation The textual representation of the level.
     * @return The initial Position of the player.
     */
    private static Position getInitialPlayerPosition(String textRepresentation) {
        StringBuilder buffer = new StringBuilder();
        int x = -1;
        int y;

        int i = textRepresentation.lastIndexOf("%") + 2;
        while (true) {
            char readChar = textRepresentation.charAt(i);

            switch (readChar) {
                case '\n': {
                    // new line -> end of player's position declaration

                    // getting y position
                    try {
                        y = Integer.parseInt(buffer.toString());
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Error reading player initial y-position declaration.");
                    }
                    buffer.delete(0, buffer.length());

                    // x position should have already been parsed
                    if (x == -1) throw new RuntimeException("Error reading player initial x-position declaration.");
                    if (x < 0 || x >= allowedMapWidth || y < 0 || y >= allowedMapHeight) throw new IllegalLevelStructure("Player can not be positioned outside of the map.");

                    LOGGER.finest(String.format(
                            "Parsed initial player position: %s.",
                            new Position(x, y)
                    ));

                    // returning position
                    return new Position(x, y);
                }
                case ',': {
                    // parsing x-position from the buffer
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