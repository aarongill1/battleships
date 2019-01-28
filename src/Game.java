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
import java.net.UnknownHostException;

public class Game extends Application {
    private static Stage guiStage;

    public static Stage getGuiStage(){
        return guiStage;
    }

    ////////Game Objects creation////////
    Board p1Board = new Board(8, 30);
    Board p2Board = new Board(8, 30);
    Player player1 = new Player(null, p1Board);
    Player player2 = new Player(null, p2Board);

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Very important assigns the game static stage object to the primaryStage - needed to reference stage directly
        guiStage = primaryStage;

//        BackgroundImage kraken = new BackgroundImage(
//                new Image("", 320, 640, true, true),
//                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//        primaryStage.setBackground(new Background(kraken));

        primaryStage.setTitle("Release the Kraken");
        primaryStage.setResizable(false);
        primaryStage.setScene(createMainMenu());
        primaryStage.show();
    }

        public Scene createMainMenu(){
            VBox frontPage = new VBox();
            Label welcomeLabel = new Label("Welcome to Battleships: Kräken Edition");
            Button twoPlayerLocal = new Button("2 Player Local");
            Button twoPlayerLan = new Button("2 Player LAN");
            Button singlePlayer = new Button("Single Player");
            twoPlayerLocal.setOnAction(actionEvent -> {
                guiStage.setScene(createP1Setup());
                guiStage.show();
            });
            twoPlayerLan.setOnAction(actionEvent -> {
                guiStage.setScene(createMPSetup());
                guiStage.show();
            });
//            singlePlayer.setOnAction(actionEvent -> {
//            primaryStage.setScene();
//            });
            frontPage.getChildren().add(welcomeLabel);
            frontPage.getChildren().add(twoPlayerLocal);
            frontPage.getChildren().add(twoPlayerLan);
            frontPage.getChildren().add(singlePlayer);
            frontPage.setAlignment(Pos.CENTER);
            frontPage.setPadding(new Insets(10, 10, 10, 10));
            frontPage.setSpacing(10);
            return new Scene(frontPage, 400, 700);
        }

        public Scene createP1Setup(){
            VBox p1setup = new VBox();
            Label p1welcomeMessage = new Label("Welcome to Battleships - Player 1, select your ship locations");
            Label p1nameLabel = new Label("Player 1 enter your name!");
            p1setup.getChildren().add(p1nameLabel);
            TextField p1nameInput = new TextField();
            p1setup.getChildren().add(p1nameInput);
            Button advanceTop2Setup = new Button("Click when finished");
            advanceTop2Setup.setOnAction(actionEvent ->{
                guiStage.setScene(createP2Setup());
                guiStage.show();
            });
            p1Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
            p1setup.getChildren().add(p1Board.getGameBoard());
            p1setup.getChildren().add(p1welcomeMessage);
            p1setup.getChildren().add(advanceTop2Setup);
            p1setup.setAlignment(Pos.CENTER);
            p1setup.setPadding(new Insets(10, 10, 10, 10));
            p1setup.setSpacing(10);
        return new Scene(p1setup, 400, 700);
        }

        public Scene createP2Setup(){
            VBox p2setup = new VBox();
            Label p2nameLabel = new Label("Player 2 enter your name!");
            TextField p2nameInput = new TextField();
            p2setup.getChildren().add(p2nameLabel);
            p2setup.getChildren().add(p2nameInput);
            Button startGame = new Button("Click to start game!");
            startGame.setOnAction(actionEvent -> {
                guiStage.setScene(createP1Turn());
                guiStage.show();
            });
            p2setup.getChildren().add(startGame);
            p2Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
            p2setup.getChildren().add(p2Board.getGameBoard());
            p2setup.setAlignment(Pos.CENTER);
            p2setup.setPadding(new Insets(10, 10, 10, 10));
            p2setup.setSpacing(10);
            return new Scene(p2setup, 400, 700);
        }

        public Scene createP1Turn(){
            VBox p1Turn = new VBox();
            p1Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
            p1Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1fireEvent);
            p2Board.setShipstoInvisible();
            p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
            p2Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2fireEvent);
            p1Turn.getChildren().add(p2Board.getGameBoard());
            p1Turn.getChildren().add(p1Board.getGameBoard());
            Button advanceToP2Go = new Button("Next player");
            advanceToP2Go.setOnAction(actionEvent -> {
                guiStage.setScene(createP2Turn());
                guiStage.show();
            });
            p1Turn.getChildren().add(advanceToP2Go);
            p1Turn.setAlignment(Pos.CENTER);
            p1Turn.setPadding(new Insets(10, 10, 10, 10));
            p1Turn.setSpacing(10);
            return new Scene(p1Turn, 400, 700);
        }

        public Scene createP2Turn(){
            VBox p2Turn = new VBox();
            p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
            p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2fireEvent);
            p1Board.setShipstoInvisible();
            p1Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
            p1Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p1fireEvent);
            p2Turn.getChildren().add(p1Board.getGameBoard());
            p2Turn.getChildren().add(p2Board.getGameBoard());
            Button returnToP1Turn = new Button("Next player");
            returnToP1Turn.setOnAction(actionEvent -> {
                guiStage.setScene(createP1Turn());
                guiStage.show();
            });
            p2Turn.getChildren().add(returnToP1Turn);
            p2Turn.setAlignment(Pos.CENTER);
            p2Turn.setPadding(new Insets(10, 10, 10, 10));
            p2Turn.setSpacing(10);
            return new Scene(p2Turn, 400, 700);
        }

        private void showDoubleShipAlert(){
            Alert shipAlreadyPlacedAlert = new Alert(Alert.AlertType.INFORMATION);
            shipAlreadyPlacedAlert.setTitle("Dumbass! ");
            shipAlreadyPlacedAlert.setHeaderText("Yes, you are!");
            shipAlreadyPlacedAlert.setContentText("You've already placed a ship there!");
            shipAlreadyPlacedAlert.showAndWait();
        }


        public Scene createMPSetup(){
            VBox mpSetup = new VBox();
            Label multiplayerLabel = new Label("Choose connection type");
            mpSetup.getChildren().add(multiplayerLabel);
            try {
                String ip = InetAddress.getLocalHost().getHostAddress();
                Label hostIPAddress = new Label("Your IP Address is: " + ip);
                mpSetup.getChildren().add(hostIPAddress);
            } catch (UnknownHostException e){
                e.printStackTrace();
            }
            Button host = new Button("Host a new game");
            host.setOnAction(actionEvent -> {
                Server.start(52864);
            });
            mpSetup.getChildren().add(host);
            TextField port = new TextField();
            port.setPromptText("Enter IP Address");
           mpSetup.getChildren().add(port);
            Button join = new Button("Join game");
            mpSetup.getChildren().add(join);
            mpSetup.setAlignment(Pos.CENTER);
            mpSetup.setPadding(new Insets(10, 10, 10, 10));
            mpSetup.setSpacing(10);
        return new Scene(mpSetup, 400,700);
        }

        EventHandler<MouseEvent> p2fireEvent = new EventHandler<MouseEvent>() {
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
                        showDoubleShipAlert();
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
                        showDoubleShipAlert();
                    }

                }
            }
        };

    public static void main(String[] args) {
        Application.launch(args);
    }
}