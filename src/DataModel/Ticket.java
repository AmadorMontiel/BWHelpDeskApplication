package DataModel;

import java.sql.Timestamp;

public class Ticket {

    private int ticketID;
    private String type;
    private String location;
    private String description;
    private int priority;
    private int technicianID;
    private int requesterID;
    private Timestamp creation_date;

    public Ticket(int ticketID, String type, String location, String description, int priority, int technicianID, int requesterID) {
        this.ticketID = ticketID;
        this.type = type;
        this.location = location;
        this.description = description;
        this.priority = priority;
        this.technicianID = technicianID;
        this.requesterID = requesterID;
    }

    public Ticket(int ticketID, String type, String location, Timestamp creation_date) {
        this.ticketID = ticketID;
        this.type = type;
        this.location = location;
        this.creation_date = creation_date;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int appointmentID) {
        this.ticketID = appointmentID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getTechnicianID() {
        return technicianID;
    }

    public void setTechnicianID(int technicianID) {
        this.technicianID = technicianID;
    }

    public int getRequesterID() {
        return requesterID;
    }

    public void setRequesterID(int requesterID) {
        this.requesterID = requesterID;
    }

    @Override
    public String toString() {
        return "ID: "+ ticketID +
                " Type: " + type;
    }
}
