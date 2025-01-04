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

    public LoginPage() {
        setTitle("League of Warriors");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JLabel title = new JLabel("Login", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel emailLabel = new JLabel("Email: ");
        emailField = new JTextField();
        JLabel passLabel = new JLabel("Password: ");
        passField = new JPasswordField();

        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(passLabel);
        inputPanel.add(passField);

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
                    break;
                } else {
                    message.setForeground(Color.RED);
                    message.setText("Invalid email or password!");
                }
            }
        }
    }
}
