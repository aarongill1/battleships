package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Player {
    //  private int playerId;       Could be useful for the server side logic
    private String name;
    private Board board;
    private IntegerProperty fleetNumber;
    private int shipsLeft;

    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
        this.shipsLeft = getFleetNumber();
    }

    public Board getBoard() {
        return board;
    }

    public final int getFleetNumber() {
        if (fleetNumber != null)
            return fleetNumber.get();
        else
            return 4;
    }

    public final void setFleetNumber(int number) {
        this.fleetNumberProperty().set(number);
    }

    public final IntegerProperty fleetNumberProperty() {
        if (fleetNumber == null) {
            fleetNumber = new SimpleIntegerProperty(4);
        }
        return fleetNumber;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getShipsLeft() {
        return shipsLeft;
    }

    public void setShipsLeft(int shipsLeft) {
        this.shipsLeft = shipsLeft;
    }
    public void resetPlayer(){
        setFleetNumber(4);
        this.shipsLeft = getFleetNumber();
        this.name = "";
    }

}