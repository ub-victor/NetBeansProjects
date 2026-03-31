package financemanager.ui;

import financemanager.dao.UserDAO;
import financemanager.models.User;
import javax.swing.*;
import java.awt.*;

public class UserDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private boolean saved = false;
    private User user;
    private UserDAO userDAO = new UserDAO();

    public UserDialog(JFrame parent, String title, User user) {
        super(parent, title, true);
        this.user = user;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        roleCombo = new JComboBox<>(new String[]{"user", "admin"});
        add(roleCombo, gbc);

        JPanel btnPanel = new JPanel();
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (user != null) {
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            roleCombo.setSelectedItem(user.getRole());
        }

        saveBtn.addActionListener(e -> save());
        cancelBtn.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }

    private void save() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleCombo.getSelectedItem();
        if (user == null) {
            User newUser = new User(0, username, password, role);
            saved = userDAO.addUser(newUser);
        } else {
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            saved = userDAO.updateUser(user);
        }
        if (saved) {
            JOptionPane.showMessageDialog(this, "Saved successfully");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error saving user");
        }
    }

    public boolean isSaved() { return saved; }
}