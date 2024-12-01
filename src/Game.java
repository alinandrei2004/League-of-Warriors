import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Game {

    private KeyHandler keyH = new KeyHandler();
    private Grid grid;
    private Character player;

    public void runHard() {
        grid = Grid.gridHard(5, 5, player);
        grid.showMap();
        grid.goEast();
        grid.showMap();
        grid.goEast();
        grid.showMap();
        grid.goEast();
        grid.showMap();
        grid.goWest();
        grid.showMap();
        grid.goEast();
        grid.showMap();
    }

    public void run() {
//        player = new Character(100, 100);
        grid = Grid.gridGenerator(5, 5, player);
//        System.out.println(grid.toString());
        grid.showMap();
    }

    public void update() {

    }


}
