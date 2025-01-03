import java.util.Random;
import java.util.Scanner;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Game {

    Random rand = new Random();
    private Grid grid;
    private Character player;
    private int EnemyHealth  = rand.nextInt(100) + 1;
    private int EnemyMana = rand.nextInt(100) + 1;
    private boolean EnemyFireImmune = rand.nextBoolean();
    private boolean EnemyIceImmune = rand.nextBoolean();
    private boolean EnemyEarthImmune = rand.nextBoolean();
    private Enemy enemy = new Enemy(EnemyHealth, EnemyMana, EnemyFireImmune, EnemyIceImmune, EnemyEarthImmune);
    public static final String RESET = "\u001B[0m";
    public static final String BR_GREEN = "\u001B[92m";
    public Game(Character player) {
        this.player = player;
    }

    public void runHard() throws ImpossibleMove, NoAbilities {
        Scanner input = new Scanner(System.in);
        grid = Grid.gridHard(5, 5, player, enemy);
        System.out.println(player.toString());
        grid.showMap();
        char move = (char)input.next().charAt(0);
        grid = grid.goEast(grid);
        grid.showMap();
        move = (char)input.next().charAt(0);
        grid = grid.goEast(grid);
        grid.showMap();
        move = (char)input.next().charAt(0);
        grid = grid.goEast(grid);
        grid.showMap();
        move = (char)input.next().charAt(0);
        grid = grid.goEast(grid);
        grid.showMap();
        move = (char)input.next().charAt(0);
        grid = grid.goSouth(grid);
        grid.showMap();
        move = (char)input.next().charAt(0);
        grid = grid.goSouth(grid);
        grid.showMap();
        move = (char)input.next().charAt(0);
        grid = grid.goSouth(grid);
        grid.showMap();
        move = (char)input.next().charAt(0);
        grid.goSouth(grid);
        grid.showMap();
        System.out.println(BR_GREEN +  "WOW, you are a true warrior!" + RESET);
        System.out.println(BR_GREEN +  "You completed the tutorial!" + RESET);

    }

    public void run() throws ImpossibleMove, NoAbilities {
//        player = new Character(100, 100);
        int x = rand.nextInt(3, 10) + 1;
        int y = rand.nextInt(3, 10) + 1;
        grid = Grid.gridGenerator(x, y, player, enemy);
        System.out.println(player.toString());
//        System.out.println(grid.toString());
        grid.showMap();
        Scanner input = new Scanner(System.in);
        char move = (char)input.next().charAt(0);

        while(move != 'q') {
            switch (move) {
                case 'a':
                    grid = grid.goWest(grid);
                    break;
                case 'd':
                    grid = grid.goEast(grid);
                    break;
                case 'w':
                    grid = grid.goNorth(grid);
                    break;
                case 's':
                    grid = grid.goSouth(grid);
                    break;
                default:
                    System.out.println("Invalid move");
                    break;
            }
            grid.showMap();
            move = (char)input.next().charAt(0);
        }
        System.out.println("Enough for today! I will be back tomorrow!");

    }


}
