package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.data;

public class MenuData {
    private final MenuItem[] inventoryData;
    private final MenuItem[] craftingData;

    public MenuData(MenuItem[] inventoryData, MenuItem[] craftingData) {
        this.inventoryData = inventoryData;
        this.craftingData = craftingData;
    }

    public MenuItem[] getInventoryData() {
        return inventoryData;
    }

    public MenuItem[] getCraftingData() {
        return craftingData;
    }
}

