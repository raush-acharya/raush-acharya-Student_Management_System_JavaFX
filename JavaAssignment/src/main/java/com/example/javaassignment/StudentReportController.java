package com.example.javaassignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class StudentReportController {

    @FXML
    private Button backButton;

    @FXML
    private TableView<StudentResult> resultsTable;

    @FXML
    private TableColumn<StudentResult, Integer> snColumn;

    @FXML
    private TableColumn<StudentResult, String> studentNameColumn;

    @FXML
    private TableColumn<StudentResult, String> questionColumn;

    @FXML
    private TableColumn<StudentResult, String> marksColumn;

    @FXML
    private TableColumn<StudentResult, String> gradeColumn;

    private final ObservableList<StudentResult> results = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        snColumn.setCellValueFactory(new PropertyValueFactory<>("sn"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        marksColumn.setCellValueFactory(new PropertyValueFactory<>("marks"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        loadResultsFromFile("Results.csv");
        resultsTable.setItems(results);
    }

    private void loadResultsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int sn = 1;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    String studentName = data[0];
                    String question = data[1].replace(".csv", ""); // Remove ".csv"
                    String totalMarks = data[2];
                    String achievedMarks = data[3];
                    String grade = data[4];
                    String marks = achievedMarks + "/" + totalMarks;
                    results.add(new StudentResult(sn++, studentName, question, marks, grade));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleBackButtonAction() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TeacherPage.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    public static class StudentResult {
        private final Integer sn;
        private final String studentName;
        private final String question;
        private final String marks;
        private final String grade;

        public StudentResult(Integer sn, String studentName, String question, String marks, String grade) {
            this.sn = sn;
            this.studentName = studentName;
            this.question = question;
            this.marks = marks;
            this.grade = grade;
        }

        public Integer getSn() {
            return sn;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getQuestion() {
            return question;
        }

        public String getMarks() {
            return marks;
        }

        public String getGrade() {
            return grade;
        }
    }
}
