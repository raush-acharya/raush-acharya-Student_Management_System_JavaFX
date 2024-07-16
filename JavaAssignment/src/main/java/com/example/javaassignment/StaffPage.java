package com.example.javaassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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
    private void handleLogoutButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleGotoTeacherAction(ActionEvent event) throws IOException {
        navigateToPage("TeacherPage.fxml");
    }

    @FXML
    private void handleGotoAdmissionAction(ActionEvent event) throws IOException {
        navigateToPage("AdmissionOfficerPage.fxml");
    }

    @FXML
    private void handleGotoLibrarianAction(ActionEvent event) throws IOException {
        navigateToPage("LibraryPage.fxml");
    }

    private void navigateToPage(String fxmlFile) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile))));
        stage.setScene(scene);
        stage.show();
    }
}
