package server;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import simple_game_model.Game;
import simple_game_model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

public class Lobby extends Observable implements Runnable {

    private List<Game> games;

    private int max_players;

    private Game currentGame;

    private SimpleBooleanProperty active;

    private SimpleListProperty<Player> playerList;

    private SimpleIntegerProperty id;

//    private SimpleListProperty players;

    public Lobby(int max_players, int id) {
        this.games = new ArrayList<>();
        this.max_players = max_players;
        this.id = new SimpleIntegerProperty(id);
        this.active = new SimpleBooleanProperty(false);

        this.playerList = new SimpleListProperty<>(FXCollections.observableArrayList());
//        this.players = new SimpleStringProperty(this.playerListToString());
    }

    public void setupGame(){
        try {
            this.currentGame = new Game(this.playerList.toArray(new Player[this.playerList.size()]));
            games.add(this.currentGame);
        } catch (Game.InvalidPlayerAmountException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        this.currentGame.play();
    }

    @Override
    public void run() {
        System.out.println("Lobby " + this.id + " has started");
        while(!Thread.currentThread().isInterrupted()) {
            while (playerAmount() < max_players) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            active.set(true);
            this.setupGame();
            this.sendLobbyMessage("Game has started");
            this.startGame();
            System.out.println("Game has ended");

            active.set(false);
            for (Player player : playerList) {
                this.removePlayer(player);
            }

        }
    }

    public boolean addPlayer(Player player) {
        if (playerAmount() >= max_players) {
            return false;
        }
        playerList.add(player);
        return true;
    }

    private void sendLobbyMessage(String message) {
        for (Player player: playerList) {
            player.sendMessage(message);
        }
    }


    public void removePlayer(Player player) {
        this.playerList.remove(player);
    }
    public boolean isActive() {
        return active.get();
    }

    public int getMax_players() {
        return max_players;
    }

    public int getId() {
        return id.get();
    }

    public List<Player> getPlayers() {
        return playerList.get();
    }

    public int playerAmount() {
        return playerList.size();
    }

    public String playerListToString() {
        return playerList.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        return id+ ": " +  playerList.stream().map(Object::toString).collect(Collectors.joining(", "));
    }
}
