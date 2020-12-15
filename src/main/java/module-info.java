module org.foxrider {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.foxrider to javafx.fxml;
    opens entitys to javafx.fxml;
    exports org.foxrider;
}