package com.example.javaassignment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Objects;

public class LibrarianPageController implements Initializable {

    @FXML
    private TextField bookTitleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField studentNameField;

    @FXML
    private ChoiceBox<String> loanBookTitleChoiceBox;

    @FXML
    private DatePicker borrowDatePicker;

    @FXML
    private DatePicker returnDatePicker;

    @FXML
    private Button backButton;

    @FXML
    private Label bookErrorLabel;

    @FXML
    private Label loanErrorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBookTitles();
    }

    @FXML
    private void handleAddBookAction() {
        String bookTitle = bookTitleField.getText();
        String author = authorField.getText();

        if (bookTitle.isEmpty() || author.isEmpty()) {
            bookErrorLabel.setVisible(true);
        } else {
            bookErrorLabel.setVisible(false);
            try (FileWriter writer = new FileWriter("Books.csv", true)) {
                writer.append(bookTitle).append(',').append(author).append('\n');
                clearBookForm();
                loadBookTitles(); // Reload the book titles
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearBookForm() {
        bookTitleField.clear();
        authorField.clear();
    }

    private void clearLoanForm() {
        studentNameField.clear();
        loanBookTitleChoiceBox.getSelectionModel().clearSelection();
        borrowDatePicker.setValue(null);
        returnDatePicker.setValue(null);
    }

    @FXML
    private void handleAddLoanAction() {
        String studentName = studentNameField.getText();
        String loanBookTitle = loanBookTitleChoiceBox.getValue();
        LocalDate borrowDate = borrowDatePicker.getValue();
        LocalDate returnDate = returnDatePicker.getValue();

        if (studentName.isEmpty() || loanBookTitle == null || borrowDate == null || returnDate == null) {
            loanErrorLabel.setVisible(true);
        } else if (!isStudentValid(studentName)) {
            loanErrorLabel.setText("Student does not exist!");
            loanErrorLabel.setVisible(true);
        } else {
            loanErrorLabel.setVisible(false);
            try (FileWriter writer = new FileWriter("Loans.csv", true)) {
                writer.append(studentName).append(',').append(loanBookTitle).append(',').append(borrowDate.toString()).append(',').append(returnDate.toString()).append('\n');
                clearLoanForm();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isStudentValid(String studentName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1 && (parts[0] + " " + parts[1]).equals(studentName)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void loadBookTitles() {
        loanBookTitleChoiceBox.getItems().clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("Books.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                loanBookTitleChoiceBox.getItems().add(details[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewLoansAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewLoans.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButtonAction() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StaffPage.fxml"))));
        stage.setScene(scene);
        stage.show();
    }
}
