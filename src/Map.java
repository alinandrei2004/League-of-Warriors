import javax.swing.*;
import java.awt.*;

public class Map extends JFrame {

    private Grid grid;
    private JPanel gridPanel, statsPanel;
    private Account account;
    private Character selectedCharacter;

    // JLabel references for stats
    private JLabel levelValue;
    private JLabel xpValue;
    private JLabel healthValue;
    private JLabel manaValue;

    public Map(Grid grid, Account account, Character selectedCharacter) {
        this.grid = grid;
        this.account = account;
        this.selectedCharacter = selectedCharacter;

        setTitle("League of Warriors");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Game Map", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridBagLayout());
        add(gridPanel, BorderLayout.CENTER);

        statsPanel = new JPanel();
        statsPanel.setLayout(new GridBagLayout());
        add(statsPanel, BorderLayout.WEST);

        initializeStatsPanel();

        createGrid();

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new FlowLayout());

        JButton northButton = new JButton("North");
        JButton southButton = new JButton("South");
        JButton eastButton = new JButton("East");
        JButton westButton = new JButton("West");

        controlsPanel.add(northButton);
        controlsPanel.add(southButton);
        controlsPanel.add(eastButton);
        controlsPanel.add(westButton);

        add(controlsPanel, BorderLayout.SOUTH);
        northButton.addActionListener(e -> move("NORTH"));
        southButton.addActionListener(e -> move("SOUTH"));
        eastButton.addActionListener(e -> move("EAST"));
        westButton.addActionListener(e -> move("WEST"));
    }

    private void initializeStatsPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        Font fieldFont = new Font("Arial", Font.PLAIN, 18);

        JLabel statsLabel = new JLabel("Stats", JLabel.LEFT);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statsPanel.add(statsLabel, gbc);

        gbc.gridy++;
        JLabel levelLabel = new JLabel("Level", JLabel.LEFT);
        JTextField levelField = new JTextField(String.valueOf(selectedCharacter.getLevel()));
        levelField.setEditable(false);
        levelField.setFont(fieldFont);
        statsPanel.add(levelLabel, gbc);
        gbc.gridx++;
        statsPanel.add(levelField, gbc);
        gbc.gridx = 0;

        gbc.gridy++;
        JLabel xpLabel = new JLabel("Experience", JLabel.LEFT);
        JTextField xpField = new JTextField(String.valueOf(selectedCharacter.getXP()));
        xpField.setEditable(false);
        xpField.setFont(fieldFont);
        statsPanel.add(xpLabel, gbc);
        gbc.gridx++;
        statsPanel.add(xpField, gbc);
        gbc.gridx = 0;

        gbc.gridy++;
        JLabel healthLabel = new JLabel("Health", JLabel.LEFT);
        JTextField healthField = new JTextField(String.valueOf(selectedCharacter.getHealth()));
        healthField.setEditable(false);
        healthField.setFont(fieldFont);
        statsPanel.add(healthLabel, gbc);
        gbc.gridx++;
        statsPanel.add(healthField, gbc);
        gbc.gridx = 0;

        gbc.gridy++;
        JLabel manaLabel = new JLabel("Mana", JLabel.LEFT);
        JTextField manaField = new JTextField(String.valueOf(selectedCharacter.getMana()));
        manaField.setEditable(false);
        manaField.setFont(fieldFont);
        statsPanel.add(manaLabel, gbc);
        gbc.gridx++;
        statsPanel.add(manaField, gbc);

        statsPanel.revalidate();
        statsPanel.repaint();
    }

    private void createGrid() {
        gridPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                Cell cell = grid.get(i).get(j);

                JButton cellButton = new JButton();
                cellButton.setPreferredSize(new Dimension(50, 50));
                cellButton.setEnabled(false);
                cellButton.setBackground(getCellColor(cell));

                gbc.gridx = j;
                gbc.gridy = i;

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
                grid = grid.goNorthGUI(grid);
                break;
            case "SOUTH":
                grid = grid.goSouthGUI(grid);
                break;
            case "EAST":
                grid = grid.goEastGUI(grid);
                break;
            case "WEST":
                grid = grid.goWestGUI(grid);
                break;
        }

        updateStatsPanel();
        createGrid();
    }

    private void updateStatsPanel() {
        levelValue.setText(String.valueOf(selectedCharacter.getLevel()));
        xpValue.setText(String.valueOf(selectedCharacter.getXP()));
        healthValue.setText(String.valueOf(selectedCharacter.getHealth()));
        manaValue.setText(String.valueOf(selectedCharacter.getMana()));

        statsPanel.revalidate();
        statsPanel.repaint();
    }
}

