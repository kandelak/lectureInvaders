package main.View;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.Audio.AudioPlayer;
import main.Controller.*;
import main.GameEntity.Alien;
import main.GameEntity.Entity;
import main.GameEntity.Player;

import java.net.URL;
import java.util.*;

/**
 * This class implements the user interface for steering the player cannon. The
 * user interface is implemented as a Thread that is started by clicking the
 * start button on the tool bar and stops by the stop button.
 */
public class GameBoardUI extends Canvas {

    private static final Color BACKGROUND_COLOR = Color.DARKBLUE;
    /**
     * The update period of the game in ms, this gives us 25 fps.
     */
    private static final int UPDATE_PERIOD = 1000 / 25;
    private static final int DEFAULT_WIDTH = 1500;
    private static final int DEFAULT_HEIGHT = 900;
    private static final Dimension2D DEFAULT_SIZE = new Dimension2D(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    public static Dimension2D getPreferredSize() {
        return DEFAULT_SIZE;
    }

    /**
     * Timer responsible for updating the game every frame that runs in a separate
     * thread.
     */
    private Timer gameTimer;

    private GameBoard gameBoard;

    private final GameToolBar gameToolBar;

    private List<KeyListener> keyListeners;

    private HashMap<String, Image> imageCache;

    public GameBoardUI(GameToolBar gameToolBar) {
        this.gameToolBar = gameToolBar;
        this.keyListeners = new LinkedList<>();
        setup();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public List<KeyListener> getKeyListener() {
        return keyListeners;
    }

    /**
     * Removes all existing entities from the game board and re-adds them. Player cannon is
     * reset to default starting position. Renders graphics.
     */
    public void setup() {
        setupGameBoard();
        setupImageCache();
        this.gameToolBar.updateToolBarStatus(false);
        paint();
    }

    private void setupGameBoard() {
        Dimension2D size = getPreferredSize();
        this.gameBoard = new GameBoard(size);
        this.gameBoard.setAudioPlayer(new AudioPlayer());
        widthProperty().set(size.getWidth());
        heightProperty().set(size.getHeight());
        for (Player player : this.gameBoard.getPlayers()) {
            this.keyListeners.add(new KeyListener(this, player));
        }

    }

    private void setupImageCache() {
        this.imageCache = new HashMap<>();
        for (Entity entity : this.gameBoard.getAliens()) {
            String imageLocation = entity.getIconLocation();
            this.imageCache.computeIfAbsent(imageLocation, this::getImage);
        }

        for (Player player : this.gameBoard.getPlayers()) {
            String playerImageLocation = player.getCannon().getIconLocation();
            this.imageCache.put(playerImageLocation, getImage(playerImageLocation));
        }

    }

    /**
     * Sets the entity's image.
     *
     * @param entityImageFilePath an image file path that needs to be available in the
     *                            resources folder of the project
     */
    private Image getImage(String entityImageFilePath) {
        URL entityImageUrl = getClass().getClassLoader().getResource(entityImageFilePath);
        if (entityImageUrl == null) {
            throw new IllegalArgumentException(
                    "Please ensure that your resources folder contains the appropriate files for this exercise.");
        }
        return new Image(entityImageUrl.toExternalForm());
    }

    /**
     * Starts the GameBoardUI Thread, if it wasn't running. Starts the game board,
     * which causes the entities to change their positions (i.e. move). Renders graphics
     * and updates tool bar status.
     */
    public void startGame() {
        if (!this.gameBoard.isRunning()) {
            this.gameBoard.startGame();
            this.gameToolBar.updateToolBarStatus(true);
            startTimer();
            paint();
        }
    }

    private void startTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                updateGame();
            }
        };
        if (this.gameTimer != null) {
            this.gameTimer.cancel();
        }
        this.gameTimer = new Timer();
        this.gameTimer.scheduleAtFixedRate(timerTask, UPDATE_PERIOD, UPDATE_PERIOD);
    }

    private void updateGame() {
        if (gameBoard.isRunning()) {
            // updates entity positions and re-renders graphics
            this.gameBoard.update();
            // when this.gameBoard.getOutcome() is OPEN, do nothing
            if (this.gameBoard.getGameOutcome() == GameOutcome.LOST) {
                showAsyncAlert("Oh.. you lost.");
                this.stopGame();
            } else if (this.gameBoard.getGameOutcome() == GameOutcome.WON) {
                showAsyncAlert("Congratulations! You won!!");
                this.stopGame();
            }
            paint();
        }
    }

    /**
     * Stops the game board and set the tool bar to default values.
     */
    public void stopGame() {
        if (this.gameBoard.isRunning()) {
            this.gameBoard.stopGame();
            this.gameToolBar.updateToolBarStatus(false);
            this.gameTimer.cancel();
        }
    }

    /**
     * Render the graphics of the whole game by iterating through the entities of the
     * game board at render each of them individually.
     */
    private void paint() {
        getGraphicsContext2D().setFill(BACKGROUND_COLOR);
        getGraphicsContext2D().fillRect(0, 0, getWidth(), getHeight());

        for (Alien alien : this.gameBoard.getAliens()) {
            paintEntity(alien);
        }
        // render player cannon
        for (Player player : this.gameBoard.getPlayers()) {
            paintEntity(player.getCannon());
        }

    }

    /**
     * Show image of a entity at the current position of the entity.
     *
     * @param entity to be drawn
     */
    private void paintEntity(Entity entity) {
        Point2D entityPosition = entity.getPosition();

        getGraphicsContext2D().drawImage(this.imageCache.get(entity.getIconLocation()), entityPosition.getX(),
                entityPosition.getY(), entity.getSize().getWidth(), entity.getSize().getHeight());
    }

    /**
     * Method used to display alerts in moveEntities().
     *
     * @param message you want to display as a String
     */
    private void showAsyncAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(message);
            alert.showAndWait();
            this.setup();
        });
    }
}
