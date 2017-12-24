package simple_game_model;

import simple_game_model.player.HumanPlayer;
import simple_game_model.player.Idiot;
import simple_game_model.player.Player;

public class PlayTest {

    public static void main(String[] args) {

        Player p1 = new HumanPlayer("Henk");
        Player p2 = new Idiot("Kees");

        Player[] players = new Player[]{p1, p2};

        try {
            Game game = new Game(players);
            game.play();
        } catch (Game.InvalidPlayerAmountException e) {
            e.printStackTrace();
        }



    }

}
