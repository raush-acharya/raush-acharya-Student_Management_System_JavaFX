package com.example.javaassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class CreateTestController {

    @FXML
    private TextField questionField;
    @FXML
    private TextField option1Field;
    @FXML
    private TextField option2Field;
    @FXML
    private TextField option3Field;
    @FXML
    private TextField option4Field;
    @FXML
    private TextField correctAnswerField;
    @FXML
    private Button nextQuestionButton;
    @FXML
    private Button backButton;

    private String testFileName;

    public void setTestFileName(String testFileName) {
        this.testFileName = testFileName;
    }

    @FXML
    private void handleNextQuestionButtonAction(ActionEvent event) throws IOException {
        if (questionField.getText().isEmpty() || option1Field.getText().isEmpty() || option2Field.getText().isEmpty() ||
                option3Field.getText().isEmpty() || option4Field.getText().isEmpty() || correctAnswerField.getText().isEmpty()) {
            showAlert("Error", "Please fill all the fields");
        } else {
            try (FileWriter writer = new FileWriter(testFileName, true)) {
                writer.append(questionField.getText()).append(',')
                        .append(option1Field.getText()).append(',')
                        .append(option2Field.getText()).append(',')
                        .append(option3Field.getText()).append(',')
                        .append(option4Field.getText()).append(',')
                        .append(correctAnswerField.getText()).append('\n');
                showAlert("Success", "Question saved successfully");
                clearForm();
            } catch (IOException e) {
                showAlert("Error", "Failed to save the question");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TeacherPage.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        questionField.clear();
        option1Field.clear();
        option2Field.clear();
        option3Field.clear();
        option4Field.clear();
        correctAnswerField.clear();
    }
}
