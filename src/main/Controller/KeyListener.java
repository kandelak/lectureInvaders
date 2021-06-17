package main.Controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.GameEntity.Player;
import main.View.GameBoardUI;

public class KeyListener {
	private KeyCode keyPressed;

	public KeyListener() {	}

	public void keyPressed(KeyEvent keyEvent) {
		keyPressed = keyEvent.getCode();
	}

	public KeyCode getKeyPressed() {
		return keyPressed;
	}

	public void keyReleased(KeyEvent keyEvent) {
		if(keyEvent.getCode() == keyPressed)
			keyPressed = null;
	}
}
