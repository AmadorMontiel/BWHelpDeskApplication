package View_Controller;

import DataModel.*;
import Implementations.AppointmentDaoImpl;
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

public class AddAppointmentController {

    //TODO change requester combobox to only allow selection of employees from specific school
    public ObservableList<String> types = FXCollections.observableArrayList("Systems", "Audio/Video", "Applications", "Network");
    public ObservableList<Integer> priorities = FXCollections.observableArrayList(1,2,3,4);

    public TextField appointmentIDTextField;


    public TextArea descriptionTextArea;

    public ComboBox<String> typeComboBox = new ComboBox<>(types);
    public ComboBox<Integer> priorityComboBox = new ComboBox<>(priorities);
    public ComboBox<School> locationComboBox;
    public ComboBox<Technician> assignedTechnicianComboBox;
    public ComboBox<Employee> requesterComboBox;

    public Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public Teacher signedInTeacher;
    public Technician signedInTechnician;
    public Manager signedInManager;
    public Employee signedInEmployee;


    public void initialize() {
        appointmentIDTextField.setText("Auto-Generated");
        typeComboBox.setItems(types);
        priorityComboBox.setItems(priorities);
        locationComboBox.setItems(SchoolDaoImpl.getAllSchools());
        requesterComboBox.setItems(EmployeeDaoImpl.getAllEmployees());

    }

    public void saveNewAppointment(MouseEvent mouseEvent) throws IOException {
        if (typeComboBox.getSelectionModel().getSelectedItem() == null || priorityComboBox.getSelectionModel().getSelectedItem() == null ||
                locationComboBox.getSelectionModel().getSelectedItem() == null ||
                descriptionTextArea.getText().isEmpty()) {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Invalid Input");
            errorAlert.setContentText("All information must be filled out.");
            errorAlert.show();
        } else {
            if (EmployeeDaoImpl.isEmpATeacherByID(signedInEmployee.getId())) {
                AppointmentDaoImpl.addAppointmentTeacher((Teacher) signedInEmployee, typeComboBox.getSelectionModel().getSelectedItem(), locationComboBox.getSelectionModel().getSelectedItem().getSchoolName(),
                        descriptionTextArea.getText(), priorityComboBox.getSelectionModel().getSelectedItem(), signedInTeacher.getId());
                close(mouseEvent);
            } else if (EmployeeDaoImpl.isEmpATechnicianByID(signedInEmployee.getId()) || EmployeeDaoImpl.isEmpAManagerByID(signedInEmployee.getId())) {
                AppointmentDaoImpl.addAppointmentTechnician((Technician) signedInEmployee, typeComboBox.getSelectionModel().getSelectedItem(), locationComboBox.getSelectionModel().getSelectedItem().getSchoolName(),
                        descriptionTextArea.getText(), priorityComboBox.getSelectionModel().getSelectedItem(), requesterComboBox.getSelectionModel().getSelectedItem().getId(), signedInTechnician.getId());
                close(mouseEvent);
            }

        }
    }

    public void close(MouseEvent event) throws IOException {

        if (EmployeeDaoImpl.isEmpATeacherByID(signedInEmployee.getId())) {
            FXMLLoader loader = getFxmlLoader();
            MainWindowTeacherController mainWindowTeacherController = loader.getController();
            mainWindowTeacherController.receiveUser(signedInEmployee);
            loadNewScene(event, loader);
        } else if (EmployeeDaoImpl.isEmpATechnicianByID(signedInEmployee.getId())) {
            FXMLLoader loader = getFxmlLoader();
            MainWindowTechnicianController mainWindowTechnicianController = loader.getController();
            mainWindowTechnicianController.receiveUser(signedInEmployee);
            loadNewScene(event, loader);
        } else if (EmployeeDaoImpl.isEmpAManagerByID(signedInEmployee.getId())) {
            FXMLLoader loader = getFxmlLoader();
            MainWindowManagerController mainWindowManagerController = loader.getController();
            mainWindowManagerController.receiveUser(signedInEmployee);
            loadNewScene(event, loader);
        }
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
        } else return null;
    }

    private void loadNewScene(MouseEvent event, FXMLLoader loader) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void receiveUser(Employee employeeSigningIn) {
        signedInEmployee = employeeSigningIn;
        if(EmployeeDaoImpl.isEmpATeacherByID(signedInEmployee.getId())) {
            assignedTechnicianComboBox.setDisable(true);
            signedInTeacher = new Teacher(employeeSigningIn.getId(), employeeSigningIn.getFirstName(), employeeSigningIn.getLastName());
            requesterComboBox.setValue(EmployeeDaoImpl.getEmployeeByID(signedInEmployee.getId()));
            requesterComboBox.setDisable(true);
            requesterComboBox.setOpacity(1);
        } else if (EmployeeDaoImpl.isEmpATechnicianByID(signedInEmployee.getId())) {
            signedInTechnician = new Technician((employeeSigningIn.getId()), employeeSigningIn.getFirstName(), employeeSigningIn.getLastName());
            assignedTechnicianComboBox.setItems(TechnicianDaoImpl.getAllTechnicians());
        } else if (EmployeeDaoImpl.isEmpAManagerByID(signedInEmployee.getId())) {
            signedInManager = new Manager(employeeSigningIn.getId(), employeeSigningIn.getFirstName(), employeeSigningIn.getLastName());
            assignedTechnicianComboBox.setItems(TechnicianDaoImpl.getAllTechnicians());
        }

    }
}
