package Model;

import java.util.Arrays;

//Ship builder class
public class Ship {
    private String shipClass;
    private int shipSize;
    private int shipHits;
    private int [][] gridPosition;

// Idea is to (evetual future feature) take in a number of adjacent coordinates and save in the gridPosition multi-dim array
//    Would then allow ships to span multiple grid tiles

    public Ship(String shipClass, int shipSize, int posX, int posY) {

    this.shipClass = shipClass;
        this.shipSize = shipSize;

        this.gridPosition = new int [shipSize][2];
        for(int i = 0; i < shipSize; i++) {
            for(int j = 0; j < 1; j++) {
                gridPosition[i][j] = posX;
                gridPosition[i][j+1] = posY;
                System.out.println(i);
            }
        }
        System.out.println(Arrays.deepToString(gridPosition));

        shipHits = shipSize;
    }

    public int getHealth() {
        return shipHits;
    }

    public String getName() {
        return shipClass;
    }

    public int [][] getPosition() {
        return gridPosition;
    }

    public void setHealth(int hits) {
        this.shipHits = shipHits - hits;
    }

    public boolean isDestroyed() {
        if (this.getHealth()<1) {
            return true;
        }
        else{
            return false;
        }
    }

    public void reportIdentity() {
        System.out.println("Ship type is a " +getName() +" with " +getHealth() +" hull points remaining");
    }

    public void reportLocation() {
        System.out.println("The " +getName() +" is located at position " +getPosition());
    }

    public void reportHealth() {
        System.out.print(getName() + " has ");
        if (isDestroyed()) {
            System.out.println("been destroyed!");
        } else {
            System.out.println(getHealth() + " hull points remaining");
        }
    }

}
