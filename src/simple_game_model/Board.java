package simple_game_model;

import simple_game_model.pieces.Base;
import simple_game_model.pieces.Piece;
import simple_game_model.pieces.Ring;

public class Board {

    /*
        Constants
     */

    /** Valid x values range from 0 to MAX_X (exclusive) */
    public static final int MAX_X = 5;
    /** Valid y values range from 0 to MAX_Y (exclusive) */
    public static final int MAX_Y = 5;

    /*
        Instance variables
     */

    /** A grid of territories */
    private final Territory[][] grid = new Territory[MAX_X][MAX_Y];

    /**
     * Default Constructors
     */
    Board() {
        for (int x = 0; x < MAX_X; x++)  {
            for (int y = 0; y < MAX_Y; y++) {
                this.grid[x][y] = new Territory();
            }
        }
    }

    /**
     * Constructs a deep copy of the board
     * @param board -- The board to be copied
     */
    Board(Board board) {
        for (int x = 0; x < MAX_X; x++)  {
            for (int y = 0; y < MAX_Y; y++) {
                this.grid[x][y] = new Territory(board.grid[x][y]);
            }
        }
    }

    /*
        Static Queries
     */

    private static boolean isValidX(int x)   {
        return x >= 0 && x < MAX_X;
    }

    private static boolean isValidY(int y)   {
        return y >= 0 && y < MAX_Y;
    }

    static boolean isValidTerritory(int x, int y)    {
        return isValidX(x) && isValidY(y);
    }

    /*
        Queries
     */

    /**
     * Get the pieces located on the specified territory
     * @param x -- The x coordinate of the territory
     * @param y -- The y coordinate of the territory
     * @return an array of pieces located on the territory
     * @throws InvalidCoordinateException -- Thrown when there is no territory on the specified coordinates
     */
    Piece[] getPieces(int x, int y) throws InvalidCoordinateException {
        if (isValidTerritory(x, y)) {
            return this.grid[x][y].getPieces();
        } else {
            throw new InvalidCoordinateException(x, y);
        }
    }

    /*
        Commands
     */

    /**
     * Put a pieces on the specified territory
     * @param x -- The x coordinate of the territory
     * @param y -- The y coordinate of the territory
     * @param piece -- The pieces to place on the territory
     * @throws InvalidCoordinateException -- Is thrown when there is no territory on the specified coordinates
     * @throws Territory.OccupiedException -- Is thrown when the pieces cannot be placed, as the territory is occupied by another pieces blocking it
     * @throws Territory.InvalidSizeException -- Is thrown when a ring is placed with an invalid size
     */
    void putPiece(int x, int y, Piece piece) throws InvalidCoordinateException, Territory.OccupiedException, Territory.InvalidSizeException {
        if (isValidTerritory(x, y)) {
            this.grid[x][y].putPiece(piece);
        } else {
            throw new InvalidCoordinateException(x, y);
        }
    }

    /**
     * Removes a ring from a territory
     * @param x -- The x coordinate of the territory
     * @param y -- The y coordinate of the territory
     * @param size -- The size of the ring to be removed
     * @return the ring that was removed
     * @throws InvalidCoordinateException -- Is thrown when there is no territory on the specified coordinates
     * @throws Territory.InvalidSizeException -- Is thrown when the size of the ring is invalid
     * @throws Territory.NoSuchPieceException -- Is thrown when the to-be-removed pieces does not exist on this territory
     */
    Ring undoRing(int x, int y, int size) throws InvalidCoordinateException, Territory.InvalidSizeException, Territory.NoSuchPieceException {
        if (isValidTerritory(x, y))   {
            return this.grid[x][y].removeRing(size);
        } else {
            throw new InvalidCoordinateException(x, y);
        }
    }

    /**
     * Removes a base from a territory
     * @param x -- The x coordinate of the territory
     * @param y -- The y coordinate of the territory
     * @return the base that was removed
     * @throws InvalidCoordinateException -- Is thrown when there is no territory on the specified coordinates
     * @throws Territory.NoSuchPieceException -- Is thrown when the to-be-removed pieces does not exist on this territory
     */
    Base undoBase(int x, int y) throws InvalidCoordinateException, Territory.NoSuchPieceException {
        if (isValidTerritory(x, y)) {
            return this.grid[x][y].removeBase();
        } else {
            throw new InvalidCoordinateException(x, y);
        }
    }

    /**
     * Clears all territories
     */
    void reset() {
        for (Territory[] territories : grid) {
            for (Territory territory : territories) {
                territory.reset();
            }
        }
    }

    /*
        Exceptions
     */

    class InvalidCoordinateException extends Exception   {
        InvalidCoordinateException(int x, int y) {
            super(String.format("Coordinate (%d,%d) is not valid!", x, y));
        }
    }


}
