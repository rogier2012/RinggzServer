package simple_game_model;

import simple_game_model.moves.Move;
import simple_game_model.moves.NormalMove;
import simple_game_model.pieces.Base;
import simple_game_model.pieces.Piece;
import simple_game_model.pieces.Ring;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static simple_game_model.Territory.SIZES;

class GameState {

    /*
        Instance variables
     */

    /** A board of territories */
    private final Board board;
    /** The moves that have been performed */
    private final Stack<Move> moves;

    /*
        Constructors
     */

    /**
     *  Default constructor
     */
    GameState() {
        this.board = new Board();
        this.moves = new Stack<>();
    }

    /**
     * Constructs a deep copy of the game state
     * @param gameState -- The game state to be copied
     */
    GameState(GameState gameState)   {
        this.board = new Board(gameState.board);
        this.moves = new Stack<>();
        this.moves.addAll(gameState.moves);
    }

    /*
        Queries
     */

    /**
     * Get a list of the moves that have been performed so far
     * @return a list of moves
     */
    List<Move> getMoves()    {
        if (moves.size() > 0) {
            return moves.subList(0, moves.size());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Get the last move that has been performed on the grid
     * @return the last move
     */
    Move getLastMove()   {
        if (moves.size() > 0) {
            return moves.peek();
        } else {
            return null;
        }
    }

    /**
     * Get the pieces on the specified territory
     * @param x -- The x coordinate of the territory
     * @param y -- The y coordinate of the territory
     * @return the pieces located on the territory
     */
    Piece[] getPieces(int x, int y) {
        try {
            return this.board.getPieces(x, y);
        } catch (Board.InvalidCoordinateException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
        Commands
     */

    /**
     * Performs a move
     * @param move -- The move to be performed
     * @throws InvalidMoveException -- Is thrown when the move cannot be performed
     */
    void doMove(Move move) throws InvalidMoveException {
        try {
            if (move instanceof NormalMove) {
                this.board.putPiece(move.getX(), move.getY(), ((NormalMove) move).getPiece());
            } else {
                Color[] colors = Color.values();
                for (int i = 0; i < colors.length; i++) {
                    this.board.putPiece(move.getX(), move.getY(), new Ring(colors[i], i));
                }
            }
        } catch (Board.InvalidCoordinateException | Territory.OccupiedException | Territory.InvalidSizeException e) {
            throw new InvalidMoveException(move);
        }
        this.moves.push(move);
    }

    /**
     * Undo the last move that has been performed on the grid
     * @return the pieces that was removed. (null when undoing starting move)
     */
    Piece undoMove()  {
        Move last = moves.pop();
        if (last instanceof NormalMove) {
            Piece piece = ((NormalMove) last).getPiece();
            if (piece instanceof Base) {
                try {
                    return this.board.undoBase(last.getX(), last.getY());
                } catch (Board.InvalidCoordinateException | Territory.NoSuchPieceException e) {
                    e.printStackTrace();
                }
            } else if (piece instanceof Ring) {
                try {
                    return this.board.undoRing(last.getX(), last.getY(), ((Ring) piece).getSize());
                } catch (Board.InvalidCoordinateException | Territory.InvalidSizeException | Territory.NoSuchPieceException e) {
                    e.printStackTrace();
                }
            }
            return piece;
        } else {
            board.reset(last.getX(), last.getY());
            return null;
        }
    }

    /*
        Exceptions
     */

    /**
     * Exception that is thrown when a move cannot be performed on the grid
     */
    class InvalidMoveException extends Exception {

        /**
         * Construct a new exception
         * @param move -- The move that could not be performed
         */
        InvalidMoveException(Move move) {
            super(String.format("Could not perform move: %s", move.toString()));
        }


    }


}
