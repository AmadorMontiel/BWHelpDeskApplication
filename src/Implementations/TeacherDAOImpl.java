package Implementations;

import Utility.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherDAOImpl {

    public static boolean isEmpATeacherByID(int id) {

        try {
            String sql = "SELECT * from employees WHERE teacher = 1 AND employee_id = " + id;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if (id == rs.getInt("employee_id")) {
                    return rs.getBoolean("teacher");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
