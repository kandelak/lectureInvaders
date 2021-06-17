package main.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Audio.AudioPlayer;
import main.Controller.Dimension2D;
import main.Controller.GameBoard;
import main.Invaders;

import static main.View.GameBoardUI.getPreferredSize;

/**
 * Starts the Invaders Application, loads the GameToolBar and GameBoardUI. This
 * class is the root of the JavaFX Application.
 *
 * @see Application
 */
public class InvadersApplication extends Application {


    private static final int GRID_LAYOUT_PADDING = 5;
    private static final int GRID_LAYOUT_PREF_HEIGHT = 1200;
    private static final int GRID_LAYOUT_PREF_WIDTH = 1000;

    private GameBoard gameBoard;

    /**
     * Starts the Bumpers Window by setting up a new tool bar, a new user interface
     * and adding them to the stage.
     *
     * @param primaryStage the primary stage for this application, onto which the
     *                     application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {

        setupGameBoard();


        Pane gridLayout = createLayout(this.gameBoard.getGameBoardUI(), this.gameBoard.getGameBoardUI().getToolBar());

        // scene and stages
        Scene scene = new Scene(gridLayout);
        primaryStage.setTitle("LectureInvaders");
        primaryStage.setScene(scene);
        //scene.setOnKeyPressed(keyEvent -> this.gameBoard.getKeyListener().keyPressed(keyEvent));
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> this.gameBoard.getKeyListener().keyPressed(keyEvent));
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> this.gameBoard.getKeyListener().keyReleased(keyEvent));
        primaryStage.setOnCloseRequest(closeEvent -> this.gameBoard.getGameBoardUI().stopGame());
        primaryStage.show();
    }

    private void setupGameBoard() {
        Dimension2D size = getPreferredSize();
        this.gameBoard = new GameBoard(size);
        this.gameBoard.setAudioPlayer(new AudioPlayer());
        this.gameBoard.getGameBoardUI().widthProperty().set(size.getWidth());
        this.gameBoard.getGameBoardUI().heightProperty().set(size.getHeight());
    }

    /**
     * Creates a new {@link Pane} that arranges the game's UI elements.
     */
    private static Pane createLayout(GameBoardUI gameBoardUI, GameToolBar toolBar) {
        // GridPanes are divided into columns and rows, like a table
        GridPane gridLayout = new GridPane();
        gridLayout.setPrefSize(GRID_LAYOUT_PREF_WIDTH, GRID_LAYOUT_PREF_HEIGHT);
        gridLayout.setVgap(GRID_LAYOUT_PADDING);
        gridLayout.setPadding(new Insets(GRID_LAYOUT_PADDING));

        // add all components to the gridLayout
        // second parameter is column index, second parameter is row index of grid
        gridLayout.add(gameBoardUI, 0, 1);
        gridLayout.add(toolBar, 0, 0);
        return gridLayout;
    }

    /**
     * The whole game will be executed through the launch() method.
     * <p>
     * Use {@link Invaders#main(String[])} to run the Java application.
     */
    public static void startApp(String[] args) {
        launch(args);
    }

}
