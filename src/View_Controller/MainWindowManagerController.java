package View_Controller;

import DataModel.Ticket;
import DataModel.Employee;
import Implementations.TicketDaoImpl;
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

    public ComboBox<Ticket> ticketComboBox;
    public Employee signedInEmployee;
    public Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    public Alert deletionConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    public Alert deletionConfirmedAlert = new Alert(Alert.AlertType.INFORMATION);


    public void initialize() {
        ticketComboBox.setItems(TicketDaoImpl.getAllTickets());
    }

    public void exitProgram() {
        Platform.exit();
    }

    public void reportsClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = getFxmlLoader("reports_manager.fxml");
        ReportsManagerController reportsManagerController = loader.getController();
        reportsManagerController.receiveManager(signedInEmployee);
        loadNewScene(mouseEvent, loader);
    }

    public void deleteTicket() {
        {
            if(ticketComboBox.getSelectionModel().getSelectedItem() != null) {
                deletionConfirmationAlert.setTitle("Confirmation");
                deletionConfirmationAlert.setHeaderText("Confirm Ticket Deletion");
                deletionConfirmationAlert.setContentText("Are you sure you want to delete Ticket: "
                        + ticketComboBox.getSelectionModel().getSelectedItem().getType());
                deletionConfirmationAlert.showAndWait();

                if (deletionConfirmationAlert.getResult() == ButtonType.OK) {
                    try {
                        TicketDaoImpl.deleteTicket(ticketComboBox.getSelectionModel().getSelectedItem().getTicketID());
                        deletionConfirmedAlert.setTitle("Deletion Confirmed");
                        deletionConfirmedAlert.setHeaderText("Ticket Deleted");
                        deletionConfirmedAlert.setContentText(ticketComboBox.getSelectionModel().getSelectedItem().toString() + " has been deleted!");
                        deletionConfirmedAlert.show();
                        ticketComboBox.getItems().remove(ticketComboBox.getSelectionModel().getSelectedItem());

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("No Ticket Selected");
                errorAlert.setContentText("Please select a Ticket to delete");
                errorAlert.show();
            }
        }
    }

    public void updateTicketClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = getFxmlLoader("updateticket.fxml");
        UpdateTicketController updateTicketController = loader.getController();
        if (ticketComboBox.getSelectionModel().getSelectedItem() != null) {
            updateTicketController.receiveTicketAndEmployee(ticketComboBox.getSelectionModel().getSelectedItem(), signedInEmployee);
            loadNewScene(mouseEvent, loader);
        } else {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No Ticket Selected");
            errorAlert.setContentText("Please select an Ticket to update.");
            errorAlert.show();
        }
    }

    public void addTicketClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = getFxmlLoader("addticket.fxml");
        AddTicketController aAController = loader.getController();
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
