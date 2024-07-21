package com.example.javaassignment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Objects;

public class ViewLoansController implements Initializable {

    @FXML
    private TableView<Loan> loansTable;

    @FXML
    private TableColumn<Loan, String> studentNameColumn;

    @FXML
    private TableColumn<Loan, String> bookTitleColumn;

    @FXML
    private TableColumn<Loan, String> borrowDateColumn;

    @FXML
    private TableColumn<Loan, String> returnDateColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        loadLoans();
    }

    private void loadLoans() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Loans.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Loan loan = new Loan(parts[0], parts[1], parts[2], parts[3]);
                    loansTable.getItems().add(loan);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButtonAction() throws IOException {
        Stage stage = (Stage) loansTable.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LibrarianPage.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    public static class Loan {
        private final String studentName;
        private final String bookTitle;
        private final String borrowDate;
        private final String returnDate;

        public Loan(String studentName, String bookTitle, String borrowDate, String returnDate) {
            this.studentName = studentName;
            this.bookTitle = bookTitle;
            this.borrowDate = borrowDate;
            this.returnDate = returnDate;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public String getBorrowDate() {
            return borrowDate;
        }

        public String getReturnDate() {
            return returnDate;
        }
    }
}
