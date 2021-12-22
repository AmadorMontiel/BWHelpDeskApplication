package DataModel;

public class Teacher extends Employee {
    public Teacher(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public Teacher(int id, String uName, String firstName, String lastName) {
        super(id, uName, firstName, lastName);
    }

}
