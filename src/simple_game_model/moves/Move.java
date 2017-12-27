package simple_game_model.moves;

/**
 * Immutable move class
 */
public abstract class Move {

    /** The coordinates on which the move is performed */
    private final int x, y;

    /**
     * Constructs a new move
     * @param x -- The x coordinate corresponding to the move
     * @param y -- The y coordinate corresponding to the move
     */
    public Move(int x, int y)   {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        return x == move.x && y == move.y;
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

}