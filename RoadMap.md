# **League of Warriors To-Do List**

## **Core Classes**
### `Game`
- [ ] Create the `Game` class to serve as the main logic controller.
- [ ] Add attributes:
    - `ArrayList<Account> accounts`
    - `Grid gameGrid`
- [ ] Implement `run()` method:
    - [ ] Parse `accounts.json`.
    - [ ] Authenticate users with email and password.
    - [ ] Allow users to select a character.
    - [ ] Initialize the grid and start the game loop.
- [ ] Add a method to display available options based on the cell type.
- [ ] Handle exceptions:
    - [ ] `InvalidCommandException`
    - [ ] `ImpossibleMoveException`.

---

## **Grid and Cells**
### `Grid`
- [ ] Extend `ArrayList<ArrayList<Cell>>`.
- [ ] Add attributes:
    - `int width, height`
    - `Character playerCharacter`
    - `Cell currentCell`
- [ ] Make the constructor private.
- [ ] Implement `static Grid generateMap(int width, int height)`:
    - [ ] Add at least 2 `SANCTUARY` cells.
    - [ ] Add at least 4 `ENEMY` cells.
    - [ ] Add 1 `PORTAL` cell.
    - [ ] Place the player randomly on an empty cell.
- [ ] Implement movement methods:
    - [ ] `void goNorth()`
    - [ ] `void goSouth()`
    - [ ] `void goWest()`
    - [ ] `void goEast()`
- [ ] Handle exceptions for invalid moves.

### `Cell`
- [x] Add attributes:
    - `int x, y` (coordinates).
    - `CellEntityType type`.
    - `boolean visited`.
- [x] Add methods:
    - [x] Getters and setters for all attributes.
    - [x] `toString()` to display cell details.

### `CellEntityType`
- [x] Create an enumeration for cell types:
    - `PLAYER`
    - `VOID`
    - `ENEMY`
    - `SANCTUARY`
    - `PORTAL`.

---

## **Entities**
### `Entity`
- [ ] Implement `Entity` as an abstract class.
- [ ] Implement the `Battle` interface.
- [ ] Add attributes:
    - `List<Spell> abilities`
    - `int currentHealth, maxHealth`
    - `int currentMana, maxMana`
    - `boolean immuneToFire, immuneToIce, immuneToEarth`.
- [ ] Add methods:
    - [ ] `void regenerateHealth(int value)`
    - [ ] `void regenerateMana(int value)`
    - [ ] `void useAbility(Spell ability, Entity target)`
    - [ ] Abstract methods:
        - `void receiveDamage(int damage)`
        - `int getDamage()`.

### `Character`
- [ ] Extend `Entity`.
- [ ] Add attributes:
    - `String name`
    - `int currentExperience`
    - `int level`
    - `int strength, charisma, dexterity`.
- [ ] Add methods:
    - [ ] Abstract:
        - `void levelUp()`
    - [ ] Concrete:
        - Override `receiveDamage(int damage)`.
        - Override `getDamage()`.

### `Warrior`, `Mage`, `Rogue`
- [ ] Extend `Character` for each type.
- [ ] Add specific attributes:
    - **Warrior**: Immunity: `Fire`, Primary Attribute: `Strength`.
    - **Mage**: Immunity: `Ice`, Primary Attribute: `Charisma`.
    - **Rogue**: Immunity: `Earth`, Primary Attribute: `Dexterity`.
- [ ] Override methods:
    - [ ] `void levelUp()`
    - [ ] `void receiveDamage(int damage)`
    - [ ] `int getDamage()`.

### `Enemy`
- [ ] Extend `Entity`.
- [ ] Add attributes:
    - Randomized `health`, `mana`, `damage`, and immunities.
    - Randomly assign 3-6 abilities from `Spell` subclasses.
- [ ] Implement methods:
    - [ ] Override `void receiveDamage(int damage)`:
        - Add a 50% chance to avoid damage.
    - [ ] Override `int getDamage()`:
        - Add a 50% chance to double damage.

---

## **Spells**
### `Spell`
- [ ] Create `Spell` as an abstract class.
- [ ] Add attributes:
    - `int damage`
    - `int manaCost`.
- [ ] Add methods:
    - [ ] `String toString()`.

### `Ice`, `Fire`, `Earth`
- [ ] Extend `Spell` for each type.
- [ ] Initialize with specific damage and mana costs in constructors.

---

## **Account Management**
### `Account`
- [ ] Add attributes:
    - `Information info`
    - `ArrayList<Character> characters`
    - `int gamesPlayed`.
- [ ] Add methods:
    - [ ] Getters and setters for attributes.

### `Information`
- [ ] Nested class inside `Account`.
- [ ] Add attributes:
    - `Credentials credentials`
    - `TreeSet<String> favoriteGames`
    - `String name, country`.

### `Credentials`
- [ ] Add attributes:
    - `String email`
    - `String password`.
- [ ] Use encapsulation (private fields with getters and setters).

---

## **Exceptions**
### `InvalidCommandException`
- [ ] Create a custom exception for invalid user inputs.
- [ ] Add a message like "Invalid command! Please try again."

### `ImpossibleMoveException`
- [ ] Create a custom exception for invalid moves on the grid.
- [ ] Add a message like "Move not possible! You reached the edge of the map."

---

## **Utilities**
### `JsonParser`
- [ ] Create a class to parse the `accounts.json` file.
- [ ] Use a library like `json-simple` or `Gson`.
- [ ] Return `ArrayList<Account>` with all user data.

### `RandomUtils`
- [ ] Create helper methods for generating random values:
    - Random abilities, immunities, and cell placements.

---

## **Testing**
### `TestGame`
- [ ] Write a test scenario for the entire flow:
    - Load accounts.
    - Create and initialize the grid.
    - Simulate movements and interactions.
    - Check level-ups and damage calculations.

### `TestCharacter`
- [ ] Test level-up and experience gain for all character types.

### `TestGrid`
- [ ] Test grid generation and movements.

### `TestSpells`
- [ ] Test spell damage and mana costs.

### `TestExceptions`
- [ ] Test `InvalidCommandException` and `ImpossibleMoveException`.

