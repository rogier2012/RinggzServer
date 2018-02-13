package server;

import simple_game_model.player.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server implements Runnable{

    private static final int MAX_LOBBIES = 4;
    private Map<Player, Lobby> playerLobbyMap;

    private List<Lobby> lobbyList;
    private List<Thread> lobbyThreads;
    private ServerSocket serverSocket;
    private int fourPlayerLobbies;
    private int threePlayerLobbies;
    private int twoPlayerLobbies;

    private ServerGUI gui;

    public Server(ServerGUI gui, int fourPlayerLobbies, int threePlayerLobbies, int twoPlayerLobbies) {
        this.fourPlayerLobbies = fourPlayerLobbies;
        this.threePlayerLobbies = threePlayerLobbies;
        this.twoPlayerLobbies = twoPlayerLobbies;
        this.gui = gui;
        this.lobbyList = new ArrayList<>();


        this.playerLobbyMap = new HashMap<>();
        this.lobbyThreads = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(9090);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }


    public void setup() {
        for (int i = 0; i < fourPlayerLobbies; i++) {
            lobbyList.add(new Lobby(4, i));
        }
        for (int i = 0; i < threePlayerLobbies; i++) {
            lobbyList.add(new Lobby(3, i + fourPlayerLobbies));
        }
        for (int i = 0; i < twoPlayerLobbies; i++) {
            lobbyList.add(new Lobby(2, i + fourPlayerLobbies + threePlayerLobbies));
        }
        for (Lobby lobby : lobbyList) {
            Thread t  = new Thread(lobby);
            this.lobbyThreads.add(t);
            t.start();
        }
    }

    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Socket client = serverSocket.accept();
                this.handleClient(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Thread thread :
                lobbyThreads) {
            thread.interrupt();

        }
    }


    private void handleClient(Socket socket) {
        ClientHandler handler = new ClientHandler(socket);
        int lobby = handler.connect(this.freeLobbies(), lobbyList.size());
        boolean playerAdded = this.addPlayer(handler.getPlayer(), lobby);
        while (!playerAdded) {
            handler.sendError("Lobby might be full, try again");
            lobby = handler.connect(this.freeLobbies(), lobbyList.size());
            playerAdded = this.addPlayer(handler.getPlayer(), lobby);
        }

    }

    private String freeLobbies() {
        Map<Integer, List<Integer>> lobbyMap = new HashMap<>();
        for (Lobby lobby : lobbyList) {
            int max_player = lobby.getMax_players();
            if (!lobby.isActive() && max_player > lobby.playerAmount()) {
                if (lobbyMap.containsKey(max_player)) {
                    lobbyMap.get(max_player).add(lobby.getId());
                } else {
                    List<Integer> lobbies = new ArrayList<>();
                    lobbies.add(lobby.getId());
                    lobbyMap.put(max_player, lobbies);
                }
            }
        }
        StringBuilder freeLobbies = new StringBuilder();
        for (Integer players : lobbyMap.keySet()){
            freeLobbies.append(players).append(": ").append(lobbyMap.get(players).toString()).append(" ");
        }
        return freeLobbies.toString();
    }

    public boolean addPlayer(Player player, int lobbyNum) {
        if (this.lobbyList.size() < lobbyNum) {
            return false;
        }
        Lobby lobby = this.lobbyList.get(lobbyNum);
        boolean successful =  lobby.addPlayer(player);
        if (successful) {
            playerLobbyMap.put(player, lobby);
        }
        return successful;
    }

    private void removePlayer(Player player) {
        Lobby lobby = playerLobbyMap.get(player);
        lobby.removePlayer(player);
        lobby.removePlayer(player);
        int threadIndex = lobby.getId();
        Thread lobbyThread = lobbyThreads.get(threadIndex);
        if (lobby.isActive()) {
            lobbyThread.interrupt();
            lobby.removePlayer(player);
            Thread newThread = new Thread(lobby);
            lobbyThreads.set(threadIndex, newThread);
            newThread.start();

        } else {
            lobby.removePlayer(player);
        }

    }

    public List<Lobby> getLobbyList() {
        return lobbyList;
    }
}
