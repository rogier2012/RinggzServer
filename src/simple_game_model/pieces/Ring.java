package simple_game_model.pieces;

import simple_game_model.Color;

/**
 * Immutable ring class
 */
public class Ring extends Piece {

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
     * @param o -- The object that should be compared with this pieces
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
