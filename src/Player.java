import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player {

    private String name;
//  private int playerId;       Could be useful for the server side logic
    private Board board;
    private int fleetNumber;

    public Player(String name, Board board) {
        this.name = name;
        this.board = new Board();
        this.fleetNumber = board.getOccupied();
    }

    public Board getBoard() {
        return board;
    }

    public int getFleetNumber(){
        return this.fleetNumber;
    }

    public String getName(){
        return this.name;
    }

}
