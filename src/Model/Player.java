package Model;

public class Player {

    private String name;
    //  private int playerId;       Could be useful for the server side logic
    private Board board;
    private int fleetNumber = 4;
    private int shipsLeft;

    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
        this.shipsLeft = fleetNumber;
//        this.fleetNumber = board.getOccupied();
    }

    public Board getBoard() {
        return board;
    }

    public int getFleetNumber(){
        return this.fleetNumber;
    }

    public void setFleetNumber(int fleetNumber) {
        this.fleetNumber = fleetNumber;
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
}