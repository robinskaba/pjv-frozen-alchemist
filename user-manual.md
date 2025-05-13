## Introduction

### Story

You are playing as an alchemist who finds himself stranded in a frozen cave. Your goal is to escape, but as you may imagine, it’s not going to be easy. As you begin to make sense of your surroundings, you notice a peculiar piece of land that looks different from everything else. What if you tried to reach it? Could it lead to freedom, or would it take you deeper into the cave? One has to wonder — but is there really any other choice than to risk it?

### About

_Frozen Alchemist_ is a single-player game. As you may have gathered from the story, your task is to escape the cave. However, the cave is complex and consists of multiple levels that you must overcome. Your goal is to reach the green tile — that marks the exit. The path won’t be easy. You’ll face various obstacles that must be dealt with. What are these obstacles, and how do you overcome them?

## Menu Screen

The menu screen is what you see after launching the game. You are presented with two choices: _Play Game_ or _Reset Progress_. If this is your first time playing, the latter won’t concern you. Click _Play Game_ to begin your journey.

The _Reset Progress_ option is useful if you've finished the game and want to start over, if you made a mistake (like crafting the wrong potion), or if any unexpected issues occurred. The game automatically saves your progress when you quit — more on that in the _Progress Saving_ section.

## Game

### Inventory and Crafting

You’ve probably noticed the grid-like button in the bottom right corner of the screen. Clicking it with your mouse opens two menus. At first, they’re likely empty. Start picking up ingredients, and your inventory will begin to fill.

#### Crafting

When you collect the right combination of ingredients, a craftable item will appear in the crafting menu. Click on it to craft the item. The required ingredients will be removed from your inventory, and in return, you’ll receive the crafted item — usually a potion.

#### Inventory

Once you’ve crafted a potion, you can equip it by clicking it in the inventory. The menus will close, and the grid button will now show the item you have equipped. To change your equipped item, open the inventory again and select a different one. You can also equip ingredients, but they won’t be of any use.

#### Hints

If you prefer to figure out crafting recipes yourself, the hint system will help. Every item in both menus has a question mark icon in its top-right corner. Clicking it opens a description panel on the left side of the screen. It provides information about the item, which should guide you in the right direction.

### Items

#### Ingredients

Before you can overcome obstacles, you’ll need to understand the objects scattered around the cave. These are ingredients. Step on one to automatically collect it. Ingredients are essential for crafting potions.

##### Examples of Ingredients

- Empty Glass Flask
- Rabbit’s Foot
- Gunpowder
- Ember Flower
- Glacial Shard
- Mystic Vine
- Sulfur Crystal
- Volatile Salt
- Feather

#### Potions

You’re an alchemist — or at least you are in the game. Alchemists make potions. Use the ingredients you collect to brew potions that help you overcome obstacles.

##### Recipes

- **Potion of Frost**: 1 empty glass flask, 1 mystic vine, 2 glacial shards
- **Potion of Levitation**: 1 empty glass flask, 1 rabbit’s foot, 1 feather
- **Potion of Pulverization**: 1 empty glass flask, 2 gunpowder, 1 volatile salt
- **Potion of Melting**: 1 empty glass flask, 1 mystic vine, 1 ember flower, 1 sulfur crystal

### Obstacles and How to Overcome Them

Each obstacle can be overcome with the right potion. Face the obstacle and use the corresponding potion.

#### Rubble

Gray tiles made of tightly stacked rocks. There’s no way to get through — unless you use the **Potion of Pulverization**. It destroys the rubble and clears the way.

#### Chasm

An endless void that can’t be crossed on foot. With the **Potion of Levitation**, however, you’ll float over it. Drink the potion, and your next two steps will defy gravity.

#### Meltable Ice

These blocks of ice look weak and cracked. Help them melt with the **Potion of Melting**, and the path will be clear.

#### Water

Water may be precious, but not when it’s blocking your way. While you can’t drain an entire river, you can freeze a small section to walk across it using the **Potion of Frost**.

### Controls

Now that you know what to do, here’s how to do it.

#### Controlling the Player

Use the **WASD** keys to move. Your character will rotate in the direction you press — this matters, because items are used on the tile you're facing.

#### Equipping an Item

Click the grid-like button in the bottom right corner to open the inventory. Then click the item you wish to equip.

#### Using a Potion

If you have a potion equipped, press the **E** key to use it. If the potion can’t be used on the tile in front of you, it won’t be consumed.

### Progress Saving

When you quit the game, your progress is saved — including your inventory, current level, player position, and any modified blocks (melted, frozen, etc.). The next time you press _Play Game_, you’ll resume exactly where you left off.

This can cause problems if, for example, you crafted the wrong potion and are now stuck. In such cases, restart the game and choose _Reset Progress_. This will restore the game to its original state as if you’ve never played it before.

## Level creation

Level designers can create their own levels by adding new .txt files named levelX.txt to resources folder "levels", where X is the number of the level. The level consists of the following sections separated by '%' character.

### Level format

#### Blocks

The first section (before the '%' sign) defines the structure for the map blocks. Each character represents a block in the game. Recommended dimensions (used in the source code) are 16x9 (16 rows and 9 columns). Each block character is separated by one space.

| block        | code |
| ------------ | ---- |
| Floor        | X    |
| Regular Wall | I    |
| Exit         | E    |
| Meltable Ice | M    |
| Chasm        | C    |
| Rubble       | R    |
| Water        | W    |

### Items

The second section (after the first '%' sign) is a list of items and where they appear in the level. The format is ITEM_CODE(xPosition,yPosition).

| item                    | code |
| ----------------------- | ---- |
| Ember Flower            | EF   |
| Empty Glass Flask       | EGF  |
| Feather                 | F    |
| Glacial Shard           | GS   |
| Gunpowder               | G    |
| Mystic Vine             | M    |
| Rabbit Foot             | RF   |
| Sulfur Crystal          | SC   |
| Volatile Salt           | VS   |
| Potion Of Melting       | POM  |
| Potion Of Pulverization | POP  |
| Potion Of Levitation    | POL  |
| Potion Of Frost         | POF  |

### Initial player position

In the last section, there are only two numbers separated by a comma. Changing these numbers change the initial position where the player appears when he enters the level for the first time. No section separating sign follows after this last section.