package View_Controller;

import Implementations.EmployeeDaoImpl;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public TextField usernameTextField;
    public PasswordField passwordField;
    public Label signInLabel;
    public Button loginButton;
    public Button exitButton;
    public Label usernameLabel;
    public Label passwordLabel;

    private final Alert loginAlert = new Alert(Alert.AlertType.ERROR);

    public void login(MouseEvent event) throws IOException {

        int isValidLogin = EmployeeDaoImpl.isValidLogin(usernameTextField.getText(), passwordField.getText());

        if(isValidLogin == 1) {
            FXMLLoader loader = getFxmlLoader(isValidLogin);
            MainWindowTechnicianController MWTController = loader.getController();
            MWTController.receiveUser(EmployeeDaoImpl.getEmployeeByUsername(usernameTextField.getText()));
            loadNewScene(event, loader);
        } else if (isValidLogin == 2) {
            FXMLLoader loader = getFxmlLoader(isValidLogin);
            MainWindowManagerController MWMController = loader.getController();
            MWMController.receiveUser(EmployeeDaoImpl.getEmployeeByUsername(usernameTextField.getText()));
            loadNewScene(event, loader);
        } else if (isValidLogin == 3) {
            FXMLLoader loader = getFxmlLoader(isValidLogin);
            MainWindowTeacherController MWTController = loader.getController();
            MWTController.receiveUser(EmployeeDaoImpl.getEmployeeByUsername(usernameTextField.getText()));
            loadNewScene(event, loader);
        }
        else {
            loginAlert.setTitle("Error");
            loginAlert.setHeaderText("Unable to login");
            loginAlert.setContentText("Incorrect username or password");
            loginAlert.show();
        }
    }
    /**
     * Helper method for sending information from login screen to mainwindow.
     * @return returns the loader to be used for the next screen
     * @throws IOException Exception thrown when dealing with FXMLLoader
     */
    private FXMLLoader getFxmlLoader(int employeeType) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        if (employeeType == 1) {
            loader.setLocation(getClass().getResource("mainwindow_technician.fxml"));
            loader.load();
            return loader;
        } else if (employeeType == 2) {
            loader.setLocation(getClass().getResource("mainwindow_manager.fxml"));
            loader.load();
            return loader;
        } else if (employeeType == 3) {
            loader.setLocation(getClass().getResource("mainwindow_teacher.fxml"));
            loader.load();
            return loader;
        }
        return null;
    }

    /**
     * Helper method to load new scenes
     * @param event event that triggered the method
     * @param loader the loader of the scene being loaded
     */
    private void loadNewScene(MouseEvent event, FXMLLoader loader) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Closes the application
     */
    public void exitProgram() {
        Platform.exit();
    }
}
