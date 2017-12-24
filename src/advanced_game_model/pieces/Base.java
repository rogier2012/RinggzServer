package advanced_game_model.pieces;

import advanced_game_model.Color;

import static advanced_game_model.Color.*;

/**
 * Immutable base class
 */
public class Base extends Piece {

    public static final Base RED_BASE       = new Base(RED);
    public static final Base YELLOW_BASE    = new Base(YELLOW);
    public static final Base BLUE_BASE      = new Base(BLUE);
    public static final Base GREEN_BASE     = new Base(GREEN);

    /**
     * Constructs a new base
     * @param color -- The color of the base
     */
    public Base(Color color) {
        super(color);
    }

    /**
     * Redefine equality of base pieces
     * @param o -- The object to compare this base piece to
     * @return a boolean indicating whether this base is equal to the given object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Base piece = (Base) o;

        return this.getColor() == piece.getColor();
    }

    @Override
    public String toString() {
        return "Base{" +
                this.getColor() +
                "}";
    }

}
