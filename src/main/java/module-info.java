module com.smallplayz.javawebbrowser {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;


    opens com.smallplayz.javawebbrowser to javafx.fxml;
    exports com.smallplayz.javawebbrowser;
}