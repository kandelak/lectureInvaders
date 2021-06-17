package main.Controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;

public class KeyListener {
    private final Set<KeyCode> keysPressed = new HashSet<>();

    public Set<KeyCode> getKeysPressed() {
        return keysPressed;
    }

    public void keyPressed(KeyEvent keyEvent) {
        keysPressed.add(keyEvent.getCode());
        keyEvent.consume();
    }

    public void keyReleased(KeyEvent keyEvent) {
        keysPressed.remove(keyEvent.getCode());
        keyEvent.consume();
    }
}
