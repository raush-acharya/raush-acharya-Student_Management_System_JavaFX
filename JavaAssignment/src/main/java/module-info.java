module com.example.javaassignment {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.javaassignment to javafx.fxml;
    exports com.example.javaassignment;
}