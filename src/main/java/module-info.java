module com.smallplayz.javawebbrowser {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.smallplayz.javawebbrowser to javafx.fxml;
    exports com.smallplayz.javawebbrowser;
}