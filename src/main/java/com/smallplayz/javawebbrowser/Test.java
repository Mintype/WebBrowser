package com.smallplayz.javawebbrowser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class Test implements Initializable {
    @FXML
    private WebView webView;

    @FXML
    private Button button;

    @FXML
    private WebEngine engine;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("hi");
        engine = webView.getEngine();
        loadPage();
    }

    public void loadPage() {
        engine.load("http://www.youtube.com");
    }

}
