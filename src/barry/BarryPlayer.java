package barry;

import simple_game_model.Observation;
import simple_game_model.player.computer.ComputerPlayer;
import simple_game_model.player.computer.strategy.Strategy;

public class BarryPlayer extends ComputerPlayer {

    private final GameState state;

    public BarryPlayer(String name, Strategy strategy) {
        super(name, strategy);
        this.state = null; // TODO
    }

    @Override
    public void observe(Observation observation) {
        // TODO -- update game state

        super.observe(observation);
    }
}
