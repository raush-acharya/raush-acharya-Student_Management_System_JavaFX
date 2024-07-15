package com.example.javaassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class ProblemForm {

    @FXML
    private Button backButton;

    @FXML
    private Button submitButton;

    @FXML
    private TextField problemTitle;

    @FXML
    private TextField studentName;

    @FXML
    private TextField Faculty;

    @FXML
    private TextArea problemDescription;

    public void setUserData(String studentName, String faculty) {
        this.studentName.setText(studentName);
        this.Faculty.setText(faculty);
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StudentDashboard.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws IOException {
        if (problemTitle.getText().isEmpty() || studentName.getText().isEmpty() ||
                Faculty.getText().isEmpty() || problemDescription.getText().isEmpty()) {
            showAlert("Error", "Please fill all the fields");
        } else {
            try (FileWriter writer = new FileWriter("problem_form.csv", true)) {
                writer.append(problemTitle.getText()).append(',')
                        .append(studentName.getText()).append(',')
                        .append(Faculty.getText()).append(',')
                        .append(problemDescription.getText()).append('\n');
                showAlert("Success", "Form submitted successfully");
                clearForm();
            } catch (IOException e) {
                showAlert("Error", "Failed to save the form data");
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        problemTitle.clear();
        problemDescription.clear();
    }
}
