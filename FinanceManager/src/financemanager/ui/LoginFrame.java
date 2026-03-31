package financemanager.ui;

import financemanager.dao.UserDAO;
import financemanager.models.User;
import financemanager.utils.SessionManager;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        setTitle("Personal Finance Manager - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Buttons
        JButton loginBtn = new JButton("Login");
        JButton exitBtn = new JButton("Exit");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(loginBtn);
        btnPanel.add(exitBtn);
        panel.add(btnPanel, gbc);

        loginBtn.addActionListener(e -> login());
        exitBtn.addActionListener(e -> System.exit(0));

        add(panel);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        User user = userDAO.authenticate(username, password);
        if (user != null) {
            SessionManager.setCurrentUser(user);
            if (user.getRole().equals("admin")) {
                new AdminDashboard().setVisible(true);
            } else {
                new UserDashboard().setVisible(true);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}