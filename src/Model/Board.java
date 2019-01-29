package Model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Board {

    private int size;
    private Pane gameBoard;
    private int rectWidth;
    public ArrayList<ArrayList<Tile>> tileList;
    public Rectangle[][] rec;

    public Board(int size, int rectWidth) {
        this.size = size;
        this.rectWidth = rectWidth;
        this.setGameBoard(this.makeGrid(size));
    }

    public Rectangle[][] getRec() {
        return rec;
    }

    public void setRec(Rectangle[][] rec) {
        this.rec = rec;
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

    public int getRectWidth() {
        return rectWidth;
    }

    public void setRectWidth(int rectWidth) {
        this.rectWidth = rectWidth;
    }

    public ArrayList<ArrayList<Tile>> getTileList() {
        return tileList;
    }

    public void setTileList(ArrayList<ArrayList<Tile>> tileList) {
        this.tileList = tileList;
    }

//    public ArrayList<Rectangle[][]> getOccupied(){
//        ArrayList<Rectangle[][]> result = this.tileList;
//        return result;
//    }

    public Pane makeGrid(int gridSize) {

        double width = rectWidth;
        Pane pane = new Pane();
        //important for ensuring no out of bounds errors
        pane.setMaxWidth(rectWidth*gridSize);

        rec = new Rectangle[gridSize][gridSize];

        tileList = new ArrayList<>();

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
            }
        }
        return pane;
    }

    public void setShipstoInvisible() {
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (this.getTileList().get(i).get(j).isOccupied() && !this.getTileList().get(i).get(j).isHit()) {
                    rec[i][j].setFill(null);
                }
            }
        }
    }

    public void setShipstoVisible(ImagePattern fill) {
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (this.getTileList().get(i).get(j).isOccupied() && !this.getTileList().get(i).get(j).isHit()) {
                    rec[i][j].setFill(fill);
                }
            }
        }
    }

    public String populateBoard() {
        String result = "";
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (this.getTileList().get(i).get(j).isOccupied()) {
                    result += i;
                    result +=j;
                }
            }
        }
        System.out.println(result);
        return result;
    }

    public void drawBoard(String coords, ImagePattern fill) {
        int[] nums = new int[coords.length()];
        for (int i = 0; i < coords.length(); i++) {
            nums[i] = Character.getNumericValue(coords.charAt(i));
        }
        for(int i = 0; i < nums.length; i+=2) {
            this.getTileList().get(nums[i]).get(nums[i+1]).setOccupied();
            rec[nums[i]][nums[i+1]].setFill(fill);
        }
    }

    public void resetBoard() {
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                    this.getTileList().get(i).get(j).isOccupied(false);
                    this.getTileList().get(i).get(j).setHit(false);
                    this.getTileList().get(i).get(j).setMiss(false);
                    rec[i][j].setFill(null);
                }
            }
        }
    }