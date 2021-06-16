package main.Controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.GameEntity.Player;
import main.View.GameBoardUI;

public class KeyListener {
	KeyPressed keyPressed;
	private final Player player;

	// step width for the movement of the player's canon
	private static double MOVE_RIGHT = 1.25;
	private static double MOVE_LEFT = -1.25;

	public KeyListener(GameBoardUI gameBoardUI, Player player) {
		this.player = player;
		gameBoardUI.addEventHandler(KeyEvent.KEY_PRESSED, this::keyPressed);
	}

	public void keyPressed(KeyEvent keyEvent) {
		// TODO: shoot keyEvent not handled yet
		// TODO: Initialize KeyListener with the player in setup

		if (keyEvent.getCode() == KeyCode.KP_RIGHT) {
			keyPressed = KeyPressed.RIGHT;
			player.moveCanon(MOVE_RIGHT, null);
		} else if (keyEvent.getCode() == KeyCode.KP_LEFT) {
			keyPressed = KeyPressed.LEFT;
			player.moveCanon(MOVE_LEFT, null);
		} else {
			keyPressed = KeyPressed.IGNORE;
		}
	}

	public KeyPressed getKeyPressed() {
		return keyPressed;
	}

}
