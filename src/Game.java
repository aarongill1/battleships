import Model.Board;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.InetAddress;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Release the Kraken");

        VBox welcome = new VBox();
        VBox root = new VBox();
        VBox multiplayerSetup = new VBox();

        Scene scene0 = new Scene(welcome, 320, 640);
        Scene scene1 = new Scene(root, 320, 640);
        Scene scene2 = new Scene(multiplayerSetup, 320, 640);

        Label label = new Label("Welcome to Battleships: Kräken Edition");
        Button twoPlayerLocal = new Button("2 Player Local");
        twoPlayerLocal.setOnAction(actionEvent -> {
            primaryStage.setScene(scene1);
        });
        Button twoPlayerLan = new Button("2 Player LAN");
        twoPlayerLan.setOnAction(actionEvent -> {
            primaryStage.setScene(scene2);
        });
        Button singlePlayer = new Button("Single Player");
        singlePlayer.setOnAction(actionEvent -> {
//            primaryStage.setScene();
        });

        welcome.getChildren().add(label);
        welcome.getChildren().add(twoPlayerLocal);
        welcome.getChildren().add(twoPlayerLan);
        welcome.getChildren().add(singlePlayer);
        welcome.setAlignment(Pos.CENTER);
        welcome.setPadding(new Insets(10, 10, 10, 10));
        welcome.setSpacing(40);

        Board p1Board = new Board(8, 30);
        Board p2Board = new Board(8, 30);

        root.getChildren().add(p1Board.getGameBoard());
        root.getChildren().add(p2Board.getGameBoard());
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setSpacing(10);

        Label multiplsyerLabel = new Label("Choose connection type");
        multiplayerSetup.getChildren().add(multiplsyerLabel);
        multiplayerSetup.setAlignment(Pos.CENTER);
        multiplayerSetup.setPadding(new Insets(10, 10, 10, 10));
        multiplayerSetup.setSpacing(10);
        InetAddress inetAddress = InetAddress.getLocalHost();
        Label hostIPAddress = new Label("Your IP Address is: " + inetAddress.getHostAddress());
        multiplayerSetup.getChildren().add(hostIPAddress);
        Button host = new Button("Host a new game");
        multiplayerSetup.getChildren().add(host);
        TextField port = new TextField();
        port.setPromptText("Enter IP Address");
        multiplayerSetup.getChildren().add(port);
        Button join = new Button("Join game");
        multiplayerSetup.getChildren().add(join);


//        BackgroundImage kraken = new BackgroundImage(new Image("", 320, 640, true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//        welcome.setBackground(new Background(kraken));

        primaryStage.setScene(scene0);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}