import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BattleGUI extends JFrame {
    private Character player;
    private Enemy enemy;
    private Cell cell;
    public Account account;
    public Character selectedCharacter;

    private JPanel playerPanel;
    private JPanel enemyPanel;

    public BattleGUI(Character player, Enemy enemy, Cell cell, Account account, Character selectedCharacter) {
        this.player = player;
        this.enemy = enemy;
        this.cell = cell;
        this.account = account;
        this.selectedCharacter = selectedCharacter;

        while (!player.abilities.isEmpty()) {
            player.abilities.remove(0);
        }
        player.generateAbilities(player.getDamage());

        while (!enemy.abilities.isEmpty()) {
            enemy.abilities.remove(0);
        }
        enemy.generateAbilities(enemy.getDamage());

        setTitle("Battle");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        playerPanel = createCharacterPanel(player, "Player");
        enemyPanel = createEnemyPanel(enemy, "Enemy");

        add(playerPanel, BorderLayout.WEST);
        add(enemyPanel, BorderLayout.EAST);

        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton attackButton = new JButton("ATTACK");
        attackButton.setFont(new Font("Arial", Font.BOLD, 16));
        attackButton.addActionListener(e -> performAttack());
        actionsPanel.add(attackButton);

        JButton abilityButton = new JButton("ABILITY");
        abilityButton.setFont(new Font("Arial", Font.BOLD, 16));
        abilityButton.addActionListener(e -> openAbilitiesWindow());
        actionsPanel.add(abilityButton);

        add(actionsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createCharacterPanel(Character character, String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 1));
        statsPanel.add(new JLabel("Health: " + character.getHealth()));
        statsPanel.add(new JLabel("Mana: " + character.getMana()));

        panel.add(statsPanel, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createTitledBorder(title));

        return panel;
    }

    private JPanel createEnemyPanel(Enemy enemy, String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(5, 1));
        statsPanel.add(new JLabel("Health: " + enemy.getHealth()));
        statsPanel.add(new JLabel("Mana: " + enemy.getMana()));
        statsPanel.add(new JLabel("Immune to Fire: " + enemy.fireImmune));
        statsPanel.add(new JLabel("Immune to Ice: " + enemy.iceImmune));
        statsPanel.add(new JLabel("Immune to Earth: " + enemy.earthImmune));

        panel.add(statsPanel, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createTitledBorder(title));

        if (enemy.getHealth() <= 0) {
            cell.setType(CellEntityType.VOID);
        }
        return panel;
    }

    private void performAttack() {
        int enemyHealthOld = enemy.getHealth();
        enemy.receiveDamage(player.getDamage());
        int enemyHealthNew = enemy.getHealth();

        if (enemyHealthOld == enemyHealthNew) {
            JOptionPane.showMessageDialog(this, "The enemy dodged the attack!");
        } else {
            JOptionPane.showMessageDialog(this, "You attacked the enemy!");
        }

        if (enemy.getHealth() <= 0) {
            cell.setType(CellEntityType.VOID);
            player.doubleHeatlh();

            int randXp = new Random().nextInt(10, 21);
            player.addXP(randXp);
            JOptionPane.showMessageDialog(this, "Enemy defeated!\nYour health is doubled!\n" +
                    "You gained " + randXp + " experience!");
            account.nKills++;
            dispose();
        } else {
            enemyTurn();
        }

        updatePanels();
    }

    private void openAbilitiesWindow() {
        if (player.abilities == null || player.abilities.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You have no abilities!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            new AbilitiesGUI(player, enemy, this).setVisible(true);
        }
    }

    public void enemyTurn() {
        Random rand = new Random();
        int enemyAbilityIndex = rand.nextInt(enemy.nAbilities + 1);

        if (enemyAbilityIndex == enemy.nAbilities) {
            player.receiveDamage(enemy.getDamage());
            JOptionPane.showMessageDialog(this, "Enemy attacked you!");
        } else {
            Spell ability = enemy.abilities.get(enemyAbilityIndex);
            if (enemy.getMana() >= ability.getManaCost()) {
                enemy.useAbility(ability, player);
                player.accept(ability);
                JOptionPane.showMessageDialog(this, "Enemy used " + ability.toString() + "!");
                enemy.abilities.remove(ability);
                enemy.nAbilities--;
            } else {
                JOptionPane.showMessageDialog(this, "Enemy tried to use " + ability.toString() + " but didn't have enough mana!");
                player.receiveDamage(enemy.getDamage());
                JOptionPane.showMessageDialog(this, "Enemy used a normal attack!");
            }
        }

        if (player.getHealth() <= 0) {
            JOptionPane.showMessageDialog(this, "You lost the battle!", "Game Over", JOptionPane.ERROR_MESSAGE);
//            System.exit(0);
            dispose();
            for (Frame frame : Frame.getFrames()) {
                if (frame.isVisible()) {
                    frame.dispose();
                }
            }
            int xp = selectedCharacter.getXP();
            int level = selectedCharacter.getLevel();
            Final finalFrame = new Final(account, account.nKills, xp, level);
            finalFrame.setVisible(true);
        }

        updatePanels();
    }

    public void updatePanels() {
        remove(playerPanel);
        remove(enemyPanel);

        playerPanel = createCharacterPanel(player, "Player");
        enemyPanel = createEnemyPanel(enemy, "Enemy");

        add(playerPanel, BorderLayout.WEST);
        add(enemyPanel, BorderLayout.EAST);

        revalidate();
        repaint();
    }

}
