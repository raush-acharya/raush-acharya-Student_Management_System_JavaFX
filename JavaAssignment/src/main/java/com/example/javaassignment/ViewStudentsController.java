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

public class ViewStudentsController {

    @FXML
    private TableView<Student> studentsTable;

    @FXML
    private TableColumn<Student, Integer> snColumn;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> phoneColumn;

    @FXML
    private TableColumn<Student, String> emailColumn;

    @FXML
    private TableColumn<Student, String> facultyColumn;

    @FXML
    private TableColumn<Student, String> genderColumn;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        snColumn.setCellValueFactory(new PropertyValueFactory<>("sn"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        loadStudents();
    }

    private void loadStudents() {
        ObservableList<Student> students = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            int sn = 1;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length < 9) continue; // Ensure there are enough fields in the line
                if ("Student".equalsIgnoreCase(userData[7])) {
                    String name = userData[0] + " " + userData[1];
                    String phone = userData[2];
                    String email = userData[5];
                    String faculty = userData[6];
                    String gender = userData[8];
                    students.add(new Student(sn++, name, phone, email, faculty, gender));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        studentsTable.setItems(students);
    }

    @FXML
    private void handleBackButtonAction() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TeacherPage.fxml"))));
        stage.setScene(scene);
        stage.show();
    }
}
