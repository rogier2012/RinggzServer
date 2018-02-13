package advanced_game_model;

import advanced_game_model.pieces.Piece;

public interface State {


    void putPiece(Piece piece, int x, int y);

    void putStart(int x, int y);

    void removeStart(int x, int y);

    void removePiece(int x, int y);

    void removePiece(Piece piece, int x, int y);

    void reset();

    Piece getPiece(int x, int y);

    int countPiece(Piece piece);

    int score(Color color, int numberOfPlayers);

    // MoveSuggestion generator

    State deepCopy();





}
