package Controller;

import GameEntity.Alien;
import GameEntity.Cannon;
import GameEntity.Player;
import View.GameBoardUI;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    List<Player> players = new ArrayList<>();
    List<Cannon> cannons = new ArrayList<>();
    private static final int NUMBER_OF_ALIENS = 15;
    KeyListener keyListener;
    GameBoardUI gameBoardUI;
    private final Dimension2D size;
    List<Alien> aliens = new ArrayList<>();
    private boolean running;
    private static double DEFAULT_ALIEN_STEP = 1.0;

    public GameBoard(Dimension2D size) {
        this.size = size;
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

    }

    void start() {

    }

    void stop() {

    }

    boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Moves all Alliens on this game board one step downwards and Player too if
     * key is pressed.
     */
    void moveObjects() {
        for (var auto : aliens) {
            auto.moveDown(DEFAULT_ALIEN_STEP);
        }

    }


    public List<Player> getPlayers() {
        return players;
    }


    public void setCharacters(List<Player> players) {
        this.players = players;
    }

    public List<Cannon> getCannons() {
        return cannons;
    }

    public void setCannons(List<Cannon> cannons) {
        this.cannons = cannons;
    }

    public Dimension2D getSize() {
        return size;
    }
}
