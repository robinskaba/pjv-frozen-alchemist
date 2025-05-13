# Technical Documentation

## Controller

Game is launched via the Frozen Alchemist file which contains regular JavaFX setup and passes it's stage to a new Executor object.

Executor is the core of the controller, it controls switching scenes, initiating the game, binding methods between view and model and other important tasks. It prompts another part of the Controller, Texture Manager, to load textures to Map objects for every enum type of entity available. It does so using the relative path of the texture specified in the enums that implement Savable interface.

## Model

### Entities

The engine features 3 types of entities that can appear on the screen (in the map). Blocks, items and the player. For all three exist separate EnumTypes (BlockType, ItemType, Direction) that differentiate between the types of the entities. These enums serve for giving objects of an Entity general properties such as their save code (used in loading/saving map/inventory), the path to their texture, in the case of items their name and description and etc. Because these properties do not differ from entity to entity, they share the type as an attribute.

The main class is Entity, which does not have any instances itself. From it inherits Block, Item and Player. Base entity class features attributes such as Position and EntityType. Individual classes then have their individual subtype (enum). There is obviously only one variant of Player, but for differentiating textures when he faces in different directions his subtype is the direction he is facing.

### Game Map

It is of a set width and height in the term of entities (currently set to 16x9). Game map then features a list of entities on the map. It does not care whether something is a Block or an Item, it sole purpose is to store the information that the map contains these entities. It does however contain getters for individual types of entities on a position, which are methods used in other classes. It also features a getter for getting all the entities (used for rendering).

### Map Loader

Map is loaded from a string (default level from text file, map in progress from ProgressSave). Firstly, it loads the blocks of the map. They are supposed to be 9 rows and 16 columns of characters separated by spaces. Each individual character represents the code for the block (saveCode from SaveConfig property in enum). After the reader reaches % sign, it switches to reading items. After the second %, the coordinates read are the initial position of player in the level. It uses the class LevelData to pass the read level data to the Game, where the GameMap is updated based on it.

### Progess saving

Progress saving is handled by ProgressFileManager that can load, save, reset the progress in the game. It saves the inventory of the player (encoded using item's saveCode), but the map as well, because changes can occur (picked up items, changed blocks) and the current player position as well. The progress is loaded on the game initialization and passes via the ProgressSave class. If any save exist, the Game will load it and instead of loading the level from the text file, it loads it from the save.

### Player

Player has Inventory, which contains methods for adding and removing items. The player is controlled from outside it's class by Player Controller. The Player Controller has references to the current map, the Controls class (which has functions to handle events to singular key presses). It binds the functions for movement and using items. When "E" is pressed, it calls an item use function based on the equipped item. If no item or an item that has no use (ingredient) is equipped, nothing happens. There are also Runnables passed to the controller to be executed when an item is used (passed from all the way from the Executor to update the View when such event occurs).

### Game

Game class wraps all the objects together. It initializes the GameMap, the Player and his controller, it loads the ProgressSave and GameMap. It handles switching levels by passing a Runnable to Player Controller when the Player enters a new block.

## View

### Screen

Screen setups the initial properties of Stage like title, icon, dimensions etc.

### View

View setups general css file used and canvas used by the view. It inherits from JavaFX Pane, so it can be passed to Scene constructor to easily create a scene based on the view.

### MenuView

MenuView inherits from View. It loads a background image and has functions for adding menu buttons to which can Runnables be passed to be executed on button click (used by Executor.)

### GameView

GameView is the main view, because it handles rendering based on an array of RenderedTexture, which is a class used to easily transfer rendered data between controller and the view. It also has many methods for creating the game UI like action button (inventory / crafting button) and setting up the inventory, crafting menus. It has functions for controlling which texture is displayed over the action button (for displaying equipped item).

For loading which data are displayed in the menus, it uses a public function setMenuData, which gets called by the controller and sets the menu data. When updateMenus() is called it then fills the GridPanes in the menus with the appropriate data. It also assigns passed Runnables (using MenuItem class) to set events on ItemClicked or it's HintIconClicked.

### GameAlert

A small class to serve as a customized alert. Currently used to notify player of progress reset.

## Used libraries

Only GSon library is used for saving the progress file in a json format.

## Game states

There are only three states. Game not initiated, in game and game over (the 3 scenes used).