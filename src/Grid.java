import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private int rows, cols, firstShow = 0;
    private Character player;
    private static Cell currentCell = new Cell(0, 0, CellEntityType.PLAYER);

    private Grid(int rows, int cols, Character player) {
        this.rows = rows;
        this.cols = cols;
        this.player = player;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public static Grid gridHard(int rows, int cols, Character player) {
        Grid grid = new Grid(rows, cols, player);

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

        System.out.println(player.toString());

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

    public static Grid gridGenerator(int rows, int cols, Character player) {
        Grid grid = new Grid(rows, cols, player);
        Random rand = new Random();

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

        playerCell.setType(CellEntityType.PLAYER);
        currentCell = playerCell;

        int portalX, portalY;
        portalX = rand.nextInt(rows);
        portalY = rand.nextInt(cols);
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

    public void goEast() {
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
                System.out.println("You won the game!");

                // Exit the game
                showMap();
                System.exit(0);
            }

            // Print data about the player
            System.out.println(player.toString());

        } else {
            System.out.println("You can't go east");
        }
    }

    public void goWest() {
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
                System.out.println("You won the game!");

                // Exit the game
                showMap();
                System.exit(0);
            }

            // Print data about the player
            System.out.println(player.toString());
        } else {
            System.out.println("You can't go west");
        }
    }

    public void goNorth() {
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
                System.out.println("You won the game!");

                // Exit the game
                showMap();
                System.exit(0);
            }

            // Print data about the player
            System.out.println(player.toString());
        } else {
            System.out.println("You can't go north");
        }
    }

    public void goSouth() {
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
                System.out.println("You won the game!");

                // Exit the game
                showMap();
                System.exit(0);
            }

            // Print data about the player
            System.out.println(player.toString());
        } else {
            System.out.println("You can't go south");
        }
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
