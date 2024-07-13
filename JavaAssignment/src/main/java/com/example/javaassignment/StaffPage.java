package com.example.javaassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static javafx.application.Application.launch;

public class StaffPage {
    @FXML
    private Button logoutButton;

    @FXML
    private Button gotoTeacher;

    @FXML
    private Button gotoAdmission;

    @FXML
    private Button gotoLibrarian;

    @FXML
    private void handleLogoutButtonAction(ActionEvent event) throws IOException{
        // Code to handle logout (e.g., redirect to login screen)
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Scene scene;
        scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml"))));
        stage.setScene(scene);
        stage.show();
    }


}
