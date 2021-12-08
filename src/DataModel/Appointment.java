package DataModel;

public class Appointment {

    private int appointmentID;
    private String type;
    private String location;
    private String description;
    private int priority;
    private int technicianID;
    private int requesterID;

    public Appointment(int appointmentID, String type, String location, String description, int priority, int technicianID, int requesterID) {
        this.appointmentID = appointmentID;
        this.type = type;
        this.location = location;
        this.description = description;
        this.priority = priority;
        this.technicianID = technicianID;
        this.requesterID = requesterID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
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
        return "ID: "+ appointmentID +
                " Type: " + type;
    }
}
