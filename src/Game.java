import java.util.Random;
import java.util.Scanner;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Game {

    Random rand = new Random();
    private KeyHandler keyH = new KeyHandler();
    private Grid grid;
    private Character player = new Warrior("GIGI", 90, 20, 50, 10, 2);
    private int EnemyHealth  = rand.nextInt(100) + 1;
    private int EnemyMana = rand.nextInt(100) + 1;
    private boolean EnemyFireImmune = rand.nextBoolean();
    private boolean EnemyIceImmune = rand.nextBoolean();
    private boolean EnemyEarthImmune = rand.nextBoolean();
    private Enemy enemy = new Enemy(EnemyHealth, EnemyMana, EnemyFireImmune, EnemyIceImmune, EnemyEarthImmune);
    public static final String RESET = "\u001B[0m";
    public static final String BR_GREEN = "\u001B[92m";
    public Game() {
        addKeyListener(keyH);
    }

    public void runHard() throws ImpossibleMove, NoAbilities {
        grid = Grid.gridHard(5, 5, player, enemy);
        System.out.println(player.toString());
        grid.showMap();
        grid.goEast();
        grid.showMap();
        grid.goEast();
        grid.showMap();
        grid.goEast();
        grid.showMap();
        grid.goEast();
        grid.showMap();
        grid = grid.goSouth(grid);
        grid.showMap();
        grid = grid.goSouth(grid);
        grid.showMap();
        grid = grid.goSouth(grid);
        grid.showMap();
        grid = grid.goSouth(grid);
        grid.showMap();
        System.out.println(BR_GREEN +  "WOW, you are a true warrior!" + RESET);
        System.out.println(BR_GREEN +  "You completed the tutorial!" + RESET);

    }

    public void run() throws ImpossibleMove, NoAbilities {
//        player = new Character(100, 100);
        int x = rand.nextInt(3, 10) + 1;
        int y = rand.nextInt(3, 10) + 1;
        grid = Grid.gridGenerator(x, y, player, enemy);
//        System.out.println(grid.toString());
        grid.showMap();
        Scanner input = new Scanner(System.in);
        char move = (char)input.next().charAt(0);

        while(move != 'q') {
            switch (move) {
                case 'a':
                    grid.goWest();
                    break;
                case 'd':
                    grid.goEast();
                    break;
                case 'w':
                    grid.goNorth();
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

    }

    public void update() {

    }


}
