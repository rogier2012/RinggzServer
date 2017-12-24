package simple_game_model.player;

import simple_game_model.Color;
import simple_game_model.Move;
import simple_game_model.Observation;
import simple_game_model.pieces.Base;
import simple_game_model.pieces.Piece;
import simple_game_model.pieces.Ring;
import simple_game_model.player.input.MoveInput;
import simple_game_model.player.input.PlayerInput;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static simple_game_model.Board.MAX_X;
import static simple_game_model.Board.MAX_Y;
import static simple_game_model.Territory.SIZES;

public class HumanPlayer extends Player {

    /** Move input consist of two numbers, seperated by a space */
    private static final String MOVE_INPUT_FORMAT = String.format("[0-%s] [0-%s] [A-Z][A-Z][0-%s]?", Integer.toString(MAX_X - 1), Integer.toString(MAX_Y - 1), Integer.toString(SIZES - 1));
    /** Compiled version of the input regex */
    private static final Pattern MOVE_INPUT_REGEX = Pattern.compile(MOVE_INPUT_FORMAT);

    /** The player is asked to make a move */
    private static final String MOVE_REQUEST = "what is your next move? (format: [x] [y] [piece])";
    /** The player is informed their input was invalid */
    private static final String INVALID_INPUT = "Input invalid! Try again";
    /** Where the human player input is obtained from */
    private static final InputStream INPUT_SOURCE = System.in;

    /**
     * Construct a new player
     *
     * @param name -- The name of the player
     */
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public PlayerInput decide(Observation observation) {
        PlayerInput result;
        // Ask the player for input
        System.out.println(this.getName() + ", " + MOVE_REQUEST);
        Scanner scanner = new Scanner(INPUT_SOURCE);
        do {
            // Read player input
            String input = scanner.nextLine();
            Matcher matcher = MOVE_INPUT_REGEX.matcher(input);
            if (matcher.matches()) {
                String[] ss = input.split(" ");
                int x = Integer.parseInt(ss[0]);
                int y = Integer.parseInt(ss[1]);
                char[] pieceChars = ss[2].toCharArray();
                char cc = pieceChars[0];
                Color c;
                switch (cc) {
                    case 'R':
                        c = Color.RED;
                        break;
                    case 'B':
                        c = Color.BLUE;
                        break;
                    case 'Y':
                        c = Color.YELLOW;
                        break;
                    case 'G':
                        c = Color.GREEN;
                        break;
                    case 'S':
                        c = Color.START;
                        break;
                    default:
                        c = null;
                }
                char pc = pieceChars[1];
                Piece piece = null;
                if (pc == 'R') {
                    int size = Integer.parseInt(String.valueOf(pieceChars[2]));
                    for (Piece p : getPieceSet().getPieces()) {
                        if (p instanceof Ring && p.getColor() == c && ((Ring) p).getSize() == size) {
                            piece = p;
                            break;
                        }
                    }

                } else if (pc == 'B') {
                    for (Piece p : getPieceSet().getPieces()) {
                        if (p instanceof Base && p.getColor() == c) {
                            piece = p;
                            break;
                        }
                    }
                }
                Move move = new Move(x, y, piece);
                System.out.println(move);
                result = new MoveInput(move);
                break;
            }
            System.out.println(INVALID_INPUT);
        } while (true);

//        scanner.close();
        return result;
    }

    @Override
    public void observe(Observation observation) {

    }
}
