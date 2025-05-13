# Frozen Alchemist

CHOSEN TOPIC - **GAME ENGINE**

## Managerial summary

The game follows an **alchemist stranded in a frozen cave**, in search of an escape while using his knowledge of potion-making to overcome various obstacles. The core gameplay revolves around **exploring levels, collecting ingredients, crafting potions, and using them to solve puzzles**. Players must navigate frozen caves, overcome blocked paths, deep chasms, and other environmental hazards by using the right potions to progress. The game is made up of numerous levels that can be added and edited.

The project’s primary goal is to develop a **flexible 2D game engine** capable of loading levels from external files and supporting interactive object manipulation. The game includes a **dynamic inventory and crafting system** where players can mix ingredients to craft potions that interact with obstacles (e.g., melting ice with a fire potion, removing rubble with a pulverizing potion). The graphical style is **simple pixel art**.

## Game functionality

### Map structure

The map (general - of all levels) is made up of tiles (squares). A tile can, for example, be a general ice block (a wall that cannot be passed through) or any of the obstacles mentioned. If there is no block or obstacle specified for the tile, it is treated as a piece of floor where an item can be placed. All levels must fit into a set size. The player has a top-down view of the whole map.

### Game progression and goal

The alchemist (the player's character) spawns in the first level, which is specified in the level's configuration. The player's goal is to reach the **Exit tile** and advance to the next level. The player wins when he completes all the levels (the number of levels depends on how many are created).

In each individual level, the player looks for ingredients for potions that will help him overcome obstacles and reach the exit. Obstacles, potions, and ingredients are listed further below.

**The game will not feature a level selector**, because the player keeps items from level to level (as described in Player \> Inventory), which would allow him to return to an older level to farm ingredients instead of advancing. Therefore, on the game home screen there will only be a **Play Game** button and a **Reset Progress** button. The **Play Game** button will resume the player in the latest level he played, with his position and items loaded from a save file, while **Reset Progress** will remove this save file so that the next time the player presses the **Play Game** button, it will be as if he were playing for the first time.

### Player

#### Controls

The player's movement is controlled by the WASD keys. If his equipped item is usable, he can activate it by pressing the E key. Ingredients are automatically picked up when the player walks onto the tile on which they lie.

#### Inventory & Crafting

Inventory and crafting menus will open at the same time next to each other using the same button.

##### Inventory

The player has an inventory. It is a menu listing all the items (potions and ingredients) that the player has. Items can be equipped by clicking on them in the inventory. Only one item can be equipped at a time. A small **?** icon will appear in the corner of each item listed in the inventory, and clicking it will display the item's description.

It is important to note that the inventory is saved, meaning it is not unique to a single level. Level designers are advised to keep this in mind when designing their levels. The player receives starting items (assumed to be some glass flasks and some of the ingredients required for the first level), and his items are saved when he leaves the game and loads it again.

##### Crafting

It's menu lists items that can be crafted. Clicking a craftable item in the crafting menu will craft the item if the player has the required items.

### Obstacles

The game will feature a variety of obstacles that add challenge to the levels.

- **Light Ice Block**
  - The Light Ice Block differs from the usual ice blocks that make up the base map boundaries and walls in that it can be melted. The player needs to use a _Potion of Melting_ in order to melt this block and pass.
- **River**
  - Rivers are tiles that cannot be walked over unless they are frozen solid. To freeze a river tile, the player needs to use a _Potion of Frost_.
- **Rubble**
  - Rubble is another form of barrier that stops the player from passing through. Rubble can be removed by using a _Potion of Pulverization_.
- **Chasm**
  - A Chasm is a hole in the ground that the player cannot pass through. However, if he uses a _Potion of Levitation_, he will gain the ability to levitate over any chasms for the next 3 steps he takes.

### Ingredients

Ingredients are basic items that cannot be used directly. Their only purpose is to serve as components in a potion's crafting recipe. A potion recipe can require more than one piece of a single ingredient, a combination of ingredients, or both.

Ingredients available (may change a bit):

- **Empty Glass Flask** – Required for all potions
- **Rabbit’s Foot** – Essential for the _Potion of Levitation_
- **Gunpowder** – Essential for the _Potion of Pulverization_
- **Ember Flower** – A rare plant that emits heat, required for the _Potion of Melting_
- **Glacial Shard** – A frozen crystal needed to craft the _Potion of Frost_
- **Mystic Vine** – A magical plant that enhances stability, required for the _Potion of Levitation_
- **Sulfur Crystal** – Enhances combustion, great for both _Potion of Pulverization_ and _Potion of Melting_
- **Volatile Salt** – Boosts reaction speed for _Potion of Pulverization_ and _Potion of Melting_
- **Feather** – Makes a player lighter, essential for the _Potion of Levitation_