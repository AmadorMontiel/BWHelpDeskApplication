package Main;

import Utility.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/login.fxml")));
            primaryStage.setTitle("Broadwater Help Desk Application");
            primaryStage.setScene(new Scene(root, 450, 300));
            primaryStage.show();
        }

        public static void main(String[] args) {
            DBConnection.startConnection();
            launch(args);
            DBConnection.closeConnection();
        }
}
