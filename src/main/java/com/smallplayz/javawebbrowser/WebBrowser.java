package com.smallplayz.javawebbrowser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class WebBrowser extends Application {
    public static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WebScene.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);

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