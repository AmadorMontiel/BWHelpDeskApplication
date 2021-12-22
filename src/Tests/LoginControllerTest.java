package Tests;

import Implementations.EmployeeDaoImpl;
import Utility.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class LoginControllerTest {

    public String testInvalidTeacher = "teacher";
    public String testInvalidTeacherPassword = "teacher1";
    public String testValidTeacher = "ajohnson";
    public String testValidTeacherPassword = "ajohnson1";

    public String testInvalidManager = "jowens";
    public String testInvalidManagerPassword = "Welcome#1";
    public String testValidManager = "lbyers";
    public String testValidManagerPassword = "Welcome#1";

    public String testInvalidTechnician = "lbyers";
    public String testInvalidTechnicianPassword = "123456";
    public String testValidTechnician = "amontiel";
    public String testValidTechnicianPassword = "Welcome#1";

    @BeforeEach
    void setUp() {
        DBConnection.startConnection();
    }

    @Test
    void loginWithTeacher() {
        try {
            if (EmployeeDaoImpl.isValidLogin(testValidTeacher, testValidTeacherPassword) == 3) {
                System.out.println("Correct Teacher Info - Test Passed");
            } else {
                fail("Incorrect Teacher Info - Test Failed");
            }
        } catch (Exception e) {
            fail("No username or password exists");
        }
    }

    @Test
    void loginWithTeacherInvalid() {
        try {
            if (EmployeeDaoImpl.isValidLogin(testInvalidTeacher, testInvalidTeacherPassword) != 3) {
                System.out.println("Incorrect Teacher Info - Test Passed");
            } else {
                fail("Correct Teacher Info - Test Failed");
            }
        } catch (Exception e) {
            fail("No username or password exists");
        }
    }

    @Test
    void loginWithTechnician() {
        try {
            if (EmployeeDaoImpl.isValidLogin(testValidTechnician, testValidTechnicianPassword) == 1) {
                System.out.println("Correct Technician Info - Test Passed");
            } else {
                fail("Incorrect Technician Info - Test Failed");
            }
        } catch (Exception e) {
            fail("No username or password exists");
        }
    }

    @Test
    void loginWithTechnicianInvalid() {
        try {
            if (EmployeeDaoImpl.isValidLogin(testInvalidTechnician, testInvalidTechnicianPassword) != 1) {
                System.out.println("incorrect Technician Info - Test Passed");
            } else {
                fail("Correct Technician Info - Test Failed");
            }
        } catch (Exception e) {
            fail("No username or password exists");
        }
    }

    @Test
    void loginWithManager() {
        try {
            if (EmployeeDaoImpl.isValidLogin(testValidManager, testValidManagerPassword) == 2) {
                System.out.println("Correct Manager Info - Test Passed");
            } else {
                fail("Incorrect Manager Info - Test Failed");
            }
        } catch (Exception e) {
            fail("No username or password exists");
        }
    }

    @Test
    void loginWithManagerInvalid() {
        try {
            if (EmployeeDaoImpl.isValidLogin(testInvalidManager, testInvalidManagerPassword) != 2) {
                System.out.println("Incorrect Manager Info - Test Passed");
            } else {
                fail("Correct Manager Info - Test Failed");
            }
        } catch (Exception e) {
            fail("No username or password exists");
        }
    }
}