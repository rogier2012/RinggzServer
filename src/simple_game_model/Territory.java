package simple_game_model;

import simple_game_model.pieces.Base;
import simple_game_model.pieces.Piece;
import simple_game_model.pieces.Ring;

/**
 * Models one territory. Makes sure no pieces block each other
 */
public class Territory {

    /** The number of ring sizes in one territory */
    public static final int SIZES = 4;

    /** An array indicating which color occupies which ring size */
    private final Ring[] rings = new Ring[SIZES];
    /** Stores a base pieces if placed on this territory */
    private Base base;

    /*
        Constructors
     */

    /**
     * Default constructor
     */
    Territory() {
        this.base = null;
    }

    /**
     * Constructs a deep copy of the territory
     * @param territory -- The territory to be copied
     */
    Territory(Territory territory)   {
        this.base = territory.base;
        System.arraycopy(territory.rings, 0, this.rings, 0, SIZES);
    }

    /*
        Static queries
     */

    /**
     * Indicates whether a ring of this size would fit on this territory
     * @param size -- The size of the ring
     * @return a boolean indicating whether the size is valid or not
     */
    private static boolean isValidSize(int size)   {
        return size >= 0 && size < SIZES;
    }

    /*
        Queries
     */

    /**
     * Get the pieces on this territory
     * @return an array of length 1 with a base pieces if a base is present. Otherwise, return a copy of the rings (including empty slots)
     */
    Piece[] getPieces() {
        if (this.base != null) {
            return new Base[]{this.base};
        } else {
            Ring[] rings = new Ring[SIZES];
            System.arraycopy(this.rings, 0, rings, 0, SIZES);
            return rings;
        }
    }

    /**
     * @return a boolean indicating whether there are no pieces on this territory
     */
    boolean isEmpty()    {
        boolean noRings = true;
        for (int size = 0; size < SIZES; size++)    {
            noRings &= this.rings[size] == null;
        }
        return noRings && this.base == null;
    }

    /*
        Commands
     */

    /**
     * Puts a pieces on this territory
     * @param piece -- The pieces to put on this territory
     * @throws InvalidSizeException -- Is thrown when the size of the ring is not valid
     * @throws OccupiedException -- Is thrown when the pieces cannot be placed on this territory, as it is occupied by a blocking pieces
     */
    void putPiece(Piece piece) throws InvalidSizeException, OccupiedException {
        if (piece instanceof Base) {
            if (this.isEmpty()) {
                this.base = (Base) piece;
            } else {
                throw new OccupiedException(piece);
            }
        } else if (piece instanceof Ring) {
            int size = ((Ring) piece).getSize();
            if (isValidSize(size)) {
                if (this.rings[size] == null && this.base == null)   {
                    this.rings[size] = (Ring) piece;
                } else {
                    throw new OccupiedException(piece);
                }
            } else {
                throw new InvalidSizeException(size);
            }
        }
    }

    /**
     * Removes a ring from this territory
     * @param size -- The size of the ring to remove
     * @return the removed pieces
     * @throws InvalidSizeException -- Is thrown when the size of the ring to remove is not valid
     * @throws NoSuchPieceException -- Is thrown when the pieces to remove is non-existent on this territory
     */
    Ring removeRing(int size) throws InvalidSizeException, NoSuchPieceException {
        if (isValidSize(size))    {
            Ring ring = this.rings[size];
            if (ring != null) {
                this.rings[size] = null;
                return ring;
            } else {
                throw new NoSuchPieceException();
            }
        } else {
            throw new InvalidSizeException(size);
        }
    }

    /**
     * Removes a base from this territory
     * @return the removed pieces
     * @throws NoSuchPieceException -- Is thrown when the pieces to remove is non-existent on this territory
     */
    Base removeBase() throws NoSuchPieceException {
        Base base = this.base;
        if (base != null)    {
            this.base = null;
            return base;
        } else {
            throw new NoSuchPieceException();
        }
    }

    /**
     * Reset this territory
     */
    void reset() {
        this.base = null;
        for (int size = 0; size < SIZES; size++)    {
            this.rings[size] = null;
        }
    }

    /*
        Exception classes
     */

    /**
     * An exception that is thrown when a ring has an invalid size
     */
    class InvalidSizeException extends Exception    {
        InvalidSizeException(int size) {
            super(String.format("%d is not a valid ring size!", size));
        }
    }

    /**
     * An exception that is thrown when a pieces cannot be placed on a territory as it is occupied by another pieces blocking it
     */
    class OccupiedException extends Exception  {
        OccupiedException(Piece blocked) {
            super(String.format("Cannot place %s as it is blocked by another pieces!", blocked.toString()));
        }
    }

    /**
     * An exception that is thrown when a pieces is expected to be on a territory, but isn't.
     */
    class NoSuchPieceException extends Exception {
        NoSuchPieceException() {
            super("Could not find the right pieces to remove");
        }
    }


}
