package simple_game_model.player.input;

import simple_game_model.moves.Move;

public class MoveInput extends PlayerInput {

    private final Move move;

    public MoveInput(Move move) {
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

}
