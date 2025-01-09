import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class CharacterSelection extends JFrame {
    private Random rand = new Random();
    private Character selectedCharacter;

    public CharacterSelection(Account account) {
        setTitle("League of Warriors");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        JLabel title = new JLabel("Select a character", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel characterPanel = new JPanel();
        characterPanel.setLayout(new GridBagLayout());

        ArrayList<Character> characters = account.getCharacters();
        if (characters != null) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);

            for (int i = 0; i < characters.size(); i++) {
                JButton characterButton = getJButton(account, characters, i);

                gbc.gridx = 0;
                gbc.gridy = i;
                characterPanel.add(characterButton, gbc);
            }
        } else {
            JLabel noCharacters = new JLabel("No characters available", JLabel.CENTER);
            characterPanel.add(noCharacters);
        }

        add(characterPanel, BorderLayout.CENTER);

    }

    private JButton getJButton(Account account, ArrayList<Character> characters, int i) {
        Character character = characters.get(i);
        if (character.getHealth() <= 0) {
            int health = 100 + character.getLevel() * 5;
            character.setHealth(health);
        }
        JButton characterButton = new JButton(character.getName());
        characterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(
                        CharacterSelection.this,
                        "Welcome " + account.getInformation().getName() + "! You selected: " +
                                character.getName() + "\n" + character.toString(),
                        "Character Selected",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE
                );

                if (res == JOptionPane.OK_OPTION) {
                    dispose();

                    selectedCharacter = characters.get(i);
                    account.nKills = 0;

                    boolean fireImmune = rand.nextBoolean();
                    boolean iceImmune = rand.nextBoolean();
                    boolean earthImmune = rand.nextBoolean();
                    int health = rand.nextInt(50,100) + 1;
                    int mana = rand.nextInt(50, 100) + 1;
                    int maxEnemyHealth = rand.nextInt(selectedCharacter.health - 20, selectedCharacter.health + 20);
                    int maxEnemyMana = rand.nextInt(selectedCharacter.mana - 20, selectedCharacter.mana + 20);
                    Enemy enemy = new Enemy(maxEnemyHealth, maxEnemyMana, health, mana, fireImmune, iceImmune, earthImmune);
                    Grid grid = Grid.gridGenerator(5, 5, selectedCharacter, enemy, account, selectedCharacter);
                    SwingUtilities.invokeLater(() -> new Map(grid, account, selectedCharacter).setVisible(true));
                }
            }
        });
        return characterButton;
    }

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }
}
