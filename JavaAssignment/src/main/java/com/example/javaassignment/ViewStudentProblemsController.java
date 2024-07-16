package com.example.javaassignment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViewStudentProblemsController {

    @FXML
    private ListView<String> studentListView;

    @FXML
    private TextField problemTitleField;

    @FXML
    private TextArea problemDescriptionArea;

    @FXML
    private Button backButton;

    @FXML
    private Button nextProblemButton;

    private Map<String, List<String[]>> problemsMap;
    private Map<String, Integer> problemIndexMap;

    @FXML
    private void handleBackButtonAction() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TeacherPage.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleNextProblemAction() {
        String selectedStudent = studentListView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null && problemsMap.containsKey(selectedStudent)) {
            List<String[]> problems = problemsMap.get(selectedStudent);
            int currentIndex = problemIndexMap.getOrDefault(selectedStudent, 0);
            currentIndex = (currentIndex + 1) % problems.size();
            problemIndexMap.put(selectedStudent, currentIndex);
            showProblemDetails(selectedStudent, currentIndex);
        }
    }

    @FXML
    public void initialize() {
        problemsMap = new HashMap<>();
        problemIndexMap = new HashMap<>();
        loadProblems();
    }

    private void loadProblems() {
        try (BufferedReader reader = new BufferedReader(new FileReader("problem_form.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length == 4) {
                    String studentName = parts[1];
                    problemsMap.putIfAbsent(studentName, new ArrayList<>());
                    problemsMap.get(studentName).add(new String[]{parts[0], parts[3]});
                    if (!studentListView.getItems().contains(studentName)) {
                        studentListView.getItems().add(studentName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        studentListView.setOnMouseClicked(this::handleStudentSelection);
    }

    private void handleStudentSelection(MouseEvent event) {
        String selectedStudent = studentListView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            problemIndexMap.put(selectedStudent, 0);
            showProblemDetails(selectedStudent, 0);
            nextProblemButton.setVisible(problemsMap.get(selectedStudent).size() > 1);
        }
    }

    private void showProblemDetails(String studentName, int index) {
        List<String[]> problems = problemsMap.get(studentName);
        if (problems != null && !problems.isEmpty()) {
            String[] details = problems.get(index);
            problemTitleField.setText(details[0]);
            problemDescriptionArea.setText(details[1]);
        }
    }
}
