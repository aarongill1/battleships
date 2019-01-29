package Model;

import javafx.scene.paint.ImagePattern;

public class Kraken {

    private boolean released;


    //Duplicate method from board - maybe move methods into single class?
    // 7 is a magic number - one less than the grid size
    public int getRandom(){
        return 0 + (int)(Math.random() * (7 - 0));
    }

    public void krakenSmash(Board board, ImagePattern destroyedFill, ImagePattern krispin, Player player){
        int colX = this.getRandom();
        int colY = this.getRandom();
        if(board.getTileList().get(colX).get(colY).isOccupied()){
            board.getTileList().get(colX).get(colY).setHit(true);
            board.rec[colX][colY].setFill(destroyedFill);
            player.setFleetNumber(player.getShipsLeft()-1);
        } else {
            board.rec[colX][colY].setFill(krispin);
        }
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }
}
