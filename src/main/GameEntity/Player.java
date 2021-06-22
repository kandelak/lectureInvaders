package main.GameEntity;

public class Player {

    /**
     * Start direction for the cannon
     */
    // TODO: New start coordinates for the cannon
    private static final int START_DIRECTION = 90;
    private final Cannon cannon;
    private static final int DEFAULT_LIFE_POINTS = 4;
    private int playerLifePoints;

    public Player(Cannon cannon) {
        this.playerLifePoints = DEFAULT_LIFE_POINTS;
        this.cannon = cannon;

    }

    public void decrementLifePoints() {
        playerLifePoints -= 1;
    }

    /**
     * Prepares player's cannon for the start of the game.
     */
    public void setup() {
        cannon.setDirection(START_DIRECTION);
        cannon.setup();
        this.playerLifePoints = DEFAULT_LIFE_POINTS;
    }

    public int getPlayerLifePoints() {
        return playerLifePoints;
    }

    public Cannon getCannon() {
        return cannon;
    }

}
