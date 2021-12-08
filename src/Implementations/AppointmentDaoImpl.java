package Implementations;

import DataModel.Appointment;
import DataModel.Employee;
import DataModel.Teacher;
import DataModel.Technician;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDaoImpl {

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String type = rs.getString("type");
                String location = rs.getString("location");
                String description = rs.getString("description");
                int priority = rs.getInt("priority");
                int technicianID = rs.getInt("technician_id");
                int requesterID = rs.getInt("requester_id");
                Appointment a = new Appointment(appointmentID,type, location, description, priority,
                        technicianID, requesterID);
                appointments.add(a);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointments;
    }

    public static ObservableList<Appointment> getAllAppointmentsByRequester (int requesterEmpID) {
        ObservableList<Appointment> appointmentsByRequester = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments where requester_id = " + requesterEmpID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String type = rs.getString("type");
                String location = rs.getString("location");
                String description = rs.getString("description");
                int priority = rs.getInt("priority");
                int technicianID = rs.getInt("technician_id");
                int requesterID = rs.getInt("requester_id");
                Appointment a = new Appointment(appointmentID,type, location, description, priority,
                        technicianID, requesterID);
                appointmentsByRequester.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsByRequester;
    }

    public static void addAppointmentTeacher(Teacher teacher, String type, String location, String description, int priority, int requesterID) {

        try {
            String sql = "INSERT INTO appointments (Type, Location, Description, Priority, Created_By, Last_Updated_by, Technician_ID, Requester_ID)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, type);
            ps.setString(2, location);
            ps.setString(3, description);
            ps.setInt(4, priority);
            ps.setString(5,teacher.getUserName());
            ps.setString(6,teacher.getUserName());
            ps.setObject(7,null);
            ps.setInt(8, requesterID);
            ps.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAppointmentTechnician(Technician technician, String type, String location, String description, int priority, int requesterID, int technicianID) {
        try {
            String sql = "INSERT INTO appointments (Type, Location, description, priority, created_by, last_updated_by, technician_id, requester_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, type);
            ps.setString(2, location);
            ps.setString(3, description);
            ps.setInt(4, priority);
            ps.setString(5, technician.getUserName());
            ps.setString(6, technician.getUserName());
            ps.setInt(7, technicianID);
            ps.setInt(8, requesterID);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAppointmentTeacher(Teacher teacher, int appointmentID, String type, int priority, String location, int requesterID, int technicianID, String description) {
        String sql = "UPDATE appointments SET type = ?, priority = ?, location = ?, requester_id = ?, technician_id = ?, description = ?, last_updated_by = ? WHERE appointment_id = " + appointmentID;

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, type);
            ps.setInt(2, priority);
            ps.setString(3, location);
            ps.setInt(4, requesterID);
            ps.setInt(5, technicianID);
            ps.setString(6, description);
            ps.setString(7, teacher.getUserName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAppointmentTechnicianOrManager(Employee signedInEmployee, int appointmentID, String type, int priority, String location, int requesterID, int technicianID, String description) {
        String sql = "UPDATE appointments SET type = ?, priority = ?, location = ?, requester_id = ?, technician_id = ?, description = ?, last_updated_by = ? WHERE appointment_id = " + appointmentID;

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, type);
            ps.setInt(2, priority);
            ps.setString(3, location);
            ps.setInt(4, requesterID);
            ps.setInt(5, technicianID);
            ps.setString(6, description);
            ps.setString(7, signedInEmployee.getUserName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAppointment(int appointmentID) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
