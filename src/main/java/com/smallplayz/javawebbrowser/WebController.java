package com.smallplayz.javawebbrowser;

import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

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
    @FXML
    private Tab defaultTab;

    private WebEngine engine;

    private boolean[] sideBarOpen = new boolean[2];

    //***************

    private BorderPane chatGPTBorderPane1 = new BorderPane();
    private Text chatGPTtitle = new Text("ChatGPT");
    private ScrollPane chatGPTScrollPane = new ScrollPane();
    private BorderPane chatGPTBorderPane2 = new BorderPane();
    private Button chatGPTSendButton = new Button("Send Message");
    private TextArea chatGPTTextArea = new TextArea();

    //***************

    private BorderPane autoClickerBorderPane1 = new BorderPane();
    private Text autoClickerTitle = new Text("AutoClicker");

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initializeEngine();
        initializeDefaultTab();
        initializeAI();
        initializeAutoClicker();
    }

    private void initializeEngine() {
        engine = webView.getEngine();
        engine.load("http://www.google.com");

        engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
                HTMLEditorKit htmlKit = new HTMLEditorKit();
                HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
                HTMLEditorKit.Parser parser = new ParserDelegator();
                try {
                    parser.parse(new InputStreamReader(new URL(engine.getLocation()).openStream()),
                            htmlDoc.getReader(0), true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                tabPane.getSelectionModel().getSelectedItem().setText(htmlDoc.getProperty("title").toString());
            }
        });
    }

    private void initializeDefaultTab() {
        defaultTab.setOnCloseRequest(event -> {
            tabPane.getTabs().remove(defaultTab);
            if(tabPane.getTabs().isEmpty()) {
                System.out.println("All Tabs Closed");
                allTabsClosed();
            }
        });
    }

    private void allTabsClosed() {
        borderPane.setCenter(null);
        BorderPane borderPane1 = new BorderPane();
        borderPane1.setCenter(new Text("Hello World"));
        borderPane.setCenter(borderPane1);
    }

    private void initializeAutoClicker() {
        autoClickerBorderPane1.setMinWidth(200);
        autoClickerBorderPane1.setMaxWidth(200);
        autoClickerTitle.setFont(new Font("Calibri Light", 35));
        autoClickerTitle.setUnderline(true);
        BorderPane.setMargin(autoClickerTitle, new Insets(25, 0, 25, 0));
        BorderPane.setAlignment(autoClickerTitle, javafx.geometry.Pos.TOP_CENTER);
        autoClickerBorderPane1.setTop(autoClickerTitle);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        Text clickIntervalText = new Text("Click Interval (ms)");
        VBox.setMargin(clickIntervalText, new Insets(0, 0, 5, 0));

        TextField textFieldAutoClickerMiliSeconds = new TextField();
        textFieldAutoClickerMiliSeconds.setMinWidth(100);
        textFieldAutoClickerMiliSeconds.setMaxWidth(100);
        VBox.setMargin(textFieldAutoClickerMiliSeconds, new Insets(0, 0, 15, 0));

        Text clickOptionsText = new Text("Click Options");
        VBox.setMargin(clickOptionsText, new Insets(0, 0, 5, 0));
        Button clickOptionsToggleButton = new Button("Left Click");
        VBox.setMargin(clickOptionsToggleButton, new Insets(0, 0, 15, 0));
        clickOptionsToggleButton.setMinWidth(100);
        clickOptionsToggleButton.setMaxWidth(100);
        clickOptionsToggleButton.setOnAction(event -> {
            if(clickOptionsToggleButton.getText().equals("Left Click"))
                clickOptionsToggleButton.setText("Right Click");
            else
                clickOptionsToggleButton.setText("Left Click");
        });

        Text clickRepeatText = new Text("Click Repeat");
        VBox.setMargin(clickRepeatText, new Insets(0, 0, 5, 0));

        RadioButton clickRepeatRadioButtonNum = new RadioButton("Repeat");
        VBox.setMargin(clickRepeatRadioButtonNum, new Insets(0, 0, 2, 0));
        RadioButton clickRepeatRadioButtonInf = new RadioButton("Infinite");
        VBox.setMargin(clickRepeatRadioButtonInf, new Insets(0, 0, 2, 0));
        TextField clickRepeatTextField = new TextField();
        clickRepeatTextField.setMinWidth(100);
        clickRepeatTextField.setMaxWidth(100);

        // Set a TextFormatter to filter out non-numeric characters
        Pattern pattern = Pattern.compile("\\d*");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (pattern.matcher(newText).matches()) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        clickRepeatTextField.setTextFormatter(textFormatter);


        clickRepeatRadioButtonNum.setOnAction(event -> {
            if (clickRepeatRadioButtonNum.isSelected()) {
                clickRepeatRadioButtonInf.setSelected(false);
                clickRepeatTextField.setEditable(true);
            }
        });

        clickRepeatRadioButtonInf.setOnAction(event -> {
            if (clickRepeatRadioButtonInf.isSelected()) {
                clickRepeatRadioButtonNum.setSelected(false);
                clickRepeatTextField.setEditable(false);
            }
        });

        vBox.getChildren().add(clickIntervalText);
        vBox.getChildren().add(textFieldAutoClickerMiliSeconds);
        vBox.getChildren().add(clickOptionsText);
        vBox.getChildren().add(clickOptionsToggleButton);
        vBox.getChildren().add(clickRepeatText);
        vBox.getChildren().add(clickRepeatRadioButtonNum);
        vBox.getChildren().add(clickRepeatRadioButtonInf);
        vBox.getChildren().add(clickRepeatTextField);
        autoClickerBorderPane1.setCenter(vBox);
    }

    private void initializeAI() {
        chatGPTBorderPane1.setMinWidth(200);
        chatGPTBorderPane1.setMaxWidth(200);
        chatGPTtitle.setFont(new Font("Calibri Light", 35));
        chatGPTtitle.setUnderline(true);
        BorderPane.setMargin(chatGPTtitle, new Insets(25, 0, 0, 0));
        BorderPane.setAlignment(chatGPTtitle, javafx.geometry.Pos.TOP_CENTER);
        chatGPTBorderPane1.setTop(chatGPTtitle);
        chatGPTScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatGPTBorderPane1.setCenter(chatGPTScrollPane);
        chatGPTBorderPane2.setTop(chatGPTSendButton);
        BorderPane.setAlignment(chatGPTSendButton, Pos.CENTER);
        chatGPTTextArea.setWrapText(true);
        chatGPTTextArea.setMaxHeight(110);
        chatGPTBorderPane2.setBottom(chatGPTTextArea);
        chatGPTBorderPane1.setBottom(chatGPTBorderPane2);

        chatGPTSendButton.setOnAction(event -> {
            try {
                String response = getChatGPT(chatGPTTextArea.getText());
                Text t = new Text(response);
                t.setWrappingWidth(200);
                chatGPTScrollPane.setContent(t);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void searchButton() throws IOException {
        if(!(searchBar.getText().contains(" ") || searchBar.getText().isEmpty())) {
            if(searchBar.getText().startsWith("https://www."))
                engine.load(searchBar.getText());
            else
                engine.load("https://www." + searchBar.getText());
            HTMLEditorKit htmlKit = new HTMLEditorKit();
            HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
            HTMLEditorKit.Parser parser = new ParserDelegator();
            parser.parse(new InputStreamReader(new URL("https://www." + searchBar.getText()).openStream()),
                    htmlDoc.getReader(0), true);
            tabPane.getSelectionModel().getSelectedItem().setText(htmlDoc.getProperty("title").toString());
        }
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
            tabPane.getTabs().remove(newTab);
            if(tabPane.getTabs().isEmpty()) {
                System.out.println("All Tabs Closed");
                allTabsClosed();
            }
        });

        engine = webView1.getEngine();
        engine.load("https://www.google.com");

        tabPane.getTabs().add(newTab);

        borderPane.setCenter(tabPane);

        tabPane.getSelectionModel().select(newTab);
    }
    public void chatGPT() {
        if(!sideBarOpen[0]) {
            Arrays.fill(sideBarOpen, false);
            sideBarOpen[0] = true;
            borderPane.setRight(chatGPTBorderPane1);
        } else {
            sideBarOpen[0] = false;
            borderPane.setRight(null);
        }
    }
    public String getChatGPT(String str) throws IOException {
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
    public void autoClicker() {
        if(!sideBarOpen[1]) {
            Arrays.fill(sideBarOpen, false);
            sideBarOpen[1] = true;
            borderPane.setRight(autoClickerBorderPane1);
        } else {
            sideBarOpen[1] = false;
            borderPane.setRight(null);
        }
    }
}