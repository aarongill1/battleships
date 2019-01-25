import Model.Board;
import Model.Player;
import Server.Server;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.net.InetAddress;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Release the Kraken");
        primaryStage.setResizable(false);

        Alert shipAlreadyPlacedAlert = new Alert(Alert.AlertType.INFORMATION);
        shipAlreadyPlacedAlert.setTitle("Dumbass! ");
        shipAlreadyPlacedAlert.setHeaderText("Yes, you are!");
        shipAlreadyPlacedAlert.setContentText("You've already placed a ship there!");

        Board p1Board = new Board(8, 30);
        Board p2Board = new Board(8, 30);
        Player player1 = new Player(null, p1Board);
        Player player2 = new Player(null, p2Board);

        VBox FrontPage = new VBox();
        VBox p1Turn = new VBox();
        VBox multiplayerSetup = new VBox();
        VBox p1setup = new VBox();

        TextField p1nameInput = new TextField();

        p1Turn.getChildren().add(p1Board.getGameBoard());
        p1Turn.getChildren().add(p2Board.getGameBoard());
        p1Turn.setAlignment(Pos.CENTER);
        p1Turn.setPadding(new Insets(10, 10, 10, 10));
        p1Turn.setSpacing(10);

        Scene sceneMPS = new Scene(FrontPage, 320, 640);
        Scene scene1 = new Scene(p1Turn, 320, 640);
        Scene mps = new Scene(multiplayerSetup, 320, 640);
        Scene welcomeScene = new Scene(p1setup, 320, 640);

        Label welcomeLabel = new Label("Welcome to Battleships: Kräken Edition");
        Button twoPlayerLocal = new Button("2 Player Local");
        twoPlayerLocal.setOnAction(actionEvent -> {
            primaryStage.setScene(welcomeScene);
        });
        Button twoPlayerLan = new Button("2 Player LAN");
        twoPlayerLan.setOnAction(actionEvent -> {
            primaryStage.setScene(mps);
        });
        Button singlePlayer = new Button("Single Player");
        singlePlayer.setOnAction(actionEvent -> {
//            primaryStage.setScene();
        });

        FrontPage.getChildren().add(welcomeLabel);
        FrontPage.getChildren().add(twoPlayerLocal);
        FrontPage.getChildren().add(twoPlayerLan);
        FrontPage.getChildren().add(singlePlayer);
        FrontPage.setAlignment(Pos.CENTER);
        FrontPage.setPadding(new Insets(10, 10, 10, 10));
        FrontPage.setSpacing(40);

        Label multiplayerLabel = new Label("Choose connection type");
        multiplayerSetup.getChildren().add(multiplayerLabel);
        multiplayerSetup.setAlignment(Pos.CENTER);
        multiplayerSetup.setPadding(new Insets(10, 10, 10, 10));
        multiplayerSetup.setSpacing(10);
        InetAddress inetAddress = InetAddress.getLocalHost();
        Label hostIPAddress = new Label("Your IP Address is: " + inetAddress.getHostAddress());
        multiplayerSetup.getChildren().add(hostIPAddress);
        Button host = new Button("Host a new game");
        host.setOnAction(actionEvent -> {
            Server.start(52864);
            primaryStage.setScene(welcomeScene);
        });
        multiplayerSetup.getChildren().add(host);
        TextField port = new TextField();
        port.setPromptText("Enter IP Address");
        multiplayerSetup.getChildren().add(port);
        Button join = new Button("Join game");
        multiplayerSetup.getChildren().add(join);


        EventHandler<MouseEvent> p2fireEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                System.out.println(me.getSource());
                double posX = me.getX();
                double posY = me.getY();
                int colX = (int) (posX / p2Board.getRectWidth());
                int colY = (int) (posY / p2Board.getRectWidth());
                p2Board.tileList.get(colX).get(colY).fire();
                String missImagePath = "resources/miss.png";
                String hitImagePath = "resources/fire.png";
                Image missImage = new Image(missImagePath);
                Image hitImage = new Image(hitImagePath);
                if (p2Board.tileList.get(colX).get(colY).isOccupied()) {
                    p2Board.rec[colX][colY].setFill(new ImagePattern(hitImage));
                } else {
                    p2Board.rec[colX][colY].setFill(new ImagePattern(missImage));
                }
            }
        };

        EventHandler<MouseEvent> p1fireEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                System.out.println(me.getSource());
                double posX = me.getX();
                double posY = me.getY();
                int colX = (int) (posX / p1Board.getRectWidth());
                int colY = (int) (posY / p1Board.getRectWidth());
                p1Board.tileList.get(colX).get(colY).fire();
                String missImagePath = "resources/miss.png";
                String hitImagePath = "resources/fire.png";
                Image missImage = new Image(missImagePath);
                Image hitImage = new Image(hitImagePath);
                if (p1Board.tileList.get(colX).get(colY).isOccupied()) {
                    p1Board.rec[colX][colY].setFill(new ImagePattern(hitImage));
                } else {
                    p1Board.rec[colX][colY].setFill(new ImagePattern(missImage));
                }
            }
        };

        EventHandler<MouseEvent> p1PlaceShips = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (player1.getFleetNumber() != 0) {
                    double posX = me.getX();
                    double posY = me.getY();
                    int colX = (int) (posX / p1Board.getRectWidth());
                    int colY = (int) (posY / p1Board.getRectWidth());
                    if (!p1Board.tileList.get(colX).get(colY).isOccupied()) {
                        p1Board.tileList.get(colX).get(colY).setOccupied();
                        String shipPath = "resources/boat.png";
                        Image shipImage = new Image(shipPath);
                        p1Board.rec[colX][colY].setFill(new ImagePattern(shipImage));
                        player1.setFleetNumber((player1.getFleetNumber() - 1));
                    } else {
                        shipAlreadyPlacedAlert.showAndWait();
                    }
                }
            }
        };

        EventHandler<MouseEvent> p2PlaceShips = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {
                if (player2.getFleetNumber() != 0) {
                    double posX = me.getX();
                    double posY = me.getY();
                    int colX = (int) (posX / p2Board.getRectWidth());
                    int colY = (int) (posY / p2Board.getRectWidth());

                    if (!p2Board.tileList.get(colX).get(colY).isOccupied()) {
                        p2Board.tileList.get(colX).get(colY).setOccupied();
                        String shipPath = "resources/boat.png";
                        Image shipImage = new Image(shipPath);
                        p2Board.rec[colX][colY].setFill(new ImagePattern(shipImage));
                        player2.setFleetNumber((player2.getFleetNumber() - 1));
                    }
                    else{
                        shipAlreadyPlacedAlert.showAndWait();
                    }

                }
            }
        };
        
        Label p1welcomeMessage = new Label("Welcome to Battleships - Player 1, select your ship locations");
        //Button advanceTop2Setup = new Button("Click when finished");
        Label label1 = new Label("Player 2, select your ships");
        Button advanceTop2Turn = new Button("Player 2 turn");
        advanceTop2Turn.setOnAction(actionEvent ->{
            VBox p2Turn = new VBox();
            Button returnToPlayer1 = new Button("Player 1 turn");
            p2Turn.getChildren().add(returnToPlayer1);
            returnToPlayer1.setOnAction(actionEvent1 -> {
                primaryStage.setScene(scene1);
            });
            Scene p2TurnScene = new Scene(p2Turn, 320, 640);
            primaryStage.setScene(p2TurnScene);
        });
        Button startGameButton = new Button("Select to Start Game");
        startGameButton.setOnAction(actionEvent -> {


            startGameButton.setOnAction(actionEvent1 -> {
                p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
                p2Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2fireEvent);
                p1Turn.getChildren().add(p2Board.getGameBoard());
                p1Turn.getChildren().add(p1Board.getGameBoard());
                p1Turn.getChildren().add(advanceTop2Turn);
                p1Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
                p1Turn.setAlignment(Pos.CENTER);
                p1Turn.setPadding(new Insets(10, 10, 10, 10));
                p1Turn.setSpacing(10);
                p2Board.setShipstoInvisible();
                primaryStage.setScene(scene1);
            });

            // p2 setup
            Label nameLabel = new Label("Player 2 enter your name!");
            TextField p2nameInput = new TextField();
            VBox p2setup = new VBox();
            p2setup.getChildren().add(label1);
            p2setup.getChildren().add(nameLabel);
            p2setup.getChildren().add(p2nameInput);
            p2setup.getChildren().add(startGameButton);
            p2setup.getChildren().add(p2Board.getGameBoard());
            p2setup.setAlignment(Pos.CENTER);
            p2setup.setPadding(new Insets(10, 10, 10, 10));
            p2setup.setSpacing(10);
            Scene p2SelectShipScreen = new Scene(p2setup, 320, 640);
            p2Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
            primaryStage.setScene(p2SelectShipScreen);
        });

        p1setup.getChildren().add(p1welcomeMessage);
        p1setup.getChildren().add(p1nameInput);
        p1setup.getChildren().add(startGameButton);
        // This line carries over into play screen and allows p1 to place ships even when game has already started.
        p1Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
        p1setup.getChildren().add(p1Board.getGameBoard());

////    BackgroundImage kraken = new BackgroundImage(new Image("", 320, 640, true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
////    welcome.setBackground(new Background(kraken));


        primaryStage.setScene(sceneMPS);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}