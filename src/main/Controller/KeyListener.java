package main.Controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.GameEntity.Player;
import main.View.GameBoardUI;


public class KeyListener {
    KeyPressed keyPressed;
    private final Player player;

    public KeyListener(GameBoardUI gameBoardUI, Player player) {
        this.player = player;
        gameBoardUI.addEventHandler(KeyEvent.KEY_PRESSED, this::keyPressed);
    }


    public void keyPressed(KeyEvent keyEvent) {
        //TODO: This method should call other methods in its linked player to perform a movement (like in Bumpers). This movement should continue as long as the key is pressed.
        if (keyEvent.getCode() == KeyCode.KP_RIGHT)
            keyPressed = KeyPressed.RIGHT;
        else if (keyEvent.getCode() == KeyCode.KP_LEFT)
            keyPressed = KeyPressed.LEFT;
        else {
            keyPressed = KeyPressed.IGNORE;
        }
    }

    public KeyPressed getKeyPressed() {
        return keyPressed;
    }

}
