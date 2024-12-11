import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Grid extends ArrayList<ArrayList<Cell>> {

    Random rand = new Random();
    private Scanner input = new Scanner(System.in);
    private int rows, cols, firstShow = 0;
    private Character player;
    private static Enemy enemy;
    private static Cell currentCell = new Cell(0, 0, CellEntityType.PLAYER);
    private static int playerDmg, enemyDmg;

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";

    private Grid(int rows, int cols, Character player, Enemy enemy) {
        this.rows = rows;
        this.cols = cols;
        this.player = player;
        this.enemy = enemy;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public static Grid gridHard(int rows, int cols, Character player, Enemy enemy) {
        Grid grid = new Grid(rows, cols, player, enemy);
        playerDmg = player.getDamage();
        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                Cell cell = new Cell(i, j, CellEntityType.VOID);
                row.add(cell);
                cell.setVisited(false);
            }
            grid.add(row);
        }

        // Set player
        ArrayList<Cell> rowP = grid.getFirst();
        Cell cellP = rowP.getFirst();
        cellP.setType(CellEntityType.PLAYER);
        cellP.setVisited(true);
        cellP.setPVisited(true);
        player.generateAbilities(playerDmg);

//        System.out.println(player.toString());

        ArrayList<Cell> rowF = grid.get(rows - 1);
        Cell cellF = rowF.get(cols - 1);
        cellF.setType(CellEntityType.PORTAL);

        ArrayList<Cell> rowS = grid.getFirst();
        Cell cellS = rowS.get(3);
        cellS.setType(CellEntityType.SANCTUARY);

        rowS = grid.get(1);
        cellS = rowS.get(3);
        cellS.setType(CellEntityType.SANCTUARY);

        rowS = grid.get(2);
        cellS = rowS.getFirst();
        cellS.setType(CellEntityType.SANCTUARY);

        rowS = grid.getLast();
        cellS = rowS.get(3);
        cellS.setType(CellEntityType.SANCTUARY);

        ArrayList<Cell> rowE = grid.get(3);
        Cell cellE = rowE.getLast();
        cellE.setType(CellEntityType.ENEMY);

        return grid;
    }

    public static Grid gridGenerator(int rows, int cols, Character player, Enemy enemy) {
        Grid grid = new Grid(rows, cols, player, enemy);
        Random rand = new Random();
        playerDmg = player.getDamage();

        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                Cell cell = new Cell(i, j, CellEntityType.VOID);
                row.add(cell);
            }
            grid.add(row);
        }

        int playerX, playerY;

        playerX = rand.nextInt(rows);
        playerY = rand.nextInt(cols);
        ArrayList<Cell> rowPlayer = grid.get(playerX);
        Cell playerCell = rowPlayer.get(playerY);
        playerCell.setPVisited(true);
        playerCell.setVisited(true);
        player.generateAbilities(playerDmg);

        playerCell.setType(CellEntityType.PLAYER);
        currentCell = playerCell;

        int portalX, portalY;
        portalX = rand.nextInt(rows);
        portalY = rand.nextInt(cols);
        while(playerX == portalX && playerY == portalY) {
            portalX = rand.nextInt(rows);
            portalY = rand.nextInt(cols);
        }
        ArrayList<Cell> rowPortal = grid.get(portalX);
        Cell portalCell = rowPortal.get(portalY);

        portalCell.setType(CellEntityType.PORTAL);

        int nSanctuary = rand.nextInt(4) + 2;
        int nEnemy = rand.nextInt(7) + 4;
        int SanctuaryX, SanctuaryY;
        int EnemyX, EnemyY;
        int i = 0;
        while (i < nSanctuary) {
            SanctuaryX = rand.nextInt(rows);
            SanctuaryY = rand.nextInt(cols);
            ArrayList<Cell> rowSanctuary = grid.get(SanctuaryX);
            Cell sanctuaryCell = rowSanctuary.get(SanctuaryY);

            if (sanctuaryCell.getType() == CellEntityType.VOID) {
                sanctuaryCell.setType(CellEntityType.SANCTUARY);
                i++;
            }
        }

        i = 0;

        while (i < nEnemy) {
            EnemyX = rand.nextInt(rows);
            EnemyY = rand.nextInt(cols);
            ArrayList<Cell> rowEnemy = grid.get(EnemyX);
            Cell enemyCell = rowEnemy.get(EnemyY);

            if (enemyCell.getType() == CellEntityType.VOID) {
                enemyCell.setType(CellEntityType.ENEMY);
                i++;
            }
        }
        return grid;
    }

    public Grid goEast(Grid grid)  {
        currentCell = this.getCurrentCell();
        int x = currentCell.getX();
        int y = currentCell.getY();
        if (y + 1 < cols) {
            ArrayList<Cell> row = this.get(x);
            Cell lastCell = row.get(y);
            Cell cell = row.get(y + 1);
            cell.setPVisited(true);
            cell.setVisited(true);
            lastCell.setType(CellEntityType.VOID);
            lastCell.setPVisited(false);

            // Print data about the current cell
            this.currentCell = cell;
            if (cell.getType() == CellEntityType.VOID) {
                System.out.println("You are traveling trough the wilderness!");
            } else if (cell.getType() == CellEntityType.SANCTUARY) {
                System.out.println("You found a sanctuary!");

                player.regenM(player.maxMana);
                player.regenH(player.maxHealth);
            } else if (cell.getType() == CellEntityType.PORTAL) {
                System.out.println("You found the portal!");
//                System.out.println("You won the game!");

                player.levelUp();
                // Exit the game
//                showMap();
//                System.exit(0);
                int newRows = rand.nextInt(3, 10) + 1;
                int newCols = rand.nextInt(3, 10) + 1;
                Grid newGrid = gridGenerator(newRows, newCols, player, enemy);
                grid = newGrid;
            } else if (cell.getType() == CellEntityType.ENEMY) {
                if (enemy.getHealth() <= 0) {
                    newEnemy();
                }

                try {
                    fight(cell);
                } catch (NoAbilities e) {
//                    throw new RuntimeException(e);
                }
            }

            // Print data about the player
            System.out.println(player.toString());

        } else {
            try {
                throw new ImpossibleMove("You can't go east");
            } catch (ImpossibleMove e) {
//                throw new RuntimeException(e);
                System.out.println("You can't go east");
            }
        }
        return grid;
    }

    public Grid goWest(Grid grid) {
        currentCell = this.getCurrentCell();
        int x = currentCell.getX();
        int y = currentCell.getY();
        if (y - 1 >= 0) {
            ArrayList<Cell> row = this.get(x);
            Cell lastCell = row.get(y);
            Cell cell = row.get(y - 1);
//            cell.setType(CellEntityType.PLAYER);
            cell.setPVisited(true);
            cell.setVisited(true);
            lastCell.setType(CellEntityType.VOID);
            lastCell.setPVisited(false);

            // Print data about the current cell
            this.currentCell = cell;
            if (cell.getType() == CellEntityType.VOID) {
                System.out.println("You are traveling trough the wilderness!");
            } else if (cell.getType() == CellEntityType.SANCTUARY) {
                System.out.println("You found a sanctuary!");

                player.regenM(player.maxMana);
                player.regenH(player.maxHealth);
            } else if (cell.getType() == CellEntityType.PORTAL) {
                System.out.println("You found the portal!");
//                System.out.println("You won the game!");
                player.levelUp();
                // Exit the game
//                showMap();
//                System.exit(0);
                int newRows = rand.nextInt(3, 10) + 1;
                int newCols = rand.nextInt(3, 10) + 1;
                Grid newGrid = gridGenerator(newRows, newCols, player, enemy);
                grid = newGrid;
            } else if (cell.getType() == CellEntityType.ENEMY) {
                if (enemy.getHealth() <= 0) {
                    newEnemy();
                }

                try {
                    fight(cell);
                } catch (NoAbilities e) {
//                    throw new RuntimeException(e);
                }
            }

            // Print data about the player
            System.out.println(player.toString());
        } else {
            try {
                throw new ImpossibleMove("You can't go west");
            } catch (ImpossibleMove e) {
//                throw new RuntimeException(e);
                System.out.println("You can't go west");
            }
        }
        return grid;
    }

    public Grid goNorth(Grid grid) {
        currentCell = this.getCurrentCell();
        int x = currentCell.getX();
        int y = currentCell.getY();
        if (x - 1 >= 0) {
            ArrayList<Cell> row = this.get(x - 1);
            ArrayList<Cell> lastRow = this.get(x);
            Cell lastCell = lastRow.get(y);
            Cell cell = row.get(y);
//            cell.setType(CellEntityType.PLAYER);
            cell.setPVisited(true);
            cell.setVisited(true);
            lastCell.setType(CellEntityType.VOID);
            lastCell.setPVisited(false);

            // Print data about the current cell
            this.currentCell = cell;
            if (cell.getType() == CellEntityType.VOID) {
                System.out.println("You are traveling trough the wilderness!");
            } else if (cell.getType() == CellEntityType.SANCTUARY) {
                System.out.println("You found a sanctuary!");

                player.regenM(player.maxMana);
                player.regenH(player.maxHealth);
            } else if (cell.getType() == CellEntityType.PORTAL) {
                System.out.println("You found the portal!");
//                System.out.println("You won the game!");
                player.levelUp();
                // Exit the game
//                showMap();
//                System.exit(0);
                int newRows = rand.nextInt(3, 10) + 1;
                int newCols = rand.nextInt(3, 10) + 1;
                Grid newGrid = gridGenerator(newRows, newCols, player, enemy);
                grid = newGrid;
            } else if (cell.getType() == CellEntityType.ENEMY) {
                if (enemy.getHealth() <= 0) {
                    newEnemy();
                }

                try {
                    fight(cell);
                } catch (NoAbilities e) {
//                    throw new RuntimeException(e);
                }
            }

            // Print data about the player
            System.out.println(player.toString());
        } else {
            try {
                throw new ImpossibleMove("You can't go north");
            } catch (ImpossibleMove e) {
//                throw new RuntimeException(e);
                System.out.println("You can't go north");
            }
        }
        return grid;
    }

    public Grid goSouth(Grid grid) {
        currentCell = this.getCurrentCell();
        int x = currentCell.getX();
        int y = currentCell.getY();
        if (x + 1 < rows) {
            ArrayList<Cell> row = this.get(x + 1);
            ArrayList<Cell> lastRow = this.get(x);
            Cell lastCell = lastRow.get(y);
            Cell cell = row.get(y);

            cell.setPVisited(true);
            cell.setVisited(true);
            lastCell.setType(CellEntityType.VOID);
            lastCell.setPVisited(false);

            // Print data about the current cell
            this.currentCell = cell;
            if (cell.getType() == CellEntityType.VOID) {
                System.out.println("You are traveling trough the wilderness!");
            } else if (cell.getType() == CellEntityType.SANCTUARY) {
                System.out.println("You found a sanctuary!");

                player.regenM(player.maxMana);
                player.regenH(player.maxHealth);
            } else if (cell.getType() == CellEntityType.PORTAL) {
                System.out.println("You found the portal!");
//                System.out.println("You won the game!");
                player.levelUp();
                // Exit the game
//                showMap();
//                System.exit(0);
                int newRows = rand.nextInt(3, 10) + 1;
                int newCols = rand.nextInt(3, 10) + 1;
                Grid newGrid = gridGenerator(newRows, newCols, player, enemy);
                grid = newGrid;
            } else if (cell.getType() == CellEntityType.ENEMY) {
                if (enemy.getHealth() <= 0) {
                    newEnemy();
                }

                try {
                    fight(cell);
                } catch (NoAbilities e) {
//                    throw new RuntimeException(e);
                }
            }

            // Print data about the player
            System.out.println(player.toString());
        } else {
            try {
                throw new ImpossibleMove("You can't go south");
            } catch (ImpossibleMove e) {
//                throw new RuntimeException(e);
                System.out.println("You can't go south");
            }
        }

        return grid;
    }

    public void fight(Cell cell) throws NoAbilities {
        System.out.println("You found an enemy!");
        System.out.println(enemy.toString());

        enemyDmg = enemy.getDamage();
        enemy.generateAbilities(enemyDmg);

        int experience = enemy.getHealth();

        while(enemy.getHealth() > 0) {
            System.out.println("Choose your attack:");
            System.out.println("1) Normal attack");
            System.out.println("2) Use ability");

            int i = input.nextInt();

            switch (i) {
                case 1:
                    enemy.receiveDamage(playerDmg);
                    System.out.println("You used a normal attack!");

                    if (enemy.getHealth() <= 0) {
                        System.out.println(GREEN + "You defeated the enemy!" + RESET);
                        cell.setType(CellEntityType.VOID);
//                    player.levelUp();
                    } else {
                        int enemyAbility = rand.nextInt(enemy.nAbilities + 1);
                        if (enemyAbility == enemy.nAbilities) {
                            player.receiveDamage(enemyDmg);
                            System.out.println("The enemy used a normal attack!");
                        } else {
                            enemy.useAbility(enemy.abilities.get(enemyAbility), player);
                            System.out.println("The enemy used " + enemy.abilities.get(enemyAbility).toString());
                            enemy.abilities.remove(enemyAbility);
                            enemy.nAbilities--;
                            if (player.getHealth() <= 0) {
                                System.out.println(RED + "You lost the game!" + RESET);
                                System.exit(0);
                            }
                        }
                        break;
                    }
                case 2:
                    System.out.println("You have " + player.nAbilities + " abilities:");
                    for (Spell spell : player.abilities) {
                        System.out.println(spell.toString());
                    }
                    System.out.println(CYAN + "[Normal]:" + RESET + " Damage: " + playerDmg);

                    System.out.println("The enemy has " + enemy.nAbilities + " abilities:");
                    for (Spell spell : enemy.abilities) {
                        System.out.println(spell.toString());
                    }
                    System.out.println(CYAN + "[Normal]:" + RESET + " Damage: " + enemyDmg);
                    i = input.nextInt();
                    if(player.abilities.isEmpty()) {
                        try {
                            throw new NoAbilities("You don't have any abilities left!");
                        } catch (NoAbilities e) {
//                        throw new RuntimeException(e);
                            System.out.println("You don't have any abilities left!");
                            continue;
                        }
                    }
                    Spell currentAbility = player.abilities.get(i);
                    if (currentAbility instanceof Fire && enemy.fireImmune) {
                        System.out.println("The enemy is immune to fire!");
                        player.abilities.remove(i);
                        player.nAbilities--;
                    } else if (currentAbility instanceof Ice && enemy.iceImmune) {
                        System.out.println("The enemy is immune to ice!");
                        player.abilities.remove(i);
                        player.nAbilities--;
                    } else if (currentAbility instanceof Earth && enemy.earthImmune) {
                        System.out.println(RED + "The enemy is immune to earth!" + RESET);
                        player.abilities.remove(i);
                        player.nAbilities--;
                    } else {
                        player.useAbility(player.abilities.get(i), enemy);
                        System.out.println("You used " + player.abilities.get(i).toString());
                        player.abilities.remove(i);
                        player.nAbilities--;
                    }

                    if (enemy.getHealth() <= 0) {

                        System.out.println(GREEN + "You defeated the enemy!" + RESET);
                        cell.setType(CellEntityType.VOID);
//                    player.levelUp();
                    } else {
                        int enemyAbility = rand.nextInt(enemy.nAbilities + 1);
                        if (enemyAbility == enemy.nAbilities) {
                            player.receiveDamage(enemyDmg);
                            System.out.println("The enemy used a normal attack!");
                        } else {
                            Spell currentEnemyAbility = enemy.abilities.get(enemyAbility);
                            if (currentEnemyAbility instanceof Fire && player.fireImmune) {
                                System.out.println("The enemy tried to use fire, but " + player.getName() + " is immune to fire!");
                                enemy.abilities.remove(enemyAbility);
                                enemy.nAbilities--;
                            } else if (currentEnemyAbility instanceof Ice && player.iceImmune) {
                                System.out.println("The enemy tried to use ice, but " + player.getName() + " is immune to ice!");
                                enemy.abilities.remove(enemyAbility);
                                enemy.nAbilities--;
                            } else if (currentEnemyAbility instanceof Earth && player.earthImmune) {
                                System.out.println("The enemy tried to use earth, but " + player.getName() + " is immune to earth!");
                                enemy.abilities.remove(enemyAbility);
                                enemy.nAbilities--;
                            } else {
                                enemy.useAbility(enemy.abilities.get(enemyAbility), player);
                                System.out.println("The enemy used " + enemy.abilities.get(enemyAbility).toString());
                                enemy.abilities.remove(enemyAbility);
                                enemy.nAbilities--;
                            }
                            if (player.getHealth() <= 0) {
                                System.out.println(RED + "You lost the game!" + RESET);
                                System.exit(0);
                            }
                        }
                    }
                    break;
                default:
                    if(player.abilities.isEmpty()) {
                        try {
                            throw new NoAbilities("You don't have any abilities left!");
                        } catch (NoAbilities e) {
//                        throw new RuntimeException(e);
                            System.out.println("You don't have any abilities left!");
                            continue;
                        }
                    }
                    break;
            }
//            System.out.println("You have " + player.nAbilities + " abilities:");
//            for (Spell spell : player.abilities) {
//                System.out.println(spell.toString());
//            }
//            System.out.println(CYAN + "[Normal]:" + RESET + " Damage: " + playerDmg);
//
//            System.out.println("The enemy has " + enemy.nAbilities + " abilities:");
//            for (Spell spell : enemy.abilities) {
//                System.out.println(spell.toString());
//            }
//            System.out.println(CYAN + "[Normal]:" + RESET + " Damage: " + enemyDmg);

//            i = input.nextInt();
//            if (i < 6) {
//                if(player.abilities.isEmpty()) {
//                    try {
//                        throw new NoAbilities("You don't have any abilities left!");
//                    } catch (NoAbilities e) {
////                        throw new RuntimeException(e);
//                        System.out.println("You don't have any abilities left!");
//                        continue;
//                    }
//                }
//                Spell currentAbility = player.abilities.get(i);
//                if (currentAbility instanceof Fire && enemy.fireImmune) {
//                    System.out.println("The enemy is immune to fire!");
//                    player.abilities.remove(i);
//                    player.nAbilities--;
//                } else if (currentAbility instanceof Ice && enemy.iceImmune) {
//                    System.out.println("The enemy is immune to ice!");
//                    player.abilities.remove(i);
//                    player.nAbilities--;
//                } else if (currentAbility instanceof Earth && enemy.earthImmune) {
//                    System.out.println(RED + "The enemy is immune to earth!" + RESET);
//                    player.abilities.remove(i);
//                    player.nAbilities--;
//                } else {
//                    player.useAbility(player.abilities.get(i), enemy);
//                    System.out.println("You used " + player.abilities.get(i).toString());
//                    player.abilities.remove(i);
//                    player.nAbilities--;
//                }
//
//                if (enemy.getHealth() <= 0) {
//
//                    System.out.println(GREEN + "You defeated the enemy!" + RESET);
//                    cell.setType(CellEntityType.VOID);
////                    player.levelUp();
//                } else {
//                    int enemyAbility = rand.nextInt(enemy.nAbilities + 1);
//                    if (enemyAbility == enemy.nAbilities) {
//                        player.receiveDamage(enemyDmg);
//                        System.out.println("The enemy used a normal attack!");
//                    } else {
//                        Spell currentEnemyAbility = enemy.abilities.get(enemyAbility);
//                        if (currentEnemyAbility instanceof Fire && player.fireImmune) {
//                            System.out.println("The enemy tried to use fire, but " + player.getName() + " is immune to fire!");
//                            enemy.abilities.remove(enemyAbility);
//                            enemy.nAbilities--;
//                        } else if (currentEnemyAbility instanceof Ice && player.iceImmune) {
//                            System.out.println("The enemy tried to use ice, but " + player.getName() + " is immune to ice!");
//                            enemy.abilities.remove(enemyAbility);
//                            enemy.nAbilities--;
//                        } else if (currentEnemyAbility instanceof Earth && player.earthImmune) {
//                            System.out.println("The enemy tried to use earth, but " + player.getName() + " is immune to earth!");
//                            enemy.abilities.remove(enemyAbility);
//                            enemy.nAbilities--;
//                        } else {
//                            enemy.useAbility(enemy.abilities.get(enemyAbility), player);
//                            System.out.println("The enemy used " + enemy.abilities.get(enemyAbility).toString());
//                            enemy.abilities.remove(enemyAbility);
//                            enemy.nAbilities--;
//                        }
//                        if (player.getHealth() <= 0) {
//                            System.out.println(RED + "You lost the game!" + RESET);
//                            System.exit(0);
//                        }
//                    }
//                }
//
//                if (player.getHealth() > 0 && enemy.getHealth() > 0) {
//                    System.out.println("Player: " + player.toString());
//                    System.out.println("Enemy: " + enemy.toString());
//                }
//            } else {
//                int playerDmg = player.getDamage();
//                enemy.receiveDamage(playerDmg);
//                System.out.println("You used a normal attack!");
//
//                if (enemy.getHealth() <= 0) {
//                    System.out.println(GREEN + "You defeated the enemy!" + RESET);
//                    cell.setType(CellEntityType.VOID);
////                    player.levelUp();
//                } else {
//                    int enemyAbility = rand.nextInt(enemy.nAbilities + 1);
//                    if (enemyAbility == enemy.nAbilities) {
//                        player.receiveDamage(enemyDmg);
//                        System.out.println("The enemy used a normal attack!");
//                    } else {
//                        enemy.useAbility(enemy.abilities.get(enemyAbility), player);
//                        System.out.println("The enemy used " + enemy.abilities.get(enemyAbility).toString());
//                        enemy.abilities.remove(enemyAbility);
//                        enemy.nAbilities--;
//                        if (player.getHealth() <= 0) {
//                            System.out.println(RED + "You lost the game!" + RESET);
//                            System.exit(0);
//                        }
//                    }
//                }
                if (player.getHealth() > 0 && enemy.getHealth() > 0) {
                    System.out.println("Player: " + player.toString());
                    System.out.println("Enemy: " + enemy.toString());
                }
            }
        }

    public void newEnemy() {
        boolean fireImmune = rand.nextBoolean();
        boolean iceImmune = rand.nextBoolean();
        boolean earthImmune = rand.nextBoolean();
        int health = rand.nextInt(100) + 1;
        int mana = rand.nextInt(100) + 1;

        enemy = new Enemy(health, mana, fireImmune, iceImmune, earthImmune);
    }

    public void showMap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ArrayList<Cell> row = this.get(i);
                Cell cell = row.get(j);

                CellEntityType type = cell.getType();
                String symb = type.getSymbol();
                if (firstShow == 0) {
                    if (symb == "P") {
                        System.out.print("| " + symb + " |");
                    } else {
                        System.out.print("  " + symb + "  ");
                    }
                    firstShow = 1;
                } else {
                    if (cell.isVisited()) {
                        if (cell.isPVisited()) {
                            System.out.print("| " + symb + " |");
                        } else {
                            System.out.print("  " + "V" + "  ");
                        }
                    } else {
                        System.out.print("  " + symb + "  ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
