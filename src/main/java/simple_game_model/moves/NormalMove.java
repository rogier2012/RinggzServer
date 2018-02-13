package simple_game_model.moves;

import simple_game_model.pieces.Piece;

/**
 * Immutable class for non-starting moves
 */
public class NormalMove extends Move {

    /** The pieces that is placed */
    private final Piece piece;

    /**
     * Construct a new move
     * @param x -- The x coordinate of the territory where the move is performed
     * @param y -- The y coordinate of the territory where the move is performed
     * @param piece -- The piece to place
     */
    public NormalMove(int x, int y, Piece piece) {
        super(x, y);
        this.piece = piece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NormalMove that = (NormalMove) o;

        return piece != null ? piece.equals(that.piece) : that.piece == null;
    }

    @Override
    public String toString() {
        return "Move{" +
                "x=" + this.getX() +
                ", y=" + this.getY() +
                ", pieces=" + piece +
                '}';
    }

    /*
        Getters
     */

    public Piece getPiece() {
        return piece;
    }

}
