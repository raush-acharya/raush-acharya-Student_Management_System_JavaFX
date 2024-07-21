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
    private Button createTestButton;

    @FXML
    private Button viewStudentResultsButton;

    @FXML
    private Button viewStudentsButton;

    @FXML
    private void handleViewStudentsButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) viewStudentsButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ViewStudents.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleViewStudentProblemsButtonAction() throws IOException {
        Stage stage = (Stage) viewProblems.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ViewStudentProblems.fxml"))));
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

    @FXML
    private void handleCreateTestButtonAction(ActionEvent event) throws IOException {
        int currentTestNumber = TestNumberManager.getNextTestNumber();
        String testFileName = "Question" + currentTestNumber + ".csv";

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("CreateTest.fxml")));
        Stage stage = (Stage) createTestButton.getScene().getWindow();
        Scene scene = new Scene(loader.load());

        CreateTestController controller = loader.getController();
        controller.setTestFileName(testFileName);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleViewStudentResultsButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) viewStudentResultsButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StudentReport.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    // Other methods and initializations
}
