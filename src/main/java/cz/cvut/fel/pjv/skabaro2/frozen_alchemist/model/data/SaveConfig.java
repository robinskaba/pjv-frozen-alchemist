package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data;

public class SaveConfig {
    private final String code;
    private final String relativePath;

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
