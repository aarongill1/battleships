package Model;

public class Tile {
    boolean hit;
    boolean occupied;
    boolean miss;

    public Tile() {
        hit = false;
        miss = false;
        occupied = false;
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

    public boolean getMiss(){
        return this.miss;
    }

}
