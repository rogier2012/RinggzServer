package advanced_game_model;

import advanced_game_model.pieces.Base;
import advanced_game_model.pieces.Piece;
import advanced_game_model.pieces.Ring;

import java.util.BitSet;

public class BitBoard {


    /*
        A 5x5 grid of territories

        Each territory can hold either 4 rings or 1 base

        Each piece has one of 4 colors


        Each bitboard is a 32 bit int

        #### #### #### ####
        #### #### #### ####

        A bitboard is created for every type of piece

        5 x 5 = 25 bits are used to store the grid:

        @@@@ @@@@ @@@@ @@@@
        @@@@ @@@@ @### ####





     */

    private static final int MAX_X = 5; // TODO -- put in interface
    private static final int MAX_Y = 5;

    private static final int TERRITORIES = MAX_X * MAX_Y;

    private final BitSet redRing0Board;
    private final BitSet yelRing0Board;
    private final BitSet bluRing0Board;
    private final BitSet grnRing0Board;

    private final BitSet redRing1Board;
    private final BitSet yelRing1Board;
    private final BitSet bluRing1Board;
    private final BitSet grnRing1Board;

    private final BitSet redRing2Board;
    private final BitSet yelRing2Board;
    private final BitSet bluRing2Board;
    private final BitSet grnRing2Board;

    private final BitSet redRing3Board;
    private final BitSet yelRing3Board;
    private final BitSet bluRing3Board;
    private final BitSet grnRing3Board;

    private final BitSet redBaseBoard;
    private final BitSet yelBaseBoard;
    private final BitSet bluBaseBoard;
    private final BitSet grnBaseBoard;

    /** Zobrist hash of this board */
    private int hash;

    public BitBoard() {
        this.redRing0Board = new BitSet(TERRITORIES);
        this.yelRing0Board = new BitSet(TERRITORIES);
        this.bluRing0Board = new BitSet(TERRITORIES);
        this.grnRing0Board = new BitSet(TERRITORIES);
        this.redRing1Board = new BitSet(TERRITORIES);
        this.yelRing1Board = new BitSet(TERRITORIES);
        this.bluRing1Board = new BitSet(TERRITORIES);
        this.grnRing1Board = new BitSet(TERRITORIES);
        this.redRing2Board = new BitSet(TERRITORIES);
        this.yelRing2Board = new BitSet(TERRITORIES);
        this.bluRing2Board = new BitSet(TERRITORIES);
        this.grnRing2Board = new BitSet(TERRITORIES);
        this.redRing3Board = new BitSet(TERRITORIES);
        this.yelRing3Board = new BitSet(TERRITORIES);
        this.bluRing3Board = new BitSet(TERRITORIES);
        this.grnRing3Board = new BitSet(TERRITORIES);
        this.redBaseBoard = new BitSet(TERRITORIES);
        this.yelBaseBoard = new BitSet(TERRITORIES);
        this.bluBaseBoard = new BitSet(TERRITORIES);
        this.grnBaseBoard = new BitSet(TERRITORIES);
        this.hash = 0;
    }

    // TODO -- merge base bitsets into ring bitsets??

    void putPiece(Piece piece, int x, int y) { // TODO
        if (piece instanceof Ring) {
            BitSet board = getRingBoard(piece.getColor(), ((Ring) piece).getSize());
            putPiece(board, x, y);
        }

        if (piece instanceof Base) {

        }
    }

    /*
        Bit board manipulation
     */

    private static int getIndex(int x, int y) {
        return y * MAX_X + x;
    }

    private static void putPiece(BitSet bitboard, int x, int y) {
        bitboard.set(getIndex(x, y));
    }

    private static void removePiece(BitSet bitboard, int x, int y) {
        bitboard.clear(getIndex(x, y));
    }

    private static int pieceCount(BitSet bitboard) {
        return bitboard.cardinality();
    }

    private static void clear(BitSet bitboard) {
        bitboard.clear();
    }

    /*
        Getters
     */

    private BitSet getBaseBoard(Color color) {
        switch (color) {
            case RED:
                return this.redBaseBoard;
            case YELLOW:
                return this.yelBaseBoard;
            case BLUE:
                return this.bluBaseBoard;
            case GREEN:
                return this.grnBaseBoard;
            default:
                return null;
        }
    }

    private BitSet getRingBoard(Color color, int size) {
        switch(size)    {
            case 0:
                switch (color) {
                    case RED:
                        return this.redRing0Board;
                    case YELLOW:
                        return this.yelRing0Board;
                    case BLUE:
                        return this.bluRing0Board;
                    case GREEN:
                        return this.grnRing0Board;
                }
                break;
            case 1:
                switch (color) {
                    case RED:
                        return this.redRing1Board;
                    case YELLOW:
                        return this.yelRing1Board;
                    case BLUE:
                        return this.bluRing1Board;
                    case GREEN:
                        return this.grnRing1Board;
                }
                break;
            case 2:
                switch (color) {
                    case RED:
                        return this.redRing2Board;
                    case YELLOW:
                        return this.yelRing2Board;
                    case BLUE:
                        return this.bluRing2Board;
                    case GREEN:
                        return this.grnRing2Board;
                }
                break;
            case 3:
                switch (color) {
                    case RED:
                        return this.redRing3Board;
                    case YELLOW:
                        return this.yelRing3Board;
                    case BLUE:
                        return this.bluRing3Board;
                    case GREEN:
                        return this.grnRing3Board;
                }
                break;
        }
        return null;
    }

//
//    private int redRing0Board;
//    private int yelRing0Board;
//    private int bluRing0Board;
//    private int grnRing0Board;
//
//    private int redRing1Board;
//    private int yelRing1Board;
//    private int bluRing1Board;
//    private int grnRing1Board;
//
//    private int redRing2Board;
//    private int yelRing2Board;
//    private int bluRing2Board;
//    private int grnRing2Board;
//
//    private int redRing3Board;
//    private int yelRing3Board;
//    private int bluRing3Board;
//    private int grnRing3Board;
//
//    private int redBaseBoard;
//    private int yelBaseBoard;
//    private int bluBaseBoard;
//    private int grnBaseBoard;
//
//
//    /** Zobrist hash of this board */
//    private int hash;
//
//
//
//    void putPiece(Piece piece, int x, int y) { // TODO
//        if (piece instanceof Ring) {
//            int board = getRingBoard(piece.getColor(), ((Ring) piece).getSize());
//
//        }
//
//        if (piece instanceof Base) {
//
//        }
//    }
//
//    private static void putPiece(int bitboard, int x, int y) {
//
//
//    }
//
//    private int getBaseBoard(Color color) {
//        switch (color) {
//            case RED:
//                return this.redBaseBoard;
//            case YELLOW:
//                return this.yelBaseBoard;
//            case BLUE:
//                return this.bluBaseBoard;
//            case GREEN:
//                return this.grnBaseBoard;
//            default:
//                return -1; // TODO -- how?
//        }
//    }
//
//    private int getRingBoard(Color color, int size) {
//        switch(size)    {
//            case 0:
//                switch (color) {
//                    case RED:
//                        return this.redRing0Board;
//                    case YELLOW:
//                        return this.yelRing0Board;
//                    case BLUE:
//                        return this.bluRing0Board;
//                    case GREEN:
//                        return this.grnRing0Board;
//                }
//                break;
//            case 1:
//                switch (color) {
//                    case RED:
//                        return this.redRing1Board;
//                    case YELLOW:
//                        return this.yelRing1Board;
//                    case BLUE:
//                        return this.bluRing1Board;
//                    case GREEN:
//                        return this.grnRing1Board;
//                }
//                break;
//            case 2:
//                switch (color) {
//                    case RED:
//                        return this.redRing2Board;
//                    case YELLOW:
//                        return this.yelRing2Board;
//                    case BLUE:
//                        return this.bluRing2Board;
//                    case GREEN:
//                        return this.grnRing2Board;
//                }
//                break;
//            case 3:
//                switch (color) {
//                    case RED:
//                        return this.redRing3Board;
//                    case YELLOW:
//                        return this.yelRing3Board;
//                    case BLUE:
//                        return this.bluRing3Board;
//                    case GREEN:
//                        return this.grnRing3Board;
//                }
//                break;
//        }
//        return -1; // TODO - how?
//    }
//
//
//





}
