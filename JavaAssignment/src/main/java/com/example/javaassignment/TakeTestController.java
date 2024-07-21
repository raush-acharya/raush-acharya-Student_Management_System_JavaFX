package com.example.javaassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TakeTestController {

    @FXML
    private Button backButton;

    @FXML
    private VBox testsVBox;

    @FXML
    private void handleBackButtonAction() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StudentDashboard.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        File folder = new File("."); // Current directory
        File[] listOfFiles = folder.listFiles((dir, name) -> name.matches("Question\\d+\\.csv"));

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                Button testButton = new Button(file.getName().replace(".csv", ""));
                testButton.setPrefHeight(25.0);
                testButton.setPrefWidth(150.0);
                testButton.setStyle("-fx-background-color: #76ABAE; -fx-text-fill: WHITE;");
                testButton.setOnAction(this::handleTestButtonAction);
                testsVBox.getChildren().add(testButton);
            }
        }
    }

    @FXML
    private void handleTestButtonAction(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        String testFileName = sourceButton.getText() + ".csv";
        try {
            loadTest(testFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTest(String testFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("TakeTestQuestions.fxml")));
        Stage stage = (Stage) testsVBox.getScene().getWindow();
        Scene scene = new Scene(loader.load());

        TakeTestQuestionsController controller = loader.getController();
        controller.setTestFileName(testFileName);

        stage.setScene(scene);
        stage.show();
    }
}
