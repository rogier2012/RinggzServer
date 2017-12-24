package simple_game_model;

import simple_game_model.pieces.Base;
import simple_game_model.pieces.Piece;
import simple_game_model.pieces.Ring;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static simple_game_model.Board.MAX_X;
import static simple_game_model.Board.MAX_Y;

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

    /**
     * @return a readable String representation of this game state
     */
    String prpr() { // TODO -- better
        StringBuilder sb = new StringBuilder();
        final String TERRITORY   = " %s, %s, %s, %s |";
        final String B_TERRITORY = "        %s      |";

        for (int x = 0; x < MAX_X; x++) {
            sb.append("|");
            for (int y = 0; y < MAX_Y; y++) {
                Piece[] pieces = this.getPieces(x, y);
                String[] strPieces = new String[pieces.length];
                for (int i = 0; i < pieces.length; i++) {
                    strPieces[i] = prprPiece(pieces[i]);
                }
                if (pieces[0] instanceof Base) {
                    sb.append(String.format(B_TERRITORY, strPieces[0]));
                } else {
                    sb.append(String.format(TERRITORY, strPieces[0], strPieces[1], strPieces[2], strPieces[3]));
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Get the string representation of this pieces
     * @param piece -- The pieces to be represented
     * @return a readable string representation of the pieces
     */
    private static String prprPiece(Piece piece) {
        if (piece == null) return "  ";
        StringBuilder sb = new StringBuilder();
        switch (piece.getColor()) {
            case START:
                sb.append("S");
                break;
            case RED:
                sb.append("R");
                break;
            case YELLOW:
                sb.append("Y");
                break;
            case BLUE:
                sb.append("B");
                break;
            case GREEN:
                sb.append("G");
        }

        if (piece instanceof Ring) {
            sb.append("R");
        } else if (piece instanceof Base) {
            sb.append("B");
        }
        return sb.toString();
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
            this.board.putPiece(move.getX(), move.getY(), move.getPiece());
        } catch (Board.InvalidCoordinateException | Territory.OccupiedException | Territory.InvalidSizeException e) {
            throw new InvalidMoveException(move);
        }
        this.moves.push(move);
    }

    /**
     * Undo the last move that has been performed on the grid
     * @return the pieces that was removed
     */
    Piece undoMove()  {
        Move last = moves.pop();
        Piece piece = last.getPiece();
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
