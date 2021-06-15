package Controller;

import GameEntity.Alien;
import GameEntity.Cannon;
import GameEntity.Player;
import View.GameBoardUI;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {


    private static final int NUMBER_OF_ALIENS = 15;


    private final Dimension2D size;
    private List<Alien> aliens = new ArrayList<>();
    Player player;

    private DataCollector dataCollector;
    private boolean running;

    KeyListener keyListener;

    GameBoardUI gameBoardUI;


    public GameBoard(Dimension2D size, int playerLifePoints) {
        this.size = size;
        this.player = new Player(playerLifePoints, new Cannon());
        player.setup();
        createAliens();
        this.dataCollector = new DataCollector();

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
        updateObjects();
    }

    public void start() {
        this.running = true;
        dataCollector.start = LocalTime.now();
    }

    public void stop() {
        this.running = false;
        dataCollector.end = LocalTime.now();
        dataCollector.evaluate();
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public List<Alien> getAliens() {
        return aliens;
    }

    /**
     * Moves all Aliens on this game board one step downwards,Cannons horizontally and
     * shoots LaserBolt when corresponding keys are pressed.
     */
    void updateObjects() {
        for (var auto : aliens) {
            auto.moveDown(this.size);
        }
        player.updateCannon(keyListener.listen(), this.size);
        // iterate through all cars (except player car) and check if it is crunched
        for (var alien : aliens) {
            if (alien.isAlienDown()) {
                // because there is no need to check for a collision
                continue;
            }

            //is not implemented fully
            Collision collision = new Collision(alien, player.getCannon().getLaserBolt());

            if (collision.isCollision()) {
                dataCollector.incrementMonstersShoot();
            }
        }
    }


    public Dimension2D getSize() {
        return size;
    }
}
