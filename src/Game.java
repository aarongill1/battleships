import Model.Board;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Release the Kraken");

        VBox root = new VBox();

        Board p1Board = new Board(8, 30);
        Board p2Board = new Board(8, 30);

        root.getChildren().add(p1Board.getGameBoard());
        root.getChildren().add(p2Board.getGameBoard());
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setSpacing(10);
        Scene scene1 = new Scene(root, 320, 640);

        VBox welcome = new VBox();

        Label label = new Label("Welcome to Battleships: Kräken Edition");
        Button twoPlayerLocal = new Button("2 Player Local");
        twoPlayerLocal.setOnAction(actionEvent ->  {
            primaryStage.setScene(scene1);
        });
        Button twoPlayerLAN = new Button("2 Player LAN");
        twoPlayerLAN.setOnAction(actionEvent ->  {
//            primaryStage.setScene();
        });
        Button singlePlayer = new Button("Single Player");
        twoPlayerLAN.setOnAction(actionEvent ->  {
//            primaryStage.setScene();
        });

        welcome.getChildren().add(label);
        welcome.getChildren().add(twoPlayerLocal);
        welcome.getChildren().add(twoPlayerLAN);
        welcome.getChildren().add(singlePlayer);
        welcome.setAlignment(Pos.CENTER);
        welcome.setPadding(new Insets(10, 10, 10, 10));
        welcome.setSpacing(40);

        Scene scene0 = new Scene(welcome, 320, 640);

        primaryStage.setScene(scene0);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}