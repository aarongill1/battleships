import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {

    @Test
    public void tileCoordinate() {

        Tile tile = new Tile();
        tile.setTileCoordinate("A1");
        assertEquals("A1", tile.getTileCoordinate());
    }

    @Test
    public void miss() {

        Tile tile = new Tile();
        tile.setMiss();
        assertTrue(tile.getMiss());
    }

    @Test
    public void hit() {

        Tile tile = new Tile();
        tile.setHit();
        assertTrue(tile.isHit());
    }

    @Test
    public void occupied() {

        Tile tile = new Tile();
        tile.setOccupied();
        assertTrue(tile.isOccupied());
    }

}