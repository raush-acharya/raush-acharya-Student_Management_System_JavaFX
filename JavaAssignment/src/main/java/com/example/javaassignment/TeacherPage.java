package com.example.javaassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TeacherPage {

    @FXML
    private Button backButton;

    @FXML
    private Button viewProblems;

    @FXML
    private void handleViewStudentProblemsButtonAction() throws IOException {
        Stage stage = (Stage) viewProblems.getScene().getWindow();
        Scene scene;
        scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ViewStudentProblems.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StaffPage.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    // Other methods and initializations
}
