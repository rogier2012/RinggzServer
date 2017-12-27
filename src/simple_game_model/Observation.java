package simple_game_model;

import simple_game_model.moves.Move;
import simple_game_model.pieces.Base;
import simple_game_model.pieces.Piece;
import simple_game_model.pieces.Ring;

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
     * Observe a piece on a territory
     * @param x -- The x coordinate of the territory
     * @param y -- The y coordinate of the territory
     * @param size -- The size of the ring (or layer to check)
     * @return the piece located on this spot in the territory
     */
    public Piece observe(int x, int y, int size) {
        return this.board[x][y][size];
    }

    /**
     * @return a readable String representation of this game state
     */
    public String prpr() { // TODO -- better
        StringBuilder sb = new StringBuilder();
        final String TERRITORY   = " %s, %s, %s, %s |";
        final String B_TERRITORY = "        %s      |";

        for (int x = 0; x < MAX_X; x++) {
            sb.append("|");
            Pieces:
            for (int y = 0; y < MAX_Y; y++) {
                String[] strPieces = new String[SIZES];
                for (int size = 0; size < SIZES; size++) {
                    Piece piece = observe(x, y, size);
                    if (piece instanceof Base) {
                        sb.append(String.format(B_TERRITORY, prprPiece(piece)));
                        continue Pieces;
                    }
                    strPieces[size] = prprPiece(piece);
                }
                sb.append(String.format(TERRITORY, strPieces[0], strPieces[1], strPieces[2], strPieces[3]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Get the string representation of this pieces
     * @param piece -- The pieces to be represented
     * @return a readable string representation of the pieces
     */
    private static String prprPiece(Piece piece) {
        if (piece == null) return "  ";
        StringBuilder sb = new StringBuilder();
        switch (piece.getColor()) {
            case RED:
                sb.append("R");
                break;
            case YELLOW:
                sb.append("Y");
                break;
            case BLUE:
                sb.append("B");
                break;
            case GREEN:
                sb.append("G");
        }

        if (piece instanceof Ring) {
            sb.append("R");
        } else if (piece instanceof Base) {
            sb.append("B");
        }
        return sb.toString();
    }

    /**
     * @return A list of the moves that have been made on the board // TODO ---- only show last move?
     */
    public List<Move> getMoves() {
        return new ArrayList<>(this.moves);
    }
}
