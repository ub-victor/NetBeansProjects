package financemanager.ui;

import financemanager.dao.*;
import financemanager.models.*;
import financemanager.utils.SessionManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class UserDashboard extends JFrame {
    private JTabbedPane tabbedPane;
    private TransactionDAO transactionDAO = new TransactionDAO();
    private BudgetDAO budgetDAO = new BudgetDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private int currentUserId = SessionManager.getCurrentUser().getId();

    public UserDashboard() {
        setTitle("Personal Finance Manager - " + SessionManager.getCurrentUser().getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Transactions", createTransactionsPanel());
        tabbedPane.addTab("Budgets", createBudgetsPanel());
        tabbedPane.addTab("Reports", createReportsPanel());
        add(tabbedPane);
    }

    private JPanel createTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table for transactions
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Date", "Category", "Type", "Amount", "Description"}, 0);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add Transaction");
        JButton editBtn = new JButton("Edit Transaction");
        JButton deleteBtn = new JButton("Delete Transaction");
        JButton refreshBtn = new JButton("Refresh");
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        refreshTransactions(model);

        refreshBtn.addActionListener(e -> refreshTransactions(model));
        addBtn.addActionListener(e -> {
            AddTransactionDialog dialog = new AddTransactionDialog(this, "Add Transaction", null, currentUserId);
            dialog.setVisible(true);
            if (dialog.isSaved()) refreshTransactions(model);
        });
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                Transaction t = getTransactionById(id);
                if (t != null) {
                    AddTransactionDialog dialog = new AddTransactionDialog(this, "Edit Transaction", t, currentUserId);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) refreshTransactions(model);
                }
            } else JOptionPane.showMessageDialog(this, "Select a transaction");
        });
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Delete transaction?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    transactionDAO.deleteTransaction(id, currentUserId);
                    refreshTransactions(model);
                }
            } else JOptionPane.showMessageDialog(this, "Select a transaction");
        });

        return panel;
    }

    private void refreshTransactions(DefaultTableModel model) {
        model.setRowCount(0);
        List<Transaction> transactions = transactionDAO.getTransactionsByUser(currentUserId);
        for (Transaction t : transactions) {
            // get category name
            Category cat = getCategoryById(t.getCategoryId());
            model.addRow(new Object[]{
                t.getId(), t.getDate(), cat != null ? cat.getName() : "?", t.getType(),
                t.getAmount(), t.getDescription()
            });
        }
    }

    private Transaction getTransactionById(int id) {
        List<Transaction> list = transactionDAO.getTransactionsByUser(currentUserId);
        for (Transaction t : list) if (t.getId() == id) return t;
        return null;
    }

    private Category getCategoryById(int id) {
        List<Category> cats = categoryDAO.getAllCategories();
        for (Category c : cats) if (c.getId() == id) return c;
        return null;
    }

    // Budgets panel
    private JPanel createBudgetsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Month", "Category", "Budget Amount"}, 0);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Set/Update Budget");
        JButton deleteBtn = new JButton("Delete Budget");
        JButton refreshBtn = new JButton("Refresh");
        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        refreshBudgets(model);

        refreshBtn.addActionListener(e -> refreshBudgets(model));
        addBtn.addActionListener(e -> {
            BudgetDialog dialog = new BudgetDialog(this, "Set Budget", null, currentUserId);
            dialog.setVisible(true);
            if (dialog.isSaved()) refreshBudgets(model);
        });
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Delete budget?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    budgetDAO.deleteBudget(id, currentUserId);
                    refreshBudgets(model);
                }
            } else JOptionPane.showMessageDialog(this, "Select a budget");
        });

        return panel;
    }

    private void refreshBudgets(DefaultTableModel model) {
        model.setRowCount(0);
        List<Budget> budgets = budgetDAO.getBudgetsByUser(currentUserId);
        for (Budget b : budgets) {
            Category cat = getCategoryById(b.getCategoryId());
            model.addRow(new Object[]{b.getId(), b.getMonthYear(), cat != null ? cat.getName() : "?", b.getAmount()});
        }
    }

    // Reports panel (simple summary)
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        JButton generateBtn = new JButton("Generate Report");
        generateBtn.addActionListener(e -> generateReport(reportArea));
        panel.add(generateBtn, BorderLayout.NORTH);
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        return panel;
    }

    private void generateReport(JTextArea reportArea) {
        // Calculate total income, total expense, and budgets vs actual for current month
        LocalDate now = LocalDate.now();
        Date monthStart = Date.valueOf(now.withDayOfMonth(1));
        Date monthEnd = Date.valueOf(now.withDayOfMonth(now.lengthOfMonth()));

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;
        List<Transaction> transactions = transactionDAO.getTransactionsByUser(currentUserId);
        for (Transaction t : transactions) {
            if (t.getDate().after(monthStart) && t.getDate().before(monthEnd)) {
                if ("income".equals(t.getType())) totalIncome = totalIncome.add(t.getAmount());
                else totalExpense = totalExpense.add(t.getAmount());
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Monthly Report: ").append(now.getMonth()).append(" ").append(now.getYear()).append("\n");
        sb.append("Total Income: ").append(totalIncome).append("\n");
        sb.append("Total Expense: ").append(totalExpense).append("\n");
        sb.append("Net: ").append(totalIncome.subtract(totalExpense)).append("\n\n");

        sb.append("Budget vs Actual (Expense categories):\n");
        List<Budget> budgets = budgetDAO.getBudgetsByUser(currentUserId);
        for (Budget b : budgets) {
            if (b.getMonthYear().equals(monthStart)) {
                Category cat = getCategoryById(b.getCategoryId());
                if (cat != null && "expense".equals(cat.getType())) {
                    BigDecimal actual = BigDecimal.ZERO;
                    for (Transaction t : transactions) {
                        if (t.getCategoryId() == b.getCategoryId() && "expense".equals(t.getType())
                                && t.getDate().after(monthStart) && t.getDate().before(monthEnd)) {
                            actual = actual.add(t.getAmount());
                        }
                    }
                    sb.append(cat.getName()).append(": Budget ").append(b.getAmount())
                      .append(", Actual ").append(actual).append("\n");
                }
            }
        }

        reportArea.setText(sb.toString());
    }
}