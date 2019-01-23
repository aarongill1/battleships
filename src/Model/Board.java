package Model;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board {

    private int size;
    private Pane gameBoard;

    public Board() {

    }

    public Pane getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Pane gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Board(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Pane makeGrid(int gridSize) {

        double width = 30;
        Pane pane = new Pane();

        Rectangle[][] rec = new Rectangle[gridSize][gridSize];

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                rec[i][j] = new Rectangle();
                rec[i][j].setX(i * width);
                rec[i][j].setY(j * width);
                rec[i][j].setWidth(width);
                rec[i][j].setHeight(width);
                rec[i][j].setFill(null);
                rec[i][j].setStroke(Color.BLACK);
                pane.getChildren().add(rec[i][j]);
                pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent me) {
                        double posX = me.getX();
                        double posY = me.getY();

                        int colX = (int) (posX / width);
                        int colY = (int) (posY / width);

                        rec[colX][colY].setFill(Color.RED);
                        System.out.println("X coordinate is: " + colX);
                        System.out.println("Y coordinate is: " + colY);
                    }
                });
            }
        }
        return pane;
    }
}