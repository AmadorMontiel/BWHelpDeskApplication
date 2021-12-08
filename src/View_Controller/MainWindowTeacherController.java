package View_Controller;

import DataModel.Appointment;
import DataModel.Employee;
import Implementations.AppointmentDaoImpl;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowTeacherController {

    public ComboBox<Appointment> appointmentsComboBox;
    public Employee signedInEmployee;
    public Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public void initialize() {

    }
    public void exitProgram(MouseEvent mouseEvent) {
        Platform.exit();
    }

    public void addAppointmentClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = getFxmlLoader("addappointment.fxml");
        AddAppointmentController aAController = loader.getController();
        aAController.receiveUser(signedInEmployee);
        loadNewScene(mouseEvent, loader);
    }

    public void updateAppointmentClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = getFxmlLoader("updateappointment.fxml");
        UpdateAppointmentController updateAppointmentController = loader.getController();
        if (appointmentsComboBox.getSelectionModel().getSelectedItem() != null) {
            updateAppointmentController.receiveAppointmentAndEmployee(appointmentsComboBox.getSelectionModel().getSelectedItem(), signedInEmployee);
            loadNewScene(mouseEvent, loader);
        } else {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No Appointment Selected");
            errorAlert.setContentText("Please select an appointment to update.");
            errorAlert.show();
        }

    }

    public void receiveUser(Employee employeeByName) {
        signedInEmployee = employeeByName;
        appointmentsComboBox.setItems(AppointmentDaoImpl.getAllAppointmentsByRequester(signedInEmployee.getId()));

    }

    private void loadNewScene(MouseEvent event, FXMLLoader loader) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    private FXMLLoader getFxmlLoader(String s) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(s));
        loader.load();
        return loader;
    }
}
