package server;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import simple_game_model.player.Player;
import simple_game_model.player.computer.Idiot;

import java.util.List;

public class ServerGUI extends Application {
    private TableView<Lobby> table = new TableView<>();
    private Server server;
    private ObservableList<Lobby> data;
    private Thread thread;

    public ServerGUI() {

    }

    @Override
    public void start(Stage primaryStage) {

        Button btn = new Button();
        btn.setText("Add player");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Idiot player = new Idiot("Barry");
                server.addPlayer(player,8);
                updateGUI();
            }
        });
        Button btn2 = new Button();
        btn2.setText("Refresh");
        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                updateGUI();
            }
        });

        GridPane root = new GridPane();
        root.add(btn, 1, 0);
        root.add(btn2, 1, 1);


        root.add(table, 0, 0);

        TableColumn<Lobby, Integer> idCol = new TableColumn<>("id");
        TableColumn<Lobby, Boolean> gameActive = new TableColumn<>("active");
        TableColumn<Lobby, List<Player>> players = new TableColumn<>("players");

        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
                new PropertyValueFactory<Lobby, Integer>("id"));

        gameActive.setMinWidth(100);
        gameActive.setCellValueFactory(
                new PropertyValueFactory<Lobby, Boolean>("active"));

        players.setMinWidth(300);
        players.setCellValueFactory(new PropertyValueFactory<Lobby, List<Player>>("players"));

        table.getColumns().addAll(idCol, gameActive, players);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        startServer();
        table.setItems(data);
    }

    public void startServer(){
        server = new Server(this, 4, 4, 4);

        server.setup();
        data = FXCollections.observableList(server.getLobbyList());

        thread  = new Thread(server);
        thread.start();
    }


    private void updateGUI(){
        this.table.refresh();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }
}
