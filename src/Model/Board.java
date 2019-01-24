package Model;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Board {

    private int size;
    private Pane gameBoard;
    private int rectWidth;

    public Board(int size, int rectWidth) {
        this.size = size;
        this.rectWidth = rectWidth;
        this.setGameBoard(this.makeGrid(size));
    }

    public Pane getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Pane gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Pane makeGrid(int gridSize) {

        double width = rectWidth;
        Pane pane = new Pane();

        Rectangle[][] rec = new Rectangle[gridSize][gridSize];

        ArrayList<ArrayList<Tile>> tileList = new ArrayList<>();

        for (int i = 0; i < gridSize; i++) {
            ArrayList<Tile> list = new ArrayList<>();
            tileList.add(list);

            for (int j = 0; j < gridSize; j++) {
                rec[i][j] = new Rectangle();
                rec[i][j].setX(i * width);
                rec[i][j].setY(j * width);
                rec[i][j].setWidth(width);
                rec[i][j].setHeight(width);
                rec[i][j].setFill(null);
                rec[i][j].setStroke(Color.BLACK);
                pane.getChildren().add(rec[i][j]);
                tileList.get(i).add(new Tile());

                pane.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent me) {
                        double posX = me.getX();
                        double posY = me.getY();

                        int colX = (int) (posX / width);
                        int colY = (int) (posY / width);

                        System.out.println("X coordinate is: " + colX);
                        System.out.println("Y coordinate is: " + colY);

                        // hard-code ships on grid
                        tileList.get(0).get(1).setOccupied();
                        tileList.get(2).get(7).setOccupied();
                        tileList.get(3).get(6).setOccupied();
                        tileList.get(4).get(2).setOccupied();
                        tileList.get(5).get(1).setOccupied();

//                        Sample of a ship
//                        Ship ship1 = new Ship("Battleship", 4, new int [2][7]);
                        Ship ship1 = new Ship("Battleship", 1, 2, 7);

                        ship1.reportIdentity();
                        ship1.reportLocation();


                        tileList.get(colX).get(colY).fire();

                        javafx.scene.image.Image missImage = new Image("https://openclipart.org/image/2400px/svg_to_png/16155/milker-X-icon.png");
                        javafx.scene.image.Image hitImage = new Image("https://www.clipartmax.com/png/small/132-1328230_flame-free-icon-fire-icon.png");

                        if(tileList.get(colX).get(colY).occupied){

                            rec[colX][colY].setFill(new ImagePattern(hitImage));
                        }
                        else {

                            rec[colX][colY].setFill(new ImagePattern(missImage));
                        }
                    }
                });
            }
        }
        return pane;
    }
}