package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;

public final class Config {
    private static final String configFilePath = "/config.json";

    private static final JsonObject data;

    // "constructor" initiated only once automatically when loaded into memory
    static {
        // load content of config.json into a JsonObject
        InputStream input = Config.class.getResourceAsStream(configFilePath);
        InputStreamReader reader = new InputStreamReader(input);
        data = JsonParser.parseReader(reader).getAsJsonObject();
    }

    // getter functions - checks if exists and returns as desired type else default value
    public static String getString(String key) {
        return data.has(key) ? data.get(key).getAsString() : null;
    }

    public static int getInt(String key) {
        return data.has(key) ? data.get(key).getAsInt() : 0;
    }

    public static boolean getBoolean(String key) {
        return data.has(key) && data.get(key).getAsBoolean(); // defaults to false
    }

    public static double getDouble(String key) {
        return data.has(key) ? data.get(key).getAsDouble() : 0.0;
    }
}
