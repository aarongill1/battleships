import Model.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TileTest {

//    @Test
//    public void tileCoordinate() {
//
//        Tile tile = new Tile();
//        tile.setTileCoordinate("A1");
//        assertEquals("A1", tile.getTileCoordinate());
//    }

    @Test
    public void miss() {

        Tile tile = new Tile();
        tile.setMiss(true);
        assertTrue(tile.getMiss());
    }

    @Test
    public void hit() {

        Tile tile = new Tile();
        tile.setHit(true);
        assertTrue(tile.isHit());
    }

    @Test
    public void occupied() {

        Tile tile = new Tile();
        tile.setOccupied();
        assertTrue(tile.isOccupied());
    }

}