package Main;

import Client.Client;
import Model.*;
import Server.Server;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends Application {
    private static Stage guiStage;
    private static Client client1;
    private static Client client2;
    private static TextArea p1display = new TextArea();
    private static TextArea p2display = new TextArea();

    public static Stage getGuiStage(){
        return guiStage;
    }
    ////////Main.Game Objects creation////////
    private static Board p1Board = new Board(8, 30);
    private static Board p2Board = new Board(8, 30);
    private static Board p1Opponent = new Board(8, 30);
    private static Board p2Opponent = new Board(8, 30);
    private static Kraken p1Kraken = new Kraken();
    private static Kraken p2Kraken = new Kraken();
    private static Player player1 = new Player(null, p1Board);
    private static Player player2 = new Player(null, p2Board);
    public static Button endP1Turn = new Button("End Turn");
    public static Button endP2Turn = new Button("End Turn");

    public static Icons gameIcons = new Icons();

    public static Button advanceTop2Setup = new Button("Click when finished");
    public static Button startGame = new Button("Click to start game!");
    public static Button quitGame = new Button("Quit game and return to Main Menu");
    public static Button backToHome = new Button("Back to Main Menu");


    public static TextField p1nameInput = new TextField();
    public static TextField p2nameInput = new TextField();

    boolean p2loaded = false;
    boolean p2updated = false;

    final static String computer = "Computer";
    private static String greet1 = "Welcome to Battleships";
    private static String greet2 = "Enter your name, Kräken Kommander!";
    private static String prompt1 = "It is your turn, Kommander ";
    private static String prompt2 = "Redeploy this ship elsewhere, Kommander?";


/////////////This alert block is now unused//////////////
//    private void showDoubleShipAlert(){
//        Alert shipAlreadyPlacedAlert = new Alert(Alert.AlertType.INFORMATION);
//        shipAlreadyPlacedAlert.setTitle("Dumbass! ");
//        shipAlreadyPlacedAlert.setHeaderText("Yes, you are!");
//        shipAlreadyPlacedAlert.setContentText("You've already placed a ship there!");
//        shipAlreadyPlacedAlert.showAndWait();
//    }

    private static void removeShipAlertP1(int colX, int colY) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove ship?");
        alert.setContentText(prompt2);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            p1Board.tileList.get(colX).get(colY).isOccupied(false);
            p1Board.rec[colX][colY].setFill(null);
            player1.setFleetNumber((player1.getFleetNumber() + 1));
        }
    }

    private static void removeShipAlertP2(int colX, int colY) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove ship?");
        alert.setContentText(prompt2);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            p2Board.tileList.get(colX).get(colY).isOccupied(false);
            p2Board.rec[colX][colY].setFill(null);
            player2.setFleetNumber((player2.getFleetNumber() + 1));
        }
    }

    private static void showDuplicateMoveAlert(){
        Alert shipAlreadyPlacedAlert = new Alert(Alert.AlertType.INFORMATION);
        shipAlreadyPlacedAlert.setTitle("Dumbass! ");
        shipAlreadyPlacedAlert.setHeaderText("Yes, you are!");
        shipAlreadyPlacedAlert.setContentText("You've already fired in this square!");
        shipAlreadyPlacedAlert.showAndWait();
    }

    private static void showDuplicateKrakenAlert(){
        Alert krakenAlreadyReleasedAlert = new Alert(Alert.AlertType.INFORMATION);
        krakenAlreadyReleasedAlert.setTitle("Dumbass! ");
        krakenAlreadyReleasedAlert.setHeaderText("Yes, you are!");
        krakenAlreadyReleasedAlert.setContentText("You've already released the Kraken!");
        krakenAlreadyReleasedAlert.showAndWait();
    }

    private static void resetPlayerandBoard() {
        player1.resetPlayer();
        player2.resetPlayer();
        p1Board.resetBoard();
        p2Board.resetBoard();
        p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1fireEvent);
        p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2fireEvent);
        p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1ComputerfireEvent);
    }

    private static void areYouSureYouWantToQuitAlert() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit game?");
        alert.setContentText("Are you sure you want to quit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            resetPlayerandBoard();
            guiStage.setScene(createMainMenu());
            guiStage.show();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Very important assigns the game static stage object to the primaryStage - needed to reference stage directly
        guiStage = primaryStage;
        primaryStage.setTitle("Release the Kraken");
        primaryStage.setResizable(false);
        primaryStage.setScene(createMainMenu());
        primaryStage.show();
    }

    public static Scene createMainMenu(){
        VBox frontPage = new VBox();
        Label welcomeLabel = new Label(greet1);
        Label welcomeLabelPt2 = new Label("Kräken Edition");
        welcomeLabel.setTextFill(Color.WHITE);
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        welcomeLabelPt2.setTextFill(Color.WHITE);
        welcomeLabelPt2.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        Button twoPlayerLocal = new Button("2 Player Local");
        Button twoPlayerLan = new Button("2 Player LAN");
        Button singlePlayer = new Button("Single Player");
//        !!Single Player set to disabled - remove when implement single player feature!!
//        singlePlayer.setDisable(true);
        twoPlayerLocal.setOnAction(actionEvent -> {
            guiStage.setScene(createP1Setup());
            guiStage.show();
        });
        twoPlayerLan.setOnAction(actionEvent -> {
            guiStage.setScene(createMPSetup());
            guiStage.show();
        });

        singlePlayer.setOnAction(actionEvent -> {
            guiStage.setScene(createP1Setup());
            player2.setName(computer);
            guiStage.show();
        });

        BackgroundImage menu = new BackgroundImage(
                new Image("resources/radar.jpg", 420, 700, true, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        frontPage.setBackground(new Background(menu));
        frontPage.getChildren().add(welcomeLabel);
        frontPage.getChildren().add(welcomeLabelPt2);
        frontPage.getChildren().add(twoPlayerLocal);
        frontPage.getChildren().add(twoPlayerLan);
        frontPage.getChildren().add(singlePlayer);
        frontPage.setAlignment(Pos.CENTER);
        frontPage.setPadding(new Insets(10, 10, 10, 10));
        frontPage.setSpacing(10);
        return new Scene(frontPage, 400, 700);
    }

    public static Scene createP1Setup(){
        VBox p1setup = new VBox();
        Label p1welcomeMessage = new Label(greet1);
        Label p1placeShipsA = new Label("Ships left to place: ");
        Label p1placeShipsB = new Label("" +player1.getFleetNumber());
        p1nameInput.setText("");
        p1nameInput.setPromptText(greet2);
        p1nameInput.setFocusTraversable(false);
        advanceTop2Setup.setDisable(true);
        p1nameInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (player1.getFleetNumber()>0)
                    advanceTop2Setup.setDisable(true);
                else if (t1.equals(""))
                    advanceTop2Setup.setDisable(true);
                else
                    advanceTop2Setup.setDisable(false);
            }
        });

        player1.fleetNumberProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                p1placeShipsB.setText(new Integer(player1.getFleetNumber()).toString());
            }
        });

        advanceTop2Setup.setOnAction(actionEvent ->{
            player1.setName(p1nameInput.getText());
            p1Board.placeKraken(gameIcons.getKrispinIcon());
//            guiStage.setScene(createP2Setup());
//            guiStage.show();
            if(player2.getName() == "Computer") {
                guiStage.setScene(createComputerP2Setup());
                guiStage.show();
            }
            else {
                guiStage.setScene(createP2Setup());
                guiStage.show();
            }
        });
////////Test for MP gameplay///////////////
        backToHome.setOnAction(actionEvent -> {
//            String temp = p1Board.populateBoard();
            resetPlayerandBoard();
            p2Board.resetBoard();
//            p1Board.drawBoard(temp);
            guiStage.setScene(createMainMenu());
        });

        p1Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
        HBox p1placeShips = new HBox(p1placeShipsA, p1placeShipsB);
        p1placeShips.setAlignment(Pos.CENTER);
        p1setup.getChildren().add(p1welcomeMessage);
        p1setup.getChildren().add(p1nameInput);
        p1setup.getChildren().add(p1Board.getGameBoard());
        p1setup.getChildren().add(p1placeShips);
        p1setup.getChildren().add(advanceTop2Setup);
        p1setup.getChildren().add(backToHome);
        p1setup.setAlignment(Pos.CENTER);
        p1setup.setPadding(new Insets(10, 10, 10, 10));
        p1setup.setSpacing(10);
        return new Scene(p1setup, 400, 700);
    }

    public static Scene createP2Setup(){
        VBox p2setup = new VBox();
        Label p2welcomeMessage = new Label(greet1);
        Label p2placeShipsA = new Label("Ships left to place: ");
        Label p2placeShipsB = new Label("" +player2.getFleetNumber());
        p2nameInput.setText("");
        p2nameInput.setPromptText(greet2);
        p2nameInput.setFocusTraversable(false);
        startGame.setDisable(true);
        p2nameInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (player2.getFleetNumber()>0)
                    startGame.setDisable(true);
                else if (t1.equals(""))
                    startGame.setDisable(true);
                else
                    startGame.setDisable(false);
            }
        });

        player2.fleetNumberProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                p2placeShipsB.setText(new Integer(player2.getFleetNumber()).toString());
            }
        });

        startGame.setOnAction(actionEvent -> {
            player2.setName(p2nameInput.getText());
            p2Board.placeKraken(gameIcons.getKrispinIcon());
            guiStage.setScene(endOfP2Turn());
            guiStage.show();
        });

        backToHome.setOnAction(actionEvent -> {
            resetPlayerandBoard();
            guiStage.setScene(createMainMenu());
        });

        p2Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
        HBox p2placeShips = new HBox(p2placeShipsA, p2placeShipsB);
        p2placeShips.setAlignment(Pos.CENTER);
        p2setup.getChildren().add(p2welcomeMessage);
        p2setup.getChildren().add(p2nameInput);
        p2setup.getChildren().add(p2Board.getGameBoard());
        p2setup.getChildren().add(p2placeShips);
        p2setup.getChildren().add(startGame);
        p2setup.getChildren().add(backToHome);
        p2setup.setAlignment(Pos.CENTER);
        p2setup.setPadding(new Insets(10, 10, 10, 10));
        p2setup.setSpacing(10);
        return new Scene(p2setup, 400, 700);
    }

    public static Scene createComputerP2Setup(){
        VBox computerSetup = new VBox();
        Label computerSelection = new Label("Computer selection is automatic - select Start Game");
        p2nameInput.setFocusTraversable(false);
        p2nameInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (player2.getFleetNumber()>0)
                    startGame.setDisable(false);
            }
        });
        startGame.setOnAction(actionEvent -> {
            guiStage.setScene(createP1Turn());
            guiStage.show();
        });

        backToHome.setOnAction(actionEvent -> {
            resetPlayerandBoard();
            guiStage.setScene(createMainMenu());
        });
        computerSetup.getChildren().add(backToHome);
        computerSetup.getChildren().add(startGame);
        computerSetup.getChildren().add(computerSelection);
        p2Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2ComputerPlaceShips);
//        computerSetup.getChildren().add(p2Board.getGameBoard());
        computerSetup.setAlignment(Pos.CENTER);
        computerSetup.setPadding(new Insets(10, 10, 10, 10));
        computerSetup.setSpacing(10);
        return new Scene(computerSetup, 400, 700);
    }

    public static Scene createP1Turn(){
        VBox p1Turn = new VBox();
        Label p1TurnLabel = new Label(prompt1 + player1.getName());
        p1Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
        p1Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1fireEvent);
        p1Board.setShipstoVisible(gameIcons.getShipIcon());
        p2Board.setShipstoInvisible();
        p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
        p2Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2fireEvent);
        p1Turn.getChildren().add(quitGame);
        p1Turn.getChildren().add(p1TurnLabel);
        p1Turn.getChildren().add(p2Board.getGameBoard());
        p1Turn.getChildren().add(p1Board.getGameBoard());
        if(p2Kraken.isReleased()){
            p2Kraken.krakenSmash(p2Board, gameIcons.getDestroyedIcon(), gameIcons.getKrispinIcon(), player2);
        }
        endP1Turn.setOnAction(actionEvent -> {
            guiStage.setScene(endOfP1Turn());
            guiStage.show();
        });
        quitGame.setOnAction(actionEvent -> {
            areYouSureYouWantToQuitAlert();
        });
        endP1Turn.setDisable(true);
        p1Turn.getChildren().add(endP1Turn);
        p1Turn.setAlignment(Pos.CENTER);
        p1Turn.setPadding(new Insets(10, 10, 10, 10));
        p1Turn.setSpacing(10);
        return new Scene(p1Turn, 400, 700);
    }

    public static Scene createP2Turn(){
        VBox p2Turn = new VBox();
        Label p2TurnLabel = new Label(prompt1 + player2.getName());
        p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
        p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2fireEvent);
        p1Board.setShipstoInvisible();
        if(player2.getName() != "Computer") {
            p2Board.setShipstoVisible(gameIcons.getShipIcon());
        }
        p1Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
        if(player2.getName() == "Computer") {
            p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2ComputerPlaceShips);
            p1Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p1ComputerfireEvent);
        }
        else
        p1Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p1fireEvent);
        p2Turn.getChildren().add(quitGame);
        p2Turn.getChildren().add(p2TurnLabel);
        p2Turn.getChildren().add(p1Board.getGameBoard());
        p2Turn.getChildren().add(p2Board.getGameBoard());
        if(p1Kraken.isReleased()){
            p1Kraken.krakenSmash(p1Board, gameIcons.getDestroyedIcon(), gameIcons.getKrispinIcon(), player1);
        }
        endP2Turn.setOnAction(actionEvent -> {
            guiStage.setScene(endOfP2Turn());
            guiStage.show();
        });
        quitGame.setOnAction(actionEvent -> {
            areYouSureYouWantToQuitAlert();
        });
        endP2Turn.setDisable(true);
        p2Turn.getChildren().add(endP2Turn);
        p2Turn.setAlignment(Pos.CENTER);
        p2Turn.setPadding(new Insets(10, 10, 10, 10));
        p2Turn.setSpacing(10);
        return new Scene(p2Turn, 400, 700);
    }

    public static Scene endOfP1Turn() {
        VBox p1Intermission = new VBox();
        Button advanceToP2Go = new Button("Start " + player2.getName() + "'s Turn");
        advanceToP2Go.setOnAction(actionEvent -> {
            guiStage.setScene(createP2Turn());
            guiStage.show();
        });
        p1Intermission.getChildren().add(advanceToP2Go);
        p1Intermission.setAlignment(Pos.CENTER);
        p1Intermission.setPadding(new Insets(10, 10, 10, 10));
        p1Intermission.setSpacing(10);
        return new Scene(p1Intermission, 400, 700);
    }

    public static Scene endOfP2Turn() {
        VBox p2Intermission = new VBox();
        Button advanceToP1Go = new Button("Start " + player1.getName() + "'s Turn");
        advanceToP1Go.setOnAction(actionEvent -> {
            guiStage.setScene(createP1Turn());
            guiStage.show();
        });
        p2Intermission.getChildren().add(advanceToP1Go);
        p2Intermission.setAlignment(Pos.CENTER);
        p2Intermission.setPadding(new Insets(10, 10, 10, 10));
        p2Intermission.setSpacing(10);
        return new Scene(p2Intermission, 400, 700);
    }

    public static Scene createMPSetup(){
        VBox mpSetup = new VBox();
        Label multiplayerLabel = new Label("Choose connection type");
        multiplayerLabel.setTextFill(Color.WHITE);
        multiplayerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        multiplayerLabel.setLineSpacing(30);
        BackgroundImage menu = new BackgroundImage(
                new Image("resources/mpsetup.jpg", 420, 700, true, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        mpSetup.setBackground(new Background(menu));
        mpSetup.getChildren().add(multiplayerLabel);
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            Label hostIPAddress = new Label("Your IP Address is: " + ip);
            hostIPAddress.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            hostIPAddress.setTextFill(Color.WHITE);
            mpSetup.getChildren().add(hostIPAddress);
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
        Button host = new Button("Host a new game");
        host.setOnAction(actionEvent -> {
            Server.start(52864);
            client1 = new Client("localhost", 52864);
            guiStage.setScene(createMP1Setup());
        });
        mpSetup.getChildren().add(host);
        TextField port = new TextField();
        port.setPromptText("Enter IP Address");
        mpSetup.getChildren().add(port);
        Button join = new Button("Join game");
        join.setOnAction(actionEvent ->{
            String IP = port.getText();
            client2 = new Client(IP, 52864);
            guiStage.setScene(createMP2Setup());

        });

        backToHome.setOnAction(actionEvent -> {
            guiStage.setScene(createMainMenu());
        });

        mpSetup.getChildren().add(join);
        mpSetup.getChildren().add(backToHome);
        mpSetup.setAlignment(Pos.CENTER);
        mpSetup.setPadding(new Insets(30, 10, 30, 10));
        mpSetup.setSpacing(10);
        return new Scene(mpSetup, 400,700);
    }

    // Host can set up their board
    public static Scene createMP1Setup(){
        VBox p1setup = new VBox();
        Label p1welcomeMessage = new Label("Welcome to Battleships - Select your ship locations");
        Label p1nameLabel = new Label("Enter your name!");
        p1setup.getChildren().add(p1nameLabel);
        TextField p1nameInput = new TextField();
        p1setup.getChildren().add(p1nameInput);
        Button advanceTop2Setup = new Button("Click when finished");
        advanceTop2Setup.setOnAction(actionEvent ->{
            client1.setName(p1nameInput.getText());
            guiStage.setScene(createMPp1View());
            guiStage.show();
        });
        Button backToHome = new Button("Home");
        backToHome.setOnAction(actionEvent -> {
            resetPlayerandBoard();
            client1.disconnect();
            Server.stop();
            guiStage.setScene(createMPSetup());
        });
        p1setup.getChildren().add(backToHome);

        p1Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
        p1setup.getChildren().add(p1Board.getGameBoard());
        p1setup.getChildren().add(p1welcomeMessage);
        p1setup.getChildren().add(advanceTop2Setup);
        p1setup.setAlignment(Pos.CENTER);
        p1setup.setPadding(new Insets(10, 10, 10, 10));
        p1setup.setSpacing(10);
        return new Scene(p1setup, 400, 700);
    }

    // Client can set up their board
    public static Scene createMP2Setup(){
        VBox p2setup = new VBox();
        Label p2welcomeMessage = new Label("Welcome to Battleships - Select your ship locations");
        Label p2nameLabel = new Label("Enter your name!");
        p2setup.getChildren().add(p2nameLabel);
        TextField p2nameInput = new TextField();
        p2setup.getChildren().add(p2nameInput);
        Button advanceToGame = new Button("Click when finished");
        advanceToGame.setOnAction(actionEvent ->{
            client2.setName(p2nameInput.getText());
            guiStage.setScene(createMPp2View());
            guiStage.show();
        });

        backToHome.setOnAction(actionEvent -> {
            resetPlayerandBoard();
            guiStage.setScene(createMPSetup());
        });
        p2setup.getChildren().add(backToHome);

        p2Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
        p2setup.getChildren().add(p2Board.getGameBoard());
        p2setup.getChildren().add(p2welcomeMessage);
        p2setup.getChildren().add(advanceToGame);
        p2setup.setAlignment(Pos.CENTER);
        p2setup.setPadding(new Insets(10, 10, 10, 10));
        p2setup.setSpacing(10);
        return new Scene(p2setup, 400, 700);
    }

    // Host's view
    public static Scene createMPp1View(){
        VBox p1Turn = new VBox();
        HBox p1boardView = new HBox();
        p1Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
        p1Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1fireEvent);
        p2Opponent.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
        p2Opponent.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2opponentFireEvent);
        p1boardView.setSpacing(95);
        p1boardView.getChildren().add(p2Opponent.getGameBoard());
        p1boardView.getChildren().add(p1Board.getGameBoard());
        p1Turn.getChildren().add(p1boardView);
        p1display.setEditable(false);
        p1display.setPrefHeight(300);
        TextField p1input = new TextField();
        p1input.setMaxHeight(100);
        p1input.setMaxWidth(500);

        Button p1Send = new Button("Send");
        p1Send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                client1.send(p1input.getText());
                p1input.setText("");
            }
        });

        Button refresh = new Button("Refresh");
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String coordinates2 = p1Board.populateBoard();
                System.out.println("p1 ship coordinates " + coordinates2);
                client1.p1sendShip(coordinates2);
            }
        });

        p1Turn.getChildren().add(p1display);
        p1Turn.getChildren().add(p1input);
        p1Turn.getChildren().add(p1Send);
        p1Turn.getChildren().add(refresh);
        p1Turn.setAlignment(Pos.CENTER);
        p1Turn.setPadding(new Insets(10, 10, 10, 10));
        p1Turn.setSpacing(10);
        return new Scene(p1Turn, 600, 700);
    }

    // Client's view
    public static Scene createMPp2View(){
        VBox p2Turn = new VBox();
        HBox p2BoardView = new HBox();
        p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
        p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2fireEvent);
        p1Opponent.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
        p1Opponent.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p1opponentFireEvent);
        p2BoardView.setSpacing(95);
        p2BoardView.getChildren().add(p1Opponent.getGameBoard());
        p2BoardView.getChildren().add(p2Board.getGameBoard());
        p2Turn.getChildren().add(p2BoardView);
        p2display.setEditable(false);
        p2display.setPrefHeight(300);
        TextField p2input = new TextField();
        p2input.setMaxHeight(100);
        p2input.setMaxWidth(500);


        String coordinates = p2Board.populateBoard();
        System.out.println("p2 ship coordinates " + coordinates);
        client2.p2sendShip(coordinates);


        Button p2Send = new Button("Send");
        p2Send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                client2.send(p2input.getText());
                p2input.setText("");
            }
        });

        p2Turn.getChildren().add(p2display);
        p2Turn.getChildren().add(p2input);
        p2Turn.getChildren().add(p2Send);
        p2Turn.setAlignment(Pos.CENTER);
        p2Turn.setPadding(new Insets(10, 10, 10, 10));
        p2Turn.setSpacing(10);
        return new Scene(p2Turn, 600, 700);
    }

    public static Scene createGameOver(){
        VBox gameOver = new VBox();
        Label welcomeLabel = new Label("GAME OVER MAN, GAME OVER!\n"
        + Gameover.getWinningPlayer().getName().toUpperCase() + " IS THE WINNER!"
        );

        Button anotherGame = new Button("Play again?");
        anotherGame.setOnAction(actionEvent -> {
            resetPlayerandBoard();
            guiStage.setScene(createMainMenu());
            guiStage.show();
        });
        gameOver.getChildren().add(welcomeLabel);
        gameOver.getChildren().add(anotherGame);
        gameOver.setAlignment(Pos.CENTER);
        gameOver.setPadding(new Insets(10, 10, 10, 10));
        gameOver.setSpacing(10);
        return new Scene(gameOver, 400, 700);
    }

    public static Scene createMpGameOver(){
        VBox gameOver = new VBox();
        Label welcomeLabel = new Label("GAME OVER MAN, GAME OVER!\n");
        Button anotherGame = new Button("Play again?");
        anotherGame.setOnAction(actionEvent -> {
            resetPlayerandBoard();
            guiStage.setScene(createMainMenu());
            guiStage.show();
        });

        Button exit = new Button("Exit");
        exit.setOnAction(actionEvent ->{
            guiStage.close();
        });

        gameOver.getChildren().add(welcomeLabel);
        gameOver.getChildren().add(anotherGame);
        gameOver.setAlignment(Pos.CENTER);
        gameOver.setPadding(new Insets(10, 10, 10, 10));
        gameOver.setSpacing(10);
        return new Scene(gameOver, 400, 700);
    }

    // Fire events

    public static EventHandler<MouseEvent> p2fireEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            double posX = me.getX();
            double posY = me.getY();
            boolean validMove = false;
            int colX = (int) (posX / p2Board.getRectWidth());
            int colY = (int) (posY / p2Board.getRectWidth());
            p2Board.tileList.get(colX).get(colY).fire();
            if (p2Board.tileList.get(colX).get(colY).isOccupied()) {
                p2Board.rec[colX][colY].setFill(gameIcons.getHitIcon());
                player2.setShipsLeft(player2.getShipsLeft() - 1);
                p2Board.tileList.get(colX).get(colY).setHit(true);
                validMove = true;
                if (Gameover.isGameOver(player2)) {
                    Gameover.setWinningPlayer(player1);
                    guiStage.setScene(createGameOver());
                    guiStage.show();
                }
            }
            else if (p2Board.tileList.get(colX).get(colY).getMiss() || p2Board.tileList.get(colX).get(colY).isHit()) {
                showDuplicateMoveAlert();
            }
            else if(p2Board.tileList.get(colX).get(colY).isKraken()&& p2Kraken.isReleased()) {
                showDuplicateKrakenAlert();
            }
            else if(p2Board.tileList.get(colX).get(colY).isKraken()) {
                p2Board.rec[colX][colY].setFill(gameIcons.getKrispinIcon());
                p2Kraken.setReleased(true);
                validMove = true;
            } else {
                p2Board.rec[colX][colY].setFill(gameIcons.getMissIcon());
                p2Board.tileList.get(colX).get(colY).setMiss(true);
                validMove = true;
            }
            if (validMove) {
                p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1fireEvent);
                endP1Turn.setDisable(false);
            }
        }
    };

    public static EventHandler<MouseEvent> p1fireEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            double posX = me.getX();
            double posY = me.getY();
            boolean validMove = false;
            int colX = (int) (posX / p1Board.getRectWidth());
            int colY = (int) (posY / p1Board.getRectWidth());
            p1Board.tileList.get(colX).get(colY).fire();
            if (p1Board.tileList.get(colX).get(colY).isOccupied()) {
                p1Board.rec[colX][colY].setFill(gameIcons.getHitIcon());
                player1.setShipsLeft(player1.getShipsLeft() - 1);
                p1Board.tileList.get(colX).get(colY).setHit(true);
                validMove = true;
                if (Gameover.isGameOver(player1)) {
                    Gameover.setWinningPlayer(player2);
                    guiStage.setScene(createGameOver());
                    guiStage.show();
                }
            }
            else if (p1Board.tileList.get(colX).get(colY).getMiss() || p1Board.tileList.get(colX).get(colY).isHit()) {
                showDuplicateMoveAlert();
            }
            else if(p1Board.tileList.get(colX).get(colY).isKraken()&& p1Kraken.isReleased()) {
                showDuplicateKrakenAlert();
            }
            else if(p1Board.tileList.get(colX).get(colY).isKraken()) {
                p1Board.rec[colX][colY].setFill(gameIcons.getKrispinIcon());
                p1Kraken.setReleased(true);
                validMove = true;
            } else {
                p1Board.rec[colX][colY].setFill(gameIcons.getMissIcon());
                p1Board.tileList.get(colX).get(colY).setMiss(true);
                validMove = true;
            }
            if (validMove) {
                p1Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1fireEvent);
                endP2Turn.setDisable(false);
            }
        }
    };
  
    public static EventHandler<MouseEvent> p1ComputerfireEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {

            String missImagePath = "resources/miss.png";
            String hitImagePath = "resources/fire.png";
            Image missImage = new Image(missImagePath);
            Image hitImage = new Image(hitImagePath);
            boolean validMove = false;

            while(!validMove) {
                int randomNum1 = ThreadLocalRandom.current().nextInt(0, 8);
                int randomNum2 = ThreadLocalRandom.current().nextInt(0, 8);
                p1Board.tileList.get(randomNum1).get(randomNum2).fire();

                if (p1Board.tileList.get(randomNum1).get(randomNum2).getMiss() ||
                        p1Board.tileList.get(randomNum1).get(randomNum2).isHit()) {
                } else if (p1Board.tileList.get(randomNum1).get(randomNum2).isOccupied()) {
                    p1Board.rec[randomNum1][randomNum2].setFill(new ImagePattern(hitImage));
                    player1.setShipsLeft(player1.getShipsLeft() - 1);
                    p1Board.tileList.get(randomNum1).get(randomNum2).setHit(true);
                    validMove = true;
                    if (Gameover.isGameOver(player1)) {
                        Gameover.setWinningPlayer(player2);
                        guiStage.setScene(createGameOver());
                        guiStage.show();
                    }
                } else {
                    p1Board.rec[randomNum1][randomNum2].setFill(new ImagePattern(missImage));
                    p1Board.tileList.get(randomNum1).get(randomNum2).setMiss(true);
                    validMove = true;
                }
            }
                p1Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1ComputerfireEvent);
                endP2Turn.setDisable(false);
        }
    };



    // Place ships events

    public static EventHandler<MouseEvent> p1PlaceShips = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            double posX = me.getX();
            double posY = me.getY();
            int colX = (int) (posX / p1Board.getRectWidth());
            int colY = (int) (posY / p1Board.getRectWidth());
            if (p1Board.tileList.get(colX).get(colY).isOccupied())
                removeShipAlertP1(colX, colY);
            else {
                if (player1.getFleetNumber() != 0) {
                    if (!p1Board.tileList.get(colX).get(colY).isOccupied()) {
                        p1Board.tileList.get(colX).get(colY).setOccupied();
                        p1Board.rec[colX][colY].setFill(gameIcons.getShipIcon());
                        player1.setFleetNumber((player1.getFleetNumber() - 1));
                    }
                    else
                        removeShipAlertP1(colX, colY);
                }
            }
            if (!(p1nameInput.getText().equals("")) && (player1.getFleetNumber() == 0))
                advanceTop2Setup.setDisable(false);
            else
                advanceTop2Setup.setDisable(true);
        }
    };

    public static EventHandler<MouseEvent> p1PlaceKraken = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            double posX = me.getX();
            double posY = me.getY();
            int colX = (int) (posX / p1Board.getRectWidth());
            int colY = (int) (posY / p1Board.getRectWidth());
            p1Board.rec[colX][colY].setFill(gameIcons.getKrispinIcon());
        }
    };



    public static EventHandler<MouseEvent> p2PlaceShips = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            double posX = me.getX();
            double posY = me.getY();
            int colX = (int) (posX / p2Board.getRectWidth());
            int colY = (int) (posY / p2Board.getRectWidth());
            if (p2Board.tileList.get(colX).get(colY).isOccupied())
                removeShipAlertP2(colX, colY);
            else {
                if (player2.getFleetNumber() != 0) {
                    if (!p2Board.tileList.get(colX).get(colY).isOccupied()) {
                        p2Board.tileList.get(colX).get(colY).setOccupied();
                        p2Board.rec[colX][colY].setFill(gameIcons.getShipIcon());
                        player2.setFleetNumber((player2.getFleetNumber() - 1));
                    } else
                        removeShipAlertP2(colX, colY);
                }
            }
            if (!(p2nameInput.getText().equals("")) && (player2.getFleetNumber() == 0))
                startGame.setDisable(false);
            else
                startGame.setDisable(true);
        }
    };

    public static EventHandler<MouseEvent> p2ComputerPlaceShips = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            while (player2.getFleetNumber() != 0) {
                int randomNum1 = ThreadLocalRandom.current().nextInt(0, 8);
                int randomNum2 = ThreadLocalRandom.current().nextInt(0, 8);
                if (!p2Board.tileList.get(randomNum1).get(randomNum2).isOccupied()) {
                    p2Board.tileList.get(randomNum1).get(randomNum2).setOccupied();
                    //code to show computer selection
//                    String shipPath = "resources/boat.png";
//                    Image shipImage = new Image(shipPath);
//                    p2Board.rec[randomNum1][randomNum2].setFill(new ImagePattern(shipImage));
                    player2.setFleetNumber((player2.getFleetNumber() - 1));
                }
                    startGame.setDisable(false);
            }
        }
    };

    public static EventHandler<MouseEvent> p2opponentFireEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            double posX = me.getX();
            double posY = me.getY();
            String coordinates = new String();
            int colX = (int) (posX / p2Opponent.getRectWidth());
            int colY = (int) (posY / p2Board.getRectWidth());
            p2Opponent.tileList.get(colX).get(colY).fire();
            boolean validMove = false;

            if (p2Opponent.tileList.get(colX).get(colY).getMiss() ||
                    p2Opponent.tileList.get(colX).get(colY).isHit())
            { showDuplicateMoveAlert(); }
            else if (p2Opponent.tileList.get(colX).get(colY).isOccupied()) {
                p2Opponent.rec[colX][colY].setFill(gameIcons.getHitIcon());
                player2.setShipsLeft(player2.getShipsLeft() - 1);
                p2Opponent.tileList.get(colX).get(colY).setHit(true);
                coordinates += colX;
                coordinates += colY;
                client1.sendHitp2(coordinates);
                coordinates = "";
                p2Opponent.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2opponentFireEvent);
                validMove = true;
                if (Gameover.isGameOver(player2)) {
                    Gameover.setWinningPlayer(player1);
                    guiStage.setScene(createMpGameOver());
                    guiStage.show();
                }
            } else {
                p2Opponent.rec[colX][colY].setFill(gameIcons.getMissIcon());
                p2Opponent.tileList.get(colX).get(colY).setMiss(true);
                coordinates += colX;
                coordinates += colY;
                client1.sendMissp2(coordinates);
                coordinates = "";
                p2Opponent.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2opponentFireEvent);
                validMove = true;
            }
            if (validMove) {
                p2Opponent.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2opponentFireEvent);
                endP1Turn.setDisable(false);
            }
        }
    };

    public static EventHandler<MouseEvent> p1opponentFireEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {
            double posX = me.getX();
            double posY = me.getY();
            String coordinates = new String();
            int colX = (int) (posX / p1Opponent.getRectWidth());
            int colY = (int) (posY / p1Opponent.getRectWidth());
            p1Opponent.tileList.get(colX).get(colY).fire();
            boolean validMove = false;

            if (p1Opponent.tileList.get(colX).get(colY).getMiss() ||
                    p1Opponent.tileList.get(colX).get(colY).isHit())
            { showDuplicateMoveAlert(); }
            else if (p1Opponent.tileList.get(colX).get(colY).isOccupied()) {
                p1Opponent.rec[colX][colY].setFill(gameIcons.getHitIcon());
                player2.setShipsLeft(player2.getShipsLeft() - 1);
                p1Opponent.tileList.get(colX).get(colY).setHit(true);
                coordinates += colX;
                coordinates += colY;
                client2.sendHitp1(coordinates);
                coordinates = "";
                p1Opponent.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1opponentFireEvent);
                validMove = true;
                if (Gameover.isGameOver(player2)) {
                    Gameover.setWinningPlayer(player1);
                    guiStage.setScene(createMpGameOver());
                    guiStage.show();
                }
            } else {
                p1Opponent.rec[colX][colY].setFill(gameIcons.getMissIcon());
                p1Opponent.tileList.get(colX).get(colY).setMiss(true);
                coordinates += colX;
                coordinates += colY;
                client2.sendMissp1(coordinates);
                coordinates = "";
                p1Opponent.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1opponentFireEvent);
                validMove = true;
            }
            if (validMove) {
                p1Opponent.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1opponentFireEvent);
                endP1Turn.setDisable(false);
            }
        }
    };



    public static void p1printToConsole(String message){
        p1display.setText(p1display.getText() + message + "\n");
    }

    public static void p2printToConsole(String message){
        p2display.setText(p2display.getText() + message + "\n");
    }

    public static void p1UpdateShips(String shipCoordinates){
        p1Opponent.drawBoard(shipCoordinates, gameIcons.getShipIcon());
        p1Opponent.setShipstoInvisible();
    }

    public static void p2UpdateShips(String shipCoordinates){
        p2Opponent.drawBoard(shipCoordinates, gameIcons.getShipIcon());
        p2Opponent.setShipstoInvisible();
    }

    // Updates p1's board after move is made on p1opponent board
    public static void p1UpdateHits(String hitCoordinates){
        p1Board.drawBoard(hitCoordinates, gameIcons.getHitIcon());
        p2Opponent.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2opponentFireEvent);
    }

    // Updates p2's board after move is made on p2opponent board
    public static void p2UpdateHits(String hitCoordinates){
        p2Board.drawBoard(hitCoordinates, gameIcons.getHitIcon());
        p1Opponent.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p1opponentFireEvent);
    }

    //
    public static void p1UpdateMisses(String missCoordinates){
        p1Board.drawBoard(missCoordinates, gameIcons.getMissIcon());
        p2Opponent.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2opponentFireEvent);
    }

    //
    public static void p2UpdateMisses(String missCoordinates){
        p2Board.drawBoard(missCoordinates, gameIcons.getMissIcon());
        p1Opponent.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p1opponentFireEvent);
    }

    //
    public static void main(String[] args) {
        Application.launch(args);
    }
}