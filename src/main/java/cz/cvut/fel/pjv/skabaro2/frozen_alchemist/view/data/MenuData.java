package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.data;

/**
 * A record that holds data for the menu, including inventory and crafting information.
 *
 * @param inventoryData An array of MenuItem objects representing the inventory data.
 * @param craftingData  An array of MenuItem objects representing the crafting data.
 */
public record MenuData(MenuItem[] inventoryData, MenuItem[] craftingData) {}