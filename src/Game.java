import java.util.Random;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Game {

    Random rand = new Random();
    private KeyHandler keyH = new KeyHandler();
    private Grid grid;
    private Character player = new Warrior("GIGI", 90, 20, 50, 10, 2);
    private int EnemyHealth  = rand.nextInt(100) + 1;
    private int EnemyMana = rand.nextInt(100) + 1;
    private int EnemyFireImmune = rand.nextInt(2);
    private int EnemyIceImmune = rand.nextInt(2);
    private int EnemyEarthImmune = rand.nextInt(2);
    private Enemy enemy = new Enemy(EnemyHealth, EnemyMana, EnemyFireImmune, EnemyIceImmune, EnemyEarthImmune);

    public Game() {
        addKeyListener(keyH);
    }

    public void runHard() {
        grid = Grid.gridHard(5, 5, player, enemy);
        grid.showMap();
        grid.goEast();
        grid.showMap();
        grid.goEast();
        grid.showMap();
        grid.goEast();
        grid.showMap();
        grid.goEast();
        grid.showMap();
        grid.goSouth();
        grid.showMap();
        grid.goSouth();
        grid.showMap();
        grid.goSouth();
        grid.showMap();
        grid.goSouth();
        grid.showMap();

    }

    public void run() {
//        player = new Character(100, 100);
        grid = Grid.gridGenerator(5, 5, player);
//        System.out.println(grid.toString());
        grid.showMap();
        grid.goEast();
        grid.showMap();
        grid.goEast();
        grid.showMap();
        grid.goWest();
        grid.showMap();
        grid.goSouth();
        grid.showMap();
    }

    public void update() {

    }


}
