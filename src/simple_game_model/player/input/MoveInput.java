package simple_game_model.player.input;

import simple_game_model.Move;

public class MoveInput extends PlayerInput {

    private final Move move;

    public MoveInput(Move move) {
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

}
