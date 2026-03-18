package dao;

import model.Bicycle;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BicycleDAO {
    
    public void delete(int id) {
    try {
        Connection conn = DBConnection.getConnection();

        String sql = "DELETE FROM bicycle WHERE id=?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();

        System.out.println("Deleted successfully!");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    public void update(Bicycle b) {
    try {
        Connection conn = DBConnection.getConnection();

        String sql = "UPDATE bicycle SET start_time=?, end_time=?, total_hours=?, amount=? WHERE id=?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, b.getStartTime());
        ps.setInt(2, b.getEndTime());
        ps.setInt(3, b.getTotalHours());
        ps.setInt(4, b.getAmount());
        ps.setInt(5, b.getId());

        ps.executeUpdate();

        System.out.println("Updated successfully!");

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void save(Bicycle b) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO bicycle(start_time, end_time, total_hours, amount) VALUES(?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, b.getStartTime());
            ps.setInt(2, b.getEndTime());
            ps.setInt(3, b.getTotalHours());
            ps.setInt(4, b.getAmount());

            ps.executeUpdate();

            System.out.println("Saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}