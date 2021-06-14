package GameEntity;

public class Player {
    private static final int DEFAULT_LIFE_POINTS = 4;
    private int playerLifePoints = DEFAULT_LIFE_POINTS;

    Player(int playerLifePoints) {
        this.playerLifePoints = playerLifePoints;
    }

    public void decrementLifePoints() {
        playerLifePoints -= 1;
    }

}
