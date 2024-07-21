package com.example.javaassignment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TakeTestQuestionsController {

    @FXML
    private Label questionLabel;
    @FXML
    private RadioButton option1Radio;
    @FXML
    private RadioButton option2Radio;
    @FXML
    private RadioButton option3Radio;
    @FXML
    private RadioButton option4Radio;

    @FXML
    private Button backButton;
    private ToggleGroup optionsGroup;

    private String testFileName;
    private int currentQuestionIndex = 0;
    private String[][] questions;
    private List<String> userAnswers = new ArrayList<>();
    private String studentName;

    @FXML
    public void initialize() {
        optionsGroup = new ToggleGroup();
        option1Radio.setToggleGroup(optionsGroup);
        option2Radio.setToggleGroup(optionsGroup);
        option3Radio.setToggleGroup(optionsGroup);
        option4Radio.setToggleGroup(optionsGroup);

        // Fetch the student name from StudentData
        studentName = StudentData.getInstance().getStudentName();
    }

    public void setTestFileName(String testFileName) {
        this.testFileName = testFileName;
        loadQuestions();
        displayQuestion(currentQuestionIndex);
    }

    private void loadQuestions() {
        try (BufferedReader reader = new BufferedReader(new FileReader(testFileName))) {
            String line;
            questions = reader.lines()
                    .map(l -> l.split(","))
                    .toArray(String[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayQuestion(int index) {
        if (index < questions.length) {
            questionLabel.setText(questions[index][0]);
            option1Radio.setText(questions[index][1]);
            option2Radio.setText(questions[index][2]);
            option3Radio.setText(questions[index][3]);
            option4Radio.setText(questions[index][4]);
        } else {
            saveResults();
            showCompletionMessage();
        }
    }

    @FXML
    private void handleBackButtonAction() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TakeTest.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleNextButtonAction() {
        if (optionsGroup.getSelectedToggle() == null) {
            showAlert("Error", "Please select an answer before proceeding.");
            return;
        }

        RadioButton selectedRadio = (RadioButton) optionsGroup.getSelectedToggle();
        userAnswers.add(selectedRadio.getText());

        if (currentQuestionIndex < questions.length - 1) {
            currentQuestionIndex++;
            displayQuestion(currentQuestionIndex);
            optionsGroup.selectToggle(null); // Clear previous selection
        } else {
            saveResults();
            showCompletionMessage();
        }
    }

    private void saveResults() {
        int totalMarks = questions.length;
        int achievedMarks = 0;

        for (int i = 0; i < questions.length; i++) {
            if (userAnswers.size() > i && userAnswers.get(i).equals(questions[i][5])) {
                achievedMarks++;
            }
        }

        String grade = calculateGrade(achievedMarks, totalMarks);
        try (FileWriter writer = new FileWriter("Results.csv", true)) {
            writer.append(studentName).append(',')
                    .append(testFileName).append(',')
                    .append(String.valueOf(totalMarks)).append(',')
                    .append(String.valueOf(achievedMarks)).append(',')
                    .append(grade).append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String calculateGrade(int achievedMarks, int totalMarks) {
        double percentage = (double) achievedMarks / totalMarks * 100;
        if (percentage > 80) return "A";
        if (percentage > 70) return "B";
        if (percentage > 60) return "C";
        if (percentage > 50) return "D";
        return "F";
    }

    private void showCompletionMessage() {
        StringBuilder summary = new StringBuilder("Test Complete! Here are your answers:\n");
        for (int i = 0; i < userAnswers.size(); i++) {
            summary.append("Question ").append(i + 1).append(": ").append(userAnswers.get(i)).append("\n");
        }
        showAlert("Test Complete", summary.toString());
        // Optionally, transition to another scene or reset the test.
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
