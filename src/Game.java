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
        primaryStage.setTitle("Release the Kracken");



//        primaryStage.setX(50);
//        primaryStage.setY(50);

//        Stage stage = new Stage();
////        stage.initModality(Modality.APPLICATION_MODAL);
////        stage.initModality(Modality.WINDOW_MODAL);
//        stage.initModality(Modality.NONE);
//        primaryStage.show();
//        stage.showAndWait();



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

        Label label = new Label("Welcome to Battleships");
        Button button1 = new Button("Play game");
        button1.setOnAction(actionEvent ->  {
            primaryStage.setScene(scene1);
        });

        welcome.getChildren().add(label);
        welcome.getChildren().add(button1);

        Scene scene0 = new Scene(welcome, 320, 640);

        primaryStage.setScene(scene0);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}