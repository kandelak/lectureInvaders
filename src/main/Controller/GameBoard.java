package main.Controller;

import main.Audio.AudioPlayerInterface;
import main.GameEntity.Alien;
import main.GameEntity.Cannon;
import main.GameEntity.Player;
import main.View.GameBoardUI;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {


    private static final int NUMBER_OF_ALIENS = 15;
    private static final double DEFAULT_ALIEN_STEP = 1.0;
    private final static double DEFAULT_CANNON_STEP = 1.0;
    private final static int PLAYER_LIFE_POINTS = 3;
    private AudioPlayerInterface audioPlayer;
    private GameOutcome gameOutcome = GameOutcome.OPEN;


    private final Dimension2D size;
    List<Alien> aliens = new ArrayList<>();
    List<Player> players = new ArrayList<>();


    private boolean running;


    GameBoardUI gameBoardUI;


    public GameBoard(Dimension2D size) {
        this.size = size;
        players.add(new Player(PLAYER_LIFE_POINTS, new Cannon(this.size)));
        players.forEach(Player::setup);
        createAliens();
    }

    /**
     * Creates Aliens and gives them positions
     */
    private void createAliens() {
        for (int i = 0; i < NUMBER_OF_ALIENS; i++) {
            aliens.add(new Alien(this.size));
        }
        positionAliens();
    }

    private void positionAliens() {

    }

    /**
     * Updates the position of each Alien.
     */
    public void update() {
        moveEntities();
    }

    public void startGame() {
        playMusic();
        this.running = true;
    }

    /**
     * Stops the game. Cars stop moving and background music stops playing.
     */
    public void stopGame() {
        stopMusic();
        this.running = false;
    }

    public AudioPlayerInterface getAudioPlayer() {
        return this.audioPlayer;
    }

    public void setAudioPlayer(AudioPlayerInterface audioPlayer) {
        this.audioPlayer = audioPlayer;
    }


    /**
     * Starts the background music.
     */
    public void playMusic() {
        this.audioPlayer.playBackgroundMusic();
    }

    /**
     * Stops the background music.
     */
    public void stopMusic() {
        this.audioPlayer.stopBackgroundMusic();
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Moves all Aliens on this game board one step downwards and Cannon too if
     * key is pressed.
     */
    //TODO: Gameboard should not have a keyListener!
    void moveEntities() {
        // Why is there a for each loop for the aliens, and a foreach call for players???
        for (Alien alien : aliens) {
            alien.moveDown(DEFAULT_ALIEN_STEP, this.size);
        }
        players.forEach(t -> t.moveCanon(DEFAULT_CANNON_STEP, this.size));

    }


    public List<Player> getPlayers() {
        return players;
    }

    public List<Alien> getAliens() {
        return aliens;
    }

    public GameOutcome getGameOutcome() {
        return gameOutcome;
    }


    public void setCharacters(List<Player> players) {
        this.players = players;
    }


    public Dimension2D getSize() {
        return size;
    }
}
