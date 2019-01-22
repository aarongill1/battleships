public class Tile {
    boolean hit;
    boolean occupied;
    boolean miss;
    private String tileCoordinate;


    public Tile() {
        tileCoordinate = "";
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

    public void setHit() {
        this.hit = true;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied() {
        this.occupied = true;
    }

    public void setTileCoordinate(String coordinate){
        this.tileCoordinate = coordinate;
    }

    public String getTileCoordinate(){
        return this.tileCoordinate;
    }

    public void setMiss(){
        this.miss = true;
    }

    public boolean getMiss(){
        return this.miss;
    }
}
