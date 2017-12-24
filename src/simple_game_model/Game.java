package simple_game_model;

import simple_game_model.pieces.Base;
import simple_game_model.pieces.Piece;
import simple_game_model.pieces.Ring;
import simple_game_model.pieces.StartBase;
import simple_game_model.player.Player;
import simple_game_model.player.input.MoveInput;
import simple_game_model.player.input.PlayerInput;

import java.util.*;

import static simple_game_model.Board.MAX_X;
import static simple_game_model.Board.MAX_Y;
import static simple_game_model.Board.isValidTerritory;
import static simple_game_model.Territory.SIZES;

/**
 * Models one game of Ringgz
 */
public class Game {

    /*
        Constants
     */

    /** Minimal number of players */
    private static final int MIN_NUMBER_OF_PLAYERS = 2;
    /** Maximal number of players */
    private static final int MAX_NUMBER_OF_PLAYERS = 4;
    /** Minimal x coordinate of the start base */
    private static final int MIN_START_X = 1;
    /** Maximal x coordinate of the start base */
    private static final int MAX_START_X = 3;
    /** Minimal y coordinate of the start base */
    private static final int MIN_START_Y = 1;
    /** Maximal y coordinate of the start base */
    private static final int MAX_START_Y = 3;
    /** The colors that can be assigned to a player */
    private static final Color[] PLAYER_COLORS = new Color[4];
    static {
        PLAYER_COLORS[0] = Color.RED;
        PLAYER_COLORS[1] = Color.YELLOW;
        PLAYER_COLORS[2] = Color.GREEN;
        PLAYER_COLORS[3] = Color.BLUE;
    }
    /** Relative positions of neighbouring territories */
    private static final int[] NORTH = new int[]{0, 1};
    private static final int[] SOUTH = new int[]{0, -1};
    private static final int[] EAST = new int[]{1, 0};
    private static final int[] WEST = new int[]{-1, 0};
    private static final int[][] NBS = new int[][]{NORTH, SOUTH, EAST, WEST};

    /*
        Instance variables
     */

    /** The number of players in this game */
    private final int numberOfPlayers;
    /** The players in this game */
    private final Player[] players;
    /** The state of the game */
    private final GameState state;
    /** Contains a list of pieces for each player */
    private final Map<Player, List<Piece>> playerPieces;
    /** Index of the player who has to make a move */
    private int currentPlayer;
    /** Reference to start pieces */
    private final StartBase start;
    /** For each player maps the set of usable colors for that player */
    private final Map<Player, Set<Color>> playerColors;
    /** Maps each color to which player can use it to score points */
    private final Map<Color, Player> scoreColors;

    /*
        Constructors
     */

    /**
     * Default constructor
     * @param players   -- An array of players
     * @throws InvalidPlayerAmountException -- Is thrown when there is an invalid amount of players
     */
    public Game(Player... players) throws InvalidPlayerAmountException { // TODO -- make sure game is used once
        this.numberOfPlayers = players.length;
        if (this.numberOfPlayers < MIN_NUMBER_OF_PLAYERS || this.numberOfPlayers > MAX_NUMBER_OF_PLAYERS) {
            throw new InvalidPlayerAmountException();
        }

        // Store the players in a new array (so the player array can only be referenced by this object)
        this.players = new Player[numberOfPlayers];
        System.arraycopy(players, 0, this.players, 0, numberOfPlayers);

        this.state = new GameState();
        this.currentPlayer = 0; // TODO -- which player starts?

        // Store for each player which pieces they have left (for move generation)
        this.playerPieces = new HashMap<>();
        for (Player player : players)   {
            playerPieces.put(player, new ArrayList<>());
        }

        // Initialize a mapping from each player to the colors they can use to move
        this.playerColors = new HashMap<>();
        for (int i = 0; i < this.numberOfPlayers; i++) {
            this.playerColors.put(this.players[i], new HashSet<>());
        }
        // Initialize a mapping for the colors that can be used to gain a majority on a territory
        this.scoreColors = new HashMap<>();

        // Divide pieces among players
        switch (this.numberOfPlayers)   {
            /*
                Two players: Each player is assigned two colors
             */
            case 2:
                // Assign two colors to each player
                for (int i = 0; i < numberOfPlayers * 2; i += 2)   {
                    this.playerColors.get(this.players[i / 2]).add(PLAYER_COLORS[i]);
                    this.playerColors.get(this.players[i / 2]).add(PLAYER_COLORS[i + 1]);
                    this.scoreColors.put(PLAYER_COLORS[i], this.players[i / 2]);
                    this.scoreColors.put(PLAYER_COLORS[i + 1], this.players[(i + 1) / 2]);
                    for (int n = 0; n < 3; n++) {
                        playerPieces.get(players[i / 2]).add(new Base(PLAYER_COLORS[i]));
                        playerPieces.get(players[i / 2]).add(new Base(PLAYER_COLORS[i + 1]));
                        for (int s = 0; s < SIZES; s++)   {
                            playerPieces.get(players[i / 2]).add(new Ring(PLAYER_COLORS[i], s));
                        }
                        for (int s = 0; s < SIZES; s++)   {
                            playerPieces.get(players[i / 2]).add(new Ring(PLAYER_COLORS[i + 1], s));
                        }
                    }
                }
                break;
            /*
                Three players: Each player is assigned one color. The fourth color is split among all players
             */
            case 3:
                // Assign one color to each player and a third of the fourth color
                for (int i = 0; i < numberOfPlayers; i++)   {
                    this.playerColors.get(this.players[i]).add(PLAYER_COLORS[i]);
                    this.playerColors.get(this.players[i]).add(PLAYER_COLORS[this.numberOfPlayers]);
                    this.scoreColors.put(PLAYER_COLORS[i], this.players[i]);
                    for (int n = 0; n < 3; n++) {
                        playerPieces.get(players[i]).add(new Base(PLAYER_COLORS[i]));
                        for (int s = 0; s < SIZES; s++)   {
                            playerPieces.get(players[i]).add(new Ring(PLAYER_COLORS[i], s));
                        }
                    }
                    for (int s = 0; s < SIZES; s++)   {
                        playerPieces.get(players[i]).add(new Ring(PLAYER_COLORS[numberOfPlayers], s));
                    }
                }
                break;
            /*
                Four players: Each player is assigned one color
             */
            case 4:
                // Assign one color to each player
                for (int i = 0; i < numberOfPlayers; i++)   {
                    this.playerColors.get(this.players[i]).add(PLAYER_COLORS[i]);
                    this.scoreColors.put(PLAYER_COLORS[i], this.players[i]);
                    for (int n = 0; n < 3; n++) {
                        playerPieces.get(players[i]).add(new Base(PLAYER_COLORS[i]));
                        for (int s = 0; s < SIZES; s++)   {
                            playerPieces.get(players[i]).add(new Ring(PLAYER_COLORS[i], s));
                        }
                    }
                }
                break;
            default:
                throw new InvalidPlayerAmountException();
        }

        for (Player p : this.players) {
            p.setPieces(new PieceSet(this.playerPieces.get(p)));
        }

        // Keep a reference to the start pieces
        this.start = new StartBase();
        // Give it to the first player
        this.playerPieces.get(this.players[this.currentPlayer]).add(this.start);

    }

    /*
        Methods
     */

    /**
     * Play the game!
     * @return each player's score
     */
    public Map<Player, Integer> play()  {
        // Keep a boolean for each player whether they are able to make a move (stopping criterion for game)
        Map<Player, Boolean> ableToMove = new HashMap<>();
        for (Player p : players) {
            ableToMove.put(p, true);
        }
        // Start taking turns
        while (true) {
            // Obtain the player that has to make a move
            Player player = currentPlayer();
            // Compute the possible moves for this player
            Map<Color, List<Move>> possibleMovesPerColor = possibleMoves(player);
            List<Move> possibleMoves = new ArrayList<>();
            for (Color playerColor : possibleMovesPerColor.keySet())  {
                possibleMoves.addAll(possibleMovesPerColor.get(playerColor));
            }

            System.out.println(state.prpr());

//            System.out.println("Possible moves:");
//            System.out.println(possibleMoves);

            // Check whether at least one player can still make moves. If not, end the game
            ableToMove.put(player, possibleMoves.size() > 0);
            boolean proceedGame = false;
            for (Player p : ableToMove.keySet()) {
                proceedGame |= ableToMove.get(p);
            }
            if (!proceedGame) {
                // The game ended. Count scores
                Map<Player, Integer> scores = this.scores();
                System.out.println("The game ended. Score:");
                for (Player p : this.players) {
                    System.out.printf("%s: %d\n", p.getName(), scores.get(p));
                }
                System.out.println(playerPieces);
                return scores; // TODO -- properly handle end of game
            }
            // If the current player can't move, skip to the next one
            if (!ableToMove.get(player)) {
                this.nextPlayer();
                continue;
            }
            // The game continues. Let the players observe the game state and let one take a turn
            Observation observation =  new Observation(this.state);
            // Ask the current player what should be done in this turn
            PlayerInput input = player.decide(observation);
            // Let the other players observe the current game state (before executing the current player's decision)
            for (Player p : players) {
                if (p != player) {
                    p.observe(observation); // TODO -- new observation for each player
                }
            }
            // Execute the current player's decision
            if (input instanceof MoveInput) {
                Move move = ((MoveInput) input).getMove();
                if (possibleMoves.contains(move)) {
                    try {
                        this.state.doMove(move);

                        System.out.printf("Performed %s\n", move);

                    } catch (GameState.InvalidMoveException e) {
                        continue;
                    }
                    this.playerPieces.get(player).remove(move.getPiece());
                } else {
                    System.out.println(String.format("Player %s tried to do an invalid move!", player.getName()));
                    continue;
                }
            } else {
                // TODO -- other inputs

            }
            // Switch to the next player
            this.nextPlayer();
        }

    }

    /**
     * Computes the possible moves for the specified player
     * @param player -- The player for which the moves should be computed
     * @return lists of possible moves, grouped by color
     */
    private Map<Color, List<Move>> possibleMoves(Player player) {
        // Initialize result array
        Map<Color, List<Move>> moveMap = new HashMap<>();
        // Obtain the colors relevant to this player. Initialize lists to store moves
        Set<Color> colors = getColorsOf(player);
        for (Color c : colors) {
            moveMap.put(c, new ArrayList<>());
        }

        // Check if start move has been done. If not, return with all possible start moves
        if (state.getMoves().size() == 0)   {
            List<Move> moves = new ArrayList<>();
            for (int x = MIN_START_X; x <= MAX_START_X; x++)    {
                for (int y = MIN_START_Y; y <= MAX_START_Y; y++) {
                    moves.add(new Move(x, y, this.start));
                }
            }
            moveMap.put(Color.START, moves);
            return moveMap;
        }

        // Compute possible moves on each territory
        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                // Get the pieces on this territory
                Piece[] pieces = state.getPieces(x, y);
                // If there is a base on this territory, skip it
                if (pieces[0] instanceof Base) continue;
                // Check for adjacent colors. Iterate through neighbourhoods
                for (int[] nbh : NBS) {
                    int x_ = x + nbh[0];
                    int y_ = y + nbh[1];
                    // Check if neighbouring territory exists on this side
                    if (isValidTerritory(x_, y_)) {
                        // Get pieces located on this territory
                        Piece[] nbh_pieces = state.getPieces(x_, y_);
                        // Check which colors are present on the territory
                        for (Piece nbh_piece : nbh_pieces) {
                            // If there is no pieces, skip
                            if (nbh_piece == null) continue;
                            Color nbh_color = nbh_piece.getColor();
                            // Check if the color is relevant to this player. If not, check if it is the start color. Else skip it
                            if (!colors.contains(nbh_color)) {
                                if (!(nbh_color == Color.START)) {
                                    continue;
                                }
                            }
                            // Check where rings can be placed and keep a boolean for when a base can be placed as well
                            boolean isEmpty = true;
                            for (int size = 0; size < SIZES; size++) {
                                // Check if the spot is empty
                                if (pieces[size] == null) {
                                    // Check if the player has the required pieces

                                    if (nbh_color == Color.START) { // TODO -- properly
                                        for (Color c : colors) {
                                            Ring ring = obtainRingFrom(player, c, size);
                                            if (ring != null) {
                                                // All requirements met. Add move
                                                Move move = new Move(x, y, ring);
                                                moveMap.get(c).add(move);
                                            }
                                        }
                                    } else {
                                        Ring ring = obtainRingFrom(player, nbh_color, size);
                                        if (ring != null) {
                                            // All requirements met. Add move
                                            Move move = new Move(x, y, ring);
                                            moveMap.get(nbh_color).add(move);
                                        }
                                    }


                                } else {
                                    isEmpty = false;
                                }
                            }
                            if (isEmpty) {
                                // Check if the player has the required pieces
                                if (nbh_color == Color.START) { // TODO -- proper
                                    for (Color c : colors) {
                                        Base base = obtainBaseFrom(player, c);
                                        if (base != null) {
                                            // All requirements met. Add move
                                            Move move = new Move(x, y, base);
                                            moveMap.get(c).add(move);
                                        }
                                    }
                                } else {
                                    Base base = obtainBaseFrom(player, nbh_color);
                                    if (base != null) {
                                        // All requirements met. Add move
                                        Move move = new Move(x, y, base);
                                        moveMap.get(nbh_color).add(move);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return moveMap;
    }

    /**
     * Compute the scores based on the grid and the number of players
     * @return the scores for each player
     */
    private Map<Player, Integer> scores()   {
        // Initialize score map
        Map<Player, Integer> scores = new HashMap<>();
        for (Player player : this.players) {
            scores.put(player, 0);
        }
        // Determine scores
        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                Piece[] pieces = state.getPieces(x, y);
                if (!(pieces[0] instanceof Base)) {
                    Map<Color, Integer> counter = colorOccurences(pieces);
                    Color majority = colorMajority(counter);
                    if (majority != null) {
                        scores.put(scoreColors.get(majority), scores.get(scoreColors.get(majority)) + 1);
                    }
                }
            }
        }
        return scores;
    }

    /**
     * Count how often pieces of each color occur in this array
     * @param pieces -- The pieces for which the colors should be counted
     * @return a count for each color
     */
    private Map<Color, Integer> colorOccurences(Piece[] pieces) {
        Map<Color, Integer> counter = new HashMap<>();
        for (Color color : PLAYER_COLORS) {
            counter.put(color, 0);
        }
        for (Piece piece : pieces) {
            if (piece == null) continue;
            counter.put(piece.getColor(), counter.get(piece.getColor()) + 1);
        }
        return counter;
    }

    /**
     * Determines if a color has a majority between the counters
     * @param counter -- The counter for each color
     * @return the color that has the majority (null if there is no majority)
     */
    private Color colorMajority(Map<Color, Integer> counter) {
        int max = Integer.MIN_VALUE;
        Color maxC = null;
        for (Color c : counter.keySet()) {
            if (counter.get(c) > max) {
                max = counter.get(c);
                maxC = c;
            } else if (counter.get(c) == max) {
                maxC = null;
            }
        }
        return maxC;
    }

    /**
     * Get the colors that the player can use
     * @param player -- The specified player
     * @return a set of colors relevant to the player
     */
    private Set<Color> getColorsOf(Player player) {
        return this.playerColors.get(player);
    }

    /**
     * Get a ring pieces from the player
     * @param player -- The player from which the ring should be obtained
     * @param color -- The color of the ring
     * @param size -- The size of the ring
     * @return a ring pieces of the player (null if non-existent)
     */
    private Ring obtainRingFrom(Player player, Color color, int size) {
        if (!playerColors.get(player).contains(color)) return null;
        for (Piece piece : this.playerPieces.get(player)) {
            if (piece instanceof Ring && piece.getColor() == color && ((Ring) piece).getSize() == size) {
                return (Ring) piece;
            }
        }
        return null;
    }

    /**
     * Get a base pieces from the player
     * @param player -- The player from which the base should be obtained
     * @param color -- The color of the base
     * @return a base pieces of the player (null if non-existent)
     */
    private Base obtainBaseFrom(Player player, Color color) {
        if (!playerColors.get(player).contains(color)) return null;
        for (Piece piece : this.playerPieces.get(player)) {
            if (piece instanceof Base && piece.getColor() == color) {
                return (Base) piece;
            }
        }
        return null;
    }

    /**
     * Returns whether a coordinate is a valid starting location
     * @param x -- The x coordinate
     * @param y -- The y coordinate
     * @return whether the coordinate is a valid starting location
     */
    private static boolean isValidStartCoordinate(int x, int y) {
        return x >= MIN_START_X && x <= MAX_START_X && y >= MIN_START_Y && y <= MAX_START_Y;
    }

    /**
     * @return the player whose turn it is
     */
    private Player currentPlayer()  {
        return this.players[this.currentPlayer];
    }

    /**
     * Switch to the next player
     * @return - The player whose turn it is after switching
     */
    private Player nextPlayer() {
        this.currentPlayer = (this.currentPlayer + 1) % this.numberOfPlayers;
        return currentPlayer();
    }

    /*
        Piece Set class
     */

    /**
     * A set of pieces that can only be modified within this game class' scope
     */
    public class PieceSet {

        /** The list of pieces */
        private final List<Piece> pieces;

        /**
         * Construct a new pieces set
         * @param pieces -- The pieces in the set
         */
        private PieceSet(List<Piece> pieces) {
            this.pieces = pieces;
        }

        /** Obtain a copy of the list of pieces in this pieces set */
        public List<Piece> getPieces() {
            return new ArrayList<>(this.pieces);
        }

    }

    /*
        Exceptions
     */

    /**
     * Is thrown when there are an invalid number of players in the game
     */
    public class InvalidPlayerAmountException extends Exception {
        // TODO
    }

}
