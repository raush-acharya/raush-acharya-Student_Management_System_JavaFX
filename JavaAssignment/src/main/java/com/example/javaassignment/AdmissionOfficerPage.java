package com.example.javaassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class AdmissionOfficerPage {

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
    private ChoiceBox<String> gender;

    @FXML
    private Button logoutButton;

    private static final String FILE_PATH = "users.csv";

    @FXML
    void initialize() {
        // Initialize the ChoiceBox with appropriate values
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
    private void handleAdmitStudentButtonAction(ActionEvent event) {
        String fName = firstName.getText();
        String lName = lastName.getText();
        String phone = number.getText();
        String uName = userName.getText();
        String pwd = password.getText();
        String mail = email.getText();
        String fac = faculty.getText();
        String gend = gender.getValue();
        String uType = "Student";

        if (fName.isEmpty() || lName.isEmpty() || phone.isEmpty() || uName.isEmpty() ||
                pwd.isEmpty() || mail.isEmpty() || fac.isEmpty() || gend == null) {
            showAlert(AlertType.ERROR, "Form Error!", "Please fill all the fields");
            return;
        }

        String hashedPassword = hashPassword(pwd);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n", fName, lName, phone, uName, hashedPassword, mail, fac, uType, gend));
            showAlert(AlertType.INFORMATION, "Registration Successful!", "Student has been registered successfully.");
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Error", "Could not save student data.");
        }
    }

    @FXML
    private void handleLogoutButtonAction(ActionEvent event) throws IOException {
        // Code to handle logout (e.g., redirect to login screen)
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml"))));
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

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
