package Implementations;

import DataModel.Employee;
import DataModel.Teacher;
import DataModel.Technician;
import DataModel.Ticket;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDaoImpl {



    public static ObservableList<Ticket> getAllTickets() {
        ObservableList<Ticket> tickets = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from tickets";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int ticketId = rs.getInt("ticket_ID");
                String type = rs.getString("type");
                String location = rs.getString("location");
                String description = rs.getString("description");
                int priority = rs.getInt("priority");
                int technicianID = rs.getInt("technician_id");
                int requesterID = rs.getInt("requester_id");
                Ticket a = new Ticket(ticketId,type, location, description, priority,
                        technicianID, requesterID);
                tickets.add(a);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return tickets;
    }

    public static ObservableList<Ticket> getAllTicketsByRequester(int requesterEmpID) {
        ObservableList<Ticket> ticketsByRequester = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM tickets where requester_id = " + requesterEmpID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ticketId = rs.getInt("ticket_ID");
                String type = rs.getString("type");
                String location = rs.getString("location");
                String description = rs.getString("description");
                int priority = rs.getInt("priority");
                int technicianID = rs.getInt("technician_id");
                int requesterID = rs.getInt("requester_id");
                Ticket a = new Ticket(ticketId,type, location, description, priority,
                        technicianID, requesterID);
                ticketsByRequester.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketsByRequester;
    }

    public static void addTicketTeacher(Teacher teacher, String type, String location, String description, int priority, int requesterID) {

        try {
            String sql = "INSERT INTO tickets (Type, Location, Description, Priority, Created_By, Last_Updated_by, Technician_ID, Requester_ID)" +
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

    public static void addTicketTechnician(Technician technician, String type, String location, String description, int priority, int requesterID, int technicianID) {
        try {
            String sql = "INSERT INTO tickets (Type, Location, description, priority, created_by, last_updated_by, technician_id, requester_id)" +
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

    public static void updateTicketTeacher(Teacher teacher, int ticketID, String type, int priority, String location, int requesterID, int technicianID, String description) {
        String sql = "UPDATE tickets SET type = ?, priority = ?, location = ?, requester_id = ?, technician_id = ?, description = ?, last_updated_by = ? WHERE ticket_id = " + ticketID;

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

    public static void updateTicketTechnicianOrManager(Employee signedInEmployee, int ticketID, String type, int priority, String location, int requesterID, int technicianID, String description) {
        String sql = "UPDATE tickets SET type = ?, priority = ?, location = ?, requester_id = ?, technician_id = ?, description = ?, last_updated_by = ? WHERE ticket_id = " + ticketID;

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

    public static void deleteTicket(int ticketID) {
        try {
            String sql = "DELETE FROM tickets WHERE ticket_id = " + ticketID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Ticket> getTicketsByType(String typeSelected) {
        ObservableList<Ticket> ticketsByType = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM tickets WHERE type = \"" + typeSelected +  "\" ORDER BY location";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int ticketID = rs.getInt("ticket_id");
                String type = rs.getString("type");
                String location = rs.getString("location");
                String creation_date = rs.getString("create_date");
                Ticket t = new Ticket(ticketID, type, location, creation_date);
                ticketsByType.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketsByType;
    }

    public static ObservableList<Ticket> getTotalTicketsPerTechnician() {
        ObservableList<Ticket> totalTicketsPerTechnician = FXCollections.observableArrayList();

        try {
            String sql = "SELECT technician_id, COUNT(*) as total, firstname, lastname FROM tickets, employees WHERE employee_id = technician_id";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int technicianID = rs.getInt("technician_id");
                int count = rs.getInt("total");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String fullNameAndID = firstName + " " + lastName + " " + technicianID;

                Ticket t = new Ticket(fullNameAndID, count);
                totalTicketsPerTechnician.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalTicketsPerTechnician;
    }

    public static ObservableList<Ticket> getTotalTickets() {
        ObservableList<Ticket> totalTickets = FXCollections.observableArrayList();

        try {
            String sql = "SELECT requester_id, firstName, lastname, location, type, create_date, technician_id from tickets, employees WHERE tickets.requester_id = employees.employee_id";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int requesterID = rs.getInt("requester_id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String location = rs.getString("location");
                String type = rs.getString("type");
                String createDate = rs.getString("create_date");
                int technicianID = rs.getInt("technician_id");
                String fullNameAndID = firstName + " " + lastName + " " + requesterID;

                Ticket t = new Ticket(requesterID, fullNameAndID, location, type, createDate, technicianID);
                totalTickets.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalTickets;
    }

    public static Ticket lookupTicket(int ticketID) {
        ObservableList<Ticket> allTickets = getTotalTickets();
        for (Ticket searchedTicket : allTickets) {
            if(searchedTicket.getTicketID() == ticketID) {
                return searchedTicket;
            }
        }
        return null;
    }

    public static ObservableList<Ticket> lookupTicket(String employeeName) {
        ObservableList<Ticket> searchedTickets = FXCollections.observableArrayList();
        ObservableList<Ticket> allTickets = getTotalTickets();

        for(Ticket searchedTicket : allTickets) {
            if(searchedTicket.getFullNameAndID().contains(employeeName)) {
                searchedTickets.add(searchedTicket);
            }
        }
        return searchedTickets;
    }

    public static ObservableList<Ticket> getAllTicketsByTechnician(int selectedTechnicianID) {
        ObservableList<Ticket> allTicketsByTechnician = FXCollections.observableArrayList();

        try {
            String sql = "SELECT technician_id, type, priority, location, create_date, firstname, lastname, requester_id FROM tickets, employees WHERE technician_id = " + selectedTechnicianID + " AND tickets.requester_id = employees.employee_id";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int technicianID = rs.getInt("technician_id");
                String type = rs.getString("type");
                int priority = rs.getInt("priority");
                String location = rs.getString("location");
                String createDate = rs.getString("create_date");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                int requesterID = rs.getInt("requester_id");
                String fullNameAndID = firstName + " " + lastName + " " + requesterID;

                Ticket t = new Ticket(technicianID, type, priority, location, createDate, fullNameAndID);
                allTicketsByTechnician.add(t);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allTicketsByTechnician;
    }

    public static ObservableList<Ticket> getTicketsWithNoTechnician() {
        ObservableList<Ticket> ticketsWithNoTechnician = FXCollections.observableArrayList();
        try {
            String sql = "SELECT ticket_id, type, priority, location, create_date, firstname, lastname, requester_id from tickets, employees WHERE technician_id IS NULL and tickets.requester_id = employees.employee_id";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int ticketID = rs.getInt("ticket_id");
                String type = rs.getString("type");
                int priority = rs.getInt("priority");
                String location = rs.getString("location");
                String createDate = rs.getString("create_date");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastName");
                int requesterID = rs.getInt("requester_id");
                String fullNameAndID = firstName + " " + lastName + " " + requesterID;

                Ticket t = new Ticket(ticketID, type, priority, location, createDate, fullNameAndID);
                ticketsWithNoTechnician.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketsWithNoTechnician;
    }
}
