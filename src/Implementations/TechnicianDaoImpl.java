package Implementations;

import DataModel.Technician;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TechnicianDaoImpl {

    public static ObservableList<Technician> getAllTechnicians() {
        ObservableList<Technician> technicians = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from employees WHERE technician = 1";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int employeeID = rs.getInt("employee_id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                Technician t = new Technician(employeeID, firstname, lastname);
                technicians.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return technicians;
    }

    public static Technician getTechnicianByID(int technicianID) {
        Technician technician = null;

        try {
            String sql = "SELECT * FROM employees WHERE employee_id = " + technicianID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("employee_id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                technician = new Technician(id, firstName, lastName);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return technician;
    }
}
