package financemanager.dao;

import financemanager.db.DatabaseConnection;
import financemanager.models.Budget;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {
    public List<Budget> getBudgetsByUser(int userId) {
        List<Budget> list = new ArrayList<>();
        String sql = "SELECT * FROM budgets WHERE user_id = ? ORDER BY month_year DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Budget b = new Budget();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setCategoryId(rs.getInt("category_id"));
                b.setMonthYear(rs.getDate("month_year"));
                b.setAmount(rs.getBigDecimal("amount"));
                list.add(b);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean addOrUpdateBudget(Budget budget) {
        // Use INSERT ... ON DUPLICATE KEY UPDATE
        String sql = "INSERT INTO budgets (user_id, category_id, month_year, amount) VALUES (?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE amount = VALUES(amount)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, budget.getUserId());
            stmt.setInt(2, budget.getCategoryId());
            stmt.setDate(3, budget.getMonthYear());
            stmt.setBigDecimal(4, budget.getAmount());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteBudget(int budgetId, int userId) {
        String sql = "DELETE FROM budgets WHERE id=? AND user_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, budgetId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}