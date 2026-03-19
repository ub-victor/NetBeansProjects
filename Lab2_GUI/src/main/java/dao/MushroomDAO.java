package dao;

import model.Mushroom;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MushroomDAO {

    public void save(Mushroom m) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO mushroom(name, has_gills, forest, has_ring, convex) VALUES(?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, m.getName());
            ps.setBoolean(2, m.isHasGills());
            ps.setBoolean(3, m.isForest());
            ps.setBoolean(4, m.isHasRing());
            ps.setBoolean(5, m.isConvex());

            ps.executeUpdate();

            System.out.println("Mushroom saved!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}