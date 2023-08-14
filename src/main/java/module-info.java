module com.example.algorhitmproject1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.algorhitmproject1 to javafx.fxml;
    exports com.example.algorhitmproject1;
}