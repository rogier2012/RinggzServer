package simple_game_model.player.computer;

import simple_game_model.Observation;
import simple_game_model.player.Player;
import simple_game_model.player.computer.strategy.Strategy;
import simple_game_model.player.input.MoveInput;
import simple_game_model.player.input.PlayerInput;

public abstract class ComputerPlayer extends Player {

    /**
     * Strategy upon which moves are based
     */
    private final Strategy strategy;

    /**
     * Constructor
     */
    public ComputerPlayer(String name, Strategy strategy) {
        super(name);
        this.strategy = strategy;
    }

    /**
     * Computers only do moves according to their strategy
     */
    @Override
    public PlayerInput decide(Observation observation) {
        return new MoveInput(strategy.determineMove(observation));
    }

    /**
     * Update strategy's game model
     * @param observation -- An observation of the current game state
     */
    @Override
    public void observe(Observation observation) {
        this.strategy.observe(observation);
    }

    /*
        Getters
     */

    protected Strategy getStrategy() {
        return strategy;
    }

}