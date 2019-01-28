package Model;

public class Gameover {

    private static Player winningPlayer;

    public static boolean isGameOver(Player player) {
        if (player.getShipsLeft() == 0) {
            return true;
        }
        return false;
    }

    public static Player getWinningPlayer() {
        return winningPlayer;
    }
    public static void setWinningPlayer(Player winningPlayer) {
        Gameover.winningPlayer = winningPlayer;
    }
}
