package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data;

/**
 * Represents the configuration for saving game data.
 * Includes a unique code and a relative file path to the used texture file.
 */
public class SaveConfig {
    private final String code;
    private final String relativePath;

    /**
     * Constructs a new SaveConfig object.
     *
     * @param code A unique code identifying the object.
     * @param relativePath The relative file path where the texture file is located.
     */
    public SaveConfig(String code, String relativePath) {
        this.code = code;
        this.relativePath = relativePath;
    }

    public String getCode() {
        return code;
    }

    public String getRelativePath() {
        return relativePath;
    }
}