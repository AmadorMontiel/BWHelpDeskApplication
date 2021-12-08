package View_Controller;

import DataModel.Ticket;
import Implementations.TicketDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;

public class ReportsController {
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
    public TableColumn<Ticket, Integer> tPEEmployeeIDColumn;
    public TableColumn<Ticket, String> tPEEmployeeFirstNameColumn;
    public TableColumn<Ticket, String> tPEEmployeeLastNameColumn;
    public TableColumn<Ticket, String> tPELocationColumn;
    public TableColumn<Ticket, String> tPETypeColumn;
    public TableColumn<Ticket, Timestamp> tPECreateDateAndTimeColumn;
    public TableColumn<Ticket, String> tPEAssignedTechnicianColumn;

    public TextField employeeSearchTextBox;

    public void initialize() {
        tTBLTicketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        tTBLTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        tTBLLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        tTBLCreationDateAndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("create_date"));

        tPTTechnicianNameAndIDColumn.setCellValueFactory(new PropertyValueFactory<>("fullNameAndID"));
        tPTTotalTicketsColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        ticketsPerTechnicianTableView.setItems(TicketDaoImpl.getTotalTicketsPerTechnician());

        ticketTypeComboBox.setItems(types);
    }

    public void typeSelection() {
        ticketTypeByLocationTableView.setItems(TicketDaoImpl.getTicketsByType(ticketTypeComboBox.getSelectionModel().getSelectedItem()));
    }
}
