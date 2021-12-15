package DataModel;

public class Technician extends Employee {

    public Technician(int id, String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password, int schoolID, int tier, String type) {
        super(id, firstName, lastName, emailAddress, phoneNumber, username, password, schoolID);
    }

    public Technician(int id, String uName) {
        super(id, uName);
    }

    public Technician(int id, String uName, String firstName, String lastName) {
        super(id, uName, firstName, lastName);
    }

    public Technician(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }


    @Override
    public String toString() {
        return getFirstName() +" " + getLastName() + "ID: " + getId();
    }
}
