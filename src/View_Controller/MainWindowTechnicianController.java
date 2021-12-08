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
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowTechnicianController {

    public ComboBox<Ticket> ticketsComboBox;
    public Employee signedInEmployee;
    public Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public void initialize() {
        ticketsComboBox.setItems(TicketDaoImpl.getAllTickets());
    }
    public void receiveUser(Employee employeeByName) {
        signedInEmployee = employeeByName;
    }

    public void exitProgram() {
        Platform.exit();
    }

    public void reportsClicked(MouseEvent mouseEvent) {
    }

    public void addTicketClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = getFxmlLoader("addticket.fxml");
        AddTicketController aAController = loader.getController();
        aAController.receiveUser(signedInEmployee);
        loadNewScene(mouseEvent, loader);
    }

    public void updateTicketClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = getFxmlLoader("updateticket.fxml");
        UpdateTicketController updateTicketController = loader.getController();
        if (ticketsComboBox.getSelectionModel().getSelectedItem() != null) {
            updateTicketController.receiveTicketAndEmployee(ticketsComboBox.getSelectionModel().getSelectedItem(), signedInEmployee);
            loadNewScene(mouseEvent, loader);
        } else {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No Ticket Selected");
            errorAlert.setContentText("Please select a ticket to update.");
            errorAlert.show();
        }
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
