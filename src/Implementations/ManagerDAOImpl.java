package Implementations;

import Utility.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDAOImpl {

    public static boolean isEmpAManagerByID(int id) {
        try {
            String sql = "SELECT * from employees WHERE manager = 1 AND employee_id = " + id;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if (id == rs.getInt("employee_id")) {
                    return rs.getBoolean("manager");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
