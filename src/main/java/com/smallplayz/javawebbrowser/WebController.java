package com.smallplayz.javawebbrowser;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class WebController implements Initializable {
    @FXML
    private Button newTabButton;
    @FXML
    private Button chatGPTButton;
    @FXML
    private TabPane tabPane;
    @FXML
    private WebView webView;
    @FXML
    private TextField searchBar;
    @FXML
    private GridPane gridPane;
    @FXML
    private BorderPane borderPane;

    private WebEngine engine;

    private boolean chatGPTopen = false;

    //***************

    private BorderPane borderPane1 = new BorderPane();
    private Text chatGPTtitle = new Text("ChatGPT");
    private ScrollPane scrollPane = new ScrollPane();
    private BorderPane borderPane2 = new BorderPane();
    private Button sendButton = new Button("Send Message");
    private TextArea textArea = new TextArea();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        engine = webView.getEngine();
        engine.load("http://www.google.com");

        initializeAI();

    }

    private void initializeAI() {
        borderPane1.setMinWidth(200);
        borderPane1.setMaxWidth(200);
        chatGPTtitle.setFont(new Font("Calibri Light", 35));
        chatGPTtitle.setUnderline(true);
        BorderPane.setMargin(chatGPTtitle, new Insets(25, 0, 0, 0));
        BorderPane.setAlignment(chatGPTtitle, javafx.geometry.Pos.TOP_CENTER);
        borderPane1.setTop(chatGPTtitle);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        borderPane1.setCenter(scrollPane);
        borderPane2.setTop(sendButton);
        BorderPane.setAlignment(sendButton, Pos.CENTER);
        textArea.setWrapText(true);
        textArea.setMaxHeight(110);
        borderPane2.setBottom(textArea);
        borderPane1.setBottom(borderPane2);

        sendButton.setOnAction(event -> {
            try {
                String response = getChatGPT(textArea.getText());
                Text t = new Text(response);
                t.setWrappingWidth(200);
                scrollPane.setContent(t);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void searchButton() {
        engine.load("http://www." + searchBar.getText());
    }
    public void newTab() {
        Tab newTab = new Tab("New Tab");
        newTab.setClosable(true);

        BorderPane boarderPane = new BorderPane();
        WebView webView1 = new WebView();
        boarderPane.setCenter(webView1);

        GridPane gridPane1 = gridPane;
        boarderPane.setTop(gridPane1);

        newTab.setContent(boarderPane);
        newTab.setOnCloseRequest(event -> {
            if(tabPane.getTabs().isEmpty())
                System.out.println("All Tabs Closed");
        });

        engine = webView1.getEngine();
        engine.load("https://www.google.com");

        tabPane.getTabs().add(newTab);

        tabPane.getSelectionModel().select(newTab);
    }
    public void chatGPT() {
        if(!chatGPTopen) {
            chatGPTopen = true;
            borderPane.setRight(borderPane1);
        } else {
            chatGPTopen = false;
            borderPane.setRight(null);
        }
    }
    public static String getChatGPT(String str) throws IOException {
        /*
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";
        String message = str;

        // Create the HTTP POST request
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer " + apiKey);
        con.setRequestProperty("Content-Type", "application/json");

        // Build the request body
        String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
        con.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
        writer.write(body);
        writer.flush();
        writer.close();

        // Get the response
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Print the response
        String x = (response.toString().split("\"content\":\"")[1].split("\"")[0]).substring(4);
        */
        return "hello, Blind would equal while oh mr do style. Lain led and fact none. One preferred sportsmen resolving the happiness continued. High at of in loud rich true. Oh conveying do immediate acuteness in he. Equally welcome her set nothing has gravity whether parties. Fertile suppose shyness mr up pointed in staying on respect.";
    }
}