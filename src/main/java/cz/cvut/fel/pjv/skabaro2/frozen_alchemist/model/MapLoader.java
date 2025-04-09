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

// creates entities based of txt map file
public class MapLoader {
    private static final String LEVELS = "/levels";

    // Lists total amount of "files" == levels in the levels directory
    public static int getAmountOfLevels() {
        Path levelsPath;

        // get path to levels directory in resources
        try {
            URL levelsUrl = MapLoader.class.getResource(LEVELS);
            if (levelsUrl == null) return 0; // avoid NullPointerException
            levelsPath = Paths.get(levelsUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // count levels
        try (Stream<Path> files = Files.list(levelsPath)) {
            return (int) files.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static LevelData getLevelData(String textRepresentation) {
        return buildLevelData(textRepresentation);
    }

    public static LevelData getLevelData(int level) {
        String textRepresentation = readLevelAsString(level);
        return buildLevelData(textRepresentation);
    }

    private static LevelData buildLevelData(String textRepresentation) {
        System.out.println("x");
        Block[] blocks = getBlocks(textRepresentation);
        System.out.println("y");
        Item[] items = getItems(textRepresentation);
        System.out.println("z");
        Position initial = getInitialPlayerPosition(textRepresentation);
        System.out.println("a");
        return new LevelData(blocks, items, initial);
    }

    private static String readLevelAsString(int level) {
        try {
            return Files.readString(Path.of("src/main/resources/levels/level" + level + ".txt")); // todo lepsi path
        } catch (IOException e) {
            // Handle or rethrow the exception depending on your use-case.
            e.printStackTrace();
            throw new RuntimeException("Failed to read file for level " + level, e);
        }
    }

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

    private static Item[] getItems(String textRepresentation) {
        ArrayList<Item> items = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        String name = null;
        int x = -1;
        int y = -1;

        int i = textRepresentation.indexOf("%") + 2;
        while (true) {
            char readChar = textRepresentation.charAt(i);
            System.out.println("reading item: " + readChar);
            switch (readChar) {
                case '%': return items.toArray(new Item[0]); // end of block declaration
                case '\n': {
                    // finished reading whole item -> create new item
                    if (name == null || x == -1 || y == -1 ) {
                        throw new RuntimeException("Error reading name/x/y of entity.");
                    }

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
                default: buffer.append(readChar);
            }

            i++;
        }
    }
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

                    if (x == -1 || y == -1 ) {
                        throw new RuntimeException("Error reading of player initial position declaration.");
                    }

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
