import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class AbilitiesGUI extends JFrame {
    private Character player;
    private Enemy enemy;
    private BattleGUI battleGUI;

    public AbilitiesGUI(Character player, Enemy enemy, BattleGUI battleGUI) {
        this.player = player;
        this.enemy = enemy;
        this.battleGUI = battleGUI;

        setTitle("Abilities");
        setSize(900, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1, player.abilities.size(), 10, 10));

        for (Spell ability : player.abilities) {
            add(createAbilityPanel(ability));
        }
    }

    private JPanel createAbilityPanel(Spell ability) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(3, 1));
        statsPanel.add(new JLabel("Name: " + ability.toString()));
        statsPanel.add(new JLabel("Mana Cost: " + ability.getManaCost()));
        statsPanel.add(new JLabel("Damage: " + ability.getDamage()));

        panel.add(statsPanel, BorderLayout.CENTER);

        JButton selectButton = new JButton("SELECT");
        selectButton.addActionListener(e -> useAbility(ability));
        panel.add(selectButton, BorderLayout.SOUTH);

        return panel;
    }

    private void useAbility(Spell ability) {
        if (player.getMana() >= ability.getManaCost()) {

            if (ability instanceof Fire && enemy.fireImmune) {
                JOptionPane.showMessageDialog(this, "Enemy is immune to fire damage!");
                player.wasteAbility(ability);
            } else if (ability instanceof Ice && enemy.iceImmune) {
                JOptionPane.showMessageDialog(this, "Enemy is immune to ice damage!");
                player.wasteAbility(ability);
            } else if (ability instanceof Earth && enemy.earthImmune) {
                JOptionPane.showMessageDialog(this, "Enemy is immune to earth damage!");
                player.wasteAbility(ability);
            } else {
                player.useAbility(ability, enemy);
                int enemyHealthOld = enemy.getHealth();
                enemy.accept(ability);
                int enemyHealthNew = enemy.getHealth();
                if (enemyHealthOld == enemyHealthNew) {
                    JOptionPane.showMessageDialog(this, "Enemy dodged the attack!");
                } else {
                    JOptionPane.showMessageDialog(this, "You used " + ability.toString() + "!");
                }
            }
            player.abilities.remove(ability);

            if (enemy.getHealth() <= 0) {
                player.doubleHeatlh();

                int randXp = new Random().nextInt(10, 21);
                player.addXP(randXp);
                JOptionPane.showMessageDialog(this, "Enemy defeated!\nYour health is doubled!\n" +
                        "You gained " + randXp + " experience!");
                battleGUI.account.nKills++;
                dispose();
                battleGUI.dispose();
            } else {
                battleGUI.enemyTurn();
            }
            battleGUI.updatePanels();
        } else {
            JOptionPane.showMessageDialog(this, "Not enough mana to use " + ability.toString() + "!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        dispose();
    }
}
