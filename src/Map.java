import javax.swing.*;
import java.awt.*;

public class Map extends JFrame {

    private Grid grid;
    private JPanel gridPanel, statsPanel;
    private Account account;
    private Character selectedCharacter;

    private JTextField levelField;
    private JTextField xpField;
    private JTextField healthField;
    private JTextField manaField;

    public Map(Grid grid, Account account, Character selectedCharacter) {
        this.grid = grid;
        this.account = account;
        this.selectedCharacter = selectedCharacter;

        setTitle("League of Warriors");
        setSize(1400, 1000);
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

        gbc.gridy++;
        JLabel statsLabel = new JLabel("Stats");
        statsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statsPanel.add(statsLabel, gbc);

        gbc.gridy++;
        levelField = new JTextField("Level: " + selectedCharacter.getLevel());
        levelField.setEditable(false);
        levelField.setFont(fieldFont);
//        gbc.gridx++;
        statsPanel.add(levelField, gbc);
        gbc.gridx = 0;

        gbc.gridy++;
        xpField = new JTextField("Experience: " + selectedCharacter.getXP());
        xpField.setEditable(false);
        xpField.setFont(fieldFont);
//        gbc.gridx++;
        statsPanel.add(xpField, gbc);
        gbc.gridx = 0;

        gbc.gridy++;
        healthField = new JTextField("Health: " + selectedCharacter.getHealth());
        healthField.setEditable(false);
        healthField.setFont(fieldFont);
//        gbc.gridx++;
        statsPanel.add(healthField, gbc);
        gbc.gridx = 0;

        gbc.gridy++;
        manaField = new JTextField("Mana: " + selectedCharacter.getMana());
        manaField.setEditable(false);
        manaField.setFont(fieldFont);
//        gbc.gridx++;
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
                cellButton.setPreferredSize(new Dimension(75, 75));
                cellButton.setEnabled(false);

                if (cell.isVisited()) {
                    cellButton.setBackground(getCellColor(cell));
                    cellButton.setText("");
                } else {
                    cellButton.setBackground(Color.DARK_GRAY);
                    cellButton.setText("?");
                    cellButton.setFont(new Font("Arial", Font.BOLD, 24));
                    cellButton.setForeground(Color.WHITE);
                }
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
        levelField.setText("Level: " + selectedCharacter.getLevel());
        xpField.setText("Experience: " + selectedCharacter.getXP());
        healthField.setText("Health: " + selectedCharacter.getHealth());
        manaField.setText("Mana: " + selectedCharacter.getMana());

        statsPanel.revalidate();
        statsPanel.repaint();
    }
}


