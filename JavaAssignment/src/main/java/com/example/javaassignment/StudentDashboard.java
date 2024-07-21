package com.example.javaassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StudentDashboard {

    @FXML
    private Button logoutButton;

    @FXML
    private Button takeTest;

    @FXML
    private Button viewResults;

    @FXML
    private Button reportProblem;

    @FXML
    private Button viewProfile;

    @FXML
    private Label nameLabel;

    private StudentData studentData = StudentData.getInstance();

    public void setUserData(String studentName, String faculty, String phone, String email) {
        studentData.setStudentName(studentName);
        studentData.setFaculty(faculty);
        studentData.setPhone(phone);
        studentData.setEmail(email);

        nameLabel.setText(studentName);
    }

    @FXML
    private void initialize() {
        nameLabel.setText(studentData.getStudentName());
    }

    @FXML
    private void handleLogoutButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleTakeTestButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) takeTest.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TakeTest.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleReportProblemButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProblemForm.fxml"));
        Stage stage = (Stage) reportProblem.getScene().getWindow();
        Scene scene = new Scene(loader.load());

        // Get the controller of ProblemForm and set the user data
        ProblemForm problemForm = loader.getController();
        problemForm.setUserData(studentData.getStudentName(), studentData.getFaculty());

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleViewProfileButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileView.fxml"));
        Stage stage = (Stage) viewProfile.getScene().getWindow();
        Scene scene = new Scene(loader.load());

        // Get the controller of ProfileView and set the user data
        ProfileViewController profileViewController = loader.getController();
        profileViewController.setUserData(studentData.getStudentName(), studentData.getFaculty(), studentData.getPhone(), studentData.getEmail());

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleViewResultsButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) viewResults.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ViewResults.fxml"))));
        stage.setScene(scene);
        stage.show();
    }
}
