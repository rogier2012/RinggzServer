package simple_game_model.player.computer;

import simple_game_model.Move;
import simple_game_model.Observation;
import simple_game_model.pieces.Piece;
import simple_game_model.player.Player;
import simple_game_model.player.input.MoveInput;
import simple_game_model.player.input.PlayerInput;

import java.util.Random;

import static simple_game_model.Board.MAX_X;
import static simple_game_model.Board.MAX_Y;

/**
 * Completely guesses all move parameters
 */
public class Idiot extends Player {

    private static final int[] XS = new int[MAX_X];
    private static final int[] YS = new int[MAX_Y];

    static {
        for (int x = 0; x < MAX_X; x++) {
            XS[x] = x;
        }
        for (int y = 0; y < MAX_Y; y++) {
            YS[y] = y;
        }
    }

    /**
     * Construct a new idiot
     */
    public Idiot(String name) {
        super("Idiot " + name);
    }

    @Override
    public PlayerInput decide(Observation observation) {
        Random r = new Random();
        int x = XS[r.nextInt(XS.length)];
        int y = YS[r.nextInt(YS.length)];
        Piece piece = this.getPieceSet().getPieces().get(r.nextInt(this.getPieceSet().getPieces().size()));
        return new MoveInput(new Move(x, y, piece));
    }

    @Override
    public void observe(Observation observation) {

    }
}
