package com.smallplayz.javawebbrowser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class WebBrowser extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WebScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        //stage.getIcons().add(new Image("icon.png"));
        stage.setTitle("WebBrowser");
        stage.setMinHeight(520);
        stage.setMinWidth(850);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}