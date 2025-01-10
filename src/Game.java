import java.util.Random;
import java.util.Scanner;

public class Game {

    Random rand = new Random();
    private Grid grid;
    private Character player;
    private int EnemyHealth  = rand.nextInt(50, 100) + 1;
    private int EnemyMana = rand.nextInt(50, 100) + 1;
    private int maxEnemyHealth = 100;
    private int maxEnemyMana = 100;
    private boolean EnemyFireImmune = rand.nextBoolean();
    private boolean EnemyIceImmune = rand.nextBoolean();
    private boolean EnemyEarthImmune = rand.nextBoolean();
    private Enemy enemy = new Enemy(maxEnemyHealth, maxEnemyMana, EnemyHealth, EnemyMana, EnemyFireImmune, EnemyIceImmune, EnemyEarthImmune);
    public static final String RESET = "\u001B[0m";
    public static final String BR_GREEN = "\u001B[92m";
    public static Game finalGame;
    private Account account;
    private Character character;

    private Game(Character player, Account account, Character character) {
        this.player = player;
        this.account = account;
        this.character = character;
    }

    public static Game StartGame(Character player) {
        if (finalGame == null) {
            finalGame = new Game(player, null, null);
        }
        return finalGame;
    }

    public void runHard() throws ImpossibleMove, NoAbilities {
        Scanner input = new Scanner(System.in);
        grid = Grid.gridHard(5, 5, player, enemy, account, character);
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
        grid = Grid.gridGenerator(x, y, player, enemy, account, character);
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
