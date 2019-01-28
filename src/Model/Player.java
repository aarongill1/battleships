package Model;

public class Player {

    private String name;
    //  private int playerId;       Could be useful for the server side logic
    private Board board;
    private int fleetNumber = 4;

    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
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

    public void setName(){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}