package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Utility class for loading and accessing configuration data from a JSON file.
 * The configuration file is loaded once when the class is initialized and its data is stored in a JsonObject.
 */
public final class Config {
    private static final String configFilePath = "/config.json";
    private static final JsonObject data;

    // static initializer block to load the configuration file into memory.
    static {
        // load the content of the configuration file as a JsonObject.
        InputStream input = Config.class.getResourceAsStream(configFilePath);
        if (input == null) throw new RuntimeException("Configuration file not found: " + configFilePath);
        InputStreamReader reader = new InputStreamReader(input);
        data = JsonParser.parseReader(reader).getAsJsonObject();
    }

    /**
     * Retrieves a string value from the configuration data.
     *
     * @param key The key of the configuration property.
     * @return The string value associated with the key, or null if the key does not exist.
     */
    public static String getString(String key) {
        return data.has(key) ? data.get(key).getAsString() : null;
    }

    /**
     * Retrieves an integer value from the configuration data.
     *
     * @param key The key of the configuration property.
     * @return The integer value associated with the key, or 0 if the key does not exist.
     */
    public static int getInt(String key) {
        return data.has(key) ? data.get(key).getAsInt() : 0;
    }

    /**
     * Retrieves a boolean value from the configuration data.
     *
     * @param key The key of the configuration property.
     * @return The boolean value associated with the key, or false if the key does not exist.
     */
    public static boolean getBoolean(String key) {
        return data.has(key) && data.get(key).getAsBoolean();
    }

    /**
     * Retrieves a double value from the configuration data.
     *
     * @param key The key of the configuration property.
     * @return The double value associated with the key, or 0.0 if the key does not exist.
     */
    public static double getDouble(String key) {
        return data.has(key) ? data.get(key).getAsDouble() : 0.0;
    }
}