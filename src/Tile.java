package org.openjfx;

public class Tile {
    boolean hit;
    boolean occupied;

    public Tile() {

        hit = false;
        occupied = false;
    }

    public void fire() {

        if (occupied && !hit) {
            System.out.println("HIT!!!");
        }
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

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
