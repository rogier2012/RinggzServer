package advanced_game_model.pieces;

import advanced_game_model.Color;

import static advanced_game_model.Color.*;

/**
 * Immutable ring class
 */
public class Ring extends Piece {

    public static final Ring RED_RING0 = new Ring(RED, 0);
    public static final Ring RED_RING1 = new Ring(RED, 1);
    public static final Ring RED_RING2 = new Ring(RED, 2);
    public static final Ring RED_RING3 = new Ring(RED, 3);

    public static final Ring[] RED_RINGS = new Ring[]{RED_RING0, RED_RING1, RED_RING2, RED_RING3};

    public static final Ring YELLOW_RING0 = new Ring(YELLOW, 0);
    public static final Ring YELLOW_RING1 = new Ring(YELLOW, 1);
    public static final Ring YELLOW_RING2 = new Ring(YELLOW, 2);
    public static final Ring YELLOW_RING3 = new Ring(YELLOW, 3);

    public static final Ring[] YELLOW_RINGS = new Ring[]{YELLOW_RING0, YELLOW_RING1, YELLOW_RING2, YELLOW_RING3};

    public static final Ring BLUE_RING0 = new Ring(BLUE, 0);
    public static final Ring BLUE_RING1 = new Ring(BLUE, 1);
    public static final Ring BLUE_RING2 = new Ring(BLUE, 2);
    public static final Ring BLUE_RING3 = new Ring(BLUE, 3);

    public static final Ring[] BLUE_RINGS = new Ring[]{BLUE_RING0, BLUE_RING1, BLUE_RING2, BLUE_RING3};

    public static final Ring GREEN_RING0 = new Ring(GREEN, 0);
    public static final Ring GREEN_RING1 = new Ring(GREEN, 1);
    public static final Ring GREEN_RING2 = new Ring(GREEN, 2);
    public static final Ring GREEN_RING3 = new Ring(GREEN, 3);

    public static final Ring[] GREEN_RINGS = new Ring[]{GREEN_RING0, GREEN_RING1, GREEN_RING2, GREEN_RING3};

    /** The size of the ring */
    private final int size;

    /**
     * Construct a new ring
     * @param color -- The color of the ring
     * @param size -- The size of the ring
     */
    public Ring(Color color, int size) {
        super(color);
        this.size = size;
    }

    /**
     * Redefine equality of ring pieces
     * @param o -- The object that should be compared with this piece
     * @return a boolean indicating whether this ring is equal to the given object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ring piece = (Ring) o;

        return this.getColor() == piece.getColor() && this.getSize() == piece.getSize();
    }

    @Override
    public String toString() {
        return "Ring{" +
                this.getColor() +
                ", size=" + size +
                '}';
    }

    /*
        Getters
     */

    public int getSize() {
        return size;
    }

}
