package DataModel;

public class Ticket {

    private int ticketID;
    private String type;
    private String location;
    private String description;
    private int priority;
    private int technicianID;
    private int requesterID;
    private String create_date;
    private int count;
    private String fullNameAndID;

    public Ticket(int ticketID, String type, String location, String description, int priority, int technicianID, int requesterID) {
        this.ticketID = ticketID;
        this.type = type;
        this.location = location;
        this.description = description;
        this.priority = priority;
        this.technicianID = technicianID;
        this.requesterID = requesterID;
    }

    public Ticket(int ticketID, String type, String location, String create_date) {
        this.ticketID = ticketID;
        this.type = type;
        this.location = location;
        this.create_date = create_date;
    }

    public Ticket(String fullNameAndID, int count) {
        this.fullNameAndID = fullNameAndID;
        this.count = count;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
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

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFullNameAndID() {
        return fullNameAndID;
    }

    public void setFullNameAndID(String fullNameAndID) {
        this.fullNameAndID = fullNameAndID;
    }

    @Override
    public String toString() {
        return "ID: "+ ticketID +
                " Type: " + type;
    }
}
