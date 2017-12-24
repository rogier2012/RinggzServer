package simple_game_model.player.computer.strategy;

import simple_game_model.Move;
import simple_game_model.Observation;

public interface Strategy {

    Move determineMove(Observation observation);

    void observe(Observation observation);

}