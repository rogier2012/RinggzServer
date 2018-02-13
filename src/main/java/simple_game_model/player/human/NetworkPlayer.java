package simple_game_model.player.human;

import server.ClientHandler;
import simple_game_model.Observation;
import simple_game_model.player.Player;
import simple_game_model.player.input.PlayerInput;

import java.io.*;
import java.net.Socket;

public class NetworkPlayer extends Player {
    private ClientHandler handler;

    public NetworkPlayer(String name, ClientHandler handler) {
        super(name);
        this.handler = handler;
    }



    @Override
    public PlayerInput decide(Observation observation) {

        return null;
    }

    @Override
    public void observe(Observation observation) {

    }

    @Override
    public void sendMessage(String message) {
        handler.sendGameMessage(message);
    }
}
