package com.example.javaassignment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewResultsController {

    @FXML
    private Button backButton;
    @FXML
    private TableView<Result> resultsTable;

    @FXML
    private TableColumn<Result, String> snColumn;

    @FXML
    private TableColumn<Result, String> testFileNameColumn;

    @FXML
    private TableColumn<Result, Integer> totalMarksColumn;

    @FXML
    private TableColumn<Result, Integer> achievedMarksColumn;

    @FXML
    private TableColumn<Result, String> gradeColumn;

    @FXML
    private Label studentNameLabel;

    @FXML
    private Label resultsForLabel;

    private StudentData studentData = StudentData.getInstance();

    @FXML
    public void initialize() {
        snColumn.setCellValueFactory(new PropertyValueFactory<>("sn"));
        testFileNameColumn.setCellValueFactory(new PropertyValueFactory<>("testFileName"));
        totalMarksColumn.setCellValueFactory(new PropertyValueFactory<>("totalMarks"));
        achievedMarksColumn.setCellValueFactory(new PropertyValueFactory<>("achievedMarks"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        resultsForLabel.setText("Results for " + studentData.getStudentName());
        loadResults();
    }

    private void loadResults() {
        List<Result> results = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("Results.csv"))) {
            String line;
            int sn = 1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[0].equals(studentData.getStudentName())) {
                    String question = parts[1].replace(".csv", ""); // Remove ".csv"
                    results.add(new Result(sn++, question, Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), parts[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        resultsTable.getItems().setAll(results);
    }


    @FXML
    private void handleBackButtonAction() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StudentDashboard.fxml"))));
        stage.setScene(scene);
        stage.show();
    }
}
