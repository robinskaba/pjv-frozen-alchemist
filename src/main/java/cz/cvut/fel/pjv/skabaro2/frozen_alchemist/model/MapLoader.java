package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public static LevelData getLevelData(int level) {
        InputStream stream = Config.class.getResourceAsStream(String.format("%s/level%d.txt", LEVELS, level));
        if (stream == null) throw new IllegalArgumentException("No level file exists for level: " + level);
        InputStreamReader reader = new InputStreamReader(stream);

        try {
            Block[] blocks = getBlocks(reader);
            Item[] items = getItems(reader);
            Position initial = getInitialPlayerPosition(reader);
            return new LevelData(blocks, items, initial);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Block[] getBlocks(InputStreamReader reader) throws IOException {
        ArrayList<Block> blocks = new ArrayList<>();

        int x = 0, y = 0; // for specifying block's position
        while (true) {
            int sign = reader.read();
            if (sign == -1) throw new RuntimeException("Map file should not end before '%' sign is present"); // EOF

            char code = (char) sign;

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
        }
    }

    private static Item[] getItems(InputStreamReader reader) throws IOException {
        // skip over '\n' after separation char '%'
        reader.skip(1);

        ArrayList<Item> items = new ArrayList<>();

        StringBuilder buffer = new StringBuilder();
        String name = null;
        int x = -1;
        int y = -1;

        while (true) {
            int sign = reader.read();
            if (sign == -1) throw new IOException("File can not end before player initial position declaration."); // legit EOF
            Character readChar = (char) sign;

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
                    continue;
                }
                case '(': {
                    name = buffer.toString();
                    buffer.delete(0, buffer.length());
                    continue;
                }
                case ',': {
                    x = Integer.parseInt(buffer.toString());
                    buffer.delete(0, buffer.length());
                    continue;
                }
                case ')': {
                    y = Integer.parseInt(buffer.toString());
                    buffer.delete(0, buffer.length());
                    continue;
                }
            }

            buffer.append(readChar);
        }
    }
    private static Position getInitialPlayerPosition(InputStreamReader reader) throws IOException {
        // skip over '\n' after separation char '%'
        reader.skip(1);

        StringBuilder buffer = new StringBuilder();
        int x = -1;
        int y = -1;

        while (true) {
            int sign = reader.read();
            if (sign == -1) throw new IOException("File reading should not end because of EOF.");
            Character readChar = (char) sign;

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
                    continue;
                }
            }

            buffer.append(readChar);
        }
    }
}
