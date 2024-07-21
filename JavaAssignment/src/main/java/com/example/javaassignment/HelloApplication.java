package com.example.javaassignment;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private ChoiceBox<String> myChoiceBox;

    @FXML
    private Label loginAsLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Label errorLabel1;

    private final String[] choices = {"Admin", "Staff", "Student"};

    private static final String FILE_PATH = "users.csv";

    private String loggedInUser;
    private String loggedInFaculty;

    private String loggedInPhone;

    private String loggedInEmail;

    @FXML
    public void initialize() {
        myChoiceBox.getItems().addAll(choices);
        myChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loginAsLabel.setVisible(false);
        });

        loginButton.setOnAction(event -> {
            try {
                handleLogin();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleLogin() throws IOException {
        String selectedRole = myChoiceBox.getValue();
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        boolean validCredentials = validateCredentials(enteredUsername, enteredPassword, selectedRole);

        if (validCredentials) {
            errorLabel.setVisible(false);
            errorLabel1.setVisible(false);
            switchToRolePage(selectedRole);
        } else {
            errorLabel.setVisible(true);
            errorLabel1.setVisible(true);
        }
    }

    private boolean validateCredentials(String username, String password, String role) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length < 9) continue; // Ensure there are enough fields in the line

                String fileUsername = userData[3];
                String filePassword = userData[4];
                String fileRole = userData[7];

                if (fileUsername.equals(username) && filePassword.equals(password) && fileRole.equals(role)) {
                    loggedInUser = userData[0] + " " + userData[1];
                    loggedInFaculty = userData[6];
                    loggedInPhone = userData[2];
                    loggedInEmail = userData[5];
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void switchToRolePage(String role) throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader loader;
        Scene scene;

        switch (role) {
            case "Admin":
                loader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
                scene = new Scene(loader.load());
                break;
            case "Staff":
                loader = new FXMLLoader(getClass().getResource("StaffPage.fxml"));
                scene = new Scene(loader.load());
                break;
            case "Student":
                loader = new FXMLLoader(getClass().getResource("StudentDashboard.fxml"));
                scene = new Scene(loader.load());
                StudentDashboard studentDashboard = loader.getController();
                studentDashboard.setUserData(loggedInUser, loggedInFaculty, loggedInPhone, loggedInEmail);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
