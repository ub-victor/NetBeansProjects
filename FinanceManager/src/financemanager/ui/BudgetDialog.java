package financemanager.ui;

import financemanager.dao.BudgetDAO;
import financemanager.dao.CategoryDAO;
import financemanager.models.Budget;
import financemanager.models.Category;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BudgetDialog extends JDialog {
    private JComboBox<String> monthCombo;
    private JComboBox<String> categoryCombo;
    private JTextField amountField;
    private boolean saved = false;
    private Budget budget;
    private int userId;
    private BudgetDAO budgetDAO = new BudgetDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    public BudgetDialog(JFrame parent, String title, Budget budget, int userId) {
        super(parent, title, true);
        this.budget = budget;
        this.userId = userId;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        // Month (select from available months or enter)
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Month (YYYY-MM):"), gbc);
        gbc.gridx = 1;
        monthCombo = new JComboBox<>();
        // Add last 12 months
        LocalDate now = LocalDate.now();
        for (int i = -6; i <= 6; i++) {
            YearMonth ym = YearMonth.from(now).plusMonths(i);
            monthCombo.addItem(ym.format(DateTimeFormatter.ofPattern("yyyy-MM")));
        }
        add(monthCombo, gbc);

        // Category (only expense categories)
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryCombo = new JComboBox<>();
        populateCategories();
        add(categoryCombo, gbc);

        // Amount
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(10);
        add(amountField, gbc);

        // Buttons
        JPanel btnPanel = new JPanel();
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (budget != null) {
            // Set month as YYYY-MM
            String monthStr = budget.getMonthYear().toString().substring(0, 7);
            monthCombo.setSelectedItem(monthStr);
            // Set category
            Category cat = getCategoryById(budget.getCategoryId());
            if (cat != null) {
                categoryCombo.setSelectedItem(cat.getName() + " (" + cat.getId() + ")");
            }
            amountField.setText(budget.getAmount().toString());
        }

        saveBtn.addActionListener(e -> save());
        cancelBtn.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }

    private void populateCategories() {
        categoryCombo.removeAllItems();
        List<Category> cats = categoryDAO.getAllCategories();
        for (Category c : cats) {
            if ("expense".equals(c.getType())) {
                categoryCombo.addItem(c.getName() + " (" + c.getId() + ")");
            }
        }
    }

    private Category getCategoryById(int id) {
        List<Category> cats = categoryDAO.getAllCategories();
        for (Category c : cats) if (c.getId() == id) return c;
        return null;
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
            String monthStr = (String) monthCombo.getSelectedItem();
            LocalDate firstDay = LocalDate.parse(monthStr + "-01");
            Date monthDate = Date.valueOf(firstDay);

            int catId = getCategoryIdFromSelected();
            if (catId == -1) {
                JOptionPane.showMessageDialog(this, "Please select a category");
                return;
            }
            BigDecimal amount = new BigDecimal(amountField.getText());

            if (budget == null) {
                Budget newBudget = new Budget(0, userId, catId, monthDate, amount);
                saved = budgetDAO.addOrUpdateBudget(newBudget);
            } else {
                budget.setCategoryId(catId);
                budget.setMonthYear(monthDate);
                budget.setAmount(amount);
                saved = budgetDAO.addOrUpdateBudget(budget);
            }
            if (saved) {
                JOptionPane.showMessageDialog(this, "Saved successfully");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error saving budget");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    public boolean isSaved() { return saved; }
}