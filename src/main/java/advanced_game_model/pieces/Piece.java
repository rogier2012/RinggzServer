package advanced_game_model.pieces;

import advanced_game_model.Color;

/**
 * Immutable piece class
 */
public class Piece {

    /** The color of the piece */
    private final Color color;

    /**
     * Construct a new piece
     * @param color -- The color of the piece
     */
    public Piece(Color color) {
        this.color = color;
    }

    /*
        Getters
     */

    public Color getColor() {
        return color;
    }

}
