import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Game {

    private KeyHandler keyH = new KeyHandler();
    private Grid grid;
    private Character player = new Warrior("GIGI", 90, 20, 50, 10, 2);

    public void runHard() {
        grid = Grid.gridHard(5, 5, player);
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
