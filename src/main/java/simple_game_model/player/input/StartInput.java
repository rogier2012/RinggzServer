package simple_game_model.player.input;

public class StartInput extends PlayerInput {

    private final int x, y;

    public StartInput(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
