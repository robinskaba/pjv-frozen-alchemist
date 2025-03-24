package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Entity;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

// creates entities based of txt map file
public class MapLoader {
    private final String LEVELS = "/levels/";

    private final Map<Character, BlockType> blockSigns = new HashMap<>();
    private final Map<String, ItemType> itemSigns = new HashMap<>();

    public MapLoader() {
        System.out.println(getAmountOfLevels());
        setupSigns();
    }

    // sets what "characters" represent what block / item
    private void setupSigns() {
        blockSigns.put('X', BlockType.Floor);
        blockSigns.put('W', BlockType.Water);
        blockSigns.put('C', BlockType.Chasm);
        blockSigns.put('R', BlockType.Rubble);
        blockSigns.put('E', BlockType.Exit);
        blockSigns.put('M', BlockType.MeltableIce);
        blockSigns.put('I', BlockType.RegularIce);
    }

    // Lists total amount of "files" == levels in the levels directory
    private int getAmountOfLevels() {
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

    public List<Entity> buildEntities(int level) {
        ArrayList<Entity> entities = new ArrayList<>();

        InputStream input = Config.class.getResourceAsStream(String.format("%slevel%d/map.txt", LEVELS, level));
        if (input == null) return entities;

        try (InputStreamReader reader = new InputStreamReader(input)) {
            int x = 0, y = 0;
            while (true) {
                int sign = reader.read();
                if (sign == -1) return entities;
                Character shortcut = (char) sign;

                if (blockSigns.containsKey(shortcut)) {
                    BlockType blockType = blockSigns.get(shortcut);
                    if (blockType == null) {
                        System.err.println("Unknown block type: <" + shortcut + ">");
                        continue;
                    };

                    Position position = new Position(x, y);
                    entities.add(new Block(blockType, position));

                    x++;
                } else if (shortcut.equals('\n')) {
                    y++;
                    x = 0;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
