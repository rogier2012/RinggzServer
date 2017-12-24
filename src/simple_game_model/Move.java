package simple_game_model;

import simple_game_model.pieces.Piece;

/**
 * Immutable move class
 */
public class Move {

    /** The coordinates on which the move is performed */
    private final int x, y;
    /** The pieces that is placed */
    private final Piece piece;

    /**
     * Constructs a new move
     * @param x -- The x coordinate corresponding to the move
     * @param y -- The y coordinate corresponding to the move
     * @param piece -- The pieces that is placed
     */
    public Move(int x, int y, Piece piece)   {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    /**
     * Redefine move equality
     * @param o -- The object with which this move should be compared
     * @return a boolean indicating whether this move is equal to the given object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (x != move.x) return false;
        if (y != move.y) return false;
        return piece != null ? piece.equals(move.piece) : move.piece == null;
    }

    @Override
    public String toString() {
        return "MoveSuggestion{" +
                "x=" + x +
                ", y=" + y +
                ", pieces=" + piece +
                '}';
    }

    /*
        Getters
     */

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPiece() {
        return piece;
    }
}