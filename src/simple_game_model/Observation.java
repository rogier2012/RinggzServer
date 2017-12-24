package simple_game_model;

import simple_game_model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

import static simple_game_model.Board.MAX_X;
import static simple_game_model.Board.MAX_Y;
import static simple_game_model.Territory.SIZES;

/**
 * Immutable observation of a game state
 */
public class Observation {

    /** Stores the pieces of the board */
    private final Piece[][][] board;
    /** Store the moves that have been made */
    private final List<Move> moves;

    /**
     * Constructs an observation of the state
     * @param state -- The state to be observed
     */
    public Observation(GameState state) {
        board = new Piece[MAX_X][MAX_Y][SIZES];
        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++)  {
                Piece[] pieces = state.getPieces(x, y);
                System.arraycopy(pieces, 0, this.board[x][y], 0, pieces.length);
            }
        }
        moves = state.getMoves();
    }

    /**
     * Observe a layer of the territory of the board
     * @param x -- The x coordinate of the territory
     * @param y -- The y coordinate of the territory
     * @param size -- The size of the ring (or layer to check)
     * @return the pieces located on the territory
     */
    public Piece observe(int x, int y, int size) {
        return this.board[x][y][size];
    }

    /**
     * @return A list of the moves that have been made on the board // TODO ---- only show last move?
     */
    public List<Move> getMoves() {
        return new ArrayList<>(this.moves);
    }
}
