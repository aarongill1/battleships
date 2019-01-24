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
//        p2Board.tileList.get(0).get(0).setOccupied();

        EventHandler<MouseEvent> fireEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                double posX = me.getX();
                double posY = me.getY();
                int colX = (int) (posX / p2Board.getRectWidth());
                int colY = (int) (posY / p2Board.getRectWidth());
                p2Board.tileList.get(colX).get(colY).fire();
                String missImagePath = "resources/miss.png";
                String hitImagePath = "resources/fire.png";
                Image missImage = new Image(missImagePath);
                Image hitImage = new Image(hitImagePath);
                if(p2Board.tileList.get(colX).get(colY).isOccupied()){
                    p2Board.rec[colX][colY].setFill(new ImagePattern(hitImage));
                }
                else {
                    p2Board.rec[colX][colY].setFill(new ImagePattern(missImage));
                }
            }
        };

        EventHandler<MouseEvent> eventHandlerPlayer1 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                System.out.println("Hello World");
                double posX = me.getX();
                double posY = me.getY();
                int colX = (int) (posX / p1Board.getRectWidth());
                int colY = (int) (posY / p1Board.getRectWidth());
                p1Board.tileList.get(colX).get(colY).setOccupied();
                String shipPath = "resources/boat.png";
                Image shipImage = new Image(shipPath);
                p1Board.rec[colX][colY].setFill(new ImagePattern(shipImage));
                System.out.println(colX);
                System.out.println(colY);
            }
        };

        EventHandler<MouseEvent> eventHandlerPlayer2 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                System.out.println("Hello World 2");
                double posX = me.getX();
                double posY = me.getY();
                int colX = (int) (posX / p2Board.getRectWidth());
                int colY = (int) (posY / p2Board.getRectWidth());
                p2Board.tileList.get(colX).get(colY).setOccupied();
                String shipPath = "resources/boat.png";
                Image shipImage = new Image(shipPath);
                p2Board.rec[colX][colY].setFill(new ImagePattern(shipImage));
                System.out.println(colX);
                System.out.println(colY);
            }
        };
        // Scene 0 setup
        VBox welcome = new VBox();
        Label label = new Label("Welcome to Battleships - Player 1, select your ship locations");
        Button button1 = new Button("Click when finished");
        button1.setOnAction(actionEvent -> {


            Label label1 = new Label("Player 2, select your ships");

            Button button2 = new Button("Select to Start Game");
            VBox root1 = new VBox();
            root1.getChildren().add(label1);
            root1.getChildren().add(button2);
            root1.getChildren().add(p2Board.getGameBoard());
            root1.setAlignment(Pos.CENTER);
            root1.setPadding(new Insets(10, 10, 10, 10));
            root1.setSpacing(10);
            Scene p2SelectShipScreen = new Scene(root1, 320, 640);
//            p2Board.getGameBoard().setOnMouseClicked(fireEvent);
            p2Board.getGameBoard().setOnMouseClicked(eventHandlerPlayer2);
//            p2Board.getGameBoard().setOnMouseClicked(fireEvent);
            primaryStage.setScene(p2SelectShipScreen);

            button2.setOnAction(actionEvent1 -> {

                VBox root = new VBox();
                root.getChildren().add(p2Board.getGameBoard());
                root.getChildren().add(p1Board.getGameBoard());
                root.setAlignment(Pos.CENTER);
                root.setPadding(new Insets(10, 10, 10, 10));
                root.setSpacing(10);
                Scene scene2 = new Scene(root, 320, 640);

                p2Board.getGameBoard().setOnMouseClicked(fireEvent);
                primaryStage.setScene(scene2);
            });
        });

        welcome.getChildren().add(label);
        welcome.getChildren().add(button1);
        p1Board.getGameBoard().setOnMouseClicked(eventHandlerPlayer1);
        welcome.getChildren().add(p1Board.getGameBoard());
        Scene welcomeScene = new Scene(welcome, 320, 640);
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}