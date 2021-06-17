package main.View;


import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;


/**
 * This class visualizes the tool bar with start and stop buttons above the game board.
 */
public class GameToolBar extends ToolBar {
    public enum GAME_TOOLBAR_ACTION {
        START,
        STOP,
        SETTINGS
    }

    private final Button start;
    private final Button stop;

    public GameToolBar() {
        this.start = new Button("Start");
        this.stop = new Button("Stop");

        // the game is stopped initially
        updateToolBarStatus(false);
        getItems().addAll(this.start, new Separator(), this.stop);
    }

    /**
     * Initializes the actions of the toolbar buttons.
     */
    public void initializeActions(GameBoardUI gameBoardUI) {
        this.start.setOnAction(event -> gameBoardUI.getGameBoard().onAction(GAME_TOOLBAR_ACTION.START));
        this.stop.setOnAction(event -> gameBoardUI.getGameBoard().onAction(GAME_TOOLBAR_ACTION.STOP));
    }

    /**
     * Updates the status of the toolbar. This will for example enable or disable
     * buttons.
     *
     * @param running true if game is running, false otherwise
     */
    public void updateToolBarStatus(boolean running) {
        this.start.setDisable(running);
        this.stop.setDisable(!running);
    }
}
