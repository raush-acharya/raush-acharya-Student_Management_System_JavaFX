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

    private final String[] choices = {"Admin", "Staff", "Student"};

    private static final String FILE_PATH = "users.csv";

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

        System.out.println(selectedRole);
        System.out.println(enteredPassword);
        System.out.println(enteredUsername);

        boolean validCredentials = validateCredentials(enteredUsername, enteredPassword, selectedRole);
        System.out.println(validCredentials);

        if (validCredentials) {
            switchToRolePage(selectedRole);
        } else {
            System.out.println("Invalid credentials");
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

                System.out.println("fileusername:");
                System.out.println(fileUsername);
                System.out.println(filePassword);
                System.out.println(fileRole);

                if (fileUsername.equals(username) && filePassword.equals(password) && fileRole.equals(role)) {
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
        Scene scene;

        switch (role) {
            case "Admin":
                scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPage.fxml"))));
                break;
            case "Staff":
                scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StaffPage.fxml"))));
                break;
            case "Student":
                scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StudentDashboard.fxml"))));
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
