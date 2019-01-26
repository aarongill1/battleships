package Client;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class ClientsWindow extends Application {

    private Client client;
    private static TextArea messages = new TextArea();

    private Parent createContent(){
        messages.setPrefHeight(500);

        TextField input = new TextField();
        input.setMaxWidth(500);
        input.setMaxHeight(100);
        Button send = new Button();
        send.setText("Send");
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                client.send(input.getText());
                input.setText("");
            }
        });
        VBox root = new VBox(20, messages, input, send);
        root.setPrefSize(600, 600);
        root.setAlignment(Pos.CENTER);
        return root;
    }

    public static void printToConsole(String message){
        messages.setText(messages.getText() + message + "\n");
    }

    public static String showTextInput(String title, String message, String defaultValue) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Input");
        dialog.setHeaderText(title);
        dialog.setContentText(message);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
        String name = showTextInput("", "Enter your name below", "Anonymous");
        client = new Client(name, "localhost", 52864);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
