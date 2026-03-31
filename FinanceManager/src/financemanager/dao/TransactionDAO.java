package financemanager.dao;

import financemanager.db.DatabaseConnection;
import financemanager.models.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    public List<Transaction> getTransactionsByUser(int userId) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setUserId(rs.getInt("user_id"));
                t.setCategoryId(rs.getInt("category_id"));
                t.setAmount(rs.getBigDecimal("amount"));
                t.setDate(rs.getDate("date"));
                t.setDescription(rs.getString("description"));
                t.setType(rs.getString("type"));
                list.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, category_id, amount, date, description, type) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getUserId());
            stmt.setInt(2, transaction.getCategoryId());
            stmt.setBigDecimal(3, transaction.getAmount());
            stmt.setDate(4, transaction.getDate());
            stmt.setString(5, transaction.getDescription());
            stmt.setString(6, transaction.getType());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET category_id=?, amount=?, date=?, description=?, type=? WHERE id=? AND user_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getCategoryId());
            stmt.setBigDecimal(2, transaction.getAmount());
            stmt.setDate(3, transaction.getDate());
            stmt.setString(4, transaction.getDescription());
            stmt.setString(5, transaction.getType());
            stmt.setInt(6, transaction.getId());
            stmt.setInt(7, transaction.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteTransaction(int transactionId, int userId) {
        String sql = "DELETE FROM transactions WHERE id=? AND user_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}