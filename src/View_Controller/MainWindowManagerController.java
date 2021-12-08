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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowManagerController {

    public ComboBox<Appointment> appointmentsComboBox;
    public Employee signedInEmployee;
    public Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    public Alert deletionConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    public Alert deletionConfirmedAlert = new Alert(Alert.AlertType.INFORMATION);


    public void initialize() {
        appointmentsComboBox.setItems(AppointmentDaoImpl.getAllAppointments());
    }

    public void exitProgram() {
        Platform.exit();
    }

    public void reportsClicked(MouseEvent mouseEvent) {
    }

    public void deleteAppointment() {
        {
            if(appointmentsComboBox.getSelectionModel().getSelectedItem() != null) {
                deletionConfirmationAlert.setTitle("Confirmation");
                deletionConfirmationAlert.setHeaderText("Confirm Appointment Deletion");
                deletionConfirmationAlert.setContentText("Are you sure you want to delete Appointment: "
                        + appointmentsComboBox.getSelectionModel().getSelectedItem().getType());
                deletionConfirmationAlert.showAndWait();

                if (deletionConfirmationAlert.getResult() == ButtonType.OK) {
                    try {
                        AppointmentDaoImpl.deleteAppointment(appointmentsComboBox.getSelectionModel().getSelectedItem().getAppointmentID());
                        deletionConfirmedAlert.setTitle("Deletion Confirmed");
                        deletionConfirmedAlert.setHeaderText("Appointment Deleted");
                        deletionConfirmedAlert.setContentText(appointmentsComboBox.getSelectionModel().getSelectedItem().toString() + " has been deleted!");
                        deletionConfirmedAlert.show();
                        appointmentsComboBox.getItems().remove(appointmentsComboBox.getSelectionModel().getSelectedItem());

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("No Appointment Selected");
                errorAlert.setContentText("Please select an appointment to delete");
                errorAlert.show();
            }
        }
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

    public void addAppointmentClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = getFxmlLoader("addappointment.fxml");
        AddAppointmentController aAController = loader.getController();
        aAController.receiveUser(signedInEmployee);
        loadNewScene(mouseEvent, loader);
    }

    public void receiveUser(Employee employeeByName) {
        signedInEmployee = employeeByName;
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
