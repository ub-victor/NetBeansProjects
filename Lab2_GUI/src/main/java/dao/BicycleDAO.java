package dao;

import model.Bicycle;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BicycleDAO {

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