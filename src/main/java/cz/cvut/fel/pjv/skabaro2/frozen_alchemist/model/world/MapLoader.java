package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Item;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.ItemType;
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
    private final String LEVELS = "/levels";

    private final Map<Character, BlockType> blockSigns = new HashMap<>();
    private final Map<String, ItemType> itemSigns = new HashMap<>();

    public MapLoader() {
        setupSigns();
    }

    // sets what "characters" represent what block / item
    private void setupSigns() {
        EnumSet<BlockType> blockTypes = EnumSet.allOf(BlockType.class);
        for (BlockType blockType : blockTypes) {
            blockSigns.put(blockType.getSaveSign(), blockType);
        }

        EnumSet<ItemType> itemTypes = EnumSet.allOf(ItemType.class);
        for (ItemType itemType : itemTypes) {
            itemSigns.put(itemType.getSaveShortcut(), itemType);
        }
    }

    // Lists total amount of "files" == levels in the levels directory
    public int getAmountOfLevels() {
        Path levelsPath;

        // get path to levels directory in resources
        try {
            URL levelsUrl = getClass().getResource(LEVELS);
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

    private InputStreamReader getLevelReader(int level) throws IOException {
        InputStream levelStream = Config.class.getResourceAsStream(String.format("%s/level%d.txt", LEVELS, level));
        if (levelStream == null) throw new IOException("Could not find level " + level);
        return new InputStreamReader(levelStream);
    }

    private Block[] getBlocks(InputStreamReader reader) throws IOException {
        ArrayList<Block> blocks = new ArrayList<>();

        int x = 0, y = 0; // for specifying block's position
        while (true) {
            int sign = reader.read();
            // todo better exceptions -> MapLoadingException class
            if (sign == -1) throw new RuntimeException("Map file should not end before '%' sign is present"); // EOF

            Character shortcut = (char) sign;

            switch (shortcut) {
                case '%': return blocks.toArray(new Block[blocks.size()]); // end of block declaration
                case '\n': {
                    y++;
                    x = 0;
                    break;
                }
                case ' ': break;
                default: {
                    // Add block based on shortcut read
                    Position position = new Position(x, y);
                    BlockType blockType = blockSigns.getOrDefault(shortcut, BlockType.MissingBlock);

                    blocks.add(new Block(blockType, position));

                    x++;
                }
            }
        }
    }

    private Item[] getItems(InputStreamReader reader) throws IOException {
        // skip over '\n' after separation char '%'
        reader.skip(1);

        ArrayList<Item> items = new ArrayList<>();

        StringBuilder buffer = new StringBuilder();
        String name = null;
        int x = -1;
        int y = -1;

        while (true) {
            int sign = reader.read();
            if (sign == -1) return items.toArray(new Item[items.size()]); // legit EOF
            Character readChar = (char) sign;

            switch (readChar) {
                case '\n': {
                    // finished reading whole item -> create new item
                    if (name == null || x == -1 || y == -1 ) {
                        throw new RuntimeException("Error reading name/x/y of item.");
                    }
                    ItemType itemType = itemSigns.get(name);
                    if (itemType == null) throw new RuntimeException("Read non-existent ItemType: " + name);

                    Item newItem = new Item(itemType, new Position(x, y));
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

            System.out.println("appending: <" + readChar + ">");
            buffer.append(readChar);
            System.out.println("current buffer: <" + buffer.toString() + ">");
        }
    }

    // void -> GameMap
    public GameMap buildMap(int level) {
        try {
            InputStreamReader reader = getLevelReader(level);
            Block[] blocks = getBlocks(reader);
            Item[] items = getItems(reader);
            return new GameMap(blocks, items);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
