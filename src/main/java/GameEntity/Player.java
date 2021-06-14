package GameEntity;

public class Player {
    /**
     * Start direction for the cannon
     */
    private static final int START_DIRECTION = 90;
    private static final double START_X_COORDINATE = 0.0;
    private static final double START_Y_COORDINATE = 0.0;
    private final Cannon cannon;
    private static final int DEFAULT_LIFE_POINTS = 4;
    private int playerLifePoints;

    Player(int playerLifePoints, Cannon cannon) {
        this.playerLifePoints = playerLifePoints;
        this.cannon = cannon;
    }

    public void decrementLifePoints() {
        playerLifePoints -= 1;
    }

    /**
     * Prepares player's cannon for the start of the game.
     */
    public void setup() {
        cannon.setPosition(START_X_COORDINATE, START_Y_COORDINATE);
        cannon.setDirection(START_DIRECTION);
    }

    void moveCanon() {
        this.cannon.moveHorizontally();
    }

    public int getPlayerLifePoints() {
        return playerLifePoints;
    }
}
