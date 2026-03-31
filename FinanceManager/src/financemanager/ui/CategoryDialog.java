package financemanager.ui;

import financemanager.dao.CategoryDAO;
import financemanager.models.Category;
import javax.swing.*;
import java.awt.*;

public class CategoryDialog extends JDialog {
    private JTextField nameField;
    private JComboBox<String> typeCombo;
    private boolean saved = false;
    private Category category;
    private CategoryDAO categoryDAO = new CategoryDAO();

    public CategoryDialog(JFrame parent, String title, Category category) {
        super(parent, title, true);
        this.category = category;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        add(nameField, gbc);

        // Type
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        typeCombo = new JComboBox<>(new String[]{"income", "expense"});
        add(typeCombo, gbc);

        // Buttons
        JPanel btnPanel = new JPanel();
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (category != null) {
            nameField.setText(category.getName());
            typeCombo.setSelectedItem(category.getType());
        }

        saveBtn.addActionListener(e -> save());
        cancelBtn.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }

    private void save() {
        String name = nameField.getText().trim();
        String type = (String) typeCombo.getSelectedItem();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty");
            return;
        }
        if (category == null) {
            Category newCat = new Category(0, name, type);
            saved = categoryDAO.addCategory(newCat);
        } else {
            category.setName(name);
            category.setType(type);
            saved = categoryDAO.updateCategory(category);
        }
        if (saved) {
            JOptionPane.showMessageDialog(this, "Saved successfully");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error saving category");
        }
    }

    public boolean isSaved() { return saved; }
}