package GameEntity;

import Controller.Dimension2D;
import Controller.KeyPressed;

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

    public Player(int playerLifePoints, Cannon cannon) {
        if (playerLifePoints <= 0) this.playerLifePoints = DEFAULT_LIFE_POINTS;
        else this.playerLifePoints = playerLifePoints;
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

    public void moveCanon(double step, KeyPressed keyPressed, Dimension2D size) {
        this.cannon.moveHorizontally(step, keyPressed);
    }

    public int getPlayerLifePoints() {
        return playerLifePoints;
    }
}
