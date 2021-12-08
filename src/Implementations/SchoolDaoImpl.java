package Implementations;

import DataModel.School;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchoolDaoImpl {

    public static ObservableList<School> getAllSchools() {
        ObservableList<School> schools = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from schools";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int schoolID = rs.getInt("school_id");
                String schoolName = rs.getString("school_name");
                School s = new School(schoolID,schoolName);
                schools.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return schools;
    }

    public static School getSchoolByLocation(String location) {
        School school = null;

        try {
            String sql = "SELECT * FROM schools WHERE school_name = " + "\"" + location + "\"";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("school_id");
                String name = rs.getString("school_name");
                school = new School(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return school;
    }
}
