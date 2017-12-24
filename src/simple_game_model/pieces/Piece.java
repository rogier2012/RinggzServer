package simple_game_model.pieces;

import simple_game_model.Color;

/**
 * Immutable pieces class
 */
public class Piece {

    /** The color of the pieces */
    private final Color color;

    /**
     * Construct a new pieces
     * @param color -- The color of the pieces
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
