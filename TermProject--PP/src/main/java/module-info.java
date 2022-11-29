module com.termprojectpp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.termprojectpp.track;
    exports com.termprojectpp.app;
    opens com.termprojectpp.app to javafx.fxml;
}