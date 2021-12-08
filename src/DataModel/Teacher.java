package DataModel;

public class Teacher extends Employee {
    public Teacher(int id, String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password, int schoolID) {
        super(id, firstName, lastName, emailAddress, phoneNumber, username, password, schoolID);
    }

    public Teacher(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public Teacher(int id, String uName) {
        super(id, uName);
    }

    public Teacher(int id, String uName, String firstName, String lastName) {
        super(id, uName, firstName, lastName);
    }

    public void createAppointment() {

    }
}
