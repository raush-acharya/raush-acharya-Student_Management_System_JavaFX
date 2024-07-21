package com.example.javaassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ProfileViewController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label facultyLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Button backButton;

    private StudentData studentData = StudentData.getInstance();
    private String studentName;
    private String faculty;
    private String phone;
    private String email;


    public void setUserData(String studentName, String faculty, String phone, String email) {
        this.studentName = studentName;
        this.faculty = faculty;
        this.phone = phone;
        this.email = email;
        nameLabel.setText(studentName);
        facultyLabel.setText(faculty);
        phoneLabel.setText(phone);
        emailLabel.setText(email);
    }

    @FXML
    private void initialize() {
        nameLabel.setText(studentData.getStudentName());
        facultyLabel.setText(studentData.getFaculty());
        phoneLabel.setText(studentData.getPhone());
        emailLabel.setText(studentData.getEmail());
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StudentDashboard.fxml"))));
        stage.setScene(scene);
        stage.show();
    }
}
