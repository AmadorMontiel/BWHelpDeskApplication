package DataModel;

public class Technician extends Employee {
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
        return getFirstName() +" " + getLastName() + " ID: " + getId();
    }
}
