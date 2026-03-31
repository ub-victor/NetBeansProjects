package financemanager.ui;

import financemanager.dao.*;
import financemanager.models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    private JTabbedPane tabbedPane;
    private UserDAO userDAO = new UserDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Users", createUsersPanel());
        tabbedPane.addTab("Categories", createCategoriesPanel());
        add(tabbedPane);
    }

    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Username", "Password", "Role"}, 0);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add User");
        JButton editBtn = new JButton("Edit User");
        JButton deleteBtn = new JButton("Delete User");
        JButton refreshBtn = new JButton("Refresh");
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        refreshUsers(model);

        refreshBtn.addActionListener(e -> refreshUsers(model));
        addBtn.addActionListener(e -> {
            UserDialog dialog = new UserDialog(this, "Add User", null);
            dialog.setVisible(true);
            if (dialog.isSaved()) refreshUsers(model);
        });
        editBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int userId = (int) model.getValueAt(selectedRow, 0);
                User user = getUserById(userId);
                if (user != null) {
                    UserDialog dialog = new UserDialog(this, "Edit User", user);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) refreshUsers(model);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a user to edit");
            }
        });
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int userId = (int) model.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Delete user? All transactions and budgets will be deleted.", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    userDAO.deleteUser(userId);
                    refreshUsers(model);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a user to delete");
            }
        });

        return panel;
    }

    private void refreshUsers(DefaultTableModel model) {
        model.setRowCount(0);
        List<User> users = userDAO.getAllUsers();
        for (User u : users) {
            model.addRow(new Object[]{u.getId(), u.getUsername(), u.getPassword(), u.getRole()});
        }
    }

    private User getUserById(int id) {
        List<User> users = userDAO.getAllUsers();
        for (User u : users) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    private JPanel createCategoriesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Type"}, 0);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add Category");
        JButton editBtn = new JButton("Edit Category");
        JButton deleteBtn = new JButton("Delete Category");
        JButton refreshBtn = new JButton("Refresh");
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        refreshCategories(model);

        refreshBtn.addActionListener(e -> refreshCategories(model));
        addBtn.addActionListener(e -> {
            CategoryDialog dialog = new CategoryDialog(this, "Add Category", null);
            dialog.setVisible(true);
            if (dialog.isSaved()) refreshCategories(model);
        });
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                Category cat = getCategoryById(id);
                if (cat != null) {
                    CategoryDialog dialog = new CategoryDialog(this, "Edit Category", cat);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) refreshCategories(model);
                }
            } else JOptionPane.showMessageDialog(this, "Select a category");
        });
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Delete category? Transactions using it may be affected.", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    categoryDAO.deleteCategory(id);
                    refreshCategories(model);
                }
            } else JOptionPane.showMessageDialog(this, "Select a category");
        });

        return panel;
    }

    private void refreshCategories(DefaultTableModel model) {
        model.setRowCount(0);
        List<Category> cats = categoryDAO.getAllCategories();
        for (Category c : cats) {
            model.addRow(new Object[]{c.getId(), c.getName(), c.getType()});
        }
    }

    private Category getCategoryById(int id) {
        List<Category> cats = categoryDAO.getAllCategories();
        for (Category c : cats) if (c.getId() == id) return c;
        return null;
    }
}