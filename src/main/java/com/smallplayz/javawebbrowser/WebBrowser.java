package com.smallplayz.javawebbrowser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class WebBrowser extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root);

        stage.setMinWidth(1280);
        stage.setMinHeight(720);
        stage.setMaximized(true);

        /*
        Image icon = new Image("C:\\Users\\900ra\\IdeaProjects\\javafxtutorial\\src\\logo.jpg");
        stage.getIcons().add(icon);
         */

        stage.setTitle("WebBrowser");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}