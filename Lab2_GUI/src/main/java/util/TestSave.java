package util;

import dao.BicycleDAO;
import model.Bicycle;

public class TestSave {
    public static void main(String[] args) {

        Bicycle b = new Bicycle(8, 12, 4, 4000);

        BicycleDAO dao = new BicycleDAO();
        dao.save(b);
    }
}