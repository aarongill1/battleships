import Model.Board;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

public class Game extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Release the Kracken");

        Board p1Board = new Board(8, 30);
        Board p2Board = new Board(8, 30);

        EventHandler<MouseEvent> fireEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {

                double posX = me.getX();
                double posY = me.getY();
                int colX = (int) (posX / p1Board.getRectWidth());
                int colY = (int) (posY / p1Board.getRectWidth());
                System.out.println(colX);
                System.out.println(colY);
                p1Board.tileList.get(colX).get(colY).fire();

                javafx.scene.image.Image missImage = new Image("https://openclipart.org/image/2400px/svg_to_png/16155/milker-X-icon.png");
                javafx.scene.image.Image hitImage = new Image("https://www.clipartmax.com/png/small/132-1328230_flame-free-icon-fire-icon.png");

                if(p1Board.tileList.get(colX).get(colY).isOccupied()){

                    p1Board.rec[colX][colY].setFill(new ImagePattern(hitImage));
                }
                else {

                    p1Board.rec[colX][colY].setFill(new ImagePattern(missImage));
                }
            }
        };

        // Scene 0 setup
        VBox welcome = new VBox();

        Label label = new Label("Welcome to Battleships");
        Button button1 = new Button("Play game");
        button1.setOnAction(actionEvent ->  {
            VBox root = new VBox();
            root.getChildren().add(p1Board.getGameBoard());
            root.getChildren().add(p2Board.getGameBoard());
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(10, 10, 10, 10));
            root.setSpacing(10);
            Scene scene1 = new Scene(root, 320, 640);
            p1Board.getGameBoard().setOnMouseClicked(fireEvent);
            primaryStage.setScene(scene1);
        });

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                System.out.println("Hello World");
                double posX = me.getX();
                double posY = me.getY();
                int colX = (int) (posX / p1Board.getRectWidth());
                int colY = (int) (posY / p1Board.getRectWidth());
                System.out.println(colX);
                System.out.println(colY);
            }
        };


        welcome.getChildren().add(label);
        welcome.getChildren().add(button1);
        p1Board.tileList.get(0).get(1).setOccupied();
        p1Board.tileList.get(2).get(7).setOccupied();
        p1Board.tileList.get(3).get(6).setOccupied();
        p1Board.tileList.get(4).get(2).setOccupied();
        p1Board.tileList.get(5).get(1).setOccupied();
        p1Board.getGameBoard().setOnMouseClicked(eventHandler);
        welcome.getChildren().add(p1Board.getGameBoard());
        Scene scene0 = new Scene(welcome, 320, 640);

        primaryStage.setScene(scene0);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}