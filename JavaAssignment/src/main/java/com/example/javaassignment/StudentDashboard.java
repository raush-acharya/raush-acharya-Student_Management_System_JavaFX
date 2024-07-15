package com.example.javaassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    private String studentName;
    private String faculty;

    public void setUserData(String studentName, String faculty) {
        this.studentName = studentName;
        this.faculty = faculty;
    }

    @FXML
    private void handleLogoutButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Scene scene;
        scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleReportProblemButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProblemForm.fxml"));
        Stage stage = (Stage) reportProblem.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        ProblemForm problemForm = loader.getController();
        problemForm.setUserData(studentName, faculty);
        stage.setScene(scene);
        stage.show();
    }
}
