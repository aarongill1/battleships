import Model.Board;
import Model.Player;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

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


    VBox p2Turn = new VBox();
    VBox multiplayerSetup = new VBox();


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Very important assigns the game static stage object to the primaryStage - needed to reference stage directly
        guiStage = primaryStage;

        primaryStage.setTitle("Release the Kraken");
        primaryStage.setResizable(false);
        primaryStage.setScene(createMainMenu());
        primaryStage.show();
    }


////////Test//////////////////////
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
//            twoPlayerLan.setOnAction(actionEvent -> {
//                primaryStage.setScene(mps);
//            });
//            singlePlayer.setOnAction(actionEvent -> {
//            primaryStage.setScene();
//            });
            frontPage.getChildren().add(welcomeLabel);
            frontPage.getChildren().add(twoPlayerLocal);
            frontPage.getChildren().add(twoPlayerLan);
            frontPage.getChildren().add(singlePlayer);
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
            p2Board.setShipstoInvisible();
            p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
            p2Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2fireEvent);
            p1Turn.getChildren().add(p2Board.getGameBoard());
            p1Turn.getChildren().add(p1Board.getGameBoard());
            p1Turn.setAlignment(Pos.CENTER);
            p1Turn.setPadding(new Insets(10, 10, 10, 10));
            p1Turn.setSpacing(10);
        return new Scene(p1Turn, 400, 700);
        }

//////////Test//////////////////////
//
//
//
//        Button returnToPlayer1 = new Button("Player 1 turn");

//          Label multiplayerLabel = new Label("Choose connection type");
//
////        p1Turn.getChildren().add(p1Board.getGameBoard());
////        p1Turn.getChildren().add(p2Board.getGameBoard());
////        p1Turn.setAlignment(Pos.CENTER);
////        p1Turn.setPadding(new Insets(10, 10, 10, 10));
////        p1Turn.setSpacing(10);
//
//        Label testLable = new Label("You shoudl see me");
//
//        p2Turn.getChildren().add(returnToPlayer1);
//        p2Turn.getChildren().add(testLable);
//        p2Turn.getChildren().add(p1Board.getGameBoard());
//        p2Turn.getChildren().add(p2Board.getGameBoard());
//        p2Turn.setPadding(new Insets(10, 10, 10, 10));
//        p2Turn.setSpacing(10);
//
//
//        p1Turn.getChildren().add(p2Board.getGameBoard());
//        p1Turn.getChildren().add(p1Board.getGameBoard());
//        p1Turn.setAlignment(Pos.CENTER);
//        p1Turn.setPadding(new Insets(10, 10, 10, 10));
//        p1Turn.setSpacing(10);
//

    
//        FrontPage.getChildren().add(welcomeLabel);
//        FrontPage.getChildren().add(twoPlayerLocal);
//        FrontPage.getChildren().add(twoPlayerLan);
//        FrontPage.getChildren().add(singlePlayer);
//        FrontPage.setAlignment(Pos.CENTER);
//        FrontPage.setPadding(new Insets(10, 10, 10, 10));
//        FrontPage.setSpacing(40);
//
//
//        multiplayerSetup.getChildren().add(multiplayerLabel);
//        multiplayerSetup.setAlignment(Pos.CENTER);
//        multiplayerSetup.setPadding(new Insets(10, 10, 10, 10));
//        multiplayerSetup.setSpacing(10);
//        InetAddress inetAddress = InetAddress.getLocalHost();
//        Label hostIPAddress = new Label("Your IP Address is: " + inetAddress.getHostAddress());
//        multiplayerSetup.getChildren().add(hostIPAddress);
//        Button host = new Button("Host a new game");
//        host.setOnAction(actionEvent -> {
//            Server.start(52864);
//        });
//        multiplayerSetup.getChildren().add(host);
//        TextField port = new TextField();
//        port.setPromptText("Enter IP Address");
//        multiplayerSetup.getChildren().add(port);
//        Button join = new Button("Join game");
//        multiplayerSetup.getChildren().add(join);
//
//        EventHandler<MouseEvent> p1GameTurn = new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                p2Board.setShipstoInvisible();
//                primaryStage.setScene(scene1);
//                primaryStage.show();
//            }
//        };
//
//        returnToPlayer1.addEventFilter(MouseEvent.MOUSE_CLICKED, p1GameTurn);
//
//        EventHandler<MouseEvent> p2GameTurn = new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
////                Button returnToPlayer1 = new Button("Player 1 turn");
////                returnToPlayer1.addEventFilter(MouseEvent.MOUSE_CLICKED, p1GameTurn);
////                p2Turn.getChildren().add(returnToPlayer1);
////                p2Turn.getChildren().add(p1Board.getGameBoard());
////                p2Turn.getChildren().add(p2Board.getGameBoard());
////                p2Turn.setPadding(new Insets(10, 10, 10, 10));
////                p2Turn.setSpacing(10);
//                Node source = (Node) event.getSource();
//                Window theStage = source.getScene().getWindow();
//                System.out.println("p2GameTurn triggered");
//                theStage.setScene(p2TurnScene);
//                primaryStage.show();
//            }
//        };
//
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
                    p1Board.tileList.get(colX).get(colY).setOccupied();
                    String shipPath = "resources/boat.png";
                    Image shipImage = new Image(shipPath);
                    p1Board.rec[colX][colY].setFill(new ImagePattern(shipImage));
                    player1.setFleetNumber((player1.getFleetNumber() - 1));
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
                    p2Board.tileList.get(colX).get(colY).setOccupied();
                    String shipPath = "resources/boat.png";
                    Image shipImage = new Image(shipPath);
                    p2Board.rec[colX][colY].setFill(new ImagePattern(shipImage));
                    player2.setFleetNumber((player2.getFleetNumber() - 1));
                }
            }
        };
//
//        //P1 turn
//        Button startGameButton = new Button("Select to Start Game");
//        startGameButton.setOnAction(actionEvent -> {
//            startGameButton.setOnAction(actionEvent1 -> {
////                p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
////                p2Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2fireEvent);
////                //p1Turn.getChildren().add(p2Board.getGameBoard());
////                //p1Turn.getChildren().add(p1Board.getGameBoard());
//                p1Turn.getChildren().add(advanceTop2Turn);
////                p1Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
////                p1Turn.setAlignment(Pos.CENTER);
////                p1Turn.setPadding(new Insets(10, 10, 10, 10));
////                p1Turn.setSpacing(10);
////                p2Board.setShipstoInvisible();
//                primaryStage.setScene(scene1);
//                primaryStage.show();
//            });
//
//            Scene p2SelectShipScreen = new Scene(p2setup, 320, 640);
//            p2Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
//            primaryStage.setScene(p2SelectShipScreen);
//            primaryStage.show();
//        });
//
//        p1setup.getChildren().add(p1welcomeMessage);
//        p1setup.getChildren().add(startGameButton);
//        // This line carries over into play screen and allows p1 to place ships even when game has already started.
//        p1Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
//        p1setup.getChildren().add(p1Board.getGameBoard());
//
//////    BackgroundImage kraken = new BackgroundImage(new Image("", 320, 640, true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//////    welcome.setBackground(new Background(kraken));
//
//
//
//    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}