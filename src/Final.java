import javax.swing.*;
import java.awt.*;

public class Final extends JFrame {

    public Final(Account account, int nKills, int xp, int level) {
        setTitle("End of the Game");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Game Over", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel endPanel = new JPanel();
        endPanel.setLayout(new GridBagLayout());
        add(endPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel endMessage = new JLabel("You have reached the end of the game!", JLabel.CENTER);
        endPanel.add(endMessage, gbc);

        gbc.gridy = 1;
        JLabel kills = new JLabel("You have killed " + nKills + " enemies", JLabel.CENTER);
        endPanel.add(kills, gbc);

        gbc.gridy = 2;
        JLabel experience = new JLabel("You have earned " + xp + " XP", JLabel.CENTER);
        endPanel.add(experience, gbc);

        gbc.gridy = 3;
        JLabel levelReached = new JLabel("You have reached level " + level, JLabel.CENTER);
        endPanel.add(levelReached, gbc);

        gbc.gridy = 4;
        JButton playAgain = new JButton("Play again");
        endPanel.add(playAgain, gbc);

        gbc.gridy = 5;
        JButton exit = new JButton("Exit");
        endPanel.add(exit, gbc);

        CharacterSelection ch = new CharacterSelection(account);

        playAgain.addActionListener(e -> {
            dispose();
            ch.setVisible(true);
        });

        exit.addActionListener(e -> {
            System.exit(0);
        });

    }
}
