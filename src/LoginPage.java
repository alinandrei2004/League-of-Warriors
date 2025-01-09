import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginPage extends JFrame{
    private JTextField emailField;
    private JPasswordField passField;
    private JButton loginButton;
    private JLabel message;

    private Account account;

    public LoginPage() {
        setTitle("League of Warriors");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        JLabel title = new JLabel("Login", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setHorizontalAlignment(JLabel.RIGHT);

        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(300, 30));

        JLabel passLabel = new JLabel("Password: ");
        passLabel.setHorizontalAlignment(JLabel.RIGHT);

        passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(300, 30));

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(passField, gbc);

        add(inputPanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        message = new JLabel("", JLabel.CENTER);

        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);

        add(message, BorderLayout.NORTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLogin();
            }
        });
    }

    public void checkLogin() {

        ArrayList<Account> accounts = JsonInput.deserializeAccounts();
        boolean ok = false;
        Account selectedAccount = null;

        String email = emailField.getText();
        String password = new String(passField.getPassword());
        for (Account account : accounts) {
            Credentials credentials = account.getInformation().getCredentials();
            if (credentials != null) {
                if (credentials.getEmail().equals(email) && credentials.getPassword().equals(password)) {
                    message.setForeground(Color.GREEN);
                    message.setText("You are logged in!");
                    ok = true;
                    selectedAccount = account;
                    JOptionPane.showMessageDialog(this, "Welcome " + account.getInformation().getName() + "!");
                    dispose();

                    this.account = account;
                    new CharacterSelection(account).setVisible(true);
                    break;
                } else {
                    message.setForeground(Color.RED);
                    message.setText("Invalid email or password!");
                }
            }
        }
    }
}
