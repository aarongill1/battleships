import Model.Board;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Release the Kracken");
        VBox root = new VBox();

        Board p1Board = new Board(8);
        Board p2Board = new Board(8);

        root.getChildren().add(p1Board.makeGrid(p1Board.getSize()));
        root.getChildren().add(p2Board.makeGrid(p1Board.getSize()));
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setSpacing(10);

        Scene scene1 = new Scene(root, 320, 640);
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}