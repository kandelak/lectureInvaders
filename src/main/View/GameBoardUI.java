package main.View;

import java.net.URL;
import java.util.*;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.Controller.Dimension2D;
import main.Controller.GameBoard;
import main.Controller.Point2D;
import main.GameEntity.Alien;
import main.GameEntity.Entity;
import main.GameEntity.Player;

/**
 * This class implements the user interface for steering the player cannon. The
 * user interface is implemented as a Thread that is started by clicking the
 * start button on the tool bar and stops by the stop button.
 */
public class GameBoardUI extends Canvas {

    private static final Color BACKGROUND_COLOR = Color.DARKBLUE;

    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 500;
    private static final Dimension2D DEFAULT_SIZE = new Dimension2D(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    private final GameToolBar toolBar;

    public static Dimension2D getPreferredSize() {
        return DEFAULT_SIZE;
    }

    private final GameBoard gameBoard;
    private HashMap<String, Image> imageCache;

    public GameBoardUI(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.toolBar = new GameToolBar(); // the tool bar object with start and stop buttons

        setup();
        toolBar.initializeActions(this);
    }

    /**
     * Removes all existing entities from the game board and re-adds them. Player
     * cannon is reset to default starting position. Renders graphics.
     */
    public void setup() {
        this.imageCache = new HashMap<>();
        paint();
    }

    /**
     * Sets the entity's image.
     *
     * @param entityImageFilePath an image file path that needs to be available in
     *                            the resources folder of the project
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
     * which causes the entities to change their positions (i.e. move). Renders
     * graphics and updates tool bar status.
     */
    public void startGame() {
        this.toolBar.updateToolBarStatus(true);
        paint();
    }

    private void updateGame() {
        paint();
    }

    /**
     * Stops the game board and set the tool bar to default values.
     */
    public void stopGame() {
        this.toolBar.updateToolBarStatus(false);
    }

    /**
     * Render the graphics of the whole game by iterating through the entities of
     * the game board at render each of them individually.
     */
    public void paint() {
        getGraphicsContext2D().setFill(BACKGROUND_COLOR);
        getGraphicsContext2D().fillRect(0, 0, getWidth(), getHeight());

        for (Entity entity : this.gameBoard.getEntities()) {
            this.imageCache.computeIfAbsent(entity.getIconLocation(), this::getImage);
            paintEntity(entity);
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
    public void showAsyncAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(message);
            alert.showAndWait();
        });
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public GameToolBar getToolBar() {
        return toolBar;
    }
}
