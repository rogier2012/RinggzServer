package server;

import simple_game_model.player.human.NetworkPlayer;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private BufferedReader reader;
    private PrintWriter writer;
    private ClientState state;
    private NetworkPlayer player;

    public ClientHandler(Socket socket) {

        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        state = ClientState.JOINING;
    }

    public int connect(String freeLobbies, int maxLobbyNum) {
        while (state == ClientState.JOINING) {
            this.sendLobbies(freeLobbies);
            //        Name Lobby
            String nameLobby = null;
            try {
                nameLobby  = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (nameLobby != null && !nameLobby.equals("")) {
                String[] split = nameLobby.split(" ");
                if (split.length == 2){
                    player = new NetworkPlayer(split[1], this);
                    state = ClientState.INGAME;
                    int lobby = Integer.parseInt(split[0]);
                    if (lobby >= maxLobbyNum || lobby < 0) {
                        this.sendError("Lobby does not exists");
                    } else {
                        return lobby;
                    }
                } else {
                    this.sendError("Wrong number of arguments");
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public NetworkPlayer getPlayer() {
        return player;
    }

    public void sendError(String error) {
        writer.write("Error: " + error + "\n");
        writer.flush();
    }

    public void sendLobbies(String lobbies){
        writer.write("Free Lobbies: " + lobbies + "\n");
        writer.flush();
    }

    public void sendGameMessage(String message) {
        writer.write("Game hosts speaks: " + message + "\n");
        writer.flush();
    }


}
