package View_Controller;

import DataModel.Employee;
import DataModel.Technician;
import DataModel.Ticket;
import Implementations.TeacherDAOImpl;
import Implementations.TechnicianDaoImpl;
import Implementations.TicketDaoImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportsTechnicianController {

    private Employee signedInEmployee;

    public TableView<Ticket> assignedTicketsByTechnicianTable;
    public TableColumn<Ticket, Integer> attTechnicianIDColumn;
    public TableColumn<Ticket, String> attTypeColumn;
    public TableColumn<Ticket, String> attPriorityColumn;
    public TableColumn<Ticket, String> attLocationColumn;
    public TableColumn<Ticket, String> attCreateDateColumn;
    public TableColumn<Ticket, String> attRequesterIDAndNameColumn;

    public ComboBox<Technician> technicianComboBox;

    public TableView<Ticket> ticketsWithNoTechnicianTable;
    public TableColumn<Ticket, Integer> twntTicketIDColumn;
    public TableColumn<Ticket, String> twntTypeColumn;
    public TableColumn<Ticket, Integer> twntPriorityColumn;
    public TableColumn<Ticket, String> twntLocationColumn;
    public TableColumn<Ticket, String> twntCreateDateColumn;
    public TableColumn<Ticket, String> twntRequesterIDAndNameColumn;

    public void initialize() {
        attTechnicianIDColumn.setCellValueFactory(new PropertyValueFactory<>("technicianID"));
        attTypeColumn.setCellValueFactory(new PropertyValueFactory<>(("type")));
        attPriorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        attLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        attCreateDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        attRequesterIDAndNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullNameAndID"));

        twntTicketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        twntTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        twntPriorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        twntLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        twntCreateDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        twntRequesterIDAndNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullNameAndID"));
        ticketsWithNoTechnicianTable.setItems(TicketDaoImpl.getTicketsWithNoTechnician());

        technicianComboBox.setItems(TechnicianDaoImpl.getAllTechnicians());


    }
    public void close(MouseEvent event) throws IOException {
        TeacherDAOImpl.isEmpATeacherByID(signedInEmployee.getId());
        FXMLLoader loader = getFxmlLoader();
        MainWindowTechnicianController mainWindowTechnicianController = loader.getController();
        mainWindowTechnicianController.receiveUser(signedInEmployee);
        loadNewScene(event, loader);
    }

    private FXMLLoader getFxmlLoader() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mainwindow_technician.fxml"));
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

    public void technicianSelection() {
        assignedTicketsByTechnicianTable.setItems(TicketDaoImpl.getAllTicketsByTechnician(technicianComboBox.getSelectionModel().getSelectedItem().getId()));
    }

    public void receiveTechnician(Employee signedInEmployee) {
        this.signedInEmployee = signedInEmployee;
    }
}
