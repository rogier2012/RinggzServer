package simple_game_model.moves;

/**
 * Immutable class for start moves
 */
public class StartMove extends Move {
    /**
     * Constructs a new move
     *
     * @param x -- The x coordinate corresponding to the move
     * @param y -- The y coordinate corresponding to the move
     */
    public StartMove(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "Starting Move{" +
                "x=" + this.getX() +
                ", y=" + this.getY() +
                '}';
    }


}
