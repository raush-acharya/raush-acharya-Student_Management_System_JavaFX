package com.example.javaassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javafx.application.Application.launch;

public class AdminPage {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField number;

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    private TextField email;

    @FXML
    private TextField faculty;

    @FXML
    private ChoiceBox<String> userType;

    @FXML
    private ChoiceBox<String> gender;

    @FXML
    private Button logoutButton;

    private static final String FILE_PATH = "users.csv";

    @FXML
    void initialize() {
        // Initialize the ChoiceBoxes with appropriate values
        userType.getItems().addAll("Admin", "Student", "Teacher", "Admission", "Librarian");
        gender.getItems().addAll("Male", "Female", "Other");


        // Ensure the file exists
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                Files.createFile(Paths.get(FILE_PATH));
            }
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Error", "Could not initialize user data file.");
        }
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        String fName = firstName.getText();
        String lName = lastName.getText();
        String phone = number.getText();
        String uName = userName.getText();
        String pwd = password.getText();
        String mail = email.getText();
        String fac = faculty.getText();
        String uType = userType.getValue();
        String gend = gender.getValue();

        if (fName.isEmpty() || lName.isEmpty() || phone.isEmpty() || uName.isEmpty() ||
                pwd.isEmpty() || mail.isEmpty() || fac.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error!", "Please fill all the fields");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n", fName, lName, phone, uName, pwd, mail, fac, uType, gend));
            showAlert(AlertType.INFORMATION, "Registration Successful!", "User has been registered successfully.");
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Error", "Could not save user data.");
        }
    }

    @FXML
    private void handleDeleteUserButtonAction(ActionEvent event) {
        String uName = userName.getText();

        if (uName.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error!", "Please enter the username.");
            return;
        }

        try {
            List<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(FILE_PATH)));
            boolean userFound = false;

            for (int i = 0; i < lines.size(); i++) {
                String[] userData = lines.get(i).split(",");
                if (userData[3].equals(uName)) {
                    lines.remove(i);
                    userFound = true;
                    break;
                }
            }

            if (userFound) {
                Files.write(Paths.get(FILE_PATH), lines);
                showAlert(AlertType.INFORMATION, "User Deleted", "User has been deleted successfully.");
            } else {
                showAlert(AlertType.WARNING, "User Not Found", "User not found.");
            }
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Error", "Could not delete user data.");
        }
    }

    @FXML
    private void handleLogoutButtonAction(ActionEvent event) throws IOException{
        // Code to handle logout (e.g., redirect to login screen)
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Scene scene;
        scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
