package DataModel;

public class Manager extends Employee {

    public Manager(int id, String uName) {
        super(id, uName);
    }

    public Manager(int id, String uName, String firstName, String lastName) {
        super(id, uName, firstName, lastName);
    }

    public Manager(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }
}
