package simple_game_model.player;

import simple_game_model.Game;
import simple_game_model.Observation;
import simple_game_model.player.input.PlayerInput;

public abstract class Player {

    /** The name of the player */
    private final String name;
    /** Pieces that this player can use */
    private Game.PieceSet pieces;

    /**
     * Construct a new player
     * @param name -- The name of the player
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Decide what to do on this player's turn
     * @param observation -- The observation of the current game state on which the player can base its decision
     * @return the decision that the player has made based on the current game state observation
     */
    public abstract PlayerInput decide(Observation observation);

    /**
     * Allows the player to obtain observations of game states in turns other than his/her own's
     * @param observation -- An observation of the current game state
     */
    public abstract void observe(Observation observation);

    /*
        Getters and Setters
     */

    public String getName() {
        return name;
    }


    protected Game.PieceSet getPieceSet()   {
        return this.pieces;
    }

    public void setPieces(Game.PieceSet pieces) {
        this.pieces = pieces;
    }

}
