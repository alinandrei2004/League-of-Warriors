import javax.swing.*;
import java.awt.*;

public class Map extends JFrame {

    private Grid grid;
    private JPanel gridPanel;

    public Map(Grid grid) {
        this.grid = grid;

        setTitle("League of Warriors");
        setSize(800, 800); // Adjusted size to fit grid and controls
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Label
        JLabel title = new JLabel("Game Map", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Grid Panel
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridBagLayout()); // Add spacing between cells
        add(gridPanel, BorderLayout.CENTER);

        // Create the grid
        createGrid();

        // Controls Panel
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new FlowLayout());

        JButton northButton = new JButton("North");
        JButton southButton = new JButton("South");
        JButton eastButton = new JButton("East");
        JButton westButton = new JButton("West");

        // Add buttons to control panel
        controlsPanel.add(northButton);
        controlsPanel.add(southButton);
        controlsPanel.add(eastButton);
        controlsPanel.add(westButton);

        // Add controls panel below grid
        add(controlsPanel, BorderLayout.SOUTH);

        // Action listeners for movement
        northButton.addActionListener(e -> move("NORTH"));
        southButton.addActionListener(e -> move("SOUTH"));
        eastButton.addActionListener(e -> move("EAST"));
        westButton.addActionListener(e -> move("WEST"));
    }

    private void createGrid() {
        gridPanel.removeAll(); // Clear the panel before re-drawing

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                Cell cell = grid.get(i).get(j);

                JButton cellButton = new JButton();
                cellButton.setPreferredSize(new Dimension(50, 50)); // Set fixed button size
                cellButton.setEnabled(false); // Disable interaction
                cellButton.setBackground(getCellColor(cell));

                // Set grid position
                gbc.gridx = j; // Column
                gbc.gridy = i; // Row

                // Add the button to the grid panel
                gridPanel.add(cellButton, gbc);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private Color getCellColor(Cell cell) {
        switch (cell.getType()) {
            case PLAYER:
                return Color.GREEN;
            case ENEMY:
                return Color.RED;
            case SANCTUARY:
                return Color.BLUE;
            case PORTAL:
                return Color.MAGENTA;
            case VOID:
            default:
                return Color.LIGHT_GRAY;
        }
    }

    private void move(String direction) {
        switch (direction) {
            case "NORTH":
                grid = grid.goNorth(grid);
                break;
            case "SOUTH":
                grid = grid.goSouth(grid);
                break;
            case "EAST":
                grid = grid.goEast(grid);
                break;
            case "WEST":
                grid = grid.goWest(grid);
                break;
        }

        createGrid(); // Refresh the grid after moving
    }
}
