package View_Controller;

import DataModel.Employee;
import DataModel.Ticket;
import Implementations.EmployeeDaoImpl;
import Implementations.TicketDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportsManagerController {

    private Employee signedInEmployee;

    public ObservableList<String> types = FXCollections.observableArrayList("Systems", "Audio/Video", "Applications", "Network");

    public TableView<Ticket> ticketTypeByLocationTableView;
    public TableColumn<Ticket, Integer> tTBLTicketIDColumn;
    public TableColumn<Ticket, String> tTBLTypeColumn;
    public TableColumn<Ticket, String> tTBLLocationColumn;
    public TableColumn<Ticket, String> tTBLCreationDateAndTimeColumn;

    public ComboBox<String> ticketTypeComboBox = new ComboBox<>(types);

    public TableView<Ticket> ticketsPerTechnicianTableView;
    public TableColumn<Ticket, String> tPTTechnicianNameAndIDColumn;
    public TableColumn<Ticket, Integer> tPTTotalTicketsColumn;

    public TableView<Ticket> ticketsPerEmployeeTableView;
    public TableColumn<Ticket, Integer> tPEEmployeeIDAndNameColumn;
    public TableColumn<Ticket, String> tPELocationColumn;
    public TableColumn<Ticket, String> tPETypeColumn;
    public TableColumn<Ticket, String> tPECreateDateAndTimeColumn;
    public TableColumn<Ticket, String> tPEAssignedTechnicianColumn;

    public TextField employeeSearchTextBox;

    public Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public void initialize() {
        tTBLTicketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        tTBLTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        tTBLLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        tTBLCreationDateAndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));

        tPTTechnicianNameAndIDColumn.setCellValueFactory(new PropertyValueFactory<>("fullNameAndID"));
        tPTTotalTicketsColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        ticketsPerTechnicianTableView.setItems(TicketDaoImpl.getTotalTicketsPerTechnician());

        tPEEmployeeIDAndNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullNameAndID"));
        tPELocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        tPETypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        tPECreateDateAndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        tPEAssignedTechnicianColumn.setCellValueFactory(new PropertyValueFactory<>("technicianID"));
        ticketsPerEmployeeTableView.setItems(TicketDaoImpl.getTotalTickets());

        ticketTypeComboBox.setItems(types);
    }

    public void typeSelection() {
        ticketTypeByLocationTableView.setItems(TicketDaoImpl.getTicketsByType(ticketTypeComboBox.getSelectionModel().getSelectedItem()));
    }

    public void searchTickets() {
        String searchedTicket = employeeSearchTextBox.getText();
        ObservableList<Ticket> searchedTicketsList = TicketDaoImpl.lookupTicket(searchedTicket);

        if(searchedTicketsList.size() == 0) {
            try {
                int ticketID = Integer.parseInt(searchedTicket);
                Ticket sTicket = TicketDaoImpl.lookupTicket(ticketID);
                if(sTicket != null) {
                    searchedTicketsList.add(sTicket);
                } else {
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("No Ticket Found");
                    errorAlert.setContentText("Please try another search");
                    errorAlert.show();
                }
            } catch (NumberFormatException ignored) {}
        }
        if(searchedTicketsList.isEmpty()) {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No Tickets Found");
            errorAlert.setContentText("Please try another search");
            errorAlert.show();
        } else {
            ticketsPerEmployeeTableView.setItems(searchedTicketsList);
        }
    }

    public void close(MouseEvent event) throws IOException {
        EmployeeDaoImpl.isEmpAManagerByID(signedInEmployee.getId());
        FXMLLoader loader = getFxmlLoader();
        MainWindowManagerController mainWindowManagerController = loader.getController();
        mainWindowManagerController.receiveUser(signedInEmployee);
        loadNewScene(event, loader);
    }

    private FXMLLoader getFxmlLoader() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mainwindow_manager.fxml"));
        loader.load();
        return loader;
    }

    private void loadNewScene(MouseEvent event, FXMLLoader loader) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void receiveManager(Employee signedInEmployee) {
        this.signedInEmployee = signedInEmployee;
    }
}
