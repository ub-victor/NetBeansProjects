package financemanager.ui;

import financemanager.dao.*;
import financemanager.models.*;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AddTransactionDialog extends JDialog {
    private JComboBox<String> typeCombo;
    private JComboBox<String> categoryCombo;
    private JTextField amountField;
    private JSpinner dateSpinner;
    private JTextArea descArea;
    private boolean saved = false;
    private Transaction transaction;
    private int userId;
    private CategoryDAO categoryDAO = new CategoryDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public AddTransactionDialog(JFrame parent, String title, Transaction transaction, int userId) {
        super(parent, title, true);
        this.transaction = transaction;
        this.userId = userId;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        // Type
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        typeCombo = new JComboBox<>(new String[]{"income", "expense"});
        typeCombo.addActionListener(e -> updateCategories());
        add(typeCombo, gbc);

        // Category
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryCombo = new JComboBox<>();
        add(categoryCombo, gbc);

        // Amount
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(10);
        add(amountField, gbc);

        // Date
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Date:"), gbc);
        gbc.gridx = 1;
        SpinnerDateModel model = new SpinnerDateModel();
        dateSpinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);
        add(dateSpinner, gbc);

        // Description
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descArea = new JTextArea(3, 20);
        add(new JScrollPane(descArea), gbc);

        // Buttons
        JPanel btnPanel = new JPanel();
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (transaction != null) {
            typeCombo.setSelectedItem(transaction.getType());
            updateCategories();
            categoryCombo.setSelectedItem(getCategoryName(transaction.getCategoryId()));
            amountField.setText(transaction.getAmount().toString());
            dateSpinner.setValue(transaction.getDate());
            descArea.setText(transaction.getDescription());
        } else {
            dateSpinner.setValue(Date.valueOf(LocalDate.now()));
        }

        saveBtn.addActionListener(e -> save());
        cancelBtn.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }

    private void updateCategories() {
        String selectedType = (String) typeCombo.getSelectedItem();
        categoryCombo.removeAllItems();
        List<Category> cats = categoryDAO.getAllCategories();
        for (Category c : cats) {
            if (c.getType().equals(selectedType)) {
                categoryCombo.addItem(c.getName() + " (" + c.getId() + ")");
            }
        }
    }

    private String getCategoryName(int id) {
        List<Category> cats = categoryDAO.getAllCategories();
        for (Category c : cats) if (c.getId() == id) return c.getName() + " (" + c.getId() + ")";
        return "";
    }

    private int getCategoryIdFromSelected() {
        String selected = (String) categoryCombo.getSelectedItem();
        if (selected == null) return -1;
        int start = selected.lastIndexOf('(');
        int end = selected.lastIndexOf(')');
        if (start != -1 && end != -1) {
            return Integer.parseInt(selected.substring(start+1, end));
        }
        return -1;
    }

    private void save() {
        try {
            String type = (String) typeCombo.getSelectedItem();
            int catId = getCategoryIdFromSelected();
            BigDecimal amount = new BigDecimal(amountField.getText());
            Date date = new Date(((java.util.Date) dateSpinner.getValue()).getTime());
            String desc = descArea.getText();

            if (transaction == null) {
                Transaction t = new Transaction();
                t.setUserId(userId);
                t.setCategoryId(catId);
                t.setAmount(amount);
                t.setDate(date);
                t.setDescription(desc);
                t.setType(type);
                saved = transactionDAO.addTransaction(t);
            } else {
                transaction.setCategoryId(catId);
                transaction.setAmount(amount);
                transaction.setDate(date);
                transaction.setDescription(desc);
                transaction.setType(type);
                saved = transactionDAO.updateTransaction(transaction);
            }
            if (saved) {
                JOptionPane.showMessageDialog(this, "Saved");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error saving");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    public boolean isSaved() { return saved; }
}