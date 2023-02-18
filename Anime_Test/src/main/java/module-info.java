module com.example.anime {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.anime to javafx.fxml;
    exports com.example.anime;
}