package Model;

public class Tile {
    boolean hit;
    boolean occupied;
    boolean miss;
    boolean kraken;

    public Tile() {
        hit = false;
        miss = false;
        occupied = false;
        kraken = false;
    }

    public void fire() {

        if (occupied && !hit) {
            System.out.println("HIT!!!");
        } else
            System.out.println("MISS!!");
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void isOccupied(boolean cmd){
        this.occupied = cmd;
    }

    public void setOccupied() {
        this.occupied = true;
    }

    public void setMiss(boolean miss){
        this.miss = miss;
    }

    public boolean isMiss() {
        return miss;
    }

    public boolean getMiss(){
        return this.miss;
    }

    public boolean isKraken() {
        return kraken;
    }

    public void setKraken(boolean kraken) {
        this.kraken = kraken;
    }
}
