package View_Controller;

import DataModel.*;
import Implementations.TicketDaoImpl;
import Implementations.EmployeeDaoImpl;
import Implementations.SchoolDaoImpl;
import Implementations.TechnicianDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateTicketController {

    public ObservableList<String> types = FXCollections.observableArrayList("Systems", "Audio/Video", "Applications", "Network");
    public ObservableList<Integer> priorities = FXCollections.observableArrayList(1,2,3,4);

    public TextField ticketIDTextField;
    public TextArea descriptionTextBox;

    public ComboBox<String> typeComboBox = new ComboBox<>(types);
    public ComboBox<Integer> priorityComboBox = new ComboBox<>(priorities);
    public ComboBox<School> locationComboBox;
    public ComboBox<Technician> technicianComboBox;
    public ComboBox<Employee> requesterComboBox;

    public School selectedSchool;
    public Technician selectedTechnician;

    public Teacher signedInTeacher;
    public Technician signedInTechnician;
    public Manager signedInManager;
    public Employee signedInEmployee;
    public Employee requester;

    public Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public void initialize() {
        technicianComboBox.setItems(TechnicianDaoImpl.getAllTechnicians());
        locationComboBox.setItems(SchoolDaoImpl.getAllSchools());
        requesterComboBox.setItems(EmployeeDaoImpl.getAllEmployees());
        typeComboBox.setItems(types);
        priorityComboBox.setItems(priorities);
    }


    public void saveUpdatedTicket(MouseEvent mouseEvent) throws IOException {
        if (typeComboBox.getSelectionModel().getSelectedItem() == null || priorityComboBox.getSelectionModel().getSelectedItem() == null ||
                locationComboBox.getSelectionModel().getSelectedItem() == null ||
                descriptionTextBox.getText().isEmpty()) {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Invalid Input");
            errorAlert.setContentText("All information must be filled out.");
            errorAlert.show();
        } else {
            if(EmployeeDaoImpl.isEmpATeacherByID(signedInEmployee.getId())) {
                TicketDaoImpl.updateTicketTeacher(signedInTeacher, Integer.parseInt(ticketIDTextField.getText()), typeComboBox.getSelectionModel().getSelectedItem(),
                        priorityComboBox.getSelectionModel().getSelectedItem(), locationComboBox.getSelectionModel().getSelectedItem().getSchoolName(),
                        requesterComboBox.getSelectionModel().getSelectedItem().getId(), technicianComboBox.getSelectionModel().getSelectedItem().getId(), descriptionTextBox.getText());
                close(mouseEvent);
            } else if (EmployeeDaoImpl.isEmpATechnicianByID(signedInEmployee.getId()) || EmployeeDaoImpl.isEmpAManagerByID(signedInEmployee.getId())) {
                TicketDaoImpl.updateTicketTechnicianOrManager(signedInEmployee, Integer.parseInt(ticketIDTextField.getText()), typeComboBox.getSelectionModel().getSelectedItem(),
                        priorityComboBox.getSelectionModel().getSelectedItem(), locationComboBox.getSelectionModel().getSelectedItem().getSchoolName(),
                        requesterComboBox.getSelectionModel().getSelectedItem().getId(), technicianComboBox.getSelectionModel().getSelectedItem().getId(), descriptionTextBox.getText());
                close(mouseEvent);
            }
        }
    }

    public void close(MouseEvent mouseEvent) throws IOException {

        if (EmployeeDaoImpl.isEmpATeacherByID(signedInEmployee.getId())) {
            FXMLLoader loader = getFxmlLoader();
            MainWindowTeacherController mainWindowTeacherController = loader.getController();
            mainWindowTeacherController.receiveUser(signedInEmployee);
            loadNewScene(mouseEvent, loader);
        } else if (EmployeeDaoImpl.isEmpATechnicianByID(signedInEmployee.getId())) {
            FXMLLoader loader = getFxmlLoader();
            MainWindowTechnicianController mainWindowTechnicianController = loader.getController();
            mainWindowTechnicianController.receiveUser(signedInEmployee);
            loadNewScene(mouseEvent, loader);
        } else if (EmployeeDaoImpl.isEmpAManagerByID(signedInEmployee.getId())) {
            FXMLLoader loader = getFxmlLoader();
            MainWindowManagerController mainWindowManagerController = loader.getController();
            mainWindowManagerController.receiveUser(signedInEmployee);
            loadNewScene(mouseEvent, loader);
        }
    }

    public void receiveTicketAndEmployee(Ticket selectedTicket, Employee signedInEmployee) {
        this.signedInEmployee = signedInEmployee;
        ticketIDTextField.setText(String.valueOf(selectedTicket.getTicketID()));
        descriptionTextBox.setText(selectedTicket.getDescription());
        typeComboBox.setValue(selectedTicket.getType());
        priorityComboBox.setValue(selectedTicket.getPriority());

        getComboBoxItems(selectedTicket);
        locationComboBox.setValue(selectedSchool);
        if (selectedTechnician != null) {
            technicianComboBox.setValue(selectedTechnician);
        } else {
            technicianComboBox.setValue(null);
        }

        if (EmployeeDaoImpl.isEmpATeacherByID(signedInEmployee.getId())) {
            signedInTeacher = (Teacher) signedInEmployee;
            technicianComboBox.setDisable(true);
            technicianComboBox.setOpacity(1);
            requesterComboBox.setValue(EmployeeDaoImpl.getEmployeeByID(signedInEmployee.getId()));
            requesterComboBox.setDisable(true);
            requesterComboBox.setOpacity(1);

        } else if (EmployeeDaoImpl.isEmpATechnicianByID(signedInEmployee.getId())) {
            signedInTechnician = (Technician) signedInEmployee;
            requesterComboBox.setValue(EmployeeDaoImpl.getEmployeeByID(selectedTicket.getRequesterID()));
        } else if (EmployeeDaoImpl.isEmpAManagerByID(signedInEmployee.getId())) {
            signedInManager = (Manager) signedInEmployee;
            requesterComboBox.setValue(EmployeeDaoImpl.getEmployeeByID(selectedTicket.getRequesterID()));
        }
    }

    private void getComboBoxItems(Ticket selectedTicket) {
        selectedSchool = SchoolDaoImpl.getSchoolByLocation(selectedTicket.getLocation());
        selectedTechnician = TechnicianDaoImpl.getTechnicianByID(selectedTicket.getTechnicianID());
        requester = EmployeeDaoImpl.getEmployeeByID(selectedTicket.getRequesterID());

    }

    private FXMLLoader getFxmlLoader() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        if (EmployeeDaoImpl.isEmpATeacherByID(signedInEmployee.getId())) {
            loader.setLocation(getClass().getResource("mainwindow_teacher.fxml"));
            loader.load();
            return loader;
        } else if (EmployeeDaoImpl.isEmpATechnicianByID(signedInEmployee.getId())) {
            loader.setLocation(getClass().getResource("mainwindow_technician.fxml"));
            loader.load();
            return loader;
        } else if (EmployeeDaoImpl.isEmpAManagerByID(signedInEmployee.getId())) {
            loader.setLocation(getClass().getResource("mainwindow_manager.fxml"));
            loader.load();
            return loader;
        } else
            return null;
    }

    private void loadNewScene(MouseEvent event, FXMLLoader loader) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
