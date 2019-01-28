package Model;

public class Gameover {

    public static boolean isGameOver(Player player) {
        if (player.getShipsLeft() == 0) {
            return true;
        }
        return false;
    }

}
