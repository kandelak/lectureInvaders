package Controller;

import GameEntity.Alien;
import GameEntity.Cannon;
import GameEntity.Player;
import View.GameBoardUI;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {


    private static final int NUMBER_OF_ALIENS = 15;
    private static final double DEFAULT_ALIEN_STEP = 1.0;
    private final static double DEFAULT_CANNON_STEP = 1.0;


    private final Dimension2D size;
    List<Alien> aliens = new ArrayList<>();
    List<Player> players = new ArrayList<>();


    private boolean running;

    KeyListener keyListener;

    GameBoardUI gameBoardUI;


    public GameBoard(Dimension2D size, int playerLifePoints) {
        this.size = size;
        players.add(new Player(playerLifePoints, new Cannon()));
        players.forEach(Player::setup);
        createAliens();
    }

    /**
     * Creates Aliens and gives them positions
     */
    private void createAliens() {
        for (int i = 0; i < NUMBER_OF_ALIENS; i++) {
            aliens.add(new Alien());
        }
        positionAliens();
    }

    private void positionAliens() {

    }

    /**
     * Updates the position of each Alien.
     */
    public void update() {
        moveObjects();
    }

    public void start() {
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }

    boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Moves all Alliens on this game board one step downwards and Cannon too if
     * key is pressed.
     */
    void moveObjects() {
        for (var auto : aliens) {
            auto.moveDown(DEFAULT_ALIEN_STEP, this.size);
        }
        players.forEach(t -> t.moveCanon(DEFAULT_CANNON_STEP, keyListener.listen(), this.size));

    }


    public List<Player> getPlayers() {
        return players;
    }


    public void setCharacters(List<Player> players) {
        this.players = players;
    }


    public Dimension2D getSize() {
        return size;
    }
}
