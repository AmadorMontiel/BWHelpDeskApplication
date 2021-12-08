package Implementations;

import DataModel.Employee;
import DataModel.Manager;
import DataModel.Teacher;
import DataModel.Technician;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImpl {

    public static int isValidLogin(String username, String password) {
        try {
            String sql = "SELECT * from employees";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if (username.equals(rs.getString("Username"))) {
                    if (password.equals(rs.getString("Password"))) {
                        if (rs.getBoolean("technician"))
                            return 1;
                        else if(rs.getBoolean("manager"))
                            return 2;
                        else if (rs.getBoolean("teacher"))
                            return 3;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public static Employee getEmployeeByUsername(String username) {

        try {
            String sql = "SELECT * FROM employees WHERE username = " + "\""+ username + "\"";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Employee_ID");
                String uName = rs.getString("Username");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                if (rs.getBoolean("teacher")) {
                    return new Teacher(id, uName, firstName, lastName);
                } else if (rs.getBoolean("manager")) {
                    return new Manager(id, uName, firstName, lastName);
                } else if (rs.getBoolean("technician")) {
                    return new Technician(id, uName, firstName, lastName);
                } else {
                    return null;
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

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

    public static boolean isEmpATechnicianByID(int id) {
        try {
            String sql = "SELECT * from employees WHERE technician = 1 AND employee_id = " + id;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if (id == rs.getInt("employee_id")) {
                    return rs.getBoolean("technician");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

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

    public static Employee getEmployeeByID(int requesterID) {

        try {
            String sql = "SELECT * FROM employees WHERE employee_id = " + requesterID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("employee_id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                if (rs.getBoolean("teacher")) {
                    return new Teacher(id, firstName, lastName);
                } else if (rs.getBoolean("manager")) {
                    return new Manager(id, firstName, lastName);
                } else if (rs.getBoolean("technician")) {
                    return new Technician(id, firstName, lastName);
                } else {
                    return null;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }

    public static ObservableList<Employee> getAllEmployees() {

        ObservableList<Employee> allEmployees = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from employees WHERE technician = 0";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int employeeID = rs.getInt("employee_id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                if (rs.getBoolean("teacher")) {
                    Teacher t = new Teacher(employeeID,firstName,lastName);
                    allEmployees.add(t);
                } else if (rs.getBoolean("manager")) {
                    Manager m = new Manager(employeeID, firstName, lastName);
                    allEmployees.add(m);
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allEmployees;
    }


}
